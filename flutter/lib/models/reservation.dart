import 'package:intl/intl.dart';
import 'driver.dart';
import 'passenger.dart';
import 'carte_bancaire.dart';
import 'conversation.dart';
import 'trajet.dart';

class Reservation {
  final int? id;
  final DateTime dateReservation;
  final double? montant;
  final DateTime? datePaiement;
  final double? evaluation;
  final Trajet trajet;
  final Passenger passenger;
  final Driver driver;
  final CarteBancaire? carteBancaire;
  final Conversation? conversation;

  Reservation({
    this.id,
    required this.dateReservation,
    this.montant,
    this.datePaiement,
    this.evaluation,
    required this.trajet,
    required this.passenger,
    required this.driver,
    this.carteBancaire,
    this.conversation,
  });

  Map<String, dynamic> toJson() {
  final DateFormat formatter = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  return {
    'dateReservation': formatter.format(dateReservation),
    'montant': montant,
    'datePaiement': datePaiement != null ? formatter.format(datePaiement!) : null,
    'evaluation': evaluation ?? 0.0, // Default to 0.0 if null
    'trajet': {
      'id': trajet.id,
    },
    'passenger': {
      'id': passenger.id,
      'username': passenger.username ?? "", // Default to empty string if null
    },
    'driver': {
      'id': driver.id,
      'enabled': driver.enabled,
      'credentialsNonExpired': driver.credentialsNonExpired,
      'accountNonExpired': driver.accountNonExpired,
      'accountNonLocked': driver.accountNonLocked,
      'passwordChanged': driver.passwordChanged ?? "", // Default to empty string if null
    },
  };
}


  factory Reservation.fromJson(Map<String, dynamic> json) {
  DateTime parseDate(String? dateStr) {
    if (dateStr == null) return DateTime.now();
    try {
      return DateTime.parse(dateStr);
    } catch (e) {
      // Try parsing with custom format if ISO format fails
      final DateFormat customFormatter = DateFormat('MM/dd/yyyy HH:mm');
      try {
        return customFormatter.parse(dateStr);
      } catch (e) {
        // If both parsing attempts fail, return current date
        print('Failed to parse date: $dateStr');
        return DateTime.now();
      }
    }
  }

  return Reservation(
    id: json['id'],
    dateReservation: parseDate(json['dateReservation']),
    montant: json['montant']?.toDouble(),
    datePaiement: json['datePaiement'] != null 
        ? parseDate(json['datePaiement'])
        : null,
    evaluation: json['evaluation']?.toDouble(),
    trajet: Trajet.fromJson(json['trajet']),
    passenger: Passenger.fromJson(json['passenger']),
    driver: Driver.fromJson(json['driver']),
    carteBancaire: json['carteBancaire'] != null 
        ? CarteBancaire.fromJson(json['carteBancaire']) 
        : null,
    conversation: json['conversation'] != null 
        ? Conversation.fromJson(json['conversation']) 
        : null,
  );
}
} 