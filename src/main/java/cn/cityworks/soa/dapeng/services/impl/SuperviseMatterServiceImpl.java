package cn.cityworks.soa.dapeng.services.impl;

import cn.cityworks.soa.dapeng.integrate.BPMClient;
import cn.cityworks.soa.dapeng.services.SuperviseMatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * create by afterloe on 2017/10/17
 */
@Service
public class SuperviseMatterServiceImpl implements SuperviseMatterService {

    @Value("${bpm.process.id:supervisionIncident:1:4}")
    private String processId;
    @Autowired
    private BPMClient client;

    @Override
    public Object getSuperviseMatterFromData() {
        return null;
    }
}
