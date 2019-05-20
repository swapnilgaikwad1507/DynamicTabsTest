package vidyoconnector.vidyo.com.tablayouttutorial.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putStringData(String key, String data) {
        mSharedPreferences.edit().putString(key,data).apply();
    }

    public String getStringData(String key,String mByDefaultKey) {
        return mSharedPreferences.getString(key,mByDefaultKey);
    }
}