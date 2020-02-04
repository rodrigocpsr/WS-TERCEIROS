package br.com.comven.corews.transacao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfDenatran = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat sdfDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static Date converteData(String date) throws ParseException {
		sdf.setLenient(false);
		return sdf.parse(date);
	}
	
	public static String converteData(Date date) throws ParseException {
		sdf.setLenient(false);
		return sdf.format(date);
	}
	
	public static Date converteDataDenatran(String date) throws ParseException {
		sdfDenatran.setLenient(false);
		return sdfDenatran.parse(date);
	}
	
	public static String converteDataDenatran(Date date) throws ParseException {
		sdfDenatran.setLenient(false);
		return sdfDenatran.format(date);
	}

	public static Date converteDataHora(String date) throws ParseException {
		sdfDataHora.setLenient(false);
		return sdfDataHora.parse(date);
	}
	
	public static String converteDataHora(Date date) throws ParseException {
		sdfDataHora.setLenient(false);
		return sdfDataHora.format(date);
	}
	
	public static Date converteHora(String date) throws ParseException {
		sdfHora.setLenient(false);
		return sdfHora.parse(date);
	}
	
	public static String converteHora(Date date) throws ParseException {
		sdfHora.setLenient(false);
		return sdfHora.format(date);
	}
	
	public static String removeDatefromDateTime(String datetime) throws ParseException {
		
		String hour = datetime.substring(datetime.length() - 8, datetime.length());
		
		return hour;
	}


}
