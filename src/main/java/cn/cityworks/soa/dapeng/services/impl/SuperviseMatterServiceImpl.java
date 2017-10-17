package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.config.Dictionaries;
import cn.cityworks.soa.dapeng.exceptions.BasicException;
import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
@Service
public class SuperviseMatterServiceImpl implements SuperviseMatterService {

    @Value("${bpm.process.id:supervisionIncident:1:4}")
    private String processId;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BPMClient client;

    private StringBuffer getFormData(File file) {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String line = null;
            while (null != (line = bufferedReader.readLine())) {
                stringBuffer.append(line);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return stringBuffer;
        }
    }

    @Override
    public Object getSuperviseMatterFromData() {
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
                        ResourceUtils.getFile("classpath:processes/" + formData));

                return formBuffer.toString();
            } catch (FileNotFoundException e) {
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
