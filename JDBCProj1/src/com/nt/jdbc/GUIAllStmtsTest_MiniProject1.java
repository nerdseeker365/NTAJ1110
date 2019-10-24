package com.nt.jdbc;
/*SQL> create table All_student (sno number(5) primary key,sname varchar2(20),m1 number(4),m2 number(4), m3 number(4));*/

/*
 create or replace procedure FIND_PASS_FAIL(m1 in number ,
                                                                                m2 in number,
                                                                                m3 in number,
                                                                                result out varchar)
   as
  begin
     if(m1<35 or m2< 35 or m3<35) then
         result:='FAIL';
     else
         result:='PASS';
    end if;
 end;
/
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIAllStmtsTest_MiniProject1 extends JFrame implements ActionListener {
	private static final String GET_ALL_SNOs="SELECT SNO FROM ALL_STUDENT";
	private static final String  GET_STUD_BY_SNO="SELECT  SNAME,M1,M2,M3 FROM ALL_STUDENT WHERE SNO=?";
	private static final String  CALL_PRO="{ CALL FIND_PASS_FAIL(?,?,?,?)}";
	private JLabel lno,lname,lm1,lm2,lm3,lresult;
	private JTextField tname,tm1,tm2,tm3,tresult;
	private JComboBox<Integer> tno;
	private JButton bDetails,bResult;
	private Connection con;
	Statement st;
	ResultSet rs,rs1;
	PreparedStatement ps;
	CallableStatement cs;
	
	public GUIAllStmtsTest_MiniProject1() {
		super("GUIFrame App-MIniProject");
		System.out.println("GUIAllStmtsTest_MiniProject()");
		setSize(300,300);
		setBackground(Color.GRAY);
		setLayout(new FlowLayout());
		//add comps
		lno=new JLabel("student  numbers:");
		add(lno);
		tno=new JComboBox<Integer>();
		add(tno);
		
		bDetails=new JButton("Details");
		add(bDetails);
		bDetails.addActionListener(this);
		
		lname=new JLabel("student name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		lm1=new JLabel("student marks1::");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		
		lm2=new JLabel("student marks2::");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		
		lm3=new JLabel("student marks3::");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		
		bResult=new JButton("result");
		add(bResult);
		bResult.addActionListener(this);
		
		lresult=new JLabel("student result::");
		add(lresult);
		tresult=new JTextField(10);
		add(tresult);
		//close window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		//disable editing on Text boxes
		tname.setEditable(false);
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tresult.setEditable(false);
		//add WindowListener
		addWindowListener(new  WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("windowClosing(-)");
				//close jdbc objs
			  try{
				  if(rs1!=null)
					  rs1.close();
			  }
			  catch(SQLException se){
				  se.printStackTrace();
			  }
			  
			  try{
				  if(rs!=null)
					  rs.close();
			  }
			  catch(SQLException se){
				  se.printStackTrace();
			  }
				
			  try{
				  if(cs!=null)
					  cs.close();
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
			}//windowClosing(-)
			
		});
		//invoke initialize() method
		initialize();
	}//constructor
	
	private void initialize(){
		try{
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create JDBC Statement obj
			st=con.createStatement();
			//create ResultSet obj
			rs=st.executeQuery(GET_ALL_SNOs);
			//copy sno values of ResultSet obj to comboBox
			while(rs.next()){
				tno.addItem(rs.getInt(1));
			}
			//create PreparedStatement object
			ps=con.prepareStatement(GET_STUD_BY_SNO);
			//create CallableStatement obj
			cs=con.prepareCall(CALL_PRO);
			cs.registerOutParameter(4,Types.VARCHAR);
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
	}

	public static void main(String[] args) {
		System.out.println("main(-) method");
		new GUIAllStmtsTest_MiniProject1();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		int sno=0;
		int m1=0,m2=0,m3=0;
		String result=null;
	 System.out.println("actionPerformed(-)");
	 System.out.println(ae.getActionCommand());
	 
	 if(ae.getSource()==bDetails){
		 System.out.println("details button is clicked");
		 try{
			 //read selected item comboBox
			 sno=(int) tno.getSelectedItem();
			 //set the above as query param value
			 ps.setInt(1,sno);
				//execute query
				rs1=ps.executeQuery();
				//read values from ResultSEt obj and set to text boxes
				if(rs1.next()){
					tname.setText(rs1.getString(1));
					tm1.setText(rs1.getString(2));
					tm2.setText(rs1.getString(3));
					tm3.setText(rs1.getString(4));
				}
		 }//try
		 catch(SQLException se){
			 se.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 
	 }
	 else{
		 System.out.println("result button is clicked");
		 try{
			 //gather results from text boxes
			 m1=Integer.parseInt(tm1.getText());
			 m2=Integer.parseInt(tm2.getText());
			 m3=Integer.parseInt(tm3.getText());
			 //set values to query params
			 cs.setInt(1,m1);
			 cs.setInt(2,m2);
			 cs.setInt(3, m3);
			 //call PL/SQL Procedure
			 cs.execute();
			 //gather results from OUT param
			 result=cs.getString(4);
			 //set the result to text box
			 tresult.setText(result);
		 }//try
		 catch(SQLException se){
			 se.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	 }//else
	}//actionPerformed(-)
	
/*	private class  MyWindowAdapter extends WindowAdapter{
		
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("windowClosing(-)");
			//close jdbc objs
		  try{
			  if(rs1!=null)
				  rs1.close();
		  }
		  catch(SQLException se){
			  se.printStackTrace();
		  }
		  
		  try{
			  if(rs!=null)
				  rs.close();
		  }
		  catch(SQLException se){
			  se.printStackTrace();
		  }
			
		  try{
			  if(cs!=null)
				  cs.close();
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
		  
		}//windowClosing(-)
	}//inner class 
	*/

}//class
