import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:mycarpooling2/models/city.dart';
import 'package:mycarpooling2/screens/passenger-screens/seats_screen.dart';

class DatePickerScreen extends StatefulWidget {
  final City? departureCity;
  final City? arrivalCity;

  const DatePickerScreen({
    super.key,
    required this.departureCity,
    required this.arrivalCity,
  });

  @override
  State<DatePickerScreen> createState() => _DatePickerScreenState();
}

class _DatePickerScreenState extends State<DatePickerScreen> {
  DateTime? _selectedDay;
  final ScrollController _scrollController = ScrollController();
  bool _isLoading = true;
  final List<DateTime> _months = [];

  @override
  void initState() {
    super.initState();
    _initializeCalendar();
    _generateMonths();
  }

  void _generateMonths() {
    DateTime current = DateTime.now();
    for (int i = 0; i < 24; i++) {
      _months.add(DateTime(current.year, current.month + i, 1));
    }
  }

  Future<void> _initializeCalendar() async {
    await initializeDateFormatting('fr_FR', null);
    if (mounted) {
      setState(() {
        _isLoading = false;
      });
    }
  }

  List<DateTime> _getDaysInMonth(DateTime month) {
    final daysInMonth = DateTime(month.year, month.month + 1, 0).day;
    final firstDayOfMonth = DateTime(month.year, month.month, 1);
    final firstWeekday = firstDayOfMonth.weekday;

    List<DateTime> days = [];

    // Add empty slots for days before the first day of the month
    for (int i = 1; i < firstWeekday; i++) {
      days.add(DateTime(0));
    }

    // Add all days of the month
    for (int day = 1; day <= daysInMonth; day++) {
      days.add(DateTime(month.year, month.month, day));
    }

    return days;
  }

  Widget _buildMonthCalendar(DateTime month) {
    final days = _getDaysInMonth(month);
    final DateFormat monthFormat = DateFormat('MMMM yyyy', 'fr_FR');

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 16.0),
          child: Text(
            monthFormat.format(month).toUpperCase(),
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Colors.black54,
            ),
          ),
        ),
        GridView.builder(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 7,
            childAspectRatio: 1.0,
          ),
          itemCount: days.length,
          itemBuilder: (context, index) {
            final day = days[index];
            final isToday = isSameDay(day, DateTime.now());
            final isSelected =
                _selectedDay != null && isSameDay(day, _selectedDay!);
            final isPastDay = day.year != 0 && day.isBefore(DateTime.now());

            if (day.year == 0) {
              return const SizedBox();
            }

            return InkWell(
              onTap: isPastDay
                  ? null
                  : () async {
                      setState(() {
                        _selectedDay = day;
                      });
                      // Navigate to SeatsSelectionScreen
                      final result = await Navigator.push<Map<String, dynamic>>(
                        context,
                        MaterialPageRoute(
                          builder: (context) => SeatsSelectionScreen(
                            departureCity: widget.departureCity,
                            arrivalCity: widget.arrivalCity,
                            selectedDate: day,
                          ),
                        ),
                      );

                      if (result != null) {
                        // Retourne à la fois la date et le nombre de places sélectionnées
                        Navigator.pop(context, result);
                      }
                    },
              child: Container(
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  color:
                      isSelected ? const Color(0xFF4052EE) : Colors.transparent,
                ),
                child: Text(
                  '${day.day}',
                  style: TextStyle(
                    color: isPastDay
                        ? Colors.grey.shade300
                        : isSelected
                            ? Colors.white
                            : isToday
                                ? const Color(0xFF4052EE)
                                : Colors.black,
                    fontWeight: isToday ? FontWeight.bold : null,
                  ),
                ),
              ),
            );
          },
        ),
        const SizedBox(height: 16),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
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
                          child: Text(
                            '${widget.departureCity?.libelle} > ${widget.arrivalCity?.libelle}',
                            style: const TextStyle(
                              fontSize: 16,
                              color: Colors.grey,
                              fontWeight: FontWeight.w500,
                            ),
                            overflow: TextOverflow.ellipsis,
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
            ),
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: Text(
                'Quand souhaites-tu partir?',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: List.generate(7, (index) {
                final weekdays = [
                  'lun.',
                  'mar.',
                  'mer.',
                  'jeu.',
                  'ven.',
                  'sam.',
                  'dim.'
                ];
                return SizedBox(
                  width: 40,
                  child: Text(
                    weekdays[index],
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      color: Colors.black54,
                      fontSize: 12,
                    ),
                  ),
                );
              }),
            ),
            Expanded(
              child: ListView.builder(
                controller: _scrollController,
                itemCount: _months.length,
                itemBuilder: (context, index) {
                  return _buildMonthCalendar(_months[index]);
                },
              ),
            ),
          ],
        ),
      ),
    );
  }

  bool isSameDay(DateTime a, DateTime b) {
    return a.year == b.year && a.month == b.month && a.day == b.day;
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }
}
