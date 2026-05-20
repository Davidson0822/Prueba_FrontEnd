package com.itm.edu.FrontendInventario.models;


import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class Producto {

    private Integer idProducto;

    private String nomProducto;

    private String descripcionProducto;

    private Date ingresoProducto;
}