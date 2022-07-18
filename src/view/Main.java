package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Supplement;

public class Main {

	static Scanner input = new Scanner(System.in);

	
	public static void main(String[] args) {
		// 로그인 메뉴
		// loginMenu(); 진입 
		MenuImpl callMenu = new MenuImpl();
		
		 callMenu.loginMenu();
		// callMenu.adminStockMenu();
		//callMenu.customerMenu();
		
	}
}
