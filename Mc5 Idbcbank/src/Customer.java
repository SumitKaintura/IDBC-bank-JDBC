import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
	long customerId;
	String customerName;
	int balance;
	String city;
	String emailid;
	String mobileno;
	String address;
	String type;
	
	
	

	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", balance=" + balance
				+ ", city=" + city + ", emailid=" + emailid + ", mobileno=" + mobileno + ", address=" + address
				+ ", type=" + type + "]";
	}

	public void initialize()
	{
		Scanner scannerObj=new Scanner(System.in);
		//System.out.println("Enter the Customer ID:");
		//customerId=scannerObj.nextInt();
		long theRandomNum =  (long)((Math.random()*100000000000L)+100000000000L);
	    customerId=theRandomNum;
		
		System.out.println("Enter the Customer Name:");
		customerName=scannerObj.next();
		System.out.println("Enter the Customer Balance:");
		balance=scannerObj.nextInt();
		System.out.println("Enter the Customer City:");
		city=scannerObj.next();
		System.out.println("Enter the Customer Email Id:");
		emailid=scannerObj.next();
		System.out.println("Enter the Customer Mobile No:");
		mobileno=scannerObj.next();
		System.out.println("Enter the Customer Address:");
		address=scannerObj.next();
		System.out.println("Enter the type of account to be created Save or Pay");
		type=scannerObj.next();
	}

	public Customer() {}
	public Customer(long customerId, String customerName, int balance, String city, String emailid, String mobileno,String address,String type) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.balance = balance;
		this.city = city;
		this.emailid = emailid;
		this.mobileno = mobileno;
		this.address = address;
		this.type=type;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type=type;
	}
	

}