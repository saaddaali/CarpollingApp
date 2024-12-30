// driver_screen.dart
import 'package:flutter/material.dart';
import 'package:mycarpooling2/models/trajet.dart';
import 'package:mycarpooling2/services/passenger_manager.dart';
import 'package:mycarpooling2/services/trajet_service.dart';
import '../chat_screen.dart';
import '../profile_screen.dart';
import '../home_screen.dart';
import '../carpool_screen.dart';
import '../wallet_screen.dart';
import 'create_trip_screen.dart';

class DriverScreen extends StatefulWidget {
  const DriverScreen({super.key});

  @override
  State<DriverScreen> createState() => _DriverScreenState();
}

class _DriverScreenState extends State<DriverScreen> {
  static const Color primaryBlue = Color(0xFF4052EE);
  final int _selectedIndex = 1;
  final TrajetService _trajetService = TrajetService();
  List<Trajet> _upcomingTrips = [];
  List<Trajet> _pastTrips = [];
  bool _isLoading = true;
  String username = 'User';



  

  @override
  void initState() {
    super.initState();
    _loadTrips();
    _loadUsername();
  }

  Future<void> _loadTrips() async {
    try {
      final trips = await _trajetService.findByCurrentUser();
      final now = DateTime.now();
      
      setState(() {
        _upcomingTrips = trips
            .where((trip) => trip.horaireDepart.isAfter(now))
            .toList();
        _pastTrips = trips
            .where((trip) => trip.horaireDepart.isBefore(now))
            .toList();
        _isLoading = false;
      });
    } catch (e) {
      print('Error loading trips: $e');
      setState(() => _isLoading = false);
    }
  }

  Future<void> _loadUsername() async {
    final passenger = await PassengerManager.getPassenger();
    if (passenger != null) {
      setState(() {
        username = passenger.username;
      });
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
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                '$username, tu voyages quelque part?',
                style: const TextStyle(
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
                      Container(
                      width: double.infinity,
                      margin: const EdgeInsets.only(bottom: 20, left: 20, right: 20),
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const CreateTripScreen(),
                            ),
                          );
                        },
                        style: ElevatedButton.styleFrom(
                          backgroundColor: primaryBlue,
                          padding: const EdgeInsets.symmetric(vertical: 15),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                        ),
                        child: const Text(
                          ' + Créer un nouveau trajet',
                          style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.bold,
                            color: Colors.white,
                          ),
                        ),
                      ),
                    ),
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
                            _buildTripsList(upcoming: true),
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
  final trips = upcoming ? _upcomingTrips : _pastTrips;

  if (_isLoading) {
    return const Center(child: CircularProgressIndicator());
  }

  if (trips.isEmpty) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            upcoming ? 'Aucun trajet à venir' : 'Aucun trajet passé',
            style: const TextStyle(fontSize: 16, color: Colors.grey),
          ),
          const SizedBox(height: 16),
          if (upcoming)
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const CreateTripScreen(),
                  ),
                );
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: primaryBlue,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(24),
                ),
              ),
              child: const Text('Créer un trajet'),
            ),
        ],
      ),
    );
  }

  return ListView.builder(
    padding: const EdgeInsets.all(20),
    itemCount: trips.length,
    itemBuilder: (context, index) {
      final trip = trips[index];
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
                  _formatDate(trip.horaireDepart),
                  style: const TextStyle(
                    color: Colors.grey,
                    fontSize: 14,
                  ),
                ),
                Text(
                  '${trip.prix} DH',
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
                  '${trip.villeDepart.libelle} → ${trip.villeDestination.libelle}',
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
            case 2:
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(builder: (context) => const CarpoolScreen()),
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
