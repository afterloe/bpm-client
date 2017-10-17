package cn.cityworks.soa.dapeng.services;

import cn.cityworks.soa.dapeng.exceptions.BasicException;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public interface Tools extends Serializable {

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
