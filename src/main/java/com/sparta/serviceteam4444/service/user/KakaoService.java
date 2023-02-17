package com.sparta.serviceteam4444.service.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class KakaoService {

    public String getAccessToken(String code) {

        String access_Token = "";

        String refresh_Token = "";

        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("grant_type=code");
            stringBuilder.append("&client_id=306c476f21776ce73e2df07d1ca45995"); //본인이 발급받은 REST_API
            stringBuilder.append("&redirect_uri=http://localhost:3000/user/kakao"); //본인이 설정한 REDIRECT_URI
            stringBuilder.append("&code=" + code);

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();

            //결과 코드가 200이면 성공
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line = "";
            String result = "";

            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }

            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser jsonParser = new JsonParser();

            JsonElement element = jsonParser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            bufferedReader.close();

            bufferedWriter.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        return access_Token;

    }
}
