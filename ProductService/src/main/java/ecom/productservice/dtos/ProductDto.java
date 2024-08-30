package ecom.productservice.dtos;

import ecom.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;

    public static ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

    public Product toProduct(){
        Product product = new Product();
        product.setName(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        return product;
    }
}
