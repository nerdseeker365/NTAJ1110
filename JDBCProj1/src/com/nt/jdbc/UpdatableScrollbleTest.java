package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatableScrollbleTest {
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			//register jdbc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create Statement obj
			if(con!=null)
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						                                     ResultSet.CONCUR_UPDATABLE);
			 //execute the SQL Query
			if(st!=null)
				rs=st.executeQuery("SELECT SNO,SNAME,SADD FROM STUDENT");
			
			//proccess the ResultSet obj
			if(rs!=null){
				while(rs.next()){
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
				}///while
			}//if
			/*//insert operation
			rs.moveToInsertRow();
			rs.updateInt(1, 8199);
			rs.updateString(2,"mahesh");
			rs.updateString(3,"kochi");
			rs.insertRow();
			System.out.println("record inserted"); */
			//update operation
			/*rs.absolute(5);
			rs.updateString(3,"raipur");
			rs.updateRow();
			System.out.println("Record Updated");*/
			
			rs.absolute(5);
			rs.deleteRow();
			System.out.println("record deleted");
			
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
