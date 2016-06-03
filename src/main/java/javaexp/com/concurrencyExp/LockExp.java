package javaexp.com.concurrencyExp;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExp {

	public static void main(String[] args) throws InterruptedException {
		CountIt c = new CountItWithLock();
		IncrThread t1 = new IncrThread(c);
		IncrThread t2 = new IncrThread(c);

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(c.getCounter());
	}
}

abstract class CountIt {
	
	abstract public void incCounter() ;
	abstract public int getCounter();
}

class CountItWithLock extends CountIt{
	private int counter;
	Lock l = new ReentrantLock();
	
	public void incCounter() {
		
		try {
			boolean locked = l.tryLock(5,TimeUnit.SECONDS);
			if (locked) {
				try {
					counter++;
				} finally {
					if(locked)
						l.unlock();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getCounter() {
		return counter;
	}
}

class CountItWithSyn extends CountIt{
	private int counter;

	public void incCounter() {
		synchronized (this) {
			counter++;
		}
	}

	public int getCounter() {
		return counter;
	}
}

class IncrThread extends Thread {
	private CountIt count;

	public IncrThread(CountIt c) {
		count = c;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			count.incCounter();
		}
	}
}