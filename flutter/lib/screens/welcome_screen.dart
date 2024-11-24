import 'package:flutter/material.dart';

class WelcomeScreen extends StatelessWidget {
  const WelcomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    const Color primaryBlue = Color(0xFF3B5998);
    const Color accentBlue = Color(0xFF4A90E2);
    const Color slogan = Color(0xFF6B8AFF);

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            Container(
              decoration: const BoxDecoration(
                image: DecorationImage(
                  image: AssetImage('assets/images/car2.jpg'),
                  fit: BoxFit.cover,
                ),
              ),
            ),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 24),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 24),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      // Titre principal
                      Row(
                        children: [
                          Text(
                            'Welcome to CarPool',
                            style: TextStyle(
                              fontSize: 30,
                              fontWeight: FontWeight.w700,
                              color: primaryBlue,
                              letterSpacing: -0.3,
                              height: 1.2,
                              shadows: [
                                Shadow(
                                  color: Colors.black.withOpacity(0.1),
                                  blurRadius: 10,
                                  offset: const Offset(0, 2),
                                ),
                              ],
                            ),
                          ),
                          const SizedBox(width: 12),
                          const Icon(
                            Icons.waving_hand,
                            color: accentBlue,
                            size: 30,
                          ),
                        ],
                      ),
                      const SizedBox(height: 12),
                      // Slogan sur une seule ligne
                      Text.rich(
                        TextSpan(
                          style: TextStyle(
                            fontSize: 17,
                            fontWeight: FontWeight.w500,
                            color: slogan,
                            letterSpacing: 0.2,
                            shadows: [
                              Shadow(
                                color: Colors.black.withOpacity(0.08),
                                blurRadius: 8,
                                offset: const Offset(0, 1),
                              ),
                            ],
                          ),
                          children: const [
                            TextSpan(text: 'Share Your Ride'),
                            TextSpan(
                              text: ' • ',
                              style: TextStyle(
                                color: Color(0xFF9FB4FF),
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            TextSpan(text: 'Save The Planet'),
                            TextSpan(
                              text: ' • ',
                              style: TextStyle(
                                color: Color(0xFF9FB4FF),
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            TextSpan(text: 'Connect Together'),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                const Spacer(),
                // Bouton
                Padding(
                  padding: const EdgeInsets.fromLTRB(24, 0, 24, 24),
                  child: Container(
                    width: double.infinity,
                    height: 58,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      gradient: const LinearGradient(
                        begin: Alignment.centerLeft,
                        end: Alignment.centerRight,
                        colors: [
                          accentBlue,
                          Color(0xFF6AA8F8),
                        ],
                      ),
                      boxShadow: [
                        BoxShadow(
                          color: accentBlue.withOpacity(0.25),
                          blurRadius: 15,
                          offset: const Offset(0, 6),
                          spreadRadius: -3,
                        ),
                      ],
                    ),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(30),
                      child: Material(
                        color: Colors.transparent,
                        child: InkWell(
                          onTap: () {
                            Navigator.pushReplacementNamed(context, '/signin');
                          },
                          splashColor: Colors.white.withOpacity(0.2),
                          highlightColor: Colors.white.withOpacity(0.1),
                          child: const Center(
                            child: Text(
                              'Let\'s Go!',
                              style: TextStyle(
                                color: Colors.white,
                                fontSize: 18,
                                fontWeight: FontWeight.w600,
                                letterSpacing: 0.5,
                              ),
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
