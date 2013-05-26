package com.fusionspy.beacon.system.dao;

import com.fusionspy.beacon.system.entity.SysrecsEntity;
import com.sinosoft.one.data.jade.annotation.SQL;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * SysrecsDao
 * User: qc
 * Date: 11-9-9
 * Time: 下午4:57
 */
public interface SysrecsDao extends PagingAndSortingRepository<SysrecsEntity, Integer> {

    public List<SysrecsEntity> findBySiteName(String siteName);

    @SQL("DELETE FROM sysrecs e WHERE e.siteName IN(?1)")
    void deleteBySiteName(String siteName);
}
