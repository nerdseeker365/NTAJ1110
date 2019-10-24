package com.nt.jdbc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*SQL> create table EmpAll(eno number(5) primary key,ename varchar2(20),esalary number(10,2),ephoto blob);

SQL> create sequence EID_SEQ start with 1 increment by 1;
*/
import java.util.Scanner;

public class BlobInsertMySQLTest {
   private static final  String  PHOTO_INSERT_QUERY="INSERT INTO EMPALL(ENAME,ESALARY,EPHOTO) VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		String name=null;
		float salary=0.0f;
		String photoPath=null;
		Connection con=null;
		PreparedStatement ps=null;
		File file=null;
		InputStream  is=null;
		long length=0;
		int result=0;
		
	    try{
	    	//read inputs
	    	sc=new Scanner(System.in);
	    	if(sc!=null){
	    		System.out.println("Enter Employee name::");
	    		name=sc.next();
	    		System.out.println("Enter Employee Salary::");
	    		salary=sc.nextFloat();
	    		System.out.println("Enter PhotoPath::");
	    		photoPath=sc.next();
	    	}
	    	//create Stream Locating given PhotoPath file
	    	file=new File(photoPath);
	    	is=new FileInputStream(file);
	    	//get the length of the file
	    	length=file.length();
	    	
	   	//register jdbc driver s/w
	    	Class.forName("com.mysql.jdbc.Driver");
	    	//establish the connection
	    	con=DriverManager.getConnection("jdbc:mysql:///NTAJ1110DB1","root","root");
	    	//create PreparedStatement object
	    	if(con!=null)
	    		ps=con.prepareStatement(PHOTO_INSERT_QUERY);
	    	//set values to query params
	    	if(ps!=null){
	    		ps.setString(1,name);
	    		ps.setFloat(2,salary);
	    		ps.setBinaryStream(3,is,length);
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
			if(is!=null)
				is.close();
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
