package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollableTest {

	public static void main(String[] args) {
		Connection con=null;
	   Statement st=null;
	   ResultSet rs=null;
		try{
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create STatement object
			if(con!=null)
				//st=con.createStatement();
				// st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					//	                                       ResultSet.CONCUR_READ_ONLY);
				st=con.createStatement(1004, 1007);
			//send and execute SQL Query
			if(st!=null)
				rs=st.executeQuery("SELECT * FROM STUDENT");
			//dispaly records
			System.out.println("top --- bottom");
			if(rs!=null){
				while(rs.next()){
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
				}
			}
			System.out.println("Bottom-Top");
			if(rs!=null){
				rs.afterLast();
				while(rs.previous()){
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
				}
			}
			//display records randomly
			rs.first();
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			rs.last();
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			rs.absolute(4);
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			rs.absolute(-3);
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			rs.relative(2);
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			rs.relative(-4);
			System.out.println(rs.getRow()+"--->"+rs.getInt(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			
		}//try
		catch (SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException  cnf){
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
