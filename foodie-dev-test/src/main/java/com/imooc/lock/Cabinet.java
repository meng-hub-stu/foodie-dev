package com.imooc.lock;

/**
 * 柜子对象
 */
public class Cabinet {
    //柜子中存储的数字
    private int storeNumber;

    public synchronized void setStoreNumber(int storeNumber){
        this.storeNumber = storeNumber;
    }

    public int getStoreNumber(){
        return this.storeNumber;
    }

}
