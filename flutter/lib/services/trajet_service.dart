import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:mycarpooling2/models/city.dart';
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
    City? villeDepart,
    City? villeArrivee,
    DateTime? dateDepart,
    int? nombrePlaceMin,
    double? prixMax,
  }) async {
    try {
      final Map<String, dynamic> criteria = {
        'villeDepart': {
          'id': villeDepart?.id,
          'libelle': villeDepart?.libelle,
          'code': villeDepart?.code,
          'description': villeDepart?.description,
        },
        'villeDestination': {
          'id': villeArrivee?.id,
          'libelle': villeArrivee?.libelle,
          'code': villeArrivee?.code,
          'description': villeArrivee?.description,
        },
        if (dateDepart != null) ...<String, dynamic>{
          'horaireDepartFrom': dateDepart.toIso8601String(),
          'horaireDepartTo': dateDepart
              .add(const Duration(days: 1))
              .subtract(const Duration(seconds: 1))
              .toIso8601String(),
        },
        if (nombrePlaceMin != null)
          'placesDisponiblesMin': nombrePlaceMin.toString(),
        if (prixMax != null)
          'prixMax': prixMax.toString(),
      };

      final response = await http.post(
        Uri.parse('${baseUrl}find-by-criteria'),
        headers: _headers,
        body: json.encode(criteria),
      );

      print('Search trajets - Request body: ${json.encode(criteria)}');
      print('Search trajets - Status: ${response.statusCode}');
      print('Search trajets - Body: ${utf8.decode(response.bodyBytes)}');

      if (response.statusCode == 204) {
        return [];
      }

      if (response.statusCode == 200) {
        final String responseBody = utf8.decode(response.bodyBytes);
        if (responseBody.isEmpty) {
          return [];
        }
        final List<dynamic> data = json.decode(responseBody);
        return data.map((json) => Trajet.fromJson(json)).toList();
      }

      if (response.statusCode == 400) {
        final errorData = json.decode(utf8.decode(response.bodyBytes));
        throw Exception('Bad Request: ${errorData['message'] ?? errorData['error'] ?? 'Unknown error'}');
      }

      if (response.statusCode == 401) {
        TokenManager.setToken('');
        throw Exception('Session expirée, veuillez vous reconnecter');
      }

      throw Exception('Failed to load trajets: ${response.statusCode}');
    } catch (e) {
      print('Error searching trajets: $e');
      if (e is http.ClientException) {
        print('Client error details: ${e.message}');
      }
      if (e is FormatException) {
        print('Format error details: ${e.message}');
      }
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

Future<Trajet> save(Trajet trajet) async {
  try {
    final response = await http.post(
      Uri.parse(baseUrl),
      headers: _headers,
      body: json.encode(trajet.toJson()),
    );

    if (response.statusCode == 201 || response.statusCode == 200) {
      final data = json.decode(utf8.decode(response.bodyBytes));
      return Trajet.fromJson(data);
    } else if (response.statusCode == 401) {
      TokenManager.setToken('');
      throw Exception('Session expirée, veuillez vous reconnecter');
    } else {
      throw Exception('Échec de la sauvegarde du trajet: ${response.statusCode}');
    }
  } catch (e) {
    print('Erreur lors de la sauvegarde du trajet: $e');
    rethrow;
  }
}

} 