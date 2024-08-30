package ecom.productservice.models;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseModel{
    private String email;
    private String password;
    private String name;
    private String address;

    @Builder.Default
    private UserRole role = UserRole.USER;

    @Builder.Default
    private boolean enabled = true;

    @Builder.Default
    private boolean tokenExpired = false;
}
