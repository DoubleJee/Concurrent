package thread.interrupt;

// 优雅线程中断 两种方式：阻塞时中断、非阻塞时中断
public class TheadInterrupt {

    public static void main(String[] args) {
        interrupt();
    }



    // 阻塞时中断        （当阻塞时，线程会自动检测中断标识，发现中断标识为true，则抛出中断异常）
    static void blockInterrupt() {
        Thread thread = new Thread(() -> {
            try {
                // wait方法也可以
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("线程被中断！");
                throw new RuntimeException(e);
            }
            System.out.println("执行完毕！");
        });
        thread.start();



        // 中断线程 （设置中断标志为true）
        thread.interrupt();
    }



    // 非阻塞时中断        （线程自己实现逻辑判断中断标识，自行中断）
    static void interrupt() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // do something;
            }

            System.out.println("被中断了！");
        });

        thread.start();


        // 中断线程 （设置中断标志为true）
        thread.interrupt();
    }

}
