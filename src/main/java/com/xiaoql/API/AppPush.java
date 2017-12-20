package com.xiaoql.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.apache.commons.codec.digest.DigestUtils.getSha256Digest;

@Service
public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "y3vvcHUf4e8FIWwYo9ELJ";
    private static String appKey = "RAWT38KSni7WS9cjGyaSKA";
    private static String masterSecret = "Zl2MFWTdCQ8jZqRNgnJpV9";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String authToken = null;

    public void pushToRider(String clientId) {
        try {
            if (authToken == null)
                authToken();

            OkHttpClient client = new OkHttpClient();

//            String post_body = "{\n" +
//                    "     \"message\": {\n" +
//                    "       \"appkey\": \"" + appKey + "\",\n" +
//                    "       \"is_offline\": true,\n" +
//                    "       \"offline_expire_time\":10000000,\n" +
//                    "       \"msgtype\": \"notification\"\n" +
//                    "    },\n" +
//                    "    \"notification\": {\n" +
//                    "        \"style\": {\n" +
//                    "            \"type\": 0,\n" +
//                    "            \"text\": \"您有新订单了\",\n" +
//                    "            \"title\": \"订单提醒\"\n" +
//                    "        },\n" +
//                    "        \"transmission_type\": true,\n" +
//                    "        \"transmission_content\": \"透传内容\"\n" +
//                    "    },\n" +
//                    "    \"cid\": \"" + clientId + "\",\n" +
//                    "    \"requestid\": \"" + new Sequence(1, 1).nextId() + "\"\n" +
//                    "}";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String begin = sdf.format(new Date());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String end = sdf.format(calendar.getTime());
            String post_body = "{\n" +
                    "        \"message\":{\n" +
                    "            \"appkey\":\"" + appKey + "\",\n" +
                    "            \"is_offline\":false,\n" +
                    "            \"msgtype\":\"transmission\"\n" +
                    "        },\n" +
                    "        \"transmission\":{\n" +
                    "            \"transmission_type\":false,\n" +
                    "            \"transmission_content\":\"您有新订单了\",\n" +
                    "            \"duration_begin\":\"" + begin + "\",\n" +
                    "            \"duration_end\":\"" + end + "\"\n" +
                    "        },\n" +
                    "        \"cid\":\"" + clientId + "\",\n" +
                    "        \"requestid\":\"" + new Sequence(1, 1).nextId() + "\"\n" +
                    "     }";

            RequestBody body = RequestBody.create(JSON, post_body);
            Request request = new Request.Builder()
                    .url(" https://restapi.getui.com/v1/" + appId + "/push_single")
                    .header("authtoken", authToken)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String s = response.body().string();
            System.out.println(s);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 10 * 60 * 1000)
    public void authToken() {
        try {
            String timestamp = new Date().getTime() + "";
            String sign = Hex.encodeHexString(getSha256Digest().digest((appKey + timestamp + masterSecret).getBytes("utf-8")));
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, "{ \"sign\":\"" + sign + "\",\n" +
                    "\"timestamp\":\"" + timestamp + "\",\n" +
                    "\"appkey\":\"" + appKey + "\"\n" +
                    "}");
            Request request = new Request.Builder()
                    .url("https://restapi.getui.com/v1/" + appId + "/auth_sign")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            String s = response.body().string();
            System.out.println(s);
            Map<String, Object> res = objectMapper.readValue(s, Map.class);
            if ("ok".equals(res.get("result")))
                authToken = res.get("auth_token").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Scheduled(initialDelay = 10 * 1000, fixedDelay = 1000)
    public void authTokenNull() {
        if (authToken != null) return;
        authToken();
    }

}