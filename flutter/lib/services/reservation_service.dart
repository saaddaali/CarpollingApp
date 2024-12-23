import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:mycarpooling2/services/token_manager.dart';
import '../models/reservation.dart';

class ReservationService {
  static const String baseUrl = 'http://localhost:8036/api/passenger/reservation/';
  final token = TokenManager.getToken();

  Future<List<Reservation>> getReservations() async {
    try {
      final response = await http.get(
        Uri.parse(baseUrl),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        print('Reservations: $jsonList');
        return jsonList.map((json) => Reservation.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load reservations: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error fetching reservations: $e');
    }
  }
} 