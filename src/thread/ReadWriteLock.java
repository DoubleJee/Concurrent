package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {

    private static volatile Map<String, Object> map = new HashMap<>();

    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static Lock readLock = rwl.readLock();

    private static Lock writeLock = rwl.writeLock();


    public static void put(String key, Object value) {
        try {
            writeLock.lock();
            System.out.println("写入数据，key：" + key + "，value：" + value + "！开始......");
            Thread.sleep(100);
            map.put(key, value);
            System.out.println("写入数据，key：" + key + "，value：" + value + "！结束......");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    public static void get(String key) {
        try{
            readLock.lock();
            System.out.println("读取数据，key：" + key + "！开始......");
            Thread.sleep(100);
            Object value = map.get(key);
            System.out.println("读取数据，key：" + key + "，value：" + value + "！结束......");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    ReadWriteLock.put(i + "",i);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    ReadWriteLock.get(i + "");
                }
            }
        }).start();
    }
}
