class Driver {
  final int id;
  final String username;
  final String photo;
  final String adresse;
  final DateTime dateInscription;
  final double evaluation;

  Driver({
    required this.id,
    required this.username,
    required this.photo,
    required this.adresse,
    required this.dateInscription,
    required this.evaluation,
  });

  factory Driver.fromJson(Map<String, dynamic> json) {
    return Driver(
      id: json['id'],
      username: json['username'],
      photo: json['photo'] ?? '',
      adresse: json['adresse'] ?? '',
      dateInscription: DateTime.parse(json['dateInscription']),
      evaluation: double.parse(json['evaluation'].toString()),
    );
  }
} 