package test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	private ReentrantLock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();

	public void biz(){
		try{
			lock.lock();
			while(true)
				try {
					signal.await(5,TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
                    Thread.currentThread().interrupt();
				}
		}finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args){
		 Thread t = new Thread(new Runnable() {
	            public void run() {
	            	LockTest test = new LockTest();
	                test.biz();
	            }
	        });
	        t.start();
	        t.interrupt();
	        for (int i = 0; i < 10; i++) {
	            new Thread(new Runnable() {
	                public void run() {
	                	LockTest test = new LockTest();
	                    test.biz();
	                }
	            }).start();
	        }
	}
}
