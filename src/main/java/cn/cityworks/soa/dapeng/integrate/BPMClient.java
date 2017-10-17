package cn.cityworks.soa.dapeng.integrate;

import cn.cityworks.soa.dapeng.integrate.fallback.BPMClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * create by afterloe on 2017/10/17
 */
@FeignClient(name = "${integrate.bpm-engine:bpm-server}", fallbackFactory = BPMClientFallbackFactory.class)
public interface BPMClient {
}
