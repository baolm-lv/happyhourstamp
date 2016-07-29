/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvmama.vst.back.client.ord.service.OrderService;
import com.lvmama.vst.back.order.po.OrdOrder;
import com.lvmama.vst.comm.utils.SynchronizedLock;
import com.lvmama.vst.comm.vo.ResultHandle;
import com.lvmama.vst.hhs.common.exception.StampBizException;
import com.lvmama.vst.hhs.common.exception.StampErrorCodes;
import com.lvmama.vst.hhs.model.common.Constant;
import com.lvmama.vst.hhs.model.common.Constant.OperateType;
import com.lvmama.vst.hhs.model.common.Constant.RefundStatus;
import com.lvmama.vst.hhs.model.common.Constant.StampStatus;
import com.lvmama.vst.hhs.model.stamp.StampRefundApplyRequest;
import com.lvmama.vst.hhs.model.stamp.StampRefundOrder;
import com.lvmama.vst.hhs.model.stamp.StampRefundResponse;
import com.lvmama.vst.hhs.stampDefinition.dao.StampDefinitionEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampRefundOrderEntity;
import com.lvmama.vst.hhs.stampDefinition.dao.StampUnbindOrderEntity;
import com.lvmama.vst.hhs.stampDefinition.repository.StampRefundOrderRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampRepository;
import com.lvmama.vst.hhs.stampDefinition.repository.StampUnbindOrderRepository;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampDefinitionRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampRefundOrderRepositorySlave;
import com.lvmama.vst.hhs.stampDefinition.repositorySlave.StampRepositorySlave;

/**
 * @author baolm
 *
 */
@Service
public class StampRefundServiceImpl implements StampRefundService {

    private static final Logger logger = LoggerFactory.getLogger(StampRefundServiceImpl.class);

    // 订单取消CODE
    private static String ORDER_CANCEL_CODE = "STAMP_ALL_REFUND";
    // 订单取消原因
    private static String ORDER_CANCEL_REASON = "预售券已退系统取消";
    // 订单取消操作人ID
    private static String ORDER_CANCEL_OPERATOR_ID = "SYSTEM";

    private static ExecutorService thread_pool = Executors.newFixedThreadPool(4);

    @Autowired
    private OrderService orderService;
    @Autowired
    private StampRefundOrderRepository refundOrderRepository;
    @Autowired
    private StampRefundOrderRepositorySlave refundOrderRepositorySlave;
    @Autowired
    private StampUnbindOrderRepository refundUnbindRepository;
    @Autowired
    private StampRepository stampRepository;
    @Autowired
    private StampRepositorySlave stampRepositorySlave;
    @Autowired
    private StampDefinitionRepositorySlave stampDefinitionRepositorySlave;
    @Autowired
    private StampOperationLogService operationLogService;
    @Autowired
    private PreSaleInventoryUnitService preSaleInventoryUnitService;

