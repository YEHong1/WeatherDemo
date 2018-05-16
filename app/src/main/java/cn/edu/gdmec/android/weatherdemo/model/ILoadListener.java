package cn.edu.gdmec.android.weatherdemo.model;

import cn.edu.gdmec.android.weatherdemo.WeatherBean;

/**
 * Created by apple on 18/5/15.
 */

public interface ILoadListener {
    void onSuccess(WeatherBean bean);
    void onFailure(Exception e);
}
