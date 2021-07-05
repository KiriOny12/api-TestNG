package AdvancedTests.Real.favorites;


import AdvancedTests.Real.assets.GetAssets;
import AdvancedTests.Real.auth.PostAuth;
import okhttp3.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PostFavorites  {



    @Test
    @Parameters({"labelUrl", "email", "password", "rememberMeEmpty", "urlOfAuthRequester" , "sourcePlatform","indexForAssetId" })
    public void postFavorites(String labelUrl, String email, String password, String rememberMeEmpty, String urlOfAuthRequester, String sourcePlatform, int indexForAssetId) throws IOException {

        PostAuth postAuth = new PostAuth();
         postAuth.postAuth(labelUrl, email, password, rememberMeEmpty,  urlOfAuthRequester,  sourcePlatform);
        GetAssets getAssets = new GetAssets();
        getAssets.getAssets(labelUrl, indexForAssetId);

        String route = "favorites";
        String inputDataFormat =
                "asset_id="+getAssets.firstAssetId;

        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), inputDataFormat);
        Request request = new Request.Builder()
                .url(labelUrl + route)
                .addHeader("apitoken", postAuth.token)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        Assert.assertNotNull(result);
        assertEquals(response.code(), 201);
        response.close();

    }
}

