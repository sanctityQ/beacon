package com.fusionspy.beacon.controllers.account;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fusionspy.beacon.account.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fusionspy.beacon.account.domain.AccountService;
import com.fusionspy.beacon.account.model.Account;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;

import static com.google.common.collect.Lists.newArrayList;

@Path("user")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Get("list")
    public String list() {
        return "userManager";
    }

    @Post("data")
	public void list(Invocation inv) throws Exception{
		List<Account> accountList = accountService.getAllAccount();
		List<Account> accounts = new ArrayList<Account>();
		for (Account account : accountList) {
			accounts.add(account);
		}
		Page<Account> page = new PageImpl<Account>(accounts);
		Gridable<Account>  gridable = new Gridable<Account> (page);
		String cellString = new String("loginName,name,statusStr,phone,email,createTimeStr,operation");
		gridable.setIdField("id");
		gridable.setCellStringField(cellString);
	    UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
	}

    @Get("create")
    @Post("errorCreate")
    public String createForm(Invocation inv) {
        inv.addModel("user", new Account());
        return "adduser";
    }

    @Post("save")
    public Reply save(Account account, Invocation inv,@Param("role")long groupId) {
        List<Group> groups = newArrayList();
        groups.add(new Group(groupId,null));
        account.setGroupList(groups);
        account.setCreateTime(new Date());
        if("".equals(account.getStatus())||account.getStatus()==null){
            account.setStatus(String.valueOf(1));
        }
        accountService.saveAccount(account);
        return Replys.simple().success();
    }

    @Get("update/{id}")
	public String update(@Param("id")String id,Invocation inv){
		inv.addModel("user", accountService.getAccount(id));
		return "adduser";
	}
    @Post("delete/{id}")
    public Reply delete(@Param("id") String id, Invocation inv) {
        try{
        	accountService.deleteAccount(id);
		}catch(Exception e){
			return Replys.simple().fail();
		}
        return Replys.simple().success();
    }
    
    @Post("batchDelete/{ids}")
    public Reply batchDelete(@Param("ids") String ids) {
    	String str[]=ids.split(",");
        try{
        	for (String string : str) {
        		accountService.deleteAccount(string);
    		}
		}catch(Exception e){
			return Replys.simple().fail();
		}
        return Replys.simple().success();
    }

    @Post("view/{id}")
    public Object view(@Param("id") String id, Invocation inv) {
        Account account = accountService.getAccount(id);
        return Replys.with(account).as(Json.class);
    }
}
