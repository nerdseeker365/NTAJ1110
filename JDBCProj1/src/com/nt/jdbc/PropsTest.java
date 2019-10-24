package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsTest {

	public static void main(String[] args) {
	  InputStream is=null;
	  Properties props=null;
		try{
		//Load Properties file 
			is=new FileInputStream("src/com/nt/commons/PersonInfo.properties");
	   //Load Properties file info to java.util.Properties class object
			props=new Properties();
			props.load(is);
		//display data
			System.out.println(props);
			System.out.println("name is::"+props.getProperty("per.name"));
	}//try
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}//main
}//class
