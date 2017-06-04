package cn.neillee.dailyzhijiu.utils.storage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * 针对com.neil.dailyzhi/image/storyimg目录下大图的操作类
 *
 * @author Neil Lee
 * @since 1.0.0
 */
public class ImageExternalDirectoryUtil {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String STORY_IMG_DIR_NAME = "storyimg/";
    private static final String QR_CODE_IMG_DIR_NAME = "qrcodeimg/";
    private static final String CERTAIN_DIR_PARENT_NAME = "image/";

    private static UtilType currentUtilType;

    private ImageExternalDirectoryUtil() {
    }

    /**
     * 将bitmap写入文件certainDirsectory文件夹中
     *
     * @param context  上下文
     * @param bitmap   要保存的Bitmap对象
     * @param storyId  storyId
     * @param quality  照片质量（0-100）
     * @param utilType 保存到文件夹的类型
     * @return <ture：保存成功></><false：保存失败></>
     */
    public static boolean saveImgIntoCertainImgDirectory(Context context, Bitmap bitmap, int storyId, int quality, UtilType utilType) {
        File certainImgDir = getCertainImgDirectory(context, utilType);
        if (certainImgDir == null) return false;
        File file = new File(certainImgDir, storyId + ".png");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    L.e("Can't create file");
                    return false;
                }
            } catch (IOException e) {
                L.e("Can't create file---IOException");
                return false;
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
            fos.close();
        } catch (IOException e) {
            L.e("IOException when compress Bitmap");
            return false;
        }
        return true;
    }

    /**
     * 返回com.neil.dailyzhi/image/XXXimg目录File，并且在目录中添加.nomedia文件
     *
     * @param context 上下文环境
     * @return com.neil.dailyzhi/image/XXXimg目录File
     */
    private static File getCertainImgDirectory(Context context, UtilType utilType) {
        if (utilType == null) {
            L.e("(utilType==null) doesn't match any UtilType");
            return null;
        }
        currentUtilType = utilType;
        File certainImgDir = null;
        String certainDirName;
        if (currentUtilType.equals(UtilType.QR_CODE_IMG)) certainDirName = QR_CODE_IMG_DIR_NAME;
        else certainDirName = STORY_IMG_DIR_NAME;
        String externalStorageState;
        externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            certainDirName = CERTAIN_DIR_PARENT_NAME + certainDirName;
            certainImgDir = new File(new File(Environment.getExternalStorageDirectory(), context.getPackageName()), certainDirName);
            if (!certainImgDir.exists()) {
                if (!certainImgDir.mkdir()) {
                    L.w("Unable to create external cache directory");
                    return null;
                }
                try {
                    new File(certainImgDir, ".nomedia").createNewFile();
                } catch (IOException e) {
                    L.i("Can't create \".nomedia\" file in application external cache directory");
                }
            }
        }
        return certainImgDir;
    }

    public static Bitmap getBitmap(Context context, int storyId) {
        File file = getImgFileFromCertainImgDirectory(context, storyId + "", UtilType.STORY_IMG);
        if (file == null || !file.exists() || !file.isFile()) return null;
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 返回CertainImgDirectory【com.neil.dailyzhi/image/XXXimg】目录中特定文件名的文件
     *
     * @param context  上下文对象
     * @param fileName 所查找的文件的文件名
     * @return 符合条件的第一个文件
     */
    public static File getImgFileFromCertainImgDirectory(Context context, final String fileName, UtilType utilType) {
        File filtedFiles[] = filteredImgFromCertainImgDirectory(context, FilterType.STRICT_FILE_NAME, fileName, utilType);
        if (filtedFiles == null || filtedFiles.length <= 0) {
            L.i("Can't find fileName(%s)file");
            return null;
        }
        if (filtedFiles.length > 1) {
            L.w("There are multiple files of fileName(%s) in directory");
        }
        return filtedFiles[0];
    }

    /**
     * 获得所有图片
     *
     * @param context 上下文
     * @return 所有图片文件数组
     */
    public static File[] getAllImgFileFromCertainImgDirectory(Context context, UtilType utiltype) {
        return filteredImgFromCertainImgDirectory(context, FilterType.ENDS, ".png", utiltype);
    }

    private static File[] filteredImgFromCertainImgDirectory(Context context, final FilterType filterType, final String matchedWord, UtilType utilType) {
        File fileDir = getCertainImgDirectory(context, utilType);
        if (fileDir == null) {
            L.w("Unable to get StoryImgDirectory!");
            return null;
        }
        File filtedFiles[] = fileDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filterType.equals(FilterType.CONTAINS_FILE_NAME)) {
                    return filename.contains(matchedWord);
                }
                if (filterType.equals(FilterType.STRICT_FILE_NAME)) {
                    return filename.equals(matchedWord);
                }
                if (filterType.equals(FilterType.ENDS)) {
                    return filename.endsWith(matchedWord);
                }
                L.w("No match filterType of (%s)", filterType);
                return false;
            }
        });
        return filtedFiles;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    public static enum FilterType {
        /**
         * 后缀
         */
        ENDS,
        /**
         * 严格的名字匹配
         */
        STRICT_FILE_NAME,
        /**
         * 文件名中包含关键字
         */
        CONTAINS_FILE_NAME
    }

    public static enum UtilType {
        /**
         * storyimg
         */
        STORY_IMG,
        /**
         * qrcodeimg
         */
        QR_CODE_IMG
    }
}
