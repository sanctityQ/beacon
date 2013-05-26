package com.sinosoft.one.monitor.controllers.account;


import com.sinosoft.one.monitor.account.model.Account;
import com.sinosoft.one.monitor.account.domain.AccountService;
import com.sinosoft.one.monitor.controllers.LoginRequired;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.validation.annotation.Validation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 使用@ModelAttribute, 实现Struts2 Preparable二次绑定的效果。
 * 因为@ModelAttribute被默认执行, 而其他的action url中并没有${id}，所以需要独立出一个Controller.
 *
 * @author Administrator
 */
@LoginRequired
@Path("user")
public class UserDetailController {

    @Autowired
    private AccountService accountService;

    @Get("update/{id}")
    @Post("update/{id}")
    public String updateForm(@Param("id") String id, Invocation inv) {
        inv.addModel("account", accountService.getAccount(id));
        return "userForm";
    }

    @Post("save/{id}")
    public String save(@Param("id") String id, @Validation(errorPath = "a:update/{id}") Account account, Invocation inv) {
        account.setCreateTime(new Date());
        accountService.updateAccount(account);
        inv.addFlash("message", "修改用户" + account.getLoginName() + "成功");
        return "r:/account/user/list";
    }
}
