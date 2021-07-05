package AdvancedTests.Real.init;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetCurrentLanguage {



    @Test
    @Parameters({"labelUrl" })

    public void getCurrentLanguage(String labelUrl) throws IOException  {
        String method = labelUrl + "current-language";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        String iso = result.getString("iso");
        Assert.assertNotNull(iso);

        assertEquals(response.code(), 200);
        Assert.assertNotNull(result);
        response.close();
    }


}
