package javaexp.com.concurrencyExp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExp {

	public static void main(String[] args) {
		AddToList c = new AddToSyncList2();
		ListAdder t1 = new ListAdder(c);
		ListAdder t2 = new ListAdder(c);
		t1.setName("bir");
		t2.setName("iki");
		
		Runtime rt = Runtime.getRuntime();
		int tc = rt.availableProcessors();
		System.out.println("Freememory : " + rt.freeMemory());
		System.out.println("CPU's : " + tc);

		ScheduledThreadPoolExecutor te = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(tc);
		
		te.schedule(t2, 1, TimeUnit.SECONDS);
		te.schedule(t1, 6, TimeUnit.SECONDS);
		
		te.shutdown();
		System.out.println(c.getListSize());
	}
}
