package com.example.medicalservice.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.medicalservice.R;


public class GlideUtils {

    public static void load(Context context,
                            String url,
                            ImageView imageView) {

        RequestOptions options = new RequestOptions();


        Glide.with(context)
                .load(url)
                .apply(options)

                .into(imageView);
    }


    public static void load(Context context,
                            String url,
                            ImageView imageView
            , int p, int radius) {

        RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(ViewUtils.dp2px(context, radius)));


        if (radius == 90) {
            options = new RequestOptions().circleCropTransform();
        }

        Glide.with(context)
                .load(url)
                .apply(options)
                .placeholder(p)
                .into(imageView);
    }


    public static void loadingPressBar(
            String url,
            ImageView imageView) {

        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)  //用内存缓存

                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有图片(原图,转换图)
                .fitCenter()   //fitCenter 缩放图片充满ImageView CenterInside大缩小原(图) CenterCrop大裁小扩充满ImageView  Center大裁(中间)小原
                ;

        Glide.with(imageView)
                .load(url)
                .apply(options)
                .thumbnail(Glide.with(imageView).load(R.drawable.donwload_photo))
                .into(imageView);
    }


}
