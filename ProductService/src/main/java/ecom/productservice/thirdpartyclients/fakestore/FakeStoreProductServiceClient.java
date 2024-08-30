package ecom.productservice.thirdpartyclients.fakestore;

import java.util.List;

public interface FakeStoreProductServiceClient {

    FakeStoreProductDto getProductById(Long id);

    List<FakeStoreProductDto> getAllProducts();

    List<FakeStoreProductDto> getProductByCategory(String category);

    void addProduct(FakeStoreProductDto product);

    void updateProduct(Long id, FakeStoreProductDto product);
}
