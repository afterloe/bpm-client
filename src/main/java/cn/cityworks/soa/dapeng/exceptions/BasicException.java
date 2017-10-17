package cn.cityworks.soa.dapeng.exceptions;

/**
 * Created by afterloe on 2017/6/12.
 */
public class BasicException extends RuntimeException {

    private int code;

    private BasicException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static BasicException build(String msg) {
        return new BasicException(msg, 500);
    }

    public static BasicException build(String msg, int code) {
        return new BasicException(msg, code);
    }
}
