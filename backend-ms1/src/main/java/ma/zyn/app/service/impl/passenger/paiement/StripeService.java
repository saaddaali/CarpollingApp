package ma.zyn.app.service.impl.passenger.paiement;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.utils.export.PdfConfig;
import ma.zyn.app.utils.transverse.emailling.EmailService;
import ma.zyn.app.ws.dto.reservation.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;


    public String checkOut(Long amount, ReservationDto reservation) {
        Stripe.apiKey = "sk_test_51Onk9WAf41bGTPf1uDA4T1bs0C4wJUZFTwC71e1wSUIvIUwzMS6v3aW7Bz6HaWu7hudXfj51TwPz2d25fiqvE0c800cbWkZahr";
        if (Stripe.apiKey == null || Stripe.apiKey.isEmpty()) {
            throw new IllegalStateException("Stripe API key is not set. Please configure it in environment variables.");
        }


        // Create product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Reservation")
                        .build();

        amount = amount * 10; // Convert amount to cents
        // Create price data
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("USD")
                        .setProductData(productData)
                        .setUnitAmount(amount)
                        .build();

        // Create line item
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setPriceData(priceData)
                        .setQuantity(1L)
                        .build();

        // Create session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/reservations/list")
                .setCancelUrl("http://localhost:4200/app/passenger/trajet")
                .build();

        // Create session
        try {
            Session session = Session.create(params);
            sendInvoice(reservation);
            return session.getUrl(); // Return session URL for redirection
        } catch (StripeException e) {
            System.out.println("Stripe session creation failed: " + e);
            throw new RuntimeException("Payment session creation failed.");
        }
    }

    public class PdfConfig extends PdfPageEventHelper {
        private Image headerImage;
        private static final Font FOOTER_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);

        public void setHeaderImage(String path) throws IOException, DocumentException {
            if (path != null && !path.trim().isEmpty()) {
                try {
                    this.headerImage = Image.getInstance(path);
                    this.headerImage.scaleToFit(500, 70);
                    this.headerImage.setAlignment(Element.ALIGN_CENTER);
                } catch (IOException | DocumentException e) {
                    throw new IOException("Failed to load header image: " + e.getMessage(), e);
                }
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                // Add header image if available
                if (headerImage != null) {
                    float x = document.left();
                    float y = document.top() + 30;
                    headerImage.setAbsolutePosition(x, y);
                    writer.getDirectContent().addImage(headerImage);
                }

                // Add page number
                Rectangle rect = writer.getBoxSize("art");
                if (rect == null) {
                    rect = document.getPageSize();
                }

                ColumnText.showTextAligned(writer.getDirectContent(),
                        Element.ALIGN_CENTER,
                        new Phrase(String.format("Page %d", writer.getPageNumber()), FOOTER_FONT),
                        (rect.getLeft() + rect.getRight()) / 2,
                        rect.getBottom() + 15,
                        0);
            } catch (DocumentException e) {
                // Log the error but don't throw to prevent PDF corruption
                System.err.println("Error adding page elements: " + e.getMessage());
            }
        }

        public void addExportDate(Document document) throws DocumentException {
            if (!document.isOpen()) {
                throw new DocumentException("Cannot add export date to closed document");
            }

            Font dateFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            Paragraph datePara = new Paragraph("Generated on: " + currentDate, dateFont);
            datePara.setAlignment(Element.ALIGN_RIGHT);
            document.add(datePara);
        }
    }

    private static final BaseColor PRIMARY_COLOR = new BaseColor(64, 82, 238);
    private static final BaseColor GRAY_COLOR = new BaseColor(149, 165, 166);

    public ByteArrayOutputStream generateReservationPdf(ReservationDto reservation) throws DocumentException, IOException {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = null;
        PdfWriter writer = null;

        try {
            // Initialize document with custom margins
            document = new Document(PageSize.A4, 36, 36, 90, 36);
            writer = PdfWriter.getInstance(document, baos);

            // Configure PDF settings
            PdfConfig pdfConfig = new PdfConfig();
            try {
                pdfConfig.setHeaderImage("/Users/saaddaali/Documents/carpooling/frontend/src/assets/layout/images/blue.png"); // Update with correct path
                writer.setPageEvent(pdfConfig);
            } catch (IOException e) {
                System.err.println("Warning: Could not load header image: " + e.getMessage());
                // Continue without header image
            }

            document.open();

            // Add content
            addTitle(document);
            addPassengerAndReservationInfo(document, reservation);
            addJourneyDetails(document, reservation);
            addPaymentDetails(document, reservation);
            addDriverInfo(document, reservation);

            // Add thank you message
            addThankYouMessage(document);

            // Add export date
            pdfConfig.addExportDate(document);

            return baos;
        } catch (Exception e) {
            throw new DocumentException("Failed to generate PDF: " + e.getMessage(), e);
        } finally {
            // Ensure proper cleanup
            if (document != null && document.isOpen()) {
                try {
                    document.close();
                } catch (Exception e) {
                    System.err.println("Error closing document: " + e.getMessage());
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }

    private void addTitle(Document document) throws DocumentException {
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, PRIMARY_COLOR);
        Paragraph title = new Paragraph("RESERVATION IVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(40);
        document.add(title);
    }

    private void addPassengerAndReservationInfo(Document document, ReservationDto reservation) throws DocumentException {
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, GRAY_COLOR);

        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingAfter(30);

        // Passenger info
        PdfPCell passengerCell = new PdfPCell();
        passengerCell.setBorder(Rectangle.NO_BORDER);
        passengerCell.addElement(new Paragraph("PASSENGER DETAILS", headingFont));
        passengerCell.addElement(new Paragraph("\n"));
        passengerCell.addElement(new Paragraph("Name", labelFont));
        String passengerName = reservation.getPassenger().getFullName();
        passengerCell.addElement(new Paragraph(passengerName, contentFont));
        infoTable.addCell(passengerCell);

        // Reservation info
        PdfPCell reservationCell = new PdfPCell();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        reservationCell.setBorder(Rectangle.NO_BORDER);
        reservationCell.addElement(new Paragraph("RESERVATION INFO", headingFont));
        reservationCell.addElement(new Paragraph("\n"));
        reservationCell.addElement(new Paragraph("Reservation Date", labelFont));
        reservationCell.addElement(new Paragraph(currentDate, contentFont));
        reservationCell.addElement(new Paragraph("Payment Date", labelFont));
        reservationCell.addElement(new Paragraph(currentDate, contentFont));
        infoTable.addCell(reservationCell);

        document.add(infoTable);
    }

    private void addJourneyDetails(Document document, ReservationDto reservation) throws DocumentException {
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, GRAY_COLOR);

        Paragraph journeyTitle = new Paragraph("JOURNEY DETAILS", headingFont);
        journeyTitle.setSpacingAfter(20);
        document.add(journeyTitle);

        PdfPTable journeyTable = new PdfPTable(2);
        journeyTable.setWidthPercentage(100);
        journeyTable.setSpacingAfter(30);

        // Departure details
        PdfPCell departureCell = new PdfPCell();
        departureCell.setBorder(Rectangle.BOX);
        departureCell.setBackgroundColor(new BaseColor(245, 245, 245));
        departureCell.setPadding(10);
        departureCell.addElement(new Paragraph("FROM", labelFont));
        departureCell.addElement(new Paragraph(reservation.getTrajet().getVilleDepart().getLibelle(), contentFont));
        departureCell.addElement(new Paragraph("Departure Time", labelFont));
        departureCell.addElement(new Paragraph(reservation.getTrajet().getHoraireDepart(), contentFont));
        journeyTable.addCell(departureCell);

        // Arrival details
        PdfPCell arrivalCell = new PdfPCell();
        arrivalCell.setBorder(Rectangle.BOX);
        arrivalCell.setBackgroundColor(new BaseColor(245, 245, 245));
        arrivalCell.setPadding(10);
        arrivalCell.addElement(new Paragraph("TO", labelFont));
        arrivalCell.addElement(new Paragraph(reservation.getTrajet().getVilleDestination().getLibelle(), contentFont));
        arrivalCell.addElement(new Paragraph("Arrival Time", labelFont));
        arrivalCell.addElement(new Paragraph(reservation.getTrajet().getHoraireArrive(), contentFont));
        journeyTable.addCell(arrivalCell);

        document.add(journeyTable);
    }

    private void addPaymentDetails(Document document, ReservationDto reservation) throws DocumentException {
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, PRIMARY_COLOR);

        PdfPTable paymentTable = new PdfPTable(2);
        paymentTable.setWidthPercentage(100);
        paymentTable.setSpacingAfter(20);

        PdfPCell amountLabelCell = new PdfPCell(new Paragraph("TOTAL AMOUNT", headingFont));
        amountLabelCell.setBorder(Rectangle.NO_BORDER);
        amountLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        paymentTable.addCell(amountLabelCell);

        PdfPCell amountValueCell = new PdfPCell(new Paragraph(String.format("%.2f MAD", reservation.getMontant()), headingFont));
        amountValueCell.setBorder(Rectangle.NO_BORDER);
        amountValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        paymentTable.addCell(amountValueCell);

        document.add(paymentTable);
    }

    private void addDriverInfo(Document document, ReservationDto reservation) throws DocumentException {
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, GRAY_COLOR);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        Paragraph driverInfo = new Paragraph();
        driverInfo.add(new Chunk("Driver: ", labelFont));
        driverInfo.add(new Chunk(reservation.getDriver().getUsername(), contentFont));
        driverInfo.setSpacingBefore(20);
        document.add(driverInfo);
    }

    private void addThankYouMessage(Document document) throws DocumentException {
        Font messageFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, PRIMARY_COLOR);

        Paragraph thankYou = new Paragraph();
        thankYou.setSpacingBefore(30);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        thankYou.add(new Chunk("Thank you for choosing our service!\n", messageFont));
        thankYou.add(new Chunk("We wish you a pleasant journey.", messageFont));

        document.add(thankYou);

        // Add some spacing after the message
        document.add(new Paragraph("\n"));
    }

    public void sendInvoice(ReservationDto reservation) {
        try {
            // Generate PDF as a byte array
            ByteArrayOutputStream pdfOutputStream = generateReservationPdf(reservation);

            // Prepare email content
            String recipientEmail = reservation.getPassenger().getEmail();
            String subject = "Your Reservation Invoice";
            String messageText = "Dear " + reservation.getPassenger().getUsername() + ",\n\n" +
                    "Please find attached the invoice for your reservation.\n\n" +
                    "Best regards,\nRideLink";

            // Send email with PDF attachment
            emailService.sendEmailWithAttachment(recipientEmail, subject, messageText, "reservation.pdf", pdfOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private EmailService emailService;


}
