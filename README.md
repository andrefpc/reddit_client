<img src="https://user-images.githubusercontent.com/4115436/134757279-da8c8ab1-d381-444f-836d-d595e48f4bb0.png" data-canonical-src="https://user-images.githubusercontent.com/4115436/134757279-da8c8ab1-d381-444f-836d-d595e48f4bb0.png" width="300" />


![](https://img.shields.io/badge/version-v1.0.0-blue)  ![](https://img.shields.io/badge/platform-android-red)

## Welcome
Reddit Client is an app to see the top posts of Reddit. 
The app was created to an assessment process.

## Setup

The app supports Light and Dark mode.

### Software Setup
-  [Android Studio (v4.1)](https://developer.android.com/studio?gclid=Cj0KCQiAjKqABhDLARIsABbJrGnu24-EGDCgj0GwU72bgw2QqhSlGUDbodJeBTlnr-_CGEPDQrZJZOcaArQ9EALw_wcB&gclsrc=aw.ds "Android Studio")

### API Reference

- [Reddit Api](https://www.reddit.com/dev/api/ "Reddit Api")
  - GET [https://www.reddit.com/top.json](https://www.reddit.com/top.json)

### Language

- Kotlin

### Architecture

- MVVM (Model View ViewModel)

### Dependency Injection

- [Koin](https://insert-koin.io/ "Koin")

### Multithreading

- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAjw7rWKBhAtEiwAJ3CWLKmcgJFMZLK1QyQwWwfd5_Oy7Da_YNByntiMwhAcQxbpwAbj9fqIORoCIWEQAvD_BwE&gclsrc=aw.ds "Coroutine")
- [LiveDatas](https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br "LiveDatas")  

### HTTP Client

- [Retrofit](https://square.github.io/retrofit/ "Retrofit")


### Testing Tools

- [Espresso](https://developer.android.com/training/testing/espresso "Espresso")
- JUnit
- Mockito

### Other Tools

- [Facebook Shimmer](https://facebook.github.io/shimmer-android/ "Facebook Shimmer") - loading animations.
- [Glide](http://bumptech.github.io/glide/ "Glide") - load remote images.
- [Gson](https://github.com/google/gson "Gson") - JSON serialization.
- [Material Design](https://material.io/develop/android/docs/getting-started "Material Design") - UI tools.

## Packages Structure
- 📔 **api** (Store the retrofit interfaces for do HTTP requests )
- 📔 **data** (Store the data objects )
- 📔 **di** (Store the dependecy injection setup files )
- 📔 **extensions** (Store the app extensions )
- 📔 **repository** (Store the repositories to call the api interfaces )
- 📔 **ui** (Store the UI files, like Activities, Fragments, Adapters and the ViewModels )
- 📔 **util** (Store the util classes )
- 📔 **widget** (Store the custom components )

## Assessment Requirements
- ✅ Pull to Refresh 
- ✅ Pagination support
- ✅ Saving pictures in the picture gallery
- ✅ App state-preservation/restoration
- ✅ Indicator of unread/read post (updated status, after post it’s selected)
- ✅ Dismiss Post Button (remove the cell from list. Animations required)
- ✅ Dismiss All Button (remove all posts. Animations required)
- ✅ Support split layout (left side: all posts / right side: detail post)

## Unit and Intrumented Tests

<img width="1112" alt="Captura de Tela 2021-09-24 às 23 48 56" src="https://user-images.githubusercontent.com/4115436/134757129-2af84e59-0487-4bda-a221-ac512d1ad97d.png">
<img width="1110" alt="Captura de Tela 2021-09-24 às 23 48 25" src="https://user-images.githubusercontent.com/4115436/134757132-3e1a67ff-04a6-4c36-bcb8-fa09f8f0c335.png">

https://user-images.githubusercontent.com/4115436/134757176-b3789fd9-1dca-4bbb-97fa-01311adec7f6.mp4

## Screen Recorders

https://user-images.githubusercontent.com/4115436/134757159-02db4689-8359-47ce-9492-a4412d7bc4de.mp4

https://user-images.githubusercontent.com/4115436/134757164-12de3f45-0543-40f4-9917-9b69e7193f32.mp4

https://user-images.githubusercontent.com/4115436/134757170-25d750e9-03ff-4b53-9ef7-8a00c65569a4.mp4






