import 'package:flutter/material.dart';
import 'date_picker_screen.dart';

class ArrivalCityScreen extends StatefulWidget {
  final String departureCity;

  const ArrivalCityScreen({
    super.key,
    required this.departureCity,
  });

  @override
  State<ArrivalCityScreen> createState() => _ArrivalCityScreenState();
}

class _ArrivalCityScreenState extends State<ArrivalCityScreen> {
  final List<String> allCities = [
    'Casablanca',
    'Fes',
    'Salé',
    'Marrakech',
    'Tanger',
    'Rabat',
    'Meknes',
    'Oujda',
    'Kenitra',
    'Agadir',
    'Tetouan',
    'Temara',
    'Safi',
    'Mohammedia',
    'El Jadida',
    'Beni Mellal',
    'Nador',
    'Khouribga',
    'Settat',
    'Berrechid',
    'Taza',
    'Khemisset',
    'Larache',
    'Ksar El Kebir',
  ];

  List<String> filteredCities = [];
  final TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    // Filtrer la ville de départ de la liste des villes d'arrivée
    filteredCities =
        allCities.where((city) => city != widget.departureCity).toList();
  }

  void filterCities(String query) {
    if (query.isEmpty) {
      setState(() {
        filteredCities =
            allCities.where((city) => city != widget.departureCity).toList();
      });
    } else {
      setState(() {
        filteredCities = allCities
            .where((city) =>
                city.toLowerCase().contains(query.toLowerCase()) &&
                city != widget.departureCity)
            .toList();
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
            // Header with back button and departure city
            Padding(
              padding: const EdgeInsets.all(20.0),
              child: Row(
                children: [
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: const Icon(
                      Icons.arrow_back,
                      color: Colors.black,
                    ),
                  ),
                  const SizedBox(width: 10),
                  Text(
                    widget.departureCity,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Colors.grey,
                    ),
                  ),
                ],
              ),
            ),

            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                "Ville d'arrivée",
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),

            const SizedBox(height: 20),

            // Search bar
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Container(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                decoration: BoxDecoration(
                  color: const Color(0xFFF5F5F5),
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
                    Expanded(
                      child: TextField(
                        controller: _searchController,
                        onChanged: filterCities,
                        decoration: InputDecoration(
                          border: InputBorder.none,
                          hintText: "Ville d'arrivée",
                          hintStyle: TextStyle(
                            fontSize: 16,
                            color: Colors.grey[600],
                          ),
                        ),
                        style: const TextStyle(
                          fontSize: 16,
                          color: Colors.black87,
                        ),
                      ),
                    ),
                    if (_searchController.text.isNotEmpty)
                      IconButton(
                        icon: const Icon(Icons.clear, color: Colors.grey),
                        onPressed: () {
                          _searchController.clear();
                          filterCities('');
                        },
                      ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Suggested cities section
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                'Villes suggérées',
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ),

            const SizedBox(height: 16),

            // Cities list
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                itemCount: filteredCities.length,
                itemBuilder: (context, index) {
                  return Container(
                    margin: const EdgeInsets.only(bottom: 12),
                    decoration: BoxDecoration(
                      color: const Color(0xFFF5F5F5),
                      borderRadius: BorderRadius.circular(15),
                    ),
                    child: ListTile(
                      leading: Container(
                        padding: const EdgeInsets.all(8),
                        decoration: BoxDecoration(
                          color: const Color(0xFF4052EE).withOpacity(0.1),
                          shape: BoxShape.circle,
                        ),
                        child: const Icon(
                          Icons.location_on,
                          color: Color(0xFF4052EE),
                          size: 20,
                        ),
                      ),
                      title: Text(
                        filteredCities[index],
                        style: const TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      trailing: const Icon(
                        Icons.chevron_right,
                        color: Colors.grey,
                      ),
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => DatePickerScreen(
                              departureCity: widget.departureCity,
                              arrivalCity: filteredCities[index],
                            ),
                          ),
                        );
                      },
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }
}
