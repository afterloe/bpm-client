package cn.cityworks.soa.dapeng.integrate;

import cn.cityworks.soa.dapeng.integrate.fallback.BPMClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
@FeignClient(name = "${integrate.bpm-engine:bpm-server}", fallbackFactory = BPMClientFallbackFactory.class)
public interface BPMClient {

    /**
     * 获取流程启动表单
     */
    @RequestMapping(value = "/v1/form/{processId}", method = RequestMethod.GET)
    Map getStartFormData(@PathVariable(value = "processId") String processId);

    /**
     * 启动流程
     *
     * @param startFormData
     * @return
     */
    @RequestMapping(value = "/v1/runtime", method = RequestMethod.POST)
    Map startProcess(@RequestParam Map startFormData);

    /**
     * 获取指定人的任务列表
     *
     * @param userId
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/v1/task/list/{userId}", method = RequestMethod.GET)
    Map listTaskByUserId(@PathVariable(value = "userId") String userId
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number);

    /**
     * 获取指定人的可签收的任务数量
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/v1/task/count/{userId}", method = RequestMethod.GET)
    Map countByCanSignTask(@PathVariable(value = "userId") String userId);

    /**
     * 获取指定组的可签收任务列表
     *
     * @param groupId
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/v1/task/group/{groupId}/list", method = RequestMethod.GET)
    Map listCanSignTaskByGroup(@PathVariable(value = "groupId") String groupId
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number);
}
