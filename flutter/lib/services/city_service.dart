import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:mycarpooling2/services/token_manager.dart';
import '../models/city.dart';

class CityService {
  static const String baseUrl = 'http://localhost:8036/api/passenger/ville/';

  Map<String, String> get _headers {
    final token = TokenManager.getToken();
    final headers = {
      'Content-Type': 'application/json; charset=UTF-8',
      'Accept': 'application/json; charset=UTF-8',
    };
    if (token != null && token.isNotEmpty) {
      headers['Authorization'] = 'Bearer $token';
    }
    print('Headers: $headers');
    return headers;
  }

  Future<List<City>> findAllOptimized() async {
    try {
      print('Fetching cities from: ${baseUrl}optimized');
      final response = await http.get(
        Uri.parse('${baseUrl}optimized'),
        headers: _headers,
      );

      print('Response status: ${response.statusCode}');
      print('Response body: ${response.body}');

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        
        final cities = jsonList.map((json) => City.fromJson(json)).toList();
        print('Parsed cities: ${cities.length}');
        return cities;
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load cities: ${response.statusCode}');
      }
    } catch (e) {
      print('Error in findAllOptimized: $e');
      throw Exception('Error fetching cities: $e');
    }
  }

  Future<List<City>> searchCities(String query) async {
    try {
      final encodedQuery = Uri.encodeComponent(query);
      final response = await http.post(
        Uri.parse('${baseUrl}find-by-criteria'),
        headers: _headers,
        body: json.encode({
          'libelle': {'like': encodedQuery},
        }),
      );

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        return jsonList.map((json) => City.fromJson(json)).toList();
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to search cities: ${response.statusCode}');
      }
    } catch (e) {
      print('Error searching cities: $e');
      throw Exception('Error searching cities: $e');
    }
  }
}