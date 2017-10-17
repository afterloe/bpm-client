package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.config.Dictionaries;
import cn.cityworks.soa.dapeng.dao.FileSystemRepository;
import cn.cityworks.soa.dapeng.domain.ResponseDTO;
import cn.cityworks.soa.dapeng.domain.UserVO;
import cn.cityworks.soa.dapeng.domain.superviseMatter.FromDataDO;
import cn.cityworks.soa.dapeng.exceptions.BasicException;
import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.integrate.ReceptionCenterClient;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import cn.cityworks.soa.dapeng.services.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
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
    private String key;
    @Value("${bpm.process.formPath:process/form}")
    private String formPath;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BPMClient client;
    @Autowired
    private ReceptionCenterClient receptionCenterClient;
    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Override
    public Object setSuperviseMatterFromData(String token, Map taskForm) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        checkedParameter(taskForm, "type", "title", "describe");
        String fromId = getUUID(), uid = response.getData().getId();

        Map variables = new LinkedHashMap();
        variables.put("starter", uid);
        variables.put("processDefinitionKey", key);
        variables.put("businessKey", fromId);

        Map processResponse = client.startProcess(variables);
        int code = Integer.valueOf(processResponse.get("code").toString());
        if (200 != code) {
            throw BasicException.build(processResponse.get("msg").toString(), code);
        }
        Map processObject = (Map) processResponse.get("data");

        long toDate = new Date().getTime();
        FromDataDO formData = new FromDataDO();
        formData.setUid(uid);
        formData.setProcessId(fromId);
        formData.setComplete(false);
        formData.setDescribe(taskForm.get("describe").toString());
        formData.setEnable(false);
        formData.setModifyTime(toDate);
        formData.getProcessId();
        formData.setCreateTime(toDate);
        formData.setTitle(taskForm.get("title").toString());
        formData.setType(taskForm.get("type").toString());

        fileSystemRepository.save(formData);

        variables.clear();
        variables.put("fromInfo", formData);
        variables.put("processInfo", processObject);

        return null;
    }

    @Override
    public Object getSuperviseMatterFromData(String token) {
        Map response = client.getStartFormData(processId);
        int code = Integer.valueOf(response.get("code").toString());
        if (200 != code) {
            throw BasicException.build(response.get("msg").toString(), code);
        }
        Map data = (Map) response.get("data");
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
