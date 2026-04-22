package com.flytrack.Service;

import com.flytrack.Model.Notificacion;
import com.flytrack.Model.Reserva;
import com.flytrack.Model.Vuelo;
import com.flytrack.Repository.NotificacionRepository;
import com.flytrack.Repository.ReservaRepository;
import com.flytrack.Repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VueloService {

    private final VueloRepository vueloRepositorio;
    private final ReservaRepository reservaRepositorio;
    private final NotificacionRepository notificacionRepositorio;

    public Vuelo guardarVuelo(Vuelo vuelo) {
        return vueloRepositorio.save(vuelo);
    }

    public void eliminarVuelo(Long id) {
        Vuelo vuelo = vueloRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        vueloRepositorio.delete(vuelo);
    }

    public List<Vuelo> obtenerTodos() {
        return vueloRepositorio.findAll();
    }

    public Vuelo obtenerPorId(Long id) {
        return vueloRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
    }

    public List<Vuelo> filtrarVuelos(String origen, String destino, LocalDate fecha, int pasajeros) {

        List<Vuelo> vuelos = vueloRepositorio.findByOrigenAndDestinoAndFecha(origen, destino, fecha);

        return vuelos.stream()
                .filter(vuelo -> {
                    int ocupados = reservaRepositorio.countByVueloId(vuelo.getId());
                    int disponibles = vuelo.getCapacidad() - ocupados;

                    return disponibles >= pasajeros;
                })
                .toList();
    }

    public Vuelo actualizarVuelo(Long vueloId, Vuelo vueloActualizado) {
        Vuelo vueloExistente = vueloRepositorio.findById(vueloId)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));

        boolean cambioPuerta = !vueloExistente.getPuertaEmbarque().equals(vueloActualizado.getPuertaEmbarque());
        boolean cambioEstado = !vueloExistente.getEstado().equals(vueloActualizado.getEstado());

        vueloExistente.setNumeroVuelo(vueloActualizado.getNumeroVuelo());
        vueloExistente.setAerolinea(vueloActualizado.getAerolinea());
        vueloExistente.setOrigen(vueloActualizado.getOrigen());
        vueloExistente.setDestino(vueloActualizado.getDestino());
        vueloExistente.setHoraSalida(vueloActualizado.getHoraSalida());
        vueloExistente.setPuertaEmbarque(vueloActualizado.getPuertaEmbarque());
        vueloExistente.setEstado(vueloActualizado.getEstado());

        Vuelo vueloGuardado = vueloRepositorio.save(vueloExistente);

        List<Reserva> reservas = reservaRepositorio.findAll()
                .stream()
                .filter(r -> r.getVuelo().getId().equals(vueloId))
                .toList();

        for (Reserva reserva : reservas) {
            if (cambioPuerta) {
                notificacionRepositorio.save(Notificacion.builder()
                        .mensaje("La puerta de embarque del vuelo " + vueloGuardado.getNumeroVuelo()
                                + " cambió a " + vueloGuardado.getPuertaEmbarque())
                        .leida(false)
                        .reserva(reserva)
                        .build());
            }

            if (cambioEstado) {
                notificacionRepositorio.save(Notificacion.builder()
                        .mensaje("El estado del vuelo " + vueloGuardado.getNumeroVuelo()
                                + " cambió a " + vueloGuardado.getEstado())
                        .leida(false)
                        .reserva(reserva)
                        .build());
            }
        }

        return vueloGuardado;
    }
}
