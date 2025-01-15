import 'package:mycarpooling2/models/driver.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import '../models/passenger.dart';

class PassengerManager {
  static const String _key = 'current_passenger';
  static Passenger? _currentPassenger;
  static Driver? _currentDriver;

  static Passenger? get currentPassenger => _currentPassenger;
  static Driver? get currentDriver => _currentDriver;

  static Future<void> setDriver(Driver driver) async {
    _currentDriver = driver;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_key, json.encode(driver.toJson()));
  }

  static Future<void> setPassenger(Passenger passenger) async {
    _currentPassenger = passenger;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_key, json.encode(passenger.toJson()));
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

  static Future<Driver?> getDriver() async {
    if (_currentDriver != null) return _currentDriver;
    
    final prefs = await SharedPreferences.getInstance();
    final String? driverJson = prefs.getString(_key);
    
    if (driverJson != null) {
      _currentDriver = Driver.fromJson(json.decode(driverJson));
      return _currentDriver;
    }
    return null;
  }

  static Future<void> clearPassenger() async {
    _currentPassenger = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_key);
  }

  static Future<void> clearDriver() async {
    _currentDriver = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_key);
  }
} 