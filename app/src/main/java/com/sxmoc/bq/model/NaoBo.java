package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class NaoBo {
    private int zuoNao;
    private int youNao;

    public NaoBo(int zuoNao, int youNao) {
        this.zuoNao = zuoNao;
        this.youNao = youNao;
    }

    public int getZuoNao() {
        return zuoNao;
    }

    public void setZuoNao(int zuoNao) {
        this.zuoNao = zuoNao;
    }

    public int getYouNao() {
        return youNao;
    }

    public void setYouNao(int youNao) {
        this.youNao = youNao;
    }
}
