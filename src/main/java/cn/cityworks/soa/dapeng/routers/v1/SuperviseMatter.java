package cn.cityworks.soa.dapeng.routers.v1;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

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
    @RequestMapping(value = "formData", method = RequestMethod.GET)
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
    public ResponseDTO saveSuperviseMatter(@RequestParam Map<String, String> taskForm
            , @RequestHeader("access-token") String access_token) {
        Object data = superviseMatterService.saveSuperviseMatter(access_token, taskForm);
        return ResponseDTO.build(data);
    }

    /**
     * 获取督办事项列表
     *
     * @param access_token
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = {"list"}, method = RequestMethod.GET)
    public ResponseDTO listSuperviseMatter(@RequestHeader("access-token") String access_token
            , @RequestParam(value = "action", required = false, defaultValue = "enable") String action
            , @RequestParam(value = "value", required = false, defaultValue = "true") Boolean value
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = superviseMatterService.listSuperviseMatter(access_token, action, value, page, number);
        return ResponseDTO.build(data);
    }

    /**
     * 获取督办事项详情
     *
     * @param access_token
     * @param superviseMatterId
     * @param superviseMatterId_Path
     * @return
     */
    @RequestMapping(value = {"/{superviseMatterId}", "/"}, method = RequestMethod.GET)
    public ResponseDTO getSuperviseMatter(@RequestHeader("access-token") String access_token
            , @PathVariable(value = "superviseMatterId", required = false) String superviseMatterId_Path
            , @RequestParam(value = "superviseMatterId", required = false) String superviseMatterId) {
        Object data = superviseMatterService.getSuperviseMatter(access_token, Optional
                .ofNullable(superviseMatterId_Path).orElse(superviseMatterId));
        return ResponseDTO.build(data);
    }

    /**
     * 确认督办事项
     *
     * @param access_token
     * @param variables
     * @return
     */
    @RequestMapping(value = {"confirm"}, method = RequestMethod.PUT)
    public ResponseDTO confirmSuperviseMatter(@RequestHeader("access-token") String access_token
            , @RequestParam Map<String, String> variables) {
        Object data = superviseMatterService.confirm(access_token, variables);
        return ResponseDTO.build(data);
    }

    /**
     * 选择反馈方式
     *
     * @param access_token
     * @return
     */
    @RequestMapping(value = {"feedbackMode/{superviseMatterId}"}, method = RequestMethod.PUT)
    public ResponseDTO choiceFeedbackMode(@RequestHeader("access-token") String access_token
            , @PathVariable(value = "superviseMatterId", required = false) String superviseMatterId
            , @RequestParam(value = "action", required = false, defaultValue = "0") Integer action) {
        Object data = superviseMatterService.choiceFeedbackMode(superviseMatterId,
                Optional.ofNullable(action).orElse(0), access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 回复督办事项
     *
     * @param access_token
     * @param variables
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDTO replySuperviseMatter(@RequestHeader("access-token") String access_token
            , @RequestParam Map<String, String> variables) {
        Object data = superviseMatterService.replySuperviseMatter(access_token, variables);
        return ResponseDTO.build(data);
    }

    /**
     * 同意督办回复
     *
     * @param access_token
     * @param superviseMatterId
     * @return
     */
    @RequestMapping(value = "approval/{superviseMatterId}", method = RequestMethod.PUT)
    public ResponseDTO approvalSuperviseMatter(@RequestHeader("access-token") String access_token
            , @PathVariable(value = "superviseMatterId", required = false) String superviseMatterId) {
        Object data = superviseMatterService.approvalSuperviseMatter(access_token, superviseMatterId);
        return ResponseDTO.build(data);
    }
}
