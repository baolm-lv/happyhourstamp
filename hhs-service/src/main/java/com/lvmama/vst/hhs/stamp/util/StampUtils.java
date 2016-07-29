/**
 * 
 */
package com.lvmama.vst.hhs.stamp.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.lvmama.vst.comm.utils.DateUtil;
import com.lvmama.vst.hhs.model.admin.StampAssociatedProdAvailTimeSlotVo;
import com.lvmama.vst.hhs.model.common.StampDuration;

/**
 * @author fengyonggang
 *
 */
public class StampUtils {

	private static final String SPLITER_EQUAL = "=";
	
	public static List<StampDuration> translateAvailTimeslot(StampAssociatedProdAvailTimeSlotVo prodAvailTimeSlot) {
		//Json sample: 
		//{"dateType":"selectDate","selectDates":["2016-05-25","2016-05-28","2016-05-29","2016-06-30"],"startDate":"","endDate":"","weekDays":null}
		//{"dateType":"selectTime","selectDates":[],"startDate":"2016-05-16","endDate":"2016-05-31","weekDays":["2","3","4","5","6"]}
		List<String> formatDates = null;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		if("selectDate".equals(prodAvailTimeSlot.getDateType())) {
			formatDates = translateAvailTimeslot(prodAvailTimeSlot.getSelectDates());
		} else {
			formatDates = translateAvailTimeslot(prodAvailTimeSlot.getStartDate(), prodAvailTimeSlot.getEndDate(), prodAvailTimeSlot.getWeekDays());
		}
//		LOGGER.debug("date duration: {}", formatDates);
		if(CollectionUtils.isEmpty(formatDates))
			return null;
		
		List<StampDuration> durations = new ArrayList<StampDuration>();
		for(String s : formatDates) {
			String [] durationStr = s.split(SPLITER_EQUAL);
			try {
				Date startDate = dateformat.parse(durationStr[0]);
				Date endDate = dateformat.parse(durationStr[1]);
				durations.add(new StampDuration(getStartDate(startDate), getEndDate(endDate)));
			} catch (Exception e) {
				throw new RuntimeException("invalid date formart", e);
			}
		}
		Collections.sort(durations, new ComparatorDate() );        
		return durations;
	}
	
