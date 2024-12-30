class City {
  final int? id;
  final String code;
  final String libelle;
  final String description;

  City({
    this.id,
    required this.code,
    required this.libelle,
    required this.description,
  });

  City.empty()
      : id = null,
        code = '',
        libelle = '',
        description = '';

  factory City.fromJson(Map<String, dynamic> json) {
    return City(
      id: json['id'],
      code: json['code'] ?? '',
      libelle: json['libelle'] ?? '',
      description: json['description'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'code': code,
      'libelle': libelle,
      'description': description,
    };
  }

  // Getters for backward compatibility
  String get name => libelle;
  String get region => description;
}