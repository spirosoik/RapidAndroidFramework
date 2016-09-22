Rapid Android Development Template [NOT SUPPORTED]
==================================

**master:** [![Build Status](https://travis-ci.org/spirosoik/RapidAndroidFramework.svg?branch=master)](https://travis-ci.org/spirosoik/RapidAndroidFramework)

**Published in:** [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RapidAndroidFramework-green.svg?style=flat)](https://android-arsenal.com/details/1/2258)

## Tools
Android Studio 1.x and Gradle 2.x

## IDE Tools
* [Dagger Plugin] (https://github.com/square/dagger-intellij-plugin)
* [Android ButterKnife Zelezny] (http://plugins.jetbrains.com/plugin/7369)

## Libraries
* [Ollie 0.3.2-SNAPSHOT](https://github.com/pardom/Ollie)
* [Retrofit 1.9.0](http://square.github.io/retrofit/)
* [OkHttp 2.4.0](http://square.github.io/okhttp/)
* [Otto Eventbus 1.3.8](http://square.github.io/otto/)
* [Dagger 1.2.2](http://square.github.io/dagger/)
* [ButterKnife 6.1.0](https://github.com/JakeWharton/butterknife)
* [Otto Picasso 2.5.2] (http://square.github.io/picasso/)
* [UI validation library Saripaar](https://github.com/ragunathjawahar/android-saripaar)

## 3rd Parties Social (as gradle imports)
* Facebook SDK
* Twitter SDK by Fabric
* Crashlytics SDK by Fabric

Note: You can change the keys into ```api_keys.xml```. For Crashlytics you can change it into ```AndroidManifest.xml```. There is a ```SocialManager``` class
to use for social actions.

## Check the documentation

* [Wiki](https://github.com/spirosoik/RapidAndroidApp/wiki)

## Testing
* Robolectric for unit testing
* Shell scripts to run tests fast. Check ```scripts``` folder.
* CircleCI continuous integration server
* Calabash (SOON)

## Proguard
* Proguard enable for ```LIVE```, ```UAT``` environments to minify the build


### Gradle
* Gradle configuration for multi-environment support

### Environment Manager
* Multi-environment support (```LIVE```, ```UAT```, ```STAGING```)
* Assigns dynamically environment for the current build
* Assigns dynamically the API URL for the current build
* Assigns dynamically the API logging level for the current build
* Assigns dynamically the logging level for the current build
* Decides if the app according to the environment can track in analytics
* Decides if the app according to the environment can receive notifications
* Decides if the app according to the environment can track bugs in crashlytics

### API Managers
* OkHttpClient support to make the HTTP requests for API
* ```AbstractApiManager``` with Retrofit based on the Environment manager API URL
* ```FULL/NONE``` logging for API based on the Environment manager ```LOGLEVEL``` of API
* AbstractAsyncTask which produces events in a public BUS
* Event Base architecture with Otto for each response after an ```AbstractAsyncTask```
* Access to API methods via Reflection. The only thing you must set up is the ```ApiRequestAttrs```. Example in wiki

### Activities
* Dagger for dependency injection
* ButterKnife views injection to use this -> ```@InjectView(R.id.btnTest)``` to minimize and remove the old ```(Button) findViewById(R.id.btnTest)```
* Each Activity/FragmentActivity registered to listen the Event BUS
* Customizable/Overridable action bar for each Activity/FragmentActivity
* ```AbstractFragmentActivity``` to extended by each Fragment activity and it can contains the fragment you want. You must implement only the createFragment method
* ```BaseFragment``` to be extended by each fragment as generic fragment

### Google Analytics
* Support for google analytics track events and pageviews. Use ```AnalyticsManager```

License
-------

    Copyright 2015 Economakis Spyridon

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
