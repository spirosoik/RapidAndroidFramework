Rapid Android Development Template
==================================

## Tools
Android Studio 0.8.x and Gradle 2.x

## IDE Tools
* [Dagger Plugin] (https://github.com/square/dagger-intellij-plugin)
* [Android ButterKnife Zelezny] (http://plugins.jetbrains.com/plugin/7369)

## Libraries
* [Active Android 3.1.10](https://github.com/pardom/ActiveAndroid)
* [Retrofit 1.7.0](http://square.github.io/retrofit/)
* [OkHttp 2.0.0](http://square.github.io/okhttp/)
* [Otto Eventbus 1.3.5](http://square.github.io/otto/)
* [Dagger 1.2.2](http://square.github.io/dagger/)
* [ButterKnife 5.1.2](https://github.com/JakeWharton/butterknife)
* [Otto Picasso 2.3.2] (http://square.github.io/picasso/)
* [UI validation library Saripaar](https://github.com/ragunathjawahar/android-saripaar)

## 3rd Parties Social
* Facebook SDK as a git module

## Check the documentation

* [Wiki](https://github.com/spirosoik/RapidAndroidApp/wiki)


## Implemented

### Gradle
* Gradle configuration for multi-environment support

### Environment Manager
* Multi-environment support (```LIVE```, ```STAGING```, ```UAT```)
* Decides the environment for the current build
* Decides the app's base API URL for the current build
* Decides the app's API logging level for the current build
* Decides the app's logging level for the current build
* Decides if the app according to the environment can track data or not

### SQLite Database
* ```DBModel``` class to extend for each Object/Model you want to have access in database.
* Public methods for SQL operations (```findOne(Long id)```, ```findAll()```, ```deleteOne(Long id)``` ,```deleteAll```)
* Quick Methods for a new ```Select``` and ```Delete``` Query

### API Managers
* OkHttpClient support to make the HTTP requests for API
* ```AbstractApiManager``` with Retrofit based on the Environment manager API URL
* ```FULL/NONE``` logging for API based on the Environment manager ```LOGLEVEL``` of API
* AbstractAsyncTask which produces events
* Event Base architecture with Otto for each response after an ```AbstractAsyncTask```
* Access to API methods via Reflection. The only thing you must set up is the ```ApiRequestAttrs```. Example in wiki

### Activities
* Dagger for dependency injection
* ButterKnife injection to use this -> ```@InjectView(R.id.btnTest)``` to minimize and remove the old ```(Button) findViewById(R.id.btnTest)```
* Each Activity/FragmentActivity registered to listen the Event BUS
* Customizable/Overridable action bar for each Activity/FragmentActivity
* ```AbstractFragmentActivity``` to extended by each Fragment activity and it can contains the fragment you want. You must implement only the createFragment method
* ```BaseFragment``` to be extended by each fragment as generic fragment

### Google Analytics
* Support for google analytics track events and pageviews. Use ```AnalyticsManager```