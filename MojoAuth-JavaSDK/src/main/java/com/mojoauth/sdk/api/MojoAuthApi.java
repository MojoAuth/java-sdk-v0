/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth.io All rights reserved.
*/

package com.mojoauth.sdk.api;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mojoauth.sdk.helper.JsonDeserializer;
import com.mojoauth.sdk.helper.MojoAuthRequest;
import com.mojoauth.sdk.helper.MojoAuthValidator;
import com.mojoauth.sdk.models.responsemodels.JwksResponse;
import com.mojoauth.sdk.models.responsemodels.LoginResponse;
import com.mojoauth.sdk.models.responsemodels.UserResponse;
import com.mojoauth.sdk.util.AsyncHandler;
import com.mojoauth.sdk.util.ErrorResponse;
import com.mojoauth.sdk.util.MojoAuthSDK;

public class MojoAuthApi {
	
	public MojoAuthApi() {
		if (!MojoAuthSDK.validate()) {
			throw new MojoAuthSDK.InitializeException();
		}
	}
	Gson gson=new Gson();

	/**
	 * 
	 * @param email The email
	 * @param handler The handler
	 */
	public void loginByMagicLink(String email, final AsyncHandler<LoginResponse> handler) {

		Map<String, String> queryParameters = new HashMap<String, String>();
		JsonObject json = new JsonObject(); //Required
		String resourcePath = "users/magiclink";
		
		if (!MojoAuthValidator.isNullOrWhiteSpace(email)) {
			
			json.addProperty("email", email);
		}
		
		MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(json), new AsyncHandler<String>() {

			@Override
			public void onSuccess(String response) {
				TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
				};
				LoginResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
				handler.onSuccess(successResponse);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}
	/**
	 * 
	 * @param email The email
	 * @param handler The handler
	 */
	public void loginByEmailOTP(String email, final AsyncHandler<LoginResponse> handler) {

		Map<String, String> queryParameters = new HashMap<String, String>();
		JsonObject json = new JsonObject(); //Required
		String resourcePath = "users/emailotp";
		
		if (!MojoAuthValidator.isNullOrWhiteSpace(email)) {
			
			json.addProperty("email", email);
		}
		
		MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(json), new AsyncHandler<String>() {

			@Override
			public void onSuccess(String response) {
				TypeToken<LoginResponse> typeToken = new TypeToken<LoginResponse>() {
				};
				LoginResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
				handler.onSuccess(successResponse);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}

	/**
	 * 
	 * @param otp The otp
	 * @param stateId The id
	 * @param handler the handler
	 */
	public void verifyEmailOTP(String otp,String stateId, final AsyncHandler<UserResponse> handler) {

		Map<String, String> queryParameters = new HashMap<String, String>();
		JsonObject json = new JsonObject(); //Required
		String resourcePath = "users/emailotp/verify";
		
		if (!MojoAuthValidator.isNullOrWhiteSpace(otp)) {
			
			json.addProperty("otp", otp);
		}

		if (!MojoAuthValidator.isNullOrWhiteSpace(stateId)) {
			
			json.addProperty("state_id", stateId);
		}

		MojoAuthRequest.execute("POST", resourcePath, queryParameters, gson.toJson(json), new AsyncHandler<String>() {

			@Override
			public void onSuccess(String response) {
				TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {
				};
				UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
				handler.onSuccess(successResponse);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}
	
	/**
	 * 
	 * @param stateId The id
	 * @param handler The handler
	 */
	public void pingStatus(String stateId, final AsyncHandler<UserResponse> handler) {

		Map<String, String> queryParameters = new HashMap<String, String>();
		String resourcePath = "users/status";
		
		if (!MojoAuthValidator.isNullOrWhiteSpace(stateId)) {
			
			queryParameters.put("state_id", stateId);
		}
		
		MojoAuthRequest.execute("GET", resourcePath, queryParameters,null, new AsyncHandler<String>() {

			@Override
			public void onSuccess(String response) {
				TypeToken<UserResponse> typeToken = new TypeToken<UserResponse>() {
				};
				UserResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
				handler.onSuccess(successResponse);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}


	/**
	 * 
	 * @param handler The handler
	 */
	public void getJWKS(final AsyncHandler<JwksResponse> handler) {

		Map<String, String> queryParameters = new HashMap<String, String>();
		String resourcePath = "token/jwks";
				
		MojoAuthRequest.execute("GET", resourcePath, queryParameters,null, new AsyncHandler<String>() {

			@Override
			public void onSuccess(String response) {
				TypeToken<JwksResponse> typeToken = new TypeToken<JwksResponse>() {
				};
				JwksResponse successResponse = JsonDeserializer.deserializeJson(response, typeToken);
				handler.onSuccess(successResponse);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}




}