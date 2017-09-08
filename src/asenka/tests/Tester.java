package asenka.tests;

import java.util.LinkedList;

public class Tester {

	
	public static void main(String[] args) {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		System.out.println(list);
		
		String A = list.remove(0);
		String E = list.remove(3);
		
		list.add(0, E);
		list.add(4, A);
		
		
		System.out.println(list);
	}

}
