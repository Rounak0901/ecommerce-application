package ecom.userservice.models;

import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class Token {
    private String value;
    private Boolean isActive;
    @ManyToOne
    private User user;
    private Date expiryAt;
}
