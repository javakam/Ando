# JVM入门和JVM

todo 2019年12月23日 20:23:30 https://www.bilibili.com/video/av64709063?p=3

1. 视频
<https://www.bilibili.com/video/av64709063>
2. `JVM指令手册` <https://blog.csdn.net/hudashi/article/details/7062675>
3. 有助理解的帖子 <https://segmentfault.com/q/1010000008683475>

- JVM 从软件层面屏蔽不同操作系统在底层硬件与指令上的区别 ;
- 栈帧 : 一个`方法`对应一块`栈帧`内存区域 ; 先进后出 FILO , 方法执行完后依次出栈
- 程序计数器: 线程特有的,标识线程马上要执行的那一行代码的内存地址指针
- store 和 load : 
操作数栈 👉 局部变量表 , store
局部变量表 👉 操作数栈 , load
- 操作数栈:数据临时存储区域
- 方法出口:调用该方法时的位置  "从哪进来的从哪出去"
- 引用类型(new 出来的对象) : 用堆替代操作数栈存储数据 , 即 局部变量表 👉 堆 

```java
public class JVM01_Math {

    public static final Integer CONSTANT = 666;

    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {

        JVM01_Math jvm01Math = new JVM01_Math();
        jvm01Math.compute();


    }

}
```
☘ 反汇编 `javap -c JVM01_Math.class` :
```java
PS C:\fastwork\Project\Idea\AndoJava\target\classes\jvm> javap -c JVM01_Math.class
Compiled from "JVM01_Math.java"
public class jvm.JVM01_Math {
  public static final java.lang.Integer CONSTANT;

  public jvm.JVM01_Math();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public int compute();
    Code:
       0: iconst_1              //将int型(1)推送至栈顶
       1: istore_1              //将栈顶int型数值存入第二个本地变量
       2: iconst_2              //
       3: istore_2          
       4: iload_1               //将第二个int型本地变量推送至栈顶
       5: iload_2               //将第三个int型本地变量推送至栈顶
       6: iadd                  //将栈顶两int型数值相加并将结果压入栈顶,执行1+2
       7: bipush        10      //将单字节的常量值(-128~127)推送至栈顶
       9: imul                  //将栈顶两int型数值相乘并将结果压入栈顶,执行3x10
      10: istore_3              //将栈顶int型数值存入第四个本地变量, int c =3
      11: iload_3               //
      12: ireturn

  public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class jvm/JVM01_Math
       3: dup
       4: invokespecial #3                  // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: invokevirtual #4                  // Method compute:()I
      12: pop
      13: return

  static {};
    Code:
       0: sipush        666
       3: invokestatic  #5                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
       6: putstatic     #6                  // Field CONSTANT:Ljava/lang/Integer;
       9: return
}
```
✨ 参考: JVM指令手册.pdf 

🍎 分析
```java
int a = 1;

istore_1  
...
iload_1   
iload_2   将第三个int型本地变量推送至栈顶


```


GC
```java
GC调优步骤：
1、打印GC日志
-XX:+PrintGCDetails  -XX:+PrintGCTimeStamps  -XX:+PrintGCDateStamps  -Xloggc:./gc.log
Tomcat则直接加在JAVA_OPTS变量里
2、分析日志得到关键性指标
3、分析GC原因，调优JVM参数
```
