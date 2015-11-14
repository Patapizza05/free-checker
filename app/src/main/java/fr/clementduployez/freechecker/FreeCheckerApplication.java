package fr.clementduployez.freechecker;

import android.app.Application;
import android.content.Context;

/**
 * Created by cdupl on 11/14/2015.
 */
public class FreeCheckerApplication extends Application {
    private static Context sContext;

    public void onCreate(){
        super.onCreate();

        // Keep a reference to the application context
        sContext = getApplicationContext();
    }

    // Used to access Context anywhere within the app
    public static Context getContext() {
        return sContext;
    }
}
