package javaexp.com.threadExp;

public class ThreadSafetyExp3 {

	
	public static void main(String[] args) {
		
		Operator o = new Operator();
		Machine m = new Machine(o);
		System.out.println(o.getId());
		System.out.println(m.getId());
		o.start();
		m.start();
		
	}
}


class Operator extends Thread {

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("Getting Shape From User..");
			synchronized (this) {
				try {
					Thread.sleep(500);
					System.out.println("Calculating new machine steps for new shape...");
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class Machine extends Thread {
	Operator opr;
	public Machine(Operator o) {
		opr = o;
	}
	
	public void run() {
		while(true) {
			
			synchronized (opr) {
				try {
					opr.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Sending Machine Steps to hardware");
		}
	}
}