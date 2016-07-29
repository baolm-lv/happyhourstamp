package com.lvmama.vst.hhs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

public class HhsUtils {
	
	public static boolean isNullOrEmpty(String str) {
        if (null == str || str.isEmpty())
            return true;
        return false;
    }
    
    public static <T> Map<String, T> jsonToMap(String str, Class<T> valueClass) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, valueClass);
			return mapper.readValue(str, type);
		} catch (Exception e) {
			return new HashMap<String, T>();
		} 
 
    }
    
    public static <T> T jsonToObject(String str, Class<T> clazz) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return mapper.readValue(str, clazz);
		} catch (Exception e) {
			return null;
		} 
    }
    
    public static <T> List<T> jsonToList(String str, Class<T> elementClass) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, elementClass);
    		return mapper.readValue(str, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
    }
    
    public static String objectToJsonString(Object obj) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {

		}
    	return "{}";
    }
    
    public static String mapToJsonStr(Map<String, Object> map ) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {

		}
    	return "{}";
    }
    
    public static Date stringDateToDate(String dateInString) {
    	String stringDate = "yyyy-MM-dd";
		Date date = null;
		 try {
              date = new SimpleDateFormat(stringDate).parse(dateInString);
         } catch (ParseException e) {
           
         }
		 return date;
    }
    
    public static Date stringDateTimeToDate(String dateAndTimeInString) {
    	String stringDate = "yyyy-MM-dd hh:mm:ss";
		Date date = null;
		 try {
              date = new SimpleDateFormat(stringDate).parse(dateAndTimeInString);
         } catch (ParseException e) {
           
         }
		 return date;
    }
    
    public static String generateStampNo() {
    	int[] count = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] intArray = new int[10];
		intArray[0] = new Random().nextInt(9) + 1;
		count[intArray[0]] = count[intArray[0]] + 1;
		
		for (int i = 1; i < 10; i++) {
			intArray[i] = new Random().nextInt(10);
			while (isSquential(i, intArray) || count[intArray[i]] >= 4) {
				intArray[i] = (intArray[i] + 3) % 10;
			}
			count[intArray[i]] = count[intArray[i]] + 1;
		}
		StringBuilder stampNo = new StringBuilder(10);
		for (int value : intArray) {
			stampNo.append(value);
		}
		return stampNo.toString();
	}

	private static boolean isSquential(int pos, int[] array) {
		if (pos < 3) {
			return false;
		}
		int num = array[pos];
		if (num == array[pos - 1] + 1 && num == array[pos - 2] + 2 && num == array[pos - 3] + 3) {
			return true;
		}

		if (num == array[pos - 1] - 1 && num == array[pos - 2] - 2 && num == array[pos - 3] - 3) {
			return true;
		}
		return false;
	}
	
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		SimpleDateFormat sdf=  new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = sdf.format(beginDate);
		String endDateStr = sdf.format(endDate);
		if (beginDateStr.equals(endDateStr)) {
			lDate.add(beginDate);
			return lDate;
		}
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

}
