package com.nt.jdbc;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJDBCRowSet;

public class CachedRowSetTest {

	public static void main(String[] args) {
		OracleCachedRowSet crowset=null;
		try{
			//create Rowset object
			crowset=new OracleCachedRowSet();
			//set inputs
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setCommand("SELECT * FROM STUDENT");
			//execute Query
			crowset.execute();
			//process the RowSet
			while(crowset.next()){
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+" "+crowset.getString(3));
			}
			//disable readly
			//crowset.setReadOnly(false);
			System.out.println("Level1->Stop DB s/w");
			Thread.sleep(40000);
			crowset.absolute(4);
			crowset.updateString(3,"kukatpally123");
			crowset.updateRow();
			System.out.println("Level2->Restart DB s/w");
			Thread.sleep(40000);
			//access the changes
			crowset.acceptChanges();
			//process the RowSet
			while(crowset.next()){
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+" "+crowset.getString(3));
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
				if(crowset!=null)
					crowset.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
