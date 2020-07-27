 事先启动一个web应用程序，用jps查看其进程id，接着用各种jdk自带命令优化应用 

- `jps`：查看java进程

  ```powershell
  PS C:\Users\liaozijian> jps
  54888 jar
  50772 Jps
  ```

- `jmap`: 查看内存信息，实例个数以及占用内存大小

  ```powershell
  #jmap -histo 54888 查看历史生成的实例
  #jmap -histo:live 54888 查看当前存活的实例，执行过程中可能会触发一次full gc
  PS C:\Users\liaozijian> jmap -histo 54888 > d:/log.txt
  ```

  `log.txt`部分内容如下

  ```properties
   num     #instances         #bytes  class name
  ----------------------------------------------
     1:          8971       23808216  [I
     2:        104987       14049304  [C
     3:         11164        7893848  [B
     4:         89315        2143560  java.lang.String
     5:         23896        2102848  java.lang.reflect.Method
     6:         43886        1404352  java.util.concurrent.locks.AbstractQueuedSynchronizer$Node
     7:         12000        1325584  java.lang.Class
     8:         39461        1262752  java.util.concurrent.ConcurrentHashMap$Node
     9:         27082        1083280  java.util.LinkedHashMap$Entry
    10:         11795         894544  [Ljava.util.HashMap$Node;
    11:         15800         863136  [Ljava.lang.Object;
    12:         13029         729624  java.util.LinkedHashMap
  	..................
  ```

  - num：序号
  - instances：实例数量
  - bytes：占用空间大小
  - class name：类名称，[C is a char[]，[S is a short[]，[I is a int[]，[B is a byte[]，[[I is a int[]

  **查看堆信息**

  ```powershell
  PS C:\Users\liaozijian> jmap -heap 54888
  Attaching to process ID 54888, please wait...
  Debugger attached successfully.
  Server compiler detected.
  JVM version is 25.161-b12
  
  using thread-local object allocation.
  Parallel GC with 4 thread(s)
  
  Heap Configuration:
     MinHeapFreeRatio         = 0
     MaxHeapFreeRatio         = 100
     MaxHeapSize              = 4292870144 (4094.0MB)
     NewSize                  = 89128960 (85.0MB)
     MaxNewSize               = 1430781952 (1364.5MB)
     OldSize                  = 179306496 (171.0MB)
     NewRatio                 = 2
     SurvivorRatio            = 8
     MetaspaceSize            = 21807104 (20.796875MB)
     CompressedClassSpaceSize = 1073741824 (1024.0MB)
     MaxMetaspaceSize         = 17592186044415 MB
     G1HeapRegionSize         = 0 (0.0MB)
  
  Heap Usage:
  PS Young Generation
  Eden Space:
     capacity = 321912832 (307.0MB)
     used     = 59181336 (56.439720153808594MB)
     free     = 262731496 (250.5602798461914MB)
     18.384273665735698% used
  From Space:
     capacity = 17825792 (17.0MB)
     used     = 17534448 (16.722152709960938MB)
     free     = 291344 (0.2778472900390625MB)
     98.36560417624081% used
  To Space:
     capacity = 19922944 (19.0MB)
     used     = 0 (0.0MB)
     free     = 19922944 (19.0MB)
     0.0% used
  PS Old Generation
     capacity = 132644864 (126.5MB)
     used     = 18898856 (18.023353576660156MB)
     free     = 113746008 (108.47664642333984MB)
     14.247710337280756% used
  
  27696 interned Strings occupying 2852544 bytes.
  ```

  堆内存 dump

  ```powershell
  jmap -dump:format=b,file=eureka.hprof 54888
  ```

  也可以设置内存溢出自动导出dump文件

  示例代码

  ```java
  /**
   * 测试OOM
   * -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump
   * @author Zijian Liao
   * @since 1.0
   */
  public class OOMTest {
      static List<Object> list = new ArrayList<>();
  
      public static void main(String[] args) {
          while (true) {
              list.add(new User());
          }
      }
  
      static class User {
          byte[] bytes = new byte[1024];
      }
  }
  ```

   **可以用jvisualvm命令工具导入该dump文件分析**
  
- `jstack`:查看线程情况：如死锁，某个线程导致cpu飙高

  示例代码

  ```java
  public class DeadLockTest {
  
     private static Object lock1 = new Object();
     private static Object lock2 = new Object();
  
     public static void main(String[] args) {
        new Thread(() -> {
           synchronized (lock1) {
              try {
                 System.out.println("thread1 begin");
                 Thread.sleep(5000);
              } catch (InterruptedException e) {
              }
              synchronized (lock2) {
                 System.out.println("thread1 end");
              }
           }
        }).start();
  
        new Thread(() -> {
           synchronized (lock2) {
              try {
                 System.out.println("thread2 begin");
                 Thread.sleep(5000);
              } catch (InterruptedException e) {
              }
              synchronized (lock1) {
                 System.out.println("thread2 end");
              }
           }
        }).start();
  
        System.out.println("main thread end");
     }
  }
  ```

  ```powershell
  PS C:\Users\liaozijian> jstack 53460
  Found one Java-level deadlock:
  =============================
  "Thread-1":
    waiting to lock monitor 0x000000001c451288 (object 0x000000076b02c848, a java.lang.Object),
    which is held by "Thread-0"
  "Thread-0":
    waiting to lock monitor 0x000000001c44d1e8 (object 0x000000076b02c858, a java.lang.Object),
    which is held by "Thread-1"
  
  Java stack information for the threads listed above:
  ===================================================
  "Thread-1":
          at com.my.jvm.test.gc.DeadLockTest.lambda$main$1(DeadLockTest.java:30)
          - waiting to lock <0x000000076b02c848> (a java.lang.Object)
          - locked <0x000000076b02c858> (a java.lang.Object)
          at com.my.jvm.test.gc.DeadLockTest$$Lambda$2/1688376486.run(Unknown Source)
          at java.lang.Thread.run(Thread.java:748)
  "Thread-0":
          at com.my.jvm.test.gc.DeadLockTest.lambda$main$0(DeadLockTest.java:17)
          - waiting to lock <0x000000076b02c858> (a java.lang.Object)
          - locked <0x000000076b02c848> (a java.lang.Object)
          at com.my.jvm.test.gc.DeadLockTest$$Lambda$1/38997010.run(Unknown Source)
          at java.lang.Thread.run(Thread.java:748)
  
  Found 1 deadlock.
  ```

- `jinfo -flags pid`:查看jvm参数

- `jinfo -sysprops pid`:查看系统参数

- `jstat -gc pid [time] [count]` :评估程序内存使用及GC压力整体情况

  - S0C：第一个幸存区的大小，单位KB
  - S1C：第二个幸存区的大小
  - S0U：第一个幸存区的使用大小
  - S1U：第二个幸存区的使用大小
  - EC：伊甸园区的大小
  - EU：伊甸园区的使用大小
  - OC：老年代大小
  - OU：老年代使用大小
  - MC：方法区大小(元空间)
  - MU：方法区使用大小
  - CCSC:压缩类空间大小
  - CCSU:压缩类空间使用大小
  - YGC：年轻代垃圾回收次数
  - YGCT：年轻代垃圾回收消耗时间，单位s
  - FGC：老年代垃圾回收次数
  - FGCT：老年代垃圾回收消耗时间，单位s
  - GCT：垃圾回收消耗总时间，单位s


