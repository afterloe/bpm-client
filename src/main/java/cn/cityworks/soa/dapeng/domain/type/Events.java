package cn.cityworks.soa.dapeng.domain.type;

import java.util.Arrays;

public enum Events {

    remediation("水体整治","water-remediation"),
    problem("黑臭问题", "black-problem"),
    events("游客事件", "tourist-events"),
    report("特殊上报", "special-report");

    private String name;
    private String value;

    /**
     * 构造函数
     *
     * @param name
     * @param index
     */
    private Events(String name, String index) {
        this.name = name;
        this.value = index;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String index) {
        this.value = index;
    }

    public static Events get(String value) {
        return Arrays.stream(Events.values()).
                filter(e -> e.getValue().equals(e)).
                findFirst().orElse(remediation);
    }
}
