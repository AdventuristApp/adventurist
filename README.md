# FINAL PROJECT "Adventurist"

# Android studio app Group :

## Abdalkareem Barham
## Mohammad Abu Shannab 
## Ghofran Daradkah
## Rahaf Bedas
## Ghidaa Al Rowaad 


## Description of the App 
A travelling application that will help you to get all the remarkable places based on your location anywhere in the world,
it will recommend the best places to enjoy while having a trip , also it can create a plan for 
you to enjoy even more and guess what you can have heads up on the weather to where you are headed.

--------------------------------------------------------------


## FIGMA 
![figma.jpeg](screenshots%2Ffigma.jpeg)
### link : https://www.figma.com/file/WYnDijZ2nJzB9IGYjk5iFv/Untitled?type=design&node-id=0-1&mode=design&t=itriFZYMNSnASnIe-0

--------------------------------------------------------------
## User story :
User Story Sentence: As a user, I want to create a profile on the traveling application to personalize my experience and access personalized recommendations.
Feature Tasks:
Implement user registration and login functionality.
Collect basic user information during registration (e.g., name, email, profile picture).
Acceptance Tests:
Users can successfully register and log in.
User profile information is accurately displayed.
Location-Based Recommendations

User Story Sentence: As a traveler, I want the application to recommend restaurants and hotels based on my current location.
Feature Tasks:
Integrate a location tracking system.
Implement algorithms to recommend nearby restaurants and hotels.
Acceptance Tests:
Recommendations are relevant to the user's current location.
The user receives timely and accurate suggestions.
Trip Planning

User Story Sentence: As a user, I want to create and manage travel plans within the application.
Feature Tasks:
Provide a user-friendly interface for creating travel plans.
Allow users to add destinations, dates, and activities to their plans.
Acceptance Tests:
Users can successfully create, edit, and delete travel plans.
Travel plans are displayed in a clear and organized manner.
Photo Gallery

User Story Sentence: As a traveler, I want to capture and share my experiences by adding photos to my profile.
Feature Tasks:
Implement a photo upload feature within the user profile.
Allow users to categorize photos and add captions.
Acceptance Tests:
Users can upload photos to their profiles.
Photos are displayed with relevant details and captions.
Personalized Recommendations

User Story Sentence: As a user, I want the application to learn about my preferences and provide personalized recommendations for restaurants and hotels.
Feature Tasks:
Implement a recommendation engine based on user behavior and feedback.
Allow users to rate and review recommended places.
Acceptance Tests:
Recommended places align with the user's preferences.
User feedback influences future recommendations.
-----------------------------------------------------------------------------------------------------
## Doman modeling :
![domain modeling.jpeg](screenshots%2Fdomain%20modeling.jpeg)

## Schema :
![schema.jpeg](screenshots%2Fschema.jpeg)



## relationships between tables :
1. no    
2. yes (one user ) >> have >> (many plans)
3. no


## User Collection:
Property: userID

Data Type: Integer
Associated Collection: Trip (via user's trips)
Property: username

Data Type: String
Property: email

Data Type: String
Trip Collection:
Property: tripID

Data Type: Integer
Associated Collection: User (via trip's user)
Property: locations

Data Type: List of Location
Associated Collection: Location
Location Collection:
Property: locationID

Data Type: Integer
Property: latitude

Data Type: Double
Property: longitude

Data Type: Double
Property: photos

Data Type: List of Photo
Associated Collection: Photo
Photo Collection:
Property: photoID

Data Type: Integer
Property: imageURL

Data Type: String
Property: location

Data Type: Location
Associated Collection: Location

## ERD
![erd.png](screenshots%2Ferd.png)


## Software Requirement :
[SOFTWAREREADME.md](SOFTWAREREADME.md)