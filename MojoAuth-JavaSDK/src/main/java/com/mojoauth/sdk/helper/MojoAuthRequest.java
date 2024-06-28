/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth.io All rights reserved.
*/
package com.mojoauth.sdk.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.reflect.TypeToken;
import com.mojoauth.sdk.util.AsyncHandler;
import com.mojoauth.sdk.util.ErrorResponse;

import com.mojoauth.sdk.util.MojoAuthSDK;

public class MojoAuthRequest {
	private MojoAuthRequest() {
	}

	private static final String encoding = "UTF-8";

	static ErrorResponse errorResponse;
	private static Integer code = 0;
	private static final String keyToken = "access_token";

	private static String authorization = "";

	public static void execute(String method, String resourcePath, Map<String, String> params, String payload,
			final AsyncHandler<String> asyncHandler) {
		String serviceUrl = MojoAuthSDK.getDomain() + "/" + resourcePath;
		if (params.containsKey(keyToken)) {
			authorization = params.get(keyToken);
			params.remove(keyToken);
		}

		String task = MojoAuthRequestRunner(method, MojoAuthSDK.getRequestUrl(serviceUrl, params), payload);
		if (code == 200) {
			asyncHandler.onSuccess(task);
		} else {
			ErrorResponse errorResponse = exception(task);
			asyncHandler.onFailure(errorResponse);
		}
	}

	private static String MojoAuthRequestRunner(String method, String url, String payload) {
		try {
		
			URL connectionUrl = new URL(url);
			HttpURLConnection con = null;
			con = (HttpURLConnection) connectionUrl.openConnection();
			con.setRequestMethod(method);
			con.setConnectTimeout(15000); // set timeout to 15 seconds
			con.setReadTimeout(15000);
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
			con.setRequestProperty("X-API-Key",MojoAuthSDK.getApiKey() );
			if (!authorization.equals("")) {
				con.setRequestProperty("Authorization", "Bearer " + authorization);
				authorization = "";
			}
			
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("charset", encoding);
			con.setRequestProperty("Accept-Encoding", "gzip");
			con.setDoInput(true);
			con.setDoOutput(true);
			if (!method.equals("GET")) {
				OutputStream os = con.getOutputStream();
				OutputStreamWriter body = new OutputStreamWriter(os, encoding);
				String p = payload != null ? payload : "{}";
				body.write(p);
				body.flush();
				body.close();
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				code = responseCode;
				return readStream(con.getInputStream(), con.getContentEncoding());
			} else {
				code = responseCode;
				return readStream(con.getErrorStream(), con.getContentEncoding());
			}

		} catch (UnknownHostException e) {
			code = 101;
			return e.toString();
		} catch (IllegalArgumentException e) {
			code = 102;
			return e.toString();
		} catch (MalformedURLException e) {
			code = 103;
			return e.toString();
		} catch (SocketTimeoutException e) {
			code = 104;
			return e.toString();
		} catch (IOException e) {
			code = 105;
			return e.toString();
		}
	}

	private static String readStream(InputStream in, String en) {
		BufferedReader reader = null;
		StringBuilder response = new StringBuilder();
		try {
			if ("gzip".equals(en)) {
				reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(in)));
			} else {
				reader = new BufferedReader(new InputStreamReader(in));
			}
			String line = "";
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response.toString();
	}

	public static String encode(String key, final String data) {
		String s = null;
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(encoding), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			s = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes(encoding)));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return s;
	}

	private static ErrorResponse exception(String error) {
		ErrorResponse obj = new ErrorResponse();
		switch (code) {
		case 101:
			obj.setCode(101);
			obj.setMessage(error);
			obj.setDescription("UnknownHostException");
			break;
		case 102:
			obj.setCode(102);
			obj.setMessage(error);
			obj.setDescription("IllegalArgumentException");
			break;
		case 103:
			obj.setCode(103);
			obj.setMessage(error);
			obj.setDescription("MalformedURLException");
			break;
		case 104:
			obj.setCode(104);
			obj.setMessage(error);
			obj.setDescription("SocketTimeoutException");
			break;
		case 105:
			obj.setCode(105);
			obj.setMessage(error);
			obj.setDescription("IOException");
			break;
		default:
			TypeToken<ErrorResponse> typeToken = new TypeToken<ErrorResponse>() {
			};
			obj = JsonDeserializer.deserializeJson(error, typeToken);
			break;
		}

		return obj;
	}
}
