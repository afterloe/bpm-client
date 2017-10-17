package cn.cityworks.soa.dapeng.dao;

import cn.cityworks.soa.dapeng.domain.superviseMatter.FromDataDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileSystemRepository extends JpaRepository<FromDataDO, String> {
}