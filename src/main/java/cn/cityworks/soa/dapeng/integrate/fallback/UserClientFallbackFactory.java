package cn.cityworks.soa.dapeng.integrate.fallback;

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
 * create by afterloe on 2017/9/20
 */
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    // 获取 断路器所用的 资源id
    @Value("${integrate.user:user-maneger}")
    private String RESOURCE_ID;
    private UserClient fallback;

    // 设置 统一的断路器返回信息
    private Map responseObjectDTO;

    @Override
    public UserClient create(Throwable cause) {
        LOGGER.error("UserClient find {}, by using {}", cause.getMessage(), RESOURCE_ID);

        if (null != fallback) {
            return fallback;
        }

        responseObjectDTO = new HashMap();
        Map result = new HashMap();
        result.put("totalElements", 0l);
        responseObjectDTO.put("data", result);
        responseObjectDTO.put("code", HttpResponseStatus.BAD_GATEWAY.code());
        responseObjectDTO.put("msg", "UserClient is not available");

        return fallback = new UserClient() {

            @Override
            public Map getUserById(String userId) {
                LOGGER.error("getUserById({}) invoke fail", userId);
                return responseObjectDTO;
            }

            @Override
            public Map listUsers(int page, int number) {
                LOGGER.error("listUsers({}, {}) invoke fail", page, number);
                return responseObjectDTO;
            }

            @Override
            public Map listUsersByGroupId(String groupId, int page, int number) {
                LOGGER.error("listUsersByGroupId({}, {}, {}) invoke fail", groupId, page, number);
                return responseObjectDTO;
            }

            @Override
            public Map listUsersByUsername(String username, int page, int number) {
                LOGGER.error("listUsersByUsername({}, {}, {}) invoke fail", username, page, number);
                return responseObjectDTO;
            }

            @Override
            public Map listGroupsByName(String groupName, int page, int number) {
                LOGGER.error("groupsByName({}, {}, {}) invoke fail", groupName, page, number);
                return responseObjectDTO;
            }

            @Override
            public Map getGroups(String groupId) {
                LOGGER.error("getGroups({}) invoke fail", groupId);
                return responseObjectDTO;
            }

            @Override
            public Map listGroupsByUserId(String userId, int page, int number) {
                LOGGER.error("listGroupsByUserId({}, {}, {}) invoke fail", userId, page, number);
                return responseObjectDTO;
            }

            @Override
            public Map listGroups(int page, int number) {
                LOGGER.error("listGroups({}, {}) invoke fail", page, number);
                return responseObjectDTO;
            }
        };
    }
}
