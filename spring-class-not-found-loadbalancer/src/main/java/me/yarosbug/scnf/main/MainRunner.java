package me.yarosbug.scnf.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ForkJoinPool;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainRunner {

    private final MyFeignClient myFeignClient;

    // https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#features.spring-application.application-events-and-listeners
    // > An ApplicationReadyEvent is sent after any application and command-line runners have been called.
    //
    // See also implementation of eager loader for loadbalancer:
    // org.springframework.cloud.loadbalancer.support.LoadBalancerEagerContextInitializer.onApplicationEvent
    @EventListener
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.err.println("Main thread class loader: " + Thread.currentThread().getContextClassLoader());

        var pool = new ForkJoinPool(1);

        var task = pool.submit(() -> {
            System.err.println("FJ Pool thread class loader: " + Thread.currentThread().getContextClassLoader());
            try {
                myFeignClient.remoteCall("Hello");
            } catch (Exception e) {
                log.error("Error in forkJoinPool: {}", Thread.currentThread(), e);
                System.exit(999);
            }
        });

        task.join();
    }
}
