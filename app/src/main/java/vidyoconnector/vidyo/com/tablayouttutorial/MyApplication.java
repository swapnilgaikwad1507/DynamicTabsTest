package vidyoconnector.vidyo.com.tablayouttutorial;

import android.app.Application;

import vidyoconnector.vidyo.com.tablayouttutorial.dependencies.SharedPreferencesModule;
import vidyoconnector.vidyo.com.tablayouttutorial.dependencies.components.DaggerMyComponent;
import vidyoconnector.vidyo.com.tablayouttutorial.dependencies.components.MyComponent;

public class MyApplication extends Application {
    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        myComponent = DaggerMyComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(getApplicationContext()))
                .build();
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }
}
