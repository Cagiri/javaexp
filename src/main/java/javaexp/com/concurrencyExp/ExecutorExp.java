package javaexp.com.concurrencyExp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorExp {

	
	public static void main(String[] args) throws InterruptedException {
		AddToList c = new AddToVector();
		ListAdder t1 = new ListAdder(c);
		ListAdder t2 = new ListAdder(c);
		t1.setName("bir");
		t2.setName("iki");
		
		Runtime rt = Runtime.getRuntime();
		int tc = rt.availableProcessors();
		System.out.println("Freememory : " + rt.freeMemory());
		System.out.println("CPU's : " + tc);

//		ExecutorService e = Executors.newCachedThreadPool();
		ThreadPoolExecutor tp = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		tp.setCorePoolSize(tc);
		tp.setMaximumPoolSize(tc);
		tp.execute(t1);
		tp.execute(t2);
		
		tp.shutdownNow();
		if(tp.isTerminated())	
			System.out.println(c.getListSize());
	}
}


class AddToSyncList2 extends AddToList {
	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

	public void addList(int i) {
		System.out.println(Thread.currentThread().getName() + " added to list : " + i);
		list.add(i);
	}

	public int getListSize() {
		return list.size();
	}
}