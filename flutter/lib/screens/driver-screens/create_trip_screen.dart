// create_trip_screen.dart

import 'package:flutter/material.dart';
import 'package:mycarpooling2/screens/driver-screens/details_screen.dart';
import 'package:mycarpooling2/services/city_service.dart';
import 'package:mycarpooling2/models/city.dart';

class CreateTripScreen extends StatefulWidget {
  const CreateTripScreen({super.key});

  @override
  State<CreateTripScreen> createState() => _CreateTripScreenState();
}

class _CreateTripScreenState extends State<CreateTripScreen> {
  static const Color primaryBlue = Color(0xFF4052EE);
  final CityService _cityService = CityService();
  
  List<City> cities = [];
  City? selectedDepartCity;
  City? selectedArrivalCity;
  int selectedSeats = 1;
  DateTime? selectedDate;
  TimeOfDay? departureTime;
  TimeOfDay? arrivalTime;
  final TextEditingController priceController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _loadCities();
  }

  Future<void> _loadCities() async {
    try {
      final fetchedCities = await _cityService.findAllOptimized();
      setState(() {
        cities = fetchedCities;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Erreur lors du chargement des villes: $e')),
      );
    }
  }

  @override
  void dispose() {
    priceController.dispose();
    super.dispose();
  }

  String _formatDate(DateTime date) {
    return '${date.day}/${date.month}/${date.year}';
  }

  String _formatTime(TimeOfDay time) {
    final hour = time.hourOfPeriod == 0 ? 12 : time.hourOfPeriod;
    final minute = time.minute.toString().padLeft(2, '0');
    final period = time.period == DayPeriod.am ? 'AM' : 'PM';
    return '$hour:$minute $period';
  }

  Future<void> _selectDate() async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate ?? DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 365)),
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: const ColorScheme.light(
              primary: primaryBlue,
              onPrimary: Colors.white,
              onSurface: Colors.black,
            ),
          ),
          child: child!,
        );
      },
    );
    if (picked != null) {
      setState(() {
        selectedDate = picked;
      });
    }
  }

  Future<void> _selectTime(bool isDeparture) async {
    TimeOfDay initialTime = isDeparture
        ? (departureTime ?? TimeOfDay.now())
        : (arrivalTime ?? TimeOfDay.now());

    bool isAm = initialTime.hour < 12;
    int displayHour =
        initialTime.hourOfPeriod == 0 ? 12 : initialTime.hourOfPeriod;

    final result = await showDialog<Map<String, dynamic>>(
      context: context,
      builder: (BuildContext context) {
        return StatefulBuilder(
          builder: (context, setStateDialog) {
            return Dialog(
              backgroundColor: Colors.white,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(15),
              ),
              child: Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Text(
                      'Saisir une heure',
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        // Sélecteur d'heures
                        SizedBox(
                          width: 70,
                          child: TextFormField(
                            textAlign: TextAlign.center,
                            keyboardType: TextInputType.number,
                            initialValue: displayHour.toString(),
                            style: const TextStyle(fontSize: 20),
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 12,
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                                borderSide:
                                    const BorderSide(color: primaryBlue),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                                borderSide: const BorderSide(
                                    color: primaryBlue, width: 2),
                              ),
                            ),
                            onChanged: (value) {
                              if (value.isNotEmpty) {
                                int? hour = int.tryParse(value);
                                if (hour != null && hour > 0 && hour <= 12) {
                                  displayHour = hour;
                                }
                              }
                            },
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.symmetric(horizontal: 10),
                          child: Text(
                            ':',
                            style: TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        // Sélecteur de minutes
                        SizedBox(
                          width: 70,
                          child: TextFormField(
                            textAlign: TextAlign.center,
                            keyboardType: TextInputType.number,
                            initialValue:
                                initialTime.minute.toString().padLeft(2, '0'),
                            style: const TextStyle(fontSize: 20),
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 12,
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                                borderSide:
                                    const BorderSide(color: primaryBlue),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                                borderSide: const BorderSide(
                                    color: primaryBlue, width: 2),
                              ),
                            ),
                            onChanged: (value) {
                              if (value.isNotEmpty) {
                                int? minute = int.tryParse(value);
                                if (minute != null &&
                                    minute >= 0 &&
                                    minute < 60) {
                                  initialTime = TimeOfDay(
                                    hour: initialTime.hour,
                                    minute: minute,
                                  );
                                }
                              }
                            },
                          ),
                        ),
                        const SizedBox(width: 20),
                        // Sélecteur AM/PM
                        Column(
                          children: [
                            TextButton(
                              onPressed: () {
                                setStateDialog(() => isAm = true);
                              },
                              style: TextButton.styleFrom(
                                backgroundColor:
                                    isAm ? primaryBlue : Colors.grey[200],
                                minimumSize: const Size(60, 36),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(8),
                                ),
                              ),
                              child: Text(
                                'AM',
                                style: TextStyle(
                                  color: isAm ? Colors.white : Colors.grey[800],
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                            const SizedBox(height: 8),
                            TextButton(
                              onPressed: () {
                                setStateDialog(() => isAm = false);
                              },
                              style: TextButton.styleFrom(
                                backgroundColor:
                                    !isAm ? primaryBlue : Colors.grey[200],
                                minimumSize: const Size(60, 36),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(8),
                                ),
                              ),
                              child: Text(
                                'PM',
                                style: TextStyle(
                                  color:
                                      !isAm ? Colors.white : Colors.grey[800],
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                    const SizedBox(height: 20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        TextButton(
                          onPressed: () => Navigator.pop(context),
                          child: Text(
                            'Annuler',
                            style: TextStyle(
                              color: Colors.grey[600],
                              fontSize: 16,
                            ),
                          ),
                        ),
                        TextButton(
                          onPressed: () {
                            final selectedHour =
                                displayHour % 12 + (isAm ? 0 : 12);
                            Navigator.pop(context, {
                              'hour': selectedHour,
                              'minute': initialTime.minute,
                            });
                          },
                          child: const Text(
                            'OK',
                            style: TextStyle(
                              color: primaryBlue,
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            );
          },
        );
      },
    );

    if (result != null) {
      final newTime = TimeOfDay(hour: result['hour'], minute: result['minute']);
      setState(() {
        if (isDeparture) {
          departureTime = newTime;
        } else {
          arrivalTime = newTime;
        }
      });
    }
  }

  Widget _buildCityField(
      String hint, City? value, Function(City?) onChanged, IconData icon) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      child: InkWell(
        onTap: () {
          showModalBottomSheet(
            context: context,
            backgroundColor: Colors.white,
            shape: const RoundedRectangleBorder(
              borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
            ),
            builder: (BuildContext context) {
              return Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Container(
                    width: 40,
                    height: 4,
                    margin: const EdgeInsets.symmetric(vertical: 8),
                    decoration: BoxDecoration(
                      color: Colors.grey[300],
                      borderRadius: BorderRadius.circular(2),
                    ),
                  ),
                  Expanded(
                    child: ListView.builder(
                      itemCount: cities.length,
                      itemBuilder: (context, index) {
                        return ListTile(
                          title: Text(
                            cities[index].libelle,
                            style: const TextStyle(fontSize: 18),
                          ),
                          onTap: () {
                            onChanged(cities[index]);
                            Navigator.pop(context);
                          },
                        );
                      },
                    ),
                  ),
                ],
              );
            },
          );
        },
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
          decoration: BoxDecoration(
            color: Colors.grey[100],
            borderRadius: BorderRadius.circular(15),
          ),
          child: Row(
            children: [
              Icon(icon, color: primaryBlue, size: 22),
              const SizedBox(width: 12),
              Text(
                value?.libelle ?? hint,
                style: TextStyle(
                  fontSize: 20,
                  color: value == null ? Colors.grey[600] : Colors.black,
                ),
              ),
              const Spacer(),
              Icon(Icons.keyboard_arrow_down, color: Colors.grey[600]),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildSeatsSelector() {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
      decoration: BoxDecoration(
        color: Colors.grey[100],
        borderRadius: BorderRadius.circular(15),
      ),
      child: Row(
        children: [
          const Icon(Icons.event_seat, color: primaryBlue, size: 22),
          const SizedBox(width: 12),
          const Text(
            'Nombre de places:',
            style: TextStyle(fontSize: 20),
          ),
          const Spacer(),
          IconButton(
            icon: Icon(
              Icons.remove_circle_outline,
              color: selectedSeats > 1 ? primaryBlue : Colors.grey,
              size: 30,
            ),
            onPressed: selectedSeats > 1
                ? () => setState(() => selectedSeats--)
                : null,
          ),
          Text(
            selectedSeats.toString(),
            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          IconButton(
            icon: Icon(
              Icons.add_circle_outline,
              color: selectedSeats < 4 ? primaryBlue : Colors.grey,
              size: 30,
            ),
            onPressed: selectedSeats < 4
                ? () => setState(() => selectedSeats++)
                : null,
          ),
        ],
      ),
    );
  }

  Widget _buildDateSelector() {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      child: InkWell(
        onTap: _selectDate,
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
          decoration: BoxDecoration(
            color: Colors.grey[100],
            borderRadius: BorderRadius.circular(15),
          ),
          child: Row(
            children: [
              const Icon(Icons.calendar_today, color: primaryBlue, size: 22),
              const SizedBox(width: 12),
              Text(
                selectedDate != null
                    ? _formatDate(selectedDate!)
                    : 'Sélectionner la date',
                style: TextStyle(
                  fontSize: 20,
                  color: selectedDate != null ? Colors.black : Colors.grey[600],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTimeSelector(String hint, TimeOfDay? time, bool isDeparture) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      child: InkWell(
        onTap: () => _selectTime(isDeparture),
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
          decoration: BoxDecoration(
            color: Colors.grey[100],
            borderRadius: BorderRadius.circular(15),
          ),
          child: Row(
            children: [
              const Icon(Icons.access_time, color: primaryBlue, size: 22),
              const SizedBox(width: 12),
              Text(
                time != null ? _formatTime(time) : hint,
                style: TextStyle(
                  fontSize: 20,
                  color: time != null ? Colors.black : Colors.grey[600],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildPriceField() {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      child: TextField(
        controller: priceController,
        keyboardType: TextInputType.number,
        style: const TextStyle(fontSize: 20),
        decoration: InputDecoration(
          hintText: 'Prix par personne',
          hintStyle: TextStyle(
            fontSize: 20,
            color: Colors.grey[600],
          ),
          prefixIcon:
              const Icon(Icons.attach_money, color: primaryBlue, size: 22),
          suffixText: 'DH',
          suffixStyle: const TextStyle(fontSize: 20),
          filled: true,
          fillColor: Colors.grey[100],
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(15),
            borderSide: BorderSide.none,
          ),
          contentPadding:
              const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
        ),
      ),
    );
  }

  void _handleSubmit(BuildContext context) {
    // Vérification des champs
    if (selectedDepartCity == null ||
        selectedArrivalCity == null ||
        selectedDate == null ||
        departureTime == null ||
        arrivalTime == null ||
        priceController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Veuillez remplir tous les champs'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    // Création des données du trajet
    final Map<String, dynamic> tripData = {
      'departCity': selectedDepartCity?.libelle,
      'arrivalCity': selectedArrivalCity?.libelle,
      'seats': selectedSeats,
      'date': selectedDate!.toIso8601String(),
      'departureTime': _formatTime(departureTime!),
      'arrivalTime': _formatTime(arrivalTime!),
      'price': int.parse(priceController.text),
    };

    print('Données du trajet à sauvegarder: $tripData');

    // Navigation vers l'écran des détails du trajet après la soumission
    Navigator.push(
      context,
      MaterialPageRoute(
          builder: (context) => TripDetailsScreenn(tripData: tripData)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: const Text(
          'Créer un trajet',
          style: TextStyle(
            color: Colors.black,
            fontSize: 22,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Détails du trajet',
              style: TextStyle(
                fontSize: 28,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
            const SizedBox(height: 32),
            _buildCityField(
              'Ville de départ',
              selectedDepartCity,
              (value) => setState(() => selectedDepartCity = value),
              Icons.location_on_outlined,
            ),
            _buildCityField(
              'Ville d\'arrivée',
              selectedArrivalCity,
              (value) => setState(() => selectedArrivalCity = value),
              Icons.location_on,
            ),
            _buildSeatsSelector(),
            _buildDateSelector(),
            _buildTimeSelector(
              'Sélectionner Heure de départ',
              departureTime,
              true,
            ),
            _buildTimeSelector(
              'Sélectionner Heure d\'arrivée',
              arrivalTime,
              false,
            ),
            _buildPriceField(),
            const SizedBox(height: 32),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: () => _handleSubmit(context),
                style: ElevatedButton.styleFrom(
                  backgroundColor: primaryBlue,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                ),
                child: const Text(
                  'Confirmer le trajet',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
