package com.flytrack.Repository;

import com.flytrack.Model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    List<Vuelo> findByOrigenAndDestinoAndFecha(String origen, String destino, LocalDate fecha);
}