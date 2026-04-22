package com.flytrack.Mapper;

import com.flytrack.Dto.ReservaDto;
import com.flytrack.Model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public ReservaDto toDTO(Reserva reserva) {

        return new ReservaDto(
                reserva.getId(),
                reserva.getCodigoReserva(),
                reserva.getNumeroAsiento(),
                reserva.getEstadoTiquete(),

                // vuelo
                reserva.getVuelo().getOrigen(),
                reserva.getVuelo().getDestino(),
                reserva.getVuelo().getFecha(),

                // pasajero
                reserva.getPasajero().getNombreCompleto()
        );
    }
}
