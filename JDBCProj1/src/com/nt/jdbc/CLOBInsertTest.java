package com.nt.jdbc;
/*SQL> create table studentAll(sno number(5)primary key,sname varchar2(20),sadd varchar2(20),resume clob); 
  SQL> create sequence sno_seq start with 1 increment by 1;
 * */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CLOBInsertTest {
  private static final  String  RESUME_INSERT_QUERY="INSERT INTO STUDENTALL VALUES(SNO_SEQ.NEXTVAL,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String name=null,addrs=null;
		String resumePath=null;
		Connection con=null;
		PreparedStatement ps=null;
		File file=null;
		Reader reader=null;
		long length=0;
		int result=0;
		
	    try{
	    	//read inputs
	    	sc=new Scanner(System.in);
	    	if(sc!=null){
	    		System.out.println("Enter Student name::");
	    		name=sc.next();
	    		System.out.println("Enter Student  addrs::");
	    		addrs=sc.next();
	    		System.out.println("Enter resume Path::");
	    		resumePath=sc.next();
	    	}
	    	//create Stream Locating given PhotoPath file
	    	file=new File(resumePath);
	    	reader=new FileReader(file);
	    	//get the length of the file
	    	length=file.length();
	    	
	    	//register jdbc driver s/w
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	//establish the connection
	    	con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
	    	//create PreparedStatement object
	    	if(con!=null)
	    		ps=con.prepareStatement(RESUME_INSERT_QUERY);
	    	//set values to query params
	    	if(ps!=null){
	    		ps.setString(1,name);
	    		ps.setString(2,addrs);
	    		ps.setCharacterStream(3,reader,length);
	    	}
	    	//execute the Query
	    	if(ps!=null)
	    		result=ps.executeUpdate();
	    	//process the result
	    	if(result==0)
	    		System.out.println("Record not inserted");
	    	else
	    		System.out.println("Record  inserted");
	    }//try
	    catch(SQLException se){
	    	se.printStackTrace();
	    }
	    catch(ClassNotFoundException cnf){
	    	cnf.printStackTrace();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally{
		try{
			if(ps!=null)
				ps.close();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		
		try{
			if(con!=null)
				con.close();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		
		try{
			if(reader!=null)
				reader.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		try{
			if(sc!=null)
				sc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}//finally

	}//main
}//class
