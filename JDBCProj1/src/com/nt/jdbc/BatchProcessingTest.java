package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessingTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		int result[]=null;
		int sum=0;
		try{
			//register jdbc driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement obj
			if(con!=null)
				st=con.createStatement();
			//add queries to batch
			if(st!=null){
			st.addBatch("insert into student values(901,'ginesh','noida')");
			st.addBatch("update student set sadd='dispur1' where sno>=8000");
			st.addBatch("delete from student where sno<=100");
			}
			//execute the query
			if(st!=null)
			  result=st.executeBatch();
			// process the results
			if(result!=null){
				for(int i=0;i<result.length;++i)
					sum=sum+result[i];
			}
			System.out.println("no.of records that are effected::"+sum);
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
		}//finally
	}//main
}//class
