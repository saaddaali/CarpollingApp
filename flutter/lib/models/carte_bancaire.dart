class CarteBancaire {
  final int id;
  // Add other fields based on your CarteBancaireDto

  CarteBancaire({
    required this.id,
    // Add other required fields
  });

  factory CarteBancaire.fromJson(Map<String, dynamic> json) {
    return CarteBancaire(
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