package com.example.demo.service;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts_ReturnsAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");
        product1.setPrice(100);
        product1.setQty(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");
        product2.setPrice(200);
        product2.setQty(20);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getProducts();

        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getTitle());
        assertEquals("Product 2", products.get(1).getTitle());
    }

    @Test
    void getProductById_WhenExists_ReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertEquals("Product 1", foundProduct.getTitle());
    }

    @Test
    void getProductById_WhenNotExists_ThrowsException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void createProduct_ReturnsCreatedProduct() {
        Product product = new Product();
        product.setTitle("New Product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertEquals("New Product", createdProduct.getTitle());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_WhenExists_ReturnsUpdatedProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Old Product");

        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Product");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("Updated Product", result.getTitle());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_WhenNotExists_ThrowsException() {
        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Product");

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, updatedProduct));
    }

    @Test
    void patchProduct_WhenExists_ReturnsPatchedProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Old Product");
        existingProduct.setPrice(100);
        existingProduct.setQty(10);

        Product patchProduct = new Product();
        patchProduct.setTitle("Patched Product");
        patchProduct.setPrice(150);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product result = productService.patchProduct(1L, patchProduct);

        assertEquals("Patched Product", result.getTitle());
        assertEquals(150, result.getPrice());
        assertEquals(10, result.getQty());
    }

    @Test
    void patchProduct_WhenNotExists_ThrowsException() {
        Product patchProduct = new Product();
        patchProduct.setTitle("Patched Product");

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.patchProduct(1L, patchProduct));
    }

    @Test
    void deleteProduct_WhenExists_ReturnsTrue() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        boolean result = productService.deleteProduct(productId);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProduct_WhenNotExists_ThrowsException() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, never()).deleteById(productId);
    }
}
