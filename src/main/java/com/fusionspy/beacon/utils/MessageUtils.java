package com.fusionspy.beacon.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Properties;

import com.fusionspy.beacon.common.Trend;
import com.fusionspy.beacon.threshold.model.SeverityLevel;

/**
 * 格式化前台输出信息
 * @author bao
 *
 */
public class MessageUtils {
	
	public final static String MESSAGE_FORMAT_A = "message_format_a";
	public final static String MESSAGE_FORM_A_SUBTITLE = "message_form_a_subtitle";
	public final static String MESSAGE_FORMAT_DIV = "message_format_div";
	public final static String MESSAGE_FORMAT_SPAN = "message_format_span";
	public final static String HANDLER_FORMAT = "handler_format";

    public final static String MESSAGE_INFO_DIV = "message_info_div";
	
	private final static Properties pops = new Properties();
	static {
		try {
			Reader reader = new InputStreamReader(MessageUtils.class.getClassLoader().getResourceAsStream("message.properties"));
			pops.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 格式化信息输出
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	public static String formateMessage(String pattern, Object... arguments) {
		return MessageFormat.format(pops.getProperty(pattern), arguments);
	}

	/**
	 * 可用性前台样式转换
	 * @param available
	 * @return
	 */
	public static String trend2CssClass(Trend available) {
		String usabilityClass = null;
		switch (available) {
		case RISE:
			usabilityClass = "sys_up";
			break;
		case SAME:
			usabilityClass = "sys_normal";
			break;
		case DROP:
			usabilityClass = "sys_down";
			break;
		default:
			usabilityClass = "sys_up";
			break;
		}
		return usabilityClass;
	}
	
	/**
	 * 健康性前台样式转换
	 * @param health
	 * @return
	 */
	public static String SeverityLevel2CssClass(SeverityLevel health) {
		String healthClass = "";
		switch (health) {
		case INFO:
			healthClass = "fine";
			break;
		/*case WARNING:
		
			break;
		case CRITICAL:
			break;*/
		default:
			healthClass = "poor";
			break;
		}
		return healthClass;
	}

    public static String available2CssClass(int isAvailable){
        if(isAvailable == 1)
            return "fine";
        else if(isAvailable == -1)
            return "poor";
        else
            return "unknown";
    }
	
	public static String trend2CssClass(String strTrend) {
		Trend trend = Trend.valueOf(strTrend);
		return trend2CssClass(trend);
	}
}
