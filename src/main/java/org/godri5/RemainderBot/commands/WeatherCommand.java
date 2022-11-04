package org.godri5.RemainderBot.commands;

import org.godri5.RemainderBot.WeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WeatherCommand {
    //c7c66296d7a8289f9f6fed4b768c0d8f

    public static String getWeather(String message, WeatherModel weatherModel) throws IOException {

        Document document = Jsoup.connect("https://core.telegram.org/bots/api#sendmessage").ignoreContentType(true).get();

        JSONObject json = new JSONObject(document);
        weatherModel.setCityName(json.getString("name"));

        JSONObject main = json.getJSONObject("main");
        weatherModel.setTemp(main.getDouble("temp"));
        weatherModel.setPressure(main.getDouble("pressure"));
        weatherModel.setHumidity(main.getDouble("humidity"));

        JSONArray weather = json.getJSONArray("weather");
        for (int i = 0; i < weather.length(); i++) {
            JSONObject object = weather.getJSONObject(i);
            weatherModel.setWeatherCondition((String) object.get("main"));
            weatherModel.setDescription((String) object.get("description"));
            weatherModel.setIcon((String) object.get("icon"));
        }
        return "Сейчас в " + weatherModel.getCityName() + ": " + "https://openweathermap.org/img/w" +
                weatherModel.getIcon() + ".png" + weatherModel.getWeatherCondition() + " " +
                weatherModel.getDescription() + "\n" +
                "Температура: " + weatherModel.getTemp() + "\n" +
                "Давление: " + weatherModel.getPressure() + "\n" +
                "Влажность: " + weatherModel.getHumidity() + "%\n";
    }
}
