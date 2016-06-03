package javaexp.com.threadExp;

public class StaticMethodSyncExp {

	public static void main(String[] args) {
		ControlClass c = new ControlClass();
		Thread t =  new StaticThreadExp(c,true);
		Thread t2 =  new StaticThreadExp(c,false);
		
		t.start();
		t2.start();
		
	}
}

class StaticThreadExp extends Thread{
	ControlClass c ;
	boolean first = false;
	public StaticThreadExp(ControlClass c1,boolean control) {
		first = control;
		c=c1;
	}
	
	@Override
	public void run() {
		
		if (first) {
			c.staticMethod1();
		} else {
			c.staticMethod2();
		}
		
	}
	
}

class ControlClass {
	
	

	public synchronized void staticMethod1(){
		
		System.out.println("Static method 1 e girildi");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void staticMethod2(){
		System.out.println("Static method 2 ye girildi");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}