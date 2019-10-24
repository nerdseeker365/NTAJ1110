package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateTest {

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
				System.out.println("Enter student existing number::");
				no=sc.nextInt(); //gives 101
				System.out.println("Enter new  name for student::");
				sc.nextLine();
				name=sc.nextLine(); //gives king
				name="'"+name+"'"; //gives 'king'
				System.out.println("Enter new  addrs for student ::");
				addrs=sc.nextLine(); //gives cyberabad
				addrs="'"+addrs+"'"; //gives 'cyberabad'
			}//if
			
			//register jdbc driver
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 //establish the connection
			 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			 //create Statement object
			 if(con!=null)
				 st=con.createStatement();
			//prepare Query
			    //update student set sname='king' ,sadd='hyd' where sno=101
			 query="update student set sname="+name+" ,sadd="+addrs+" where sno="+no;
			 System.out.println(query);
			   //send and execute SQL Query in Db s/w
			   if(st!=null)
				   count=st.executeUpdate(query);
			 
			   //process the result
			   if(count==0)
				   System.out.println("Record not updated");
			   else
				   System.out.println("Record updated");
		}//try
		catch(SQLException se){
			se.printStackTrace();
			 System.out.println("Record not updated");
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
