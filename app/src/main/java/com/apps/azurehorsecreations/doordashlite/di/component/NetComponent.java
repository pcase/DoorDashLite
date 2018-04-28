package com.apps.azurehorsecreations.doordashlite.di.component;

/**
 * Created by pattycase on 4/27/18.
 */

import com.apps.azurehorsecreations.doordashlite.di.module.AppModule;
import com.apps.azurehorsecreations.doordashlite.di.module.NetModule;
import javax.inject.Singleton;
import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
