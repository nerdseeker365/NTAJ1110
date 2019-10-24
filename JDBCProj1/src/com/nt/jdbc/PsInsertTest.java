package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PsInsertTest {
	private static final  String  STUD_INSERT_QUERY="INSERT INTO STUDENT VALUES(?,?,?)";

	public static void main(String[] args) {
		//read inputs
		Scanner sc=null;
		int count=0;
		Connection con=null;
		PreparedStatement ps=null;
		int no=0;
		String name=null,addrs=null;
		int result=0;
		try{
			sc=new Scanner(System.in);
		if(sc!=null){
			System.out.println("Enter students Count::");
			count=sc.nextInt();
		  }
		//register JDBc driver
		   //Class.forName("oracle.jdbc.driver.OracleDriver");
		  //establish the connection
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		//Send SQL Query Db s/ws and make it Pre-compiled SQL Query
		 if(con!=null)
			 ps=con.prepareStatement(STUD_INSERT_QUERY);
		 
		  //read multiple students details from enduser and set them to query param
		 //values
		 if(sc!=null && ps!=null){
		   for(int i=1;i<=count;++i){
			   //read each student details from enduser
			   System.out.println("Enter "+i+" student details");
			   System.out.println("Enter sno::");
			   no=sc.nextInt();
			   System.out.println("Enter sname::");
			   name=sc.next();
			   System.out.println("Enter Adress::");
			   addrs=sc.next();
			   //set input values to Query params
			   ps.setInt(1,no);
			   ps.setString(2,name);
			   ps.setString(3, addrs);
			   //execute the Query
			   result=ps.executeUpdate();
			   //Process the result
			   if(result==0)
				   System.out.println(i+" Student details not inserted ");
			   else
				   System.out.println(i+" Student details  inserted ");
		   }//for
		 }//if
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
			if(sc!=null)
				sc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//finally
  }//main
}//class
