package AdvancedTests.Real.auth;


import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PostAuth  {

    public String token;



    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void postAuth(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException {

        String route = "auth";
        String inputDataFormat =
                  email + "&"
                + password + "&"
                + rememberMeEmpty + "&"
                + urlOfAuthRequester + "&"
                + sourcePlatform;
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(labelUrl + route)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println("result is " + result);
        token = result.getString("token");
        Assert.assertNotNull(token);
        System.out.println(token);
        assertEquals(response.code(), 200);

        response.close();

    }


}