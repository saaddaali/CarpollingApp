class Conversation {
  final int id;
  // Add other fields based on your ConversationDto

  Conversation({
    required this.id,
    // Add other required fields
  });

  factory Conversation.fromJson(Map<String, dynamic> json) {
    return Conversation(
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