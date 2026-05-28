package com.itm.edu.FrontendInventario.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    private Integer idProveedor;

    private String nombreProveedor;

    private String contacto;

    private String telefono;

    private String correo;
}