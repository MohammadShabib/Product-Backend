package com.rest;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Services {

    public static float convertCurrency(float amount, String from, String to)
    {

        final String uri = String.format("https://api.apilayer.com/exchangerates_data/convert?to=%s&from=%s&amount=%f", to, from, amount) ;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.set("apikey", "Sv7Bo7ngDqAAKkDFYAtvUb6xKATujQ8e");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);



        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);



        JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();


        return jsonObject.get("result").getAsFloat();
    }
    static public boolean currencyIsValid(String currency)
    {
        if(currency.length() != 3)
        {
            return false;
        }

        return true;
    }
}
