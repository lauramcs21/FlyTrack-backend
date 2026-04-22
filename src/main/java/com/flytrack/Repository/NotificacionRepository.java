package com.flytrack.Repository;

import com.flytrack.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByReservaPasajeroId(Long pasajeroId);
}