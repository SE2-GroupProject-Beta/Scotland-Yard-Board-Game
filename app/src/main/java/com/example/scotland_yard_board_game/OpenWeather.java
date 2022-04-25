package com.example.scotland_yard_board_game;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class OpenWeather extends AppCompatActivity {

    private static final String openWeatherAPIKey = "27a23c76ccdfc820c455939f63005e70";
    private static final String urlOpenWeatherKlagenfurt =
            "https://api.openweathermap.org/data/2.5/weather?lat=46.6228162&lon=14.3079604&appid=27a23c76ccdfc820c455939f63005e70";
    TextView weatherOutput;
    Button reloadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = "onCreate";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openweather);
        weatherOutput = findViewById(R.id.weatherOutput);
        reloadButton = findViewById(R.id.reloadButton);
        GetWeatherTask getWeatherTask;

        getWeatherTask = new GetWeatherTask(weatherOutput);
        getWeatherTask.execute(urlOpenWeatherKlagenfurt);
        /*
        reloadButton.setOnClickListener(view -> {
            if (getWeatherTask.getStatus() != AsyncTask.Status.RUNNING) {
                getWeatherTask = new GetWeatherTask(weatherOutput).execute();
                Log.d(TAG, "onCreate: reloadButton.setOnClickListener");
            }
        }); */
    }

    private class GetWeatherTask extends AsyncTask<String, Void, String> {
        private TextView textView;

        public GetWeatherTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            String weather = "UNDEFINED";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(stringBuilder.toString());
                JSONObject main = topLevel.getJSONObject("main");
                weather = String.valueOf(main.getDouble("temp"));

                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(String temperature) {
            Double celsius = Double.parseDouble((String.format(Locale.ENGLISH, "%.1f",
                    Double.parseDouble(temperature) - 273.15)));
            weatherOutput.setText("Klagenfurt: " + celsius + " Grad Celsius");
        }
    }


}
