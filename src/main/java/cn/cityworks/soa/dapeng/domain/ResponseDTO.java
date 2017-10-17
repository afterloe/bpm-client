package cn.cityworks.soa.dapeng.domain;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.Serializable;

/**
 * Created by afterloe on 2017/5/31.
 *
 * SOA系统统一 返回对象
 */
public class ResponseDTO<T extends Object> implements Serializable {

    /**
     * 数据 依据具体业务 - 实现范型自动转换
     */
    private T data;

    /**
     * 请求状态码 - 国际化处理
     */
    private int code;

    /**
     * 响应信息 - 服务端提示信息
     */
    private String msg;

    private ResponseDTO() {
        super();
    }

    /**
     * 构建 成功的响应对象
     *
     * @param data 响应数据
     * @return tech.ascs.citywork.domain.net.ResponseDTO
     */
    public static ResponseDTO build(Object data) {
        ResponseDTO response = new ResponseDTO();
        response.data = data;
        response.code = HttpResponseStatus.OK.code();
        return response;
    }

    /**
     * 构建 成功的响应对象
     *
     * @param code 状态码
     * @param msg 服务端提示信息
     * @return tech.ascs.citywork.domain.net.ResponseDTO
     */
    public static ResponseDTO build(int code, String msg) {
        ResponseDTO response = new ResponseDTO();
        response.code = code;
        response.msg = msg;
        return response;
    }

    /**
     * 构建 响应对象
     *
     * @param data 响应数据
     * @param code 提示信息
     * @param msg 服务端提示信息
     * @return tech.ascs.citywork.domain.net.ResponseDTO
     */
    public static ResponseDTO build(Object data, int code, String msg) {
        ResponseDTO response = new ResponseDTO();
        response.data = data;
        response.code = code;
        response.msg = msg;
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}