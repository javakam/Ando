# [Java JUC](https://www.bilibili.com/video/BV14W411u7gB)

- 学习曲线 https://www.bilibili.com/read/cv3560825/
- 尚硅谷 -Java 并发视频教程全集（15P）| 3 小时从入门到精通 <br>
  视频 :  <https://www.bilibili.com/video/av59548640/> <br>
  源码 :  链接: https://pan.baidu.com/s/1_3Vg0lBeieQ5Ak4Ru3dyfw 提取码: 5w2a

### volatile(互斥锁) 
- [TestVolatile](TestVolatile.java)
- 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
- 相较于 synchronized 是一种较为轻量级的同步策略。
- 注意：
    1. volatile 不具备“互斥性” -> 每个线程都可以同时访问,不过每次访问都是从主存中取
    2. volatile 不能保证变量的“原子性” -> 不可分割性
    3. volatile 每次都会从主存中取值,相对于什么都不设置,执行效率是降低了(JVM 重排序),但是相较于 synchronized 方式,效率提高很多
```java
while (true) {
    if (td.isFlag()) {
        System.out.println("------------------");
        break;
    }
    //如果 flag 没有 volatile 修饰,可以采用 synchronized 方式,每次都会重新从主存中读数据并刷新缓存,效率会降低
	//synchronized (td){
	//}
}
```

### Atomic 👉 解决 i++ 问题
- **重** [TestAtomicDemo](TestAtomicDemo.java)
#### i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
		  int i = 10;
		  i = i++; //10
		  int temp = i;
		  i = i + 1;
		  temp = i;
#### 原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
		1. volatile 保证内存可见性
		2. CAS（Compare and Swap ，即比较再交换） 算法保证数据变量的原子性
			CAS 算法是硬件对于并发操作的支持
			CAS 包含了三个操作数：
			①内存值  V
			②预估值  A
			③更新值  B
			当且仅当 V == A 时， V = B; 否则，不会执行任何操作。

### 模拟 CAS 算法
- [TestCompareAndSwap](TestCompareAndSwap.java)

### ConcurrentHashMap \ HashTable
- HashTable 执行效率低;
  而且复合操作出现问题:
  ```java
  if(!table.contains){
     table.add();
  }
  ```
  上下两句会出现线程不安全问题
  
- ConcurrentHashMap 采取"锁分段"机制:
  默认有16段 Segment
  concurrentLevel 
  锁分段机制可以并行执行任务;
  1.8以后,把 ConcurrentHashMap 内部改成 CAS (Compare and Swap) , 移除 "锁分段"

### CopyOnWriteArrayList/CopyOnWriteArraySet
- [TestCopyOnWriteArrayList](TestCopyOnWriteArrayList.java)
> CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制” <br>
**注意：** 添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。

### CountDownLatch(闭锁)
- <a href="TestCountDownLatch.java" target="_blank">TestCountDownLatch</a>
- CountDownLatch ：闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
```java
CountDownLatch latch = new CountDownLatch(10);
latch.await();	  //try
latch.countDown();//finally
```

### Callable<T>
- [TestCallable](TestCallable.java)
- 创建执行线程的方式三：实现 Callable 接口。 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
- 执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。FutureTask 是  Future 接口的实现类
```java
//1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
//  FutureTask 本质上也是 Runnable
FutureTask<Integer> result = new FutureTask<>(td);
new Thread(result).start();
//2.接收线程运算后的结果
try {
    // 此时 get() 不会立刻返回值,而是等到线程执行完并返回值后,才会执行下面的代码,可见 FutureTask 也可用于 [闭锁] !!!!!!
    Integer sum = result.get();
    System.out.println(sum);
    System.out.println("------------------------------------");
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}
System.out.println("main+++++++++++++++++++++++++++");
```

### ReentrantLock(锁)
- [TestLock](TestLock.java)
#### 用于解决多线程安全问题的方式：
synchronized:隐式锁
1. 同步代码块
2. 同步方法 
> **jdk 1.5 后：**
3. 同步锁 Lock
- 注意：是一个显式锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
#### 标准写法
```java
private Lock lock = new ReentrantLock();
@Override
public void run() {
    while (true) {
        lock.lock(); //1 上锁
        try {
            
        } finally {
            lock.unlock(); //2 释放锁,必须在 finally 里执行
        }
    }
}
```

### 生产者消费者
- [TestProductorAndConsumer](TestProductorAndConsumer.java)
- [TestProductorAndConsumerByLockAndCondition](TestProductorAndConsumerByLockAndCondition.java)
#### 采用 synchronized+wait+notifyAll 方式解决虚假唤醒问题
```java
//进货
public synchronized void get(){//循环次数：0
	while(product >= 1){// 为了避免虚假唤醒问题，应该总是使用在循环中 !!!  while 替换掉 if
		System.out.println(Thread.currentThread().getName() +" 产品已满！");
		try {
			this.wait();
		} catch (InterruptedException e) {
		}
	}
	System.out.println(Thread.currentThread().getName() + " : " + ++product);
	this.notifyAll();
}
```
#### 采用 ReentrantLock+Condition 方式解决虚假唤醒问题
```java
// 进货
public void get() {
    lock.lock();
    try {
        while (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
            System.out.println(Thread.currentThread().getName() + " 产品已满！");
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        condition.signalAll();
    } finally {
        lock.unlock();
    }
}
```

