package ma.zyn.app.service.impl.passenger.paiement;


import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.math.BigDecimal;


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

    public ByteArrayOutputStream generateReservationPdf(ReservationDto reservation) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Initialize PDF document
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // Configure PdfConfig
            PdfConfig pdfConfig = new PdfConfig();
            pdfConfig.setPagination(true);
            pdfConfig.setHeaderImage("path/to/header/image.png"); // Optional: Add your header image
            writer.setPageEvent(pdfConfig);

            // Open document for writing
            document.open();

            // Add reservation details
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Reservation Details", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Passenger: " + reservation.getPassenger().getFirstName()+" "+reservation.getPassenger().getLastName() , contentFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Date of Reservation: " + reservation.getDateReservation(), contentFont));
            document.add(new Paragraph("Amount: " + reservation.getMontant() + " MAD", contentFont));
            document.add(new Paragraph("Payment Date: " + reservation.getDatePaiement(), contentFont));

            document.add(new Paragraph(" ")); // Spacer
            //from
            document.add(new Paragraph("From: " + reservation.getTrajet().getVilleDepart().getLibelle(), contentFont));
            document.add(new Paragraph("Time: " + reservation.getTrajet().getHoraireDepart(), contentFont));

            //to
            document.add(new Paragraph("To: " + reservation.getTrajet().getVilleDestination().getLibelle(), contentFont));
            document.add(new Paragraph("Time: " + reservation.getTrajet().getHoraireArrive(), contentFont));

            document.add(new Paragraph("Driver: " + reservation.getDriver().getUsername(), contentFont));

            // Add export date to footer
            pdfConfig.addExportDate(document);

            // Close document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos;
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
                    "Best regards,\nYour Company";

            // Send email with PDF attachment
            emailService.sendEmailWithAttachment(recipientEmail, subject, messageText, "reservation.pdf", pdfOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private EmailService emailService;



}
