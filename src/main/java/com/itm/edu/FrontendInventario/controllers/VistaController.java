package com.itm.edu.FrontendInventario.controllers;

import com.itm.edu.FrontendInventario.models.Producto;
import com.itm.edu.FrontendInventario.models.Proveedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

@Controller
@RequestMapping("/")
public class VistaController {

    @Autowired
    private RestTemplate restTemplate;

    private final String API = "http://localhost:8081/docs/productos";

    private final String API_PROVEEDOR = "http://localhost:8081/docs/proveedores";

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping("/inventario")
    public String inventario(Model model){

        RestTemplate restTemplate = new RestTemplate();

        Producto[] productos =
                VistaController.this.restTemplate.getForObject(
                        API + "/listar",
                        Producto[].class
                );

        model.addAttribute("productos", productos);

        return "inventario";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model){
        model.addAttribute("producto", new Producto());
        return "agregar";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam String nomProducto,
            @RequestParam String descripcionProducto
    )
    {

        Producto producto = new Producto();

        Instant fecha = Instant.now();
        int idProducto = 10000;
        producto.setIdProducto(idProducto);
        producto.setNomProducto(nomProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setIngresoProducto(Date.from(fecha));

        RestTemplate restTemplate = new RestTemplate();

        VistaController.this.restTemplate.postForObject(
                API + "/nuevo",
                producto,
                Integer.class
        );

        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable int id,
            Model model
    ){

        RestTemplate restTemplate = new RestTemplate();

        Producto producto =
                VistaController.this.restTemplate.getForObject(
                        API + "/" + id,
                        Producto.class
                );

        model.addAttribute(
                "producto",
                producto
        );

        return "editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable int id,
            @RequestParam String nomProducto,
            @RequestParam String descripcionProducto
    )
    {

        Producto producto = new Producto();

        Instant fecha = Instant.now();
        producto.setIdProducto(id);
        producto.setNomProducto(nomProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setIngresoProducto(Date.from(fecha));

        RestTemplate restTemplate = new RestTemplate();

        producto.setIdProducto(id);

        VistaController.this.restTemplate.put(
                API + "/" + id,
                producto
        );

        return "redirect:/inventario";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(
            @PathVariable int id,
            Model model
    ){

        RestTemplate restTemplate = new RestTemplate();

        Producto producto =
                VistaController.this.restTemplate.getForObject(
                        API + "/" + id,
                        Producto.class
                );

        model.addAttribute("producto", producto);

        return "detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable int id
    ){

        RestTemplate restTemplate = new RestTemplate();

        VistaController.this.restTemplate.delete(
                API + "/" + id
        );

        return "redirect:/inventario";
    }

    //inicio de proveedores

    @GetMapping("/proveedores")
    public String proveedores(Model model){

        Proveedor[] proveedores =
                restTemplate.getForObject(
                        API_PROVEEDOR + "/listar",
                        Proveedor[].class
                );

        model.addAttribute(
                "proveedores",
                proveedores
        );

        return "proveedores";
    }

    @GetMapping("/proveedores/nuevo")
    public String nuevoProveedor(Model model){

        model.addAttribute(
                "proveedor",
                new Proveedor()
        );

        return "agregarProveedor";
    }

    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(
            @ModelAttribute Proveedor proveedor
    ){

        restTemplate.postForObject(
                API_PROVEEDOR + "/nuevo",
                proveedor,
                Proveedor.class
        );

        return "redirect:/proveedores";
    }
    @GetMapping("/proveedores/detalle/{id}")
    public String detalleProveedor(
            @PathVariable Integer id,
            Model model
    ){

        Proveedor proveedor =
                restTemplate.getForObject(
                        API_PROVEEDOR + "/buscar/" + id,
                        Proveedor.class
                );

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "detalleProveedor";
    }

    @GetMapping("/proveedores/editar/{id}")
    public String editarProveedor(
            @PathVariable Integer id,
            Model model
    ){

        Proveedor proveedor =
                restTemplate.getForObject(
                        API_PROVEEDOR + "/buscar/" + id,
                        Proveedor.class
                );

        model.addAttribute(
                "proveedor",
                proveedor
        );

        return "editarProveedor";
    }

    @PostMapping("/proveedores/actualizar/{id}")
    public String actualizarProveedor(
            @PathVariable Integer id,
            @ModelAttribute Proveedor proveedor
    ){

        restTemplate.put(
                API_PROVEEDOR + "/actualizar/" + id,
                proveedor
        );

        return "redirect:/proveedores";
    }

    @GetMapping("/proveedores/eliminar/{id}")
    public String eliminarProveedor(
            @PathVariable Integer id
    ){

        restTemplate.delete(
                API_PROVEEDOR + "/eliminar/" + id
        );

        return "redirect:/proveedores";
    }
}
