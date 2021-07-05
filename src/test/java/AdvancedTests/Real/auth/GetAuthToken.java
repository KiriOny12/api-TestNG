package AdvancedTests.Real.auth;

import AdvancedTests.Real.accountSwitch.GetAccountSwitch;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.*;

import static org.testng.Assert.assertEquals;

public class GetAuthToken {

    String token;
    String authorizationToken;

    @Test(priority = 0)
    @Parameters({"DB_URL","DB_USER", "DB_PASSWORD", "getLoginsToken"})

    public void getToken(String DB_URL, String DB_USER, String DB_PASSWORD, String getLoginsToken) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {

        GetAccountSwitch getAccountSwitch = new GetAccountSwitch();
        getAccountSwitch.getAccountSwitch();

        String dbClass = "com.mysql.cj.jdbc.Driver";
        Class.forName(dbClass).
                newInstance();
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement stmt = con.createStatement();
        try {
            ResultSet res = stmt.executeQuery(getLoginsToken);
            while (res.next()) {
                 token = res.getString("token");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            con.close();
    }

    @Test(priority = 1)
    @Parameters({"labelUrl"})
    public void getAuthToken(String labelUrl) throws IOException  {

        String method = labelUrl + "auth/"+ token + "?" + "source=PLATFORM";
        Request request = new Request.Builder()
                .url(method)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        JSONObject result = new JSONObject(response.body().string());
        System.out.println(result);
        authorizationToken  = result.getString("token");
        assertEquals(response.code(), 200);
        Assert.assertNotNull(authorizationToken);
        System.out.println("authorizationToken is " + authorizationToken);
        response.close();
    }
}
