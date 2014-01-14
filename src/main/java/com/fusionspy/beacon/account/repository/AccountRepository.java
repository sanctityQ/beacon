package com.fusionspy.beacon.account.repository;
// Generated 2012-12-21 10:49:05 by One Data Tools 1.0.0

import com.sinosoft.one.data.jade.annotation.SQL;
import com.fusionspy.beacon.account.model.Account;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends PagingAndSortingRepository<Account, String>, QueryDslPredicateExecutor<Account> {
    Account findByLoginName(String loginName);
    
	@SQL("DELETE FROM ge_monitor_account a WHERE a.id IN(?1)")
	void deleteByIDs(@Param("ids") String ids);

}

