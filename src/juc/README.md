# Java JUC

todo 2019年9月16日 17:03:53 <https://www.bilibili.com/video/av59548640/?p=7>

- TestVolatile.java
- TestAtomicDemo.java
- TestCompareAndSwap.java
- TestCopyOnWriteArrayList.java
- TestCountDownLatch.java
- TestCallable.java
- TestLock.java

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