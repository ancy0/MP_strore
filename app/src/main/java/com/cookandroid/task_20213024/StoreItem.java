package com.cookandroid.task_20213024;

//그리드 뷰에 상품정보 넣기 위한 클래스
public class StoreItem {
    String name;
    int imgResId;

    public StoreItem(String name, int imgResId) {
        this.name = name;
        this.imgResId = imgResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}

