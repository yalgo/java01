package model;

//DTO(Data Transfer Object) : 데이터 전송 객체
/*
 * 작성일자 : 2022-07-15
 * 작성자 : 김진아
 * 멤버변수 : 제품의 이름과 종류, 가격, 유통기한, 수량
 * set 및 get으로 메서드 작성
 * toString() 오버라이딩 하여 제품정보 반환
 * AdminImpl 및 customerImpl에서 매개변수 생성자를 통해 값 전달받음
 */

public class Supplement {
	
	private String name;
	private String kind;
	private int price;
	private String exp;
	private int amount; 
	
	private Supplement() {}
	private static Supplement instance = new Supplement();
	
	public static Supplement getInstance() {
		if(instance == null) {
			instance = new Supplement();
		}
		return instance;
	}
	
	// 디폴트생성자
	public Supplement(String name, String kind, int price, String exp, int amount) {
		this.name = name;	// 약이름
		this.kind = kind;	// 종류(액체/알약)
		this.price = price;	// 가격
		this.exp = exp;	// 유통기한
		this.amount = amount; // 개수
	}

	
	// 멤버메서드
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getKind() {
		return kind;
	}
	
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	public String getExp() {
		return exp;
	}
	
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	
	@Override
	public String toString() {
		//return "제품명 : " + name + ", 종류 : " + kind + ", 가격 : " + price + ", 유통기한 : " + exp + ", 재고 : " + amount;
		return name+"\t"+kind+"\t"+exp+"\t"+price+"\t"+amount+"\t";
	}
}
