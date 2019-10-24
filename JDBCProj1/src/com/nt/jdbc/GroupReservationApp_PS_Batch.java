package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*SQL> create table PassengerInfo(viechleNo number(5) primary key,psngr_name varchar2(20),psngr_source varchar2(10),psngr_dest varchar2(10),fare number(7,2));*/
public class GroupReservationApp_PS_Batch {
   private static final String  INSERT_PASSEGENER_INFO="INSERT INTO PASSENGERINFO VALUES(?,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int vno=0,groupCount=0;
		String name=null,source=null,dest=null;
		Connection con=null;
		float fare=0;
		PreparedStatement ps=null;
		int result[]=null;
		
		try{
			sc=new Scanner(System.in);
			//read inputs
			if(sc!=null){
				System.out.println("Enter Viechle number::");
				vno=sc.nextInt();

				System.out.println("Enter source place::");
				source=sc.next();
				System.out.println("Enter Dest place::");
				dest=sc.next();

				System.out.println("Enter Group Count::");
				groupCount=sc.nextInt();
				System.out.println("enter Fare::");
				fare=sc.nextFloat();
			}
			
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PrpearedStatement obj
			 if(con!=null)
				 ps=con.prepareStatement(INSERT_PASSEGENER_INFO);
			 //read passgener's info and them batch as query param values
			 if(sc!=null && ps!=null){
				 for(int i=1;i<=groupCount;++i){
					 System.out.println("Enter  "+i+" passegener name::");
					 name=sc.next();
					 //add Each Passenger details to batch
					 ps.setInt(1,vno);
					 ps.setString(2,name);
					 ps.setString(3,source);
					 ps.setString(4, dest);
					 ps.setFloat(5,fare);
					 ps.addBatch();
				 }//for
				 //execute the batch
				 result=ps.executeBatch();
				 //process the result
				 if(result!=null){
					 System.out.println("Group reservation is done");
				 }
				 else{
					 System.out.println("Group reservation is not done");
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
				if(ps!=null)
					ps.close();
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
