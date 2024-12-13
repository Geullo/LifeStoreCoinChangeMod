package com.geullo.coinchange.UI;

public enum JobType {
    NONE("04","백수","NONE"),
    LUMBER_JACK("00","나무꾼","LUMBER_JACK"),
    MINER("01","광부","MINER"),
    HUNTER("02","사냥꾼","HUNTER"),
    FARMER("03","농부","FARMER"),
    FURNITURE_MAKER("10","가구장이","FURNITURE_MAKER"),
    SHAMAN("20","무당","SHAMAN"),
    JEWELER("11","금은방","JEWELER"),
    ARTIST("21","예술가","ARTIST"),
    CHEF("12","식당 주인","CHEF"),
    DESIGNER("22","옷집 주인","DESIGNER"),
    BARKEEPER("13","주점 주인","BARKEEPER"),
    SUPERMARKET("23","슈퍼 주인","SUPERMARKET"),
    ;
    public String recogCode, krNm,engNM;
    JobType(String recogCode, String krNm, String engNM){
        this.recogCode = recogCode;
        this.krNm = krNm;
        this.engNM = engNM;
    }
    public static boolean isSecondJob(JobType jobType) {
        return FURNITURE_MAKER.equals(jobType) || SHAMAN.equals(jobType) || JEWELER.equals(jobType) || ARTIST.equals(jobType) || CHEF.equals(jobType) || DESIGNER.equals(jobType) || BARKEEPER.equals(jobType) || SUPERMARKET.equals(jobType);
    }

    public static JobType getFirstJob(JobType jobType){
        return jobType.recogCode.substring(1,2).equals(LUMBER_JACK.recogCode.substring(1,2))?LUMBER_JACK:
                jobType.recogCode.substring(1,2).equals(MINER.recogCode.substring(1,2))?MINER:
                        jobType.recogCode.substring(1,2).equals(HUNTER.recogCode.substring(1,2))?HUNTER:
                                jobType.recogCode.substring(1,2).equals(FARMER.recogCode.substring(1,2))?FARMER:
                                        NONE;
    }

    public static JobType codeToJobType(String recogCode) {
        return recogCode.equals(NONE.recogCode) ? NONE : recogCode.equals(LUMBER_JACK.recogCode) ? LUMBER_JACK : recogCode.equals(MINER.recogCode) ? MINER : recogCode.equals(HUNTER.recogCode) ? HUNTER : recogCode.equals(FARMER.recogCode) ? FARMER : recogCode.equals(FURNITURE_MAKER.recogCode) ? FURNITURE_MAKER : recogCode.equals(SHAMAN.recogCode) ? SHAMAN : recogCode.equals(JEWELER.recogCode) ? JEWELER : recogCode.equals(ARTIST.recogCode) ? ARTIST : recogCode.equals(CHEF.recogCode) ? CHEF : recogCode.equals(DESIGNER.recogCode) ? DESIGNER : recogCode.equals(BARKEEPER.recogCode) ? BARKEEPER : recogCode.equals(SUPERMARKET.recogCode) ? SUPERMARKET : NONE;

    }
    public static JobType krNmToType(String krNm) {
        return krNm.equalsIgnoreCase(NONE.krNm) ? NONE : krNm.equalsIgnoreCase(LUMBER_JACK.krNm) ? LUMBER_JACK : krNm.equalsIgnoreCase(MINER.krNm) ? MINER : krNm.equalsIgnoreCase(HUNTER.krNm) ? HUNTER : krNm.equalsIgnoreCase(FARMER.krNm) ? FARMER : krNm.equalsIgnoreCase(FURNITURE_MAKER.krNm) ? FURNITURE_MAKER : krNm.equalsIgnoreCase(SHAMAN.krNm) ? SHAMAN : krNm.equalsIgnoreCase(JEWELER.krNm) ? JEWELER :krNm.equalsIgnoreCase(ARTIST.krNm) ? ARTIST : krNm.equalsIgnoreCase(CHEF.krNm) ? CHEF : krNm.equalsIgnoreCase(DESIGNER.krNm) ? DESIGNER : krNm.equalsIgnoreCase(BARKEEPER.krNm) ? BARKEEPER : krNm.equalsIgnoreCase(SUPERMARKET.krNm) ? SUPERMARKET : NONE;

    }
    public static JobType engNmToType(String engNm) {
        return engNm.equalsIgnoreCase(NONE.engNM) ? NONE : engNm.equalsIgnoreCase(LUMBER_JACK.engNM) ? LUMBER_JACK : engNm.equalsIgnoreCase(MINER.engNM) ? MINER : engNm.equalsIgnoreCase(HUNTER.engNM) ? HUNTER : engNm.equalsIgnoreCase(FARMER.engNM) ? FARMER : engNm.equalsIgnoreCase(FURNITURE_MAKER.engNM) ? FURNITURE_MAKER : engNm.equalsIgnoreCase(SHAMAN.engNM) ? SHAMAN : engNm.equalsIgnoreCase(JEWELER.engNM) ? JEWELER : engNm.equalsIgnoreCase(ARTIST.engNM) ? ARTIST : engNm.equalsIgnoreCase(CHEF.engNM) ? CHEF : engNm.equalsIgnoreCase(DESIGNER.engNM) ? DESIGNER : engNm.equalsIgnoreCase(BARKEEPER.engNM) ? BARKEEPER : engNm.equalsIgnoreCase(SUPERMARKET.engNM) ? SUPERMARKET : NONE;

    }
}
