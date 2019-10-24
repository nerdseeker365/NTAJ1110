package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIScrollFrame extends JFrame implements ActionListener {
	private static final String GET_ALL_STUDS="SELECT SNO,SNAME,SADD FROM STUDENT";
   private JLabel lno,lname,laddrs;
   private JTextField tno,tname,taddrs;
   private JButton bFirst,bNext,bPrevious,bLast;
   private Connection con;
   private PreparedStatement ps;
   private ResultSet rs;
   
   
   
   public GUIScrollFrame() {
	    super("GUI ScrollFrame App");
	    System.out.println("GUIScrollFrame:: 0-param constructor");
	    setSize(200, 300);
	    setLayout(new FlowLayout());
	    setBackground(Color.GRAY);
	    //add comps
	    lno=new JLabel("student number");
	    add(lno);
	    tno=new JTextField(10);
	    add(tno);
	    
	    lname=new JLabel("student name");
	    add(lname);
	    tname=new JTextField(10);
	    add(tname);
	    
	    laddrs=new JLabel("student address");
	    add(laddrs);
	    taddrs=new JTextField(10);
	    add(taddrs);
	    
	    bFirst=new JButton("First");
	    add(bFirst);
	    bNext=new JButton("Next");
	    add(bNext);
	    bPrevious=new JButton("Previous");
	    add(bPrevious);
	    bLast=new JButton("Last");
	    add(bLast);
	    bFirst.addActionListener(this);
	    bNext.addActionListener(this);
	    bPrevious.addActionListener(this);
	    bLast.addActionListener(this);
	    //Set Editable to false in text boxes
	    tno.setEditable(false);
	    tname.setEditable(false);
	    taddrs.setEditable(false);
	    addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent e) {
	    		System.out.println("windowClosing(-)");
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
	    	}
		});
	    
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    //perform jdbc code related intialize activities
	    initialize();
   }
   
   private void initialize(){
	   System.out.println("initialize()");
	   try{
		   //register jdbc driver
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //establish the connection
		   con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
		   //create PreparedStatement obj
		   ps=con.prepareStatement(GET_ALL_STUDS,
				                                              ResultSet.TYPE_SCROLL_SENSITIVE,
				                                              ResultSet.CONCUR_READ_ONLY);
		   rs=ps.executeQuery();
	   }//initialize()
	   catch(SQLException se){
		   se.printStackTrace();
	   }
	   catch(ClassNotFoundException  cnf){
		   cnf.printStackTrace();
	   }
	   catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
    public static void main(String[] args) {
    	System.out.println("main(-) mehtod");
		new GUIScrollFrame();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("actionPErformed(-)--->"+ae.getActionCommand());
		boolean flag=false;
		try{
		if(ae.getSource()==bFirst){
			System.out.println("first Button");
			rs.first();
			flag=true;
			
		}
		else if(ae.getSource()==bNext){
			System.out.println("next Button");
			if(!rs.isLast()){
				rs.next();
				flag=true;
			}
			
		}
		else if(ae.getSource()==bPrevious){
			System.out.println("previous button");
			if(!rs.isFirst()){
				rs.previous();
				flag=true;
			}
		}
		else{
			System.out.println("last button");
			rs.last();
			flag=true;
		}
		
		//set values of ResultSet obj record to Text Boxes
		if(flag){
			tno.setText(rs.getString(1));
			tname.setText(rs.getString(2));
			taddrs.setText(rs.getString(3));
		}
		
		
		}//try
		catch(SQLException  se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
