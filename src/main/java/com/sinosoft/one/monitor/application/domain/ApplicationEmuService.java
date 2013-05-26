package com.sinosoft.one.monitor.application.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.application.model.EumUrl;
import com.sinosoft.one.monitor.application.model.EumUrlAva;
import com.sinosoft.one.monitor.application.model.EumUrlAvaSta;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlAvaStaRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlRepository;
import com.sinosoft.one.monitor.common.Trend;
import com.sinosoft.one.monitor.utils.AvailableCalculate;

/**
 * 仿真URL服务对象
 * User: cq
 * Date: 13-3-6
 * Time: AM11:37
 */
@Service
public class ApplicationEmuService {



    @Autowired
    private EumUrlRepository eumUrlRepository;

    @Autowired
    private EumUrlAvaRepository eumUrlAvaRepository;

    @Autowired
    private EumUrlAvaStaRepository eumUrlAvaStaRepository;


    /**
     * 查询当天的仿真URL统计数据，如果没有会默认返回初始化的仿真URL统计数据
     * @param eumUrlId
     * @return
     */
    public EumUrlAvaSta getTodayEumUrlStatistics(String eumUrlId){
        return getEumUrlStatisticsByEnumIdAndDate(eumUrlId,LocalDate.now().toDate());
    }

    EumUrlAvaSta getEumUrlStatisticsByEnumIdAndDate(String eumUrlId,Date date){
        List<EumUrlAvaSta> eumUrlAvaStas = eumUrlAvaStaRepository.findByRecordTimeAndEumUrlId(date,eumUrlId);
        return eumUrlAvaStas.isEmpty()?newEumUrlAvaSta():eumUrlAvaStas.get(0);
    }

    public EumUrlAva getTodayLatestEumUrlAva(String eumUrlId){
        Assert.hasText(eumUrlId);
        Sort desc = new Sort(Sort.Direction.DESC,"recordTime");
        Pageable pageDesc = new PageRequest(0,1,desc);
        List<EumUrlAva> eumUrlAvas = eumUrlAvaRepository.findByEumUrlId(eumUrlId, pageDesc).getContent();
        if(eumUrlAvas.isEmpty())
            return  null;
        return eumUrlAvas.get(0);
    }

    public EumUrlAva getEumUrlAvaTodayAndLatestAndUnavailable(String eumUrlId){
        Assert.hasText(eumUrlId);
        Sort desc = new Sort(Sort.Direction.DESC,"recordTime");
        Pageable pageDesc = new PageRequest(0,1,desc);
        List<EumUrlAva> eumUrlAvas = eumUrlAvaRepository.findByEumUrlIdAndState(eumUrlId,"0", pageDesc).getContent();
        if(eumUrlAvas.isEmpty())
            return  null;
        return eumUrlAvas.get(0);
    }

    public EumUrlAva getTodayFirstEumUrlAva(String eumUrlId){
        Sort asc = new Sort("recordTime");
        Pageable pageAsc = new PageRequest(0,1,asc);
        List<EumUrlAva> eumUrlAvas =eumUrlAvaRepository.findByEumUrlId(eumUrlId, pageAsc).getContent();
        if(eumUrlAvas.isEmpty())
            return null;
        return eumUrlAvas.get(0);
    }

    private EumUrlAvaSta newEumUrlAvaSta(){
        EumUrlAvaSta eumUrlAvaSta = new EumUrlAvaSta();
        eumUrlAvaSta.setRecordTime(new Date());
        eumUrlAvaSta.setNormalRuntime(BigDecimal.ZERO);
        eumUrlAvaSta.setTotalFailureTime(BigDecimal.ZERO);
        eumUrlAvaSta.setFailureCount(BigDecimal.ZERO);
        return eumUrlAvaSta;
    }

