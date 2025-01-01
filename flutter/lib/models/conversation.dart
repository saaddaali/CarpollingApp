class Conversation {
  final int id;
  final String name;
  final String lastMessage;
  final String avatar;
  final DateTime lastMessageTime;
  final bool isUnread;

  Conversation({
    required this.id,
    required this.name,
    required this.lastMessage,
    required this.avatar,
    required this.lastMessageTime,
    required this.isUnread,
  });

  factory Conversation.fromJson(Map<String, dynamic> json) {
    return Conversation(
      id: json['id'],
      name: json['name'],
      lastMessage: json['lastMessage'],
      avatar: json['avatar'],
      lastMessageTime: DateTime.parse(json['lastMessageTime']),
      isUnread: json['isUnread'],

    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'lastMessage': lastMessage,
      'avatar': avatar,
      'lastMessageTime': lastMessageTime.toIso8601String(),
      'isUnread': isUnread,
      
    };
  }
} 