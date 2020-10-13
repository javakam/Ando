package aop.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPool {
	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(new Runnable(){
			public void run() {
				System.out.println("I dead");
			}		
			
		});
		//���û������Ĵ��룬����һֱ���ڲ�����״̬��
		service.shutdown();
		//service.shutdownNow();
	}

}
