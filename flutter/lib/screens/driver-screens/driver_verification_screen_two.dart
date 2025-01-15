import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';

const Color primaryBlue = Color(0xFF4052EE);

class DriverVerificationScreenTwo extends StatefulWidget {
  const DriverVerificationScreenTwo({super.key});

  @override
  State<DriverVerificationScreenTwo> createState() => _DriverVerificationScreenState();
}

class _DriverVerificationScreenState extends State<DriverVerificationScreenTwo> {
  File? _idCardImage;
  bool _isUploading = false;
  final ImagePicker _picker = ImagePicker();

  Future<void> _pickImage() async {
    try {
      final XFile? pickedFile = await _picker.pickImage(
        source: ImageSource.gallery,
        maxWidth: 1920,
        imageQuality: 85,
      );

      if (pickedFile != null) {
        setState(() {
          _idCardImage = File(pickedFile.path);
        });
      }
    } catch (e) {
      if (!mounted) return;
      
      // Show error message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Impossible d\'accéder à la galerie photos. Vérifiez vos paramètres de confidentialité.'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  Future<void> _submitVerification() async {
    if (_idCardImage == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Veuillez ajouter votre carte d\'identité'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    setState(() {
      _isUploading = true;
    });

    // TODO: Implement backend upload
    await Future.delayed(const Duration(seconds: 2));

    setState(() {
      _isUploading = false;
    });

    if (!mounted) return;
    Navigator.pop(context);
  }

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
          'Vérification Conducteur',
          style: TextStyle(
            color: Colors.black,
            fontSize: 22,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Documents requis',
              style: TextStyle(
                fontSize: 28,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
            const SizedBox(height: 16),
            const Text(
              'Pour des raisons de sécurité, veuillez soumettre l\'un des documents suivants :',
              style: TextStyle(fontSize: 16, height: 1.5),
            ),
            const SizedBox(height: 24),
            
            // Accepted documents list
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.grey[100],
                borderRadius: BorderRadius.circular(15),
              ),
              child: Column(
                children: const [
                  ListTile(
                    leading: Icon(Icons.check_circle, color: primaryBlue),
                    title: Text('Carte Nationale d\'Identité'),
                  ),
                  ListTile(
                    leading: Icon(Icons.check_circle, color: primaryBlue),
                    title: Text('Passeport'),
                  ),
                  ListTile(
                    leading: Icon(Icons.check_circle, color: primaryBlue),
                    title: Text('Permis de conduire'),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 32),
            
            // Upload preview
            if (_idCardImage != null)
              Container(
                margin: const EdgeInsets.only(bottom: 24),
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.grey[100],
                  borderRadius: BorderRadius.circular(15),
                ),
                child: Column(
                  children: [
                    ClipRRect(
                      borderRadius: BorderRadius.circular(12),
                      child: Image.file(_idCardImage!),
                    ),
                    const SizedBox(height: 16),
                    TextButton.icon(
                      onPressed: _pickImage,
                      icon: const Icon(Icons.edit, color: primaryBlue),
                      label: const Text(
                        'Modifier la photo',
                        style: TextStyle(color: primaryBlue),
                      ),
                    ),
                  ],
                ),
              ),
            
            // Action button
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _isUploading ? null : (_idCardImage != null ? _submitVerification : _pickImage),
                style: ElevatedButton.styleFrom(
                  backgroundColor: primaryBlue,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                ),
                child: _isUploading
                  ? const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(
                        color: Colors.white,
                        strokeWidth: 2,
                      ),
                    )
                  : Text(
                      _idCardImage == null ? 'Ajouter une photo' : 'Soumettre pour vérification',
                      style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}