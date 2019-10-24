package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.internal.OracleTypes;

public class CsFxTest6 {
  private static final String  CALL_FX="{?=call view_delete_stud_by_no(?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		int cnt=0;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter student number::");
				no=sc.nextInt();
			}
			//register jdbc driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create  Statement obj
			if(con!=null)
				cs=con.prepareCall(CALL_FX);
			//register OUT,return params with JDBC types
				if(cs!=null){
					cs.registerOutParameter(1,OracleTypes.CURSOR);
					cs.registerOutParameter(3,Types.INTEGER);
				}
				//set values to IN params 
				if(cs!=null)
					cs.setInt(2,no);
				//execute Query
				if(cs!=null)
					cs.execute();
				//display student detials (return param)
				if(cs!=null){
					rs=(ResultSet)cs.getObject(1);
				}
				if(rs!=null){
					if(rs.next()){
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
					}
					else{
						System.out.println("record not found");
					}
				}
				//display deletion status
				if(cs!=null)
					cnt=rs.getInt(1);
				
				if(cnt==0)
					System.out.println("Record not found and not deleted");
				else
					System.out.println("Record found and deleted");
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
				if(cs!=null)
					cs.close();
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
