package com.jdbc.product;

public class Product 
{
	private int id;
	private String pname;
	
	public Product(int id, String pname) 
	{
		super();
		this.id = id;
		this.pname = pname;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getPname() 
	{
		return pname;
	}
	
	public void setPname(String pname) 
	{
		this.pname = pname;
	}

	@Override
	public String toString() 
	{
		return "Product [id=" + id + ", pname=" + pname + "]";
	}
	
	
}
