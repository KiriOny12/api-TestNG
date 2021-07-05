package AdvancedTests.Real.auth;


import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PostAuthMobile  {


    @Test
    @Parameters({"labelUrl", "email", "password"})
    public void postAuthMobile(String labelUrl, String email, String password) throws IOException {

        String route = "auth-mobile";
        String inputDataFormat =
                  email + "&"
                + password;

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(labelUrl + route)
                .addHeader("Platform", "IOS")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        String hash = result.getString("hash");
        assertEquals(hash.length(), 32);

        String token = result.getString("token");
        assertEquals(token.length(), 32);

        assertEquals(response.code(), 200);
        response.close();

    }
}