class Driver {
  final int id;
  final String username;
  final String email;
  final String photo;
  final double evaluation;
  final bool enabled;
  final bool credentialsNonExpired;
  final bool accountNonExpired;
  final bool accountNonLocked;
  final bool passwordChanged;
  final bool verified;

  Driver({
    required this.id,
    required this.username,
    required this.email,
    required this.photo,
    required this.evaluation,
    required this.enabled,
    required this.credentialsNonExpired,
    required this.accountNonExpired,
    required this.accountNonLocked,
    required this.passwordChanged,
    required this.verified,
  });

  Driver.empty()
      : id = 3,
        username = 'passenger',
        email = 'passenger',
        photo = '',
        evaluation = 0.0,
        enabled = true,
        credentialsNonExpired = true,
        accountNonExpired = true,
        accountNonLocked = true,
        passwordChanged = false,
        verified = false;
      

  factory Driver.fromJson(Map<String, dynamic> json) {
    return Driver(
      id: json['id'] ?? 0,
      username: json['username'] ?? '',
      email: json['email'] ?? '',
      photo: json['photo'] ?? '',
      evaluation: (json['evaluation'] ?? 0.0).toDouble(),
      enabled: json['enabled'] ?? true,
      credentialsNonExpired: json['credentialsNonExpired'] ?? true,
      accountNonExpired: json['accountNonExpired'] ?? true,
      accountNonLocked: json['accountNonLocked'] ?? true,
      passwordChanged: json['passwordChanged'] ?? false,
      verified: json['verified'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'email': email,
      'photo': photo,
      'evaluation': evaluation,
      'enabled': enabled,
      'credentialsNonExpired': credentialsNonExpired,
      'accountNonExpired': accountNonExpired,
      'accountNonLocked': accountNonLocked,
      'passwordChanged': passwordChanged,
      'verified': verified,
    };
  }
}