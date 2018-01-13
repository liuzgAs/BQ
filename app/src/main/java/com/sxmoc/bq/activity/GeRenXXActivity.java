package com.sxmoc.bq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.RespondAppimgadd;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.model.UserProfile;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.ImgToBase64;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;


public class GeRenXXActivity extends ZjbBaseActivity implements View.OnClickListener {

    private ImageView imageHeadimg;
    private TextView textNickname;
    private TextView textRealName;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.USERINFO:
                    String username = intent.getStringExtra(Constant.IntentKey.NICKNAME);
                    if (!TextUtils.isEmpty(username)) {
                        textNickname.setText(username);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private TextView textSex;
    private TextView textBirthday;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren_xx);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        imageHeadimg = (ImageView) findViewById(R.id.imageHeadimg);
        textNickname = (TextView) findViewById(R.id.textNickname);
        textRealName = (TextView) findViewById(R.id.textRealName);
        textSex = (TextView) findViewById(R.id.textSex);
        textBirthday = (TextView) findViewById(R.id.textBirthday);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("个人信息");
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewNickName).setOnClickListener(this);
        findViewById(R.id.viewHeadImg).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getXXOkObject() {
        String url = Constant.HOST + Constant.Url.USER_PROFILE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(GeRenXXActivity.this, getXXOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("GeRenXXActivity--onSuccess", s + "");
                try {
                    userProfile = GsonUtils.parseJSON(s, UserProfile.class);
                    if (userProfile.getStatus() == 1) {
                        GlideApp.with(GeRenXXActivity.this)
                                .asBitmap()
                                .dontAnimate()
                                .load(userProfile.getHeadImg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageHeadimg);
                        textNickname.setText(userProfile.getNickname());
                        textRealName.setText(userProfile.getReal_name());
                        switch (userProfile.getSex()) {
                            case 0:
                                textSex.setText("男");
                                break;
                            case 1:
                                textSex.setText("女");
                                break;
                            default:
                                break;
                        }
                        textBirthday.setText(userProfile.getBirthday());
                    } else if (userProfile.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GeRenXXActivity.this);
                    } else {
                        Toast.makeText(GeRenXXActivity.this, userProfile.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(GeRenXXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(GeRenXXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            // 图片选择结果回调
            List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            String cutPath = selectList1.get(0).getCutPath();
            ApiClient.post(GeRenXXActivity.this, getOkObject(cutPath), new ApiClient.CallBack() {
                @Override
                public void onSuccess(String s) {
                    cancelLoadingDialog();
                    LogUtil.LogShitou("GeRenXXActivity--上传图片", s + "");
                    try {
                        RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                        if (respondAppimgadd.getStatus() == 1) {
                            edit("headimg",String.valueOf(respondAppimgadd.getImgId()));
                        } else if (respondAppimgadd.getStatus() == 3) {
                            MyDialog.showReLoginDialog(GeRenXXActivity.this);
                        } else {
                            Toast.makeText(GeRenXXActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(GeRenXXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError() {
                    cancelLoadingDialog();
                    Toast.makeText(GeRenXXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(String path) {
        String url = Constant.WEB_HOST + Constant.Url.RESPOND_APPIMGADD;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("code", "headimg");
        params.put("img", ImgToBase64.toBase64(path));
        params.put("type","png");
        return new OkObject(params, url);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getEditOkObject(String key,String value) {
        String url = Constant.HOST + Constant.Url.USER_SVAEINFO;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("key", key);
        params.put("value", value);
        return new OkObject(params, url);
    }
    /**
     * 修改
     */
    private void edit(String key,String value) {
        showLoadingDialog();
        ApiClient.post(GeRenXXActivity.this, getEditOkObject(key,value), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("GeRenXXActivity--修改头像", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        onStart();
                        Intent intent = new Intent();
                        intent.setAction(Constant.BroadcastCode.USERINFO);
                        sendBroadcast(intent);
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GeRenXXActivity.this);
                    } else {
                    }
                    Toast.makeText(GeRenXXActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(GeRenXXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(GeRenXXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * des： 选择图片
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:31
     */
    public void chooseHead() {
        PictureSelector.create(this)
                /*全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()*/
                .openGallery(PictureMimeType.ofImage())
                /* 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE*/
                .selectionMode(PictureConfig.SINGLE)
                /*是否显示拍照按钮 true or false*/
                .isCamera(true)
                /*拍照保存图片格式后缀,默认jpeg*/
                .imageFormat(PictureMimeType.PNG)
                /*glide 加载图片大小 0~1之间 如设置 .glideOverride()无效*/
                .sizeMultiplier(0.5f)
                /*是否允许裁剪*/
                .enableCrop(true)
                /*是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false*/
                .showCropFrame(false)
                /*是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false*/
                .showCropGrid(false)
                /*圆形裁剪*/
                .circleDimmedLayer(true)
                /*是否压缩 true or false*/
                .compress(true)
                /*裁剪压缩质量 默认90 int*/
                .cropCompressQuality(100)
                /*小于100kb的图片不压缩*/
                .minimumCompressSize(100)
                /*同步true或异步false 压缩 默认同步*/
                .synOrAsy(true)
                /*裁剪是否可旋转图片 true or false*/
                .rotateEnabled(true)
                /*裁剪是否可放大缩小图片 true or false*/
                .scaleEnabled(true)
                /*结果回调onActivityResult code*/
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewHeadImg:
                if (TextUtils.isEmpty(userProfile.getHeadImg())) {
                    chooseHead();
                } else {
                    MyDialog.choosePic(this);
                    MyDialog.setOnChoosePicListener(new MyDialog.OnChoosePicListener() {
                        @Override
                        public void chaKan() {
                            MyDialog.showPicDialog(GeRenXXActivity.this, userProfile.getHeadImg());
                        }

                        @Override
                        public void shangChuan() {
                            chooseHead();
                        }
                    });
                }
                break;
            case R.id.viewNickName:
//                Intent intent = new Intent();
//                intent.putExtra(Constant.IntentKey.VALUE, userProfile.getNickname());
//                intent.setClass(this, EditActivity.class);
//                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.USERINFO);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}