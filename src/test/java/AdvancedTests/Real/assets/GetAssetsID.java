package AdvancedTests.Real.assets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class GetAssetsID  {

    public double last_value;
    public int def_amount;
    public long ls_mc;

    @Test
    @Parameters({"labelUrl", "indexForAssetId" })
    public void getAssetsID(String labelUrl, int indexForAssetId) throws IOException  {

        GetAssets getAssets = new GetAssets();
        getAssets.getAssets(labelUrl, indexForAssetId);

        String method = labelUrl + "assets/"+getAssets.firstAssetId;
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result + " " + getClass().toString());
        last_value = result.getDouble("last_value");
        def_amount = result.getInt("def_amount");
        ls_mc = result.getLong("ls_mc");
        assertEquals(response.code(), 200);
        Assert.assertNotNull(result);
        response.close();
    }
}
