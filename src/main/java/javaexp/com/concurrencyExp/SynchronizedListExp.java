package javaexp.com.concurrencyExp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronizedListExp {

	public static void main(String[] args) throws InterruptedException {
		AddToList c = new AddToString();
		ListAdder t1 = new ListAdder(c);
		ListAdder t2 = new ListAdder(c);
		t1.setName("bir");
		t2.setName("iki");
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		
		System.out.println(c.getListSize());
	}
}

abstract class AddToList {

	abstract public void addList(int i);

	abstract public int getListSize();
}

class AddToNormalList extends AddToList {
	private List<Integer> list = new ArrayList<Integer>();
	final SynchronousQueue<String> s = new SynchronousQueue<>();

	public void addList(int i) {

		try {
			if (Thread.currentThread().getName().toString().equalsIgnoreCase("bir")) {
				System.out.println("ikinci nin okuduğu : " + s.take());
			} else {
				s.put("Birinci thread synchroniousa koydu birşeyler...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		list.add(i);
	}

	public int getListSize() {
		return list.size();
	}
}

class AddToString extends AddToList {
	private String s = "";
	final private LinkedTransferQueue<String> ltq = new LinkedTransferQueue<>();

	public void addList(int i) {
		try {
			if (Thread.currentThread().getName().toString().equalsIgnoreCase("bir")) {
				System.out.println("ikinci nin okuduğu : " + ltq.take());
			} else {
				ltq.transfer("Birinci thread synchroniousa koydu birşeyler...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		s = s.concat("1");
	}

	public int getListSize() {
		return s.length();
	}
}

class AddToVector extends AddToList {
	private List<Integer> list = new Vector<Integer>();

	public void addList(int i) {
		list.add(i);
	}

	public int getListSize() {
		return list.size();
	}
}

class AddToCopyOnWriteList extends AddToList {
	private List<Integer> list = new CopyOnWriteArrayList<>();

	public void addList(int i) {
		list.add(i);
	}

	public int getListSize() {
		return list.size();
	}
}

class AddToSyncList extends AddToList {
	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

	public void addList(int i) {
		list.add(i);
	}

	public int getListSize() {
		return list.size();
	}
}

class ListAdder extends Thread {
	private AddToList addIt;

	public ListAdder(AddToList c) {
		addIt = c;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			addIt.addList(i);
		}
	}
}