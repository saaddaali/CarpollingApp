import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String baseUrl = 'http://localhost:8036/api/';

  Future<String> login(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse('${baseUrl}login'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        print('Login Response: ${response.body}');
        final Map<String, dynamic> data = json.decode(response.body);
        // Assurez-vous que la clé correspond à celle renvoyée par votre API
        final token = data['accessToken'] ?? data['token'];
        if (token != null) {
          return token;
        }
        throw Exception('Token not found in response');
      } else {
        print('Login Error: ${response.statusCode} - ${response.body}');
        throw Exception('Failed to login: ${response.statusCode}');
      }
    } catch (e) {
      print('Login Error: $e');
      throw Exception('Error during login: $e');
    }
  }
} 