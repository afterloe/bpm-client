package cn.cityworks.soa.dapeng.routers.v1;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

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
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseDTO getSuperviseMatterFromData() {
        Object data = superviseMatterService.getSuperviseMatterFromData();
        return ResponseDTO.build(data);
    }
}
