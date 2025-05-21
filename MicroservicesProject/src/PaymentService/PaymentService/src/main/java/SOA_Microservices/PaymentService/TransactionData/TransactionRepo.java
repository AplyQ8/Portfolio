package SOA_Microservices.PaymentService.TransactionData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
}
