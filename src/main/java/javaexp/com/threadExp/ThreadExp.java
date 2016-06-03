package javaexp.com.threadExp;

public class ThreadExp {

	public static void main(String[] args) {

		Thread1 tr1 = new Thread1();
		Thread2 tr2 = new Thread2();
		Thread3 tr3 = new Thread3();

		Thread t1 = new Thread(tr1);
		Thread t2 = new Thread(tr2);
//		Thread t3 = new Thread(tr3);

		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.NORM_PRIORITY);
//		t3.setPriority(Thread.MIN_PRIORITY);
		
		t1.start();
		t2.start();
//		t3.start();
	}

}

class Thread1 implements Runnable {

	public void run() {

		for (int i = 0; i < 5000000; i++) {
			System.out.println("Thread1 state : " + Thread.currentThread().getState() + " 1111111111111111");
		}
		
	}

}

class Thread2 implements Runnable {

	public void run() {

		for (int i = 0; i < 500; i++) {
			System.out.println("Thread2 state : " + Thread.currentThread().getState() + " 2222222222222222222");
		}
	}

}

class Thread3 implements Runnable {

	public void run() {

		for (int i = 0; i < 500; i++) {
			System.out.println("Thread3 state : " + Thread.currentThread().getState() + " 3333333333333333333333");
		}
	}

}