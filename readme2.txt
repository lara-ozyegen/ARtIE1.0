Group Number: g3F
Title: ARtIE (Augmented Reality to Improve Education)
Description: Our project, ARtIE, is based on displaying 3D models in real life to improve education and provide information with using visualization.

What has been done?

- We were able to finish our project with a few features missing such as our add model feature. We were able to get our models from database and insert them on the AR screen, get their info from realtime database, and much more features in AR screen like taking a video of the screen and uploading it to the database so you can get the video and watch it in our app.
- We used Android Studio 3.6.3 , and got help with AR screen methods using Google’s ARCore Library. For our database operations; we used Realtime Database, Storage and Authentication subbranches of the Firebase Database.
- To use our application, you have to either connect your android to your computer or use an emulator. We used Pixel 2 as emulator and we would suggest everyone to do so. After you press play Android Studio does the download/setup for you and opens the app. After the app opens, you can press the buttons and play around with models in free mode, to reach the session mode you have to sign up- sign in and then you are also free to experiment! 

What remains to be done?

- Currently, the difference between session mode and free mode is not so visible in our application, we are planning to fix that with adding the session ID and add model features - since we want free mode to be used without internet, we are planning to use only the models user added in free mode-
- We want to be able to assert a session ID to our videos and get the videos using the sessionID. This is also the reason why we have a child class of our AR Screen.
- Our “add model” feature gets the model but can’t download it to AR screen.
- Our search feature is not working, simply, we want to fix that.



Contributions

Berk Saltuk Yılmaz: Mostly worked on layouts, views and some features of AR screen such as painting and retrieving video (helped to fix this). Added visuals and features to screens, fixed paint feature. Created Help, AboutUs pages. Added some spacing, fixed some indentation and created a logo for our app! Created an transparent activity for painting feature and made it work. Also tried to help my friends about their parts since we are new to Android projects.

Lara Özyeğen: Mostly worked on the AR screen and its features like screenshot. In terms of database: I linked firebase with android studio, added methods for account and uploading/downloading the video to storage. To render our code more understandable I worked on MVC and applied inheritance to the AR screens. Also I made several views and layouts in the app. 

Sarper Turan: Mostly worked on AR screen and MVC pattern of AR screen/models. Worked on mostly storage part of database; getting models from database, getting model info from database. Worked on paint and screenshot features with my team members. Also worked on views/layouts and listeners and stuff. Also worked on retrieving the video we uploaded from database with my team members.

Öykü Erhan: Mostly worked on AR Screen features. Worked on capturing screenshots, recording video of paint and ar screen and downloading it to phone. Also worked on uploading videos and getting them from firebase database. Created toolbar and add that to pages. Also worked on creating some layouts.

Yaren Durgun: Mostly worked on database, authentication features such as creating a new account, logging in, logging off etc. For the storage part, attended to uploading and downloading video features. Worked on identifying and clearing the differences between two AR screen types by using hierarchy. Created and edited layouts & comments.

Enis Özer: I have worked on creating a session mode class by using the existing free mode class, helped on layouts and removing an AR object from the environment. I have also tried to help my friends on fixing and adding some features on paint class and adding a functionality to remove the existing AR object from the screen .

Onur Asım İlhan: Worked on how to create an AR screen and how to solve the errors that we got. Tried to attend the meetings and tried to help my friends with the errors that they got. However, due to some technical and daily life issues, I could not contribute to the project as much as my friends did.
