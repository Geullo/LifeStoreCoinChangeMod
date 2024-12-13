package com.geullo.coinchange;

public enum PacketList {
    TELEPORT_TRAIN("00"),
    UNLOCK_STATION("01"),
    OPEN_TRAIN_UI("02"),
    CHANGE_COIN("03"),
    OPEN_CHANGE_COIN_UI("04"),
    GET_COIN_CHANGE_ITEM("05"),
    NOTICE("06"),
    UP_FAVOR("07"),
    DOWN_FAVOR("08"),
    GET_FAKE_NAME("09"),
    SHOW_FAVOR("24"),
    ;

    public String recogCode;

    PacketList(String recogCode) {
        this.recogCode = recogCode;
    }

    public static PacketList convert(String recogCode) {
        switch (recogCode) {
            case "00":
                return TELEPORT_TRAIN;
            case "01":
                return UNLOCK_STATION;
            case "02":
                return OPEN_TRAIN_UI;
            case "03":
                return CHANGE_COIN;
            case "04":
                return OPEN_CHANGE_COIN_UI;
            case "05":
                return GET_COIN_CHANGE_ITEM;
            case "06":
                return NOTICE;
            case "07":
                return UP_FAVOR;
            case "08":
                return DOWN_FAVOR;
            case "09":
                return GET_FAKE_NAME;
            case "24":
                return SHOW_FAVOR;
        }
        return null;
    }
}
