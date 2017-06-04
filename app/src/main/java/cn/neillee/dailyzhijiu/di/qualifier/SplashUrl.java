package cn.neillee.dailyzhijiu.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：codeest on 2017/2/26 22:12.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface SplashUrl {

}