    public ApplicationAvailableInf getApplicationAvailableToday(String applicationId) throws EumUrlsNotFoundException {
        Assert.hasText(applicationId);

        List<EumUrl> eumUrls = eumUrlRepository.findByApplication_Id(applicationId);
        if(eumUrls.isEmpty())
            throw new EumUrlsNotFoundException("application Id is "+applicationId+" not found any eumUrls!");
        int  count = 0;
        int  avCount = 0;
        for(EumUrl eumUrl:eumUrls){
            UrlAvailableInf urlAvailableInf =  getUrlAvailableToday(eumUrl.getUrlId());
            count+=urlAvailableInf.getCount();
            avCount+=urlAvailableInf.getAvailableCount();
        }
        return new  ApplicationAvailableInf(calTrend(eumUrls),count,avCount);
    }



    public Trend urlAvaTrendByUrlId(String urlId){
        EumUrl eumUrl = getEumUrlByUrlId(urlId);
        return calTrend(Lists.newArrayList(eumUrl));
    }


    public UrlAvailableInf getUrlAvailableToday(String urlId){
        EumUrl eumUrl = getEumUrlByUrlId(urlId);
	    EumUrlAva eumUrlAva = getTodayFirstEumUrlAva(eumUrl.getId());
        Interval interval = new Interval(new DateTime(eumUrlAva == null ? LocalDate.now().toDate(): eumUrlAva.getRecordTime()), DateTime.now());

	    eumUrlAva = getEumUrlAvaTodayAndLatestAndUnavailable(eumUrl.getId());
        return new UrlAvailableInf(urlAvaTrendByUrlId(urlId),
                eumUrlAvaRepository.countByEmuId(eumUrl.getId()),
                eumUrlAvaRepository.countByEmuIdAndStatus(eumUrl.getId(),"1"),
                interval.toPeriod(), eumUrlAva == null ? null : eumUrlAva.getRecordTime());

    }

    EumUrl getEumUrlByUrlId(String urlId) {
        Assert.hasText(urlId);
        List<EumUrl> eumUrls = eumUrlRepository.findByUrlId(urlId);
        if (eumUrls.isEmpty())
            throw new IllegalArgumentException("urlId is " + urlId + ",can't find eumUrl");
        if (eumUrls.size() > 1)
            throw new MutileEumUrlException();
        return eumUrls.get(0);
    }


    List<TimeQuantumAvailableInfo> findAvailableStatisticsByUrlId(String urlId) {
        Assert.hasText(urlId);
        DateTime now = DateTime.now().withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        DateTime prev = now.minusHours(6);
        List<TimeQuantumAvailableStatistics>  list = eumUrlAvaRepository.statisticsByEumUrlIdAndRecordTime(getEumUrlByUrlId(urlId).getId(),prev.toDate(),now.toDate());
        Map<String,TimeQuantumAvailableInfo> map = Maps.newHashMap();
        for(TimeQuantumAvailableStatistics statistics:list){
            TimeQuantumAvailableInfo timeQuantumAvailableInfo = null;
            if(map.get(statistics.getTimeQuantum())==null){
                 timeQuantumAvailableInfo = new TimeQuantumAvailableInfo();
                if(statistics.getStatus().equals("1")){
                    timeQuantumAvailableInfo.setAvaCount(statistics.getCount());
                }
                timeQuantumAvailableInfo.setCount(statistics.getCount());
	            timeQuantumAvailableInfo.setTimeQuantum(statistics.getTimeQuantum());
                map.put(statistics.getTimeQuantum(),timeQuantumAvailableInfo);
            }else{
                timeQuantumAvailableInfo =map.get(statistics.getTimeQuantum());
                if(statistics.getStatus().equals("1")){
                    timeQuantumAvailableInfo.setAvaCount(timeQuantumAvailableInfo.getAvaCount() + statistics.getCount());
                }
                int count = timeQuantumAvailableInfo.getCount()+statistics.getCount();
                timeQuantumAvailableInfo.setCount(count);
                timeQuantumAvailableInfo.setTimeQuantum(statistics.getTimeQuantum());
            }
        }
        return Lists.newArrayList(map.values());
    }


