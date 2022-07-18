package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.Admin;
import controller.AdminImpl;
import controller.CustomerImpl;
import model.Code;
import model.Supplement;

/*
 * 작성일자 : 2022. 7. 15.
 * 작성자 : 김진아
 * Menu와 Code interface를 구현하는 클래스
 * admin과 customer을 싱글톤으로 사용
 * 두개의 hashMap을 사용 : 사용자의 ID, PW와 관리자 로그인용 아이디, 비밀번호
 * 초기화면 : 관리자, 고객, 회원가입, 종료 
 * 관리자메뉴 : 독립적으로 부여한 관리자용 아이디 사용하여 진입 가능
 * 고객메뉴 : 회원가입을 한 후 고객메뉴 진입 가능
 *  - 고객의 아이디는 관리자용 아이디인 admin과 중복될 수 없으며, 영어 및 숫자로만 이루어진 4자 이상 ID여야 함
 * switchCase의 default와 Exception을 활용하여 예외처리 
 */

public class MenuImpl implements Menu, Code{
	
	public static AdminImpl admin = AdminImpl.getInstance();
	public static CustomerImpl customer = CustomerImpl.getInstance();
	public static Supplement supplement = Supplement.getInstance();
	
	static Scanner input = new Scanner(System.in);
	
	public static MenuImpl instance = new MenuImpl();
	
	public static MenuImpl getInstance(){
		if(instance == null) {
			instance = new MenuImpl();
		}
		return instance;
	}
	
	static Map <String, String> cusmap = new HashMap<String, String>();	// 고객 로그인
	
	
	@Override
	public void commonMenu(int num) {
		switch(num) {
		case SHOP_LOGIN : loginMenu();
		break;
		
		case ADMIN_MENU_LOGIN : adminLogin();
		break;
		
		case ADMIN_MENU : adminMenu();
		break;
		
		case ADMIN_STOCK_MENU : adminStockMenu();
		break;
		
		case ADMIN_ORDER_MENU : adminAdminMenu();
		break;
		
		case ADMIN_ORDER_TOTAL : adminTotalMenu();
		break;
		
		
		
		
		case GUEST_MENU_LOGIN : customerLogin();
		break;
		
		case GUEST_MENU : customerMenu();
		break;
		
		case GUEST_CART_MENU : customerCartMenu();
		
		case GUEST_CART_LIST : customer.cartList();
		break;
		
		case ADMIN_GOODS_LIST : admin.productList();
		break;
		
		case ADMIN_GOODS_ADD : admin.productAdd();
		break;
		
		case ADMIN_GOODS_UPDATE : admin.productUpdate();
		break;
		
		case ADMIN_GOODS_DEL : admin.productRemove();
		break;
		
		case ADMIN_ORDER_LIST : admin.orderList();
		break;
		
		case ADMIN_ORDER_CONFIRM : admin.orderConfirm();
		break;
		
		case ADMIN_ORDER_CANCEL : admin.orderCancel();
		break;
		
		case GUEST_GOODS : admin.productList();
		break;
		
		case GUEST_CART_ADD : customer.cartAdd();
		break;
		
		case GUEST_CART_DEL : customer.cartRemove();
		break;
		
		case GUEST_CART_BUY : customer.cartBuy();
		break;
		
		case GUEST_NOW_BUY : customer.nowBuy();
		break;
		
		case GUEST_REFUND : customer.refund();
		break;
		
		default : 
			System.out.println("존재하지 않는 메뉴입니다.");
		}
	}

	@Override
	// ------------------------------------------------초기화면
	public void loginMenu() {
		System.out.println("      kosmo DrugStore에 오신 것을 환영합니다!      ");
		System.out.println("────────────────────로그인───────────────────");
		System.out.println("1. 관리자   2. 고객   3. 회원가입   4. 종료");
		System.out.println("───────────────────────────────────────────");
		System.out.print("이용하려는 서비스의 번호를 입력해주세요 : ");
		
		int lonum = 0;
		try {
			while(true) {
				lonum = input.nextInt();
				switch(lonum){
				case 1 : commonMenu(ADMIN_MENU_LOGIN);
						 break;
						 
				case 2 : commonMenu(GUEST_MENU_LOGIN);
						 break;
				
				case 3 : register();
						 break;
				
				case 4 : 
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
					break;
					
				default : System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
				break;
				}
			}
			
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
			
		}
	}
	
