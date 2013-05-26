package com.sinosoft.one.monitor.controllers.application.agent;

import com.sinosoft.one.monitor.application.domain.AgentMessageServiceFactory;
import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 代理端消息处理Controller.
 * User: carvin
 * Date: 13-3-3
 * Time: 下午10:31
 */
@Path
public class AgentMessageController {
	private Logger logger = LoggerFactory.getLogger(AgentMessageController.class);
	@Autowired
	private AgentMessageServiceFactory agentMessageServiceFactory;
	@Autowired
	private ApplicationService applicationService;

	@Post("/message")
	public Reply handleMessage(Invocation invocation) {
		String applicationId = invocation.getParameter("applicationId");
		String notificationType = invocation.getParameter("notificationType");
		String data = invocation.getParameter("data");
		Application application = applicationService.findApplication(applicationId);
		try {
			if(application == null) {
				return Replys.with("NotExist");
			}
			agentMessageServiceFactory.buildAgentMessageService(notificationType).handleMessage(applicationId, data);
		} catch (Throwable throwable) {
			logger.error("handler applicaiton agent message exception.", throwable);
			return Replys.with("Exception");
		}
		return Replys.with("Success");
	}
}
