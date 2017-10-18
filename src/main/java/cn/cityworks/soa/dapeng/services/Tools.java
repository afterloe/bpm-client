package cn.cityworks.soa.dapeng.services;

import cn.cityworks.soa.dapeng.exceptions.BasicException;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * create by afterloe on 2017/10/17
 */
public interface Tools extends Serializable {

    /**
     * 快速获取并检测 返回的参数
     *
     */
    Function<Map, Map> checkedResponseMap = responseMap -> {
        int code = Integer.valueOf(responseMap.get("code").toString());
        if (HttpStatus.SC_OK != code) {
            throw BasicException.build(responseMap.get("msg").toString(), code);
        }

        return (Map)responseMap.get("data");
    };

    default String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 参数检测
     *
     * @param parameter
     * @param args
     */
    default void checkedParameter(Map parameter, String ...args) {
        Arrays.stream(args).forEach(arg -> {
            if (!parameter.containsKey(arg)) {
                throw BasicException.build("lack parameter -> " + arg, HttpStatus.SC_BAD_REQUEST);
            }
        });
    }
}
