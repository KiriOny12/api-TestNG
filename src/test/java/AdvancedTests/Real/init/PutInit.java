package AdvancedTests.Real.init;


import AdvancedTests.Real.auth.PostAuth;
import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class PutInit  {



    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void putInitCurrentTheme(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);


        String route = "init/current_theme";
        String inputDataFormat = "theme=dark";

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(labelUrl + route)
                .addHeader("apitoken", postAuth.token)
                .put(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        Assert.assertNotNull(result);

        response.close();

    }
}