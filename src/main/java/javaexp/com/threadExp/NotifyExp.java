package javaexp.com.threadExp;

public class NotifyExp {

	public static void main(String[] args) {
		Threadb t = new Threadb();
		t.start();
		
		synchronized (t) {
			try {
				System.out.println("waiting t for finish...");
				t.wait();
				System.out.println("waiting t end...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


class Threadb extends Thread {
	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 30; i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(i+1 + " ");
			}
			System.out.println("");
			notify();
		}
	}
}