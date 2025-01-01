import 'package:flutter/material.dart';
import 'package:mycarpooling2/screens/faq.dart';
import 'passenger-screens/departure_city_screen.dart';
import 'chat_screen.dart';
import 'profile_screen.dart';
import 'driver-screens/driver_home_screen.dart';
import 'carpool_screen.dart';
import 'wallet_screen.dart';
import '../models/passenger.dart';
import '../services/passenger_manager.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;
  String? selectedDepartureCity;
  String username = 'User';

  @override
  void initState() {
    super.initState();
    _loadUsername();
  }

  Future<void> _loadUsername() async {
    final passenger = await PassengerManager.getPassenger();
    if (passenger != null) {
      setState(() {
        username = passenger.username;
      });
    }
  }

  // Définition des couleurs
  static const Color primaryBlue = Color(0xFF4052EE);
  static const Color searchBarColor = Color(0xFFF5F5F5);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header with profile, help and chat buttons
            Padding(
              padding: const EdgeInsets.fromLTRB(20, 16, 20, 0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  // Profile Button
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

                  // Help and Chat buttons
                    Row(
                    children: [
                      // Help Button
                      GestureDetector(
                      onTap: () {
                        Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const FAQScreen(),
                        ),
                        );
                      },
                      child: Container(
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
                      ),

                      const SizedBox(width: 12),

                      // Chat Button
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

            // Welcome text
            Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                '$username, où souhaites-tu voyager?',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  height: 1.2,
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Search bar
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: InkWell(
                onTap: () async {
                  final result = await Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => const DepartureCityScreen(),
                    ),
                  );
                  if (result != null) {
                    setState(() {
                      selectedDepartureCity = result;
                    });
                  }
                },
                child: Container(
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: searchBarColor,
                    borderRadius: BorderRadius.circular(15),
                  ),
                  child: Row(
                    children: [
                      Icon(
                        Icons.search,
                        color: Colors.grey[600],
                        size: 26,
                      ),
                      const SizedBox(width: 12),
                      Text(
                        selectedDepartureCity ?? 'Chercher parmi 339 offres',
                        style: TextStyle(
                          fontSize: 16,
                          color: selectedDepartureCity != null
                              ? Colors.black
                              : Colors.grey[600],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),

            const SizedBox(height: 32),

            // Popular searches section
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                'Recherches populaires',
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ),

            const SizedBox(height: 16),

            // Popular routes
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Column(
                children: [
                  _buildRouteItem('Casablanca', 'Marrakech'),
                  _buildRouteItem('Casablanca', 'Agadir'),
                  _buildRouteItem('Agadir', 'Casablanca'),
                ],
              ),
            ),

            const Spacer(),

            // Bottom navigation
            Column(
              children: [
                // Indicator line
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

                // Navigation bar
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

  Widget _buildRouteItem(String from, String to) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 20),
      decoration: BoxDecoration(
        color: searchBarColor,
        borderRadius: BorderRadius.circular(15),
      ),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: primaryBlue.withOpacity(0.1),
              shape: BoxShape.circle,
            ),
            child: const Icon(
              Icons.location_on,
              color: primaryBlue,
              size: 20,
            ),
          ),
          const SizedBox(width: 12),
          Text(
            '$from > $to',
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.w500,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildNavItem(int index, IconData icon, String label) {
    bool isSelected = _selectedIndex == index;

    return InkWell(
      onTap: () {
        setState(() {
          _selectedIndex = index;
        });
        switch (index) {
          case 1:
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(builder: (context) => const DriverScreen()),
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
