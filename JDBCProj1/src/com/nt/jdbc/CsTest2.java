package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace  procedure p_get_EmpDetails_By_No(no in number,name out varchar,desg out varchar,salary out varchar)
as
begin
   SELECT ENAME,SAL,JOB INTO name,salary,desg  FROM EMP  WHERE EMPNO=no;
end;
/
*/
public class CsTest2 {
   private static final String CALL_PRO="{ CALL p_get_EmpDetails_By_No(?,?,?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println("Enter Employee No:::");
				no=sc.nextInt();
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement object
			if(con!=null)
			   cs=con.prepareCall(CALL_PRO);
			//register OUT params with JDBC types
			if(cs!=null){
				cs.registerOutParameter(2, Types.VARCHAR);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.FLOAT);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(1, no);
			
			//call PL/SQL Procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null){
				System.out.println("emp name::"+cs.getString(2));
				System.out.println("emp desg::"+cs.getString(3));
				System.out.println("emp salary::"+cs.getFloat(4));
			}
		}//try
		catch(SQLException se){
			System.out.println("No Record found");
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
			
		}//finally

	}//main
}//class
