package cn.cityworks.soa.dapeng.dao;

import cn.cityworks.soa.dapeng.domain.superviseMatter.FormDataDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileSystemRepository extends JpaRepository<FormDataDO, String> {
}