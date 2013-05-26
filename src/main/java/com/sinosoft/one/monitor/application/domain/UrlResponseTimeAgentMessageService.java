package com.sinosoft.one.monitor.application.domain;

import com.alibaba.fastjson.JSON;
import com.sinosoft.one.monitor.application.model.RequestPerMinute;
import com.sinosoft.one.monitor.application.model.UrlResponseTime;
import com.sinosoft.one.monitor.application.repository.RequestPerMinuteRepository;
import com.sinosoft.one.monitor.application.repository.UrlResponseTimeRepository;
import com.sinosoft.one.monitor.utils.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * URL响应时间代理端消息处理服务类.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:06
 */
@Service
public class UrlResponseTimeAgentMessageService implements AgentMessageService{
	private Logger logger = LoggerFactory.getLogger(UrlResponseTimeAgentMessageService.class);
	@Autowired
	private UrlResponseTimeRepository urlResponseTimeRepository;
	@Autowired
	private RequestPerMinuteRepository requestPerMinuteRepository;

	/**
	 * 处理代理端URL响应时间消息
	 * @param applicationId 应用系统ID
	 * @param data URL响应时间数据
	 */
	public void handleMessage(String applicationId, String data) {
		logger.debug("UrlResponseTimeAgentMessageService");
		List<UrlResponseTime> urlResponseTimes = JSON.parseArray(data, UrlResponseTime.class);

		MinMaxTime minMaxTime = new MinMaxTime();
		Map<String, UrlResponseTime> urlResponseTimeMap = new HashMap<String, UrlResponseTime>();
		Map<String, Integer> requestPerMinuteMap = new HashMap<String, Integer>();

		generateDatas(urlResponseTimes, urlResponseTimeMap, requestPerMinuteMap, minMaxTime);

		Date startDate = DateUtil.getHoursDate(minMaxTime.min);
		Date endDate = DateUtil.getHoursDate(minMaxTime.max);
		List<UrlResponseTime> storeUrlResponseTimes = urlResponseTimeRepository.selectUrlResponseTimes(applicationId, startDate, endDate);

		List<UrlResponseTime> toUpdateUrlResponseTimes = new ArrayList<UrlResponseTime>();
		if(storeUrlResponseTimes.isEmpty()) {
			addToUpdateUrlResponseTime(toUpdateUrlResponseTimes, urlResponseTimeMap, applicationId);
		} else {
			for(UrlResponseTime storeUrlResponseTime : storeUrlResponseTimes) {
				String key = getUrlResponseTimeKey(storeUrlResponseTime);
				if(urlResponseTimeMap.containsKey(key)) {
					UrlResponseTime targetUrlResponseTime = urlResponseTimeMap.get(key);
					if(storeUrlResponseTime.getMinResponseTime() > targetUrlResponseTime.getMinResponseTime()) {
						storeUrlResponseTime.setMinResponseTime(targetUrlResponseTime.getMinResponseTime());
					}
					if(storeUrlResponseTime.getMaxResponseTime() < targetUrlResponseTime.getMaxResponseTime()) {
						storeUrlResponseTime.setMaxResponseTime(targetUrlResponseTime.getMaxResponseTime());
					}
					storeUrlResponseTime.addTotalResponseTime(targetUrlResponseTime.getTotalResponseTime());
					storeUrlResponseTime.increaseTotalCount();
					toUpdateUrlResponseTimes.add(storeUrlResponseTime);
					urlResponseTimeMap.remove(key);
				}
			}
			logger.debug("addToUpdateUrlResponseTime");
			addToUpdateUrlResponseTime(toUpdateUrlResponseTimes, urlResponseTimeMap, applicationId);
		}
		logger.debug("urlResponseTimeRepository.save");
		urlResponseTimeRepository.save(toUpdateUrlResponseTimes);


		List<RequestPerMinute> storeRequestPerMinutes = requestPerMinuteRepository.selectRequestPerMinutes(applicationId, startDate, endDate);
		List<RequestPerMinute> toUpdateRequestPerMinutes = new ArrayList<RequestPerMinute>();
		if(!storeRequestPerMinutes.isEmpty()) {
			for(RequestPerMinute storeRequestPerMinute : storeRequestPerMinutes) {
				String requestPerMinuteKey = getRequestPerMinuteKey(storeRequestPerMinute.getRecordTime());
				if(requestPerMinuteMap.containsKey(requestPerMinuteKey)) {
					storeRequestPerMinute.setRequestNumber(storeRequestPerMinute.getRequestNumber() + requestPerMinuteMap.get(requestPerMinuteKey));
					toUpdateRequestPerMinutes.add(storeRequestPerMinute);
					requestPerMinuteMap.remove(requestPerMinuteKey);
				}
			}
			logger.debug("addToUpdateRequestPerMinute");
			addToUpdateRequestPerMinute(toUpdateRequestPerMinutes, requestPerMinuteMap, applicationId);
		} else {
			addToUpdateRequestPerMinute(toUpdateRequestPerMinutes, requestPerMinuteMap, applicationId);
		}
		logger.debug("urlResponseTimeRepository.save");
		requestPerMinuteRepository.save(toUpdateRequestPerMinutes);
	}

	private void addToUpdateUrlResponseTime(List<UrlResponseTime> toUpdateUrlResponseTimes,
	                                        Map<String, UrlResponseTime> urlResponseTimeMap,
	                                        String applicationId) {
		for(UrlResponseTime urlResponseTime : urlResponseTimeMap.values()) {
			urlResponseTime.setApplicationId(applicationId);
			urlResponseTime.setRecordTime(DateUtil.getHoursDate(urlResponseTime.getRecordTime()));
			toUpdateUrlResponseTimes.add(urlResponseTime);
		}
	}

