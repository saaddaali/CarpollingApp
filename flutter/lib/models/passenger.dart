class Passenger {
  final String? firstName;
  final String? lastName;
  final String username;
  final String email;
  final List<String> roles;
  final String accessToken;
  final String tokenType;

  Passenger({
    this.firstName,
    this.lastName,
    required this.username,
    required this.email,
    required this.roles,
    required this.accessToken,
    required this.tokenType,
  });

  factory Passenger.fromJson(Map<String, dynamic> json) {
    return Passenger(
      firstName: json['firstName'],
      lastName: json['lastName'],
      username: json['username'],
      email: json['email'],
      roles: List<String>.from(json['roles']),
      accessToken: json['accessToken'],
      tokenType: json['tokenType'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'firstName': firstName,
      'lastName': lastName,
      'username': username,
      'email': email,
      'roles': roles,
      'accessToken': accessToken,
      'tokenType': tokenType,
    };
  }
} 