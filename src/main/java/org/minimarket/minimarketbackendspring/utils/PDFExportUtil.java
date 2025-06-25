package org.minimarket.minimarketbackendspring.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;
import org.minimarket.minimarketbackendspring.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
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

        agregarLogo(document);
        agregarTitulo(document, "LISTADO DE PRODUCTOS",
                "Reporte completo de todos los productos disponibles");

        Table table = new Table(UnitValue.createPercentArray(new float[]{8, 18, 22, 10, 8, 10, 12, 12}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(crearCeldaCentrada("ID", true));
        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Descripción", true));
        table.addCell(crearCeldaCentrada("Precio", true));
        table.addCell(crearCeldaCentrada("Stock", true));
        table.addCell(crearCeldaCentrada("Estado", true));
        table.addCell(crearCeldaCentrada("Categoría", true));
        table.addCell(crearCeldaCentrada("Proveedor", true));

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

        agregarLogo(document);
        agregarTitulo(document, "LISTADO DE PROVEEDORES",
                "Reporte completo de todos los proveedores registrados");

        Table table = new Table(UnitValue.createPercentArray(new float[]{10, 15, 15, 12, 20, 18, 10}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(crearCeldaCentrada("ID", true));
        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Contacto", true));
        table.addCell(crearCeldaCentrada("Teléfono", true));
        table.addCell(crearCeldaCentrada("Dirección", true));
        table.addCell(crearCeldaCentrada("Email", true));
        table.addCell(crearCeldaCentrada("Estado", true));

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

        agregarLogo(document);
        agregarTitulo(document, "LISTADO DE " + tipoReporte.toUpperCase(),
                "Reporte completo de " + tipoReporte.toLowerCase() + " registrados");

        Table table = new Table(UnitValue.createPercentArray(new float[]{15, 15, 25, 12, 13, 10, 10}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(crearCeldaCentrada("Nombre", true));
        table.addCell(crearCeldaCentrada("Apellido", true));
        table.addCell(crearCeldaCentrada("Email", true));
        table.addCell(crearCeldaCentrada("Teléfono", true));
        table.addCell(crearCeldaCentrada("Distrito", true));
        table.addCell(crearCeldaCentrada("Rol", true));
        table.addCell(crearCeldaCentrada("Estado", true));

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

    public byte[] exportarBoletaPDF(List<CarritoTemporalDto> items, String idUsuario, String numeroTransaccion) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        DeviceRgb colorPrimario = new DeviceRgb(52, 152, 219);
        DeviceRgb colorSecundario = new DeviceRgb(46, 204, 113);
        DeviceRgb colorFondo = new DeviceRgb(248, 249, 250);
        DeviceRgb colorDescuento = new DeviceRgb(231, 76, 60);
        DeviceRgb colorTexto = new DeviceRgb(52, 58, 64);

        agregarLogo(document);
        agregarHeaderBoleta(document, colorPrimario, colorFondo);
        agregarInfoBoletaBonita(document, numeroTransaccion, idUsuario, colorPrimario, colorFondo);
        agregarTablaCarritoBonita(document, items, colorPrimario, colorSecundario, colorDescuento, colorFondo);
        agregarResumenTotalesBonito(document, items, colorPrimario, colorSecundario, colorDescuento, colorFondo);
        agregarPieBoletaBonito(document, colorTexto);

        document.close();
        return out.toByteArray();
    }

    private void agregarHeaderBoleta(Document document, DeviceRgb colorPrimario, DeviceRgb colorFondo) {
        Div headerDiv = new Div();
        headerDiv.setBackgroundColor(colorFondo);
        headerDiv.setPadding(15);
        headerDiv.setMarginBottom(20);
        headerDiv.setBorder(new SolidBorder(colorPrimario, 2));

        Paragraph empresa = new Paragraph("MARKET LA CASERITA")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(22)
                .setBold()
                .setFontColor(colorPrimario)
                .setMarginBottom(5);

        Paragraph slogan = new Paragraph("Tu tienda de confianza desde 2020 ✨")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setItalic()
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(10);

        Paragraph direccion = new Paragraph("Av. Los Mercados 456, Cercado de Lima | (01) 234-5678 | www.caserita.pe")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY);

        headerDiv.add(empresa);
        headerDiv.add(slogan);
        headerDiv.add(direccion);

        document.add(headerDiv);
    }

    private void agregarInfoBoletaBonita(Document document, String numeroTransaccion, String idUsuario,
            DeviceRgb colorPrimario, DeviceRgb colorFondo) {

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{60, 40}));
        infoTable.setWidth(UnitValue.createPercentValue(100));
        infoTable.setMarginBottom(20);

        Cell celdaIzq = new Cell();
        celdaIzq.setBackgroundColor(colorPrimario);
        celdaIzq.setPadding(15);
        celdaIzq.setBorder(Border.NO_BORDER);

        Paragraph tipoBoleta = new Paragraph("BOLETA DE VENTA ELECTRÓNICA")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.WHITE)
                .setMarginBottom(5);

        Paragraph numeroBoleta = new Paragraph("N° " + numeroTransaccion)
                .setFontSize(16)
                .setBold()
                .setFontColor(ColorConstants.WHITE);

        celdaIzq.add(tipoBoleta);
        celdaIzq.add(numeroBoleta);

        Cell celdaDer = new Cell();
        celdaDer.setBackgroundColor(colorFondo);
        celdaDer.setPadding(15);
        celdaDer.setBorder(Border.NO_BORDER);
        celdaDer.setTextAlignment(TextAlignment.RIGHT);

        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        Paragraph fecha = new Paragraph("Fecha: " + fechaActual)
                .setFontSize(10)
                .setMarginBottom(3);

        Paragraph usuario = new Paragraph("Cliente: " + idUsuario)
                .setFontSize(10)
                .setMarginBottom(3);

        Paragraph ruc = new Paragraph("RUC: 20123456789")
                .setFontSize(9)
                .setFontColor(ColorConstants.GRAY);

        celdaDer.add(fecha);
        celdaDer.add(usuario);
        celdaDer.add(ruc);

        infoTable.addCell(celdaIzq);
        infoTable.addCell(celdaDer);

        document.add(infoTable);
    }

    private void agregarTablaCarritoBonita(Document document, List<CarritoTemporalDto> items,
            DeviceRgb colorPrimario, DeviceRgb colorSecundario,
            DeviceRgb colorDescuento, DeviceRgb colorFondo) {

        DeviceRgb colorBlanco = new DeviceRgb(255, 255, 255);

        // Tabla con 6 columnas (sin Estado)
        Table table = new Table(UnitValue.createPercentArray(new float[]{30, 10, 15, 15, 15, 15}));
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {"🛍️ Producto", "Cant.", "P. Orig.", "Descuento", "P. Final", "Subtotal"};

        for (String header : headers) {
            Cell headerCell = new Cell();
            headerCell.add(new Paragraph(header));
            headerCell.setBackgroundColor(colorPrimario);
            headerCell.setFontColor(ColorConstants.WHITE);
            headerCell.setTextAlignment(TextAlignment.CENTER);
            headerCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerCell.setBold();
            headerCell.setPadding(10);
            headerCell.setBorder(Border.NO_BORDER);
            table.addHeaderCell(headerCell);
        }

        boolean filaAlterna = false;
        for (CarritoTemporalDto item : items) {
            DeviceRgb colorFilaFondo = filaAlterna ? colorFondo : colorBlanco;

            // Producto
            Cell productoCell = crearCeldaBonita(validarTexto(item.getIdProductoNombre()), colorFilaFondo, false);
            productoCell.setTextAlignment(TextAlignment.LEFT);
            table.addCell(productoCell);

            // Cantidad
            table.addCell(crearCeldaBonita(item.getCantidad().toString(), colorFilaFondo, false));

            // Precio Original
            BigDecimal precioOrig = item.getPrecioOriginal() != null
                    ? item.getPrecioOriginal() : BigDecimal.valueOf(item.getIdProductoPrecio());
            table.addCell(crearCeldaBonita("S/ " + String.format("%.2f", precioOrig), colorFilaFondo, false));

            // Descuento
            Cell descuentoCell = new Cell();
            descuentoCell.setBackgroundColor(colorFilaFondo);
            descuentoCell.setTextAlignment(TextAlignment.CENTER);
            descuentoCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            descuentoCell.setPadding(8);
            descuentoCell.setBorder(Border.NO_BORDER);

            if (item.getTieneDescuento() != null && item.getTieneDescuento()) {
                Paragraph descP1 = new Paragraph("💸 -S/ " + String.format("%.2f", item.getMontoDescuento()))
                        .setFontSize(9)
                        .setBold()
                        .setFontColor(colorDescuento)
                        .setMarginBottom(2);

                Paragraph descP2 = new Paragraph("(" + String.format("%.1f", item.getPorcentajeDescuento()) + "% OFF)")
                        .setFontSize(8)
                        .setItalic()
                        .setFontColor(colorDescuento);

                descuentoCell.add(descP1);
                descuentoCell.add(descP2);
            } else {
                descuentoCell.add(new Paragraph("-").setFontSize(10));
            }
            table.addCell(descuentoCell);

            // Precio Final
            BigDecimal precioFinal = item.getPrecioConDescuento() != null
                    ? item.getPrecioConDescuento() : BigDecimal.valueOf(item.getIdProductoPrecio());
            Cell precioFinalCell = crearCeldaBonita("S/ " + String.format("%.2f", precioFinal), colorFilaFondo, true);
            precioFinalCell.setFontColor(colorSecundario);
            table.addCell(precioFinalCell);

            // Subtotal
            BigDecimal subtotal = precioFinal.multiply(BigDecimal.valueOf(item.getCantidad()));
            table.addCell(crearCeldaBonita("S/ " + String.format("%.2f", subtotal), colorFilaFondo, true));

            filaAlterna = !filaAlterna;
        }

        document.add(table);
    }

    private void agregarResumenTotalesBonito(Document document, List<CarritoTemporalDto> items,
            DeviceRgb colorPrimario, DeviceRgb colorSecundario,
            DeviceRgb colorDescuento, DeviceRgb colorFondo) {

        BigDecimal subtotalSinDescuentos = BigDecimal.ZERO;
        BigDecimal totalDescuentos = BigDecimal.ZERO;
        BigDecimal totalFinal = BigDecimal.ZERO;
        int itemsConDescuento = 0;

        for (CarritoTemporalDto item : items) {
            BigDecimal precioOrig = item.getPrecioOriginal() != null
                    ? item.getPrecioOriginal() : BigDecimal.valueOf(item.getIdProductoPrecio());
            BigDecimal precioFinal = item.getPrecioConDescuento() != null
                    ? item.getPrecioConDescuento() : BigDecimal.valueOf(item.getIdProductoPrecio());
            BigDecimal cantidad = BigDecimal.valueOf(item.getCantidad());

            subtotalSinDescuentos = subtotalSinDescuentos.add(precioOrig.multiply(cantidad));
            totalFinal = totalFinal.add(precioFinal.multiply(cantidad));

            if (item.getTieneDescuento() != null && item.getTieneDescuento()) {
                itemsConDescuento++;
            }
        }

        totalDescuentos = subtotalSinDescuentos.subtract(totalFinal);
        DeviceRgb colorNegro = new DeviceRgb(0, 0, 0);

        Div resumenDiv = new Div();
        resumenDiv.setWidth(UnitValue.createPercentValue(50));
        resumenDiv.setMarginTop(25);

        Table tablaTotales = new Table(UnitValue.createPercentArray(new float[]{60, 40}));
        tablaTotales.setWidth(UnitValue.createPercentValue(100));
        resumenDiv.setMarginLeft(200);

        tablaTotales.addCell(crearCeldaTotalIzquierda("Subtotal:", false, colorNegro));
        tablaTotales.addCell(crearCeldaTotal("S/ " + String.format("%.2f", subtotalSinDescuentos), false, colorNegro));

        if (totalDescuentos.compareTo(BigDecimal.ZERO) > 0) {
            tablaTotales.addCell(crearCeldaTotalIzquierda("Descuentos (" + itemsConDescuento + " items):", false, colorDescuento));
            tablaTotales.addCell(crearCeldaTotal("- S/ " + String.format("%.2f", totalDescuentos), false, colorDescuento));
        }

        Cell separador1 = new Cell();
        separador1.setBorderTop(new SolidBorder(colorPrimario, 2));
        separador1.setBorderBottom(Border.NO_BORDER);
        separador1.setBorderLeft(Border.NO_BORDER);
        separador1.setBorderRight(Border.NO_BORDER);
        separador1.setHeight(8);

        Cell separador2 = new Cell();
        separador2.setBorderTop(new SolidBorder(colorPrimario, 2));
        separador2.setBorderBottom(Border.NO_BORDER);
        separador2.setBorderLeft(Border.NO_BORDER);
        separador2.setBorderRight(Border.NO_BORDER);
        separador2.setHeight(8);

        tablaTotales.addCell(separador1);
        tablaTotales.addCell(separador2);

        Cell totalLabelCell = crearCeldaTotalIzquierda("TOTAL A PAGAR:", true, colorPrimario);
        totalLabelCell.setBackgroundColor(colorFondo);
        totalLabelCell.setPadding(10);

        Cell totalValueCell = crearCeldaTotal("S/ " + String.format("%.2f", totalFinal), true, colorPrimario);
        totalValueCell.setBackgroundColor(colorFondo);
        totalValueCell.setPadding(10);

        tablaTotales.addCell(totalLabelCell);
        tablaTotales.addCell(totalValueCell);

        resumenDiv.add(tablaTotales);
        document.add(resumenDiv);

        if (totalDescuentos.compareTo(BigDecimal.ZERO) > 0) {
            Div ahorroDiv = new Div();
            ahorroDiv.setBackgroundColor(new DeviceRgb(255, 248, 220));
            ahorroDiv.setBorder(new SolidBorder(new DeviceRgb(255, 193, 7), 2));
            ahorroDiv.setPadding(15);
            ahorroDiv.setMarginTop(20);

            Paragraph ahorroMsg = new Paragraph("¡FELICIDADES! Has ahorrado S/ " + String.format("%.2f", totalDescuentos) + " en esta compra")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(new DeviceRgb(184, 134, 11));

            ahorroDiv.add(ahorroMsg);
            document.add(ahorroDiv);
        }
    }

    private void agregarPieBoletaBonito(Document document, DeviceRgb colorTexto) {
        document.add(new Paragraph(" ").setMarginTop(30));

        Paragraph gracias = new Paragraph("¡Gracias por elegirnos!")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16)
                .setBold()
                .setFontColor(new DeviceRgb(46, 204, 113))
                .setMarginBottom(15);
        document.add(gracias);

        Paragraph info1 = new Paragraph("Conserve este comprobante para cualquier reclamo o devolución")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setFontColor(colorTexto)
                .setMarginBottom(5);
        document.add(info1);

        Paragraph info2 = new Paragraph("🔄 Política de devoluciones: Revise antes de irse xd | Aceptamos todas las formas de pago")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(9)
                .setFontColor(ColorConstants.GRAY)
                .setMarginBottom(5);
        document.add(info2);

        Paragraph info3 = new Paragraph("Síguenos en redes sociales para más ofertas: @MarketLaCaserita")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(9)
                .setItalic()
                .setFontColor(ColorConstants.GRAY);
        document.add(info3);
    }

    private Cell crearCeldaBonita(String texto, DeviceRgb colorFondo, boolean esBold) {
        Cell cell = new Cell();
        cell.add(new Paragraph(texto));
        cell.setBackgroundColor(colorFondo);
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(8);
        cell.setBorder(Border.NO_BORDER);
        if (esBold) {
            cell.setBold();
        }
        return cell;
    }

    private Cell crearCeldaTotal(String texto, boolean esBold, DeviceRgb color) {
        Cell cell = new Cell();
        Paragraph p = new Paragraph(texto);
        if (esBold) {
            p.setBold();
            p.setFontSize(12);
        } else {
            p.setFontSize(11);
        }
        p.setFontColor(color);
        cell.add(p);
        cell.setTextAlignment(TextAlignment.RIGHT);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(5);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell crearCeldaTotalIzquierda(String texto, boolean esBold, DeviceRgb color) {
        Cell cell = new Cell();
        Paragraph p = new Paragraph(texto);
        if (esBold) {
            p.setBold();
            p.setFontSize(12);
        } else {
            p.setFontSize(11);
        }
        p.setFontColor(color);
        cell.add(p);
        cell.setTextAlignment(TextAlignment.LEFT);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setPadding(5);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
}
