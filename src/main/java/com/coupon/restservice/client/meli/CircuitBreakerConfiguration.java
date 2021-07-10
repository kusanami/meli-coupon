/*
 * PayU Latam - Copyright (c) 2013-2021
 * http://www.payu.com.co
 */
package com.coupon.restservice.client.meli;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Circuit Breaker creation bean
 *
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@Configuration
public class CircuitBreakerConfiguration {

	@Bean
	public CircuitBreaker circuitBreaker(final CircuitBreakerRegistry circuitBreakerRegistry) {

		return circuitBreakerRegistry.circuitBreaker("Meli");
	}

}