	private static List<String> translateAvailTimeslot(String startDate, String endDate, List<String> weeks) {
		if(CollectionUtils.isEmpty(weeks) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate))
			return null;
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> formatDates = new ArrayList<String>();
		String start = null, end = null, cur = startDate;
		Calendar curCalendar = Calendar.getInstance();
		long diffDays;
		try {
			diffDays = DateUtil.diffDay(dateformat.parse(startDate), dateformat.parse(endDate));
			curCalendar.setTime(dateformat.parse(cur));
		} catch (ParseException e) {
			throw new RuntimeException("invalid date formart : " + cur);
		}
		if(diffDays == 0) {
			formatDates.add(startDate + SPLITER_EQUAL + endDate);
		} else {
			if(weeks.contains(String.valueOf(curCalendar.get(Calendar.DAY_OF_WEEK)))) {
				start = cur;
			}
			for(int i=0;i<diffDays+1; i++) {
			    if(i==0){
			       // curCalendar.add(Calendar.DATE, 1); 
			    }else{
			        curCalendar.add(Calendar.DATE, 1); 
			    }				
				cur = dateformat.format(curCalendar.getTime());
				
				if(weeks.contains(String.valueOf(curCalendar.get(Calendar.DAY_OF_WEEK)))) {
					if(start == null) 
						start = cur;
					end = cur;
				} else {
					if(start != null && end != null) 
						formatDates.add(start + SPLITER_EQUAL + end);
					start = null;
					end = null;
				}
			}
			if(weeks.contains(String.valueOf(curCalendar.get(Calendar.DAY_OF_WEEK)))) 
				end = cur;
			if(start != null && end != null) 
				formatDates.add(start + SPLITER_EQUAL + end);
		}
		return formatDates;
	}
	
	private static List<String> translateAvailTimeslot(List<String> selectDates) {
		if(selectDates == null || selectDates.size() == 0) {
			return null;
		}
		String start = null, end = null, cur = null, tmp = null;
		Calendar curCalendar = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> formatDates = new ArrayList<String>();
		for (String date : selectDates) {
			cur = date;
			if(start == null) 
				start = date;
			if(end == null) 
				end = date;
			if(cur.equals(start)) 
				continue;
			try {
				curCalendar.setTime(dateformat.parse(cur));
			} catch (ParseException e) {
				throw new RuntimeException("invalid date formart : " + cur, e);
			}
			curCalendar.add(Calendar.DATE, -1);
			tmp = dateformat.format(curCalendar.getTime());
			if(end.equals(tmp)) {
				end = date;
				continue;
			} 
			formatDates.add(start + SPLITER_EQUAL + end);
			start = cur;
			end = cur;
		}
		formatDates.add(start + SPLITER_EQUAL + end);
		return formatDates;
	}
	
	public static String[] parseHotelDays(String stayDays, Date visitTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null, end = null;
		if(StringUtils.isEmpty(stayDays)) {
			start = visitTime;
			end = addDay(start, 1);
		} else {
			if(stayDays.indexOf(",") > 0) {
				String [] days = stayDays.split(",");
				int s = NumberUtils.toInt(days[0]);
				if(s > 1) 
					start = addDay(visitTime, s - 1);
				else 
					start = visitTime;
				end = addDay(start, days.length);
			} else {
				int day = NumberUtils.toInt(stayDays);
				if(day > 1) 
					start = addDay(visitTime, day - 1);
				else
					start = visitTime;
				end = addDay(start, 1);
			}
		}
		return new String[]{format.format(start), format.format(end)};
	}
	
	public static Date addDay(Date d, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	
	public static Date getStartDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static Date getEndDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	} 
	
	public static boolean checkDuration(List<StampDuration> durations, Date date) {
		if(CollectionUtils.isEmpty(durations) || date == null)
			return false;
		for(StampDuration duration : durations) {
			if(date.getTime()>=duration.getStartDate().getTime() && date.getTime()<=duration.getEndDate().getTime()) 
				return true;
		}
		return false;
	}
	
	public static List<BigDecimal> LongCollectionToBigDecimal(Collection<Long> col) {
		if(col == null)
			return null;
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		for(Long lo : col) {
			list.add(new BigDecimal(lo));
		}
		return list;
	}
	
	public static List<StampDuration> getIntersection(List<StampDuration> durations1, List<StampDuration> durations2) {
		if(CollectionUtils.isEmpty(durations1) || CollectionUtils.isEmpty(durations2)) {
			return null;
		}
		List<StampDuration> durations = new ArrayList<StampDuration>();
		for(StampDuration dur1 : durations1) {
			for(StampDuration dur2 : durations2) {
				if(dur2.getStartDate().getTime()<=dur1.getEndDate().getTime() && dur2.getEndDate().getTime()>=dur1.getStartDate().getTime()) {
					Date start = dur2.getStartDate().getTime()<=dur1.getStartDate().getTime() ? dur1.getStartDate() : dur2.getStartDate();
					Date end = dur2.getEndDate().getTime()>=dur1.getEndDate().getTime() ? dur1.getEndDate() : dur2.getEndDate();
					durations.add(new StampDuration(start, end));
				}
			}
		}
		return durations;
	}
	
	public static class ComparatorDate implements Comparator<StampDuration>{  
        public int compare(StampDuration d0, StampDuration d1) {  
            int thislv = d1.getStartDate().compareTo(d1.getStartDate());  
            if (thislv == 0) {  
                return d0.getEndDate().compareTo(d1.getEndDate());  
            } else {  
                return thislv;  
            }  
        }  
	}
	
	public static List<String> parseVisitDate(String startDays, Date startTime) {
		if(StringUtils.isBlank(startDays)) {
			return null;
		}
		String [] dayArray = startDays.split(",");
		int day = 0;
		List<String> dates = new ArrayList<String>();
		for(String d : dayArray) {
			if((day = NumberUtils.toInt(d)) == 1) {
				dates.add(DateUtil.formatSimpleDate(startTime));
			} else {
				dates.add(DateUtil.formatSimpleDate(StampUtils.addDay(startTime, day - 1)));
			}
		}
		return dates;
	}
	
	public static List<String> getVisitDates(int day) {
		if(day < 0) 
			return null;
		List<String> dates = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<=day;i++) {
			dates.add(format.format(c.getTime()));
			c.add(Calendar.DATE, 1);
		}
		return dates;
	}
	
}
