package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*  CREATE OR REPLACE PROCEDURE P_FIRST_PRO(X IN NUMBER,Y IN NUMBER,Z OUT NUMBER)as
 *    BEGIN
 *       Z:=X+Y;
 *      END;
 * * */


public class CsTest1 {
   private static final String CALL_PRO="{ CALL P_FIRST_PRO(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int  first=0,second=0;
		Connection con=null;
		CallableStatement cs=null;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter First Value:::");
				first=sc.nextInt();
				System.out.println("Enter Second Value::");
				second=sc.nextInt();
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
			   cs=con.prepareCall(CALL_PRO);
			//register OUT params with JDBC types
			if(cs!=null)
				cs.registerOutParameter(3, Types.INTEGER);
			//set values to IN params
			if(cs!=null){
				cs.setInt(1, first);
				cs.setInt(2,second);
			}
			//call PL/SQL Procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null)
				System.out.println("Sum is:::"+cs.getInt(3));
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
			
		}//finallt

	}//main
}//class
