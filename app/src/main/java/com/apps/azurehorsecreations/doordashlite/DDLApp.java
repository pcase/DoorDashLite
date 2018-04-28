package com.apps.azurehorsecreations.doordashlite;

import android.app.Application;

import com.apps.azurehorsecreations.doordashlite.di.component.DaggerNetComponent;
import com.apps.azurehorsecreations.doordashlite.di.component.NetComponent;
import com.apps.azurehorsecreations.doordashlite.di.module.AppModule;
import com.apps.azurehorsecreations.doordashlite.di.module.NetModule;

/**
 * Created by pattycase on 4/27/18.
 */

public class DDLApp extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.doordash.com/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}