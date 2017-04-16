package com.neil.dailyzhihu.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：codeest on 16/8/7 15:43.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
