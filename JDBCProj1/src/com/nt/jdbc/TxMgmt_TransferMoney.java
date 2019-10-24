package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TxMgmt_TransferMoney {

	public static void main(String[] args) {
		Scanner sc=null;
		int srcAcno=0,destAcno=0;
		float amt=0.0f;
		Connection con=null;
		Statement st=null;
		int result[]=null;		
		boolean flag=false;
		try{
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null){
				System.out.println(" source Account number::");
				srcAcno=sc.nextInt();
				System.out.println(" Dest Account number::");
				destAcno=sc.nextInt();
				System.out.println(" Amount to Transfer::");
				amt=sc.nextFloat();
			}//if
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement object
			if(con!=null)
				st=con.createStatement();
			//Begin Tx (By disabling autoCommit mode on DB s/w)
			if(con!=null)
				con.setAutoCommit(false);
			//add queries to batch
			if(st!=null){
			st.addBatch("UPDATE JDBC_ACCOUNT SET BALANCE=BALANCE-"+amt+ "WHERE ACNO="+srcAcno);
			st.addBatch("UPDATE JDBC_ACCOUNT SET BALANCE=BALANCE+"+amt+ "WHERE ACNO="+destAcno);
			}
			//execute the batch
			if(st!=null)
				result=st.executeBatch();
			//commit or rollback the Tx
			if(con!=null && result!=null){
				 for(int i=0;i<result.length;++i){
					 if(result[i]==0){
						 flag=true;
					 }//if
				 }//for
				 if(flag==true){
					 con.rollback();
					 System.out.println("Money not transfered(Tx rolledBack)");
				 }
				 else{
					 con.commit();
					 System.out.println("Money  transfered(Tx committed)");
				 }
					 
			}//if
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
				//close jdbc objs
				if(st!=null)
					st.close();
			}
			catch(SQLException se){
		         se.printStackTrace();
		}
			try{
				//close jdbc objs
				if(con!=null)
					con.close();
			}
			catch(SQLException se){
		         se.printStackTrace();
		}
			
		try{
				//close jdbc objs
				if(sc!=null)
					sc.close();
			}
			catch(Exception e){
		         e.printStackTrace();
		}
		}//finally
	}//main
}//class
