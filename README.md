# Pedometer App

This is a simple Android application that uses the device's built-in step counter sensor to count the user's steps. It also includes user authentication using Firebase.

## Features

1. **User Authentication**: The app includes user registration and login functionality. Firebase Authentication is used for this purpose.

2. **Step Counter**: The app uses the device's built-in step counter sensor to count the user's steps. The total steps are displayed on the main screen.

3. **Reset Steps**: The user can reset the step count by long pressing on the step count displayed.

## Classes

1. **Register**: This class handles the user registration process. It includes fields for email and password input, a register button, and a progress bar. Upon successful registration, the user is redirected to the Login screen.

2. **Login**: This class handles the user login process. It includes fields for email and password input, a login button, and a progress bar. Upon successful login, the user is redirected to the Main screen.

3. **MainActivity**: This is the main screen of the app where the step count is displayed. It includes a progress bar that fills up as the user takes more steps, a step count display, and a logout button. The step count can be reset by long pressing on the step count display.

## Usage

1. Clone the repository.
2. Open the project in Android Studio.
3. Run the app on an emulator or an actual device.

## Dependencies

1. Firebase Authentication
2. Android's Sensor and SensorManager APIs

## Note

This app requires a device with a built-in step counter sensor to function properly.
