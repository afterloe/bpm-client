package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.domain.UserVO;
import cn.cityworks.soa.dapeng.exceptions.BasicException;
import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.integrate.ReceptionCenterClient;
import cn.cityworks.soa.dapeng.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * create by afterloe on 2017/10/18
 */
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ReceptionCenterClient receptionCenterClient;
    @Autowired
    private BPMClient client;

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    private UserVO getUser(String token) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        return response.getData();
    }

    @Override
    public Object listTask(String token, int page, int number) {
        UserVO user = getUser(token); // 获取用户信息
        return checkedResponseMap.apply(client.listTaskByUserId(user.getId(), page, number));
    }

    @Override
    public Object countByCanSignTask(String token) {
        UserVO user = getUser(token); // 获取用户信息
        return checkedResponseMap.apply(client.countByCanSignTask(user.getId()));
    }
}
