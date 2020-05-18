# CS102 ~ Personal Log page ~
****
## Yaren DURGUN
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 20/04/2020 - 27/04/2020 ~
This week, I learned the essentials about Android Studio and how to use GitHub in accord with it. I also learned how layouts work in Android Studio and about pop-up windows. I practiced with some of what I have learnt, such as assigning the visual objects in the layouts a variable in the classes and then changing their attributes. It seems like the way things are designed in Android Studio, it is not practical to try and change layouts programatically. This might cause some complications in the future, though I hope not.

### ~ 27/04/2020 - 04/05/2020 ~
This week, I have started learning about Firebase with Sarper. I have hunted for 3D models on the internet to put into our Firebase database and found some good ones, so we have some good material to work with. Other than 3D models, I am responsible for designing anything to do with the user, such as authentication process (sign in, sign up etc.) and storing their other necessary information. I have created new classes and layouts for different actions. Unfortunately, as every layout is assigned a class, I could not merge all user-related methods under one simple class. So now I have SignUp, SignIn and Profile classes for allowing and tracking user actions and keeping user information stored (I have written all the necessary methods for each class and it looks like everything works pretty much perfectly!). Also, this week we have realized that our project in general does not seem to follow MVC pattern strictly and from our research we have learnt that Android might not be very compatible with MVC pattern. Still, we have brainstormed how to better organize our code and we have decided we might use inheritence and hiearchy more efficiently to make everything more organized.

### ~ 04/05/2020 - 11/05/2020 ~
At the beginning of the week, I have worked with Lara to implement inheritance for ARScreen class in order to avoid repetition in our code. Our second ARScreen class, which we have named ARScreenSession, is supposed to have some additional features that our first ARScreen class does not have. Since layouts are not inheritable, we were not sure what to do at the beginning. It pretty much looked like we should do everything we have done for ARScreen for ARScreenSession too, which defies the whole purpose if we need to write all the methods again. But then, we came up with the idea to add invisible and non-functional buttons and texts to ARScreen and then activate them in ARScreenSession. I have written the methods to change the visibility and enabled/disabled status of the buttons and texts. When all was done, it worked quite well so we have saved ourselves from a lot of repetition. Then, I have worked with Öykü on uploading videos to database and retrieving them when necessary. We have encountered the problem of transferring data from one class to another and possible solutions we have learned (inheritance, get methods) were of no use to solve this particular problem we were facing. Luckily, we have managed to overcome this obstacle by using bundles. Long story short, we are now able to record and upload videos to our database with the filename the user wants to use. Other than that, this week I have spent some time rendering our code more presentable and understandable. I have added comments and javadoc comments where necessary. Optimized imports and reorganized classes. I expect I will revise everything once more when the project is all finished.

### ~ 11/05/2020 - 18/05/2020 ~
-kept on organizing the code
-username feature
-retrieve video (watch video feature)

****
