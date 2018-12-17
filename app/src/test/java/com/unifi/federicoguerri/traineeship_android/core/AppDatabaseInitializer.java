package com.unifi.federicoguerri.traineeship_android.core;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseReactiveAndroid;

import org.robolectric.RuntimeEnvironment;

public class AppDatabaseInitializer {

    public static void initDatabase(){
        if(!ReActiveAndroid.isInitialized()) {
            ReActiveAndroid.init(new ReActiveConfig.Builder(RuntimeEnvironment.application)
                    .addDatabaseConfigs(new DatabaseConfig.Builder(DatabaseReactiveAndroid.class).addModelClasses(DatabasePrice.class)
                            .build())
                    .build());
        }
    }

    public static void destroyDatabase(){
        ReActiveAndroid.destroy();
    }
}
