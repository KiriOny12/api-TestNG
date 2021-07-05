package AdvancedTests.Real.accountSwitch;


import AdvancedTests.Real.auth.PostAuth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetAccountSwitch  {

    public String Demotoken;



    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })

    public void getAccountSwitch(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException  {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String method = labelUrl + "account-switch";
        Request request = new Request.Builder()
                .url(method)
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());

        assertEquals(response.code(), 200);

        Demotoken = result.getString("token");
        System.out.println(Demotoken);
        assertEquals(Demotoken.length(), 32);

        response.close();
    }

    public void getAccountSwitch() {
    }
}
