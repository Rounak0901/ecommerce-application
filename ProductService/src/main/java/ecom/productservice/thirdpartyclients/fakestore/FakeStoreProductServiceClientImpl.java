package ecom.productservice.thirdpartyclients.fakestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeStoreProductServiceClientImpl implements FakeStoreProductServiceClient {

    String fakeStoreProductsApiUrl;

    RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductServiceClientImpl(
            @Value("${fakestore.api.url}") String fakeStoreApiUrl,
            @Value("${fakestore.api.path.product}") String fakeStoreProductsPath
    ) {
        this.restTemplateBuilder = new RestTemplateBuilder();
        fakeStoreProductsApiUrl = fakeStoreApiUrl + fakeStoreProductsPath;
        System.out.println("FakeStoreProductServiceClientImpl: fakeStoreProductsApiUrl: " + fakeStoreProductsApiUrl);
    }

    @Override
    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response = restTemplateBuilder.build().getForEntity(fakeStoreProductsApiUrl, FakeStoreProductDto[].class);
        FakeStoreProductDto[] products = response.getBody();
        return List.of(products);
    }

    @Override
    public FakeStoreProductDto getProductById(Long id) {
        ResponseEntity<FakeStoreProductDto> response = restTemplateBuilder.build().getForEntity(fakeStoreProductsApiUrl + "/" + id, FakeStoreProductDto.class);
        return response.getBody();
    }

    public List<FakeStoreProductDto> getProductByCategory(String category) {
        ResponseEntity<FakeStoreProductDto[]> response = restTemplateBuilder.build().getForEntity(String.format(fakeStoreProductsApiUrl +"/categories/"+category), FakeStoreProductDto[].class);
        FakeStoreProductDto[] products = response.getBody();
        return List.of(products);
    }

    @Override
    public void addProduct(FakeStoreProductDto product) {
        restTemplateBuilder.build().postForEntity(fakeStoreProductsApiUrl, product, FakeStoreProductDto.class);
    }

    @Override
    public void updateProduct(Long id, FakeStoreProductDto product) {
        restTemplateBuilder.build().put(fakeStoreProductsApiUrl + "/" + id, product);
    }
}
