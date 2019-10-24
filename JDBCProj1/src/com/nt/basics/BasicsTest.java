package com.nt.basics;

import java.text.SimpleDateFormat;

public class BasicsTest {

	public static void main(String[] args) throws Exception{
		
		//Converting String date value to java.util.Date class obj
		String sd1="15-08-1997";  // dd-MM-yyyy
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date ud1=sdf.parse(sd1);
		System.out.println(ud1);
		
		//Converting java.util.Date class obj to java.sql.Date class obj
		long ms=ud1.getTime();  //Epoach Standard
		System.out.println(ms);
		java.sql.Date sqd1=new java.sql.Date(ms);
		System.out.println(sqd1);
		//if String date value is there in yyyy-MM-dd pattern it can be
		//directly converted in java.sql.Date class object
		String sd2="2000-10-20"; //yyyy-MM-dd
		java.sql.Date sqd2=java.sql.Date.valueOf(sd2);
		System.out.println(sqd2);
		//Convert java.sql.Date class obj to  java.util.Date class obj
		java.util.Date ud2=(java.util.Date)sqd2;
		System.out.println("Util date::"+ud2);
		//Converting java.util.Date class obj to  String date value
		SimpleDateFormat sdf2=new SimpleDateFormat("dd-yyyy-MM");
		String sd3=sdf2.format(ud2);
		System.out.println("String date::"+sd3);
		
	}

}
