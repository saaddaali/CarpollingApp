import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mycarpooling2/models/city.dart';
import 'driver_list_screen.dart';

class SeatsSelectionScreen extends StatefulWidget {
  final City? departureCity;
  final City? arrivalCity;
  final DateTime selectedDate;

  const SeatsSelectionScreen({
    super.key,
    required this.departureCity,
    required this.arrivalCity,
    required this.selectedDate,
  });

  @override
  State<SeatsSelectionScreen> createState() => _SeatsSelectionScreenState();
}

class _SeatsSelectionScreenState extends State<SeatsSelectionScreen> {
  int _seats = 1;
  static const int _maxSeats = 4;
  final Color _appBlueColor = const Color(0xFF4052EE);

  @override
  Widget build(BuildContext context) {
    final DateFormat dateFormat = DateFormat('EEEE, d MMM.', 'fr_FR');
    final String formattedDate =
        dateFormat.format(widget.selectedDate).toLowerCase();

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header
            _buildHeader(formattedDate),

            // Title
            _buildTitle(),

            // Seats Counter
            _buildSeatsCounter(),

            // Search Button
            _buildSearchButton(),
          ],
        ),
      ),
    );
  }

  Widget _buildHeader(String formattedDate) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Expanded(
            child: Row(
              children: [
                GestureDetector(
                  onTap: () => Navigator.pop(context),
                  child: const Icon(Icons.arrow_back),
                ),
                const SizedBox(width: 15),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        '${widget.departureCity?.libelle} > ${widget.arrivalCity?.libelle}',
                        style: const TextStyle(
                          fontSize: 16,
                          color: Colors.grey,
                          fontWeight: FontWeight.w500,
                        ),
                        overflow: TextOverflow.ellipsis,
                      ),
                      Text(
                        formattedDate,
                        style: const TextStyle(
                          fontSize: 14,
                          color: Colors.grey,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text(
              'Sauter',
              style: TextStyle(
                color: Colors.black,
                fontSize: 16,
                fontWeight: FontWeight.w500,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTitle() {
    return const Padding(
      padding: EdgeInsets.symmetric(horizontal: 20),
      child: Text(
        'Nombre de places',
        style: TextStyle(
          fontSize: 28,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

  Widget _buildSeatsCounter() {
    return Expanded(
      child: Center(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // Minus Button
            _buildCounterButton(
              onTap: _seats > 1 ? () => setState(() => _seats--) : null,
              icon: Icons.remove,
              isEnabled: _seats > 1,
            ),

            // Counter Value
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 40),
              child: Text(
                '$_seats',
                style: const TextStyle(
                  fontSize: 48,
                  fontWeight: FontWeight.w500,
                ),
              ),
            ),

            // Plus Button
            _buildCounterButton(
              onTap: _seats < _maxSeats ? () => setState(() => _seats++) : null,
              icon: Icons.add,
              isEnabled: _seats < _maxSeats,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSearchButton() {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: SizedBox(
        width: double.infinity,
        height: 56,
        child: ElevatedButton(
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => DriverListScreen(
                  departureCity: widget.departureCity,
                  arrivalCity: widget.arrivalCity,
                  selectedDate: widget.selectedDate,
                  seats: _seats,
                ),
              ),
            );
          },
          style: ElevatedButton.styleFrom(
            backgroundColor: _appBlueColor,
            foregroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(30),
            ),
          ),
          child: const Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(Icons.search, color: Colors.white),
              SizedBox(width: 8),
              Text(
                'chercher',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildCounterButton({
    required VoidCallback? onTap,
    required IconData icon,
    required bool isEnabled,
  }) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        width: 56,
        height: 56,
        decoration: BoxDecoration(
          shape: BoxShape.circle,
          color: isEnabled ? _appBlueColor : Colors.grey.shade300,
        ),
        child: Icon(
          icon,
          color: Colors.white,
          size: 24,
        ),
      ),
    );
  }
}
