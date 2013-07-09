package com.sinosoft.one.monitor.os.linux.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.one.monitor.os.linux.model.OsShell;
import org.springframework.stereotype.Repository;

@Repository
public interface OsShellRepository extends PagingAndSortingRepository<OsShell, String> {
	
	@Query("from OsShell")
    public List<OsShell> findShell();
	
}

