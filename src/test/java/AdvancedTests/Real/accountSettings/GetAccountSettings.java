package AdvancedTests.Real.accountSettings;


import AdvancedTests.Real.auth.PostAuth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetAccountSettings {


    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void getAccountSettings(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String method = labelUrl + "account-settings";
        Request request = new Request.Builder()
                .url(method)
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONArray result = new JSONArray(response.body().string());

        for (int i = 0; i < result.length(); ++i) {

            JSONObject jsn = result.getJSONObject(i);

            String link = jsn.getString("link");
            Assert.assertNotNull(link);
            assertEquals(response.code(), 200);
            response.close();
        }
    }
}
