package thread;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

/**
 * 停车位信号量
 */
class ParkingSpot extends Thread {

    private Semaphore semaphore;

    public ParkingSpot(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int count = semaphore.availablePermits();
        if(count <= 0){
            System.out.println(getName() + ":开始阻塞了，没有空位了");
        }else{
            System.out.println(getName() + ":有空位开始找车位");
        }

        try {
            semaphore.acquire();
            Thread.sleep(new Random().nextInt(1000)); // 模拟停车时间。
            System.out.println(getName() + ":找到了，停好车了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }
}

public class SemaphoreTest {

    public static void main(String [] args){
//        Semaphore semaphore = new Semaphore(3);
//        for (int i = 1;i<=10;i++){
//            new ParkingSpot(semaphore).start();
//        }

        ConcurrentLinkedDeque concurrentLinkedDeque = new ConcurrentLinkedDeque();
        concurrentLinkedDeque.add("一");
        concurrentLinkedDeque.add(2);
        concurrentLinkedDeque.add(true);
        System.out.println(concurrentLinkedDeque.poll());
        System.out.println(concurrentLinkedDeque.size());
        System.out.println(concurrentLinkedDeque.poll());
        System.out.println(concurrentLinkedDeque.size());
        System.out.println(concurrentLinkedDeque.poll());
        System.out.println(concurrentLinkedDeque.size());
        System.out.println(concurrentLinkedDeque.poll());
        System.out.println(concurrentLinkedDeque.size());
        System.out.println(concurrentLinkedDeque.peek());
        System.out.println(concurrentLinkedDeque.size());
    }
}
