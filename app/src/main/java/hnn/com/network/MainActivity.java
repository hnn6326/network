package hnn.com.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.hnn.net.NetWorkServices;
import com.hnn.net.callback.IRequestListener;
import com.hnn.net.parameter.RequestParameters;
import com.hnn.net.util.Response;

public class MainActivity extends AppCompatActivity {

    private NetWorkServices mNetWorkServices = new NetWorkServices();

    private TextView mWeatherTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherTv = findViewById(R.id.tv_context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        requestWeather();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetWorkServices.destroy();
    }

    public void requestWeather() {
        mNetWorkServices.setBaseUrl("http://www.weather.com.cn/");

        mNetWorkServices.sendRequest(new RequestParameters(WeatherParameter.LIST, new Object()), new IRequestListener() {
            @Override
            public void onSuccess(Response response) {
                WeatherResponseBody weatherResponseBody = (WeatherResponseBody) response.getBody(WeatherResponseBody.class);
                mWeatherTv.setText(weatherResponseBody.weatherinfo.city);
            }

            @Override
            public void onError(Response response) {
                mWeatherTv.setText(response.bodyStr);
            }
        });
    }
}
