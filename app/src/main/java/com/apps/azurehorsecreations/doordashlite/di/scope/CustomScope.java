package com.apps.azurehorsecreations.doordashlite.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * CustomScope is needed because two dependent components cannot share the same scope.
 * Its used by MainScreenComponent and MainScreenModule.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomScope {
}
