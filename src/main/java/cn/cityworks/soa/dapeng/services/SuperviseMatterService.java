package cn.cityworks.soa.dapeng.services;

import java.io.*;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
public interface SuperviseMatterService extends Serializable, Tools {

    /**
     * 获取督办事件 表单
     *
     * @return
     */
    Object getSuperviseMatterFromData(String token);

    /**
     * 上报督办事件
     *
     * @param token
     * @param taskForm
     * @return
     */
    Object setSuperviseMatterFromData(String token, Map taskForm);

    /**
     * 读取文件信息
     *
     * @param file
     * @return
     */
    default StringBuffer getFormData(File file) {
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
}
