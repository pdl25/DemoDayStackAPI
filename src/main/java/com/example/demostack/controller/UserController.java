package com.example.demostack.controller;

import com.example.demostack.CryptoResponse;
import com.example.demostack.SpaceResponse;
import com.example.demostack.WeatherResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@CrossOrigin(origins={"http://localhost:3000"})
@RestController
@RequestMapping("api/")
public class UserController {
    @GetMapping("weather/{city}")
    public WeatherResponse getWeather(@PathVariable(required=false,name="city") String city) {
        try {
            WebClient client = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=5b430fa8c6e04027e005f8bd80b984dd&units=imperial");
            Mono<WeatherResponse> response = client.get().retrieve().bodyToMono(WeatherResponse.class);
            WeatherResponse json = response.share().block();
            return json;
        } catch(Exception e) {
            System.out.println(e);
            WeatherResponse json = null;
            return json;
        }
    }
    @GetMapping("iss")
    public SpaceResponse getISS() {
        try {
            WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json");
            Mono<SpaceResponse> response = client.get().retrieve().bodyToMono(SpaceResponse.class);
            SpaceResponse json = response.share().block();
            String lon = json.iss_position.longitude;
            String lat = json.iss_position.latitude;

            WebClient client2 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=5b430fa8c6e04027e005f8bd80b984dd&units=imperial");
            Mono<WeatherResponse> response2 = client2.get().retrieve().bodyToMono(WeatherResponse.class);
            WeatherResponse json2 = response2.share().block();
            if (json2.sys.country == null) {
                json.country = "Not currently in a country";
            } else {
                json.city = json2.name;
                json.country = json2.sys.country;
            }
            return json;

        } catch(Exception e) {
            SpaceResponse json = null;
            return json;
        }
    }
    @GetMapping("wiss")
    public SpaceResponse getISSWeather() {
        try {
            WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json");
            Mono<SpaceResponse> response = client.get().retrieve().bodyToMono(SpaceResponse.class);
            SpaceResponse json = response.share().block();
            String lon = json.iss_position.longitude;
            String lat = json.iss_position.latitude;
            WebClient client2 = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=5b430fa8c6e04027e005f8bd80b984dd&units=imperial");
            Mono<WeatherResponse> response2 = client2.get().retrieve().bodyToMono(WeatherResponse.class);
            WeatherResponse json2 = response2.share().block();
            if (json2.sys.country == null) {
                json.country = "Not currently in a country";
            } else {
                json.city = json2.name;
                json.country = json2.sys.country;
            }
            json.description = json2.weather[0].description;
            json.temp = json2.main.temp;
            return json;

        } catch (Exception e) {
            System.out.println(e);
            SpaceResponse json = null;
            return json;
        }
    }
    @GetMapping("crypto/{coin}")
    public CryptoResponse getCrypto(@PathVariable(required=false,name="coin")  String coin) {
        try {
            WebClient client = WebClient.create("https://rest.coinapi.io/v1/assets/" + coin + "?apikey=919353ED-A83D-47CB-BE8E-5F6F3C1F2CF1");
            Mono<CryptoResponse[]> response = client.get().retrieve().bodyToMono(CryptoResponse[].class);
            CryptoResponse[] json = response.share().block();
            return json[0];
        } catch( Exception e) {
            System.out.println(e);
            CryptoResponse json = null;
            return json;
        }
    }
}
