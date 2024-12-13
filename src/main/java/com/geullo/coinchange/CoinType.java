package com.geullo.coinchange;

public enum CoinType {
    COIN_TO_JOB_POINT("1"),
    JOB_POINT_TO_COIN("2"),
    COIN_TO_STAT_POINT("3");
    public String type;
    CoinType(String type){
        this.type = type;
    }
    public static CoinType convert(String type){
        return type.equals(COIN_TO_JOB_POINT.type)?COIN_TO_JOB_POINT:
                type.equals(JOB_POINT_TO_COIN.type)?JOB_POINT_TO_COIN:
                        type.equals(COIN_TO_STAT_POINT.type)?COIN_TO_STAT_POINT:null;
    }
}
