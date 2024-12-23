import 'package:flutter_stripe/flutter_stripe.dart';
import 'package:http/http.dart' as http;
import 'package:mycarpooling2/models/consts.dart';
import 'dart:convert';

class StripeService {
  StripeService._();
  static final StripeService instance = StripeService._();

  bool _isInitialized = false;

  final headers = {
    'Accept': 'application/json; charset=UTF-8',
    'Authorization': "Bearer $stripeSecretKey",
  };

  Future<void> ensureInitialized() async {
    if (!_isInitialized) {
      await Stripe.instance.applySettings();
      _isInitialized = true;
    }
  }

  Future<void> createPaymentMethod({required int amount}) async {
    try {
      await ensureInitialized();
      
      String? paymentIntentClientSecret = await createPaymentIntent(amount: amount);
      if (paymentIntentClientSecret == null) {
        throw Exception('Failed to create payment intent');
      }

      await Stripe.instance.initPaymentSheet(
        paymentSheetParameters: SetupPaymentSheetParameters(
          paymentIntentClientSecret: paymentIntentClientSecret,
          merchantDisplayName: 'RideLink'
        ),
      );

      await confirmPayment();
    } catch (e) {
      throw Exception('Failed to create payment method: $e');
    }
  }

  Future<String?> createPaymentIntent({
    required int amount,
  }) async {
    try {
      Map<String, String> data = {
        'amount': amount.toString(),
        'currency': 'usd',
      };
      
      final response = await http.post(
        Uri.parse('https://api.stripe.com/v1/payment_intents'),
        headers: headers,
        body: data
      );

      print('Response status: ${response.statusCode}');
      
      if (response.statusCode != 200) {
        throw Exception('Failed to create payment intent. Status: ${response.statusCode}');
      }
      
      final responseData = jsonDecode(response.body);
      if (responseData['client_secret'] == null) {
        throw Exception('No client secret in response');
      }
      
      return responseData['client_secret'];
      
    } catch (e) {
      throw Exception('Failed to create payment intent: $e');
    }
  }

  Future<void> confirmPayment() async {
    try {
      await Stripe.instance.presentPaymentSheet();
    } catch (e) {
      throw Exception('Failed to confirm payment $e');
    }
  }
}
