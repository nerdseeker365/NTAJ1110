package com.nt.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CLOBRetrieveTest {
     private static final String  CLOB_RETRIEVE_QUERY="SELECT SNO,SNAME,SADD,RESUME FROM STUDENTALL WHERE SNO=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String name=null,sadd=null;
		Reader reader=null;
		Writer writer=null;
		char[] buffer=null;
		int charsRead=0;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("enter student number::");
				no=sc.nextInt();
			}//if
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement obj
			if(con!=null)
				ps=con.prepareStatement(CLOB_RETRIEVE_QUERY);
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
				sadd=rs.getString(3);
				System.out.println(no+"   "+name+"  "+sadd);
				reader=rs.getCharacterStream(4);
			}else{
				System.out.println("Record not found");
			}
			//create OutputStream pointing to dest file
			writer=new FileWriter("newResume.txt");
			//write streams based logic using buffer
			if(reader!=null && writer!=null){
				buffer=new char[1024];
				while((charsRead=reader.read(buffer))!=-1){
					writer.write(buffer, 0, charsRead);
				}
				System.out.println("CLOB value retrieved");
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
			if(reader!=null)
				reader.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		try{
			if(writer!=null)
				writer.close();
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
