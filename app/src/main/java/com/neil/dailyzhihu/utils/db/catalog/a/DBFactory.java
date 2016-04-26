package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.Context;

/**
 * Created by Neil on 2016/4/26.
 */
public class DBFactory {
    private static IDBSimpleStoryTabledao sIDBSimpleStoryTabledao;
    private static IDBSpecialSimpleStoryTabledao sIDBSpecialSimpleStoryTabledao;

    public static IDBSimpleStoryTabledao getsIDBSimpleStoryTabledao(Context context) {
        if (sIDBSimpleStoryTabledao == null) {
            synchronized (IDBSimpleStoryTabledao.class) {
                if (sIDBSimpleStoryTabledao == null) {
                    sIDBSimpleStoryTabledao = new IDBSimpleStoryTabledao(context);
                }
            }
        }
        return sIDBSimpleStoryTabledao;
    }

    public static IDBSpecialSimpleStoryTabledao getsIDBSpecialSimpleStoryTabledao(Context context) {
        if (sIDBSpecialSimpleStoryTabledao == null) {
            synchronized (IDBSpecialSimpleStoryTabledao.class) {
                if (sIDBSimpleStoryTabledao == null) {
                    sIDBSpecialSimpleStoryTabledao = new IDBSpecialSimpleStoryTabledao(context);
                }
            }
        }
        return sIDBSpecialSimpleStoryTabledao;
    }
}
