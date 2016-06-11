package com.neil.dailyzhihu.utils.db.catalog.a;

import android.content.Context;

/**
 * Created by Neil on 2016/4/26.
 */
public class DBFactory {
    private static IDBSimpleStoryTabledao sIDBSimpleStoryTabledao;
    private static IDBSpecialSimpleStoryTabledao sIDBSpecialSimpleStoryTabledao;
    private static IDBDetailStoryTabledao sIDBDetailStoryTabledao;
    private static IDBStarRecordDetailStoryTabledao sIIDBStarRecordDetailStoryTabledao;
    private static IDBSectionBeanTabledao sIDBSectionBeanTabledao;
    private static IDBShareRecordDetailStoryTabledao sIDBShareRecordDetailStoryTabledao;
    private static IDBSubscribSectionTabledao sIDBSubscribSectionTabledao;

    public static IDBSubscribSectionTabledao getsIDBSubscribSectionTabledao(Context context) {
        if (sIDBSubscribSectionTabledao == null) {
            synchronized (IDBSubscribSectionTabledao.class) {
                if (sIDBSubscribSectionTabledao == null) {
                    sIDBSubscribSectionTabledao = new IDBSubscribSectionTabledao(context);
                }
            }
        }
        return sIDBSubscribSectionTabledao;
    }

    public static IDBShareRecordDetailStoryTabledao getsIDBShareRecordDetailStoryTabledao(Context context) {
        if (sIDBShareRecordDetailStoryTabledao == null) {
            synchronized (IDBShareRecordDetailStoryTabledao.class) {
                if (sIDBShareRecordDetailStoryTabledao == null) {
                    sIDBShareRecordDetailStoryTabledao = new IDBShareRecordDetailStoryTabledao(context);
                }
            }
        }
        return sIDBShareRecordDetailStoryTabledao;
    }

    public static IDBSectionBeanTabledao getsIDBSectionBeanTabledao(Context context) {
        if (sIDBSectionBeanTabledao == null) {
            synchronized (IDBSectionBeanTabledao.class) {
                if (sIDBSectionBeanTabledao == null) {
                    sIDBSectionBeanTabledao = new IDBSectionBeanTabledao(context);
                }
            }
        }
        return sIDBSectionBeanTabledao;
    }

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
                if (sIDBSpecialSimpleStoryTabledao == null) {
                    sIDBSpecialSimpleStoryTabledao = new IDBSpecialSimpleStoryTabledao(context);
                }
            }
        }
        return sIDBSpecialSimpleStoryTabledao;
    }

    public static IDBDetailStoryTabledao getIDBDetailStoryTabledao(Context context) {
        if (sIDBDetailStoryTabledao == null) {
            synchronized (IDBDetailStoryTabledao.class) {
                if (sIDBDetailStoryTabledao == null) {
                    sIDBDetailStoryTabledao = new IDBDetailStoryTabledao(context);
                }
            }
        }
        return sIDBDetailStoryTabledao;
    }

    public static IDBStarRecordDetailStoryTabledao getIIDBStarRecordDetailStoryTabledao(Context context) {
        if (sIIDBStarRecordDetailStoryTabledao == null) {
            synchronized (IDBStarRecordDetailStoryTabledao.class) {
                if (sIIDBStarRecordDetailStoryTabledao == null) {
                    sIIDBStarRecordDetailStoryTabledao = new IDBStarRecordDetailStoryTabledao(context);
                }
            }
        }
        return sIIDBStarRecordDetailStoryTabledao;
    }
}
