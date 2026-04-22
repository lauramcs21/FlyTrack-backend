package com.flytrack.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vuelos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroVuelo;
    private String aerolinea;
    private String origen;
    private String destino;
    private String horaSalida;
    private LocalDate fecha;
    private String puertaEmbarque;
    private String estado;
    private double precio;
    private int capacidad;

}