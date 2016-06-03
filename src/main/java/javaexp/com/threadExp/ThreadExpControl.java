package javaexp.com.threadExp;

public class ThreadExpControl {

	
	public synchronized static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread();
		t.start();
		System.out.println("X");
		System.out.println("Y");
	}
}
