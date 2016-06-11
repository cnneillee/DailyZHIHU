package com.neil.dailyzhihu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Neil on 2016/4/19.
 */
public class StorageOperatingHelper {
    private static final String PACKAGE_DIR = "/com.neil.dailyzhihu";
    private static final String IMAGE_DIR = "/image";
    private static final String QR_IMAGE_DIR = "/qrimage";
    private static final String FAVORITE_STORY_DIR = "/image/favorite";
    private static final String STORY_IMG_DIR = "/image/storyimg";

    public static String savingFavoriteStoryBitmap2SD(Context context, Bitmap bm, String storyId) {
        if (bm == null) return null;
        String fileName = storyId + ".png";
        File file = createFile(FAVORITE_STORY_DIR, fileName);
        if (file == null)
            return null;
        return excuteBM2File(file, bm);
    }

    public static String savingStoryImgBitmap2SD(Context context, Bitmap bm, String storyId) {
        if (bm == null) return null;
        String fileName = storyId + ".png";
        File file = createFile(STORY_IMG_DIR, fileName);
        if (file == null)
            return null;
        return excuteBM2File(file, bm);
    }

    private static String excuteBM2File(File file, Bitmap bm) {
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    private static boolean isExternalStorageAvailable() {
        String exStorageState = Environment.getExternalStorageState();
        if (!exStorageState.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    private static File getExternalStorageDir() {
        if (!isExternalStorageAvailable())
            return Environment.getExternalStorageDirectory();
        return null;
    }

    private static File getPkgExternalDir() {
        if (!isExternalStorageAvailable()) {
            File pkgdir = new File(getExternalStorageDir().getAbsolutePath(), PACKAGE_DIR);
            if (!(pkgdir.exists() && pkgdir.isDirectory())) {
                pkgdir.mkdirs();
            }
            return pkgdir;
        }
        return null;
    }

    /**
     * 相对于pkgDir
     *
     * @param relativeDirName 形如："/image"
     * @return
     */
    private static File getCertainExternalDir(String relativeDirName) {
        File pkgExternalDir = getPkgExternalDir();
        if (pkgExternalDir == null)
            return null;
        File certainDir = new File(pkgExternalDir.getAbsolutePath(), relativeDirName);
        if (!(certainDir.exists() && certainDir.isDirectory())) {
            if (certainDir.mkdirs())
                return certainDir;
        } else {
            return certainDir;
        }
        return null;
    }

    private static File createFile(String dirName, String fileName) {
        File dir = getCertainExternalDir(dirName);
        if (dir == null) {
            return null;
        }
        File file = new File(dir, fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile())
                    return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
