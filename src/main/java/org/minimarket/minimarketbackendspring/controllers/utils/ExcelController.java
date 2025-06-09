package org.minimarket.minimarketbackendspring.controllers.utils;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.ProductoService;
import org.minimarket.minimarketbackendspring.services.interfaces.ProveedorService;
import org.minimarket.minimarketbackendspring.services.interfaces.UsuarioService;
import org.minimarket.minimarketbackendspring.utils.ExcelExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*")
public class ExcelController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ExcelExportUtil excelExportService;

    @GetMapping("/productos")
    public ResponseEntity<byte[]> exportarProductosAExcel() throws IOException {
        List<ProductoDTO> productos = productoService.findAll();
        byte[] excel = excelExportService.exportarProductosAExcel(productos);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=productos.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }
    @GetMapping("/proveedores")
    public ResponseEntity<byte[]> exportarProveedoresAExcel() throws IOException {
        List<ProveedorDTO> proveedores = proveedorService.findAll();
        byte[] excel = excelExportService.exportarProveedoresAExcel(proveedores);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=proveedores.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }
    @GetMapping("/usuarios/clientes")
    public ResponseEntity<byte[]> exportarClientesAExcel() throws IOException {
        List<UsuarioDTO> clientes = usuarioService.findByRol("cliente");
        byte[] excel = excelExportService.exportarUsuariosAExcel(clientes, "Clientes");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=usuarios_clientes.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }
    @GetMapping("/usuarios/personal")
    public ResponseEntity<byte[]> exportarPersonalAExcel() throws IOException {
        List<UsuarioDTO> personal = usuarioService.findByRolNot("cliente");
        byte[] excel = excelExportService.exportarUsuariosAExcel(personal, "Personal");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=usuarios_personal.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }
}
