package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.domain.UserVO;
import cn.cityworks.soa.dapeng.exceptions.BasicException;
import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.integrate.ReceptionCenterClient;
import cn.cityworks.soa.dapeng.integrate.UserClient;
import cn.cityworks.soa.dapeng.services.TaskService;
import org.apache.http.HttpStatus;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * create by afterloe on 2017/10/18
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ReceptionCenterClient receptionCenterClient;
    @Autowired
    private BPMClient client;
    @Autowired
    private UserClient userClient;

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

    @Override
    public Object listCanSignTaskByGroup(String token, final String groupId, int page, int number) {
        UserVO user = getUser(token); // 获取用户信息
        Map data = (Map) checkedResponseMap.apply(userClient.listGroupsByUserId(user.getId(), 0, 200));
        List<Map> groups = (List) data.get("content");
        if (0 == groups.size()) {
            return  null;
        }
        Map group = groups.stream().filter(g -> g.get("id").equals(groupId))
                .findFirst()
                .orElseThrow(() -> BasicException.build("user is not in this groups", HttpStatus.SC_BAD_REQUEST));
        return checkedResponseMap.apply(client.listCanSignTaskByGroup(group.get("groupName").toString(), page, number));
    }
}
