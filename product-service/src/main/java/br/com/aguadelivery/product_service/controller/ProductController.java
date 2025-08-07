package br.com.aguadelivery.product_service.controller;

import br.com.aguadelivery.product_service.dto.ProductRequestDTO;
import br.com.aguadelivery.product_service.dto.ProductResponseDTO;
import br.com.aguadelivery.product_service.model.Product;
import br.com.aguadelivery.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDto) {
        ProductResponseDTO product = productService.createProduct(productRequestDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }
}
