package cn.cityworks.soa.dapeng.routers.v1;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
@RestController
@RequestMapping("/v1/superviseMatter")
public class SuperviseMatter implements Serializable {

    @Autowired
    private SuperviseMatterService superviseMatterService;

    /**
     * 获取督办事件 表单
     *
     * @param access_token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseDTO getSuperviseMatterFromData(@RequestHeader("access-token") String access_token) {
        Object data = superviseMatterService.getSuperviseMatterFromData(access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 上报督办事件
     *
     * @param taskForm
     * @param access_token
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDTO setSuperviseMatterFromData(@RequestParam Map<String, String> taskForm
            , @RequestHeader("access-token") String access_token) {
        Object data = superviseMatterService.setSuperviseMatterFromData(access_token, taskForm);
        return ResponseDTO.build(data);
    }
}
