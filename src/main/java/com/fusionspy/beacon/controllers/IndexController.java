package com.fusionspy.beacon.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fusionspy.beacon.alarm.domain.AlarmService;
import com.fusionspy.beacon.alarm.model.Alarm;
import com.fusionspy.beacon.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;

/**
 * 首页
 * @author bao
 *
 */
@Path
public class IndexController {
	
	@Autowired
	private AlarmService alarmService;
	
	@Get("index")
	public String index() {
		return "index";
	}
	
	public Reply alarmList(Invocation inv) {
		/* 获取项目根路径*/
		String contextPath = inv.getServletContext().getContextPath();
		/* 封装表格行数据信息List->rows*/
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
		/* 查询数据库健康列表数据*/
		List<Alarm> alarmList = alarmService.queryLatestAlarmsRows(20);
		/* 循环构建表格行数据*/
		for(Alarm alarm : alarmList) {
			Map<String, Object> row = new HashMap<String, Object>();
			List<String> cell = new ArrayList<String>();
			
			/* 健康状况 1-健康(绿色=fine) ；其它状态均不健康(红色=poor)*/
			String healthyClass = MessageUtils.SeverityLevel2CssClass(alarm.getSeverity());
			/* 构建预警详细信息地址*/
			String url = contextPath + "/alarm/manager/detail/"+alarm.getId();
			/* 格式化表格数据信息*/
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV, healthyClass));
			String title = alarm.getMessage();
			String subTitle = title.length()>20 ? title.substring(0, 19)+"...." : title;
			cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORM_A_SUBTITLE, url, title, subTitle));
			cell.add(alarm.getResourceName());
			cell.add(alarm.getMonitorType());
			cell.add(DateFormatUtils.format(alarm.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			row.put("cell", cell);
			rows.add(row);
		}
		Map<String, Object> grid = new HashMap<String, Object>();
		grid.put("rows", rows);
		return Replys.with(grid).as(Json.class);
	}


}
