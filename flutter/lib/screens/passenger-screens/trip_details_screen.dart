import 'package:flutter/material.dart';
import 'package:mycarpooling2/screens/passenger-screens/driver_details_screen.dart';

class TripDetailsScreen extends StatelessWidget {
  final String cityName;
  final String locationDetails;
  final String endCityName;
  final String endLocationDetails;
  final String startTime;
  final String endTime;
  final String driverName;
  final double rating;
  final int reviews;
  final int availableSeats;
  final int totalSeats;
  final double price;

  static const Color primaryBlue = Color(0xFF4052EE);
  static const Color textGrey = Color(0xFF9E9E9E);

  const TripDetailsScreen({
    super.key,
    required this.cityName,
    required this.locationDetails,
    required this.endCityName,
    required this.endLocationDetails,
    required this.startTime,
    required this.endTime,
    required this.driverName,
    required this.rating,
    required this.reviews,
    required this.availableSeats,
    required this.totalSeats,
    required this.price,
  });

  Widget buildInfoTile({
    required IconData icon,
    required String title,
    String? subtitle,
    String? trailing,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.grey[100],
        borderRadius: BorderRadius.circular(12),
      ),
      padding: const EdgeInsets.all(12),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: Colors.grey[200],
              borderRadius: BorderRadius.circular(8),
            ),
            child: Icon(icon, size: 20, color: Colors.black87),
          ),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                if (subtitle != null)
                  Text(
                    subtitle,
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.grey[600],
                    ),
                  ),
                if (trailing != null)
                  Text(
                    trailing,
                    style: TextStyle(
                      fontSize: 12,
                      color: Colors.grey[600],
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget buildTimeContainer(String time) {
    return Container(
      padding: const EdgeInsets.symmetric(
        horizontal: 12,
        vertical: 6,
      ),
      decoration: BoxDecoration(
        color: Colors.black.withOpacity(0.05),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Icon(Icons.access_time, size: 16, color: Colors.black87),
          const SizedBox(width: 4),
          Text(
            time,
            style: const TextStyle(color: Color.fromARGB(255, 35, 27, 189)),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[100],
      body: SafeArea(
        child: Column(
          children: [
            // Header
            const Padding(
              padding: EdgeInsets.all(16),
              child: Row(
                children: [
                  BackButton(),
                  SizedBox(width: 8),
                  Text(
                    'lundi 18 novembre',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),

            // Main content
            Expanded(
              child: ListView(
                padding: const EdgeInsets.all(16),
                children: [
                  // Route Card
                  Container(
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.1),
                          blurRadius: 10,
                        ),
                      ],
                    ),
                    child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: IntrinsicHeight(
                        child: Row(
                          children: [
                            // Timeline
                            SizedBox(
                              width: 20,
                              child: Column(
                                children: [
                                  Container(
                                    width: 12,
                                    height: 12,
                                    decoration: BoxDecoration(
                                      color: primaryBlue,
                                      shape: BoxShape.circle,
                                      border: Border.all(
                                        color: Colors.white,
                                        width: 2,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: VerticalDivider(
                                      color: primaryBlue.withOpacity(0.3),
                                      thickness: 2,
                                    ),
                                  ),
                                  Container(
                                    width: 12,
                                    height: 12,
                                    decoration: BoxDecoration(
                                      color: primaryBlue,
                                      shape: BoxShape.circle,
                                      border: Border.all(
                                        color: Colors.white,
                                        width: 2,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            const SizedBox(width: 16),
                            Expanded(
                              child: Column(
                                children: [
                                  Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Row(
                                        children: [
                                          const Icon(
                                            Icons.location_on,
                                            size: 20,
                                            color: Colors.black87,
                                          ),
                                          const SizedBox(width: 8),
                                          Text(
                                            cityName,
                                            style: const TextStyle(
                                              fontSize: 16,
                                              fontWeight: FontWeight.bold,
                                            ),
                                          ),
                                        ],
                                      ),
                                      Padding(
                                        padding:
                                            const EdgeInsets.only(left: 28),
                                        child: Text(
                                          'No details',
                                          style: const TextStyle(
                                            color: textGrey,
                                            fontSize: 14,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.only(
                                          left: 28,
                                          top: 8,
                                        ),
                                        child: buildTimeContainer(startTime),
                                      ),
                                    ],
                                  ),
                                  const SizedBox(height: 24),
                                  Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Row(
                                        children: [
                                          const Icon(
                                            Icons.location_on,
                                            size: 20,
                                            color: Colors.black87,
                                          ),
                                          const SizedBox(width: 8),
                                          Text(
                                            endCityName,
                                            style: const TextStyle(
                                              fontSize: 16,
                                              fontWeight: FontWeight.bold,
                                            ),
                                          ),
                                        ],
                                      ),
                                      Padding(
                                        padding:
                                            const EdgeInsets.only(left: 28),
                                        child: Text(
                                          'No details',
                                          style: const TextStyle(
                                            color: textGrey,
                                            fontSize: 14,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.only(
                                          left: 28,
                                          top: 8,
                                        ),
                                        child: buildTimeContainer(endTime),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),

                  const SizedBox(height: 16),

                  // Trip Info Card
                  Container(
                    padding: const EdgeInsets.all(16),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.1),
                          blurRadius: 10,
                        ),
                      ],
                    ),
                    child: Column(
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Expanded(
                              child: buildInfoTile(
                                icon: Icons.event_seat,
                                title: '$availableSeats restant(s)',
                                subtitle: '$totalSeats Total',
                              ),
                            ),
                            const SizedBox(width: 16),
                            Expanded(
                              child: buildInfoTile(
                                icon: Icons.directions_car,
                                title: 'SKODA',
                                trailing: 'Black',
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 16),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Expanded(
                              child: buildInfoTile(
                                icon: Icons.luggage,
                                title: 'Moyen',
                              ),
                            ),
                            const SizedBox(width: 16),
                            Expanded(
                              child: buildInfoTile(
                                icon: Icons.play_arrow,
                                title: 'Covoiturage direct',
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),

                  const SizedBox(height: 20),

                  // Price Card
                  Container(
                    padding: const EdgeInsets.all(16),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.1),
                          blurRadius: 10,
                        ),
                      ],
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Prix total pour 1 passager(s)',
                          style: TextStyle(
                            color: Colors.grey[600],
                            fontSize: 14,
                          ),
                        ),
                        const SizedBox(height: 8),
                        Row(
                          children: [
                            Text(
                              '${price.toStringAsFixed(2)} DHS',
                              style: const TextStyle(
                                fontSize: 24,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            const Spacer(),
                            //const Icon(Icons.chevron_right),
                          ],
                        ),
                      ],
                    ),
                  ),

                  const SizedBox(height: 16),

                  // Driver Card
                  // Dans la section "Driver Card", remplacez le Container par un GestureDetector:

                  GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => DriverDetailsScreen(
                            driverName: driverName,
                            rating: rating,
                            reviews: reviews,
                          ),
                        ),
                      );
                    },
                    child: Container(
                      padding: const EdgeInsets.all(16),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(12),
                        boxShadow: [
                          BoxShadow(
                            color: Colors.black.withOpacity(0.1),
                            blurRadius: 10,
                          ),
                        ],
                      ),
                      child: Row(
                        children: [
                          CircleAvatar(
                            radius: 24,
                            backgroundColor: Colors.grey[300],
                            child: const Icon(
                              Icons.person,
                              color: Colors.white,
                            ),
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
                                const SizedBox(height: 4),
                                Row(
                                  children: [
                                    const Icon(
                                      Icons.star,
                                      color: Colors.amber,
                                      size: 16,
                                    ),
                                    Text(
                                      ' $rating/5 - $reviews avis',
                                      style: const TextStyle(color: textGrey),
                                    ),
                                  ],
                                ),
                              ],
                            ),
                          ),
                          const Icon(Icons.chevron_right, color: Colors.black),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),

            // Bottom Button
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                //color: Colors.white,
                border: Border(
                  top: BorderSide(color: Colors.grey[300]!),
                ),
              ),
              child: ElevatedButton(
                onPressed: () {},
                style: ElevatedButton.styleFrom(
                  backgroundColor: primaryBlue,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(24),
                  ),
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  elevation: 0,
                ),
                child: const Text(
                  'Réserver',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 18,
                    fontWeight: FontWeight.w600,
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
