package javaexp.com.threadExp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ThreadSafetyExp2 {

	public static void main(String[] args) throws InterruptedException {
		Namelist n = new Namelist();
		
		BookListController bc1 = new BookListController(n, false);
		BookListController bc2 = new BookListController(n, true);
		BookListController bc3 = new BookListController(n, true);
		
		Thread t1 = new Thread(bc1);
		Thread t1_1 = new Thread(bc1);
		Thread t1_2 = new Thread(bc1);
		Thread t1_3 = new Thread(bc1);
		Thread t2 = new Thread(bc2);
		Thread t2_1 = new Thread(bc2);
		Thread t2_2 = new Thread(bc2);
		Thread t3 = new Thread(bc3);
		Thread t3_1 = new Thread(bc3);
		Thread t3_2 = new Thread(bc3);
		
		t1.start();
		t1_1.start();
		t1_2.start();
		t1_3.start();
		t1_3.join();
		t2.start();
		t2_1.start();
		t2_2.start();
		t3.start();
		t3_1.start();
		t3_2.start();
	}
	
}

class BookListController implements Runnable {
	private Namelist nl;
	private boolean forDel;
	
	public BookListController(Namelist n,boolean forDelete) {
		nl = n;
		forDel = forDelete;
	}
	
	public void run() {
		if (forDel) {
			for (int i = 0; i < 10; i++) {
				nl.removeFirst();
			}
		} else {
			for (int i = 0; i < 10; i++) {
				nl.add("İsim İşte Hacı. " + i + " " + Thread.currentThread().getName());
			}

		}
	}
} 

class Namelist {
	
	private List<String> bookList = Collections.synchronizedList(new LinkedList<String>());
	
	public void add(String name){
		bookList.add(name);
	}
	
	public void removeFirst() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (bookList.size()>0) {
			System.out.println(bookList.remove(0) + " silindi.....");
		} else {
			System.out.println("SİLİNECEK BİRŞEY BULUNMUYOR...");
		}
	}
}