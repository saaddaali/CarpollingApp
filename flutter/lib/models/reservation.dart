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
      'id': id,
      'dateReservation': formatter.format(dateReservation),
      'montant': montant,
      'datePaiement': datePaiement != null ? formatter.format(datePaiement!) : null,
      'evaluation': evaluation,
      'trajet': {
        'id': trajet.id,
      },
      'passenger': {
        'id': passenger.id,
        'username': passenger.username,
      },
      'driver': {
        'id': driver.id,
        'enabled': driver.enabled,
        'credentialsNonExpired': driver.credentialsNonExpired,
        'accountNonExpired': driver.accountNonExpired,
        'accountNonLocked': driver.accountNonLocked,
        'passwordChanged': driver.passwordChanged,
      },
    };
  }

  factory Reservation.fromJson(Map<String, dynamic> json) {
  final DateFormat customFormatter = DateFormat('MM/dd/yyyy HH:mm');
  
  return Reservation(
    id: json['id'],
    dateReservation: customFormatter.parse(json['dateReservation']),
    montant: json['montant']?.toDouble(),
    datePaiement: json['datePaiement'] != null 
        ? customFormatter.parse(json['datePaiement']) 
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