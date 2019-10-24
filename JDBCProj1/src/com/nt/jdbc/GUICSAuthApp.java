package com.nt.jdbc;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*SQL> select * from userlist;

UNAME                PWD
-------------------- --------------------
raja                 rani
ramesh               suresh
king                 kingdom
*/
/*create or replace procedure p_authentication(user in varchar2,pass  in varchar2,result out varchar2)
as
  cnt number(5);
begin
    select count(*) into cnt from userlist where uname=user and pwd=pass;
end;
/
*/

public class GUICSAuthApp extends JFrame implements ActionListener,WindowListener{
	private static final String CALL_PRO="{ CALL P_AUTHENTICATION(?,?,?) }";
	private JLabel luser,lpwd,lresult;
	private JTextField tuser,tpwd;
	private JButton btn;
	Connection con;
	CallableStatement cs;
	
	
	public GUICSAuthApp() {
		super("GUI-Swing AUTHENTICATION App");
		System.out.println("GUCsAuthApp: 0-param consturctor");
		setSize(300, 300);
		setLayout(new FlowLayout());
		//add comos
		luser=new JLabel("user name:");
		add(luser);
		tuser=new JTextField(10);
		add(tuser);
		lpwd=new JLabel("password:");
		add(lpwd);
		tpwd=new JTextField(10);
		add(tpwd);
		btn=new JButton("validate");
		add(btn);
		btn.addActionListener(this); // to handle action event on button
		this.addWindowListener(this);
		
		lresult=new JLabel("");
		add(lresult);
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initialize();
	}
	
	private   void  initialize(){
		
		System.out.println("initialize() method");
		try{
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create CallableStatement obj having query
			cs=con.prepareCall(CALL_PRO);
			//register OUT param with jdbc types
			cs.registerOutParameter(3,Types.VARCHAR);
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
		System.out.println("main(-)");
		new GUICSAuthApp();

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String user=null,pass=null;
		String result=null;
		System.out.println("actionPerformed(-)");
		try{
			//read text box values
			user=tuser.getText();
			pass=tpwd.getText();
			//set these values to query IN params
			cs.setString(1,user);
			cs.setString(2,pass);
			//execute the query
			cs.execute();
			//get Out paramter value
			result=cs.getString(3);
			//set result to label
			lresult.setText(result);
		}
		catch(SQLException se){
			lresult.setText(se.getMessage());
			se.printStackTrace();
		}
		catch(Exception e){
			lresult.setText(e.getMessage());
			e.printStackTrace();
		}
	}//actionPerformed(-)

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
	  System.out.println("windowClosing(-) method");
	  //close jdbc objs
	  try{
		  if(cs!=null)
			  cs.close();
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
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}//class
