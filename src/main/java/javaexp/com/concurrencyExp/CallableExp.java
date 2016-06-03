package javaexp.com.concurrencyExp;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class CallableExp {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CallClass c1 = new CallClass();
		CallClass c2 = new CallClass();
		
		ExecutorService e = Executors.newCachedThreadPool();
		Future<Integer> f1 = e.submit(c1);
		System.out.println(f1.get());
		Future<Integer> f2 = e.submit(c2);
		System.out.println(f2.get());
		
		
		e.shutdownNow();
	}
}


class CallClass implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
	
		int val = ThreadLocalRandom.current().nextInt(1, 50);
		
		for (int i = 0; i < val; i++) {
			System.out.println(Thread.currentThread().getName()  + " Thread running... " + i);
		}
		
		return val;
	}
	
}