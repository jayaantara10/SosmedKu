package id.jayaantara.sosmedku.controller.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import id.jayaantara.sosmedku.DBHandler;
import id.jayaantara.sosmedku.PrefKey;

public class Auth {

    //    Variable
    private static Auth singletone = null;
    private long id_curent_user;
    private long pref_id_user;

    DBHandler dbHandler;

    //    Singletone Function
    public static Auth getInstance() {
        if (singletone == null)
            singletone = new Auth();

        return singletone;
    }

    //    Login Function
    public Auth login(Context context, String username, String password){
        Auth user = new Auth();
        dbHandler = new DBHandler(context);
        boolean result = dbHandler.checkUser(username, password);
        if(result == true){
            id_curent_user = dbHandler.getIdUser(username, password);
            setPreferenceCurentUser(context);
            return user;
        }
        return null;
    }

    //    Set Preference for save curent admin login Function
    private void setPreferenceCurentUser(Context context){
        SharedPreferences pref_curent_admin = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref_curent_admin.edit();

        editor.putLong(PrefKey.ID_CURENT_USER, id_curent_user);
        editor.apply();
    }

    //    Get preference for curent admin use for auto login Function
    public long getPreferenceCurentUser(Context context){
        SharedPreferences pref_curent_admin = PreferenceManager.getDefaultSharedPreferences(context);
        pref_id_user = pref_curent_admin.getLong(PrefKey.ID_CURENT_USER, -1);

        if(pref_id_user == -1){
            return 0;
        }

        long curent_user = pref_id_user;
        return curent_user;
    }

    //    Logout Function
    public void logout(Context context) {
        SharedPreferences _pref = PreferenceManager.getDefaultSharedPreferences(context);
        _pref.edit().remove(PrefKey.ID_CURENT_USER).apply();
    }
}
