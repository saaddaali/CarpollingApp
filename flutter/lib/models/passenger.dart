class Passenger {
  final int id;
  // Add other fields based on your PassengerDto

  Passenger({
    required this.id,
    // Add other required fields
  });

  factory Passenger.fromJson(Map<String, dynamic> json) {
    return Passenger(
      id: json['id'],
      // Map other fields
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      // Add other fields
    };
  }
} 