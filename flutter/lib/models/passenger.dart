class Passenger {
  final int? id;
  final String? firstName;
  final String? lastName;
  final String username;
  final String email;
  final List<String>? roles;
  final String? accessToken;
  final String? tokenType;
  final bool? credentialsNonExpired;
  final bool? enabled;
  final bool? accountNonExpired;
  final bool? accountNonLocked;
  final bool? passwordChanged;
  final double? evaluation;

  Passenger({
    this.id,
    this.firstName,
    this.lastName,
    required this.username,
    required this.email,
    this.roles,
    this.accessToken,
    this.tokenType,
    this.credentialsNonExpired,
    this.enabled,
    this.accountNonExpired,
    this.accountNonLocked,
    this.passwordChanged,
    this.evaluation,
  });

  factory Passenger.fromJson(Map<String, dynamic> json) {
    return Passenger(
      id: json['id'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      username: json['username'] ?? '',
      email: json['email'] ?? '',
      roles: json['roles'] != null ? List<String>.from(json['roles']) : null,
      accessToken: json['accessToken'],
      tokenType: json['tokenType'],
      credentialsNonExpired: json['credentialsNonExpired'],
      enabled: json['enabled'],
      accountNonExpired: json['accountNonExpired'],
      accountNonLocked: json['accountNonLocked'],
      passwordChanged: json['passwordChanged'],
      evaluation: json['evaluation']?.toDouble(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (id != null) 'id': id,
      if (firstName != null) 'firstName': firstName,
      if (lastName != null) 'lastName': lastName,
      'username': username,
      'email': email,
      if (roles != null) 'roles': roles,
      if (accessToken != null) 'accessToken': accessToken,
      if (tokenType != null) 'tokenType': tokenType,
      if (credentialsNonExpired != null) 'credentialsNonExpired': credentialsNonExpired,
      if (enabled != null) 'enabled': enabled,
      if (accountNonExpired != null) 'accountNonExpired': accountNonExpired,
      if (accountNonLocked != null) 'accountNonLocked': accountNonLocked,
      if (passwordChanged != null) 'passwordChanged': passwordChanged,
      if (evaluation != null) 'evaluation': evaluation,
    };
  }

  // Helper method to get the full authentication token
  String? get authToken => accessToken != null && tokenType != null 
      ? '$tokenType $accessToken' 
      : null;

  // Create a copy of this passenger with modified fields
  Passenger copyWith({
    int? id,
    String? firstName,
    String? lastName,
    String? username,
    String? email,
    List<String>? roles,
    String? accessToken,
    String? tokenType,
    bool? credentialsNonExpired,
    bool? enabled,
    bool? accountNonExpired,
    bool? accountNonLocked,
    bool? passwordChanged,
    double? evaluation,
  }) {
    return Passenger(
      id: id ?? this.id,
      firstName: firstName ?? this.firstName,
      lastName: lastName ?? this.lastName,
      username: username ?? this.username,
      email: email ?? this.email,
      roles: roles ?? this.roles,
      accessToken: accessToken ?? this.accessToken,
      tokenType: tokenType ?? this.tokenType,
      credentialsNonExpired: credentialsNonExpired ?? this.credentialsNonExpired,
      enabled: enabled ?? this.enabled,
      accountNonExpired: accountNonExpired ?? this.accountNonExpired,
      accountNonLocked: accountNonLocked ?? this.accountNonLocked,
      passwordChanged: passwordChanged ?? this.passwordChanged,
      evaluation: evaluation ?? this.evaluation,
    );
  }
} 