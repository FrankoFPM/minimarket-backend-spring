package org.minimarket.minimarketbackendspring.controllers.utils;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.ProductoService;
import org.minimarket.minimarketbackendspring.services.interfaces.ProveedorService;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.minimarket.minimarketbackendspring.utils.PDFExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PDFController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PDFExportUtil pdfExportService;

    @GetMapping("/productos")
    public ResponseEntity<byte[]> exportarProductosAPDF() throws IOException {
        List<ProductoDTO> productos = productoService.findAll();
        byte[] pdf = pdfExportService.exportarProductosAPDF(productos);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=productos.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

    @GetMapping("/proveedores")
    public ResponseEntity<byte[]> exportarProveedoresAPDF() throws IOException {
        List<ProveedorDTO> proveedores = proveedorService.findAll();
        byte[] pdf = pdfExportService.exportarProveedoresAPDF(proveedores);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=proveedores.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

    @GetMapping("/usuarios/clientes")
    public ResponseEntity<byte[]> exportarClientesAPDF() throws IOException {
        List<UsuarioDTO> clientes = usuarioService.findByRol("cliente");
        byte[] pdf = pdfExportService.exportarUsuariosAPDF(clientes, "Clientes");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=usuarios_clientes.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

    @GetMapping("/usuarios/personal")
    public ResponseEntity<byte[]> exportarPersonalAPDF() throws IOException {
        List<UsuarioDTO> personal = usuarioService.findByRolNot("cliente");
        byte[] pdf = pdfExportService.exportarUsuariosAPDF(personal, "Personal");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=usuarios_personal.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}