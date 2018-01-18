package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.sxmoc.bq.R;
import com.sxmoc.bq.adapter.TagAdapter;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.FlowTagLayout;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.OrderGotoeeva;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

public class PingJiaActivity extends ZjbBaseActivity implements View.OnClickListener {

    private int id;
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
        ((TextView)findViewById(R.id.textViewTitle)).setText("评价");
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnTiJiao).setOnClickListener(this);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
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
            params.put("tokenTime",tokenTime);
        }
        params.put("oid",String.valueOf(id));
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(PingJiaActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("PingJiaActivity--onSuccess",s+ "");
                try {
                    OrderGotoeeva orderGotoeeva = GsonUtils.parseJSON(s, OrderGotoeeva.class);
                    if (orderGotoeeva.getStatus()==1){
                        tagAdapter = new TagAdapter(PingJiaActivity.this);
                        flowTagLayout.setAdapter(tagAdapter);
                        List<OrderGotoeeva.FlagBean> flagBeanList = orderGotoeeva.getFlag();
                        tagAdapter.clearAndAddAll(flagBeanList);
                        flowTagLayout.clearAllOption();
                        List<OrderGotoeeva.GoodsBean> goodsBeanList = orderGotoeeva.getGoods();
                        if(goodsBeanList.size()>0){
                            GlideApp.with(PingJiaActivity.this)
                                    .asBitmap()
                                    .circleCrop()
                                    .load(goodsBeanList.get(0).getImg())
                                    .placeholder(R.mipmap.ic_empty)
                                    .into(imageGood);
                            textGoodName.setText(goodsBeanList.get(0).getTitle());
                            textPrice.setText("¥"+goodsBeanList.get(0).getPrice());
                        }
                        viewPingJia.setVisibility(View.VISIBLE);
                        btnTiJiao.setVisibility(View.VISIBLE);
                    }else if (orderGotoeeva.getStatus()==3){
                        MyDialog.showReLoginDialog(PingJiaActivity.this);
                    }else {
                        Toast.makeText(PingJiaActivity.this, orderGotoeeva.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PingJiaActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
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
        switch (view.getId()){
            case R.id.btnTiJiao:
                List<Integer> allSelect = flowTagLayout.getAllSelect();
                for (int i = 0; i < allSelect.size(); i++) {
                    LogUtil.LogShitou("PingJiaActivity--onClick", ""+allSelect.get(i));
                }
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
}
