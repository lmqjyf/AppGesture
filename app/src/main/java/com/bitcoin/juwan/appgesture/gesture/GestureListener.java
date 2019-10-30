package com.bitcoin.juwan.appgesture.gesture;

import java.util.List;

/**
 * FileName：GestureListener
 * Create By：liumengqiang
 * Description：TODO
 */
public interface GestureListener {
    void onComplete(List<Integer> list);

    void onFailed(); //小于4个
}
