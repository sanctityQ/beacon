package com.fusionspy.beacon.site;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

/**
 * tux alarm message format
 * User: qc
 * Date: 11-9-21
 * Time: 下午5:18
 */
@BaseName("alarm")
@LocaleData( { @Locale("en"), @Locale(value="zh_CN",charset="GBK") })
public enum AlarmMessageFormat {
    START,
    END,
    TUX_DIED,
    TUX_NOTRAN,
    TUX_BUSY,
    TUX_QUE,
    TUX_MEM,
    TUX_CPU,
    TUX_STOP,
    WLS_DIED,
    WLS_JDBC,
    WLS_HEAP,
    WLS_THREAD,
    WLS_CPU,
    WLS_STOP;
}

