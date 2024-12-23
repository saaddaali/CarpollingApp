import 'package:intl/intl.dart';
import 'driver.dart';
import 'passenger.dart';
import 'carte_bancaire.dart';
import 'conversation.dart';
import 'trajet.dart';

class Reservation {
  final int id;
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
    required this.id,
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

  factory Reservation.fromJson(Map<String, dynamic> json) {
    final DateFormat format = DateFormat("MM/dd/yyyy HH:mm");
    
    return Reservation(
      id: json['id'] ?? 0,
      dateReservation: format.parse(json['dateReservation']),
      montant: json['montant']?.toDouble(),
      datePaiement: json['datePaiement'] != null 
          ? format.parse(json['datePaiement'])
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

  Map<String, dynamic> toJson() {
    final DateFormat format = DateFormat("MM/dd/yyyy HH:mm");
    return {
      'id': id,
      'dateReservation': format.format(dateReservation),
      'montant': montant,
      'datePaiement': datePaiement != null ? format.format(datePaiement!) : null,
      'evaluation': evaluation,
      'trajet': trajet.toJson(),
      'passenger': passenger.toJson(),
      'driver': driver.toJson(),
      'carteBancaire': carteBancaire?.toJson(),
      'conversation': conversation?.toJson(),
    };
  }
} 