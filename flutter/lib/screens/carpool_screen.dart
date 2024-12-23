// carpool_screen.dart
import 'package:flutter/material.dart';
import 'chat_screen.dart';
import 'profile_screen.dart';
import 'home_screen.dart';
import 'driver-screens/driver_home_screen.dart';
import 'wallet_screen.dart';
import '../models/reservation.dart';
import '../services/reservation_service.dart';

class CarpoolScreen extends StatefulWidget {
  const CarpoolScreen({super.key});

  @override
  State<CarpoolScreen> createState() => _CarpoolScreenState();
}

class _CarpoolScreenState extends State<CarpoolScreen> {
  static const Color primaryBlue =
      Color(0xFF4052EE); // Même couleur que HomeScreen
  final int _selectedIndex = 2;
  final ReservationService _reservationService = ReservationService();
  List<Reservation> _upcomingReservations = [];
  List<Reservation> _pastReservations = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadReservations();
  }

  Future<void> _loadReservations() async {
    try {
      final reservations = await _reservationService.getReservations();
      final now = DateTime.now();
      
      print('Loaded ${reservations.length} reservations');
      
      setState(() {
        _upcomingReservations = reservations
            .where((reservation) => reservation.dateReservation.isAfter(now))
            .toList();
        _pastReservations = reservations
            .where((reservation) => reservation.dateReservation.isBefore(now))
            .toList();
        _isLoading = false;
      });
      
      print('Upcoming reservations: ${_upcomingReservations.length}');
      print('Past reservations: ${_pastReservations.length}');
    } catch (e) {
      print('Error loading reservations: $e');
      setState(() => _isLoading = false);
      // You might want to show an error message here
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header avec profil, aide et chat
            Padding(
              padding: const EdgeInsets.fromLTRB(20, 16, 20, 0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const ProfileScreen(),
                        ),
                      );
                    },
                    child: Container(
                      padding: const EdgeInsets.all(8),
                      decoration: BoxDecoration(
                        color: primaryBlue.withOpacity(0.1),
                        shape: BoxShape.circle,
                      ),
                      child: const Icon(
                        Icons.person,
                        color: primaryBlue,
                        size: 24,
                      ),
                    ),
                  ),
                  Row(
                    children: [
                      Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 16,
                          vertical: 8,
                        ),
                        decoration: BoxDecoration(
                          color: const Color(0xFFFED049),
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: const Row(
                          children: [
                            Icon(
                              Icons.warning,
                              size: 20,
                              color: Colors.black,
                            ),
                            SizedBox(width: 6),
                            Text(
                              'Aide',
                              style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.w500,
                                color: Colors.black,
                              ),
                            ),
                          ],
                        ),
                      ),
                      const SizedBox(width: 12),
                      GestureDetector(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const ChatScreen(),
                            ),
                          );
                        },
                        child: Container(
                          padding: const EdgeInsets.all(8),
                          decoration: BoxDecoration(
                            color: Colors.grey[200],
                            shape: BoxShape.circle,
                          ),
                          child: Icon(
                            Icons.chat_outlined,
                            size: 24,
                            color: Colors.grey[600],
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),

            const SizedBox(height: 32),

            // Titre
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                'Mes Covoiturages',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  height: 1.2,
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Contenu principal
            Expanded(
              child: DefaultTabController(
                length: 2,
                child: Column(
                  children: [
                    const TabBar(
                      tabs: [
                        Tab(text: 'À venir'),
                        Tab(text: 'Historique'),
                      ],
                      labelColor: primaryBlue,
                      unselectedLabelColor: Colors.grey,
                      indicatorColor: primaryBlue,
                    ),
                    Expanded(
                      child: TabBarView(
                        children: [
                          // Onglet À venir
                          _buildTripsList(upcoming: true),
                          // Onglet Historique
                          _buildTripsList(upcoming: false),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),

            // Barre de navigation
            Column(
              children: [
                Container(
                  margin: const EdgeInsets.symmetric(horizontal: 20),
                  height: 4,
                  decoration: BoxDecoration(
                    color: primaryBlue.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(2),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: Container(
                          margin: EdgeInsets.only(
                            left: MediaQuery.of(context).size.width *
                                _selectedIndex /
                                4,
                            right: MediaQuery.of(context).size.width *
                                (3 - _selectedIndex) /
                                4,
                          ),
                          decoration: BoxDecoration(
                            color: primaryBlue,
                            borderRadius: BorderRadius.circular(2),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                Container(
                  padding: const EdgeInsets.fromLTRB(0, 12, 0, 32),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      _buildNavItem(0, Icons.person, 'Passager'),
                      _buildNavItem(1, Icons.directions_car, 'Conducteur'),
                      _buildNavItem(2, Icons.groups, 'Covoiturages'),
                      _buildNavItem(3, Icons.account_balance_wallet, 'Wallet'),
                    ],
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTripsList({required bool upcoming}) {
    final reservations = upcoming ? _upcomingReservations : _pastReservations;

    if (_isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (reservations.isEmpty) {
      return const Center(
        child: Text('Aucun covoiturage trouvé'),
      );
    }

    return ListView.builder(
      padding: const EdgeInsets.all(20),
      itemCount: reservations.length,
      itemBuilder: (context, index) {
        final reservation = reservations[index];
        return Container(
          margin: const EdgeInsets.only(bottom: 16),
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(15),
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.1),
                blurRadius: 10,
                offset: const Offset(0, 2),
              ),
            ],
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    _formatDate(reservation.dateReservation),
                    style: const TextStyle(
                      color: Colors.grey,
                      fontSize: 14,
                    ),
                  ),
                  Text(
                    '${reservation.montant ?? 0} DH',
                    style: const TextStyle(
                      color: primaryBlue,
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Icon(Icons.location_on, color: primaryBlue, size: 20),
                  const SizedBox(width: 8),
                  Text(
                    '${reservation.trajet.villeDepart.libelle} → ${reservation.trajet.villeDestination.libelle}',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ],
              ),
            ],
          ),
        );
      },
    );
  }

  String _formatDate(DateTime date) {
    return '${date.day}/${date.month}/${date.year}';
  }

  Widget _buildNavItem(int index, IconData icon, String label) {
    bool isSelected = _selectedIndex == index;

    return InkWell(
      onTap: () {
        if (index != _selectedIndex) {
          switch (index) {
            case 0:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => const HomeScreen()),
              );
              break;
            case 1:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => const DriverScreen()),
              );
              break;
            case 3:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => const WalletScreen()),
              );
              break;
          }
        }
      },
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(
            icon,
            color: isSelected ? primaryBlue : Colors.grey,
            size: 26,
          ),
          const SizedBox(height: 6),
          Text(
            label,
            style: TextStyle(
              color: isSelected ? primaryBlue : Colors.grey,
              fontSize: 14,
              fontWeight: isSelected ? FontWeight.w500 : FontWeight.normal,
            ),
          ),
        ],
      ),
    );
  }
}
