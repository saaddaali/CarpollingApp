import 'dart:convert';
import 'package:intl/intl.dart';
import 'city.dart';

class Trajet {
  final int id;
  final City villeDepart;
  final City villeArrivee;
  final DateTime dateDepart;
  final int nombrePlaceDisponible;
  final double prix;
  final String? description;

  Trajet({
    required this.id,
    required this.villeDepart,
    required this.villeArrivee,
    required this.dateDepart,
    required this.nombrePlaceDisponible,
    required this.prix,
    this.description,
  });

  factory Trajet.fromJson(Map<String, dynamic> json) {
    String decodeString(String? value) {
      if (value == null) return '';
      try {
        return utf8.decode(value.codeUnits);
      } catch (e) {
        return value;
      }
    }

    return Trajet(
      id: json['id'] ?? 0,
      villeDepart: City.fromJson(json['villeDepart'] ?? {}),
      villeArrivee: City.fromJson(json['villeArrivee'] ?? {}),
      dateDepart: DateTime.parse(json['dateDepart'] ?? DateTime.now().toIso8601String()),
      nombrePlaceDisponible: json['nombrePlaceDisponible'] ?? 0,
      prix: (json['prix'] ?? 0).toDouble(),
      description: json['description'] != null ? decodeString(json['description']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    final DateFormat formatter = DateFormat('yyyy-MM-dd');
    return {
      'id': id,
      'villeDepart': villeDepart.toJson(),
      'villeArrivee': villeArrivee.toJson(),
      'dateDepart': formatter.format(dateDepart),
      'nombrePlaceDisponible': nombrePlaceDisponible,
      'prix': prix,
      if (description != null) 'description': description,
    };
  }
} 