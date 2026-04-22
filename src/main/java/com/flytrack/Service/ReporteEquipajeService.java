package com.flytrack.Service;

import com.flytrack.Model.ReporteEquipaje;
import com.flytrack.Model.Reserva;
import com.flytrack.Repository.ReporteEquipajeRepository;
import com.flytrack.Repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteEquipajeService {

    private final ReporteEquipajeRepository reporteEquipajeRepositorio;
    private final ReservaRepository reservaRepositorio;

    public ReporteEquipaje crearReporte(Long reservaId, String descripcion) {
        Reserva reserva = reservaRepositorio.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        ReporteEquipaje reporte = ReporteEquipaje.builder()
                .descripcion(descripcion)
                .estado("RECIBIDO")
                .reserva(reserva)
                .build();

        return reporteEquipajeRepositorio.save(reporte);
    }

    public List<ReporteEquipaje> obtenerPorPasajero(Long pasajeroId) {
        return reporteEquipajeRepositorio.findByReservaPasajeroId(pasajeroId);
    }
}