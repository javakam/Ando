# Java JUC



- 学习曲线 https://www.bilibili.com/read/cv3560825/
- 尚硅谷 -Java 并发视频教程全集（15P）| 3 小时从入门到精通
  视频 :  <https://www.bilibili.com/video/av59548640/>
  源码 :  链接: https://pan.baidu.com/s/1_3Vg0lBeieQ5Ak4Ru3dyfw 提取码: 5w2a

- [TestVolatile](TestVolatile.java)
- [TestAtomicDemo](TestAtomicDemo.java)
- [TestCompareAndSwap](TestCompareAndSwap.java)
- [TestCopyOnWriteArrayList](TestCopyOnWriteArrayList.java)
- [TestCountDownLatch](TestCountDownLatch.java)
- [TestCallable](TestCallable.java)
- [TestLock](TestLock.java)
- [TestProductorAndConsumer](TestProductorAndConsumer.java)
- [TestProductorAndConsumerByLockAndCondition](TestProductorAndConsumerByLockAndCondition.java)
- 线程按序交替[TestABCAlternate](TestABCAlternate.java)
- 线程读写锁[TestReadWriteLock](TestReadWriteLock.java)
- 线程八锁[TestThread8Monitor](TestThread8Monitor.java)
- 线程池[TestThreadPool](TestThreadPool.java)
- []()
todo 2019年9月23日 17:07:49 https://www.bilibili.com/video/av59548640/?p=13

- ConcurrentHashMap \ HashTable
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
      
      1.8以后,把 ConcurrentHashMap 改成 CAS (Compare and Swap) , 移除 "锁分段"