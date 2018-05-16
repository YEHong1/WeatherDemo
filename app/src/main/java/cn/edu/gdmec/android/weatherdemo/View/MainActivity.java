package cn.edu.gdmec.android.weatherdemo.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;

import cn.edu.gdmec.android.weatherdemo.R;
import cn.edu.gdmec.android.weatherdemo.WeatherBean;
import cn.edu.gdmec.android.weatherdemo.presenter.WeatherPresenter;

public class MainActivity extends AppCompatActivity implements IWeatherView, View.OnClickListener{

    private TextView tvWeather;
    private TextView tvWeatherYesterday;
    private ProgressDialog progressDialog;
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_guangzh_search).setOnClickListener(this);
        findViewById(R.id.btn_beijin_search).setOnClickListener(this);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvWeatherYesterday = (TextView) findViewById(R.id.tv_weather_yesterday);
        presenter = new WeatherPresenter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_guangzh_search:
                presenter.loadWeather("广州");
                break;
            case R.id.btn_beijin_search:
                presenter.loadWeather("北京");
                break;
        }
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.show(MainActivity.this, "", "正在获取");
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showWeatherData( final WeatherBean weatherBean) {
        runOnUiThread(new TimerTask() {

            @Override
            public void run() {
                if (weatherBean.getStatus() == 304) {
                    Toast.makeText(MainActivity.this, weatherBean.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    tvWeather.setText("城市:" + weatherBean.getCity() + "日期：" + weatherBean.getDate() + "温度：" + weatherBean.getData().getWendu());
                    tvWeatherYesterday.setText("昨日天气：" + weatherBean.getData().getYesterday().getLow() + " " + weatherBean.getData().getYesterday().getHigh());
                }
            }
        });
    }

    @Override
    public void showLoadFailMsg(final Exception e) {
        runOnUiThread(new TimerTask() {
            @Override
            public void run() {
                tvWeather.setText("加载数据失败：" + e.toString());
            }
        });
    }
}
