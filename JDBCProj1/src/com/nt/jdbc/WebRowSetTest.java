package com.nt.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import oracle.jdbc.rowset.OracleWebRowSet;

public class WebRowSetTest {

	public static void main(String[] args) {
		OracleWebRowSet wrowset=null;
		Writer writer=null;
		try{
			//create Rowset object
			wrowset=new OracleWebRowSet();
			//set inputs
			wrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			wrowset.setUsername("system");
			wrowset.setPassword("manager");
			wrowset.setCommand("SELECT * FROM STUDENT");
			//execute Query
			wrowset.execute();
			//process the RowSet
			while(wrowset.next()){
				System.out.println(wrowset.getInt(1)+"  "+wrowset.getString(2)+" "+wrowset.getString(3));
			}
			//locate the file
			writer=new FileWriter("E:/student.xml");
			wrowset.writeXml(writer);
			System.out.println("Written to the file");
			
		}//try
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//close jdbc objs
			try{
				if(wrowset!=null)
					wrowset.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(writer!=null)
					writer.close();
			}
			catch(IOException se){
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
