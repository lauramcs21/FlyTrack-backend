package com.flytrack.Repository;

import com.flytrack.Model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    boolean existsByNumeroDocumento(String documento);
}