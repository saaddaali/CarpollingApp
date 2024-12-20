package ma.zyn.app.service.impl.passenger.paiement;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ma.zyn.app.bean.core.reservation.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;


    public String checkOut(Reservation reservation) {
        Stripe.apiKey = "sk_test_51Onk9WAf41bGTPf1uDA4T1bs0C4wJUZFTwC71e1wSUIvIUwzMS6v3aW7Bz6HaWu7hudXfj51TwPz2d25fiqvE0c800cbWkZahr";
        if (Stripe.apiKey == null || Stripe.apiKey.isEmpty()) {
            throw new IllegalStateException("Stripe API key is not set. Please configure it in environment variables.");
        }

        // Validate reservation
        if (reservation == null || reservation.getTrajet() == null || reservation.getTrajet().getPrix() == null) {
            throw new IllegalArgumentException("Invalid reservation data.");
        }

        Long amount = reservation.getTrajet().getPrix().multiply(BigDecimal.valueOf(100)).longValue(); // Convert to centimes

        // Create product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Reservation id : " + reservation.getId())
                        .build();

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
                .setSuccessUrl("http://localhost:4200/success")
                .setCancelUrl("http://localhost:4200/cancel")
                .build();

        // Create session
        try {
            Session session = Session.create(params);
            return session.getUrl(); // Return session URL for redirection
        } catch (StripeException e) {
            System.out.println("Stripe session creation failed: "+e);
            throw new RuntimeException("Payment session creation failed.");
        }
    }


}
