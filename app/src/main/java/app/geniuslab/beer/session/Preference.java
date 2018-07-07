package app.geniuslab.beer.session;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static final String PREFERENCE_NAME = "Beer";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static Preference preference;

    private static final String LOGIN = "login";
    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String PROFILEPICTURE = "profilePicture";

    public static Preference getIntance(Context context) {
        if (preference == null) {
            preference = new Preference(context);
        }
        return preference;
    }

    public Preference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean login) {
        editor.putBoolean(LOGIN, login).commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public String getName() {
        return sharedPreferences.getString(NAME, "");
    }

    public String getProfilePicture() {
        return sharedPreferences.getString(PROFILEPICTURE, "");
    }

    public void setUsername(String login) {
        editor.putString(USERNAME, login).commit();
    }

    public void setName(String login) {
        editor.putString(NAME, login).commit();
    }
    public void setProfilePicture(String login) {
        editor.putString(PROFILEPICTURE, login).commit();
    }
}

