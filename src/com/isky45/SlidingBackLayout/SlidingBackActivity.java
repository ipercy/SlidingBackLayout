package com.isky45.SlidingBackLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * User: hqs
 * Date: 2016/3/2
 * Time: 17:34
 */
public class SlidingBackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SlidingBackLayout layout = (SlidingBackLayout) LayoutInflater.from(this).inflate(R.layout.base,null);
        layout.replaceRootLayer(this);
    }
}
