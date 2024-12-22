import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:mycarpooling2/services/token_manager.dart';
import '../models/trajet.dart';

class TrajetService {
  static const String baseUrl = 'http://localhost:8036/api/passenger/trajet/';

  Map<String, String> get _headers {
    final token = TokenManager.getToken();
    final headers = {
      'Content-Type': 'application/json; charset=UTF-8',
      'Accept': 'application/json; charset=UTF-8',
    };
    if (token != null && token.isNotEmpty) {
      headers['Authorization'] = 'Bearer $token';
    }
    return headers;
  }

  Future<List<Trajet>> findByCriteria({
    String? villeDepartLibelle,
    String? villeArriveeLibelle,
    DateTime? dateDepart,
    int? nombrePlaceMin,
    double? prixMax,
  }) async {
    try {
      final Map<String, dynamic> criteria = {
        if (villeDepartLibelle != null)
          'villeDepart': {'libelle': {'equals': villeDepartLibelle}},
        if (villeArriveeLibelle != null)
          'villeArrivee': {'libelle': {'equals': villeArriveeLibelle}},
        if (dateDepart != null)
          'dateDepart': {'equals': DateFormat('yyyy-MM-dd').format(dateDepart)},
        if (nombrePlaceMin != null)
          'nombrePlaceDisponible': {'greaterThanOrEqual': nombrePlaceMin},
        if (prixMax != null) 'prix': {'lessThanOrEqual': prixMax},
      };

      final response = await http.post(
        Uri.parse('${baseUrl}find-by-criteria'),
        headers: _headers,
        body: utf8.encode(json.encode(criteria)),
      );

      print('Search trajets - Status: ${response.statusCode}');
      print('Search trajets - Body: ${utf8.decode(response.bodyBytes)}');

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(utf8.decode(response.bodyBytes));
        return data.map((json) => Trajet.fromJson(json)).toList();
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load trajets: ${response.statusCode}');
      }
    } catch (e) {
      print('Error searching trajets: $e');
      rethrow;
    }
  }

  Future<Trajet> findById(int id) async {
    try {
      final response = await http.get(
        Uri.parse('${baseUrl}id/$id'),
        headers: _headers,
      );

      if (response.statusCode == 200) {
        final data = json.decode(utf8.decode(response.bodyBytes));
        return Trajet.fromJson(data);
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load trajet: ${response.statusCode}');
      }
    } catch (e) {
      print('Error loading trajet: $e');
      rethrow;
    }
  }

  Future<List<Trajet>> findAllOptimized() async {
    try {
      final response = await http.get(
        Uri.parse('${baseUrl}optimized'),
        headers: _headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(utf8.decode(response.bodyBytes));
        return data.map((json) => Trajet.fromJson(json)).toList();
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load optimized trajets');
      }
    } catch (e) {
      print('Error loading optimized trajets: $e');
      rethrow;
    }
  }

  Future<List<Trajet>> findByCurrentUser() async {
    try {
      final response = await http.get(
        Uri.parse('${baseUrl}find-by-current-user'),
        headers: _headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(utf8.decode(response.bodyBytes));
        return data.map((json) => Trajet.fromJson(json)).toList();
      } else if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      } else {
        throw Exception('Failed to load user trajets');
      }
    } catch (e) {
      print('Error loading user trajets: $e');
      rethrow;
    }
  }
} 