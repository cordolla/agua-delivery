package br.com.aguadelivery.product_service.service;

import br.com.aguadelivery.product_service.dto.ProductRequestDTO;
import br.com.aguadelivery.product_service.dto.ProductResponseDTO;
import br.com.aguadelivery.product_service.model.Product;
import br.com.aguadelivery.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDto) {
        if (productRepository.findByName(productRequestDto.getName()).isPresent()) {
            throw new RuntimeException("Produto ja existe");
        }

        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());

        Product productSaved = productRepository.save(product);

        return new ProductResponseDTO(
                productSaved.getId(),
                productSaved.getName(),
                productSaved.getPrice()
        );
    }

    public Product findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return product;
    }
}
