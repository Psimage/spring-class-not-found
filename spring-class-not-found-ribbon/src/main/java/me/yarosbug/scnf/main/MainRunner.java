package me.yarosbug.scnf.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ForkJoinPool;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainRunner implements ApplicationRunner {

    private final MyFeignClient myFeignClient;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Main thread class loader: {}", Thread.currentThread().getContextClassLoader());

        var pool = new ForkJoinPool(1);

        var task = pool.submit(() -> {
            log.info("FJ Pool thread class loader: " + Thread.currentThread().getContextClassLoader());
            try {
                log.info("Feign response: {}", myFeignClient.remoteCall("Hello"));
            } catch (Exception e) {
                log.error("Error in forkJoinPool: {}", Thread.currentThread(), e);
                Throwable rootCause = NestedExceptionUtils.getRootCause(e);
                if (rootCause != null && rootCause.getClass() == ClassNotFoundException.class) {
                    System.exit(999);
                }
            }
        });

        task.join();
    }
}
