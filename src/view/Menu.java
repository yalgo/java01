package view;


public interface Menu {
	
	public void commonMenu(int num); 	// 있어도 없어도 그만 공통메뉴 (1. 이전 : 내가 원래 있던 함수 재호출 )

	public void loginMenu();			// 로그인메뉴
	public void register();				// 회원가입
	public void adminLogin();			// 관리자 로그인
	public void customerLogin();		// 고객 로그인
	
	public void adminMenu();			// 관리자메뉴
		// 관리자 메뉴 (1. 재고관리 2. 주문관리 3. 결산 4. 로그아웃)
	public void adminStockMenu();		// 관리자 재고관리 메뉴
		// 관리자 재고관리 메뉴 (1. 제품 목록 2. 제품 추가 3. 제품 수정 4. 제품 삭제 5. 이전)
	public void adminAdminMenu();		// 관리자 주문관리 메뉴
		// 관리자 주문관리 메뉴 (1. 주문 목록 2. 구매 승인 3. 환불 처리 4. 이전 화면
	public void adminTotalMenu();		// 관리자 결산 메뉴
		// 관리자 결산 메뉴 (1. 결산 , 2. 이전화면)
	
	public void customerMenu();	
		// 고객메누
			// 1. 장바구니, 2. 바로구매. 3., 환불 , 4. 로그아웃
	public void customerCartMenu();		// 고객 장바구니 메뉴
		// 1. 장바구니 목록
		// 2. 제품 추가
		// 3. 목록 삭제
		// 4. 제품 구매
		// 5. 이전 화면

	
	
}
