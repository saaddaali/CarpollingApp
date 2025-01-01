import 'package:flutter/material.dart';

class FAQScreen extends StatefulWidget {
  const FAQScreen({Key? key}) : super(key: key);

  @override
  State<FAQScreen> createState() => _FAQScreenState();
}

class _FAQScreenState extends State<FAQScreen> {
  static const Color primaryBlue = Color(0xFF4052EE);
  final List<FAQItem> faqItems = [
    FAQItem(
      question: 'Comment fonctionne le covoiturage ?',
      answer: 'Le covoiturage vous permet de partager des trajets avec d\'autres utilisateurs allant dans la même direction. Il suffit de rechercher les trajets disponibles ou de publier votre propre trajet.',
      icon: Icons.directions_car,
    ),
    FAQItem(
      question: 'Comment réserver un trajet ?',
      answer: 'Parcourez les trajets disponibles, sélectionnez celui qui correspond à votre itinéraire et cliquez sur "Réserver". Vous recevrez une confirmation une fois que le conducteur aura accepté.',
      icon: Icons.book_online,
    ),
    FAQItem(
      question: 'Le covoiturage est-il sûr ?',
      answer: 'Nous vérifions tous les utilisateurs et mettons en place des fonctionnalités de sécurité. Les utilisateurs sont évalués après chaque trajet, et vous pouvez consulter les profils avant de réserver.',
      icon: Icons.verified_user,
    ),
    FAQItem(
      question: 'Comment fonctionnent les paiements ?',
      answer: 'Les paiements sont traités en toute sécurité via l\'application. Le tarif est affiché avant la réservation et n\'est traité qu\'une fois le trajet terminé.',
      icon: Icons.payments,
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
        title: const Text(
          'Aide',
          style: TextStyle(
            color: Colors.black,
            fontSize: 28,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      body: ListView(
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 24),
        children: [
          const Text(
            'Questions fréquentes',
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: Colors.black87,
            ),
          ),
          const SizedBox(height: 24),
          ...faqItems.map((item) => _buildFAQItem(item)).toList(),
        ],
      ),
    );
  }

  Widget _buildFAQItem(FAQItem item) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Theme(
        data: Theme.of(context).copyWith(dividerColor: Colors.transparent),
        child: ExpansionTile(
          leading: Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              color: primaryBlue.withOpacity(0.1),
              shape: BoxShape.circle,
            ),
            child: Icon(
              item.icon,
              color: primaryBlue,
              size: 24,
            ),
          ),
          title: Text(
            item.question,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w500,
              color: Colors.black87,
            ),
          ),
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(16, 0, 16, 16),
              child: Text(
                item.answer,
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.grey[600],
                  height: 1.5,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class FAQItem {
  final String question;
  final String answer;
  final IconData icon;

  FAQItem({
    required this.question,
    required this.answer,
    required this.icon,
  });
}