package thread.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// 专注于异步多任务协同工作
public class CompletableFutureTest {

    // 创建异步任务
    static void supplyAsync() {
        // 异步Supplier
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "data");
    }

    static void runAsync() {
        // 异步Runnable
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> System.out.println("run"));
    }

    // 获取任务结果
    static String get() {
        // 如果完成则返回结果，否则就抛出具体的异常
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "data");
            return completableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static String getTimeOut() {
        // 最大时间等待返回结果，否则就抛出具体的异常
        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "data";
            });
            return completableFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    static String join() {
        // 如果完成则返回结果，否则就抛出包装后的具体的异常     （无需显示捕获检查型异常）
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "data");
        return completableFuture.join();
    }

    // 未完成时主动设置任务结果
    static void complete() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "data";
        });

//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        // 如果任务没有完成，设置任务结果为给定值
        completableFuture.complete("love");
        System.out.println(completableFuture.join());
    }

    static void completeExceptionally() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "data";
        });

//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        // 如果任务没有完成，设置任务结果抛出给定异常
        completableFuture.completeExceptionally(new RuntimeException("超时啦！"));
        System.out.println(completableFuture.join());
    }

    // 组合任务
    static void thenAcceptAsync() {
        // 接受前个任务的值，并异步消费
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> "data").thenAcceptAsync((param) -> {
            System.out.println(param);
            param = null;
        });
        completableFuture.join();
    }

    static void thenApplyAsync() {
        // 接受前个任务的值，并异步function使用
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "data").thenApplyAsync((param) -> {
            System.out.println(param);
            return param;
        });

        completableFuture.join();
    }

    static void thenCombine() {
        // 接受前两个任务的值，并且异步function使用          (有对应的run 消费等方法)
        CompletableFuture.supplyAsync(() -> "first").thenCombineAsync(CompletableFuture.supplyAsync(() -> "second")
                , (a, b) -> {
                    System.out.println(a);
                    System.out.println(b);
                    return a + b;
                }).join();
    }

    static void applyToEither() {
        // 接受前面任务任意一个完成的值，并且异步function使用          (有对应的run 消费等方法)
        CompletableFuture.supplyAsync(() -> "first").applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "second";
        }), (result) -> {
            System.out.println(result);
            return result;
        }).join();
    }

    static void allOf() {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 多个任务都执行完成后才会执行， 有任何一个执行异常，则执行方法get时抛出异常
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(future1, future2, future3);
        // 等待多任务都完成
        allFuture.join();
        System.out.println("所有任务都已完成");
    }

    static void anyOf() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "3";
        });
        // 任意一个任务执行完成后就会执行，get时返回执行完成任务的结果或异常
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2, future3);
        // 等待任意一个任务完成
        System.out.println(anyFuture.join());
    }

    // 计算结果完成后的处理
    static void whenCompleteAsync() {
        // 接受前个任务结束后的值和抛出的异常，并异步消费处理，然后返回前个任务结果
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("some data");
            int i = 1 / 0;
            return "some data";
        }).whenCompleteAsync((result, e) -> {
            System.out.println(result);
            System.out.println(e);
        });

        completableFuture.join();
    }

    static void exceptionally() {
        // 接受前个任务抛出的异常，当异常时，异步function处理
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("some data");
            return "some data";
        }).exceptionally((e) -> {
            System.out.println("error: " + e);
            return "error done";
        });
        System.out.println(completableFuture.join());
    }

    static void handleAsync() {
        // 接受前个任务结束后的值和抛出的异常，并异步function处理
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("some data");
            int i = 1 / 0;
            return "some data";
        }).handleAsync((result, e) -> {
            System.out.println(result);
            System.out.println(e);
            return e == null;
        });

        System.out.println(completableFuture.join());
    }


}
