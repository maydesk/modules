/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PDFormat {

	
	private static final DateFormat defaultDateAndTimeFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");
	private static final DateFormat defaultDateLongFormat = new SimpleDateFormat("EEEE, MMMM, d, yyyy");
	private static final DateFormat defaultDateOnlyFormat = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat defaultDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	private static final DateFormat defaultMonthOnlyFormat = new SimpleDateFormat("MMMM");
	private static final DateFormat defaultMonthYearFormat = new SimpleDateFormat("MM-yyyy");
	private static final DateFormat defaultTimeOnlyFormat = new SimpleDateFormat("HH:mm");
	
	private static NumberFormat nf2FractionDigits = NumberFormat.getNumberInstance(Locale.GERMAN);
	private static NumberFormat nfInteger = NumberFormat.getIntegerInstance(Locale.GERMAN);
	static {
		nf2FractionDigits.setMaximumFractionDigits(2);
		nf2FractionDigits.setMinimumFractionDigits(2);
	}
	
	public static String format(Object o) {
		if (o == null) {
			return "";
		} else if (o instanceof Integer) {
			return nfInteger.format(((Integer)o).intValue());
		} else if (o instanceof Number) {
			return nf2FractionDigits.format(((Number)o).doubleValue());
		} else if (o instanceof Date) {
			return defaultDateOnlyFormat.format(((Date)o));
		} else if (o instanceof Calendar) {
			Date d = ((Calendar)o).getTime();
			return defaultDateOnlyFormat.format(d);
		}
		return o.toString(); 
	}

	public static String formatLong(Date date) {
		if (date == null) {
			return "";
		}
		return defaultDateAndTimeFormat.format(date);
	}

	public static String formatTimeOnly(Calendar calendar) {
		if (calendar == null) {
			return "";
		}

		return formatTimeOnly(calendar.getTime());
	}
	
	public static String formatTimeOnly(Date date) {
		if (date == null) {
			return "";
		}
		return defaultTimeOnlyFormat.format(date);
	}

	
	public static DateFormat getDefaultDateLongFormat() {
		return defaultDateLongFormat;
	}

	public static DateFormat getDefaultDateOnlyFormat() {
		return defaultDateOnlyFormat;
	}

	public static DateFormat getDefaultDateTimeFormat() {
		return defaultDateTimeFormat;
	}	

	public static DateFormat getDefaultMonthOnlyFormat() {
		return defaultMonthOnlyFormat;
	}

	public static DateFormat getDefaultMonthYearFormat() {
		return defaultMonthYearFormat;
	}

	public static DateFormat getDefaultTimeOnlyFormat() {
		return defaultTimeOnlyFormat;
	}

	public static NumberFormat getNumberFormat(int digits) {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		nf.setMinimumFractionDigits(digits);
		nf.setMaximumFractionDigits(digits);
		return nf;
	}

	public static NumberFormat getNumberFormat0() {
		return nfInteger;
	}

	public static Calendar parseDate(String string) {
		return parseDate(string, getDefaultDateOnlyFormat());
	}
	
	public static Calendar parseDate(String string, DateFormat df) {
		if (PDUtil.isEmpty(string)) {
			return null;
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(string));
			return cal;
		} catch (ParseException e) {
			return null;
		}
	}
}
