package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PsScrollableTest {
  private static final String GET_ALL_STUDENTS="SELECT SNO,SNAME,SADD FROM STUDENT";
	public static void main(String[] args) {
		Connection con=null;
	   PreparedStatement ps=null;
	   ResultSet rs=null;
		try{
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
			//create STatement object
			if(con!=null)
				ps=con.prepareStatement(GET_ALL_STUDENTS,
						                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
						                                        ResultSet.CONCUR_UPDATABLE);
			
			//send and execute SQL Query
			if(ps!=null)
				rs=ps.executeQuery();
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
				if(ps!=null)
					ps.close();
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
