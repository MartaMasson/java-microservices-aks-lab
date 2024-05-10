package org.springframework.samples.petclinic.customers.config;


import java.lang.System.Logger;
import java.util.function.Supplier;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class ManualProducerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManualProducerConfiguration.class);

    @Bean
    public Sinks.Many<Message<String>> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<String>>> supply(Sinks.Many<Message<String>> many) {
        return () -> many.asFlux()
                         .doOnNext(m -> LOGGER.info("Manually sending message {}", m))
                         .doOnError(t -> LOGGER.error("Error encountered", t));
    }
}
