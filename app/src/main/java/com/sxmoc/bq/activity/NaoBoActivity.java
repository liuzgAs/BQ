package com.sxmoc.bq.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.NaoBoTu;
import com.sxmoc.bq.customview.RoateImg;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.holder.LanYaViewHolder;
import com.sxmoc.bq.interfacepage.OnDaoJiShiJieShuListener;
import com.sxmoc.bq.model.BlueBean;
import com.sxmoc.bq.model.BuyerSavedata;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.TesterGetreport;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NaoBoActivity extends ZjbBaseActivity implements View.OnClickListener {
    private View[] viewJieMian = new View[6];
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<BlueBean> adapter;
    private String saoMiaoStatue = "正在搜索……";
    private int jieMian = 0;
    private NaoBoTu naoBo01;
    private NaoBoTu naoBo02;
    private TextView textLeftTime;
    private TextView textZuoNaoDis;
    private TextView textYouNaoDis;
    private RelativeLayout viewShangChuan;
    private RoateImg roateImg;
    private RelativeLayout viewDuQuBG;
    ObjectAnimator[] animator = new ObjectAnimator[5];
    ObjectAnimator[] animatorMusic = new ObjectAnimator[5];
    private TextView textShangChuanStatue;
    private int screenWidth;
    private int id;
    private LanYaViewHolder lanYaViewHolder;
    private BuyerSavedata buyerSavedata;
    ObjectAnimator animator01 = new ObjectAnimator();
    ObjectAnimator animator1 = new ObjectAnimator();
    private ImageView imageDaoJiShi;
    boolean isBack = true;
    private OnDaoJiShiJieShuListener onDaoJiShiJieShuListener;
    private RelativeLayout viewDaoJiShi;
    private RelativeLayout viewMusic;
    //    private int load80;
//    private int load40;
    private ImageView imageMusic;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nao_bo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        BleManager.getInstance().init(getApplication());
        init();
    }

    @Override
    protected void initSP() {
//        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        load80 = soundPool.load(this, R.raw.naobo80, 1);
//        load40 = soundPool.load(this, R.raw.naobo40, 1);
        mMediaPlayer = MediaPlayer.create(this, R.raw.zhunbei);
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 1);
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        viewJieMian[0] = findViewById(R.id.recyclerView);
        viewJieMian[1] = findViewById(R.id.viewNaoBo);
        naoBo01 = (NaoBoTu) findViewById(R.id.naoBo01);
        naoBo02 = (NaoBoTu) findViewById(R.id.naoBo02);
        textLeftTime = (TextView) findViewById(R.id.textLeftTime);
        textZuoNaoDis = (TextView) findViewById(R.id.textZuoNaoDis);
        textYouNaoDis = (TextView) findViewById(R.id.textYouNaoDis);
        viewShangChuan = (RelativeLayout) findViewById(R.id.viewShangChuan);
        viewDuQuBG = (RelativeLayout) findViewById(R.id.viewDuQuBG);
        viewJieMian[2] = findViewById(R.id.viewShangChuan);
        viewJieMian[3] = findViewById(R.id.viewDuQuBG);
        roateImg = (RoateImg) findViewById(R.id.roateImg);
        textShangChuanStatue = (TextView) findViewById(R.id.textShangChuanStatue);
        imageDaoJiShi = (ImageView) findViewById(R.id.imageDaoJiShi);
        viewJieMian[4] = (RelativeLayout) findViewById(R.id.viewDaoJiShi);
        viewDaoJiShi = (RelativeLayout) findViewById(R.id.viewDaoJiShi);
        viewJieMian[5] = (RelativeLayout) findViewById(R.id.viewMusic);
        viewMusic = (RelativeLayout) findViewById(R.id.viewMusic);
        imageMusic = (ImageView) findViewById(R.id.imageMusic);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initViews() {
        initRecycler();
        viewJieMian[1].setPadding(0, ScreenUtils.getStatusBarHeight(NaoBoActivity.this), 0, 0);

        screenWidth = ScreenUtils.getScreenWidth(NaoBoActivity.this);
        ImageView imageView1 = new ImageView(NaoBoActivity.this);
        imageView1.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewShangChuan.addView(imageView1, layoutParams1);
        for (int i = 0; i < 5; i++) {
            final ImageView imageView = new ImageView(NaoBoActivity.this);
            imageView.setImageResource(R.mipmap.jianbianquan);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewShangChuan.addView(imageView, layoutParams);
            if (i > 0) {
                imageView.setVisibility(View.GONE);
            }
            PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
            PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);
            PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            animator[i] = ObjectAnimator.ofPropertyValuesHolder(imageView, holder01, holder02, holder03);
            animator[i].setInterpolator(new LinearInterpolator());
            animator[i].setDuration(4000);
            animator[i].setStartDelay(0 + 1000 * i);
            animator[i].setRepeatCount(ValueAnimator.INFINITE);
            animator[i].setRepeatMode(ValueAnimator.INFINITE);
            animator[i].start();
            animator[i].addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    imageView.setVisibility(View.VISIBLE);
                }
            });
        }

        ImageView imageView3 = new ImageView(NaoBoActivity.this);
        imageView3.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.8f), (int) ((float) screenWidth * 0.8f));
        layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewDuQuBG.addView(imageView3, layoutParams3);
        imageView3.setAlpha(0.4f);

        ImageView imageView2 = new ImageView(NaoBoActivity.this);
        imageView2.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 1f), (int) ((float) screenWidth * 1f));
        layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewDuQuBG.addView(imageView2, layoutParams2);
        imageView2.setAlpha(0.2f);
        setJieMian(0);
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(NaoBoActivity.this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BlueBean>(NaoBoActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_lanya;
                lanYaViewHolder = new LanYaViewHolder(parent, layout);
                return lanYaViewHolder;
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textSouSuo;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(NaoBoActivity.this).inflate(R.layout.header_sousuo, null);
                textSouSuo = view.findViewById(R.id.textSouSuo);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                textSouSuo.setText(saoMiaoStatue);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(List<String> naoBoDataList) {
        String url = Constant.HOST + Constant.Url.BUYER_SAVEDATA;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("bid", id + "");
        List<String> naoBoDataList00 = new ArrayList<>();
        List<String> naoBoDataList01 = new ArrayList<>();
        List<String> naoBoDataList02 = new ArrayList<>();
        List<String> naoBoDataList03 = new ArrayList<>();
        List<String> naoBoDataList04 = new ArrayList<>();
        List<String> naoBoDataList05 = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            naoBoDataList00.add(naoBoDataList.get(i));
        }
        for (int i = 80; i < 160; i++) {
            naoBoDataList01.add(naoBoDataList.get(i));
        }
        for (int i = 160; i < 240; i++) {
            naoBoDataList02.add(naoBoDataList.get(i));
        }
        for (int i = 240; i < 320; i++) {
            naoBoDataList03.add(naoBoDataList.get(i));
        }
        for (int i = 320; i < 400; i++) {
            naoBoDataList04.add(naoBoDataList.get(i));
        }
        for (int i = 400; i < 480; i++) {
            naoBoDataList05.add(naoBoDataList.get(i));
        }
        String str00 = "";
        String str01 = "";
        String str02 = "";
        String str03 = "";
        String str04 = "";
        String str05 = "";
        for (int i = 0; i < 80; i++) {
            str00 = str00 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
        }
        for (int i = 80; i < 160; i++) {
            str01 = str01 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
        }
        for (int i = 160; i < 240; i++) {
            str02 = str02 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
        }
        for (int i = 240; i < 320; i++) {
            str03 = str03 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
        }
        for (int i = 320; i < 400; i++) {
            str04 = str04 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
        }
        for (int i = 400; i < 480; i++) {
            if (i == 479) {
                str05 = str05 + "\"" + naoBoDataList.get(i) + "\"";
            } else {
                str05 = str05 + "\"" + naoBoDataList.get(i) + "\"," + "\n";
            }
        }
        params.put("raw_data1", str00);
        params.put("raw_data2", str01);
        params.put("raw_data3", str02);
        params.put("raw_data4", str03);
        params.put("raw_data5", str04);
        params.put("raw_data6", str05);
        return new OkObject(params, url);
    }

    /**
     * 上传脑波数据
     *
     * @param naoBoDataList
     */
    private void upLoadData(List<String> naoBoDataList) {
        showLoadingDialog();
        ApiClient.post(NaoBoActivity.this, getOkObject(naoBoDataList), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("CeYiCeFragment--onSuccess", s + "");
                try {
                    buyerSavedata = GsonUtils.parseJSON(s, BuyerSavedata.class);
                    if (buyerSavedata.getStatus() == 1) {
                        shangChuangWanCheng();
                    } else if (buyerSavedata.getStatus() == 3) {
                        MyDialog.showReLoginDialog(NaoBoActivity.this);
                    } else {
                        Toast.makeText(NaoBoActivity.this, buyerSavedata.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(NaoBoActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(NaoBoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 上传完成
     */
    private void shangChuangWanCheng() {
        textShangChuanStatue.setText("上传完成");
        for (int i = 0; i < animator.length; i++) {
            animator[i].end();
        }
        roateImg.stopAnim();
        PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.6f);
        PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder01, holder03);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        PropertyValuesHolder holder04 = PropertyValuesHolder.ofFloat("scaleY", 0.6f, 1f);
        PropertyValuesHolder holder05 = PropertyValuesHolder.ofFloat("scaleX", 1.4f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder04, holder05);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setDuration(200);
        PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", -1500);
        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder02);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator, animator2, animator1);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setJieMian(3);
            }
        });
    }

    /**
     * 设置界面
     *
     * @param jieMianIndex
     */
    private void setJieMian(int jieMianIndex) {
        jieMian = jieMianIndex;
        for (int i = 0; i < viewJieMian.length; i++) {
            viewJieMian[i].setVisibility(View.GONE);
        }
        viewJieMian[jieMianIndex].setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.btnDuQuBG).setOnClickListener(this);
        findViewById(R.id.btnFangSongDN).setOnClickListener(this);
        findViewById(R.id.btnKaiShiCS).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setJieMian(0);
        showLoadingDialog();
        adapter.addAll(new ArrayList<BlueBean>());
        List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
        List<BlueBean> blueBeanList = new ArrayList<>();
        for (int i = 0; i < allConnectedDevice.size(); i++) {
            BlueBean blueBean = new BlueBean(allConnectedDevice.get(i), 0);
            blueBeanList.add(blueBean);
        }
        adapter.clear();
        adapter.addAll(blueBeanList);
        if (allConnectedDevice.size() == 0) {
            BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                    /**
                     * 只扫描指定广播名的设备，可选
                     */
                    .setDeviceName(true, Constant.bluetooth_name)
                    /**
                     * 扫描超时时间，可选，默认10秒
                     */
                    .setScanTimeOut(10000)
                    .build();
            BleManager.getInstance().initScanRule(scanRuleConfig);
            ThreadPoolHelp.Builder
                    .cached()
                    .builder()
                    .execute(new Runnable() {
                        @Override
                        public void run() {
                            startScan();
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private boolean isScan = false;

    /**
     * 开始扫描
     */
    private void startScan() {
        isScan = true;
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saoMiaoStatue = "正在搜索大脑雷达";
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {

            }

            @Override
            public void onScanFinished(final List<BleDevice> scanResultList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isScan = false;
                        cancelLoadingDialog();
                        saoMiaoStatue = "搜索完成";
                        List<BlueBean> blueBeanList = new ArrayList<>();
                        for (int i = 0; i < scanResultList.size(); i++) {
                            BlueBean blueBean = new BlueBean(scanResultList.get(i), 0);
                            blueBeanList.add(blueBean);
                        }
                        adapter.addAll(blueBeanList);
                        adapter.notifyDataSetChanged();
                        if (adapter.getAllData().size() == 0) {
                            MyDialog.dialogFinish(NaoBoActivity.this, "没有发现设备，请确认设备是否开机");
                        }
                    }
                });
            }
        });
    }

    /**
     * 刷新脑波图
     *
     * @param value01
     * @param value02
     */
    public void setNaoBo(int value01, int value02) {
        naoBo01.setNaoBoPoint(value01);
        naoBo02.setNaoBoPoint(value02);
        textZuoNaoDis.setText(String.valueOf(value01));
        textYouNaoDis.setText(String.valueOf(value02));
    }

    /**
     * 刷新脑波图
     */
    public void initNaoBo() {
        naoBo01.chuShiHua();
        naoBo02.chuShiHua();
        textZuoNaoDis.setText("512");
        textYouNaoDis.setText("512");
    }

    /**
     * 打开通知成功
     */
    public void success() {
        textLeftTime.setText("120");
        setJieMian(1);
    }

