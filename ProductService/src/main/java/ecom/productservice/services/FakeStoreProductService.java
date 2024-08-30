package ecom.productservice.services;

import ecom.productservice.dtos.ProductDto;
import ecom.productservice.thirdpartyclients.fakestore.FakeStoreProductDto;
import ecom.productservice.thirdpartyclients.fakestore.FakeStoreProductServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final FakeStoreProductServiceClient fakeStoreProductServiceClient;

    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }


    @Override
    public List<ProductDto> getAllProducts() {
        List<FakeStoreProductDto> res =  fakeStoreProductServiceClient.getAllProducts();
        return res.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        FakeStoreProductDto res = fakeStoreProductServiceClient.getProductById(id);
        return mapToProductDto(res);
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<FakeStoreProductDto> res = fakeStoreProductServiceClient.getProductByCategory(category);
        return res.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductDto product) {
        FakeStoreProductDto fakeStoreProductDto = mapToFakeStoreProductDto(product);
        fakeStoreProductServiceClient.addProduct(fakeStoreProductDto);
    }

    @Override
    public void updateProduct(Long id, ProductDto product) {
        FakeStoreProductDto fakeStoreProductDto = mapToFakeStoreProductDto(product);
        fakeStoreProductServiceClient.updateProduct(id, fakeStoreProductDto);
    }

    private ProductDto mapToProductDto(FakeStoreProductDto fakeStoreProductDto) {
        // map FakeStoreProductDto to ProductDto
        ProductDto product = new ProductDto();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());
        return product;
    }

    private FakeStoreProductDto mapToFakeStoreProductDto(ProductDto productDto) {
        // map ProductDto to FakeStoreProductDto
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productDto.getId());
        fakeStoreProductDto.setTitle(productDto.getTitle());
        fakeStoreProductDto.setDescription(productDto.getDescription());
        fakeStoreProductDto.setPrice(productDto.getPrice());
        fakeStoreProductDto.setCategory(productDto.getCategory());
        fakeStoreProductDto.setImage("NA");
        return fakeStoreProductDto;
    }
}
