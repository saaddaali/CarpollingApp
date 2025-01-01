class Message {
  final String id;
  final String content;
  final String senderId;
  final DateTime timestamp;
  final bool isMe;

  Message({
    required this.id,
    required this.content,
    required this.senderId,
    required this.timestamp,
    required this.isMe,
  });
}