package javaexp.com.threadExp;

public class ThreadSafetyExp1 {
	
	public static void main(String[] args) {
		Account a = new Account();
		WithdrawMoney w1 = new WithdrawMoney(a);

		Thread t1 = new Thread(w1);
		Thread t2 = new Thread(w1);
		t1.setName("Ahmet");
		t2.setName("Naciye");

		t1.start();
		t2.start();
	}

}

class WithdrawMoney implements Runnable {

	private Account a;

	public WithdrawMoney(Account al) {
		a = al;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			a.withdrawMoney(10);
		}
	}
}

class Account {
	
	private int balance = 50;

	public int getBalance() {
		return balance;
	}

	public void withdrawMoney(int money) {
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		synchronized (this) {
			if (balance > 0) {
				balance = balance - money;
				System.out.println(Thread.currentThread().getName() + " 10 lira çekti.Kalan bakiye : " + balance);
			} else {
				System.out.println("kalan para : " + balance + " Para Bitti " + Thread.currentThread().getName());
			}
		}
	}
}
