package com.sinosoft.one.monitor.utils;

import com.sinosoft.one.monitor.account.model.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class CurrentUserUtil {

    public static Account getCurrentUser(){
        Subject currentUser= SecurityUtils.getSubject();
        Account account= (Account) currentUser.getPrincipals().getPrimaryPrincipal();
        return account;
    }
}
