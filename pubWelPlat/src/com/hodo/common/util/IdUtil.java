package com.hodo.common.util;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IdUtil { 
 
 
    /** The FieldPosition. */ 
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0); 
 
    /** This Format for format the data to special format. */ 
    private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS"); 
 
    /** This Format for format the number to special format. */ 
    private final static NumberFormat numberFormat = new DecimalFormat("0000"); 
 
    /** This int is the sequence number ,the default value is 0. */ 
    private static int seq = 0; 
 
    private static final int MAX = 9999; 
 
    /** 
     * 时间格式生成序列 
     * @return String 
     */ 
    public static synchronized String generateSequenceNo() { 
 
        Calendar rightNow = Calendar.getInstance(); 
 
        StringBuffer sb = new StringBuffer(); 
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION); 
 
        numberFormat.format(seq, sb, HELPER_POSITION); 
 
        if (seq == MAX) { 
            seq = 0; 
        } else { 
            seq++; 
        } 
        return sb.toString(); 
    } 
    /** 
     * 产生固定位数随机数
     * @return String 
     */ 
    public static String genCode(int size,int end) { 
    	String result = "";
    	for(int i=1;i<=size;i++) {
    		int random=(int)(Math.random()*end+1);
    		result=result+String.valueOf(random);
    	}  	
        return result; 
    } 
} 