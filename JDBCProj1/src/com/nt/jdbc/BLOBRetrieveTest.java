package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BLOBRetrieveTest {
     private static final String  BLOB_RETRIEVE_QUERY="SELECT ENO,ENAME,ESALARY,EPHOTO FROM EMPALL WHERE ENO=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String name=null;
		float salary=0.0f;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=null;
		int bytesRead=0;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("enter employee number::");
				no=sc.nextInt();
			}//if
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement obj
			if(con!=null)
				ps=con.prepareStatement(BLOB_RETRIEVE_QUERY);
			//set value to query params
			if(ps!=null)
				ps.setInt(1,no);
			//execute the Query
			if(ps!=null)
			   rs=ps.executeQuery();
			//process the ResultSet
			if(rs.next()){
				no=rs.getInt(1);
				name=rs.getString(2);
				salary=rs.getFloat(3);
				System.out.println(no+"   "+name+"  "+salary);
				is=rs.getBinaryStream(4);
			}
			//create OutputStream pointing to dest file
			os=new FileOutputStream("newDisha.jpg");
			//write streams based logic using buffer
			if(is!=null && os!=null){
				buffer=new byte[4096];
				while((bytesRead=is.read(buffer))!=-1){
					os.write(buffer, 0, bytesRead);
				}
				System.out.println("BLOB value retrieved");
			}
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
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
			if(os!=null)
				os.close();
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
