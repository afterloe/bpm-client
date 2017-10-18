package cn.cityworks.soa.dapeng.dao;

import cn.cityworks.soa.dapeng.domain.superviseMatter.FormDataDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * create by afterloe on 2017/10/17
 */
public interface FormDataRepository extends JpaRepository<FormDataDO, String> {

    /**
     * 按照是否被 删除 分页查询列表
     *
     * @param isEnable
     * @param pageable
     * @return
     */
    Page<FormDataDO> findByEnable(Boolean isEnable, Pageable pageable);

    /**
     * 按照是否 完成 分页查询列表
     *
     * @param isComplete
     * @param pageable
     * @return
     */
    Page<FormDataDO> findByComplete(Boolean isComplete, Pageable pageable);

    /**
     * 按照是否 在处理 分页查询列表
     *
     * @param isAssign
     * @param pageable
     * @return
     */
    Page<FormDataDO> findByAssign(Boolean isAssign, Pageable pageable);

    /**
     * 按照是否 在否被督办 分页查询列表
     *
     * @param isActivity
     * @param pageable
     * @return
     */
    Page<FormDataDO> findByActivity(Boolean isActivity, Pageable pageable);

}