	String customerID;
	String customerPW;
	
	String adminID;
	String adminPW;
	
	@Override
	// ------------------------------------------------ 손님 회원가입 : 한글 x 영어, 숫자로 이루어진 4자리 이상의 아이디
	public void register() {
		System.out.println("───────────────────회원가입───────────────────");
		try {
			while(true) {
				boolean b;
				b = false;
				System.out.print("ID(4자 이상) : ");
				customerID = input.next();
				for(int i = 0; i<customerID.length(); i++) {
				char a = customerID.charAt(i);
				int asc = (int)a;	// 아스키코드로 customerID 변경
				
				if ((96<asc)&&(asc<123)||(47>asc && asc<58)) {	// if 아스키코드가 소문자 또는 숫자일 때 while문 정지
					b = false;
					break;
			} else {
				System.out.println("영문 또는 숫자만 입력 가능합니다.");
				b = true;
				break;
				}
			}
			if(b==false) {
				if(customerID.length() < 4) {
					System.out.println("아이디는 4자 이상 입력해 주십시오");
					
				} else if(customerID.equals(Admin.ID)){
					System.out.println("입력하신 아이디를 사용하실 수 없습니다.");

				} else if(cusmap.containsKey(customerID)) {
					System.out.println("이미 존재하는 아이디입니다.");
				} else {
					System.out.print("원하시는 비밀번호를 입력하세요 : ");
					customerPW = input.next();
					System.out.println("환영합니다! " + customerID + " 님의 회원가입이 완료되었습니다.");
					System.out.println("───────────────────────────────────────────");
					cusmap.put(customerID, customerPW);
					commonMenu(SHOP_LOGIN);
					break;
				}
			}
		}
	} catch(Exception e) {
		System.out.println("아이디는 영문과 숫자로 이루어진 4자 이상으로 입력 가능합니다. 다시 입력해 주십시오");
		register();
	}
	}

	@Override
	// ------------------------------------------------관리자 로그인
	public void adminLogin() {
		System.out.println("─────────────────관리자 로그인─────────────────");
		Map <String, String> adminmap = new HashMap<String, String>();
		adminmap.put(Admin.ID,Admin.PASSWORD);
		try {
			do {
				System.out.println("ID를 입력하세요 [q 입력 시 종료] : ");
				String adminid = input.next();
				if(adminid.equals("q")||adminid.equals("Q")) {
					loginMenu();
				} else if(adminid.equals(Admin.ID)) {
					System.out.println("패스워드를 입력하세요 : ");
					String adminpw = input.next();
					if(adminpw.equals(adminmap.get(Admin.ID))) {
						break;
					} else {
						System.out.println("비밀번호 불일치");
					} 
				} else {
					System.out.println("ID가 일치하지 않습니다.");
					commonMenu(ADMIN_MENU_LOGIN);
				}
			} while(true);
			System.out.println("관리자 로그인 성공! 관리자 페이지로 이동합니다.");
			System.out.println("───────────────────────────────────────────");
			commonMenu(ADMIN_MENU);
		} catch(Exception e) {
			System.out.println("잘못된 아이디입니다. 관리자에게 문의하십시오");
		}
	}
	
	@Override
	// ------------------------------------------------ 고객 로그인
	public void customerLogin() {
		cusmap.put(customerID,customerPW);
		try {
			do {
				System.out.print("ID를 입력하세요 [이전 : q] ");
				customerID = input.next();
				if(customerID.equals("q")||customerID.equals("Q")) {
					loginMenu();
				} else if(customerID.equals(customerID)) {
					System.out.print("패스워드를 입력하세요 : ");
					customerPW = input.next();
					if(customerPW.equals(cusmap.get(customerID))) {
						System.out.println("로그인 성공!");
						break;
					} else {
						System.out.println("비밀번호 불일치");
					} 
				} else {
					System.out.println("ID가 일치하지 않습니다.");
				}
			} while(true);
			System.out.println( customerID + "님 반갑습니다.");
			customerMenu();
		} catch(Exception e) {
			System.out.println("회원가입 관련 오류는 관리자에게 문의하십시오");
		}
	}

