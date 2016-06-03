package javaexp.com.threadExp;

public class ThreadSafetyExp4 {

	public static void main(String[] args) {
		Calculator c = new Calculator();
		new Reader(c).start();
		new Reader(c).start();
		new Reader(c).start();
		new Reader(c).start();
	}
}

class Reader extends Thread {

	Calculator c;

	public Reader(Calculator cal) {
		c = cal;
	}

	@Override
	public void run() {
		synchronized (c) {
			try {
				System.out.println("Waiting for calculation...");
				c.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Total is : " + c.total);
		}
	}

}

class Calculator implements Runnable {
	int total;

	public void run() {
		synchronized (this) {
			for (int i = 0; i < 30; i++) {
				total += i;
			}
		}
		System.out.println("Total is : " + total);
		notifyAll();
	}

}