    /*
     * (non-Javadoc)
     * 
     * @see com.lvmama.vst.hhs.stamp.service.StampRefundService#refundStampApply(com.
     * lvmama.vst.hhs.model.stamp.StampRefundApplyRequest)
     */
    @Override
    @Transactional("transactionManager")
    public StampRefundResponse refundStampApply(StampRefundApplyRequest request) {

        if (StringUtils.isBlank(request.getRefundApplyId()))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundApplyId is empty");
        if (StringUtils.isBlank(request.getOrderId()))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "orderId is empty");
        if (StringUtils.isBlank(request.getUserId()))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "userId is empty");
        if (request.getRefundNum() == null || request.getRefundNum().intValue() <= 0)
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundNum is empty or <= 0");

        String key = "refundStampApply" + request.getRefundApplyId();
        try {
            if (SynchronizedLock.isOnDoingMemCached(key))
                throw new StampBizException(StampErrorCodes.E_REQ_1002, "请求重复");

            StampRefundOrderEntity entity = this.refundOrderRepository.getByRefundApplyId(request.getRefundApplyId());
            if (entity != null)
                throw new StampBizException(StampErrorCodes.E_REQ_1002, "请求重复");

            existsRefundingOrder(request.getOrderId());

            StampRefundResponse resp = refundApplyBack(request.getRefundApplyId(), null, request.getOrderId(),
                    request.getUserId(), request.getRefundNum());

            return resp;
        } finally {
            SynchronizedLock.releaseMemCached(key);
        }

    }

    /**
     * 前台发起申请时判断后台是否有未完成的退款
     */
    private void existsRefundingOrder(String orderId) {

        List<StampRefundOrderEntity> list = this.refundOrderRepository.getByOrderId(orderId);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (StampRefundOrderEntity refund : list) {
            if (!StringUtils.equals(refund.getRefundStatus(), StampStatus.REFUNDED.name())) {
                throw new StampBizException(StampErrorCodes.E_REFUND_5001, "存在退款中的退款单，订单号：" + orderId);
            }
        }
    }
    /*
     * private StampRefundResponse refundApply(String applyId, String orderId, String userId, Integer refundNum) {
     * 
     * String id = UUID.randomUUID().toString(); Timestamp date = new Timestamp(System.currentTimeMillis());
     * StampRefundOrderEntity refundOrder = new StampRefundOrderEntity(); refundOrder.setId(id);
     * refundOrder.setRefundApplyId(applyId); refundOrder.setOrderId(orderId); refundOrder.setUserId(userId);
     * refundOrder.setRefundNum(refundNum); refundOrder.setRefundStatus(StampStatus.REFUNDING.toString());
     * refundOrder.setCreateDate(date); refundOrder.setUpdateDate(date);
     * 
     * List<StampEntity> stampCodes = stampRepository.getByOrderIdAndStampStatus(orderId, StampStatus.UNUSE.name());
     * 
     * if (CollectionUtils.isEmpty(stampCodes)) throw new StampBizException(StampErrorCodes.E_REFUND_002,
     * "no UNSUE stamps," + orderId);
     * 
     * if (stampCodes.size() < refundNum) throw new StampBizException(StampErrorCodes.E_REFUND_002,
     * "not enough stamps," + stampCodes.size() + "<" + refundNum);
     * 
     * if (StringUtils.isNotBlank(userId) && !StringUtils.equals(stampCodes.get(0).getCustomerId(), userId)) throw new
     * StampBizException(StampErrorCodes.E_REQ_001, "不是该用户的券：" + userId + "," + "orderId");
     * 
     * Integer price = stampCodes.get(0).getPrice(); long stampAmount = price == null ? 0 : price.longValue(); long
     * refundAmount = stampAmount * refundNum;
     * 
     * refundOrder.setStampAmount(stampAmount); refundOrder.setRefundAmount(refundAmount);
     * 
     * refundOrderRepository.save(refundOrder);
     * 
     * List<String> codes = refundStampCode(refundNum, id, stampCodes);
     * 
     * StampRefundResponse resp = new StampRefundResponse(); resp.setStampCodes(codes);
     * resp.setRefundAmount(refundAmount); return resp; }
     */
    /*
     * private List<String> refundStampCode(Integer refundNum, String refundOrderId, List<StampEntity> stampCodes) {
     * List<String> codes = Lists.newArrayList(); List<StampOperationLogEntity> logList = Lists.newArrayList(); String
     * remark = StampStatus.UNUSE + "->" + StampStatus.REFUNDING; for (StampEntity stamp : stampCodes) { if (refundNum
     * == 0) { break; } int row = stampRepository.compareAndSetStatusById(stamp.getId(), stamp.getStampStatus(),
     * StampStatus.REFUNDING.toString(), refundOrderId); if (row == 1) { refundNum--;
     * codes.add(stamp.getSerialNumber());
     * 
     * StampOperationLogEntity log = new StampOperationLogEntity(OperateType.REFUND_APPLY.name(), refundOrderId,
     * stamp.getSerialNumber(), remark); logList.add(log); } } if (refundNum > 0) { throw new
     * StampBizException(StampErrorCodes.E_REFUND_002, "not enough stamps, lack " + refundNum); }
     * 
     * if(codes.size() > 0)
     * preSaleInventoryUnitService.rollbackConsumeInventory(stampCodes.get(0).getStampDefinitionId(), codes.size());
     * 
     * operationLogService.addLogList(logList); return codes; }
     */

    @Override
    @Transactional("transactionManager")
    public StampRefundResponse refundStampApplyBack(String refundId, String orderId, Integer refundNum) {

        logger.info("log refundStampApply... ");
        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");
        if (StringUtils.isBlank(orderId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "orderId is empty");
        if (refundNum == null || refundNum.intValue() <= 0)
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundNum is empty or <= 0");

        String key = "refundStampApplyBack" + refundId;
        try {
            if (SynchronizedLock.isOnDoingMemCached(key)) {
                throw new StampBizException(StampErrorCodes.E_REQ_1002, "请求重复");
            }
            StampRefundOrderEntity entity = this.refundOrderRepository.getByRefundId(refundId);
            if (entity != null)
                throw new StampBizException(StampErrorCodes.E_REQ_1002, "请求重复");
            return refundApplyBack(null, refundId, orderId, null, refundNum);
        } finally {
            SynchronizedLock.releaseMemCached(key);
        }
    }

    private StampRefundResponse refundApplyBack(String refundApplyId, String refundId, String orderId, String userId,
            Integer refundNum) {

        String id = UUID.randomUUID().toString();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        StampRefundOrderEntity refundOrder = new StampRefundOrderEntity();
        refundOrder.setId(id);
        if (StringUtils.isNotBlank(refundApplyId))
            refundOrder.setRefundApplyId(refundApplyId);
        if (StringUtils.isNotBlank(refundId))
            refundOrder.setRefundId(refundId);
        refundOrder.setOrderId(orderId);
        if (StringUtils.isNotBlank(userId))
            refundOrder.setUserId(userId);
        refundOrder.setRefundNum(refundNum);
        refundOrder.setRefundStatus(StampStatus.REFUNDING.toString());
        refundOrder.setCreateDate(date);
        refundOrder.setUpdateDate(date);

        List<StampEntity> canRefundList = Lists.newArrayList();
        List<StampEntity> unuseStampCodes = this.stampRepository.getByOrderIdAndStampStatus(orderId,
                StampStatus.UNUSE.name());
        List<StampEntity> paidInvalidStampCodes = this.stampRepository.getByOrderIdAndStampStatus(orderId,
                StampStatus.PAID_INVALID.name());

        if (CollectionUtils.isNotEmpty(unuseStampCodes))
            canRefundList.addAll(unuseStampCodes);
        if (CollectionUtils.isNotEmpty(paidInvalidStampCodes))
            canRefundList.addAll(paidInvalidStampCodes);

        if (CollectionUtils.isEmpty(canRefundList))
            throw new StampBizException(StampErrorCodes.E_REFUND_5002, "no UNSUE or PAID_INVALID stamps," + orderId);

        if (canRefundList.size() < refundNum)
            throw new StampBizException(StampErrorCodes.E_REFUND_5002,
                    "not enough stamps," + canRefundList.size() + "<" + refundNum);

        if (StringUtils.isNotBlank(userId) && !StringUtils.equals(canRefundList.get(0).getCustomerId(), userId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "不是该用户的券：" + userId + "," + "orderId");

        Integer price = canRefundList.get(0).getPrice();
        long stampAmount = price == null ? 0 : price.longValue();
        long refundAmount = stampAmount * refundNum;

        refundOrder.setStampAmount(stampAmount);
        refundOrder.setRefundAmount(refundAmount);

        this.refundOrderRepository.save(refundOrder);

        List<String> codes = refundStampCodeBack(refundNum, id, paidInvalidStampCodes, unuseStampCodes);

        StampRefundResponse resp = new StampRefundResponse();
        resp.setStampCodes(codes);
        resp.setRefundAmount(refundAmount);
        return resp;
    }

    /**
     * 修改券状态并回滚券库存（回滚库存只针对未使用的券，已作废的券库存已回滚）
     */
    private List<String> refundStampCodeBack(Integer refundNum, String refundOrderId,
            List<StampEntity> paidInvalidStampCodes, List<StampEntity> unuseStampCodes) {
        List<String> codes = Lists.newArrayList();
        List<StampOperationLogEntity> logList = Lists.newArrayList();

        // 退款时优先退（已付作废）的券
        if (CollectionUtils.isNotEmpty(paidInvalidStampCodes)) {
            String remark = StampStatus.PAID_INVALID + "->" + StampStatus.REFUNDING;
            for (StampEntity stamp : paidInvalidStampCodes) {
                if (refundNum == 0) {
                    break;
                }
                int row = this.stampRepository.compareAndSetStatusById(stamp.getId(), StampStatus.PAID_INVALID.name(),
                        StampStatus.REFUNDING.name(), refundOrderId);
                if (row == 1) {
                    refundNum--;
                    codes.add(stamp.getSerialNumber());

                    StampOperationLogEntity log = new StampOperationLogEntity(OperateType.REFUND_APPLY.name(),
                            refundOrderId, stamp.getSerialNumber(), remark);
                    logList.add(log);
                }
            }
        }

        // 退（未使用）的券
        if (refundNum > 0 && CollectionUtils.isNotEmpty(unuseStampCodes)) {
            String remark = StampStatus.UNUSE + "->" + StampStatus.REFUNDING;
            int updateRow = 0;
            for (StampEntity stamp : unuseStampCodes) {
                if (refundNum == 0) {
                    break;
                }
                int row = this.stampRepository.compareAndSetStatusById(stamp.getId(), StampStatus.UNUSE.name(),
                        StampStatus.REFUNDING.name(), refundOrderId);
                if (row == 1) {
                    refundNum--;
                    updateRow++;
                    codes.add(stamp.getSerialNumber());

                    StampOperationLogEntity log = new StampOperationLogEntity(OperateType.REFUND_APPLY.name(),
                            refundOrderId, stamp.getSerialNumber(), remark);
                    logList.add(log);
                }
            }

            if (updateRow > 0)
                this.preSaleInventoryUnitService.rollbackConsumeInventory(unuseStampCodes.get(0).getStampDefinitionId(),
                        updateRow);
        }
        if (refundNum > 0) {
            throw new StampBizException(StampErrorCodes.E_REFUND_5002, "not enough stamps, lack " + refundNum);
        }

        this.operationLogService.addLogList(logList);
        return codes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lvmama.vst.hhs.stamp.service.StampRefundService#notifyRefundId(java. lang.String, java.lang.String)
     */
    @Override
    @Transactional("transactionManager")
    public void notifyRefundId(String refundApplyId, String refundId) {

        if (StringUtils.isBlank(refundApplyId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundApplyId is empty");
        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");

        StampRefundOrderEntity entity = this.refundOrderRepository.getByRefundApplyId(refundApplyId);
        if (entity == null)
            throw new StampBizException(StampErrorCodes.E_ENTITY_2001,
                    "StampRefundOrderEntity not found, refundApplyId=" + refundApplyId);

        entity.setRefundId(refundId);
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        this.refundOrderRepository.save(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lvmama.vst.hhs.stamp.service.StampRefundService#notifyRefundResult( java.lang.String,
     * java.lang.String)
     */
    @Override
    @Transactional("transactionManager")
    public void notifyRefundResult(String refundId, String refundStatus) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");
        if (StringUtils.isBlank(refundStatus))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundStatus is empty");

        StampRefundOrderEntity refundOrder = this.refundOrderRepository.getByRefundId(refundId);
        if (refundOrder == null)
            throw new StampBizException(StampErrorCodes.E_ENTITY_2001,
                    "StampRefundOrderEntity not found, refundId=" + refundId);

        StampStatus status = null;
        if (StringUtils.equalsIgnoreCase(refundStatus, "REFUNDED")) {
            status = StampStatus.REFUNDED;
        } else if (StringUtils.equalsIgnoreCase(refundStatus, "FAIL")) {
            status = StampStatus.REFUND_FAIL;
        } else {
            logger.info("ignore notify {}, {}", refundId, refundStatus);
            return;
        }

        if (status.name().equals(refundOrder.getRefundStatus())) {
            logger.info("already notified {}, {}", refundId, refundStatus);
            return;
        }

        List<StampOperationLogEntity> logList = Lists.newArrayList();

        // if (!StringUtils.equals(refundOrder.getRefundStatus(), StampStatus.REFUNDING.toString())) {
        // throw new RuntimeException("refund status wrong, " + refundOrder.getRefundStatus());
        // }

        String oldStatus = refundOrder.getRefundStatus();
        refundOrder.setRefundStatus(refundStatus);
        refundOrder.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        this.refundOrderRepository.save(refundOrder);

        int row = this.stampRepository.compareAndSetStatusByObjectId(refundOrder.getId(), oldStatus, status.name());
        if (!refundOrder.getRefundNum().equals(row)) {
            logger.error("notify refund result error, stamp status wrong");
            throw new StampBizException(StampErrorCodes.E_STAMP_3001,
                    oldStatus + "->" + status + " error, refundId=" + refundId);
        }

        // operation log
        List<StampEntity> stamps = this.stampRepository.getByRefundOrderId(refundOrder.getId());
        OperateType type = OperateType.REFUND_FAIL;
        if (status == StampStatus.REFUNDED) {
            type = OperateType.REFUND_SUCCESS;
        }
        String remark = oldStatus + "->" + status;
        for (StampEntity stamp : stamps) {

            StampOperationLogEntity log = new StampOperationLogEntity(type.name(), refundOrder.getId(),
                    stamp.getSerialNumber(), remark);
            logList.add(log);
        }

        this.operationLogService.addLogList(logList);
    }

    @Override
    @Transactional("slaveTransactionManager")
    public StampRefundOrder getByRefundApplyId(String refundApplyId) {

        StampRefundOrderEntity refundOrder = this.refundOrderRepositorySlave.getByRefundApplyId(refundApplyId);
        if (refundOrder == null) {
            return null;
        }
        StampRefundOrder result = new StampRefundOrder();
        BeanUtils.copyProperties(refundOrder, result);

        List<StampEntity> stampList = this.stampRepositorySlave.getByRefundOrderId(refundOrder.getId());
        List<String> stampCodes = Lists.newArrayList();
        if (CollectionUtils.isEmpty(stampList)) {
            throw new RuntimeException("can not find StampEntity, refundId=" + refundOrder.getId());
        }
        for (StampEntity stamp : stampList) {
            stampCodes.add(stamp.getSerialNumber());
        }
        result.setStampCodes(stampCodes);

        StampDefinitionEntity stampDefinition = this.stampDefinitionRepositorySlave
                .getById(stampList.get(0).getStampDefinitionId());
        if (stampDefinition == null) {
            throw new RuntimeException("can not find stampDefinition, id=" + stampList.get(0).getStampDefinitionId());
        }
        stampDefinition.getProductBinding().getProductId();
        result.setStampId(stampDefinition.getId());
        result.setStampName(stampDefinition.getName());
        result.setProductId(stampDefinition.getProductBinding().getProductId());
        result.setProductName(stampDefinition.getProductBinding().getProductName());
        return result;
    }

    @Override
    @Transactional("transactionManager")
    public void unbindStamp(String refundId, List<String> stampCodes) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");
        if (CollectionUtils.isEmpty(stampCodes))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "stampCodes is empty");

        List<StampEntity> stampList = this.stampRepository.getByStampCodes(stampCodes);

        if (stampList == null || stampList.size() != stampCodes.size())
            throw new StampBizException(StampErrorCodes.E_REQ_1001,
                    "stampCodes is error" + JSON.toJSONString(stampCodes));

        Map<String, List<StampEntity>> stampMap = Maps.newHashMap();
        for (StampEntity stamp : stampList) {
            if (stampMap.containsKey(stamp.getOrderId())) {
                stampMap.get(stamp.getOrderId()).add(stamp);
            } else {
                List<StampEntity> stamps = Lists.newArrayList();
                stamps.add(stamp);
                stampMap.put(stamp.getOrderId(), stamps);
            }
        }

        List<StampOperationLogEntity> logList = null;

        for (Map.Entry<String, List<StampEntity>> entry : stampMap.entrySet()) {

            OrdOrder order = this.orderService.queryOrdorderByOrderId(Long.valueOf(entry.getKey()));

            List<StampEntity> list = entry.getValue();
            // 券订单已取消，则券直接改为StampStatus.PAID_INVALID, 并退库存
            StampStatus newStatus = null;
            if (order.isCancel()) {
                newStatus = StampStatus.PAID_INVALID;
                this.preSaleInventoryUnitService.rollbackConsumeInventory(list.get(0).getStampDefinitionId(),
                        list.size());
            } else {
                newStatus = StampStatus.UNUSE;
            }
            logList = unbindStamp(refundId, list, newStatus);
        }

        // String remark = StampStatus.USED + "->" + StampStatus.UNUSE;
        // Timestamp date = new Timestamp(System.currentTimeMillis());

        // List<StampOperationLogEntity> logList = Lists.newArrayList();
        //
        // for(StampEntity stamp : stampList) {
        //
        // StampUnbindOrderEntity unbindEntity = new StampUnbindOrderEntity();
        // String id = UUID.randomUUID().toString();
        // unbindEntity.setId(id);
        // unbindEntity.setRefundId(refundId);
        // unbindEntity.setStampCode(stamp.getSerialNumber());
        // unbindEntity.setUseOrderId(stamp.getUseOrderId());
        // unbindEntity.setUseOrderItemId(stamp.getUseOrderItemId());
        // unbindEntity.setCreateDate(date);
        // refundUnbindRepository.save(unbindEntity);
        //
        // int row = stampRepository.unbindStamp(stamp.getId(), StampStatus.USED.toString(),
        // StampStatus.UNUSE.toString());
        // if (row != 1) {
        // throw new RuntimeException("unbind error: " + stamp.getSerialNumber());
        // }
        //
        // StampOperationLogEntity log = new StampOperationLogEntity(OperateType.UNBIND.name(),
        // id, stamp.getSerialNumber(), remark);
        // logList.add(log);
        //
        // }

        // StampRefundOrderEntity refundOrder = new StampRefundOrderEntity();
        // String id = UUID.randomUUID().toString();
        // Timestamp date = new Timestamp(System.currentTimeMillis());
        // refundOrder.setId(id);
        // refundOrder.setRefundId(refundId);
        // refundOrder.setOrderId(orderId);
        // refundOrder.setRefundNum(stampCodes.size());
        // refundOrder.setRefundStatus(StampStatus.REFUNDED.toString());
        // refundOrder.setCreateDate(date);
        // refundOrder.setUpdateDate(date);
        // refundOrderRepository.save(refundOrder);
        //
        // for (String stampCode : stampCodes) {
        //
        // int row = stampRepository.unbindStamp(stampCode, StampStatus.USED.toString(), StampStatus.UNUSE.toString(),
        // id);
        // if (row != 1) {
        // throw new RuntimeException("unbind error: " + stampCode);
        // }
        //
        // operationLogService.addLog(OperateType.UNBIND, id, stampCode, StampStatus.USED + "->" + StampStatus.UNUSE);
        // }

        this.operationLogService.addLogList(logList);
    }

    private List<StampOperationLogEntity> unbindStamp(String refundId, List<StampEntity> list, StampStatus newStatus) {

        List<StampOperationLogEntity> logList = Lists.newArrayList();
        String remark = StampStatus.USED + "->" + newStatus;
        Timestamp date = new Timestamp(System.currentTimeMillis());

        for (StampEntity stamp : list) {

            StampUnbindOrderEntity unbindEntity = new StampUnbindOrderEntity();
            String id = UUID.randomUUID().toString();
            unbindEntity.setId(id);
            unbindEntity.setRefundId(refundId);
            unbindEntity.setStampCode(stamp.getSerialNumber());
            unbindEntity.setUseOrderId(stamp.getUseOrderId());
            unbindEntity.setUseOrderItemId(stamp.getUseOrderItemId());
            unbindEntity.setCreateDate(date);
            this.refundUnbindRepository.save(unbindEntity);

            int row = this.stampRepository.unbindStamp(stamp.getId(), StampStatus.USED.name(), newStatus.name());
            if (row != 1)
                throw new StampBizException(StampErrorCodes.E_STAMP_3001,
                        "USED->" + newStatus + " error" + JSON.toJSONString(stamp.getSerialNumber()));

            StampOperationLogEntity log = new StampOperationLogEntity(OperateType.UNBIND.name(), id,
                    stamp.getSerialNumber(), remark);
            logList.add(log);
        }

        return logList;
    }

    @Override
    @Transactional("transactionManager")
    public void unbindAndRefundStamp(String refundId, List<String> stampCodes) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");
        if (CollectionUtils.isEmpty(stampCodes))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "stampCodes is empty");

        List<StampOperationLogEntity> logList = Lists.newArrayList();

        List<StampEntity> stampList = this.stampRepository.getByStampCodes(stampCodes);

        if (stampList == null || stampList.size() != stampCodes.size())
            throw new StampBizException(StampErrorCodes.E_REQ_1001,
                    "stampCodes is error" + JSON.toJSONString(stampCodes));

        String remark = StampStatus.USED + "->" + StampStatus.UNUSE_LOCK;
        Timestamp date = new Timestamp(System.currentTimeMillis());

        Map<String, List<StampEntity>> orderStampMap = Maps.newHashMap();

        for (StampEntity stamp : stampList) {

            if (!orderStampMap.containsKey(stamp.getOrderId())) {
                List<StampEntity> list = Lists.newArrayList();
                list.add(stamp);
                orderStampMap.put(stamp.getOrderId(), list);
            } else {
                orderStampMap.get(stamp.getOrderId()).add(stamp);
            }

            StampUnbindOrderEntity unbindEntity = new StampUnbindOrderEntity();
            String id = UUID.randomUUID().toString();
            unbindEntity.setId(id);
            unbindEntity.setRefundId(refundId);
            unbindEntity.setStampCode(stamp.getSerialNumber());
            unbindEntity.setUseOrderId(stamp.getUseOrderId());
            unbindEntity.setUseOrderItemId(stamp.getUseOrderItemId());
            unbindEntity.setCreateDate(date);
            this.refundUnbindRepository.save(unbindEntity);

            int row = this.stampRepository.unbindStamp(stamp.getId(), StampStatus.USED.toString(),
                    StampStatus.UNUSE_LOCK.toString());
            if (row != 1)
                throw new StampBizException(StampErrorCodes.E_STAMP_3001,
                        "USED->UNUSE_LOCK error" + JSON.toJSONString(stamp.getSerialNumber()));

            StampOperationLogEntity log = new StampOperationLogEntity(OperateType.UNBIND_AND_REFUND.name(), id,
                    stamp.getSerialNumber(), remark);
            logList.add(log);
        }

        // StampRefundOrderEntity refundOrder = new StampRefundOrderEntity();
        // String refundOrderId = UUID.randomUUID().toString();
        // refundOrder.setId(refundOrderId);
        // refundOrder.setRefundId(refundId);
        //// refundOrder.setOrderId(orderId); // 多个orderId
        // refundOrder.setRefundNum(stampCodes.size());
        // refundOrder.setRefundStatus(StampStatus.REFUNDING.toString());
        // refundOrder.setCreateDate(date);
        // refundOrder.setUpdateDate(date);

        // 券兑换订单直接退款，可能会退多种券，也就是多个券订单，分成两步
        /*
         * String refundRemark = StampStatus.UNUSE + "->" + StampStatus.REFUNDING; for(Map.Entry<String,
         * List<StampEntity>> entry : orderStampMap.entrySet()) {
         * 
         * StampRefundOrderEntity refundOrderEntity = new StampRefundOrderEntity(); String id =
         * UUID.randomUUID().toString(); refundOrderEntity.setId(id); refundOrderEntity.setRefundId(refundId);
         * refundOrderEntity.setOrderId(entry.getKey()); refundOrderEntity.setRefundNum(stampCodes.size());
         * refundOrderEntity.setRefundStatus(StampStatus.REFUNDING.toString()); refundOrderEntity.setCreateDate(date);
         * refundOrderEntity.setUpdateDate(date);
         * 
         * // OrdOrder order = orderService.queryOrdorderByOrderId(Long.valueOf(entry.getKey())); // if (order ==
         * null) { // throw new RuntimeException("order not found"); // } // if
         * (CollectionUtils.isEmpty(order.getOrderItemList())) { // throw new RuntimeException("order item not found "
         * ); // } // // long stampAmount = order.getOrderItemList().get(0).getPrice(); Integer price =
         * entry.getValue().get(0).getPrice(); long stampAmount = price == null ? 0 : price.longValue(); long
         * refundAmount = stampAmount * entry.getValue().size(); refundOrderEntity.setStampAmount(stampAmount);
         * refundOrderEntity.setRefundAmount(refundAmount);
         * 
         * refundOrderRepository.save(refundOrderEntity);
         * 
         * for(StampEntity stamp : entry.getValue()) {
         * 
         * int row = stampRepository.compareAndSetStatusById(stamp.getId(), StampStatus.UNUSE.toString(),
         * StampStatus.REFUNDING.toString(), id); if (row != 1) { throw new RuntimeException("refund stamp error " +
         * JSON.toJSONString(stamp)); }
         * 
         * StampOperationLogEntity log = new StampOperationLogEntity(); log.setId(UUID.randomUUID().toString());
         * log.setOperateType(OperateType.REFUND_APPLY.toString()); log.setObjectId(id);
         * log.setStampCode(stamp.getSerialNumber()); log.setRemark(refundRemark); log.setCreateDate(date);
         * logList.add(log); // operationLogService.addLog(OperateType.REFUND_APPLY, id, stamp.getSerialNumber(), //
         * refundRemark); } }
         */
        // OrdOrder order =
        // orderService.queryOrdorderByOrderId(Long.valueOf(orderId));
        // if (order == null) {
        // throw new RuntimeException("order not found");
        // }
        // if(CollectionUtils.isEmpty(order.getOrderItemList())) {
        // throw new RuntimeException("order item not found ");
        // }
        // long stampAmount = order.getOrderItemList().get(0).getPrice();
        // long refundAmount = stampAmount*request.getRefundNum();
        //
        // refundOrder.setStampAmount(stampAmount);
        // refundOrder.setRefundAmount(refundAmount);

        // refundOrderRepository.save(refundOrder);

        // for (String stampCode : stampCodes) {
        //
        // stampRepository.compareAndSetStatusById(id, StampStatus.UNUSE.toString(), StampStatus.REFUNDING.toString(),
        // objectId)
        //
        // int row = stampRepository.unbindStamp(stampCode, StampStatus.USED.toString(),
        // StampStatus.REFUNDING.toString(), id);
        // if (row != 1) {
        // throw new RuntimeException("unbindAndRefund error " + stampCode);
        // }
        //
        // operationLogService.addLog(OperateType.UNBIND_AND_REFUND, refundOrder.getId(), stampCode,
        // StampStatus.REFUNDING + "->" + StampStatus.REFUNDING);
        // }

        this.operationLogService.addLogList(logList);
    }

    @Override
    @Transactional("transactionManager")
    public void refundStampApplyBackByCode(String refundId, String orderId, List<String> stampCodes) {

        if (StringUtils.isBlank(refundId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "refundId is empty");
        if (StringUtils.isBlank(orderId))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "orderId is empty");
        if (CollectionUtils.isEmpty(stampCodes))
            throw new StampBizException(StampErrorCodes.E_REQ_1001, "stampCodes is empty");

        List<StampEntity> stampList = this.stampRepository.getByStampCodes(stampCodes);

        if (stampList == null || stampList.size() != stampCodes.size())
            throw new StampBizException(StampErrorCodes.E_REQ_1001,
                    "stampCodes is error" + JSON.toJSONString(stampCodes));

        Timestamp date = new Timestamp(System.currentTimeMillis());

        StampRefundOrderEntity refundOrder = new StampRefundOrderEntity();
        String id = UUID.randomUUID().toString();
        refundOrder.setId(id);
        refundOrder.setRefundId(refundId);
        refundOrder.setOrderId(orderId);
        refundOrder.setRefundNum(stampCodes.size());
        refundOrder.setRefundStatus(StampStatus.REFUNDING.toString());
        refundOrder.setCreateDate(date);
        refundOrder.setUpdateDate(date);

        Integer price = stampList.get(0).getPrice();
        long stampAmount = price == null ? 0 : price.longValue();
        long refundAmount = stampAmount * stampCodes.size();

        refundOrder.setStampAmount(stampAmount);
        refundOrder.setRefundAmount(refundAmount);

        this.refundOrderRepository.save(refundOrder);

        String remark = StampStatus.UNUSE_LOCK + "->" + StampStatus.REFUNDING;
        List<StampOperationLogEntity> logList = Lists.newArrayList();
        for (StampEntity stamp : stampList) {
            int row = this.stampRepository.compareAndSetStatusById(stamp.getId(), StampStatus.UNUSE_LOCK.name(),
                    StampStatus.REFUNDING.name(), id);
            if (row != 1)
                throw new StampBizException(StampErrorCodes.E_STAMP_3001,
                        "UNUSE_LOCK->REFUNDING error" + JSON.toJSONString(stamp.getSerialNumber()));

            StampOperationLogEntity log = new StampOperationLogEntity(OperateType.REFUND_APPLY.name(), id,
                    stamp.getSerialNumber(), remark);
            logList.add(log);
        }
        
        // 退换库存
        this.preSaleInventoryUnitService.rollbackConsumeInventory(stampList.get(0).getStampDefinitionId(),
                stampList.size());

        this.operationLogService.addLogList(logList);

    }

    @Override
    @Transactional("transactionManager")
    public void cancelStampRefund(String refundId) {

        StampRefundOrderEntity refundOrder = this.refundOrderRepository.getByRefundId(refundId);
        if (refundOrder == null)
            throw new StampBizException(StampErrorCodes.E_ENTITY_2001,
                    "StampRefundOrderEntity not found, refundId=" + refundId);

        if (RefundStatus.CANCEL.name().equals(refundOrder.getRefundStatus()))
            throw new StampBizException(StampErrorCodes.E_REQ_1002, "退款已废弃");

        if (RefundStatus.REFUNDED.name().equals(refundOrder.getRefundStatus()))
            throw new StampBizException(StampErrorCodes.E_REFUND_5003, "退款已完成，不能废弃");

        String oldStatus = refundOrder.getRefundStatus();
        // 1. 退款单
        refundOrder.setRefundStatus(RefundStatus.CANCEL.name());
        refundOrder.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        this.refundOrderRepository.save(refundOrder);

        // 2. 库存
        OrdOrder order = this.orderService.queryOrdorderByOrderId(Long.valueOf(refundOrder.getOrderId()));

        List<StampEntity> stamps = this.stampRepository.getByRefundOrderId(refundOrder.getId());
        StampStatus newStatus = null;
        // 券订单已取消，则券直接改为StampStatus.PAID_INVALID
        if (order.isCancel()) { // TODO 退回这个状态没有意义吧？
            newStatus = StampStatus.PAID_INVALID;
        } else { // 退款申请成功会退回库存，这里需要重新扣回来
            newStatus = StampStatus.UNUSE;
            this.preSaleInventoryUnitService.consumeInventory(stamps.get(0).getStampDefinitionId(), stamps.size());
        }

        // 3. 券状态
        String remark = refundOrder.getRefundStatus() + "->" + newStatus;
        int row = this.stampRepository.compareAndSetStatusByObjectId(refundOrder.getId(), oldStatus, newStatus.name());
        if (row != stamps.size()) {
            throw new StampBizException(StampErrorCodes.E_STAMP_3001, remark + " cancel error, refundId=" + refundId);
        }

        for (StampEntity stamp : stamps) {
            this.operationLogService.addLog(OperateType.REFUND_CANCEL, refundOrder.getId(), stamp.getSerialNumber(),
                    remark);
        }

    }

    @Override
    @Transactional("transactionManager")
    public void cancelStampUnbind(String refundId) {

        List<StampUnbindOrderEntity> unbindList = this.refundUnbindRepository.getByRefundId(refundId);
        if (CollectionUtils.isEmpty(unbindList))
            throw new StampBizException(StampErrorCodes.E_ENTITY_2001,
                    "StampUnbindOrderEntity not found, refundId=" + refundId);

        List<StampOperationLogEntity> logList = Lists.newArrayList();
        for (StampUnbindOrderEntity unbind : unbindList) {
            StampEntity stamp = this.stampRepository.getBySerialNumber(unbind.getStampCode());

            if (!StampStatus.UNUSE.name().equals(stamp.getStampStatus())
                    && !StampStatus.PAID_INVALID.name().equals(stamp.getStampStatus())) {
                logger.error("cancel stamp unbind failed, refundId={}, stamp={}, status={}", refundId,
                        stamp.getSerialNumber(), stamp.getStampStatus());
                throw new StampBizException(StampErrorCodes.E_REFUND_5004, "券状态不对，无法解绑废弃" + refundId);
            }

            int row = this.stampRepository.compareAndSetStatusBySerialNumber(stamp.getSerialNumber(),
                    stamp.getStampStatus(), StampStatus.USED.name());
            if (row != 1)
                logger.warn("warning! compareAndSetStatusBySerialNumber failed. {}", stamp.getSerialNumber());
            StampOperationLogEntity log = new StampOperationLogEntity(OperateType.UNBIND_CANCEL.name(), refundId,
                    stamp.getSerialNumber(), stamp.getStampStatus() + "->" + StampStatus.USED);
            logList.add(log);
        }

        this.operationLogService.addLogList(logList);
    }

    @Override
    public void cancelOrderAfterRefund(final String orderId) {

        if (StringUtils.isBlank(orderId)) {
            logger.error("cancelOrderAfterRefund: orderId is null");
            return;
        }

        thread_pool.submit(new Runnable() {

            @Override
            public void run() {

                List<StampEntity> list = stampRepository.getByOrderId(orderId);
                if (CollectionUtils.isEmpty(list))
                    return;

                for (StampEntity s : list) {
                    if (!Constant.StampStatus.REFUNDING.name().equals(s.getStampStatus())
                            && !Constant.StampStatus.REFUND_FAIL.name().equals(s.getStampStatus())
                            && !Constant.StampStatus.REFUNDED.name().equals(s.getStampStatus()))
                        return;
                }

                logger.info("cancelOrderAfterRefund: orderId={}", orderId);

                Long orderIdL = Long.valueOf(orderId);

                try {
                    OrdOrder order = orderService.queryOrdorderByOrderId(orderIdL);
                    if (order.hasCanceled()) {
                        logger.info("cancelOrderAfterRefund: order[{}] already cancel.", orderId);
                        return;
                    }

                    // dubbo调原来的订单取消接口
                    ResultHandle rl = orderService.cancelOrder(orderIdL, ORDER_CANCEL_CODE, ORDER_CANCEL_REASON,
                            ORDER_CANCEL_OPERATOR_ID, null);
                    if (rl != null) {
                        logger.info("cancel order result: {},{}", orderId, JSON.toJSONString(rl));
                    }
                } catch (Exception e) { // 异常则重试一次
                    logger.error("cancel stamp order error: " + orderId, e);
                    try {
                        Thread.sleep(30000); // sleep 30 seconds
                    } catch (InterruptedException e2) {
                        // ignore
                    }
                    try {
                        ResultHandle rl = orderService.cancelOrder(orderIdL, ORDER_CANCEL_CODE, ORDER_CANCEL_REASON,
                                ORDER_CANCEL_OPERATOR_ID, null);
                        if (rl != null) {
                            logger.info("retry cancel order result: {},{}", orderId, JSON.toJSONString(rl));
                        }
                    } catch (Exception e1) {
                        logger.error("retry cancel stamp order error: " + orderId, e);
                    }
                }
            }
        });

    }

}
