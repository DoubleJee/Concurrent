package thread;

class TxThread extends Thread{

    private Object lock;

    public TxThread(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println(getName() + "获取锁了");
            try {
                lock.wait();
                System.out.println(getName() + "锁又回来了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class TxThread2 extends Thread{

    private Object lock;

    public TxThread2(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            System.out.println(getName() + "获取锁了");
            System.out.println(getName() + "现在没锁了");
            lock.notify();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接着在有锁的地方工作");
        }
        System.out.println("接着在有锁的地方工作2");
        System.out.println("接着在有锁的地方工作3");

    }
}


public class TxThreadDemo {

    public static void main(String[] args) {
        int lock = 1;
        TxThread txThread = new TxThread(lock);
        TxThread2 txThread2 = new TxThread2(lock);
        txThread.start();
        txThread2.start();
    }
}
