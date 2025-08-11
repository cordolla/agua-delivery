package br.com.aguadelivery.payment_service.config;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public static final String QUEUE_PAYMENT_CONFIRMED = "payments.v1.payment-confirmed";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_PAYMENT_CONFIRMED, true);
    }
}