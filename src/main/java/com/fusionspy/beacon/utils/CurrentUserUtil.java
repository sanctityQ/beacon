package com.fusionspy.beacon.utils;

import com.fusionspy.beacon.account.model.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class CurrentUserUtil {

    public static Account getCurrentUser(){
        Subject currentUser= SecurityUtils.getSubject();
        Account account= (Account) currentUser.getPrincipals().getPrimaryPrincipal();
        return account;
    }
}