### 线程按序交替
- [TestABCAlternate](TestABCAlternate.java)
> 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，
要求输出的结果必须按顺序显示。如：ABCABCABC…… 依次递归
采用 ReentrantLock + Condition 方案,涉及到的函数 Condition.await()/Condition.signal()
	
### ReadWriteLock(读写锁)	
- [TestReadWriteLock](TestReadWriteLock.java)
> 写写/读写 需要“互斥” , 读读 不需要互斥
```java
class ReadWriteLockDemo{
	private int number = 0;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	//读
	public void get(){
		lock.readLock().lock(); //上锁
		try{
			System.out.println(Thread.currentThread().getName() + " : " + number);
		}finally{
			lock.readLock().unlock(); //释放锁
		}
	}
	//写
	public void set(int number){
		lock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName());
			this.number = number;
		}finally{
			lock.writeLock().unlock();
		}
	}
}
```

### 线程八锁
- [TestThread8Monitor](TestThread8Monitor.java)
#### 题目：判断打印的 "one" or "two" ？
```
1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
3. 新增普通方法 getThree() , 打印? //three  one   two
4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
5. 修改 getOne() 为静态同步方法，打印?  //two   one   -> 同步的对象不一样👉字节码文件 & Number对象
6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
8. 两个静态同步方法，两个 Number 对象?   //one  two
```
#### 线程八锁的关键
①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例  <br>
②某一个时刻内，只能有一个线程持有锁，无论几个方法。

### 线程池
- [TestThreadPool](TestThreadPool.java)
```java
一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
二、线程池的体系结构：
	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
		|--**ExecutorService 子接口: 线程池的主要接口
			|--ThreadPoolExecutor 线程池的实现类
			|--ScheduledExecutorService 子接口：负责线程的调度
				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
三、工具类 : Executors 
ExecutorService newFixedThreadPool() : 创建固定大小的线程池
ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
```
#### 操作
```java
//1. 创建线程池
ExecutorService pool = Executors.newFixedThreadPool(5);
//2. 为线程池中的线程分配任务
pool.submit(...)
//三个方法:
Future<?> submit(Runnable task);
<T> Future<T> submit(Callable<T> task);
<T> Future<T> submit(Runnable task, T result);
//3. 关闭线程池
pool.shutdown();
```

### 线程调度
- [TestScheduledThreadPool](TestScheduledThreadPool.java)
> ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
```java
Future<Integer> result = pool.schedule(new Callable<Integer>(){
	@Override
	public Integer call() throws Exception {
		int num = new Random().nextInt(100);//生成随机数
		System.out.println(Thread.currentThread().getName() + " : " + num);
		return num;
	}
	
}, 1, TimeUnit.SECONDS);
System.out.println(result.get());
```

### ForkJoinPool分支合并框架
- [TestForkJoinPool](TestForkJoinPool.java)
> 应用场景:当并发执行时间,小于串行执行的情况。
```java
public static void main(String[] args) {
	Instant start = Instant.now();
	ForkJoinPool pool = new ForkJoinPool();
	ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 50000000000L);
	Long sum = pool.invoke(task);
	System.out.println(sum);
	Instant end = Instant.now();
	System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//166-1996-10590
}
```
普通for循环(串行执行)
```java
@Test
public void test1(){
	Instant start = Instant.now();
	long sum = 0L;
	for (long i = 0L; i <= 50000000000L; i++) {
		sum += i;
	}
	System.out.println(sum);
	Instant end = Instant.now();
	System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//35-3142-15704
}
```
java8 新特性 LongStream
```java
@Test
public void test2(){
	Instant start = Instant.now();
	Long sum = LongStream.rangeClosed(0L, 50000000000L)
						 .parallel()
						 .reduce(0L, Long::sum);
	System.out.println(sum);
	Instant end = Instant.now();
	System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//1536-8118
}
```
ForkJoinSumCalculate 类
```java
class ForkJoinSumCalculate extends RecursiveTask<Long>{

	private static final long serialVersionUID = -259195479995561737L;
	private long start;
	private long end;
	private static final long THURSHOLD = 10000L;  //临界值
	
	public ForkJoinSumCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length = end - start;
		if(length <= THURSHOLD){
			long sum = 0L;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		}else{
			long middle = (start + end) / 2;
			ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle); 
			left.fork(); //进行拆分，同时压入线程队列
			
			ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle+1, end);
			right.fork(); //
			return left.join() + right.join();
		}
	}
}
```