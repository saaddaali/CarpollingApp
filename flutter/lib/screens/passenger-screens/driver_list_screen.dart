import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mycarpooling2/screens/passenger-screens/arrival_city_screen.dart';
import 'package:mycarpooling2/screens/passenger-screens/date_picker_screen.dart';
import 'package:mycarpooling2/screens/passenger-screens/departure_city_screen.dart';
import 'package:mycarpooling2/screens/passenger-screens/seats_screen.dart';
import 'package:mycarpooling2/screens/passenger-screens/trip_details_screen.dart';
import 'package:mycarpooling2/services/trajet_service.dart';
import 'package:mycarpooling2/models/trajet.dart';

class DriverListScreen extends StatefulWidget {
  final String departureCity;
  final String arrivalCity;
  final DateTime selectedDate;
  final int seats;

  static const Color primaryBlue = Color(0xFF4052EE);
  static const Color backgroundColor = Color(0xFFF5F5F5);
  static const Color lightBlue = Color(0xFFFFFFFF);
  static const Color textGrey = Color(0xFF757575);
  static const String baseImageUrl = 'http://localhost:8036';

  static String getImageUrl(String? photoPath) {
    if (photoPath == null) return '';
    return '$baseImageUrl/../../frontend/src/${photoPath.replaceFirst('assets/', '')}';
  }

  const DriverListScreen({
    super.key,
    required this.departureCity,
    required this.arrivalCity,
    required this.selectedDate,
    required this.seats,
  });

  @override
  State<DriverListScreen> createState() => _DriverListScreenState();
}

class _DriverListScreenState extends State<DriverListScreen> {
  final TrajetService _trajetService = TrajetService();
  List<Trajet> _trajets = [];
  bool _isLoading = true;
  String? _error;

  // Variables pour stocker les détails du trajet sélectionné
  String? selectedCityName;
  String? selectedLocationDetails;
  String? selectedEndCityName;
  String? selectedEndLocationDetails;
  String? selectedStartTime;
  String? selectedEndTime;
  String? selectedDriverName;
  double? selectedRating;
  int? selectedReviews;
  int? selectedAvailableSeats;
  int? selectedTotalSeats;
  double? selectedPrice;

  @override
  void initState() {
    super.initState();
    _loadTrajets();
  }

  Future<void> _loadTrajets() async {
    try {
      setState(() {
        _isLoading = true;
        _error = null;
      });

      final trajets = await _trajetService.findByCriteria(
        villeDepartLibelle: widget.departureCity,
        villeArriveeLibelle: widget.arrivalCity,
        //dateDepart: widget.selectedDate,
        nombrePlaceMin: widget.seats,
      );

      setState(() {
        _trajets = trajets;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = e.toString();
        _isLoading = false;
      });
    }
  }

