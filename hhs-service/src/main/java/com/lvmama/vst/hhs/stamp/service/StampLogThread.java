/**
 * 
 */
package com.lvmama.vst.hhs.stamp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Queues;
import com.lvmama.vst.hhs.stampDefinition.dao.StampOperationLogEntity;

/**
 * @author baolm
 *
 */
@Service
public class StampLogThread implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(StampLogThread.class);

    static Queue<StampOperationLogEntity> logQueue = Queues.newConcurrentLinkedQueue();

    private volatile boolean running = false;

    @Autowired
    private StampOperationLogService logService;

    private class ExecuteThread implements Runnable {

        @Override
        public void run() {

            logger.info("stamp log thread start up.");
            List<StampOperationLogEntity> logList = new ArrayList<>();

            while (running) {

                try {
                    Thread.sleep(60000 * 5); // sleep 5 min
                } catch (InterruptedException e) {
                    // ignore
                }

                // get log list
                for (;;) {

                    StampOperationLogEntity entity = logQueue.poll();

                    if (entity == null)
                        break;

                    logger.info("get a log: {}", JSON.toJSONString(entity));
                    logList.add(entity);

                    if (logList.size() >= 500) {
                        // batch save log
                        logService.batchInsert(logList);
                        // clear list
                        logList.clear();
                    }
                }

                if (logList.isEmpty()) {
                    logger.info("get no stamp log.");
                    continue;
                }

                // batch save log
                logService.batchInsert(logList);

                // clear list
                logList.clear();
            }

            logger.info("stamp log thread shut down.");
        }

    }

    @Override
    public void destroy() throws Exception {
        running = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        running = true;
        new Thread(new ExecuteThread()).start();
    }
}