    void saveEnumUrlAvailableDetail(String eumUrlId,boolean result,BigDecimal interval){
        deleteEnumUrlAvaData(eumUrlId);
        EumUrlAva eumUrlAva = new EumUrlAva();
        eumUrlAva.setEumUrlId(eumUrlId);
        eumUrlAva.setInterval(interval);
        eumUrlAva.setRecordTime(DateTime.now().toDate());
        eumUrlAva.setState(result?"1":"0");
        eumUrlAvaRepository.save(eumUrlAva);
    }

    void deleteEnumUrlAvaData(String eumUrlId){
        EumUrlAva eumAvaLast = getTodayLatestEumUrlAva(eumUrlId);
        if(eumAvaLast!=null){
            LocalDate prevDate = new LocalDate(eumAvaLast.getRecordTime());
            if(prevDate.compareTo(LocalDate.now())<0){
                eumUrlAvaRepository.deleteByLessThanDate(LocalDate.now().minusDays(1).toDate());
            }
        }
    }

    List<EumUrl> findEumUrlByApplicationId(String applicationId){
        return  eumUrlRepository.findByApplication_Id(applicationId);
    }


    private Trend calTrend(List<EumUrl> eumUrls ){
        int yesterdayCount=0;
        int todayCount=0;
        Date yesterday = LocalDate.now().minusDays(1).toDate();
        for(EumUrl eumUrl:eumUrls){
            todayCount += getTodayEumUrlStatistics(eumUrl.getId()).getTotalFailureTime().intValue();
            yesterdayCount+= getEumUrlStatisticsByEnumIdAndDate(eumUrl.getId(),yesterday).getTotalFailureTime().intValue();
        }
        if(yesterdayCount>todayCount){
            return Trend.RISE;
        }
        else if(yesterdayCount<todayCount){
            return Trend.DROP;
        }
        else
            return Trend.SAME;
    }


    public void saveEnumUrlAvailableStatistics(String eumUrlId, EumUrlAvaSta eumUrlAvaSta, boolean result,BigDecimal interval) {
        Assert.hasText(eumUrlId);
        Assert.notNull(interval);

        List<AvailableCalculate.AvailableCountsGroupByInterval> avaCount = eumUrlAvaRepository.countsGroupByInterval(eumUrlId,"1");
        List<AvailableCalculate.AvailableCountsGroupByInterval> unAvaCount = eumUrlAvaRepository.countsGroupByInterval(eumUrlId,"0");
        EumUrlAva eumAvaLast = getTodayLatestEumUrlAva(eumUrlId);

        AvailableCalculate.AvailableCalculateParam availableCalculateParam =  new AvailableCalculate.AvailableCalculateParam(
                new AvailableCalculate.AvailableStatistics( eumUrlAvaSta.getNormalRuntime().longValue(),
                        eumUrlAvaSta.getTotalFailureTime().longValue(), eumUrlAvaSta.getFailureCount().intValue()),
                avaCount,unAvaCount,interval.intValue(),result,
                eumAvaLast == null?null:new AvailableCalculate.AvailableInf(
                        eumAvaLast.getRecordTime(), eumAvaLast.getState().equals("1"),
                        eumAvaLast.getInterval().intValue())
        );
        AvailableCalculate avaResult = AvailableCalculate.calculate(availableCalculateParam);

        eumUrlAvaSta.setTotalFailureTime(avaResult.getStopTime());
        eumUrlAvaSta.setFailureCount(avaResult.getFalseCount());
        eumUrlAvaSta.setAvgFailureTime(avaResult.getTimeBetweenFailures());
        eumUrlAvaSta.setNormalRuntime(avaResult.getAliveTime());
        eumUrlAvaSta.setEumUrlId(eumUrlId);

        eumUrlAvaStaRepository.save(eumUrlAvaSta);
    }

}
