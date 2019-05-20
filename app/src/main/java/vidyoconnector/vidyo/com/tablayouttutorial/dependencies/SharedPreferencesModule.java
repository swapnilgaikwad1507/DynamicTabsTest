package vidyoconnector.vidyo.com.tablayouttutorial.dependencies;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import vidyoconnector.vidyo.com.tablayouttutorial.dependencies.components.MyApplicationScope;

@Module
public class SharedPreferencesModule {

    private Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    @MyApplicationScope
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("MyData",Context.MODE_PRIVATE);
    }
}
