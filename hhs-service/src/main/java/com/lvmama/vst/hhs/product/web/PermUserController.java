package com.lvmama.vst.hhs.product.web;

import static com.lvmama.vst.hhs.common.web.ResponseEntityBuilder.ok;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;
import com.lvmama.vst.hhs.common.web.RestApiException;
import com.lvmama.vst.hhs.model.admin.ManagerVo;
import com.lvmama.vst.pet.adapter.PermUserServiceAdapter;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
@RequestMapping(HhsApiUriTemplates.V1 + "/admin/user/")
public class PermUserController {
    
    @Autowired
    private PermUserServiceAdapter permUserService;
    
    @ApiOperation("产品经理查询")
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/manager" },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ManagerVo>> getPermUser(
            @RequestParam("searchName") final String searchName) {

        try {
            List<PermUser> useres = this.permUserService.findPermUser(searchName);
            List<ManagerVo> result = new ArrayList<ManagerVo>();
            for(PermUser user:useres){
                ManagerVo manager = new ManagerVo();
                manager.setManagerId(user.getUserId());
                manager.setManagerName(user.getRealName());
                result.add(manager);
            }
            return ok(result);
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }

}
