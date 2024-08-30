package ecom.productservice.services;

import ecom.productservice.dtos.ProductDto;
import ecom.productservice.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id) throws ProductNotFoundException;

    List<ProductDto> getProductsByCategory(String category);

    void addProduct(ProductDto product);

    void updateProduct(Long id, ProductDto product);
}
