
import 'package:flutter/material.dart';
import 'package:mycarpooling2/screens/driver-screens/driver_home_screen.dart';

class TripDetailsScreenn extends StatefulWidget {
  final Map<String, dynamic> tripData;

  const TripDetailsScreenn({super.key, required this.tripData});

  @override
  State<TripDetailsScreenn> createState() => _TripDetailsScreennState();
}

class _TripDetailsScreennState extends State<TripDetailsScreenn> {
  late Map<String, dynamic> currentTripData;
  static const Color primaryBlue = Color(0xFF4052EE);
  static const Color lightBlue = Color(0xFFE6E8FF);
  static const Color editGreen = Color.fromRGBO(162, 18, 18, 1);
  static const Color backgroundGrey = Color(0xFFF8F9FE);
  static const Color textGrey = Color(0xFF6B7280);

  final List<String> cities = [
    'Casablanca',
    'Rabat',
    'Marrakech',
    'Fès',
    'Tanger',
    'Meknès',
    'Oujda',
    'Agadir',
    'Tétouan',
    'Salé',
    'Nador',
    'Kénitra',
  ];

  @override
  void initState() {
    super.initState();
    currentTripData = Map.from(widget.tripData);
  }

  void _showPublishConfirmation() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return Dialog(
          backgroundColor: Colors.transparent,
          elevation: 0,
          child: Container(
            padding: const EdgeInsets.all(20),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(15),
            ),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Container(
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: const Color(0xFF34C759).withOpacity(0.1),
                    shape: BoxShape.circle,
                  ),
                  child: const Icon(
                    Icons.check_circle,
                    color: Color(0xFF34C759),
                    size: 48,
                  ),
                ),
                const SizedBox(height: 16),
                const Text(
                  'Trajet publié avec succès!',
                  style: TextStyle(
                    color: Color(0xFF34C759),
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );

    // Fermer le dialogue après 2 secondes et naviguer
    Future.delayed(const Duration(seconds: 2), () {
      Navigator.of(context).pop(); // Ferme le dialogue
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(
          builder: (context) => const DriverScreen(),
        ),
      );
    });
  }

  Future<void> _editCity(bool isDeparture) async {
    await showModalBottomSheet(
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
                      cities[index],
                      style: const TextStyle(fontSize: 18),
                    ),
                    onTap: () {
                      setState(() {
                        if (isDeparture) {
                          currentTripData['departCity'] = cities[index];
                        } else {
                          currentTripData['arrivalCity'] = cities[index];
                        }
                      });
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
  }

  Future<void> _editDate() async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.parse(currentTripData['date']),
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
        currentTripData['date'] = picked.toIso8601String();
      });
    }
  }

  Future<void> _editTime(bool isDeparture) async {
    TimeOfDay initialTime = TimeOfDay.now();
    String currentTime = isDeparture
        ? currentTripData['departureTime']
        : currentTripData['arrivalTime'];

    if (currentTime.contains('AM') || currentTime.contains('PM')) {
      List<String> parts = currentTime.split(' ');
      List<String> timeParts = parts[0].split(':');
      int hour = int.parse(timeParts[0]);
      int minute = int.parse(timeParts[1]);
      if (parts[1] == 'PM' && hour < 12) hour += 12;
      if (parts[1] == 'AM' && hour == 12) hour = 0;
      initialTime = TimeOfDay(hour: hour, minute: minute);
    }

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
                    Text(
                      'Modifier ${isDeparture ? "l'heure de départ" : "l'heure d'arrivée"}',
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        SizedBox(
                          width: 70,
                          child: TextFormField(
                            textAlign: TextAlign.center,
                            keyboardType: TextInputType.number,
                            initialValue: displayHour.toString(),
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 12,
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
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
                        SizedBox(
                          width: 70,
                          child: TextFormField(
                            textAlign: TextAlign.center,
                            keyboardType: TextInputType.number,
                            initialValue:
                                initialTime.minute.toString().padLeft(2, '0'),
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 12,
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
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
                          child: const Text('Annuler'),
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
                            style: TextStyle(color: primaryBlue),
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
      final hour = result['hour'];
      final minute = result['minute'];
      final period = hour < 12 ? 'AM' : 'PM';
      final displayHour = (hour % 12 == 0 ? 12 : hour % 12).toString();
      final displayMinute = minute.toString().padLeft(2, '0');
      final timeString = '$displayHour:$displayMinute $period';

      setState(() {
        if (isDeparture) {
          currentTripData['departureTime'] = timeString;
        } else {
          currentTripData['arrivalTime'] = timeString;
        }
      });
    }
  }

  Future<void> _editSeats() async {
    int seats = currentTripData['seats'];

    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return StatefulBuilder(
          builder: (context, setStateDialog) {
            return Dialog(
              backgroundColor: Colors.white,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20),
              ),
              child: Padding(
                padding: const EdgeInsets.all(24),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Text(
                      'Nombre de places',
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                        color: Colors.black,
                      ),
                    ),
                    const SizedBox(height: 24),
                    Container(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 16, vertical: 12),
                      decoration: BoxDecoration(
                        color: backgroundGrey,
                        borderRadius: BorderRadius.circular(15),
                      ),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          IconButton(
                            icon: Icon(
                              Icons.remove_circle_outline,
                              color: seats > 1 ? primaryBlue : Colors.grey,
                              size: 32,
                            ),
                            onPressed: seats > 1
                                ? () => setStateDialog(() => seats--)
                                : null,
                          ),
                          Container(
                            width: 50,
                            padding: const EdgeInsets.symmetric(
                                horizontal: 16, vertical: 8),
                            decoration: BoxDecoration(
                              color: lightBlue,
                              borderRadius: BorderRadius.circular(10),
                            ),
                            child: Text(
                              seats.toString(),
                              textAlign: TextAlign.center,
                              style: const TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                                color: primaryBlue,
                              ),
                            ),
                          ),
                          IconButton(
                            icon: Icon(
                              Icons.add_circle_outline,
                              color: seats < 4 ? primaryBlue : Colors.grey,
                              size: 32,
                            ),
                            onPressed: seats < 4
                                ? () => setStateDialog(() => seats++)
                                : null,
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 24),
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
                        const SizedBox(width: 12),
                        TextButton(
                          onPressed: () {
                            setState(() {
                              currentTripData['seats'] = seats;
                            });
                            Navigator.pop(context);
                          },
                          child: const Text(
                            'Confirmer',
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
  }

  Future<void> _editPrice() async {
    TextEditingController priceController = TextEditingController(
      text: currentTripData['price'].toString(),
    );

    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return Dialog(
          backgroundColor: Colors.white,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
          ),
          child: Padding(
            padding: const EdgeInsets.all(24),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                const Text(
                  'Prix par personne',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
                const SizedBox(height: 24),
                Container(
                  padding:
                      const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                  decoration: BoxDecoration(
                    color: backgroundGrey,
                    borderRadius: BorderRadius.circular(15),
                  ),
                  child: TextField(
                    controller: priceController,
                    keyboardType: TextInputType.number,
                    style: const TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.black,
                    ),
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      suffixText: 'DH',
                      suffixStyle: TextStyle(
                        fontSize: 18,
                        color: Colors.grey[600],
                        fontWeight: FontWeight.bold,
                      ),
                      prefixIcon: const Icon(
                        Icons.attach_money,
                        color: primaryBlue,
                        size: 24,
                      ),
                    ),
                  ),
                ),
                const SizedBox(height: 24),
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
                    const SizedBox(width: 12),
                    TextButton(
                      onPressed: () {
                        if (priceController.text.isNotEmpty) {
                          setState(() {
                            currentTripData['price'] =
                                int.parse(priceController.text);
                          });
                          Navigator.pop(context);
                        }
                      },
                      child: const Text(
                        'Confirmer',
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
  }

  String _formatDetailValue(String key, dynamic value) {
    switch (key) {
      case 'seats':
        return value.toString(); // Retourne uniquement le nombre
      case 'price':
        return '$value DH';
      case 'date':
        return DateTime.parse(value).toString().split(' ')[0];
      case 'departureTime':
      case 'arrivalTime':
        return value;
      default:
        return value.toString();
    }
  }

  Widget _buildDetailItem(String label, IconData icon, String key) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 18),
      decoration: BoxDecoration(
        color: backgroundGrey,
        borderRadius: BorderRadius.circular(15),
      ),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: lightBlue,
              borderRadius: BorderRadius.circular(10),
            ),
            child: Icon(icon, color: primaryBlue, size: 22),
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: const TextStyle(
                    fontSize: 14,
                    color: textGrey,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  _formatDetailValue(key, currentTripData[key]),
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
              ],
            ),
          ),
          const Icon(
            Icons.edit,
            size: 20,
            color: editGreen,
          ),
        ],
      ),
    );
  }

  Widget _buildLocationCard() {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: primaryBlue,
        borderRadius: BorderRadius.circular(15),
      ),
      child: Column(
        children: [
          Row(
            children: [
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.2),
                  borderRadius: BorderRadius.circular(10),
                ),
                child: const Icon(
                  Icons.location_on_outlined,
                  color: Colors.white,
                  size: 24,
                ),
              ),
              const SizedBox(width: 12),
              Text(
                currentTripData['departCity'],
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const Spacer(),
              IconButton(
                icon: const Icon(Icons.edit, size: 20),
                color: const Color.fromARGB(255, 91, 1, 1),
                onPressed: () => _editCity(true),
              ),
            ],
          ),
          Container(
            margin: const EdgeInsets.symmetric(vertical: 12),
            height: 40,
            child: Row(
              children: [
                Container(
                  width: 2,
                  height: 40,
                  margin: const EdgeInsets.symmetric(horizontal: 19),
                  child: CustomPaint(
                    painter: DashedLinePainter(color: Colors.white),
                  ),
                ),
              ],
            ),
          ),
          Row(
            children: [
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.2),
                  borderRadius: BorderRadius.circular(10),
                ),
                child: const Icon(
                  Icons.location_on,
                  color: Colors.white,
                  size: 24,
                ),
              ),
              const SizedBox(width: 12),
              Text(
                currentTripData['arrivalCity'],
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const Spacer(),
              IconButton(
                icon: const Icon(Icons.edit, size: 20),
                color: const Color.fromARGB(255, 91, 1, 1),
                onPressed: () => _editCity(false),
              ),
            ],
          ),
        ],
      ),
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
          'Détails du trajet',
          style: TextStyle(
            color: Colors.black,
            fontSize: 22,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildLocationCard(),
            const SizedBox(height: 32),
            const Text(
              'Informations du trajet',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
            const SizedBox(height: 24),
            GestureDetector(
              onTap: _editDate,
              child: _buildDetailItem(
                  'Date du trajet', Icons.calendar_today, 'date'),
            ),
            GestureDetector(
              onTap: () => _editTime(true),
              child: _buildDetailItem(
                  'Heure de départ', Icons.access_time, 'departureTime'),
            ),
            GestureDetector(
              onTap: () => _editTime(false),
              child: _buildDetailItem(
                  'Heure d\'arrivée', Icons.access_time, 'arrivalTime'),
            ),
            GestureDetector(
              onTap: _editSeats,
              child: _buildDetailItem(
                  'Nombre de places disponibles', Icons.event_seat, 'seats'),
            ),
            GestureDetector(
              onTap: _editPrice,
              child: _buildDetailItem(
                  'Prix par personne', Icons.attach_money, 'price'),
            ),
            const SizedBox(height: 32),
            SizedBox(
              width: double.infinity,
              height: 54,
              child: ElevatedButton(
                onPressed: () async {
                  print('Nouvelles données du trajet: $currentTripData');
                  _showPublishConfirmation();
                  await Future.delayed(const Duration(seconds: 2));
                  if (mounted) {
                    Navigator.of(context)
                        .pushReplacementNamed('/driver_screen');
                  }
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: primaryBlue,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                  elevation: 0,
                ),
                child: const Text(
                  'Publier',
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

class DashedLinePainter extends CustomPainter {
  final Color color;

  DashedLinePainter({required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    final Paint paint = Paint()
      ..color = color
      ..strokeWidth = 2
      ..strokeCap = StrokeCap.round;

    const double dashWidth = 4;
    const double dashSpace = 4;
    double startY = 0;
    final double endY = size.height;

    while (startY < endY) {
      canvas.drawLine(
        Offset(0, startY),
        Offset(0, startY + dashWidth),
        paint,
      );
      startY += dashWidth + dashSpace;
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;
}
