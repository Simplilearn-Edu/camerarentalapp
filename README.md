# camerarentalapp
A Java CLI app for renting a camera
To set up the project you have to create two different files inside src folder with the following names - 
1. camera.txt - This file holds all the list of existing cameras for rent along with their brand, price and status.
2. mycamera.txt - This file holds the list of the cameras added by the user who is going to use the application.

Both the files can have list of cameras as shown in below format - 
CAMERA_ID:CAMERA_BRAND:MODEL_NO:PRICE_PER_DAY:STATUS,

Eg.
1:NIKON:DSLR1234:250.0:R,2:CANON:DSLR1234:250.0:A,............ N
In the above example, the list of camera is having information of 2 cameras. Where 1 is the ID, NIKON is the brand, DSLR1234 is the model number, 250.0 is the per day price of the camera. Status R means the camera is already given on Rent, and Status A means the camera is available.
