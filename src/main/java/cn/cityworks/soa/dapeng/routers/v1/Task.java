package cn.cityworks.soa.dapeng.routers.v1;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/18
 */
@RestController
@RequestMapping("/v1/task")
public class Task implements Serializable {

    @Autowired
    private TaskService taskService;

    /**
     * 获取我的任务
     *
     * @param access_token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseDTO listTask(@RequestHeader("access-token") String access_token
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = taskService.listTask(access_token, page, number);
        return ResponseDTO.build(data);
    }

    /**
     * 可接的任务数
     *
     * @param access_token
     * @return
     */
    @RequestMapping(value = "canSign", method = RequestMethod.GET)
    public ResponseDTO countByCanSignTask(@RequestHeader("access-token") String access_token) {
        Object data = taskService.countByCanSignTask(access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 按照组获取任务列表
     *
     * @param access_token
     * @param groupId
     * @return
     */
    @RequestMapping(value = "canSign/group", method = RequestMethod.GET)
    public ResponseDTO listCanSignTaskByGroups(@RequestHeader("access-token") String access_token
            , @RequestParam(value = "groupId", required = false) String groupId
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = taskService.listCanSignTaskByGroup(access_token, groupId, page, number);
        return ResponseDTO.build(data);
    }
}
