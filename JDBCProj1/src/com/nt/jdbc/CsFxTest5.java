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
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;


public class CsFxTest5 {
  private static final  String CALL_FX="{?= call FX_GET_EMP_BY_NO(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		try{
		 //read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Employee number::");
				no=sc.nextInt();
			}
			
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
			   cs=con.prepareCall(CALL_FX);
			//register OUT params,return Param with JDBC Data types
			if(cs!=null){
				cs.registerOutParameter(1,Types.VARCHAR);
				cs.registerOutParameter(3,Types.VARCHAR);
				cs.registerOutParameter(4,Types.FLOAT);
			}
			//set values to query params (IN)
			if(cs!=null)
			   cs.setInt(2,no);
			//execute Query (calling PL/SQL procedure)
			if(cs!=null)
				cs.execute();
			//Gather results from OUT params,Return param
			if(cs!=null){
				System.out.println("Emp name::"+cs.getString(3));
				System.out.println("Emp salary::"+cs.getFloat(4));
				System.out.println("Emp desg::"+cs.getString(1));
			}
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
