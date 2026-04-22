package com.flytrack.Service;

import com.flytrack.Model.Notificacion;
import com.flytrack.Repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepositorio;

    public List<Notificacion> obtenerPorPasajero(Long pasajeroId) {
        return notificacionRepositorio.findByReservaPasajeroId(pasajeroId);
    }
}
