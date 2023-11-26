package com.communitas.store.app.controller;


import com.communitas.store.app.controller.dto.VentaRequest;
import com.communitas.store.app.entity.Libro;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaLibro;
import com.communitas.store.app.entity.VentaOrden;
import com.communitas.store.app.repository.LibroRepository;
import com.communitas.store.app.repository.UsuarioRepository;
import com.communitas.store.app.repository.VentaLibroRepository;
import com.communitas.store.app.service.VentaRequestService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/venta")
public class VentaRequestController {
    @Autowired
    private VentaRequestService ventaService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private VentaLibroRepository ventaLibroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/crear")
    public ResponseEntity<String> crearOrdenVenta(@RequestBody VentaRequest ventaRequest) {
        float totalVenta=0;
        try {
            //Obtenemos usuario y libro
            Optional<Usuario> usuarioEncontrado=Optional.of(usuarioRepository.findById(ventaRequest.getIdUsuario()).orElseThrow());
            List<Libro> librosSeleccionados = ventaRequest.getLibrosSeleccionados();

            //Nueva orden de venta
            VentaOrden ordenDeVenta = new VentaOrden();
            ordenDeVenta.setEstado(VentaOrden.Estado.PENDIENTE); // Definimos el estado PENDIENTE por defecto
            ordenDeVenta.setTipoEntrega(ventaRequest.getTipoDeEntrega());
            System.out.println("TIPO DE ENTREGA"+ventaRequest.getTipoDeEntrega());
            ordenDeVenta.setUsuario(usuarioEncontrado.get());

            //Lista de VentaLibro asociados a la orden de venta
            List<VentaLibro> ventaLibros = new ArrayList<>();
            for (Libro libro : librosSeleccionados) {
                VentaLibro ventaLibro = new VentaLibro();
                ventaLibro.setPrecio(libro.getPrecio());
                ventaLibro.setCantidad(1);
                ventaLibro.setLibro(libroRepository.findById(libro.getIdLibro()).orElse(null));
                ventaLibro.setVentaOrden(ordenDeVenta);
                ventaLibros.add(ventaLibro);

                //Obtenemos el total de venta por cada libro en el list
                totalVenta=libro.getPrecio()+totalVenta;
            }

            ordenDeVenta.setTotal(totalVenta);
            ordenDeVenta.setVentaLibros(ventaLibros);

            ventaService.registrarVentaConDetalles(ordenDeVenta);
            // Envío de correo electrónico
            enviarCorreoConfirmacion(ordenDeVenta);

            return new ResponseEntity<>("Orden de venta creada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden de venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void enviarCorreoConfirmacion(VentaOrden ordenDeVenta) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setTo(ordenDeVenta.getUsuario().getEmail());
            helper.setFrom("libreriaproyectocommunitas@gmail.com");
            helper.setSubject("Confirmación de Orden de Venta");

            // Construir el texto del correo con detalles de la orden y libros
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("<html><body>");
            mensaje.append("<p style='font-weight: bold;'>Gracias por tu compra en Communitas.</p>");
            mensaje.append("<p>Detalles de la orden:</p>");
            mensaje.append("<ul>");
            mensaje.append("<li>Número de Orden: ").append(ordenDeVenta.getIdVentaOrden()).append("</li>");
            mensaje.append("<li>Total de Venta: ").append(ordenDeVenta.getTotal()).append("</li>");
            mensaje.append("<li>Estado: ").append(ordenDeVenta.getEstado()).append("</li>");
            mensaje.append("</ul>");

            // Detalles de los libros
            mensaje.append("<p>Detalles de los libros:</p>");
            mensaje.append("<ul>");
            for (VentaLibro ventaLibro : ordenDeVenta.getVentaLibros()) {
                mensaje.append("<li><strong>Libro:</strong> ").append(ventaLibro.getLibro().getTitulo()).append("- ");
                mensaje.append("<strong>Precio:</strong> ").append("s/ "+ventaLibro.getPrecio()).append("</li>");
            }
            mensaje.append("</ul>");

            // Detalles adicionales de la entrega
            mensaje.append("<p>Recordar que el Período de Envío a domilicio es de Máximo 2 días y recojo en tienda es el mismo día</p>");
            mensaje.append("<p style='font-weight: bold;'>Tipo de Entrega: ").append(ordenDeVenta.getTipoEntrega()).append("</p>");

            mensaje.append("<p>Distrito de Entrega: ").append(ordenDeVenta.getUsuario().getDistrito().getNombre()).append("</p>");
            mensaje.append("<p>Dirección de Entrega: ").append(ordenDeVenta.getUsuario().getDireccion()).append("</p>");
            mensaje.append("<p>¡Gracias por elegirnos!</p>");
            mensaje.append("</body></html>");

            helper.setText(mensaje.toString(), true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            // Manejar excepciones en el envío del correo (puedes loguear el error, etc.)
            e.printStackTrace();
        }
    }



}
