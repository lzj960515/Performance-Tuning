package com.my.jvm.test.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试内存参数 -Xms64m -Xmx64m -Xmn30m -XX:NewRatio=1 -XX:MaxMetaspaceSize=256m -XX:MetaspaceSize=256m
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class MemoryTest {

    static class Memory{
        byte[] bytes = new byte[1024*1024];//1m
    }

    public static void main(String[] args) throws InterruptedException {
        List<Memory> memoryList = new ArrayList<>();
        for(;;){
            memoryList.add(new Memory());
            Thread.sleep(1000);
        }
    }
}
