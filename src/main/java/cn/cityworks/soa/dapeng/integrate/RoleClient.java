package cn.cityworks.soa.dapeng.integrate;

import cn.cityworks.soa.dapeng.integrate.fallback.RoleClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${integrate.role:role-store}", fallbackFactory = RoleClientFallbackFactory.class)
public interface RoleClient {

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    Map roles();
}
