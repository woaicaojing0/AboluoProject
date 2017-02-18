package com.aboluo.adapter;

/**
 * Created by CJ on 2017/2/7.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aboluo.com.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 * Created by Jelly on 2016/3/10.
 */
public class BigImageViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public BigImageViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if (containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image, container, false);
            view.setTag(position);
            final ImageView image = (ImageView) view.findViewById(R.id.image);
            final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            if (isNumeric(images.get(position))) {
                Picasso.with(context).load(Integer.valueOf(images.get(position)))
                        .error(context.getResources().getDrawable(R.drawable.imageview_error))
                        .placeholder(context.getResources().getDrawable(R.drawable.imagviewloading))
                        .config(Bitmap.Config.RGB_565).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher.update();
                    }

                    @Override
                    public void onError() {
                        photoViewAttacher.update();
                    }
                });
            } else {
                Picasso.with(context).load(images.get(position))
                        .error(context.getResources().getDrawable(R.drawable.imageview_error))
                        .placeholder(context.getResources().getDrawable(R.drawable.imagviewloading))
                        .config(Bitmap.Config.RGB_565).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher.update();
                    }

                    @Override
                    public void onError() {
                        photoViewAttacher.update();
                    }
                });
            }
            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.onBackPressed();
                }
            });
            cacheView.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
