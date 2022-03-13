import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.util.Date;

public class execute {
	
	
	public static void main(String arg[])
	{
		Scanner sc = new Scanner(System.in);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/forminiproject","root","Alphamale@123");
		
			Customer cust = new Customer();
			
			System.out.println("enter 1 for Creating a new Account,Enter 2 for Delete Customer Data,Enter 3 for update salary,Enter 4 for Withdraw Balance,Enter 5 for Deposit Balance,Enter 6 for view Balance,Enter 7 for fund Transfer ");
			int num = sc.nextInt();
			
			switch(num) {
			
			case 1:
			   cust.initialize();
			   int roweff=execute.insertData(conn, cust);
			
			   if(roweff>0)
				System.out.println("Account Created");
			   else
				System.out.println("Error Occured");
			    break;
			//for deleting an entry
			case 2:    
			System.out.println("enter the employee id to delete");
			long empid = sc.nextLong();
			
            int roweff1=execute.deleteData(conn, empid);
			
			if(roweff1>0)
				System.out.println("Account Deleted");
			else
				System.out.println("Error Occured");
			    break;
			//for updating Salary
			case 3:
			System.out.println("enter the employee id to to update salary");
			long empid1 = sc.nextLong();
			int roweff2=execute.updateData(conn, empid1);
			if(roweff2>0)
				System.out.println("Balance Updated");
			else
				System.out.println("Error Occured");
			break;
			//for withdrawal
			case 4:
			System.out.println("enter the customer id to withdrawal balance");
			Long customerid = sc.nextLong();
			System.out.println("Enter the amount you want to withdraw");
			int amount = sc.nextInt();
			int roweff3=execute.withdrawal(conn, customerid , amount);
			if(roweff3>0)
				System.out.println("Withdrawal Successfull");
			else
				System.out.println("Failure.Please try again later");
			break;
			//for deposit
			case 5:
			System.out.println("enter the customer id to deposit balance");
			long customerid1 = sc.nextLong();
			System.out.println("Enter the amount you want to deposit");
			int amount1 = sc.nextInt();
			int roweff4=execute.deposit(conn, customerid1 , amount1);
			if(roweff4>0)
				System.out.println("Deposit Successfull");
			else
				System.out.println("Failure.Please try again later");
			break;
			//for viewing Balance
			case 6:
			System.out.println("enter the customer id to to view balance");
			long customerid2 = sc.nextLong();
			execute.viewBalance(conn, customerid2);
			 break;
			 //for fund transfer
			case 7:
				System.out.println("Enter your customer ID");
				long customerid3=sc.nextLong();
				System.out.println("Enter  customer ID where you want to send money");
				long customerid4=sc.nextLong();
				System.out.println("Enter the amount you want to transfer");
				int amount2 = sc.nextInt();
				int roweff5=execute.fundtransfer(conn,customerid3,customerid4,amount2);
				if(roweff5>0)
					System.out.println("Transfer Successfull");
				else
					System.out.println("Failure.Please try again later");
				break;
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception Raised:"+e);
		}
		
	}
	
	public static int insertData(Connection conn,Customer customer)throws SQLException
	{
		PreparedStatement psmt=conn.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?)");
		
		psmt.setLong(1, customer.getCustomerId());
		psmt.setString(2, customer.getCustomerName());
		psmt.setInt(3,customer.getBalance());
		psmt.setString(4,customer.getCity());
		psmt.setString(5, customer.getEmailid());
		psmt.setString(6, customer.getMobileno());
		psmt.setString(7, customer.getAddress());
		psmt.setString(8, customer.gettype());
		
		int row_eff=psmt.executeUpdate();
		psmt.close();
		
