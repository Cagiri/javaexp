package javaexp.com.concurrencyExp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoinExp {
	
	private static final long jobCount = 100000L;

	public static void main(String[] args) throws InterruptedException {
		ArrayforkJoin c = new LinkTransQ();
		ForkyListAdder t1 = new ForkyListAdder(c,jobCount);
		
		ForkJoinPool fp = new ForkJoinPool();
		fp.invoke(t1);
		
		ForkyFindMax f = new ForkyFindMax(c, jobCount,0,0);
		Integer val = f.invoke();
		System.out.println(val);
		
//		Runtime rt = Runtime.getRuntime();
//		int tc = rt.availableProcessors();
//		System.out.println("Freememory : " + rt.freeMemory());
//		System.out.println("CPU's : " + tc);
	}
}

abstract class ArrayforkJoin {
	
	public abstract void addToList(Integer str);
	
	public abstract int getListSize();
	
	public abstract List<Integer> getList();
}


class LinkTransQ extends ArrayforkJoin {
	final private List<Integer> ltq = Collections.synchronizedList(new ArrayList<Integer>());

	public void addToList(Integer str) {
		ltq.add(str);
	}

	public int getListSize() {
		return ltq.size();
	}
	
	public List<Integer> getList(){
		return ltq;
	}
}

class ForkyListAdder extends RecursiveAction {
	private static final long serialVersionUID = -1568345131181693082L;
	private static final long TRESHOLD = 25000;
	private long curVal;
	private ArrayforkJoin addIt;
	final private LinkedTransferQueue<ForkyListAdder> aList = new LinkedTransferQueue<>();

	public ForkyListAdder(ArrayforkJoin c,long cValue) {
		addIt = c;
		curVal = cValue;
	}

	@Override
	protected void compute() {
		if (curVal <= TRESHOLD) {
			for (int i = 0; i < curVal; i++) {
				addIt.addToList(ThreadLocalRandom.current().nextInt(0,65));
			}
		} else {
			int threadC = 4;
			
			long jobCount = curVal/threadC;
			
			for (int i = 0; i < threadC; i++) {
				aList.add(new ForkyListAdder(addIt, jobCount));
			}
			
			invokeAll(aList);
			
			System.out.println(addIt.getListSize());
		}
	}
}

class ForkyFindMax extends RecursiveTask<Integer> {
	private static final long serialVersionUID = -1568345131181693082L;
	private static final long TRESHOLD = 25000;
	private long curVal;
	private ArrayforkJoin addIt;
	final private LinkedTransferQueue<ForkyFindMax> aList = new LinkedTransferQueue<>();
	private int maxVal;
	private int sv;
	private int ev;

	public ForkyFindMax(ArrayforkJoin c,long cValue,int startv,int endv) {
		addIt = c;
		curVal = cValue;
		sv = startv;
		ev = endv;
	}

	@Override
	protected Integer compute() {
		if (curVal <= TRESHOLD) {
			for (int i = sv; i < ev; i++) {
				maxVal = Math.max(maxVal, addIt.getList().get(i));
			}
		} else {
			int threadC = 4;
			
			int jobCount = (int) (curVal/threadC);
			
			for (int i = 0; i < threadC; i++) {
				sv = ev;
				ev = ev + jobCount;
				aList.add(new ForkyFindMax(addIt, jobCount,sv,ev));
			}
			try {
				invokeAll(aList.take(),aList.take(),aList.take(),aList.take());
				int tempVal = 0 ;
				tempVal = aList.take().invoke();
				maxVal = Math.max(maxVal, tempVal);
				tempVal = aList.take().invoke();
				maxVal = Math.max(maxVal, tempVal);
				tempVal = aList.take().invoke();
				maxVal = Math.max(maxVal, tempVal);
				tempVal = aList.take().invoke();
				maxVal = Math.max(maxVal, tempVal);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(addIt.getListSize());
		}
		
		return maxVal;
	}
}