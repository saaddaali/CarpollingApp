import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import '../models/passenger.dart';

class PassengerManager {
  static const String _key = 'current_passenger';
  static Passenger? _currentPassenger;

  static Passenger? get currentPassenger => _currentPassenger;

  static Future<void> setPassenger(Passenger passenger) async {
    _currentPassenger = passenger;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_key, json.encode({
      'firstName': passenger.firstName,
      'lastName': passenger.lastName,
      'username': passenger.username,
      'email': passenger.email,
      'roles': passenger.roles,
      'accessToken': passenger.accessToken,
      'tokenType': passenger.tokenType,
    }));
  }

  static Future<Passenger?> getPassenger() async {
    if (_currentPassenger != null) return _currentPassenger;
    
    final prefs = await SharedPreferences.getInstance();
    final String? passengerJson = prefs.getString(_key);
    
    if (passengerJson != null) {
      _currentPassenger = Passenger.fromJson(json.decode(passengerJson));
      return _currentPassenger;
    }
    return null;
  }

  static Future<void> clearPassenger() async {
    _currentPassenger = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_key);
  }
} 