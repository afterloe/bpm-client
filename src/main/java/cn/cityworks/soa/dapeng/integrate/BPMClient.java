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
}
