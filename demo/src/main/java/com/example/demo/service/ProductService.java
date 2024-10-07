package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id){
        logger.info("Fetching products with ID: {}", id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
    }

    @Transactional
    public Product createProduct(Product product){
        logger.info("Creating new product: {}", product.toString());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product product){
        logger.info("Updating products with ID: {}", id);
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    public Product patchProduct(Long id, Product product) {
        logger.info("Partially updating products with ID: {}", id);
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));

        if (product.getTitle() != null) {
            existingProduct.setTitle(product.getTitle());
        }
        if (product.getPrice() != 0) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getQty() != 0) {
            existingProduct.setQty(product.getQty());
        }

        return productRepository.save(existingProduct);
    }

    @Transactional
    public boolean deleteProduct(Long id){
        logger.info("Deleting products with ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }
}
