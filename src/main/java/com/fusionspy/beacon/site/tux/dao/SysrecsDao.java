package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.site.tux.entity.SysrecsEntity;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SysrecsDao
 * User: qc
 * Date: 11-9-9
 * Time: 下午4:57
 */
@Repository
public interface SysrecsDao extends PagingAndSortingRepository<SysrecsEntity, Integer> {

    public List<SysrecsEntity> findBySiteName(String siteName);

    @SQL("DELETE FROM ge_monitor_tux_sysrecs WHERE siteName IN (?1)")
    void deleteBySiteName(String siteName);
}
