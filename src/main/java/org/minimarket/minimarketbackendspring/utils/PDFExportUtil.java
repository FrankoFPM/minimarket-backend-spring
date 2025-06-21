package org.minimarket.minimarketbackendspring.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

@Service
public class PDFExportUtil {

    private void agregarLogo(Document document) {
        try {
            InputStream logoStream = getClass().getResourceAsStream("/images/Logo.jpg");
            if (logoStream != null) {
                byte[] logoBytes = logoStream.readAllBytes();
                Image logo = new Image(ImageDataFactory.create(logoBytes));
                logo.setWidth(120);
                logo.setHeight(60);
                
                Paragraph logoContainer = new Paragraph();
                logoContainer.add(logo);
                logoContainer.setTextAlignment(TextAlignment.CENTER);
                document.add(logoContainer);
                
                logoStream.close();
            }
        } catch (Exception e) {
            Paragraph logoText = new Paragraph("MARKET LA CASERITA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(logoText);
        }
    }

    private void agregarTitulo(Document document, String titulo, String descripcion) {
        Paragraph title = new Paragraph(titulo)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold()
                .setMarginTop(20);
        document.add(title);

        Paragraph desc = new Paragraph(descripcion)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setMarginBottom(20);
        document.add(desc);
    }

    private String validarTexto(String texto) {
        return texto != null ? texto : "";
    }

    private Cell crearCeldaCentrada(String texto, boolean esEncabezado) {
        Cell cell = new Cell().add(new Paragraph(texto));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        if (esEncabezado) {
            cell.setBold();
        }
        return cell;
    }

    public byte[] exportarProductosAPDF(List<ProductoDTO> productos) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Logo
        agregarLogo(document);

        // Título y descripción
        agregarTitulo(document, "LISTADO DE PRODUCTOS", 
                     "Reporte completo de todos los productos disponibles");

        // Tabla
        Table table = new Table(UnitValue.createPercentArray(new float[]{8, 18, 22, 10, 8, 10, 12, 12}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados centrados
        table.addCell(crearCeldaCentrada("ID", true));
        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Descripción", true));
        table.addCell(crearCeldaCentrada("Precio", true));
        table.addCell(crearCeldaCentrada("Stock", true));
        table.addCell(crearCeldaCentrada("Estado", true));
        table.addCell(crearCeldaCentrada("Categoría", true));
        table.addCell(crearCeldaCentrada("Proveedor", true));

        // Datos centrados
        for (ProductoDTO p : productos) {
            table.addCell(crearCeldaCentrada(validarTexto(p.getIdProducto()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getNombre()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getDescripcion()), false));
            table.addCell(crearCeldaCentrada(String.valueOf(p.getPrecio() != null ? p.getPrecio() : 0), false));
            table.addCell(crearCeldaCentrada(String.valueOf(p.getStock() != null ? p.getStock() : 0), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getEstado()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getCategoriaNombre()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getProveedorNombre()), false));
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }

    public byte[] exportarProveedoresAPDF(List<ProveedorDTO> proveedores) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Logo
        agregarLogo(document);

        // Título y descripción
        agregarTitulo(document, "LISTADO DE PROVEEDORES", 
                     "Reporte completo de todos los proveedores registrados");

        // Tabla
        Table table = new Table(UnitValue.createPercentArray(new float[]{10, 15, 15, 12, 20, 18, 10}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados centrados
        table.addCell(crearCeldaCentrada("ID", true));
        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Contacto", true));
        table.addCell(crearCeldaCentrada("Teléfono", true));
        table.addCell(crearCeldaCentrada("Dirección", true));
        table.addCell(crearCeldaCentrada("Email", true));
        table.addCell(crearCeldaCentrada("Estado", true));

        // Datos centrados
        for (ProveedorDTO p : proveedores) {
            table.addCell(crearCeldaCentrada(String.valueOf(p.getId() != null ? p.getId() : 0), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getNombre()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getContacto()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getTelefono()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getDireccion()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getEmail()), false));
            table.addCell(crearCeldaCentrada(validarTexto(p.getEstado()), false));
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }

    public byte[] exportarUsuariosAPDF(List<UsuarioDTO> usuarios, String tipoReporte) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Logo
        agregarLogo(document);

        // Título y descripción
        agregarTitulo(document, "LISTADO DE " + tipoReporte.toUpperCase(), 
                     "Reporte completo de " + tipoReporte.toLowerCase() + " registrados");

        // Tabla
        Table table = new Table(UnitValue.createPercentArray(new float[]{15, 15, 25, 12, 13, 10, 10}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezados centrados
        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Apellido", true));
        table.addCell(crearCeldaCentrada("Email", true));
        table.addCell(crearCeldaCentrada("Teléfono", true));
        table.addCell(crearCeldaCentrada("Distrito", true));
        table.addCell(crearCeldaCentrada("Rol", true));
        table.addCell(crearCeldaCentrada("Estado", true));

        // Datos centrados
        for (UsuarioDTO u : usuarios) {
            table.addCell(crearCeldaCentrada(validarTexto(u.getNombre()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getApellido()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getEmail()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getTelefono()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getDistritoNombre()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getRol()), false));
            table.addCell(crearCeldaCentrada(validarTexto(u.getEstado()), false));
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }
}