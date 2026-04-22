package com.flytrack.Mapper;

import com.flytrack.Dto.PasajeroDTO;
import com.flytrack.Model.Pasajero;
import org.springframework.stereotype.Component;

@Component
public class PasajeroMapper {

    public Pasajero toEntity(PasajeroDTO dto) {
        return Pasajero.builder()
                .nombreCompleto(dto.getNombre())
                .numeroDocumento(dto.getDocumento())
                .fechaNacimiento(dto.getFechaNacimiento())
                .correo(dto.getEmail())
                .build();
    }

    public PasajeroDTO toDTO(Pasajero entity) {
        return new PasajeroDTO(
                entity.getNombreCompleto(),
                entity.getNumeroDocumento(),
                entity.getFechaNacimiento(),
                entity.getCorreo()
        );
    }
}