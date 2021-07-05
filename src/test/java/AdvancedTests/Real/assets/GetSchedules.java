package AdvancedTests.Real.assets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetSchedules {


    @Test
    @Parameters({"labelUrl", "indexForAssetId" })
    public void getSchedules(String labelUrl,  int indexForAssetId) throws IOException  {

        GetAssets getAssets = new GetAssets();
        getAssets.getAssets(labelUrl,  indexForAssetId);
        String method = labelUrl + "schedules?"+"asset_id="+getAssets.firstAssetId;
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONArray result = new JSONArray(response.body().string());
        System.out.println(result);
        assertEquals(response.code(), 200);

        Assert.assertNotNull(result);

        response.close();
    }
}
