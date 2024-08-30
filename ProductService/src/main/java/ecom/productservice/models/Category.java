package ecom.productservice.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel{
    private String description;
}
