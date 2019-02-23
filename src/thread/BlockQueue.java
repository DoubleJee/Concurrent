package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Producer extends Thread {

    private BlockingQueue blockingQueue;

    private AtomicInteger atomicInteger = new AtomicInteger();

    private volatile boolean flag = true;

    public Producer(BlockingQueue blockQueue){
        this.blockingQueue = blockQueue;
    }

    @Override
    public void run() {
        try {
            while (flag) {
                atomicInteger.incrementAndGet();
                String data = getName() + atomicInteger;
                blockingQueue.offer(data);
                System.out.println("生产者生成数据:" + data);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止线程");
        }

    }

    public void stopThread() {
        this.flag = false;
    }
}

class Customer extends Thread{
    private BlockingQueue blockingQueue;

    public Customer(BlockingQueue blockQueue){
        this.blockingQueue = blockQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String data = (String) blockingQueue.poll(2, TimeUnit.SECONDS);
                if(data != null){
                    System.out.println(getName()+"消费者获取数据:" + data + "成功");
                }else{
                    System.out.println(getName()+"消费者获取数据:" + data + "失败");
                    break;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("消费者停止线程");
        }
    }
}

public class BlockQueue {
    //消费阻塞队列
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
        Producer producer1 = new Producer(arrayBlockingQueue);
        Producer producer2 = new Producer(arrayBlockingQueue);
        Customer customer = new Customer(arrayBlockingQueue);
        Customer customer2 = new Customer(arrayBlockingQueue);
        producer1.start();
        producer2.start();
        customer.start();
        customer2.start();
        Thread.sleep(10 * 1000);
        producer1.stopThread();
        producer2.stopThread();
//        AtomicInteger atomicInteger = new AtomicInteger(0);
//
//        Thread t2 = new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                atomicInteger.incrementAndGet();
//                System.out.println("线程2加了");
//            }
//        };
//        Thread t1 = new Thread(){
//            @Override
//            public void run() {
//                int num = atomicInteger.incrementAndGet();
//                System.out.println("线程1加了");
//                try {
//                    t2.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Thread.sleep(2000);
//                    System.out.println(num);
//                    System.out.println(atomicInteger);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        t1.start();
//        t2.start();
    }
}
