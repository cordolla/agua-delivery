package br.com.aguadelivery.order_service.client.decoder;

import br.com.aguadelivery.order_service.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            if (methodKey.contains("ProductServiceClient")){
                return new ProductNotFoundException("Produto não encontrado. ID " + extractProductIdFromRequest(response));
            }
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

    private String extractProductIdFromRequest(Response response) {
        try {
            String url = response.request().url();
            return url.substring(url.lastIndexOf("/") + 1);
        }catch (Exception e){
            return "ID não pôde ser extraído";
        }
    }
}
