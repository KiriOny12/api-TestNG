package AdvancedTests.Real.account;

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

public class GetAccount {

    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform" })
    public void getAccount(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform) throws IOException  {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);

        String method = labelUrl + "account";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("apitoken", postAuth.token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());

        long account_id = result.getLong("account_id");
        Assert.assertTrue(account_id>0);

        String category_name = result.getString("category_name");
        Assert.assertNotNull(category_name);

        int category_id = result.getInt("category_id");
        Assert.assertTrue(category_id>0);

        String last_name = result.getString("last_name");
        Assert.assertNotNull(last_name);

        String verification_status_label = result.getString("verification_status_label");
        Assert.assertNotNull(verification_status_label);

        String first_name = result.getString("first_name");
        Assert.assertNotNull(first_name);

        int category_points_required = result.getInt("category_points_required");
        Assert.assertTrue(category_points_required>=0);

        int category_points_achieved = result.getInt("category_points_achieved");
        Assert.assertTrue(category_points_achieved>=0);

        assertEquals(response.code(), 200);

        response.close();
    }
}
