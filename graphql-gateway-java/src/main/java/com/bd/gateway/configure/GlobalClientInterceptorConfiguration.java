package com.bd.gateway.configure;

import com.bd.gateway.clients.interceptor.LogGrpcInterceptor;
import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidatorIndex;
import io.envoyproxy.pgv.grpc.ValidatingClientInterceptor;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class GlobalClientInterceptorConfiguration {

    @Bean
    public GlobalClientInterceptorConfigurer globalInterceptorConfigurerAdapter() {
		ValidatorIndex index = new ReflectiveValidatorIndex();
        return registry -> registry
			.addClientInterceptors(new LogGrpcInterceptor())
			.addClientInterceptors(new ValidatingClientInterceptor(index));
    }

}
