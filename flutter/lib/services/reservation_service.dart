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

  Future<Reservation> saveReservation(Reservation reservation) async {
    try {
      final response = await http.post(
        Uri.parse(baseUrl),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
        body: json.encode(reservation.toJson()),
      );

      if (response.statusCode == 201 || response.statusCode == 200) {
        final dynamic jsonResponse = json.decode(response.body);
        return Reservation.fromJson(jsonResponse);
      } else {
        throw Exception('Failed to save reservation: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error saving reservation: $e');
    }
  }
} 