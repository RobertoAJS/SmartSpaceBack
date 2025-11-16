package pe.edu.smartspace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.smartspace.entities.Pago;

public interface IPagoRepository extends JpaRepository<Pago, Long> {
}
