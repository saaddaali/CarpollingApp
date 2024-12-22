import 'package:intl/intl.dart';
import 'city.dart';
import 'driver.dart';

class Trajet {
  final int id;
  final DateTime horaireDepart;
  final DateTime horaireArrive;
  final int placesDisponibles;
  final int placesMax;
  final DateTime dateCreation;
  final City villeDepart;
  final City villeDestination;
  final Driver driver;
  final double prix;

  Trajet({
    required this.id,
    required this.horaireDepart,
    required this.horaireArrive,
    required this.placesDisponibles,
    required this.placesMax,
    required this.dateCreation,
    required this.villeDepart,
    required this.villeDestination,
    required this.driver,
    required this.prix,
  });

  factory Trajet.fromJson(Map<String, dynamic> json) {
    final DateFormat format = DateFormat("MM/dd/yyyy HH:mm");

    return Trajet(
      id: json['id'] ?? 0,
      horaireDepart: format.parse(json['horaireDepart']),
      horaireArrive: format.parse(json['horaireArrive']),
      placesDisponibles: int.parse(json['placesDisponibles'].toString()),
      placesMax: int.parse(json['placesMax'].toString()),
      dateCreation: format.parse(json['dateCreation']),
      villeDepart: City.fromJson(json['villeDepart']),
      villeDestination: City.fromJson(json['villeDestination']),
      driver: Driver.fromJson(json['driver']),
      prix: (json['prix'] ?? 0.0).toDouble(),
    );
  }

  Map<String, dynamic> toJson() {
    final DateFormat format = DateFormat("MM/dd/yyyy HH:mm");
    return {
      'id': id,
      'horaireDepart': format.format(horaireDepart),
      'horaireArrive': format.format(horaireArrive),
      'placesDisponibles': placesDisponibles,
      'placesMax': placesMax,
      'dateCreation': format.format(dateCreation),
      'villeDepart': villeDepart.toJson(),
      'villeDestination': villeDestination.toJson(),
      'driver': driver.toJson(),
      'prix': prix,
    };
  }
}