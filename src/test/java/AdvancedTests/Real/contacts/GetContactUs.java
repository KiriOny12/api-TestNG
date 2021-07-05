package AdvancedTests.Real.contacts;


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

public class GetContactUs  {

    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })

    public void getContactUs(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException  {

        PostAuth postAuth = new PostAuth();
        postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String method = labelUrl + "contact-us";
        Request request = new Request.Builder()
                .url(method)
                .addHeader("apitoken", postAuth.token)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        String international = result.getString("international");
        String fax = result.getString("fax");
        String local = result.getString("local");

        String plusInternational = international.substring(0,1);
        Assert.assertEquals(plusInternational, "+");

        String plusFax = fax.substring(0,1);
        Assert.assertEquals(plusFax, "+");

        String plusLocal = local.substring(0,1);
        Assert.assertEquals(plusLocal, "+");


        assertEquals(response.code(), 200);
        response.close();
    }
}
