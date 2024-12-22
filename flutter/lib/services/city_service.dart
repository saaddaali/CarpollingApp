import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:mycarpooling2/services/token_manager.dart';
import '../models/city.dart';

class CityService {
  static const String baseUrl = 'http://localhost:8036/api/passenger/ville/';

  Map<String, String> get _headers {
    final token = TokenManager.getToken();
    final headers = {
      'Content-Type': 'application/json',
    };
    if (token != null && token.isNotEmpty) {
      headers['Authorization'] = 'Bearer $token';
    }
    print('Headers: $headers'); // Debug log
    return headers;
  }

  Future<List<City>> searchCities(String query) async {
    try {
      final response = await http.post(
        Uri.parse('${baseUrl}find-by-criteria'),
        headers: _headers,
        body: json.encode({
          'libelle': {'like': query},
        }),
      );

      print('Search cities - Status: ${response.statusCode}');
      print('Search cities - Body: ${response.body}');

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        final cities = data.map((json) => City.fromJson(json)).toList();
        print('Parsed cities: ${cities.length}');
        return cities;
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load cities: ${response.statusCode} - ${response.body}');
      }
    } catch (e) {
      print('Error searching cities: $e');
      rethrow;
    }
  }

  Future<List<City>> findAllOptimized() async {
    try {
      final response = await http.get(
        Uri.parse('${baseUrl}optimized'),
        headers: _headers,
      );

      print('Find all optimized - Status: ${response.statusCode}');
      print('Find all optimized - Body: ${response.body}');

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        final cities = data.map((json) => City.fromJson(json)).toList();
        print('Parsed cities: ${cities.length}');
        return cities;
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load optimized cities: ${response.statusCode} - ${response.body}');
      }
    } catch (e) {
      print('Error loading optimized cities: $e');
      rethrow;
    }
  }
}