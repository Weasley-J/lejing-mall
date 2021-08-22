package cn.alphahub.mall.search.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <b>输入描述</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Slf4j
public class ThreadTest {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    @BeforeEach
    void setUp() {
        log.info("------------------");
    }

    @AfterEach
    void tearDown() {
        log.info("------------------");
    }

    @Test
    void runAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("执行结果：" + i);
        }, executor);
    }

    /**
     * 可以获取返回结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            return 10 / 2;
        }, executor);
        Integer i = future.get();
        System.out.println("执行结果：" + i);
    }

    /**
     * 可以获取返回结果,执行完了supplyAsync后该干什么whenComplete
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void supplyAsyncWhenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            return 10 / 2;
        }, executor).whenComplete((res, throwable) -> {
            log.info("异步线程执行完毕：{},异常：{}", res, throwable);
        });
        Integer i = future.get();
        System.out.println("执行结果：" + i);
    }

    /**
     * 可以获取返回结果,执行完了supplyAsync后该干什么whenComplete，出异常了，exceptionally处理异常返回默认结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void supplyAsyncWhenCompleteWithException() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            return 10 / 0;
        }, executor).whenComplete((res, throwable) -> {
            log.info("异步线程执行完毕：{}, 异常：{}", res, throwable);
        }).exceptionally(throwable -> {
            String localizedMessage = throwable.getLocalizedMessage();
            log.info("异常原因:{}", localizedMessage);
            return 0;
        });
        Integer i = future.get();
        System.out.println("执行结果：" + i);
    }

    /**
     * 可以获取返回结果
     * 执行完了supplyAsync后该干什么whenComplete，出异常了，handle处理异常,并反会结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void supplyAsyncWhenCompleteWithHandle() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            return 10 / 0;
        }, executor).handle((res, throwable) -> {
            if (Objects.nonNull(res) && Objects.nonNull(throwable)) {
                log.info("异步线程执行完毕：{}, 异常：{}", res, throwable);
                return res * 2;
            }
            return 0;
        });
        Integer i = future.get();
        System.out.println("执行结果：" + i);
    }

    /**
     * 可以获取返回结果
     * 执行完了supplyAsync后该干什么串行化
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void supplyAsyncWithSerial() throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            log.info("当前线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            return 10 / 2;
            // thenRunAsync不能获取上一步的执行结果，thenAcceptAsync能接受但是并没有返回值
        }, executor).thenAcceptAsync((var) -> {
            System.out.println("任务2执行完了, 上一步执行结果：" + var);
        }, executor);

        //Integer i = future1.get();
        System.out.println("执行结果：");
    }

    /**
     * 可以获取返回结果
     *
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    @Test
    void combineMultipleAsynchronousThreadTasks() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future1线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("future1执行结果：" + i);
            return i;
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future2线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            String holler = "你好！";
            System.out.println("future2执行结果：" + holler);
            return holler;
        }, executor);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future3线程：" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            String holler = "你好！";
            System.out.println("future3执行结果：" + holler);
            return holler;
        }, executor);
        System.out.println("");
        future1.runAfterBothAsync(future2, () -> {
            System.out.println("任务三开始：333333");
        }, executor);
        System.out.println("");
        CompletableFuture<String> combineAsync = future1.thenCombineAsync(future2, (f1, f2) -> f1 + ":" + f2 + "-->HaHa", executor);
        String combineAsyncStr = combineAsync.get();
        System.out.println("combineAsyncStr = " + combineAsyncStr);
        log.info("执行结果：" + future1.get());
    }
}
