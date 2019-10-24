package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTest {

	public static void main(String[] args) {
		Scanner sc=null;
		String name=null,addrs=null;
		int no=0;
		Connection con=null;
		Statement st=null;
		String query=null;
		int count=0;
		//read inputs
		try{
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter student number::");
				no=sc.nextInt(); //gives 101
				System.out.println("Enter student name::");
				name=sc.next(); //gives raja
				name="'"+name+"'"; //gives 'raja'
				System.out.println("Enter student addrs::");
				addrs=sc.next(); //gives hyd
				addrs="'"+addrs+"'"; //gives 'hyd'
			}//if
			
			//register jdbc driver
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			 //create Statement object
			 if(con!=null)
				 st=con.createStatement();
			//prepare Query
			      //  insert into student values(101,'raja','hyd')
			   query=" INSERT INTO STUDENT VALUES("+no+","+name+","+addrs+")";
			   System.out.println(query);
			   //send and execute SQL Query in Db s/w
			   if(st!=null)
				   count=st.executeUpdate(query);
			 
			   //process the result
			   if(count==0)
				   System.out.println("Record not inserted");
			   else
				   System.out.println("Record inserted");
		}//try
		catch(SQLException se){
			se.printStackTrace();
			 System.out.println("Record not inserted");
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
