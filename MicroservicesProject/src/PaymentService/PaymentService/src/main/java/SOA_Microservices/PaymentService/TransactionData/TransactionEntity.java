package SOA_Microservices.PaymentService.TransactionData;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "purchase")
    private String purchase;

    @Column(name = "price")
    private Float price;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    public TransactionEntity(Long id, String username, String purchase, Float price, LocalDateTime purchaseDate) {
        this.id = id;
        this.username = username;
        this.purchase = purchase;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public TransactionEntity(String username, String purchase, Float price, LocalDateTime purchaseDate) {
        this.username = username;
        this.purchase = purchase;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public TransactionEntity() {
    }
}
