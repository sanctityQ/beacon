package com.sinosoft.one.monitor.os.linux.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.one.monitor.common.AlarmMessageBuilder;
import com.sinosoft.one.monitor.common.AlarmSource;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.common.MessageBase;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
import com.sinosoft.one.monitor.os.linux.model.OsRam;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;

@Component
public class OsProcessService {

    private Logger logger = LoggerFactory.getLogger(OsProcessService.class);
    @Autowired
	private OsCpuService osCpuService;
	@Autowired
	private OsDiskService osDiskService;
	@Autowired
	private OsRamService osRamService;
	@Autowired
	private OsRespondTimeService osRespondTimeService;
	@Autowired
	private OsAvailableServcie OsAvailableServcie;
	@Autowired
	private OsDataMathService osDataMathService;
	@Autowired
	private OsAvailableServcie osAvailableServcie;
	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;
	/**
	 * 保存采样信息  //并更新统计记录
	 * @param cpuInfo 采集的CPU信息字符串
	 * @param ramInfo 采集的内存信息字符串
	 * @param diskInfo 采集的磁盘信息字符串
	 * @param respondTime 采集的响应信息字符串
	 * @param sampleTime 采集时间
	 */
	public void saveSampleData(String osInfoId,String cpuInfo,String ramInfo,String diskInfo,String respondTime ,Date sampleTime){
        logger.debug("cpu info:{}",cpuInfo);
        logger.debug("os disk:{}",diskInfo);
        logger.debug("os ram:{}",ramInfo);
		Calendar c  = Calendar.getInstance();
		////获取当前时间的小时数 取整时点
		c.setTime(sampleTime);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date hourPoint=c.getTime();
		MessageBase messageBase = alarmMessageBuilder.newMessageBase(osInfoId);
		OsCpu osCpu=OsTransUtil.getCpuInfo(cpuInfo);
		osCpuService.saveCpu(osInfoId,osCpu ,sampleTime);//保存CPU采样
		messageBase.addAlarmAttribute(AttributeName.CPUUtilization, osCpu.getUtiliZation());
		
		List<OsDisk> osDisks=OsTransUtil.getDiskInfo(diskInfo);


		osDiskService.saveDisk(osInfoId,osDisks, sampleTime);//保存磁盘采样
		messageBase.addAlarmAttribute(AttributeName.DiskUtilization, osDisks.get(0).getTotalUtiliZation());
		
 		OsRam osRam=OsTransUtil.getRamInfo(ramInfo);


		osRamService.saveRam(osInfoId,osRam , sampleTime);//保存内存采样
		messageBase.addAlarmAttribute(AttributeName.PhysicalMemoryUtilization, osRam.getMemUtiliZation());
		messageBase.addAlarmAttribute(AttributeName.SwapMemoryUtilization, osRam.getSwapUtiliZation());
		
		osRespondTimeService.saveRespondTime(osInfoId,respondTime , sampleTime);//保存响应时间采样
		messageBase.addAlarmAttribute(AttributeName.ResponseTime, respondTime);
		
		messageBase.alarmSource(AlarmSource.OS).alarm();
		//更新统计记录
		osDataMathService.statiOneHourRam(osInfoId, sampleTime,hourPoint);//更新内存统计
		osDataMathService.statiOneHourCpu(osInfoId, sampleTime,hourPoint);//更新CPU统计
		osDataMathService.statiOneHourDisk(osInfoId, sampleTime,hourPoint);//更行磁盘统计
		osDataMathService.statiOneHourRespond(osInfoId, sampleTime,hourPoint);//更行响应时间统计
		Calendar c2  = Calendar.getInstance();
		c2.set(Calendar.DATE, sampleTime.getDate());
		c2.add(Calendar.HOUR_OF_DAY,-24);
		Date deleteTime= c2.getTime();
		//删除24小时前的临时数据
		osCpuService.deleteCpuByLessThanTime(osInfoId, deleteTime);
		osDiskService.deleteDiskByLessThanTime(osInfoId, deleteTime);
		osRamService.deleteRamByLessThanTime(osInfoId, deleteTime);
		osRespondTimeService.deleteResponTimekByLessThanTime(osInfoId, deleteTime);
	}
	
	/**
	 * 轮询任务 保存可用性采样 //并更新统计记录
	 * @param Status
	 */
	@Transactional
	public void savaAvailableSampleData(String osInfoId,Date sampleTime,int interCycleTime ,String Status){
		OsAvailabletemp osAvailabletemp=new OsAvailabletemp();
		Os os=new Os();
		os.setOsInfoId(osInfoId);
		osAvailabletemp.setOs(os);
		osAvailabletemp.setSampleDate(sampleTime);
		osAvailabletemp.setStatus(Status);	
		osAvailabletemp.setIntercycleTime(interCycleTime);
		//统计采样结果 今天
		Calendar c  = Calendar.getInstance();
		c.setTime( sampleTime);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		//取当天的前24小时整时点
		Date todayzeroTime= c.getTime();
		//修改今天的统计表记录
		osDataMathService.statiAvailable(osInfoId, sampleTime, todayzeroTime, interCycleTime, todayzeroTime,osAvailabletemp);//保存新统计记录
		//保存本次采样
		osAvailableServcie.saveAvailableTemp(osAvailabletemp);
		//删除24小时前的数据
		Calendar c2  = Calendar.getInstance();
		c2.setTime( sampleTime);
		c2.add(Calendar.HOUR_OF_DAY,-24);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		Date deleteTime= c2.getTime();
		osAvailableServcie.deleteTempByLessThanTime(osInfoId, deleteTime);
	}
	
	/**
	 * 获取最后一次轮询时间
	 * @param osInfoId
	 * @return
	 */
	public OsAvailabletemp getLastSampleTime(String osInfoId ,Date currentTime){
		//获取最后一次可用性记录
 		OsAvailabletemp osAvailabletemp=OsAvailableServcie.getLastAvailable(osInfoId, currentTime);
		return osAvailabletemp;
	}
	
}
