package com.health.pusun.device.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.health.pusun.device.R;

/**
 * <Pre>
 *     glide图片加载工具类
 * </Pre>
 *
 * @version 1.0
 *  Created by jiangtao.ma 18/3/29
 */
public class GlideUtil {

    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .placeholder(null)
                .error(R.drawable.ic_launcher3)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}
