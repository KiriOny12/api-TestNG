package AdvancedTests.Real.init;

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

public class GetInit  {

    public JSONObject resultGetInitWeb;

    public JSONObject resultGetInitMobile;

    @Test
    @Parameters({"labelUrl" })
    public void GetInitWeb(String labelUrl) throws IOException {
        String method = labelUrl + "init?device=web";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        resultGetInitWeb = new JSONObject(response.body().string());

        assertEquals(response.code(), 200);
        Assert.assertNotNull(resultGetInitWeb);
        response.close();

    }


    @Test
    @Parameters({"labelUrl"})
    public void GetInitMobile(String labelUrl) throws IOException  {
        String method = labelUrl + "init?device=mobile";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        resultGetInitMobile = new JSONObject(response.body().string());

        assertEquals(response.code(), 200);
        Assert.assertNotNull(resultGetInitMobile);
        response.close();
    }


}
