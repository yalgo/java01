package controller;

import java.util.*;
import model.Supplement;
import view.MenuImpl;

public class CustomerImpl implements Customer {
	
	public static CustomerImpl instance = new CustomerImpl();
	public static MenuImpl menu = new MenuImpl();
	public static AdminImpl admin = AdminImpl.getInstance();
	
	// 싱글톤
	private CustomerImpl() {}
	public static CustomerImpl getInstance(){
		if(instance == null) {
			instance = new CustomerImpl();
		}
		return instance;
	}
	Scanner input = new Scanner(System.in);

	// HashMap
	Map<Integer,Supplement> cart = new HashMap<Integer,Supplement>();	// 장바구니 맵
	Map<Integer,Supplement> buy = new HashMap<Integer,Supplement>();	// 구매요청 맵
	Map<Integer,Supplement> cancel = new HashMap<Integer,Supplement>();	// 환불요청 맵
	
	@Override
	// ------------------------------------------------장바구니 리스트
	public void cartList() {
		mapCartList();
		System.out.println("[이전 메뉴 : 0]");
		try {
			while(true) {
				int qexit = input.nextInt();
				if(qexit == 0) {
					menu.customerMenu();
				} else {
					System.out.println("잘못된 입력입니다.");
					}
				}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}

	@Override
	// ------------------------------------------------장바구니 추가
	public void cartAdd() {
		admin.mapList();
		// 상품 목록을 출력
		try {
			while(true) {
				System.out.print("장바구니에 추가할 제품코드[이전 : 0] :");	
				int cartadd = input.nextInt();
				if(cartadd == 0) {
				cartList();
				} else if(admin.map.containsKey(cartadd)) {
					System.out.println("제품 수량 : ");
					// 구매 수량을 count로 설정하고, 입력값을 받음.
					int count = input.nextInt();		
					// 장바구니 맵에 cartadd제품과, 그에 따른 벨류값을 매개변수 생성자를 이용하여 추가한다.
					cart.put(cartadd, new Supplement(
							admin.map.get(cartadd).getName(), admin.map.get(cartadd).getKind(),
							admin.map.get(cartadd).getPrice(),admin.map.get(cartadd).getExp(),count));
					System.out.println("───────────────────추가완료───────────────────");
					menu.customerMenu();
					}
				System.out.println(cartadd + "번호가 목록에 없습니다.");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}

	@Override
	// ------------------------------------------------장바구니 삭제
	public void cartRemove() {
		mapCartList();
		try {
			while(true) {
				System.out.print("삭제할 제품 코드[이전 : 0] : ");
				int deletekey = input.nextInt();
				if(deletekey == 0) {
					cartList();
					// 장바구니에 있던 제품 중 deletekey에 입력한 코드 삭제
				} else if(cart.containsKey(deletekey)) {
					cart.remove(deletekey);
					System.out.println(deletekey+"번 제품이 장바구니에서 삭제되었습니다.");
					menu.customerMenu();
				}
				System.out.println(deletekey + "번호가 목록에 없습니다.");
				}
		} catch (Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}	
	
	@Override
	// ------------------------------------------------장바구니 구입
	public void cartBuy() {
		mapCartList();
		try {
			while(true) {
				System.out.print("구매할 제품 코드[이전 : 0] : ");
				int cartbuykey = input.nextInt();
				if(cartbuykey == 0) {
					menu.customerCartMenu();
				}else if(cart.containsKey(cartbuykey)) {
					// 장바구니에서 구입한 제품은 cart맵에서 buy 맵으로 이동 / 이동 후에 기존에 있던 위치에서 삭제
					// 실제 구매되는건 관리자가 승인한 이후기 때문에 수량에는 변화가 없음
					buy.put(cartbuykey, new Supplement(
							admin.map.get(cartbuykey).getName(), admin.map.get(cartbuykey).getKind(),
							admin.map.get(cartbuykey).getPrice(),admin.map.get(cartbuykey).getExp(),cart.get(cartbuykey).getAmount()));
					
					System.out.println(cartbuykey+"번 제품의 구매 요청이 완료되었습니다.");
					cart.remove(cartbuykey);
					menu.customerMenu();
				} else 
				System.out.println(cartbuykey + "번 제품이 목록에 없습니다.");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}
	
	// ------------------------------------------------ 바로구입
	@Override
	public void nowBuy() {
		admin.mapList();
		try {
			while(true) {
				System.out.print("구매할 제품 코드[이전 : 0] : ");
				int buykey = input.nextInt();
				if(buykey == 0) {
					menu.customerMenu();
					// 장바구니를 거치지 않고 map에서 바로 buy로 데이터(수량)이동
					// 실제 구매되는건 관리자가 승인한 이후기 때문에 수량에는 변화가 없음
				} else if(admin.map.containsKey(buykey)) {
					System.out.print("제품 수량 : ");
					int count = input.nextInt();
					buy.put(buykey, new Supplement(
							admin.map.get(buykey).getName(), admin.map.get(buykey).getKind(),
							admin.map.get(buykey).getPrice(),admin.map.get(buykey).getExp(),count)); // 구매한 수량 count값을 넣었음
					System.out.println(buykey+"번 제품의 구매 요청이 완료되었습니다.");
					menu.customerMenu();
				}
				System.out.println(buykey+"번 제품이 목록에 없습니다.");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}

	@Override
	// ------------------------------------------------ 환불요청
	public void refund() {
		admin.confirmList();
		// 관리자가 구매를 승인한 제품만이 환불 대상으로서 목록에 출력됨
		while(true) {
			System.out.println("환불하실 제품 코드[이전 : 0] : ");
			int buybackkey = input.nextInt();
			if(buybackkey == 0) {
				menu.customerMenu();
			} else if(admin.confirm.containsKey(buybackkey)) {
				// confirm(구매완료)에 있던 데이터가 cancel(환불대기) map으로 이동
				cancel.put(buybackkey, new Supplement(
					admin.confirm.get(buybackkey).getName(), admin.confirm.get(buybackkey).getKind(),
					admin.confirm.get(buybackkey).getPrice(),admin.confirm.get(buybackkey).getExp(),admin.confirm.get(buybackkey).getAmount()));
				System.out.println("해당 제품의 환불 요청이 완료되었습니다.");
				admin.confirm.remove(buybackkey);
				menu.customerMenu();
			}
		}
	}
	// ------------------------------------------------ 생성한 hashMap을 출력하는 메서드를 따로 생성해서 활용
	public void mapCartList() {
		System.out.println("────────────────────────장바구니────────────────────────");
		if(cart.isEmpty()) {
		System.out.println("장바구니가 비어있습니다.");
		}
		for(Map.Entry<Integer,Supplement> cart : cart.entrySet()) {
			System.out.println(cart.getKey() + "\t" + cart.getValue());
			}
		System.out.println("─────────────────────────────────────────────────────");
		} 
	
	public void mapcartbuyList() {
		System.out.println("────────────────────────구입목록────────────────────────");
		for(Map.Entry<Integer,Supplement> buy : buy.entrySet()) {
			System.out.println(buy.getKey() + "\t" + buy.getValue());
			}
		}
	
	public void cancelList() {
		System.out.println("────────────────────────환불요청────────────────────────");
		for(Map.Entry<Integer,Supplement> cancel : cancel.entrySet()) {
			System.out.println(cancel.getKey() + "\t" + cancel.getValue());
			}
		}
	}

