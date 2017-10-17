package cn.cityworks.soa.dapeng.integrate.fallback;

import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.integrate.UserClient;
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
 * create by afterloe on 2017/10/17
 */
@Component
public class BPMClientFallbackFactory implements FallbackFactory<BPMClient>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BPMClientFallbackFactory.class);

    // 获取 断路器所用的 资源id
    @Value("${integrate.bpm-engine:bpm-server}")
    private String RESOURCE_ID;
    private BPMClient fallback;

    // 设置 统一的断路器返回信息
    private Map responseObjectDTO;

    @Override
    public BPMClient create(Throwable cause) {
        LOGGER.error("BPMClient find {}, by using {}", cause.getMessage(), RESOURCE_ID);

        if (null != fallback) {
            return fallback;
        }

        responseObjectDTO = new HashMap();
        Map result = new HashMap();
        result.put("totalElements", 0l);
        responseObjectDTO.put("data", result);
        responseObjectDTO.put("code", HttpResponseStatus.BAD_GATEWAY.code());
        responseObjectDTO.put("msg", "UserClient is not available");

        return fallback = new BPMClient() {

            @Override
            public Map getStartFormData(String processId) {
                LOGGER.error("getStartFormData({}) invoke fail", processId);
                return null;
            }
        };
    }
}
