package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.config.Dictionaries;
import cn.cityworks.soa.dapeng.dao.FormDataRepository;
import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.domain.UserVO;
import cn.cityworks.soa.dapeng.domain.superviseMatter.FormDataDO;
import cn.cityworks.soa.dapeng.exceptions.BasicException;
import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.integrate.ReceptionCenterClient;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
@Service
public class SuperviseMatterServiceImpl implements SuperviseMatterService {

    @Value("${bpm.process.id:supervisionIncident:1:4}")
    private String processId;
    @Value("${bpm.process.key:supervisionIncident}")
    private String processDefinitionKey;
    @Value("${bpm.process.formPath:process/form}")
    private String formPath;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BPMClient client;
    @Autowired
    private ReceptionCenterClient receptionCenterClient;
    @Autowired
    private FormDataRepository formDataRepository;

    @Override
    public Object confirm(String token, Map variables) {
        UserVO user = getUser(token); // 获取用户信息
        checkedParameter(variables, "superviseMatterId"); // 检测参数
        String superviseMatterId = variables.get("superviseMatterId").toString();
        FormDataDO formDataDO = formDataRepository.getOne(superviseMatterId);
        if (null == formDataDO) {
            throw BasicException.build("no such this superviseMatter! -> " + superviseMatterId
                    , HttpStatus.SC_NOT_FOUND);
        }
        Map data = (Map) checkedResponseMap.apply(
                client.submitProcess(formDataDO.getProcessId(), user.getId())); // 推动流程

        return null;
    }

    @Override
    public Object getSuperviseMatter(String token, String superviseMatterId) {
        UserVO user = getUser(token); // 获取用户信息
        FormDataDO formDataDO = formDataRepository.getOne(superviseMatterId);
        return formDataDO;
    }

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

    /**
     * 启动流程
     *
     * @param uid
     * @param fromId
     * @return
     */
    private Map startProcess(String uid, String fromId) {
        Map variables = new LinkedHashMap();
        variables.put("starter", uid);
        variables.put("processDefinitionKey", processDefinitionKey);
        variables.put("businessKey", fromId);
        return (Map) checkedResponseMap.apply(client.startProcess(variables));
    }

    /**
     * 保存FromData进入数据库
     *
     * @param taskForm
     * @param processId
     * @param uid
     * @param fromId
     * @return
     */
    private FormDataDO saveSuperviseMatterFromData(Map taskForm, String processId, String uid, String fromId) {
        long toDate = new Date().getTime();

        FormDataDO formData = new FormDataDO();
        formData.setUid(uid);
        formData.setProcessId(fromId);
        formData.setComplete(false);
        formData.setDescribe(taskForm.get("describe").toString());
        formData.setEnable(false);
        formData.setModifyTime(toDate);
        formData.setProcessId(processId); // 设置启动后的流程id
        formData.setCreateTime(toDate);
        formData.setTitle(taskForm.get("title").toString());
        formData.setType(taskForm.get("type").toString());

        formDataRepository.save(formData);

        return formData;
    }

    @Override
    public Object listSuperviseMatter(String token, String action, Boolean value, int page, int number) {
        UserVO user = getUser(token); // 获取用户信息
        Object response;
        Pageable pageable = new PageRequest(page, number);
        switch (action) {
            case "complete":
                response = formDataRepository.findByComplete(value, pageable);
                break;
            case "assign":
                response = formDataRepository.findByAssign(value, pageable);
                break;
            case "enable":
                response = formDataRepository.findByEnable(value, pageable);
                break;
            case "activity":
                response = formDataRepository.findByActivity(value, pageable);
                break;
            default:
                response = formDataRepository.findByEnable(value, pageable);
        }
        return response;
    }

    /*
     *  保存 督办事项 N-S 流程说明
     *
     *  1. 通过token 调用远程服务获取用户信息 [可以在1 ~ 2 步骤后加入鉴权相关代码]
     *  2. 检测参数，从routers获取的参数，确保流程能够正常启动
     *  3. 生成FromDataId，并获取启动流程所需的参数
     *  4. 启动流程
     *  5. 获取流程启动后的参数，并继续组装FormData数据
     *  6. 保存FormData数据进入数据
     *  7. 整理返回数据s
     */
    @Override
    public Object saveSuperviseMatter(String token, Map taskForm) {
        UserVO user = getUser(token); // 获取用户信息
        checkedParameter(taskForm, "type", "title", "describe"); // 检测参数
        String formId = getUUID(), uid = user.getId(); // 生成FormData 相关参数
        Map processObject = startProcess(uid, formId); // 启动流程
        FormDataDO formData = saveSuperviseMatterFromData(taskForm,
                processObject.get("processId").toString(), uid, formId); // 保存进入数据库

        // 整理返回数据
        Map variables = new LinkedHashMap();
        variables.put("fromInfo", formData);
        variables.put("processInfo", processObject);

        return variables;
    }

    @Override
    public Object getSuperviseMatterFromData(String token) {
        Map data = (Map) checkedResponseMap.apply(client.getStartFormData(processId));
        Object type = data.get("type");
        String formData = data.get("formData").toString();

        if (Dictionaries.FORM_DATA_TYPE_KEY.equals(type)) {
            try {
                StringBuffer formBuffer = getFormData(
                        ResourceUtils.getFile("classpath:" + formPath
                                + File.separator + formData));
                return objectMapper.readValue(formBuffer.toString(), Map.class);
            } catch (Exception e) {
                throw BasicException.build("form data can't found -> " + formData, HttpStatus.SC_NOT_FOUND);
            }
        } else if (Dictionaries.FORM_DATA_TYPE_PROPERTY.equals(type)){
            try {
                return objectMapper.readValue(formData, Map.class);
            } catch (IOException e) {
                throw BasicException.build("form data can't transformation -> " + formData
                        , HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
        }

        throw BasicException.build("BPM service engine return data type is not supper! ->" + type
                , HttpStatus.SC_BAD_REQUEST);
    }
}
