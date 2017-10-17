package cn.cityworks.soa.dapeng.domain;

import java.io.Serializable;

/**
 * create by afterloe on 2017/9/1
 */
public class UserVO implements Serializable {

    private String id;
    private String username;
    private String nickname;
    private Boolean enabled;
    private String token;

    public String toQueryString() {
        return "id=" + id
                + "&username=" + username
                + "&nickname=" + nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
