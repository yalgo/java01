package controller;

public interface Customer {
	
//	public static final String ID = "admin";
//	public static final String PASSWORD = "admin";
	
	
	// 고객 - 장바구니
	public void cartList();
	public void cartAdd();
	public void cartRemove();
	public void cartBuy();
	
	// 고객 - 바로구매
	public void nowBuy();
	
	// 고객 - 환불
	public void refund();
	
	// 
	
}
