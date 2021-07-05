package AdvancedTests.Real.account;


import AdvancedTests.Real.auth.PostAuth;
import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PostAccount {

    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void postAccount(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String route = "account";
        String inputDataFormat =
                "{\"lang\":\"en\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), inputDataFormat);
        Request request = new Request.Builder()
                .url(labelUrl + route)
                .addHeader("apitoken", postAuth.token)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        Assert.assertNotNull(result);
        assertEquals(response.code(), 200);
        response.close();
    }
}