	private void addToUpdateRequestPerMinute(List<RequestPerMinute> toUpdateRequestPerMinutes,
	                                         Map<String, Integer> requestPerMinuteMap,
	                                         String applicationId) {
		try {
			for (String key : requestPerMinuteMap.keySet()) {
				Integer rpm = requestPerMinuteMap.get(key);
				RequestPerMinute requestPerMinute = new RequestPerMinute();
				requestPerMinute.setRequestNumber(rpm);
				requestPerMinute.setApplicationId(applicationId);
				requestPerMinute.setRecordTime(DateUtil.getHoursDate(new SimpleDateFormat("yyyy-MM-dd HH").parse(key)));
				toUpdateRequestPerMinutes.add(requestPerMinute);
			}
		} catch (Exception e) {
			logger.error("add data to update request per minute exception.", e);
		}

	}

	/**
	 * 根据响应时间列表生成响应时间Map
	 * @param urlResponseTimes 响应时间List
	 * @param minMaxTime 最小最大时间对象
	 * @return 响应时间Map
	 */
	private void generateDatas(List<UrlResponseTime> urlResponseTimes,
	                          Map<String, UrlResponseTime> urlResponseTimeMap,
	                          Map<String, Integer> requestPerMinuteMap,
	                          MinMaxTime minMaxTime) {
		UrlResponseTime firstUrlResponseTime = urlResponseTimes.get(0);
		minMaxTime.min = firstUrlResponseTime.getRecordTime();
		minMaxTime.max = firstUrlResponseTime.getRecordTime();
		firstUrlResponseTime.setTotalResponseTime(firstUrlResponseTime.getResponseTime());
		firstUrlResponseTime.setMinResponseTime(firstUrlResponseTime.getResponseTime());
		firstUrlResponseTime.setMaxResponseTime(firstUrlResponseTime.getResponseTime());
		firstUrlResponseTime.increaseTotalCount();
		urlResponseTimeMap.put(getUrlResponseTimeKey(firstUrlResponseTime), firstUrlResponseTime);

		requestPerMinuteMap.put(getRequestPerMinuteKey(firstUrlResponseTime.getRecordTime()), 1);

		for(int i=1, len=urlResponseTimes.size(); i<len; i++) {
			UrlResponseTime urlResponseTime = urlResponseTimes.get(i);
			String key = getUrlResponseTimeKey(urlResponseTime);
			if(urlResponseTimeMap.containsKey(key)) {
				UrlResponseTime targetUrlResponseTime = urlResponseTimeMap.get(key);
				long responseTime = urlResponseTime.getResponseTime();
				if(targetUrlResponseTime.getMinResponseTime() > responseTime) {
					targetUrlResponseTime.setMinResponseTime(responseTime);
				}
				if(targetUrlResponseTime.getMaxResponseTime() < responseTime) {
					targetUrlResponseTime.setMaxResponseTime(responseTime);
				}
				targetUrlResponseTime.addTotalResponseTime(responseTime);
				targetUrlResponseTime.increaseTotalCount();
			} else {
				long responseTime = urlResponseTime.getResponseTime();
				urlResponseTime.setMaxResponseTime(responseTime);
				urlResponseTime.setTotalResponseTime(responseTime);
				urlResponseTime.setMinResponseTime(responseTime);
				urlResponseTime.increaseTotalCount();
				urlResponseTimeMap.put(key, urlResponseTime);
			}

			String requestPerMinuteKey = getRequestPerMinuteKey(urlResponseTime.getRecordTime());
			Date recordTime = urlResponseTime.getRecordTime();
			if(requestPerMinuteMap.containsKey(requestPerMinuteKey)) {
				requestPerMinuteMap.put(requestPerMinuteKey , requestPerMinuteMap.get(requestPerMinuteKey) + 1);
			} else {
				requestPerMinuteMap.put(requestPerMinuteKey , 1);
			}

			if(minMaxTime.min.compareTo(recordTime) > 0) {
				minMaxTime.min = recordTime;
			}
			if(minMaxTime.max.compareTo(recordTime) < 0) {
				minMaxTime.max = recordTime;
			}
		}
	}

	/**
	 * 得到UrlResponseTimeKey
	 * @param urlResponseTime
	 * @return
	 */
	private String getUrlResponseTimeKey(UrlResponseTime urlResponseTime) {
		return urlResponseTime.getUrl() + "_" + DateFormatUtils.format(urlResponseTime.getRecordTime(), "yyyy-MM-dd HH");
	}

	/**
	 * 得到RequestPerMinuteKey
	 * @param recordTime
	 * @return
	 */
	private String getRequestPerMinuteKey(Date recordTime) {
		return DateFormatUtils.format(recordTime, "yyyy-MM-dd HH");
	}

	/**
	 * 为了获得最大最小时间的类
	 */
	private class MinMaxTime {
		private Date min;
		private Date max;
	}

	/**
	 * 查询当天的每分钟请求数
	 * @return 当天的每分钟请求数
	 */
	public RequestPerMinute queryCurrentDateRPM() {
		PageRequest pageRequest = new PageRequest(1, 1, new Sort(Sort.Direction.DESC, "recordDate"));
		Page<RequestPerMinute> pagable = requestPerMinuteRepository.findAll(pageRequest);
		return pagable.iterator().next();
	}
}
