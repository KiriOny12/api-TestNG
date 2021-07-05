package AdvancedTests.Real.bonuses;


import AdvancedTests.Real.auth.PostAuth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetCurrentBonusData  {



    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void getCurrentBonusData(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException  {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String method = labelUrl + "current-bonus-data";
        Request request = new Request.Builder()
                .url(method)
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();

        assertEquals(response.code(), 200);

        response.close();
    }
}
