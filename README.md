# projectar

Furniture shop with the possibility to test out how the products look in a room with augmented reality.


# Setup

1. Clone project
2. Run


# Known issues

Issues with bottom navigation bar, when navigating out of the AR-View. When tapping on a bottom app bar icon (home or search), the application always navigates to the home screen. This also crashed the application in rare cases, but we could not reproduce and fix. 

This is due to the not-so easily compatible Jetpack Compose + Sceneform. 

The application UI is built with Compose. The recommended navigation for composable layouts, which we opted to use, did not work as good as expected with sceneform. Currently, the application has two fragments. The first one hosts all the composable screens with its own navigation system, and the other hosts the AR-Fragment. This is the root of the issue. The application would need to use fragments rather than Compose navigation so that everything would work better with sceneform.


# Device requirements

1. Device must have AR-Functionality for the application to work (api +24)
