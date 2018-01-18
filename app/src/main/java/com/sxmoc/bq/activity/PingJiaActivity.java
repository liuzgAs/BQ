package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxmoc.bq.R;
import com.sxmoc.bq.adapter.TagAdapter;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.FlowTagLayout;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.OrderGotoeeva;
import com.sxmoc.bq.model.PingJiaTiJiao;
import com.sxmoc.bq.model.RespondAppimgadd;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.ImgToBase64;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.MD5Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PingJiaActivity extends ZjbBaseActivity implements View.OnClickListener {

    private int id;
    private int imgId;
    private View viewPingJia;
    private Button btnTiJiao;
    private SimpleRatingBar ratingbar;
    private ImageView imageGood;
    private TextView textGoodName;
    private TextView textPrice;
    private EditText editPingLun;
    private ImageView imageAdd;
    private FlowTagLayout flowTagLayout;
    private TagAdapter tagAdapter;
    private OrderGotoeeva orderGotoeeva;
    private List<OrderGotoeeva.FlagBean> flagBeanList;
    private String cutPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_jia);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 0);
    }

    @Override
    protected void findID() {
        viewPingJia = findViewById(R.id.viewPingJia);
        btnTiJiao = (Button) findViewById(R.id.btnTiJiao);
        imageGood = (ImageView) findViewById(R.id.imageGood);
        textGoodName = (TextView) findViewById(R.id.textGoodName);
        textPrice = (TextView) findViewById(R.id.textPrice);
        ratingbar = (SimpleRatingBar) findViewById(R.id.ratingbar);
        editPingLun = (EditText) findViewById(R.id.editPingLun);
        imageAdd = (ImageView) findViewById(R.id.imageAdd);
        flowTagLayout = (FlowTagLayout) findViewById(R.id.flowTagLayout);
    }

    @Override
    protected void initViews() {
        viewPingJia.setVisibility(View.GONE);
        btnTiJiao.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.textViewTitle)).setText("评价");
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnTiJiao).setOnClickListener(this);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        imageAdd.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_GOTOEEVA;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("oid", String.valueOf(id));
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(PingJiaActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("PingJiaActivity--onSuccess", s + "");
                try {
                    orderGotoeeva = GsonUtils.parseJSON(s, OrderGotoeeva.class);
                    if (orderGotoeeva.getStatus() == 1) {
                        tagAdapter = new TagAdapter(PingJiaActivity.this);
                        flowTagLayout.setAdapter(tagAdapter);
                        flagBeanList = orderGotoeeva.getFlag();
                        tagAdapter.clearAndAddAll(flagBeanList);
                        flowTagLayout.clearAllOption();
                        List<OrderGotoeeva.GoodsBean> goodsBeanList = orderGotoeeva.getGoods();
                        if (goodsBeanList.size() > 0) {
                            GlideApp.with(PingJiaActivity.this)
                                    .asBitmap()
                                    .circleCrop()
                                    .load(goodsBeanList.get(0).getImg())
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageGood);
                            textGoodName.setText(goodsBeanList.get(0).getTitle());
                            textPrice.setText("¥" + goodsBeanList.get(0).getPrice());
                        }
                        viewPingJia.setVisibility(View.VISIBLE);
                        btnTiJiao.setVisibility(View.VISIBLE);
                    } else if (orderGotoeeva.getStatus() == 3) {
                        MyDialog.showReLoginDialog(PingJiaActivity.this);
                    } else {
                        Toast.makeText(PingJiaActivity.this, orderGotoeeva.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PingJiaActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(PingJiaActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
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
            params.put("tokenTime", tokenTime);
        }
        params.put("code", "comment");
        params.put("img", ImgToBase64.toBase64(path));
        params.put("type", "png");
        return new OkObject(params, url);
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
            cutPath = selectList1.get(0).getCompressPath();
            GlideApp.with(PingJiaActivity.this)
                    .asBitmap()
                    .load(cutPath)
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageAdd);
        }
    }

    /**
     * 上传图片
     */
    private void upLoad() {
        showLoadingDialog();
        ApiClient.post(PingJiaActivity.this, getOkObject(cutPath), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("GeRenXXActivity--上传图片", s + "");
                try {
                    RespondAppimgadd respondAppimgadd = GsonUtils.parseJSON(s, RespondAppimgadd.class);
                    if (respondAppimgadd.getStatus() == 1) {
                        imgId = respondAppimgadd.getImgId();
                        pingJia();
                    } else if (respondAppimgadd.getStatus() == 3) {
                        MyDialog.showReLoginDialog(PingJiaActivity.this);
                    } else {
                        Toast.makeText(PingJiaActivity.this, respondAppimgadd.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PingJiaActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(PingJiaActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageAdd:
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
                        .enableCrop(false)
                /*是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false*/
                        .showCropFrame(false)
                /*是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false*/
                        .showCropGrid(false)
                /*圆形裁剪*/
                        .circleDimmedLayer(false)
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
                break;
            case R.id.btnTiJiao:
                if (TextUtils.isEmpty(editPingLun.getText().toString().trim())) {
                    Toast.makeText(PingJiaActivity.this, "说点什么吧~~~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cutPath)){
                    pingJia();
                }else {
                    upLoad();
                }
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private String getPingJiaOkObject() {
        List<Integer> allSelect = new ArrayList<>();
        for (int i = 0; i < flowTagLayout.getAllSelect().size(); i++) {
            allSelect.add(flagBeanList.get(i).getFid());
        }
        PingJiaTiJiao pingJiaTiJiao = new PingJiaTiJiao(MD5Util.getMD5Time(),
                1,
                "android",
                userInfo.getUid(),
                tokenTime,
                id,
                orderGotoeeva.getGoods().get(0).getId(),
                editPingLun.getText().toString().trim(),
                ratingbar.getNumberOfStars() - 1,
                imgId,
                allSelect
        );
        return GsonUtils.parseObject(pingJiaTiJiao);
    }

    /**
     * 评价
     */
    private void pingJia() {
        showLoadingDialog();
        String url = Constant.HOST + Constant.Url.ORDER_ADDEEVA;
        ApiClient.postJson(PingJiaActivity.this, url, getPingJiaOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("PingJiaActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Constant.BroadcastCode.SHUA_XIN_PING_JIA);
                        sendBroadcast(intent);
                        finish();
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(PingJiaActivity.this);
                    } else {
                        Toast.makeText(PingJiaActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PingJiaActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(PingJiaActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
