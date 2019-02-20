
- Import

	    maven { url 'https://jitpack.io' }    // add build.gradle project

	    implementation 'com.github.duchoangitt:EzAds:1.0.5'  //add build.gradle app


- In Splash Screen

	    EzAdDialog.loadJson(this);

-  In OnBackPress


 		 new EzAdDialog(this) {
            @Override
            public void onAction(Action action, String apId) {
                if(action!=Action.SHOW)finish();
            }
 		 }.showDialog();

