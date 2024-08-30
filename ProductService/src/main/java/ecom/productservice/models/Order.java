package ecom.productservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;

    @ManyToOne
    User user;

    @ManyToMany(cascade = {CascadeType.PERSIST} )
    @JoinTable(
        name = "product_orders",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> orderItems;
    Date orderDate;
    String shippingAddress;
    OrderStatus status;
}
