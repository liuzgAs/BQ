package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.OrderCreateorder;
import com.sxmoc.bq.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class QueRenDDViewHolder extends BaseViewHolder<OrderCreateorder> {

    private final View viewAddress;
    private final View viewNoAddress;
    private final TextView textShouHuoRen;
    private final TextView textAddress;
    private final TextView textPhone;
    private final TextView textTitle;
    private final TextView textPrice;
    private final ImageView imageImg;
    private final ImageView imageAdd;
    private final ImageView imageDelete;
    public EditText editNum;
    OrderCreateorder data;

    public QueRenDDViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        viewAddress = $(R.id.viewAddress);
        viewNoAddress = $(R.id.viewNoAddress);
        textShouHuoRen = $(R.id.textShouHuoRen);
        textAddress = $(R.id.textAddress);
        textPhone = $(R.id.textPhone);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        imageImg = $(R.id.imageImg);
        imageAdd = $(R.id.imageAdd);
        imageDelete = $(R.id.imageDelete);
        editNum = $(R.id.editNum);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editNum.getText().toString().trim())){
                    editNum.setText("1");
                    editNum.setSelection(1);
                }else {
                    int goodsNum = Integer.parseInt(editNum.getText().toString().trim());
                    if (goodsNum < data.getGoods_stock()) {
                        editNum.setText((goodsNum + 1)+"");
                        editNum.setSelection(((goodsNum + 1)+"").length());
                    } else {
                        Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editNum.getText().toString().trim())){

                }else {
                    int goodsNum = Integer.parseInt(editNum.getText().toString().trim());
                    if (goodsNum > 1) {
                        editNum.setText((goodsNum - 1)+"");
                        editNum.setSelection(((goodsNum - 1)+"").length());
                    }
                }

            }
        });
    }

    @Override
    public void setData(OrderCreateorder data) {
        super.setData(data);
        this.data = data;
        if (data.getIs_deddr() == 1) {
            viewAddress.setVisibility(View.VISIBLE);
            viewNoAddress.setVisibility(View.GONE);
            textShouHuoRen.setText("收货人：" + data.getConsignee());
            textAddress.setText(data.getAddress());
            textPhone.setText(data.getPhone());
        } else {
            viewAddress.setVisibility(View.GONE);
            viewNoAddress.setVisibility(View.VISIBLE);
        }
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getGoods_img())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getGoods_title());
        textPrice.setText("¥" + data.getGoods_price());
        editNum.setText(data.getNum()+"");
        editNum.setSelection((data.getNum()+"").length());
    }

}
