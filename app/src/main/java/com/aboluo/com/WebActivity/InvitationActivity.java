package com.aboluo.com.WebActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by CJ on 2016/12/23.
 */

public class InvitationActivity extends Activity {
    private WebView invitation_webiview;
    private String MemberId;
    private ImageView invitation_back, invitation_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        MemberId = CommonUtils.GetMemberId(this);
        invitation_webiview = (WebView) findViewById(R.id.invitation_webiview);
        invitation_back = (ImageView) findViewById(R.id.invitation_back);
        invitation_share = (ImageView) findViewById(R.id.invitation_share);
        invitation_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        invitation_webiview.setVerticalScrollBarEnabled(false);
        invitation_webiview.setVerticalScrollbarOverlay(false);
        invitation_webiview.setHorizontalScrollBarEnabled(false);
        invitation_webiview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = invitation_webiview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webviewsetting.setLoadWithOverviewMode(true);
        Log.i("invitation>>", CommonUtils.GetValueByKey(this, "backUrl") + "/moblie/PersonShare?memberId=" + MemberId);
        invitation_webiview.loadUrl(CommonUtils.GetValueByKey(this, "backUrl") + "/moblie/PersonShare?memberId=" + MemberId);
        //capital_webiview.loadUrl("http://t.back.aboluomall.com/Moblie/ScoreLog?memberId" + MemberId);
        invitation_webiview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        invitation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        invitation_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShareSdk();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ShareSdk() throws PackageManager.NameNotFoundException {
        String detailurl0 = CommonUtils.GetValueByKey(this, "backUrl")
                + "/moblie/PersonShare?memberId=" + MemberId;
        Log.i("detailurl0",detailurl0);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("邀请码");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(detailurl0);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点击查看详情");
//        Context packageContext = this.createPackageContext(this.getPackageName(),
//                Context.CONTEXT_RESTRICTED);
//        Resources resources = packageContext.getResources();
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                "://" + resources.getResourcePackageName(R.drawable.ic_launcher) +
//                "/" + resources.getResourceTypeName(R.drawable.ic_launcher) + "/" +
//                resources.getResourceEntryName(R.drawable.ic_launcher));
//        Log.i("invitationimageurl",getRealPathFromUri(this,uri));
//        oks.setImagePath(getRealPathFromUri(this,uri));//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl(detailurl0);
        oks.setUrl(detailurl0);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(detailurl0);
        // 启动分享GUI
        oks.show(this);

    }

    /**
     * 根据图片的Uri获取图片的绝对路径(已经适配多种API)
     * @return 如果Uri对应的图片存在,那么返回该图片的绝对路径,否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wholeID = DocumentsContract.getDocumentId(uri);
        }

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = { MediaStore.Images.Media.DATA };
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = { id };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }
}
