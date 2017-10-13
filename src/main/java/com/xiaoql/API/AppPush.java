package com.xiaoql.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.getSha256Digest;

@Service
public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "qMUnwfAaj566iFOBczXuwA";
    private static String appKey = "wsopJK8JAJ91QkLotr2N7A";
    private static String masterSecret = "YPK5L4bDWj9ahR1Z48Mrd";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String authToken = null;

    public void pushToRider(String clientId) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON,
                    "{\n" +
                            "     \"message\": {\n" +
                            "       \"appkey\": \"" + appKey + "\",\n" +
                            "       \"is_offline\": true,\n" +
                            "       \"offline_expire_time\":10000000,\n" +
                            "       \"msgtype\": \"notification\"\n" +
                            "    },\n" +
                            "    \"notification\": {\n" +
                            "        \"style\": {\n" +
                            "            \"type\": 0,\n" +
                            "            \"text\": \"text\",\n" +
                            "            \"title\": \"tttt\"\n" +
                            "        },\n" +
                            "        \"transmission_type\": true,\n" +
                            "        \"transmission_content\": \"透传内容\"\n" +
                            "    },\n" +
                            "    \"cid\": \"" + clientId + "\",\n" +
                            "    \"requestid\": \"" + UUID.randomUUID().toString().replace("-", "") + "\"\n" +
                            "}");
            Request request = new Request.Builder()
                    .url(" https://restapi.getui.com/v1/" + appId + "/push_single")
                    .header("authtoken", authToken)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
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
            authToken = "error";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}