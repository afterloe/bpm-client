package cn.cityworks.soa.dapeng.services;

import java.io.Serializable;

public interface TaskService extends Serializable, Tools {

    /**
     * 获取我的任务
     *
     * @param token
     * @return
     */
    Object listTask(String token, int page, int number);

    /**
     * 可接的任务数
     *
     * @param token
     * @return
     */
    Object countByCanSignTask(String token);

    /**
     * 按组获取可接的任务列表
     *
     * @param groupId
     * @return
     */
    Object listCanSignTaskByGroup(String token, String groupId, int page, int number);
}
