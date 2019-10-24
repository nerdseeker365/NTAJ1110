package com.nt.jdbc;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JDBCRowSetTest {

	public static void main(String[] args) {
		OracleJDBCRowSet jrowset=null;
		try{
			//create Rowset object
			jrowset=new OracleJDBCRowSet();
			//set inputs
			jrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jrowset.setUsername("system");
			jrowset.setPassword("manager");
			jrowset.setCommand("SELECT * FROM STUDENT");
			//execute Query
			jrowset.execute();
			//process the RowSet
			while(jrowset.next()){
				System.out.println(jrowset.getInt(1)+"  "+jrowset.getString(2)+" "+jrowset.getString(3));
			}
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
				if(jrowset!=null)
					jrowset.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
