# Carpooling Application

A web & mobile application that helps connect drivers with passengers to share rides and reduce transportation costs.

## Description
This carpooling platform allows users to:
- Post available rides as a driver
- Search and book rides as a passenger
- View ride history and manage bookings
- Communicate with other users

## Fonctionnalités
🔐 Authentification (connexion/inscription)
🚗 Publication de trajets (conducteur)
🔍 Recherche de trajets (passager)
💬 Chat entre utilisateurs
💳 Paiements sécurisés (Stripe)
📱 Interface responsive
🌍 Localisation en français

## Architechture
```plaintext
carpooling/
├── frontend/           # Frontend code 
├── backend-ms1/        # Backend
├── flutter/            # Flutter mobile application
└── deploy/             # Deployment scripts and configurations
```

## Prérequis

Java 17+
Node.js & npm
Angular CLI 18
MySQL 8
Maven 3.8+
Flutter SDK (version ^3.5.4)
Dart SDK
Android Studio / Xcode
VS Code (recommandé)

## Support

📱 Android
📱 iOS
🌐 Web


## Installation

1. Clone the repository
2. Navigate to the project directory:
   ```bash
   cd carpooling
   ```

3. Install dependencies for both frontend and backend:
   ```bash
   # Install frontend dependencies
   cd frontend
   npm install

   # Install backend dependencies
   cd ../backend-ms1
   npm install

   # Install Flutter dependencies
   cd ../flutter
   flutter pub get
   ```

4. Set up environment variables:
   - Create a `.env` file in the backend directory
   - Add necessary environment variables (database connection, API keys, etc.)

5. Start the development servers:
   ```bash
   # Start backend server
   cd backend-ms1
   npm run start

   # Start frontend server (in a new terminal)
   cd frontend
   npm run start

   # Start Flutter APP
   flutter run

   ```

The application should now be running on:
- Frontend: http://localhost:4200
- Backend: http://localhost:8036
  
Documentation API
Swagger UI: http://localhost:8036/swagger-ui.html



