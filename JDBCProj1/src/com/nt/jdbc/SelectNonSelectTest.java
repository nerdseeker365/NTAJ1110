package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectNonSelectTest {

	public static void main(String[] args) {
		Scanner sc=null;
		String query=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		boolean flag=false,isRsEmpty=true;
		
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter SQL Query(select or non-select)");
				query=sc.nextLine();
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create Statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query in DB s/w
			if(st!=null)
				flag=st.execute(query);
			
			if(flag){
				System.out.println("Select SQL Query executed");
				//get ResultSet obj
				rs=st.getResultSet();
				//process the ResultSet obj
				if(rs!=null){
					while(rs.next()){
						isRsEmpty=false;
						System.out.println(rs.getString(1)+"  "+rs.getString(2)+" "+rs.getString(3));
					}
					if(isRsEmpty)
						System.out.println("Records not found");
				}//if
				
			}//if
			else{
				System.out.println("non-select SQL Query executed");
				count=st.getUpdateCount();
				System.out.println("no.of records that effected::"+count);
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
			//close jdbc objs
			try{
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			
			try{
				if(st!=null)
					st.close();
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
				if(sc!=null)
					sc.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
