package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PropertiesScrollableTest {

	public static void main(String[] args) {
		Connection con=null;
	   Statement st=null;
	   ResultSet rs=null;
	   InputStream is=null;
	   Properties props=null;
		try{
			//load properties file 
			is=new FileInputStream("src/com/nt/commons/jdbc.properties");
			//load Properties file info java.util.Properties class obj
			props=new Properties();
			props.load(is);
			
			//register jdbc driver
			Class.forName(props.getProperty("jdbc.driver"));
			//establish the connection
			con=DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.user"),props.getProperty("jdbc.pwd"));
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
