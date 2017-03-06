package com.neil.dailyzhihu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;

/**
 * 对 Bitmap 的常见操作进行了封装
 */
public class BitmapUtil {

    private static final String LOG_TAG = BitmapUtil.class.getSimpleName();

    /**
     * 将图片从资源 Id 中读取成 Bitmap
     *
     * @param context 上下文对象
     * @param resId   资源 Id
     * @return 得到的 Bitmap
     */
    public static Bitmap readBitmapFromResId(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 按最大边按一定大小缩放图片
     */
    public static Bitmap scaleImage(byte[] buffer, float size) {
        // 获取原图宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);

        // 计算缩放比例（用最大边去缩放）
        float reSize = options.outWidth / size;
        if (options.outWidth < options.outHeight) reSize = options.outHeight / size;

        if (reSize <= 1) {// 如果是小图则放大
            int newWidth;
            int newHeight;
            if (options.outWidth > options.outHeight) {
                newWidth = (int) size;
                newHeight = options.outHeight * (int) size / options.outWidth;
            } else {
                newHeight = (int) size;
                newWidth = options.outWidth * (int) size / options.outHeight;
            }
//            bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            bm = scaleImage(bm, newWidth, newHeight);
            if (bm == null) Log.e(LOG_TAG, "convertToThumb, decode fail:" + null);
        } else {// 如果是大图则缩小
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) reSize;
            bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
            if (bm == null) Log.e(LOG_TAG, "convertToThumb, decode fail:" + null);
        }

        return bm;
    }

    /**
     * 按新的宽高缩放 Bitmap
     *
     * @param bm bitmap对象
     * @param newWidth 新的宽度
     * @param newHeight 新的高度
     * @return 缩放后的Bitmap
     */
    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight) {
        if (bm == null) return null;

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);

        if (!bm.isRecycled()) bm.recycle();

        return newBitmap;
    }

    /**
     * 检查图片是否超过一定值，是则缩小
     * @param buffer 字节数组
     * @param size 某个限定的值
     * @return 缩放后的 Bitmap
     */
    public static Bitmap convertToThumb(byte[] buffer, float size) {
        // 获取原图宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);

        // 计算缩放比例（用最小边去缩放）
        float reSize = options.outWidth / size;
        if (options.outWidth > options.outHeight) reSize = options.outHeight / size;
        if (reSize <= 0) reSize = 1;

        Log.d(LOG_TAG, "convertToThumb, reSize:" + reSize);
        // 缩放
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) reSize;
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            Log.e(LOG_TAG, "convertToThumb, recycle");
        }
        bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
        if (bm == null) Log.e(LOG_TAG, "convertToThumb, decode fail:" + null);

        return bm;
    }

    /**
     * 将 Bitmap 转化为字节数组
     * @param bmp bitmap对象
     * @return 字节数组
     */
    private static byte[] readBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 将字节数组转化为特定大小的字节数组
     * @param buffer 待缩放的字节数组
     * @param size 大小
     * @return 缩放后的字节数组
     */
    public static byte[] readBitmapFromBuffer(byte[] buffer, float size) {
        return readBitmap(convertToThumb(buffer, size));
    }

    /**
     * 以屏幕宽度为基准，显示图片
     * @param context 上下文对象
     * @param data 数据
     * @param size 大小
     * @return bitmap 对象
     */
    public static Bitmap decodeStream(Context context, Intent data, float size) {
        Bitmap image = null;
        try {
            Uri dataUri = data.getData();

            // 获取原图宽度
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(dataUri), null, options);

            // 计算缩放比例
            float reSize = (int) (options.outWidth / size);
            if (reSize <= 0) reSize = 1;
            Log.d(LOG_TAG, "old-w:" + options.outWidth + ", llyt-w:" + size + ", resize:" + reSize);

            // 缩放
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) reSize;
            image = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(dataUri), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param target   需要转化bitmap参数
     * @param newWidth 设置新的宽度
     * @return 转换后的 bitmap
     */
    public static Bitmap fitBitmap(Bitmap target, int newWidth) {
        if (target == null) return null;
        int width = target.getWidth();
        int height = target.getHeight();

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = scaleWidth * height;
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix, true);
        if (!target.equals(bmp) && !target.isRecycled()) target.recycle();

        return bmp;
    }

    /**
     * 根据指定的宽度平铺图像
     *
     * @param width 指定的宽度
     * @param src bitmap对象
     * @return 缩放后的bitmap
     */
    public static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, src.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    /**
     * 图片的质量压缩方法
     *
     * @param image bitmap对象
     * @return 处理后的bitmap对象
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int options = 100;
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);

        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;// 每次都减少10
        }

        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());

        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try {
            baos.close();
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!image.isRecycled()) image.recycle();

        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法(根据Bitmap图片压缩)
     *
     * @param image bitmap对象
     * @return 处理后的bitmap
     */
    public static Bitmap getImage(Bitmap image) {
        if (image == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩50%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        try {
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!image.isRecycled()) image.recycle();

        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);
    }

    /**
     * 通过资源id转化成Bitmap 全屏显示
     *
     * @param context
     * @param drawableId
     * @param screenWidth
     * @param screenHight
     * @return
     */
    public static Bitmap readBitmapFromResId(Context context, int drawableId, int screenWidth, int screenHight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inInputShareable = true;
        options.inPurgeable = true;
        InputStream stream = context.getResources().openRawResource(drawableId);
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        return scaleImage(bitmap, screenWidth, screenHight);
    }
}