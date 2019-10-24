package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
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
				st=con.createStatement();
			//send and execute SQL Query
			if(st!=null)
				rs=st.executeQuery("SELECT * FROM PRODUCT");
			//process the ResultSet
			if(rs!=null){
				while(rs.next()){
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
				}//while
			}//if
			System.out.println("con obj class name::"+con.getClass());
			System.out.println("st obj class name::"+st.getClass());
			System.out.println("rs obj class name::"+rs.getClass());
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
}//calss
