package eccrm.utils.date;

import java.util.Calendar;
import java.util.Date;

/**
 * @author miles
 * @datetime 2014/3/21 13:16
 * 默认情况下所有的时间都会去掉毫秒数
 */
public class DateUtils {

    /**
     * 获得当前时间的凌晨0点
     *
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date) {
        Calendar c = get(date);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return c.getTime();
    }

    /**
     * 获得当前时间的下一天的凌晨0点
     *
     * @param date
     * @return
     */
    public static Date getNextDayBegin(Date date) {
        Calendar c = get(date);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 24, 0, 0);
        return c.getTime();
    }

    /**
     * 获得第二天的这个时间
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar c = get(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * 获得一个指定年月日，时间为0:0:0的时间对象
     * 注意：月份是从0开始的
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static Date getDate(int year, int month, int dayOfMonth) {
        Calendar c = get(new Date());
        c.set(year, month, dayOfMonth, 0, 0, 0);
        return c.getTime();
    }

    /**
     * 获得日期为当前年月日，时间为指定时间的时间对象
     *
     * @return
     */
    public static Date getDate(Date date, int hour, int minute, int seconds) {
        Calendar c = get(date);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hour, minute, seconds);
        return c.getTime();
    }


    /**
     * 根据时间获得指定的Calendar并去掉毫秒数
     *
     * @param date
     * @return
     */
    public static Calendar get(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null!");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    /**
     * 获得两个时间的时间间隔
     *
     * @param start
     * @param end
     * @return
     */
    public static long duration(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能为空!");
        }
        return Math.abs(start.getTime() - end.getTime());
    }
}
