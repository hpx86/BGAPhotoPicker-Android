package cn.bingoogolapple.photopicker.imageloader;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/25 下午6:03
 * 描述:
 */
public class BGAXUtilsImageLoader implements BGAImageLoader {

    @Override
    public void displayImage(final ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final DisplayDelegate delegate) {
        x.Ext.init((Application) imageView.getContext().getApplicationContext());

        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }

        ImageOptions options = new ImageOptions.Builder()
                .setLoadingDrawableId(loadingResId)
                .setFailureDrawableId(failResId)
                .setSize(width, height)
                .build();

        final String finalPath = path;
        x.image().bind(imageView, finalPath, options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                if (delegate != null) {
                    delegate.onSuccess(imageView, finalPath);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void downloadImage(Context context, String path, final DownloadDelegate delegate) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }

        final String finalPath = path;
        x.image().loadDrawable(finalPath, new ImageOptions.Builder().build(), new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                if (delegate != null) {
                    delegate.onSuccess(finalPath, ((BitmapDrawable) result).getBitmap());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (delegate != null) {
                    delegate.onFailed(finalPath);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

}