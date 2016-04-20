package com.neil.dailyzhihu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Neil on 2016/4/19.
 */
public class StorageOperatingHelper {
    public static String savingBitmap2SD(Context context, Bitmap bm, String imgName) {
        if (bm == null)
            return null;
        String exStorageState = Environment.getExternalStorageState();
        if (!exStorageState.equals(Environment.MEDIA_MOUNTED))
            return null;
        String pkgName = context.getPackageName();
        File dir = Environment.getExternalStorageDirectory();
        if (!dir.exists())
            dir.mkdirs();
        File fileDir = new File(dir, pkgName);
        if (!fileDir.exists())
            fileDir.mkdirs();
//        File file = new File(fileDir, imgName + ".png");
        File file = new File(fileDir, System.currentTimeMillis() + ".png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
