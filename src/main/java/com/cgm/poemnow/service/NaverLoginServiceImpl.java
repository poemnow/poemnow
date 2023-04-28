package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.NaverLoginMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional
public class NaverLoginServiceImpl implements NaverLoginService {

	@Autowired
	private NaverLoginMapper naverLoginMapper;

	@Override
	public String getUserInfo(String token) {

		String id = "";
		String reqURL = "https://openapi.naver.com/v1/nid/me";

		//access_token을 이용하여 사용자 정보 조회
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
		//	System.out.println("responseCode : " + responseCode);

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
		//	System.out.println("response body : " + result);

			JSONObject jsonObject = new JSONObject(result);
			id = jsonObject.getJSONObject("response").getString("id");
		//	System.out.println("id: " + id);
		//	System.out.println("id : " + id);


			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public String getToken(String state, String code) {

		String access_Token = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			//POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=cf5PAbTrDyD9g5jgqQTn"); // TODO REST_API_KEY 입력
			sb.append("&client_secret=pheuJnD83H"); // TODO 인가코드 받은 redirect_uri 입력
			sb.append("&code=" + code);
			sb.append("&state" + state);
			bw.write(sb.toString());
			bw.flush();

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
		//	System.out.println("responseCode : " + responseCode);

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
		//	System.out.println("response body : " + result);
			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();

		//	System.out.println("access_token : " + access_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}

	@Override
	public User getUser(String id) {
		return naverLoginMapper.getUser(id);
	}

}
