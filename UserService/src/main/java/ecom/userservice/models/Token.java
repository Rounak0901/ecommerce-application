package ecom.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Token extends BaseModel {
    private String value;
    private Boolean isActive;
    @ManyToOne
    private User user;
    private Date expiryAt;
}
