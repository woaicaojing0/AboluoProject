package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by CJ on 2016/10/25.
 */

public class EvaluationDetailImageActivity extends Activity implements View.OnClickListener{
    private PhotoView evaluation_bigimage;
    private PhotoViewAttacher photoViewAttacher;
    private TextView evaluation_text_delete;
    private ImageView evaluation_text_back;
    private String url ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail_image);
        init();
        Bundle bundle = getIntent().getExtras();
        url =bundle.getString("imgeurl");
        evaluation_bigimage = (PhotoView) findViewById(R.id.evaluation_bigimage);
        Picasso.with(this).load(new File(url)).placeholder(getResources().getDrawable(R.drawable.imagviewloading))
                .error(getResources().getDrawable(R.drawable.imageview_error))
                .into(evaluation_bigimage);
        photoViewAttacher = new PhotoViewAttacher(evaluation_bigimage);
        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                finish();
            }
        });
        evaluation_text_delete.setOnClickListener(this);
        evaluation_text_back.setOnClickListener(this);

    }

    private void init()
    {
        evaluation_text_delete = (TextView) findViewById(R.id.evaluation_text_delete);
        evaluation_text_back = (ImageView) findViewById(R.id.evaluation_text_back);
    }
    @Override
    public void onClick(View v) {
    switch (v.getId())
    {
        case R.id.evaluation_text_delete:
            Intent intent = new Intent();
            intent.putExtras(getIntent().getExtras());
            setResult(RESULT_OK,intent);
            finish();
            break;
        case R.id.evaluation_text_back:
            Intent intent2 = new Intent();
            setResult(RESULT_CANCELED,intent2);
            finish();
            break;
    }
    }
}
