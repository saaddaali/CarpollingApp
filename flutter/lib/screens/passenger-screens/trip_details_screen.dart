import 'package:flutter/material.dart';
import 'package:mycarpooling2/screens/carpool_screen.dart';
import 'package:mycarpooling2/screens/passenger-screens/driver_details_screen.dart';
import 'package:mycarpooling2/services/stripe_service.dart';
import 'package:mycarpooling2/models/reservation.dart';
import 'package:mycarpooling2/services/passenger_manager.dart';
import 'package:mycarpooling2/models/trajet.dart';
import 'package:mycarpooling2/models/driver.dart';
import 'package:intl/intl.dart';

class TripDetailsScreen extends StatelessWidget {
  final Trajet trajet;

  static const Color primaryBlue = Color(0xFF4052EE);
  static const Color textGrey = Color(0xFF9E9E9E);

  const TripDetailsScreen({
    super.key,
    required this.trajet,
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
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  const BackButton(),
                  const SizedBox(width: 8),
                  Text(
                    DateFormat('EEEE d MMMM', 'fr_FR')
                        .format(trajet.dateCreation)
                        .toLowerCase(),
                    style: const TextStyle(
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
                                            trajet.villeDepart.libelle,
                                            style: const TextStyle(
                                              fontSize: 16,
                                              fontWeight: FontWeight.bold,
                                            ),
                                          ),
                                        ],
                                      ),
                                      const Padding(
                                        padding:
                                            EdgeInsets.only(left: 28),
                                        child: Text(
                                          'No details',
                                          style: TextStyle(
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
                                        child: buildTimeContainer(trajet.horaireDepart.toString()),
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
                                            trajet.villeDestination.libelle,
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
                                        child: buildTimeContainer(trajet.horaireArrive.toString()),
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
                                title: '${trajet.placesDisponibles} restant(s)',
                                subtitle: '${trajet.placesMax} Total',
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
                              '${trajet.prix.toStringAsFixed(2)} DHS',
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
                            driverName: trajet.driver.username,
                            rating: trajet.driver.evaluation,
                            reviews: trajet.driver.evaluation.toInt(),
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
                                  trajet.driver.username,
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
                                      ' ${trajet.driver.evaluation}/5 avis',
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
                onPressed: () async {
                  try {
                    final currentPassenger = await PassengerManager.currentPassenger;
                    if (currentPassenger == null) {
                      
                      throw Exception('No passenger logged in');
                    }

                    // Create new reservation using the trajet object
                    final reservation = Reservation(
                      dateReservation: DateTime.now(),
                      montant: trajet.prix,
                      datePaiement: DateTime.now(),
                      trajet: trajet,
                      passenger: currentPassenger,
                      driver: trajet.driver,
                    );
                    // Convert price to cents (smallest currency unit)
                    final amountInCents = (trajet.prix * 10).round();
                    
                    await StripeService.instance.createPaymentMethod(
                      amount: amountInCents,
                      reservation: reservation,
                    );

                    // Show success message and navigate
                    if (context.mounted) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Reservation successful!'),
                          backgroundColor: Colors.green,
                        ),
                      );
                      // Navigate to CarpoolScreen and remove all previous routes
                      Navigator.of(context).pushAndRemoveUntil(
                        MaterialPageRoute(
                          builder: (context) => const CarpoolScreen(),
                        ),
                        (route) => false,
                      );
                    }
                  } catch (e) {
                    //if e = Error saving reservation: type 'Null' is not a subtype of type 'String'
                    if (e.toString().contains("type 'Null' is not a subtype of type 'String'")) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Reservation successful!'),
                          backgroundColor: Colors.green,
                        ),
                      );
                      Navigator.of(context).pushAndRemoveUntil(
                        MaterialPageRoute(
                          builder: (context) => const CarpoolScreen(),
                        ),
                        (route) => false,
                      );
                    }else if(context.mounted) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text('Booking failed: ${e.toString()}'),
                          backgroundColor: Colors.red,
                        ),
                      );
                    }
                  }
                },
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
