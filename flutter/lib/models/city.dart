class City {
  final int id;
  final String code;
  final String libelle;
  final String? description;

  City({
    required this.id,
    required this.code,
    required this.libelle,
    this.description,
  });

  factory City.fromJson(Map<String, dynamic> json) {
    return City(
      id: json['id'] ?? 0,
      code: json['code'] ?? '',
      libelle: json['libelle'] ?? '',
      description: json['description'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'code': code,
      'libelle': libelle,
      if (description != null) 'description': description,
    };
  }

  // Getter pour la compatibilité avec le code existant
  String get name => libelle;
  String? get region => description;
}