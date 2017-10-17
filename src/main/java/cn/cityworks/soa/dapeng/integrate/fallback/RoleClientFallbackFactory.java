package cn.cityworks.soa.dapeng.integrate.fallback;

import cn.cityworks.soa.dapeng.integrate.RoleClient;
import feign.hystrix.FallbackFactory;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * create by afterloe on 2017/9/20
 */
@Component
public class RoleClientFallbackFactory implements FallbackFactory<RoleClient>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleClientFallbackFactory.class);

    // 获取 断路器所用的 资源id
    @Value("${summary.role:role-store}")
    private String RESOURCE_ID;
    private RoleClient fallback;

    // 设置 统一的断路器返回信息
    private Map responseObjectDTO;

    @Override
    public RoleClient create(Throwable cause) {
        LOGGER.error("RoleClient find {}, by using {}", cause.getMessage(), RESOURCE_ID);
        if (null != fallback) {
            return fallback;
        }

        responseObjectDTO = new HashMap();
        Map result = new HashMap();
        result.put("totalElements", 0l);
        responseObjectDTO.put("data", result);
        responseObjectDTO.put("code", HttpResponseStatus.BAD_GATEWAY.code());
        responseObjectDTO.put("msg", "RoleClient is not available");

        return fallback = new RoleClient() {
            @Override
            public Map roles() {
                LOGGER.error("roles() invoke fail");
                return responseObjectDTO;
            }
        };
    }
}
