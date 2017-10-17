package cn.cityworks.soa.dapeng.integrate.fallback;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.domain.UserVO;
import cn.cityworks.soa.dapeng.integrate.ReceptionCenterClient;
import feign.hystrix.FallbackFactory;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * create by afterloe on 2017/9/1
 */
@Component
public class ReceptionCenterFallbackFactory implements FallbackFactory<ReceptionCenterClient>, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceptionCenterFallbackFactory.class);

    // 获取 断路器所用的 资源id
    @Value("${sso.userManager.id:user-center}")
    private String RESOURCE_ID;
    private ReceptionCenterClient receptionCenterClient;

    // 设置 统一的断路器返回信息
    private ResponseDTO<UserVO> responseObjectDTO;

    @Override
    public ReceptionCenterClient create(Throwable cause) {
        LOGGER.error("ReceptionCenterClient find {}, by using {}", cause.getMessage(), RESOURCE_ID);
        if (null != receptionCenterClient) {
            return receptionCenterClient;
        }

        responseObjectDTO = ResponseDTO.build(null, HttpResponseStatus.BAD_GATEWAY.code()
                , "token error or no permission");

        return receptionCenterClient = new ReceptionCenterClient() {
            @Override
            public ResponseDTO<UserVO> who(String access_token) {
                LOGGER.error("who({}) invoke fail", access_token);
                return responseObjectDTO;
            }

            @Override
            public ResponseDTO<List> permissionCode(String access_token) {
                LOGGER.error("permissionCode({}) invoke fail", access_token);
                return null;
            }
        };
    }


}