		return row_eff;
		
	}
	
	public static int deleteData(Connection conn,long employeeid)throws SQLException
	{
		PreparedStatement psmt=conn.prepareStatement("delete from customer where customerId=?");		
		psmt.setLong(1, employeeid);
		int row_eff=psmt.executeUpdate();
		psmt.close();
		return row_eff;
	}
	
	public static int updateData(Connection conn,long employeeid)throws SQLException
	{
		int salary=80000;
		PreparedStatement psmt=conn.prepareStatement("update customer set salary=? where customerId=?");
		psmt.setInt(1, salary);
	    psmt.setLong(2, employeeid);
		int row_eff=psmt.executeUpdate();
		psmt.close();
		return row_eff;
	}
	
	public static int withdrawal(Connection conn,long customerId,int amount)throws SQLException
	{
		
		
		PreparedStatement psmt = conn.prepareStatement("update customer set balance=balance-? where customerId=?");
		psmt.setInt(1, amount);
		psmt.setLong(2, customerId);
		int row_eff=psmt.executeUpdate();
		psmt.close();
		execute.trans(conn,customerId,amount);
		
		return row_eff;
	}
	public static void trans(Connection conn,long customerId,int amount)throws SQLException
	{
		int balance=0;
		PreparedStatement psmt2=conn.prepareStatement("select * from customer where customerId=?");		
		psmt2.setLong(1, customerId);
		ResultSet rs=psmt2.executeQuery();
		while(rs.next())
		{
		   balance=rs.getInt(3);	
		}
		int newbalance=balance-amount;
		psmt2.close();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the type of transaction :cheque or Cash");
		String typeoftrans=sc.nextLine();
		Date date=new Date();
		long no=3456;
	    java.sql.Date sqldate=new java.sql.Date(date.getTime());
		int theRandomNum = (int)(Math.random()*10000+1000);
		PreparedStatement psmt = conn.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
		psmt.setInt(1,theRandomNum);
		psmt.setLong(2,customerId);
		psmt.setLong(3,0);
		psmt.setInt(4,amount);
		psmt.setDate(5,sqldate);
		psmt.setString(6,typeoftrans);
		psmt.setInt(7,newbalance);
		psmt.executeUpdate();
		psmt.close();
		//System.out.println(theRandomNum);
		//System.out.println(customerId);
		//System.out.println(no);
		//System.out.println(amount);
		//System.out.println(sqldate);
		//System.out.println(typeoftrans);
		//System.out.println(newbalance);
	}
	
	public static int deposit(Connection conn,long customerId,int amount)throws SQLException
	{
		PreparedStatement psmt = conn.prepareStatement("update customer set balance=balance+? where customerId=?");
		psmt.setInt(1, amount);
		psmt.setLong(2, customerId);
		int row_eff=psmt.executeUpdate();
		psmt.close();
		execute.transdep(conn,customerId,amount);
		return row_eff;
	}
	
	public static void transdep(Connection conn,long customerId,int amount)throws SQLException
	{
		int balance=0;
		PreparedStatement psmt2=conn.prepareStatement("select * from customer where customerId=?");		
		psmt2.setLong(1, customerId);
		ResultSet rs=psmt2.executeQuery();
		while(rs.next())
		{
		   balance=rs.getInt(3);	
		}
		int newbalance=balance+amount;
		psmt2.close();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the type of transaction :cheque or Cash");
		String typeoftrans=sc.nextLine();
		Date date=new Date();
		long no=3456;
	    java.sql.Date sqldate=new java.sql.Date(date.getTime());
		int theRandomNum = (int)(Math.random()*10000+1000);
		PreparedStatement psmt = conn.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
		psmt.setInt(1,theRandomNum);
		psmt.setLong(2,customerId);
		psmt.setLong(3,0);
		psmt.setInt(4,amount);
		psmt.setDate(5,sqldate);
		psmt.setString(6,typeoftrans);
		psmt.setInt(7,newbalance);
		psmt.executeUpdate();
		psmt.close();
		//System.out.println(theRandomNum);
		//System.out.println(customerId);
		//System.out.println(no);
		//System.out.println(amount);
		//System.out.println(sqldate);
		//System.out.println(typeoftrans);
		//System.out.println(newbalance);
	}
	
	public static void viewBalance(Connection conn,long customerId)throws SQLException
	{
		PreparedStatement psmt=conn.prepareStatement("select * from customer where customerId=?");		
		psmt.setLong(1, customerId);
		ResultSet rs=psmt.executeQuery();
		
		while(rs.next())
		{
			System.out.print(rs.getString(1)+"\t");
			System.out.print(rs.getString(2)+"\t");
			System.out.print(rs.getString(3)+"\t");
			System.out.print(rs.getString(4)+"\t");
			System.out.print(rs.getString(5)+"\t");
			System.out.print(rs.getString(6)+"\t");
			System.out.print(rs.getString(7)+"\t");
			//System.out.println(rs.getString(8));
			System.out.println();
			
		}
		psmt.close();
		conn.close();
	}
	
	public static int fundtransfer(Connection conn,long customerId,long customerId2,int amount)throws SQLException
	{
		PreparedStatement psmt = conn.prepareStatement("update customer set balance=balance-? where customerId=?");
		psmt.setInt(1, amount);
		psmt.setLong(2, customerId);
		PreparedStatement psmt2 = conn.prepareStatement("update customer set balance=balance+? where customerId=?");
		psmt2.setInt(1, amount);
		psmt2.setLong(2, customerId2);
		psmt.executeUpdate();
		int row_eff=psmt2.executeUpdate();
		
		psmt.close();
		psmt2.close();
		execute.transfer(conn,customerId,customerId2,amount);
		return row_eff;
	}
	public static void transfer(Connection conn,long customerId,long customerId2,int amount)throws SQLException
	{
		int balance=0;
		PreparedStatement psmt2=conn.prepareStatement("select * from customer where customerId=?");		
		psmt2.setLong(1, customerId);
		ResultSet rs=psmt2.executeQuery();
		while(rs.next())
		{
		   balance=rs.getInt(3);	
		}
		psmt2.close();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the type of transaction :cheque or Cash");
		String typeoftrans=sc.nextLine();
		Date date=new Date();
		long no=3456;
	    java.sql.Date sqldate=new java.sql.Date(date.getTime());
		int theRandomNum = (int)(Math.random()*10000+1000);
		PreparedStatement psmt = conn.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
		psmt.setInt(1,theRandomNum);
		psmt.setLong(2,customerId);
		psmt.setLong(3,customerId2);
		psmt.setInt(4,amount);
		psmt.setDate(5,sqldate);
		psmt.setString(6,typeoftrans);
		psmt.setInt(7,balance);
		psmt.executeUpdate();
		psmt.close();
		//System.out.println(theRandomNum);
		//System.out.println(customerId);
		//System.out.println(no);
		//System.out.println(amount);
		//System.out.println(sqldate);
		//System.out.println(typeoftrans);
		//System.out.println(newbalance);
	}

}