  void _showFilterDialog(BuildContext context) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (BuildContext context) {
        return Container(
          height: MediaQuery.of(context).size.height * 0.7,
          decoration: const BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
            ),
          ),
          child: Column(
            children: [
              Container(
                margin: const EdgeInsets.only(top: 12),
                width: 40,
                height: 4,
                decoration: BoxDecoration(
                  color: Colors.grey[300],
                  borderRadius: BorderRadius.circular(2),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(20),
                child: Row(
                  children: [
                    IconButton(
                      icon: const Icon(Icons.close),
                      onPressed: () => Navigator.pop(context),
                      padding: EdgeInsets.zero,
                      constraints: const BoxConstraints(),
                    ),
                    const Expanded(
                      child: Text(
                        'Modifier la recherche',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                    const SizedBox(width: 24),
                  ],
                ),
              ),
              Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                margin: const EdgeInsets.symmetric(horizontal: 20),
                decoration: BoxDecoration(
                  color: const Color(0xFFF6F8FF),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Row(
                  children: [
                    Expanded(
                      child: GestureDetector(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const DepartureCityScreen(),
                            ),
                          );
                        },
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              widget.departureCity,
                              style: const TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                            const SizedBox(height: 4),
                            Text(
                              'Départ',
                              style: TextStyle(
                                fontSize: 14,
                                color: Colors.grey[600],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    const Icon(Icons.arrow_forward,
                        color: DriverListScreen.primaryBlue),
                    Expanded(
                      child: GestureDetector(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ArrivalCityScreen(
                                departureCity: widget.departureCity,
                              ),
                            ),
                          );
                        },
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.end,
                          children: [
                            Text(
                              widget.arrivalCity,
                              style: const TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                            const SizedBox(height: 4),
                            Text(
                              'Arrivée',
                              style: TextStyle(
                                fontSize: 14,
                                color: Colors.grey[600],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 20),
              Expanded(
                child: ListView(
                  padding: const EdgeInsets.symmetric(horizontal: 20),
                  children: [
                    GestureDetector(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => DatePickerScreen(
                              departureCity: widget.departureCity,
                              arrivalCity: widget.arrivalCity,
                            ),
                          ),
                        );
                      },
                      child: _buildFilterItem(
                        'Date',
                        DateFormat('EEEE d MMMM', 'fr_FR')
                            .format(widget.selectedDate)
                            .toLowerCase(),
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => SeatsSelectionScreen(
                              departureCity: widget.departureCity,
                              arrivalCity: widget.arrivalCity,
                              selectedDate: widget.selectedDate,
                            ),
                          ),
                        );
                      },
                      child: _buildFilterItem(
                        'Nombre de places',
                        '${widget.seats} ${widget.seats > 1 ? 'places' : 'place'}',
                      ),
                    ),
                  ],
                ),
              ),
              Container(
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color: Colors.white,
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.1),
                      spreadRadius: 0,
                      blurRadius: 10,
                      offset: const Offset(0, -4),
                    ),
                  ],
                ),
                child: ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                    _loadTrajets();
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: DriverListScreen.primaryBlue,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(28),
                    ),
                    padding: const EdgeInsets.symmetric(vertical: 16),
                    minimumSize: const Size(double.infinity, 56),
                    elevation: 0,
                  ),
                  child: const Text(
                    'Appliquer',
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildFilterItem(String title, String value) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 16),
      decoration: BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey[200]!)),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            title,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
            ),
          ),
          Row(
            children: [
              Text(
                value,
                style: const TextStyle(
                  fontSize: 16,
                  color: DriverListScreen.textGrey,
                ),
              ),
              const SizedBox(width: 8),
              const Icon(Icons.chevron_right, color: DriverListScreen.textGrey),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildTripCard(
    BuildContext context, {
    required String cityName,
    required String locationDetails,
    required String endCityName,
    required String endLocationDetails,
    required String startTime,
    required String endTime,
    required String driverName,
    required double rating,
    required int reviews,
    required int availableSeats,
    required int totalSeats,
    required double price,
    String? driverPhoto,
  }) {
    bool isSelected = selectedCityName == cityName &&
        selectedStartTime == startTime &&
        selectedEndTime == endTime;

    return GestureDetector(
      onTap: () {
        setState(() {
          selectedCityName = cityName;
          selectedLocationDetails = locationDetails;
          selectedEndCityName = endCityName;
          selectedEndLocationDetails = endLocationDetails;
          selectedStartTime = startTime;
          selectedEndTime = endTime;
          selectedDriverName = driverName;
          selectedRating = rating;
          selectedReviews = reviews;
          selectedAvailableSeats = availableSeats;
          selectedTotalSeats = totalSeats;
          selectedPrice = price;
        });
      },
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 6),
        decoration: BoxDecoration(
          color: DriverListScreen.lightBlue,
          borderRadius: BorderRadius.circular(16),
          border: isSelected
              ? Border.all(color: DriverListScreen.primaryBlue, width: 2)
              : null,
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              spreadRadius: 0,
              blurRadius: 10,
              offset: const Offset(0, 2),
            ),
          ],
        ),
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          cityName,
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                          ),
                        ),
                        Text(
                          locationDetails,
                          style: const TextStyle(
                            fontSize: 14,
                            color: DriverListScreen.textGrey,
                          ),
                        ),
                        const SizedBox(height: 16),
                        Text(
                          endCityName,
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                          ),
                        ),
                        Text(
                          endLocationDetails,
                          style: const TextStyle(
                            fontSize: 14,
                            color: DriverListScreen.textGrey,
                          ),
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    width: 80,
                    child: Column(
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            SizedBox(
                              width: 20,
                              child: Center(
                                child: Container(
                                  width: 6,
                                  height: 6,
                                  decoration: const BoxDecoration(
                                    shape: BoxShape.circle,
                                    color: DriverListScreen.primaryBlue,
                                  ),
                                ),
                              ),
                            ),
                            Text(
                              startTime,
                              style: const TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.w500,
                              ),
                            ),
                          ],
                        ),
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 4),
                          child: SizedBox(
                            width: 20,
                            height: 30,
                            child: Center(
                              child: CustomPaint(
                                size: const Size(2, 30),
                                painter: DottedLinePainter(
                                    color: DriverListScreen.primaryBlue),
                              ),
                            ),
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            SizedBox(
                              width: 20,
                              child: Center(
                                child: Container(
                                  width: 6,
                                  height: 6,
                                  decoration: const BoxDecoration(
                                    shape: BoxShape.circle,
                                    color: DriverListScreen.primaryBlue,
                                  ),
                                ),
                              ),
                            ),
                            Text(
                              endTime,
                              style: const TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.w500,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
            const Divider(height: 1),
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  CircleAvatar(
                    radius: 20,
                    backgroundColor: Colors.grey[300],
                    child: driverPhoto != null
                        ? ClipOval(
                            child: Image.network(
                              driverPhoto,
                              width: 40,
                              height: 40,
                              fit: BoxFit.cover,
                              errorBuilder: (context, error, stackTrace) {
                                return const Icon(Icons.person, color: Colors.white);
                              },
                            ),
                          )
                        : const Icon(Icons.person, color: Colors.white),
                  ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          driverName,
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w500,
                          ),
                        ),
                        Row(
                          children: [
                            const Icon(Icons.star,
                                color: Colors.amber, size: 16),
                            Text(
                              ' $rating/5 - $reviews avis',
                              style: const TextStyle(
                                color: DriverListScreen.textGrey,
                                fontSize: 14,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  Row(
                    children: [
                      const Icon(
                        Icons.airline_seat_recline_normal,
                        color: DriverListScreen.textGrey,
                        size: 20,
                      ),
                      Text(
                        ' $availableSeats/$totalSeats',
                        style: const TextStyle(
                          color: DriverListScreen.textGrey,
                          fontSize: 14,
                        ),
                      ),
                      const SizedBox(width: 8),
                      Text(
                        '${price.toStringAsFixed(2)} DHS',
                        style: const TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTrajetsList() {
    if (_isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (_error != null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Error: $_error'),
            ElevatedButton(
              onPressed: _loadTrajets,
              child: const Text('Retry'),
            ),
          ],
        ),
      );
    }

    if (_trajets.isEmpty) {
      return const Center(
        child: Text('No trips found for these criteria'),
      );
    }

    return ListView.builder(
      padding: const EdgeInsets.all(20),
      itemCount: _trajets.length,
      itemBuilder: (context, index) {
        final trajet = _trajets[index];
        return _buildTripCard(
          context,
          cityName: trajet.villeDepart.libelle,
          locationDetails: 'Pas de détails',
          endCityName: trajet.villeDestination.libelle,
          endLocationDetails: 'Pas de détails',
          startTime: DateFormat('HH:mm').format(trajet.horaireDepart),
          endTime: DateFormat('HH:mm').format(trajet.horaireArrive),
          driverName: trajet.driver.username,
          rating: trajet.driver.evaluation,
          reviews: 0,
          availableSeats: trajet.placesDisponibles,
          totalSeats: trajet.placesMax,
          price: trajet.prix,
          driverPhoto: trajet.driver.photo,
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: DriverListScreen.backgroundColor,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              margin: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: DriverListScreen.lightBlue,
                borderRadius: BorderRadius.circular(16),
              ),
              child: Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(left: 16),
                    child: GestureDetector(
                      onTap: () => Navigator.pop(context),
                      child: const Icon(Icons.arrow_back),
                    ),
                  ),
                  Expanded(
                    child: GestureDetector(
                      onTap: () => _showFilterDialog(context),
                      behavior: HitTestBehavior.opaque,
                      child: Padding(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 15, vertical: 16),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              '${widget.departureCity} > ${widget.arrivalCity}',
                              style: const TextStyle(
                                fontSize: 16,
                                color: DriverListScreen.textGrey,
                              ),
                            ),
                            Text(
                              '${DateFormat('E', 'fr_FR').format(widget.selectedDate).toLowerCase()}., ${DateFormat('d MMM', 'fr_FR').format(widget.selectedDate).toLowerCase()} - ${widget.seats} ${widget.seats > 1 ? 'places' : 'place'}',
                              style: const TextStyle(
                                fontSize: 14,
                                color: DriverListScreen.textGrey,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(right: 16),
                    child: GestureDetector(
                      onTap: () => _showFilterDialog(context),
                      child: const Icon(Icons.edit),
                    ),
                  ),
                ],
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Text(
                DateFormat('EEEE d MMMM', 'fr_FR')
                    .format(widget.selectedDate)
                    .toLowerCase(),
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),
            ),
            Expanded(
              child: _buildTrajetsList(),
            ),
            Container(
              decoration: BoxDecoration(
                color: DriverListScreen.backgroundColor,
                border: Border(
                  top: BorderSide(
                    color: Colors.grey[300]!,
                    width: 1,
                  ),
                ),
              ),
              padding: const EdgeInsets.all(20),
              child: SizedBox(
                width: double.infinity,
                height: 56,
                child: ElevatedButton(
                  onPressed: selectedCityName != null
                      ? () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => TripDetailsScreen(
                                cityName: selectedCityName!,
                                locationDetails: selectedLocationDetails!,
                                endCityName: selectedEndCityName!,
                                endLocationDetails: selectedEndLocationDetails!,
                                startTime: selectedStartTime!,
                                endTime: selectedEndTime!,
                                driverName: selectedDriverName!,
                                rating: selectedRating!,
                                reviews: selectedReviews!,
                                availableSeats: selectedAvailableSeats!,
                                totalSeats: selectedTotalSeats!,
                                price: selectedPrice!,
                              ),
                            ),
                          );
                        }
                      : null,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: DriverListScreen.primaryBlue,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(28),
                    ),
                    elevation: 0,
                    disabledBackgroundColor: Colors.grey[300],
                  ),
                  child: const Text(
                    'Parcourir',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.w600,
                    ),
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

class DottedLinePainter extends CustomPainter {
  final Color color;

  DottedLinePainter({required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    final Paint paint = Paint()
      ..color = color
      ..strokeWidth = 1.5
      ..strokeCap = StrokeCap.round;

    const double dashHeight = 3;
    const double dashSpace = 3;
    double startY = 0;

    while (startY < size.height) {
      canvas.drawLine(
        Offset(0, startY),
        Offset(0, startY + dashHeight),
        paint,
      );
      startY += dashHeight + dashSpace;
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;
}
