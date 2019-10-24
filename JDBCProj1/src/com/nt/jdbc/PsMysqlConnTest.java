package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PsMysqlConnTest {
	private static final String  GET_PRODUCTS_QUERY="SELECT PID,PNAME,PRICE FROM PRODUCT WHERE PID<=?";

	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			//regisgter jdbc driver
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.gjt.mm.mysql.Driver");
			//establish the connnection
			//con=DriverManager.getConnection("jdbc:mysql:///NTAJ1110DB","root","root");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/NTAJ1110DB","root","root");
			//create STatement obj
			if(con!=null)
				ps=con.prepareStatement(GET_PRODUCTS_QUERY);
			//set input values to query params
			if(ps!=null)
				ps.setInt(1,1500);
				
			//send and execute SQL Query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the ResultSet
			if(rs!=null){
				while(rs.next()){
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
				}//while
			}//if
		}//try
		catch (SQLException se) {
			se.printStackTrace();
		}
	/*	catch(ClassNotFoundException  cnf){
			cnf.printStackTrace();
		}*/
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
}//calss
