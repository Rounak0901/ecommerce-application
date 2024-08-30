package ecom.productservice.controllers;

import ecom.productservice.dtos.ProductDto;
import ecom.productservice.exceptions.ProductNotFoundException;
import ecom.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier  ("productService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("category") String category) {
        List<ProductDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto product) {
        productService.addProduct(product);
        return ResponseEntity.ok().body("SUCCESS");
    }

    @PutMapping("/")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto product) {
        productService.updateProduct(product.getId(), product);
        return ResponseEntity.ok().body("SUCCESS");
    }

}
