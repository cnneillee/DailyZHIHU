package com.neil.dailyzhihu.utils.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.neil.dailyzhihu.R;
import com.neil.dailyzhihu.utils.storage.ImageExternalDirectoryUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;

/**
 * 开源框架 Android-Universal-Image-Loader 的封装实现
 * <p/>
 * <link>https://github.com/nostra13/Android-Universal-Image-Loader</link>
 * Created by Clock on 2016/1/18.
 */
public class UniversalAndroidImageLoader implements ImageLoaderWrapper {

    private final static String HTTP = "http";
    private final static String HTTPS = "https";

    public UniversalAndroidImageLoader() {
    }

    @Override
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option) {
        int imageLoadingResId = R.mipmap.img_default;
        int imageErrorResId = R.mipmap.img_error;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true) //加载本地图片不需要再做SD卡缓存，只做内存缓存即可
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        String uri = ImageDownloader.Scheme.FILE.wrap(imageFile.getAbsolutePath());
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    // @Override
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option, File file) {
        int imageLoadingResId = R.mipmap.img_default;
        int imageErrorResId = R.mipmap.img_error;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true) //加载本地图片不需要再做SD卡缓存，只做内存缓存即可
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        String uri = ImageDownloader.Scheme.FILE.wrap(imageFile.getAbsolutePath());
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option) {
        int imageLoadingResId = R.mipmap.img_default;
        int imageErrorResId = R.mipmap.img_error;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
       /* if (imageUrl.startsWith(HTTPS)) {
            String uri = ImageDownloader.Scheme.HTTPS.wrap(imageUrl);
        } else if (imageUrl.startsWith(HTTP)) {
            String uri = ImageDownloader.Scheme.HTTP.wrap(imageUrl);
        }*/
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option, ImageLoadingListener listener) {
        int imageLoadingResId = R.mipmap.img_default;
        int imageErrorResId = R.mipmap.img_error;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, listener);
        /*if (imageUrl.startsWith(HTTPS)) {
            String uri = ImageDownloader.Scheme.HTTPS.wrap(imageUrl);
        } else if (imageUrl.startsWith(HTTP)) {
            String uri = ImageDownloader.Scheme.HTTP.wrap(imageUrl);
        }*/
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        int imageLoadingResId = R.mipmap.img_default;
        int imageErrorResId = R.mipmap.img_error;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        if (progressListener == null) {
            displayImage(imageView, imageUrl, option, listener);
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        if (imageUrl.startsWith(HTTPS)) {
            String uri = ImageDownloader.Scheme.HTTPS.wrap(imageUrl);
            ImageLoader.getInstance().displayImage(uri, imageView, options, listener, progressListener);
        } else if (imageUrl.startsWith(HTTP)) {
            String uri = ImageDownloader.Scheme.HTTP.wrap(imageUrl);
            ImageLoader.getInstance().displayImage(uri, imageView, options, listener, progressListener);
        }
    }

    @Override
    public void downloadImage(final Context context, String imageUrl, String imagePath, final int imageName, ImageLoadingListener listener) {
        ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //StorageOperatingHelper.savingStoryImgBitmap2SD(context, bitmap, imageName + "");
                ImageExternalDirectoryUtil.saveImgIntoCertainImgDirectory(context, bitmap, imageName, 100, ImageExternalDirectoryUtil.UtilType.STORY_IMG);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, null);
//        UniversalContentLoader.getRequestQueue(context).add(imageRequest);
    }

    /**
     * 初始化Universal-Image-Loader框架的参数设置
     *
     * @param context
     */
    public static void init(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        Md5FileNameGenerator md5FileNameGenerator = new Md5FileNameGenerator();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(md5FileNameGenerator);
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
