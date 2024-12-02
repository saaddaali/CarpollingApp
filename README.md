# Carpooling Application

A simple web application that helps connect drivers with passengers to share rides and reduce transportation costs.

## Description
This carpooling platform allows users to:
- Post available rides as a driver
- Search and book rides as a passenger
- View ride history and manage bookings
- Communicate with other users

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
   cd ../backend
   npm install
   ```

4. Set up environment variables:
   - Create a `.env` file in the backend directory
   - Add necessary environment variables (database connection, API keys, etc.)

5. Start the development servers:
   ```bash
   # Start backend server
   cd backend
   npm run start

   # Start frontend server (in a new terminal)
   cd frontend
   npm run start
   ```

The application should now be running on:
- Frontend: http://localhost:4200
- Backend: http://localhost:8036


