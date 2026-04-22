package com.flytrack.Repository;

import com.flytrack.Model.ReporteEquipaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteEquipajeRepository extends JpaRepository<ReporteEquipaje, Long> {
    List<ReporteEquipaje> findByReservaPasajeroId(Long pasajeroId);
}
