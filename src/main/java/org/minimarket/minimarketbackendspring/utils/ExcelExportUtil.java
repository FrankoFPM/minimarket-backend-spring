package org.minimarket.minimarketbackendspring.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

@Service
public class ExcelExportUtil {

    private void agregarLogo(Workbook workbook, Sheet sheet, int ultimaColumnaData) {
        try {
            InputStream logoStream = getClass().getResourceAsStream("/images/Logo.jpg");
            if (logoStream != null) {
                byte[] logoBytes = IOUtils.toByteArray(logoStream);
                int pictureIdx = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);

                Drawing<?> drawing = sheet.createDrawingPatriarch();
                CreationHelper helper = workbook.getCreationHelper();
                ClientAnchor anchor = helper.createClientAnchor();

                int columnaLogo = ultimaColumnaData + 5;
                anchor.setCol1(columnaLogo);
                anchor.setRow1(0);
                // 9 columnas de ancho
                anchor.setCol2(columnaLogo + 9);
                // 10 filas de alto
                anchor.setRow2(10);

                Picture picture = drawing.createPicture(anchor, pictureIdx);
                picture.resize(0.5);

                logoStream.close();
            }
        } catch (Exception e) {
            Row logoRow = sheet.getRow(0);
            if (logoRow == null) {
                logoRow = sheet.createRow(0);
            }
            Cell logoCell = logoRow.createCell(ultimaColumnaData + 5);
            logoCell.setCellValue("MARKET LA CASERITA");
        }
    }

    public byte[] exportarProductosAExcel(List<ProductoDTO> productos) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Descripción");
        header.createCell(3).setCellValue("Precio");
        header.createCell(4).setCellValue("Stock");
        header.createCell(5).setCellValue("Estado");
        header.createCell(6).setCellValue("Categoría");
        header.createCell(7).setCellValue("Proveedor");

        // Datos
        int rowNum = 1;
        for (ProductoDTO p : productos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getIdProducto());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getDescripcion());
            row.createCell(3).setCellValue(p.getPrecio() != null ? p.getPrecio() : 0);
            row.createCell(4).setCellValue(p.getStock() != null ? p.getStock() : 0);
            row.createCell(5).setCellValue(p.getEstado());
            row.createCell(6).setCellValue(p.getCategoriaNombre());
            row.createCell(7).setCellValue(p.getProveedorNombre());
        }

        // Agregar logo
        agregarLogo(workbook, sheet, 8);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    public byte[] exportarProveedoresAExcel(List<ProveedorDTO> proveedores) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Proveedores");

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Contacto");
        header.createCell(3).setCellValue("Teléfono");
        header.createCell(4).setCellValue("Dirección");
        header.createCell(5).setCellValue("Email");
        header.createCell(6).setCellValue("Estado");
        header.createCell(7).setCellValue("Creado");
        header.createCell(8).setCellValue("Actualizado");

        // Datos
        int rowNum = 1;
        for (ProveedorDTO p : proveedores) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getId() != null ? p.getId() : 0);
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getContacto());
            row.createCell(3).setCellValue(p.getTelefono());
            row.createCell(4).setCellValue(p.getDireccion());
            row.createCell(5).setCellValue(p.getEmail());
            row.createCell(6).setCellValue(p.getEstado());
            row.createCell(7).setCellValue(p.getCreatedAt() != null ? p.getCreatedAt().toString() : "");
            row.createCell(8).setCellValue(p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : "");
        }

        // Agregar logo
        agregarLogo(workbook, sheet, 9);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    public byte[] exportarUsuariosAExcel(List<UsuarioDTO> usuarios, String nombreHoja) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(nombreHoja);

        // Encabezados
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Apellido");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Teléfono");
        header.createCell(5).setCellValue("Distrito");
        header.createCell(6).setCellValue("Dirección");
        header.createCell(7).setCellValue("Rol");
        header.createCell(8).setCellValue("Estado");

        // Datos
        int rowNum = 1;
        for (UsuarioDTO u : usuarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getId());
            row.createCell(1).setCellValue(u.getNombre());
            row.createCell(2).setCellValue(u.getApellido());
            row.createCell(3).setCellValue(u.getEmail());
            row.createCell(4).setCellValue(u.getTelefono());
            row.createCell(5).setCellValue(u.getDistritoNombre() != null ? u.getDistritoNombre() : "");
            row.createCell(6).setCellValue(u.getDireccion());
            row.createCell(7).setCellValue(u.getRol());
            row.createCell(8).setCellValue(u.getEstado());
        }

        // Agregar logo
        agregarLogo(workbook, sheet, 9);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}
