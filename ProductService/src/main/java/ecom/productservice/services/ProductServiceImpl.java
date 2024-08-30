package ecom.productservice.services;

import ecom.productservice.dtos.ProductDto;
import ecom.productservice.exceptions.ProductNotFoundException;
import ecom.productservice.models.Product;
import ecom.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDto::from).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow( ()-> new ProductNotFoundException("Product with id " + id + " not found"));
        return ProductDto.from(product);
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        return products.stream().map(ProductDto::from).collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductDto product) {
        productRepository.save(product.toProduct());
    }

    @Override
    public void updateProduct(Long id, ProductDto product) {
        productRepository.findById(id).ifPresentOrElse(p -> {
            p.setName(product.getTitle());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            productRepository.save(p);
        }, ()-> {
            Product newProduct = product.toProduct();
            productRepository.save(newProduct);
        });
    }

}
