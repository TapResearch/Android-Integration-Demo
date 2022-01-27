# TapResearch Android Integration demo app

This simple app demonstrates how to integrate the TapResearch SDK in your app.

* Clone the repo

~~~~~~bash
$ git clone git@github.com:TapResearch/Android-Integration-Demo.git
~~~~~~

* Start Android Studio select File -> Open -> Android-Integration-Demo folder 

* If you want to see the app in action make sure you add your Android api token and a user identifier in `MainApplication.java` or  `TokenModule.java`

~~~~java

  @Override
   public void onCreate() {
       super.onCreate();
       //initializer via repository
       tapResearchRepository.initialize();
       // or initialize with configure
       TapResearch.configure("<TAPRESEARCH_API_TOKEN>", this);
       TapResearch.getInstance().setUniqueUserIdentifier("<USER_IDENTIFIER>");
   }

~~~~

Please send all questions, concerns, or bug reports to developers@tapresearch.com.
