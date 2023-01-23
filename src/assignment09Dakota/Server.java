package assignment09Dakota;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {

private JTextArea jta = new JTextArea();

public static void main(String[] args) {
	   new Server();
}//End of Main
	 
public Server() {

	   //Place text area on the frame
	   getContentPane().setLayout(new BorderLayout());
	   getContentPane().add(new JScrollPane(jta),BorderLayout.CENTER);

	   setTitle("ServerLoan");
	   setSize(500,300);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setVisible(true); //It is necessary to show the frame here!

	   try{

	   //Create a server socket
	   ServerSocket serverSocket = new ServerSocket(8000);
	   jta.append("Server started at" +new Date()+ '\n');

	   //Listen for a connection request
	   Socket connectToClient = serverSocket.accept();

	   //Create data input and output streams
	   DataInputStream isFromClient = new DataInputStream(
	   connectToClient.getInputStream());
	   DataOutputStream osToClient = new DataOutputStream(
	   connectToClient.getOutputStream());

	   while (true) {

	      //Receive annual interest rate from the client
	      double annualInterestRate = isFromClient.readDouble();

	      //Receive number of years from the client
	      int numOfYears = isFromClient.readInt();

	      //Receive loan amount from the client
	      double loanAmount = isFromClient.readDouble();


	      //Obtain monthly interest rate
	      double monthlyInterestRate = annualInterestRate / 1200;


	      //Compute total payment
	      double totalPayment=(loanAmount*annualInterestRate/100*numOfYears)+loanAmount;

	      //Compute monthly payment
	      double monthlyPayment = totalPayment/(numOfYears*12);

	      //Send monthly payment back to the client
	      osToClient.writeDouble(monthlyPayment);

	      //Send total payment back to the client
	      osToClient.writeDouble(totalPayment);


	      jta.append("The Annual Interest Rate received from client is "+ annualInterestRate+'\n');
	      jta.append("The Number Of Years received from client is "+ numOfYears+'\n');
	      jta.append("The Loan Amount received from client is "+ loanAmount+'\n');
	      jta.append("The Monthly Payment is "+ monthlyPayment+'\n') ;
	      jta.append("The Total Payment is"+ totalPayment+'\n');
	    }

	  }

	     catch(IOException ex){
	         System.err.println(ex);

	     }
	 }
	}

	 