	@Override
	// ------------------------------------------------관리자 메뉴
	public void adminMenu() {
		System.out.println("──────────────────관리자 메뉴──────────────────");
		System.out.println("1. 재고관리     2. 주문관리     3. 결산     4. 로그아웃 ");
		System.out.print("이용하려는 서비스의 번호를 눌러주세요 : ");
		
		while(true)
		try {
			int adminNum = input.nextInt();
			switch(adminNum) {
			case 1 : commonMenu(ADMIN_STOCK_MENU);
			break;
			
			case 2 : commonMenu(ADMIN_ORDER_MENU);
			break;
			
			case 3 : commonMenu(ADMIN_ORDER_TOTAL);
			break;
			
			case 4 : System.out.println("로그아웃이 완료되었습니다.");
			commonMenu(SHOP_LOGIN);
			break;
			
			default : 
			break;
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			input.nextInt();
			adminMenu();
		}
	}
	

	@Override
	// ------------------------------------------------관리자 재고관리
	public void adminStockMenu() {
		System.out.println("──────────────────재고관리 메뉴──────────────────");
		System.out.println("1. 목록     2. 추가     3. 수정     4. 삭제     5. 이전 ");
		System.out.print("이용하려는 서비스의 번호를 눌러주세요 : ");
		while(true)
		try {
			int stocknum = input.nextInt();
			switch(stocknum) {
			//case 1 : admin.productList();
			case 1 : commonMenu(ADMIN_GOODS_LIST);
			break;
			
			case 2 : admin.productAdd();
			break;
			
			case 3 : admin.productUpdate();
			break;
			
			case 4 : admin.productRemove();
			break;
			
			case 5 : adminMenu();
			break;
			
			default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			break;
			
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			input.next();
			adminMenu();
		}
	}

	@Override
	// ------------------------------------------------관리자 주문관리
	public void adminAdminMenu() {
		System.out.println("────────────────────────주문관리────────────────────────");
		System.out.println("1. 주문목록     2. 결제승인     3. 결제취소     4. 이전");
		System.out.print("이용하려는 서비스의 번호를 눌러주세요 : ");
		while(true)
		try {
			int adminnum = input.nextInt();
			switch(adminnum) {
			case 1 : admin.orderList();
			break;
			
			case 2 : admin.orderConfirm();
			break;
			
			case 3 : admin.orderCancel();
			break;
			
			case 4 : adminMenu();
			break;
			
			default : 
			break;

			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			input.next();
			adminMenu();
		}
	}

	@Override
	// ------------------------------------------------결산 
	public void adminTotalMenu() {
		// 결산을 위한 메서드 호출
		admin.saleTotal();
	}

	@Override
	// ------------------------------------------------ 고객메뉴(초기)
	public void customerMenu() {
		System.out.println("────────────────────────고객메뉴────────────────────────");
		admin.mapList();
		System.out.println("1. 장바구니     2. 구매     3. 환불     4. 로그아웃 ");
		System.out.print("이용하려는 서비스의 번호를 눌러주세요 : ");
		while(true) {
		try {
			int customNum = input.nextInt();
			switch(customNum) {
			case 1 : customerCartMenu();
			break;
			
			case 2 : customer.nowBuy();
			break;
			
			case 3 : customer.refund();
			break;
			
			case 4 : System.out.println("로그아웃이 완료되었습니다.");
			loginMenu();
			break;
			
			default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			break;

			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			input.next();
			customerMenu();
			}
		}
		
	}

	@Override
	// ------------------------------------------------고객 장바구니 메뉴
	public void customerCartMenu() {
		System.out.println("1. 장바구니 목록     2. 장바구니 추가     3. 장바구니 삭제     4. 장바구니 구매     5. 이전 ");
		System.out.print("이용하려는 서비스의 번호를 눌러주세요 : ");
		while(true)
		try {
			int cartNum = input.nextInt();
			switch(cartNum) {
			
			case 1 : customer.cartList();
			break;
			
			case 2 : customer.cartAdd();
			break;
			
			case 3 : customer.cartRemove();
			break;
			
			case 4 : customer.cartBuy();
			break;
			
			case 5 : customerMenu();
			break;
			
			default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			break;
			
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
		}
	}
}