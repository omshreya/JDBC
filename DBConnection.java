package com.jdbc.product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class DBConnection 
{
	Scanner sc=new Scanner(System.in);
	public Connection connect() throws ClassNotFoundException, SQLException 
	{
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/sample";
		String userName = "root";
		String password = "password";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}
	
	@SuppressWarnings("resource")
	public void selectStoredProc(int cid)
	{
		Connection conn=null;
		CallableStatement cstmt = null;
		String sql = "{call x(?,?)}";
		Statement stmt = null;
		ResultSet rs=null;
		String sql2 = "select * from product";
		PreparedStatement pstmt = null;
		String sql3 = "insert into customer values(?,?)";
		String updatestmt="UPDATE orderTable SET quantity = ? WHERE id = ? AND pname=? ";
		try
		{
			conn = connect();
			if(null != conn)
			{
				cstmt = conn.prepareCall(sql);
				cstmt.setInt(1, cid);
			    cstmt.registerOutParameter(2,java.sql.Types.INTEGER);
				cstmt.executeUpdate();
				int i=cstmt.getInt(2);
				System.out.println("Stored Proc Output: " +i);
				if(i==1)
				{
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql2);
					while(rs.next())
					{
						System.out.println(rs.getString(2));
					}
				}
				else
				{
					System.out.println("Enter your Name:");
					String cname=sc.next();
					pstmt = conn.prepareStatement(sql3);
					pstmt.setInt(1, cid);
					pstmt.setString(2, cname);
					int count = pstmt.executeUpdate();
					System.out.println("No. of rows inserted= " + count);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql2);
					while(rs.next())
					{
						System.out.println(rs.getString(2));
					}
				}
				
				System.out.println("What you want...???");
				String s=sc.next();
				Map<String,Integer> map=new HashMap<>();
				
				 try
				 {
			            rs = stmt.executeQuery("SELECT id,pname FROM orderTable");
			            while(rs.next())
			            {
			                Integer id = rs.getInt("id");
			                String pname = rs.getString("pname");
			                map.put(pname, id);
			            }
			            
			            if(map.containsKey(s))
						{
							map.put(s, map.get(s)+1);
							System.out.println(map.get(s)+1);
							pstmt=conn.prepareStatement(updatestmt);
							pstmt.setInt(1,map.get(s)+1);
							pstmt.setInt(2, cid);
							pstmt.setString(3, s);
							int count = pstmt.executeUpdate();
							System.out.println("No. of rows inserted= " + count);
							
						}
						else
						{
							map.put(s, 1);
							String sql4 = "insert into orderTable values(?,?,?)";
							pstmt = conn.prepareStatement(sql4);
							pstmt.setInt(1, cid);
							pstmt.setString(2, s);
							pstmt.setInt(3,1);
							int count = pstmt.executeUpdate();
							System.out.println("No. of rows inserted= " + count);
						}
						
			              
			      }
				 catch(Exception ex)
				 {
		            ex.printStackTrace();
		        }
				 
			}
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("SQL State" + e.getSQLState());
		}
		finally
		{
			try 
			{
				rs.close();
				pstmt.close();
				cstmt.close();
				stmt.close();
				conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}			
		}
	}
		
}
