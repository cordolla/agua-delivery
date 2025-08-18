package br.com.aguadelivery.order_service.client;


import br.com.aguadelivery.order_service.config.FeignClientConfig;
import br.com.aguadelivery.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductServiceClient {

    @GetMapping("api/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);
}
