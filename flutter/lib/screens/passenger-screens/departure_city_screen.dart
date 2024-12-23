import 'package:flutter/material.dart';
import 'arrival_city_screen.dart';
import '../../models/city.dart';
import '../../services/city_service.dart';

class DepartureCityScreen extends StatefulWidget {
  const DepartureCityScreen({super.key});

  @override
  State<DepartureCityScreen> createState() => _DepartureCityScreenState();
}

class _DepartureCityScreenState extends State<DepartureCityScreen> {
  final CityService _cityService = CityService();
  List<City> filteredCities = [];
  bool _isLoading = false;
  String _errorMessage = '';
  final TextEditingController _searchController = TextEditingController();

  Future<void> _loadInitialCities() async {
    try {
      setState(() {
        _isLoading = true;
        _errorMessage = '';
      });

      final cities = await _cityService.findAllOptimized();
      print('Loaded cities: ${cities.length}'); // Debug log
      cities.forEach((city) => print('City: ${city.libelle}')); // Debug log

      if (mounted) {
        setState(() {
          filteredCities = cities;
          _isLoading = false;
        });
      }
    } catch (e) {
      print('Error loading cities: $e'); // Debug log
      if (mounted) {
        setState(() {
          _errorMessage = 'Erreur lors du chargement des villes';
          _isLoading = false;
        });
      }
    }
  }

  @override
  void initState() {
    super.initState();
    _loadInitialCities();
  }

  Future<void> filterCities(String query) async {
    if (query.isEmpty) {
      setState(() {
        filteredCities = [];
        _errorMessage = '';
      });
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    try {
      final cities = await _cityService.searchCities(query);
      setState(() {
        filteredCities = cities;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _errorMessage = 'Erreur lors de la recherche des villes';
        _isLoading = false;
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
            // Header with close button
            Padding(
              padding: const EdgeInsets.all(20.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Ville de départ',
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: const Icon(Icons.close),
                  ),
                ],
              ),
            ),

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
                          hintText: 'Ville de départ',
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
              child: _isLoading 
                ? const Center(child: CircularProgressIndicator())
                : _errorMessage.isNotEmpty
                  ? Center(child: Text(_errorMessage))
                  : ListView.builder(
                      padding: const EdgeInsets.symmetric(horizontal: 20),
                      itemCount: filteredCities.length,
                      itemBuilder: (context, index) {
                        final city = filteredCities[index];
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
                              city.libelle,
                              style: const TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.w500,
                              ),
                            ),
                            trailing: const Icon(
                              Icons.chevron_right,
                              color: Colors.grey,
                            ),
                            onTap: () async {
                              final result = await Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => ArrivalCityScreen(
                                    departureCity: city.libelle,
                                  ),
                                ),
                              );
                              if (result != null && context.mounted) {
                                Navigator.pop(context, result);
                              }
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
