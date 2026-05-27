package com.itm.edu.FrontendInventario.controllers;

import com.itm.edu.FrontendInventario.models.Producto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

@Controller
@RequestMapping("/")
public class VistaController {

    private final String API = "http://localhost:8081/docs/productos";

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @GetMapping("/inventario")
    public String inventario(Model model){

        RestTemplate rest = new RestTemplate();

        Producto[] productos =
                rest.getForObject(
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
            @RequestParam String descripcionProducto,
            Model model
    )
    {

        Producto producto = new Producto();

        Instant fecha = Instant.now();
        int idProducto = 10000;
        producto.setIdProducto(idProducto);
        producto.setNomProducto(nomProducto);
        producto.setDescripcionProducto(descripcionProducto);
        producto.setIngresoProducto(Date.from(fecha));

        RestTemplate rest = new RestTemplate();

        try
        {
            rest.postForObject(
                    API + "/nuevo",
                    producto,
                    Integer.class
            );

            return "redirect:/inventario";
        }catch (org.springframework.web.client.RestClientException e){
            model.addAttribute("error", "Error al guardar: Verifica que los datos sean correctos o que el servidor esté activo.");

            model.addAttribute("producto", producto);

            return  "agregar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable int id,
            Model model
    ){

        RestTemplate rest = new RestTemplate();

        Producto producto =
                rest.getForObject(
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

        RestTemplate rest = new RestTemplate();

        producto.setIdProducto(id);

        rest.put(
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

        RestTemplate rest = new RestTemplate();

        Producto producto =
                rest.getForObject(
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

        RestTemplate rest = new RestTemplate();

        rest.delete(
                API + "/" + id
        );

        return "redirect:/inventario";
    }

}
