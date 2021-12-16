package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Callable {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //未来任务
        FutureTask<String> futureTask = new FutureTask<>(new java.util.concurrent.Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0;i< 100;i++){
                    Thread.sleep(10);
                    System.out.println("执行完成"+ i);
                }
                return "OK";
            }
        });
        executorService.submit(futureTask);
        System.out.println("牛");
        Thread.sleep(50);
        //取消任务（只是调用线程的中断方法，如果线程没有阻塞的方法来抛出中断异常，那只能逻辑判断中断标志来中断线程）
        System.out.println(futureTask.cancel(true));
        //判断任务是不是被取消了
        System.out.println(futureTask.isCancelled());
    }
}
