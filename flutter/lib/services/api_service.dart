import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  // Base URL of your Spring Boot backend
  static const String baseUrl = 'http://10.0.2.2:8036'; // For Android emulator
  // Use 'http://localhost:8036' for iOS simulator
  // Use your actual IP address when testing on physical devices

  // Authentication endpoint
  static Future<Map<String, dynamic>> login(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/login'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        throw Exception('Failed to login: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to connect to server: $e');
    }
  }

  // Example of a GET request with authentication
  static Future<List<dynamic>> getDrivers(String token) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/api/admin/driver/'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        return jsonDecode(response.body);
      } else {
        throw Exception('Failed to fetch drivers: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Failed to connect to server: $e');
    }
  }
} 