package vidyoconnector.vidyo.com.tablayouttutorial.dependencies.components;

import dagger.Component;
import vidyoconnector.vidyo.com.tablayouttutorial.MainActivity;
import vidyoconnector.vidyo.com.tablayouttutorial.dependencies.SharedPreferencesModule;

@Component(modules = SharedPreferencesModule.class)
@MyApplicationScope
public interface MyComponent {
    void inject(MainActivity mainActivity);
}
