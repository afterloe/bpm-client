package cn.cityworks.soa.dapeng.integrate;

import cn.cityworks.soa.dapeng.integrate.fallback.UserClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${integrate.user:user-maneger}", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    /**
     * 获取用户详细信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/users/{userId}/one", method = RequestMethod.GET)
    Map getUserById(@PathVariable(value = "userId") String userId);

    /**
     * 用户列表
     *
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Map listUsers(@RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    /**
     * 获取用户组 通过 (组id)groupId
     *
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Map listUsersByGroupId(@RequestParam(value = "groupId") String groupId
            , @RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    /**
     * 获取用户组 通过 用户名(username)
     *
     * @param username
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Map listUsersByUsername(@RequestParam(value = "name") String username
            , @RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    /**
     * 获取组列表 按照名字搜索
     *
     * @param groupName
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    Map listGroupsByName(@RequestParam(value = "groupName") String groupName
            , @RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    /**
     * 获取组详细信息
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/groups/{groupId}/one", method = RequestMethod.GET)
    Map getGroups(@PathVariable(value = "groupId") String groupId);

    /**
     * 获取用户所在的组
     *
     * @param userId
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/users/{userId}/groups", method = RequestMethod.GET)
    Map listGroupsByUserId(@PathVariable(value = "userId") String userId
            , @RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    /**
     * 获取组列表
     *
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    Map listGroups(@RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);
}
