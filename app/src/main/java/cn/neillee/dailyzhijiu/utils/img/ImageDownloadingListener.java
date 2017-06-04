package cn.neillee.dailyzhijiu.utils.img;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.FailReason;

/**
 * Listener for image downloading process.<br />
 * You can use {@link SimpleImageDownloadingListener} for implementing only needed methods.
 *
 * @author Neil Lee
 * @since 1.0.0
 */
public interface ImageDownloadingListener {
    /**
     * Is called when image downloading task was started
     *
     * @param imageUri  Downloading image URI
     * @param imagePath Path for image
     */
    void onDownloadingStarted(String imageUri, String imagePath);

    /**
     * Is called when image downloading task was failed
     *
     * @param imageUri   Downloading image URI
     * @param imagePath  Path for image
     * @param failReason {@linkplain com.nostra13.universalimageloader.core.assist.FailReason The reason} why image
     *                   loading was failed
     */
    void onDownloadingFailed(String imageUri, String imagePath, FailReason failReason);

    /**
     * Is called when image downloading task was completed
     *
     * @param imageUri        Downloaded image URI
     * @param imagePath       Path for image
     * @param downloadedImage Bitmap of loaded and decoded image
     */
    void onDownloadingCompleted(String imageUri, String imagePath, Bitmap downloadedImage);
}
