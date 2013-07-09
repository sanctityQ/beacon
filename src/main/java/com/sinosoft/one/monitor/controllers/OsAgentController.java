package com.sinosoft.one.monitor.controllers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.one.monitor.common.AlarmMessageBuilder;
import com.sinosoft.one.monitor.os.linux.domain.OsProcessService;
import com.sinosoft.one.monitor.os.linux.domain.OsService;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsShell;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;

@Path
public class OsAgentController {
	/**
	 * 系统基本信息几脚本service
	 */
	@Autowired
	private OsService osService;
	
	@Autowired
	private OsProcessService osProcessService;

	/**
	 * 响应Angent，发送基本信息的脚本
	 */
	@Post("recieveOsInfo")
	@Get("recieveOsInfo")
	public void receiveOsInfo(Invocation inv) throws IOException {
            Map<String, String[]> osAgentInfo  = inv.getRequest().getParameterMap();
			String osAgentIp = getValue("ip", osAgentInfo);
			Os  os = osService.getOsBasicByIp(osAgentIp);
			ObjectOutputStream oos = new ObjectOutputStream(inv.getResponse()
					.getOutputStream());
			System.out.println(getValue("ID", osAgentInfo));
            Map<String, String> shellAndIp = Maps.newHashMap();
            if(os!=null){
				shellAndIp.put("ID", os.getOsInfoId());
				shellAndIp.put("pollingTime", String.valueOf(os.getIntercycleTime()));
				List<OsShell>osShells=osService.getOsShell();
				for (OsShell osShell : osShells) {
					shellAndIp.put(osShell.getType(),osShell.getTemplate());
				}
				
			}else {
				shellAndIp.put("ID", null);
			}
			oos.writeObject(shellAndIp);
			oos.close();


	}

	/**
	 * 获取系统所有监控数据
	 */
	@Post("recieveOsResult")
	@Get("recieveOsResult")
	public void receiveOsResult(Invocation inv) {
		try {
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
            Map<String, String[]> osAgentInfo   =  inv.getRequest().getParameterMap();
			String osAgentID = getValue("ID", osAgentInfo);
			Os os = osService.getOsBasicById(osAgentID);
			ObjectOutputStream oos = new ObjectOutputStream(inv.getResponse().getOutputStream());
			Map<String, String> responsInfo=new HashMap<String, String>();//返回代理端的响应信息;
            String cpuInfo = StringUtils.EMPTY;
            String ramInfo = StringUtils.EMPTY;
            String diskInfo = StringUtils.EMPTY;
            String respondTime = StringUtils.EMPTY;
            String thisInterCycleTime = StringUtils.EMPTY;
            if(os!=null){
				//采样时间
				cpuInfo = getValue("cpuInfo", osAgentInfo);
				ramInfo = getValue("ramInfo", osAgentInfo);
				diskInfo = getValue("diskInfo", osAgentInfo);
				respondTime = getValue("respondTime", osAgentInfo);
				thisInterCycleTime=getValue("thisInterCycleTime", osAgentInfo);
				responsInfo.put("newInterCycle", os.getIntercycleTime()+"");//返回轮询时间
				oos.writeObject(responsInfo);
				oos.close();
			}
			//保存采样数据
			osProcessService.saveSampleData(os.getOsInfoId(), cpuInfo, ramInfo, diskInfo, respondTime, calendar.getTime());
			//记录每次采样的可用性临时数据 此处为可用状态  状态码“1”
			osProcessService.savaAvailableSampleData(os.getOsInfoId(), calendar.getTime(), Integer.valueOf(thisInterCycleTime), "1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private String getValue(String key, Map<String, String[]> osInfo) {
		return osInfo.get(key)[0];
	}
}
