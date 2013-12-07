package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.StatisticClean;
import com.fusionspy.beacon.site.tux.dao.TuxQueueStatsDao;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.fusionspy.beacon.site.tux.dao.TuxSvrsStatsDao;
import com.fusionspy.beacon.site.tux.dao.TuxcltsStatsDao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TuxStatisticClean implements StatisticClean{

    @Autowired
    private TuxcltsStatsDao cltsStatsDao;

    @Autowired
    private TuxResourceDao resourceDao;

    @Autowired
    private TuxQueueStatsDao queueStatsDao;

    @Autowired
    private TuxSvrsStatsDao svrsStatsDao;

    @Override
    public void clean() {

        Timestamp date = new Timestamp(getCleanupDate().getMillis());
        cltsStatsDao.deleteByRectimeBefore(date);
        resourceDao.deleteByRectimeBefore(date);
        queueStatsDao.deleteByRectimeBefore(date);
        svrsStatsDao.deleteByRecTimeBefore(date);

    }

    DateTime getCleanupDate(){
        return DateTime.now().withTimeAtStartOfDay();
    }
}
