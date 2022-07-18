package controller;

/*
 * 일자 : 2022. 7. 15.
 * 작성자 : 김진아
 * 
 * 관리자의 인터페이스를 구현하는 클래스
 *  - 판매 품목 관리
 *    1) 관리자 메뉴에서 추가하는 제품 외 3개의 기본 제품목록 생성(호출 시 랜덤키 부여되어 생성됨)
 *  - 고객의 주문 관리
 *    1) 고객이 장바구니에 넣고(cartmap), 장바구니 결제한 제품과 바로구매한 제품을 (confirmmap)으로 put
 *    2) 구매 확정된 confirmmap의 제품 중에서 고객이 환불요청할 시 remove 
 *  - 상품 결산 
 *  - 0개의 맵 생성
 *    1) map : 기존 상품 목록
 *    2) confirm : 구매확정된 제품들의 목록
 *    3) 작업을 위한 hashMap 출력 메서드 생성(상품목록 mapList() , 구매신청된 목록 maporderList() , 구매확정목록 confirmList())
 *    
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import model.Supplement;
import view.MenuImpl;
public class AdminImpl implements Admin {

	private AdminImpl() {}
	public static AdminImpl getInstance() {
		if(instance == null) {
			instance = new AdminImpl();
		}
		return instance;
	}
	public static AdminImpl instance = new AdminImpl();
	
	// 싱글톤 활용
	public static MenuImpl menu = new MenuImpl();
	public static CustomerImpl customer = CustomerImpl.getInstance();
	public static Supplement supplement = Supplement.getInstance();
	
	
	Scanner input = new Scanner(System.in);
	
	//HashMap
	Map<Integer,Supplement> map = new HashMap<Integer,Supplement>();
	Map<Integer,Supplement> confirm = new HashMap<Integer,Supplement>();
	
	
	// 관리자가 상품을 직접 입력을 통해 추가하기 전 인스턴스 생성 
	Supplement sup1 = new Supplement("유산균", "가루형", 13000, "2024-06-30", 200);
	Supplement sup2 = new Supplement("비타민D", "알약", 15000, "2024-08-31", 150);
	Supplement sup3 = new Supplement("종합비타민", "구미젤리", 18000, "2024-05-31", 200);
	
	//int codNum = (int)(Math.random()*1000)+1000;

	
	@Override
	// ------------------------------------------------품목 리스트
	public void productList() {

		// map을 출력하는 메서드 호출
		mapList();
		
		
		System.out.println("이전 메뉴 : 0번");
		while(true) {
			int qexit = input.nextInt();
			if(qexit == 0) {
			menu.adminStockMenu();
			} else {
			System.out.println("이전화면은 0번을 눌러주십시오");
			}
		}
	}
	
	@Override
	// ------------------------------------------------관리자 재고관리 - 재고추가
	public void productAdd() {
		System.out.println("────────────────────────상품추가────────────────────────");
		
		System.out.print("제품명 :");	
		String name = input.next();
		
		System.out.print("종류 : ");
		String kind = input.next();
		
		System.out.println("유통기한 : ");
		String exp = input.next();
		
		System.out.print("가격 : ");
		int price = input.nextInt();
		
		System.out.print("수량 : ");
		int amount = input.nextInt();
		
		// 입력받은 항목을 new Supplement 인스턴스에 매개변수로 추가하고, 이를 map에 put (랜덤키값)
		Supplement addsup = new Supplement(name,kind,price,exp,amount);
		
		map.put((int)(Math.random()*1000)+1000,addsup);
		
		System.out.println("────────────────────────추가완료────────────────────────");
		// 추가한 후에 상품 리스트 보여주기
		productList();
	}

	@Override
	// ------------------------------------------------ 관리자 재고관리 - 업데이트(수정)
	public void productUpdate() {
		System.out.println("────────────────────────업데이트────────────────────────");
		mapList();
			System.out.print("업데이트할 상품의 코드를 입력하세요 [이전 : 0]  : ");
			int updatekey = input.nextInt();
			if(updatekey == 0) {
				menu.adminStockMenu();
			} else if (map.containsKey(updatekey)){
				System.out.print("제품명을 입력하세요 : ");
				String updName = input.next();
				System.out.print("종류를 입력하세요 : ");
				String updkind = input.next();
				System.out.print("가격을 입력하세요 : ");
				int updprice = input.nextInt();
				System.out.print("유통기한을 입력하세요 : ");
				String updexp = input.next();
				System.out.println("수량을 입력하세요 : ");
				int updamount = input.nextInt();
				
				// 입력받은 데이터를 map 해쉬맵의 벨류값으로 업데이트(수정)
				map.put(updatekey,new Supplement(updName, updkind, updprice, updexp, updamount));
				System.out.println(updatekey+"번 제품 업데이트가 완료되었습니다.");
			}
			menu.adminStockMenu();
	}
	
	@Override
	// ------------------------------------------------관리자 재고관리 - 품목 삭제
	public void productRemove() {
		mapList();
		try {
			System.out.println("삭제할 제품 코드 [이전 메뉴 : 0번] : ");
			while(true) {
			int deletekey = input.nextInt();
			if(deletekey == 0) {
				menu.adminStockMenu();
			} else if(map.containsKey(deletekey)) {
				map.remove(deletekey);
				System.out.println(deletekey+"번 제품이 삭제되었습니다.");
				menu.adminStockMenu();
			} 
			System.out.println(deletekey + "번 제품이 존재하지 않습니다. 다시 입력해 주십시오");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}
	

	
	@Override
	public void orderList() {
		// 고객이 구입 완료(장바구니 구매, 바로구매)한 목록의 창 출력
		maporderList();
		while(true) {
			int qexit = input.nextInt();
			if(qexit == 0) {
				menu.adminAdminMenu();
			} else {
			System.out.println("이전화면은 0번을 눌러주십시오");
			}
		}
	}
	@Override
	public void orderConfirm() {
		maporderList();
		System.out.println("구매 승인 제품 코드 [이전 메뉴 : 0번] : ");
		// 목록을 다시 출력해서 코드 출력
		try {
			while(true) {
			int confirmkey = input.nextInt();
			if(confirmkey == 0) {
				menu.adminAdminMenu();
				// confirm(구매완료)로 해당 키값에 따른 영양제를 넣어줌 
			} else	if(customer.buy.containsKey(confirmkey)) {
				confirm.put(confirmkey, new Supplement(
						map.get(confirmkey).getName(), map.get(confirmkey).getKind(),
						map.get(confirmkey).getPrice(),map.get(confirmkey).getExp(),customer.buy.get(confirmkey).getAmount()));
				System.out.println(confirmkey+"번 제품의 구매 처리가 완료하였습니다.");
				// 새로운 변수 생성 : 제품의 현 재고는  원래의 제품 개수 - 구입한 제품 개수를 차감
				// 차감한 제품 개수를 포함한 supplement의 정보를 다시 map의 벨류값에 replace
				int newAmount = map.get(confirmkey).getAmount()-customer.buy.get(confirmkey).getAmount();
				map.replace(confirmkey , new Supplement(
						confirm.get(confirmkey).getName(),confirm.get(confirmkey).getKind(),
						confirm.get(confirmkey).getPrice(),confirm.get(confirmkey).getExp(),newAmount));
				customer.buy.remove(confirmkey);
				menu.adminAdminMenu();
				}
			System.out.println(confirmkey + "번 제품이 목록에 없습니다. 다시 입력해 주십시오");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}

	@Override
	public void orderCancel() {
		customer.cancelList();
		try {
			System.out.println("환불 처리 제품 코드 [이전 메뉴 : 0번] : ");
			while(true) {
			int cancelkey = input.nextInt();
			// 환불 후 제품의 총 수량 : 고객이 요청한 환불 수량(기존에 구매했던 수량) + 기존 제품의 수량
			int newAmount = customer.cancel.get(cancelkey).getAmount() + map.get(cancelkey).getAmount();
			if(cancelkey == 0) {
				menu.adminAdminMenu();
			} else	if(customer.cancel.containsKey(cancelkey)) {
				// 상품리스트 맵에 newAmount값을 넣어 value값 수정
				map.replace(cancelkey, new Supplement(
						map.get(cancelkey).getName(), map.get(cancelkey).getKind(),
						map.get(cancelkey).getPrice(),map.get(cancelkey).getExp(),newAmount));
				System.out.println(cancelkey+"번 제품의 환불 처리가 완료하였습니다.");
				// 수정 후 환불대기목록에서 해당 제품 삭제
				customer.cancel.remove(cancelkey);
				menu.adminAdminMenu();
				}
			System.out.println(cancelkey + "번 제품이 목록에 없습니다. 다시 입력해 주십시오");
			}
		} catch(Exception e) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
		}
	}

	@Override
	public void saleTotal() {
		System.out.println("────────────────────────총 결산────────────────────────");
		
	     int sum = 0;
	     Iterator<Integer> keys = confirm.keySet().iterator();
	     while(keys.hasNext()) {
	     int key = keys.next();
	     // Iterator를 활용하여 구매 완료한 맵(confirm)맵을 순회하며 수량(getAmount())와 가격(getPrice())을 추출하고 서로 곱해서 더하는 방식으로 결산금액 산출
	     Supplement value = confirm.get(key);
	     sum += confirm.get(key).getAmount() * confirm.get(key).getPrice();
	      }
	      System.out.println("결산 : " + sum + "원");		
	      menu.adminMenu();
	}
	// ------------------------------------------------ 생성한 hashMap을 출력하는 메서드를 따로 생성해서 활용
	// ------------------------------------------------ 관리자가 관리(추가/삭제)하는 상품 리스트 hashmap 출력 메서드
	public void mapList() {
		
		if(map.isEmpty()) {
			// 상품 리스트가 비어있을 경우 미리 생성한 영양제 인스턴스를 넣어준다. 
			// 이유 : 고객이 로그인했을때 제품이 몇가지 보이면 쇼핑몰의 느낌을 살릴 수 있을 것 같아서..^^ 
		map.put((int)(Math.random()*1000)+1000, sup1);
		map.put((int)(Math.random()*1000)+1000, sup2);
		map.put((int)(Math.random()*1000)+1000, sup3);
		}

		System.out.println("────────────────────────상품목록────────────────────────");
		System.out.println("번호\t제품명\t종류\t유통기한\t\t가격\t수량\t");
		System.out.println("─────────────────────────────────────────────────────");

		for(Map.Entry<Integer,Supplement> map : map.entrySet()) {
			Integer key = map.getKey();
			Supplement value = map.getValue();
			System.out.println(key + "\t" + value);
		}
		System.out.println("─────────────────────────────────────────────────────");
	}
	// ------------------------------------------------ 손님의 장바구니결제, 바로결제를 승인해준 최종 결제 hashmap 출력메서드
	public void confirmList() {
		for(Map.Entry<Integer,Supplement> confirm : confirm.entrySet()) {
			Integer key = confirm.getKey();
			Supplement value = confirm.getValue();
			System.out.println(key + "\t" + value);
		}
	}
	
	// ------------------------------------------------ 손님의 장바구니결제 및 바로결제 후 승인 대기중인 hashmap 출력메서드
	public void maporderList() {
		System.out.println("───────────────────주문리스트[이전 : 0]───────────────────");
		for(Map.Entry<Integer,Supplement> crt : customer.buy.entrySet()) {
			System.out.println(crt.getKey() + "\t" + crt.getValue());
			}
		System.out.println("─────────────────────────────────────────────────────");
	}
}
