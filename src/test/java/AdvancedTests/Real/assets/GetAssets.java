package AdvancedTests.Real.assets;

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

public class GetAssets  {
    public int firstAssetId;
    public int def_amount;
    public long ls_mc;
    public double sell_value;
    public double buy_value;
    public int min_amount;
    public int max_amount;

    @Test
    @Parameters({"labelUrl", "indexForAssetId" })
    public void getAssets(String labelUrl, int indexForAssetId) throws IOException  {

        String method = labelUrl + "assets?"+"menu=3";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONArray result = new JSONArray(response.body().string());
        JSONObject firstJsonObject = result.getJSONObject(indexForAssetId);

        firstAssetId = firstJsonObject.getInt("id");
        def_amount = firstJsonObject.getInt("def_amount");
        ls_mc = firstJsonObject.getLong("ls_mc");
        sell_value = firstJsonObject.getDouble("sell_value");
        buy_value = firstJsonObject.getDouble("buy_value");
        min_amount = firstJsonObject.getInt("min_amount");
        max_amount = firstJsonObject.getInt("max_amount");

        assertEquals(response.code(), 200);
        Assert.assertNotNull(result);
        response.close();
    }


    @Test
    @Parameters({"labelUrl"})
    public void GetAssetsCheckWorkTime(String labelUrl) throws IOException  {
        String method = labelUrl + "assets?"+"menu=3";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONArray result = new JSONArray(response.body().string());



        for (int i = 0; i<result.length(); i++) {

            JSONObject jsonObject = result.getJSONObject(i);
            int id = jsonObject.getInt("id");
            JSONObject work_time = jsonObject.getJSONObject("work_time");
            System.out.println("asset id = " + id + " work time is " +work_time);
            String end_day = work_time.getString("end_day");
            String start_day = work_time.getString("start_day");
            Assert.assertNotNull(end_day);
            Assert.assertNotNull(start_day);

            Assert.assertNotNull(work_time);
        }
        assertEquals(response.code(), 200);
        response.close();
    }


}
