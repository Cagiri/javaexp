package javaexp.com.threadExp;

public class DeadLockExp {

	public static void main(String[] args) {
		Deadlock dl = new Deadlock();
		DeadLockThread dl1 = new DeadLockThread(dl, false);
		DeadLockThread dl2 = new DeadLockThread(dl, true);
		dl1.start();
		dl2.start();
	}
}


class DeadLockThread extends Thread{
	Deadlock dl;
	boolean forcon;
	public DeadLockThread(Deadlock d,boolean forConcat) {
		dl=d;
		forcon = forConcat;
	}
	@Override
	public void run() {
		if (forcon) {
			dl.concat();
		}else {
			dl.ChangeString("Birinci Str", "İkinci Str");
		}
	}
}

class Deadlock{
	
	private String a = "String bir";
	private String b = "String iki";
	
	public String concat() {
		synchronized(a) {
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			synchronized(b){
				System.out.println(a + " " + b);
				return a + " " + b;
			}
		}
	}
	
	public void ChangeString(String s,String s2) {
		synchronized(b) {
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			synchronized(a){
				a = s;
				b = s2;
				System.out.println("a = " + a + " b = " + b);
			}
		}
	}
}