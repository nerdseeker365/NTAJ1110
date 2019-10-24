package com.nt.jdbc;

/*create or replace procedure p_get_emps_by_desg(desg in varchar,
        details out sys_refcursor)
as
begin
open  details for
select empno,ename,job,sal from emp where job=desg;
end;
/
*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;


public class CsCursorTest4 {
  private static final  String CALL_PRO="{ CALL p_get_emps_by_desg(?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		String desg=null;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		boolean flag=false;
		try{
		 //read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Employee Desg::");
				desg=sc.next();
			}
			
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
			   cs=con.prepareCall(CALL_PRO);
			//register OUT params with JDBC Data types
			if(cs!=null)
				cs.registerOutParameter(2,OracleTypes.CURSOR);
			//set values to query params
			if(cs!=null)
			   cs.setString(1,desg);
			//execute Query (calling PL/SQL procedure)
			if(cs!=null)
				cs.execute();
			//Gather results from OUT params
			if(cs!=null)
				rs=(ResultSet) cs.getObject(2);
			//process the ResultSet
			if(rs!=null){
				while(rs.next()){
					flag=true;
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getInt(4));
				}
				if(flag==false)
					System.out.println("records not found");
			}//if
		}//try
		catch(SQLException se){
			System.out.println("No Record found");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf){
			System.out.println("Problem in jdbc driver class Loading");
			cnf.printStackTrace();
		}
		catch(Exception e){
			System.out.println("Unknown Internal problem");
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
