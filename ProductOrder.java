package com.jdbc.product;
import java.util.Scanner;


public class ProductOrder 
{
	static Scanner sc=new Scanner(System.in);
	
	public static void testSelectStoredProc()
	{
		DBConnection oDBConnector = new DBConnection();
		System.out.println("Enter your id:");
		int cid=sc.nextInt();
		oDBConnector.selectStoredProc(cid);
	}

	public static void main(String[] args) 
	{
		testSelectStoredProc();
	}
}
