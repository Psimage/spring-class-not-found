package me.yarosbug.scnf.main;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class WorkaroundConfig {

    // Original factory defined at:
    // org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration.loadBalancerClientFactory
    @Bean
    public LoadBalancerClientFactory loadBalancerClientFactory(
            LoadBalancerClientsProperties properties,
            ObjectProvider<List<LoadBalancerClientSpecification>> configurations) {

        var factory = new LoadBalancerClientFactory(properties) {
            @Override
            protected AnnotationConfigApplicationContext createContext(String name) {
                // FIXME: temporary switch classloader to use the correct one when creating the context
                ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(WorkaroundConfig.class.getClassLoader());
                AnnotationConfigApplicationContext context = super.createContext(name);
                Thread.currentThread().setContextClassLoader(originalClassLoader);
                return context;
            }
        };
        factory.setConfigurations(configurations.getIfAvailable(Collections::emptyList));
        return factory;
    }
}