//    private SoundPool soundPool;

    /**
     * 刷新剩余时间
     *
     * @param leftTime
     */
    public void leftTime(int leftTime) {
        textLeftTime.setText(String.valueOf(120 - leftTime));


        if (leftTime == 80) {
//            soundPool.play(load40, 1, 1, 0, 0, 1);
            mMediaPlayer = MediaPlayer.create(this, R.raw.naobo40);
            mMediaPlayer.start();

        }
        if (leftTime == 40) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.naobo80);
            mMediaPlayer.start();
//            soundPool.play(load80, 1, 1, 0, 0, 1);
        }
    }

    private boolean isUpload = false;

    /**
     * 上传
     *
     * @param naoBoDataList
     */
    public void upLoad(List<String> naoBoDataList) {
        isUpload = true;
        upLoadData(naoBoDataList);
        setJieMian(2);
    }

    @Override
    public void onBackPressed() {
        if (jieMian == 1) {
            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(NaoBoActivity.this, "是否终止测试？", "是", "否");
            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void doConfirm() {
                    lanYaViewHolder.closeNotify();
                    setJieMian(0);
                    twoBtnDialog.dismiss();
                }

                @Override
                public void doCancel() {
                    twoBtnDialog.dismiss();
                }
            });
            twoBtnDialog.show();
        } else if (jieMian == 0 && isScan) {
            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(NaoBoActivity.this, "是否终止扫描设备？", "是", "否");
            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                @Override
                public void doConfirm() {
                    twoBtnDialog.dismiss();
                    BleManager.getInstance().cancelScan();
                }

                @Override
                public void doCancel() {
                    twoBtnDialog.dismiss();
                }
            });
        } else if (jieMian == 2 && isUpload) {
            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(NaoBoActivity.this, "正在上传数据，确定要终止吗？", "是", "否");
            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                @Override
                public void doConfirm() {
                    twoBtnDialog.dismiss();
                }

                @Override
                public void doCancel() {
                    twoBtnDialog.dismiss();
                }
            });
        } else {
            finish();
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getBaoGaoOkObject() {
        String url = Constant.HOST + Constant.Url.USER_BUYERINDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    private boolean isBoFang = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnKaiShiCS:
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                daoJiShi();
                break;
            case R.id.btnFangSongDN:
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }
                }
                break;
            case R.id.btnDuQuBG:
                showLoadingDialog();
                ApiClient.post(NaoBoActivity.this, getBaoGaoOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        cancelLoadingDialog();
                        LogUtil.LogShitou("CeYiCeFragment--onSuccess", s + "");
                        try {
                            final UserBuyerindex userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                            if (userBuyerindex.getStatus() == 1) {
                                String report_num = userBuyerindex.getReport_num();
                                if (Integer.parseInt(report_num) > 0) {
                                    chaKanBaoGao();
                                } else {
                                    final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(NaoBoActivity.this, "您已没有多余的报告可使用", "购买", "取消");
                                    twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                                        @Override
                                        public void doConfirm() {
                                            twoBtnDialog.dismiss();
                                            Intent intent = new Intent();
                                            intent.putExtra(Constant.IntentKey.ID, userBuyerindex.getReport_id());
                                            intent.setClass(NaoBoActivity.this, ChanPinXQActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void doCancel() {
                                            twoBtnDialog.dismiss();
                                        }
                                    });
                                    twoBtnDialog.show();
                                }
                            } else if (userBuyerindex.getStatus() == 3) {
                                MyDialog.showReLoginDialog(NaoBoActivity.this);
                            } else {
                                Toast.makeText(NaoBoActivity.this, userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(NaoBoActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {
                        cancelLoadingDialog();
                        Toast.makeText(NaoBoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
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
    private OkObject getShengChengOkObject() {
        String url = Constant.HOST + Constant.Url.TESTER_GETREPORT;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("bid", String.valueOf(id));
        params.put("sid", String.valueOf(buyerSavedata.getSid()));
        return new OkObject(params, url);
    }

    /**
     * 查看报告
     */
    private void chaKanBaoGao() {
        upLoad(false);
    }

    /**
     * 下载
     */
    private void upLoad(final boolean fuzhi) {
        showLoadingDialog();
        ApiClient.post(NaoBoActivity.this, getShengChengOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("CeShiLSViewHolder--onSuccess", s + "");
                try {
                    final TesterGetreport testerGetreport = GsonUtils.parseJSON(s, TesterGetreport.class);
                    if (testerGetreport.getStatus() == 1) {
                        if (fuzhi) {
                            ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText(testerGetreport.getData_url());
                            MyDialog.showTipDialog(NaoBoActivity.this, "已复制到剪切版");
                        } else {
                            showLoadingDialog();
                            ApiClient.downLoadFile(NaoBoActivity.this, testerGetreport.getData_url(), "大脑雷达", "详情报告" + System.currentTimeMillis() + ".pdf", new ApiClient.CallBack() {
                                @Override
                                public void onSuccess(String s) {
                                    cancelLoadingDialog();
                                    Intent intent = new Intent();
                                    intent.setClass(NaoBoActivity.this, PdfActivity.class);
                                    intent.putExtra(Constant.IntentKey.TYPE, 1);
                                    intent.putExtra(Constant.IntentKey.TITLE, "检测报告详情");
                                    intent.putExtra(Constant.IntentKey.VALUE, s);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError() {
                                    cancelLoadingDialog();
                                }
                            });
                        }
                    } else if (testerGetreport.getStatus() == 3) {
                        MyDialog.showReLoginDialog(NaoBoActivity.this);
                    } else if (testerGetreport.getStatus() == 2) {
                        MyDialog.dialogFinish(NaoBoActivity.this,testerGetreport.getInfo());
                    }else {
                        MyDialog.showTipDialog(NaoBoActivity.this,testerGetreport.getInfo());
                    }
                } catch (Exception e) {
                    Toast.makeText(NaoBoActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(NaoBoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().cancelScan();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 倒计时
     */
    @SuppressLint("WrongConstant")
    public void daoJiShi() {
        setJieMian(4);
        isBack = false;
        screenWidth = ScreenUtils.getScreenWidth(NaoBoActivity.this);
        final ImageView imageView = new ImageView(NaoBoActivity.this);
        imageView.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewDaoJiShi.addView(imageView, layoutParams);
        PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
        PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);
        PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        animator01 = ObjectAnimator.ofPropertyValuesHolder(imageView, holder01, holder02, holder03);
        animator01.setInterpolator(new LinearInterpolator());
        animator01.setDuration(1000);
        animator01.setRepeatCount(2);
        animator01.setRepeatMode(ValueAnimator.INFINITE);
        animator01.start();
        animator01.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        final int[] count = {1};
        PropertyValuesHolder holder0101 = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
        PropertyValuesHolder holder0201 = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
        PropertyValuesHolder holder0301 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f);
        animator1 = ObjectAnimator.ofPropertyValuesHolder(imageDaoJiShi, holder0101, holder0201, holder0301);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setDuration(1000);
        animator1.setRepeatCount(2);
        animator1.setRepeatMode(ValueAnimator.INFINITE);
        animator1.start();
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onDaoJiShiJieShuListener.jieShu();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                count[0]++;
                if (count[0] == 2) {
                    imageDaoJiShi.setImageResource(R.mipmap.daojishi2);
                } else if (count[0] == 3) {
                    imageDaoJiShi.setImageResource(R.mipmap.daojishi1);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void setMusicView() {
//        mMediaPlayer.start();
        ViewGroup.LayoutParams layoutParams2 = imageMusic.getLayoutParams();
        layoutParams2.width = (int) ((float) screenWidth * 0.6f);
        layoutParams2.height = (int) ((float) screenWidth * 0.6f);
        imageMusic.setLayoutParams(layoutParams2);
//        ImageView imageView1 = new ImageView(NaoBoActivity.this);
//        imageView1.setImageResource(R.mipmap.jianbianquan);
//        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
//        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
//        viewMusic.addView(imageView1, layoutParams1);
        for (int i = 0; i < 5; i++) {
            final ImageView imageView = new ImageView(NaoBoActivity.this);
            imageView.setImageResource(R.mipmap.jianbianquan);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.58f), (int) ((float) screenWidth * 0.58f));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewMusic.addView(imageView, layoutParams);
            if (i > 0) {
                imageView.setVisibility(View.GONE);
            }
            PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
            PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);
            PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            animatorMusic[i] = ObjectAnimator.ofPropertyValuesHolder(imageView, holder01, holder02, holder03);
            animatorMusic[i].setInterpolator(new LinearInterpolator());
            animatorMusic[i].setDuration(4000);
            animatorMusic[i].setStartDelay(0 + 1000 * i);
            animatorMusic[i].setRepeatCount(ValueAnimator.INFINITE);
            animatorMusic[i].setRepeatMode(ValueAnimator.INFINITE);
            animatorMusic[i].start();
            animatorMusic[i].addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    imageView.setVisibility(View.VISIBLE);
                }
            });
        }
        setJieMian(5);
    }

    public void setOnDaoJiShiJieShuListener(OnDaoJiShiJieShuListener onDaoJiShiJieShuListener) {
        this.onDaoJiShiJieShuListener = onDaoJiShiJieShuListener;
    }
}
