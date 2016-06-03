package javaexp.com.concurrencyExp;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableExp {

	public static void main(String[] args) throws InterruptedException {
		Counter c = new CounterNormal();
		IncThread t1 = new IncThread(c);
		IncThread t2 = new IncThread(c);

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(c.getCounter());
	}

}

abstract class Counter{
	
	public abstract int getCounter();
	public abstract void incCounter();
}

class CounterNormal extends Counter{
	private int counter;

	public void incCounter() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}
}

class CounterAtomic extends Counter{
	private AtomicInteger counter = new AtomicInteger();

	public void incCounter() {
		counter.getAndIncrement();
	}

	public int getCounter() {
		return counter.get();
	}
}

class IncThread extends Thread {
	private Counter count;

	public IncThread(Counter c) {
		count = c;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			count.incCounter();
		}
	}
}