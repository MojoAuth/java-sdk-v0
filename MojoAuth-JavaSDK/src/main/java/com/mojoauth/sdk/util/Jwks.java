/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth.io All rights reserved.
*/

package com.mojoauth.sdk.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import com.mojoauth.sdk.api.MojoAuthApi;
import com.mojoauth.sdk.models.responsemodels.JwksResponse;
import com.mojoauth.sdk.models.responsemodels.VerifyTokenResponse;
import com.mojoauth.sdk.util.MojoAuthSDK.Initialize;

public class Jwks {

	public Jwks() {
		if (!MojoAuthSDK.validateJwk()) {
			setValues();
		}
	}
	private static Boolean verify;
	private static int code;
	 static String error;

	static ErrorResponse errorResponse;
	
	/**
	 * 
	 * @param accessToken The accessToken
	 * @param handler The handler
	 */
	public void verifyAccessToken(String accessToken, final AsyncHandler<VerifyTokenResponse> handler) {

		execute(accessToken, new AsyncHandler<VerifyTokenResponse>() {

			@Override
			public void onSuccess(VerifyTokenResponse response) {
				handler.onSuccess(response);
			}

			@Override
			public void onFailure(ErrorResponse errorResponse) {
				handler.onFailure(errorResponse);
			}
		});
	}

	
	private static void execute(String accessToken,final AsyncHandler<VerifyTokenResponse> asyncHandler) {
		verify=verifyJWT(accessToken);
		
	if (code==0) {

		VerifyTokenResponse verifyAccessToken = new VerifyTokenResponse();
		verifyAccessToken.setIsValid(verify);
		verifyAccessToken.setAccessToken(accessToken);
		asyncHandler.onSuccess(verifyAccessToken);
			}else {
				ErrorResponse errorResponse =new ErrorResponse();
				errorResponse.setCode(code);
				errorResponse.setMessage("Exception");
				errorResponse.setDescription(error);
				
				asyncHandler.onFailure(errorResponse);	
			}
	}
	
	private static Boolean verifyJWT(String jwt) {
		if (!MojoAuthSDK.validateJwk()) {
			setValues();
		}

		try {
			
		String modulus = MojoAuthSDK.getModulus();
		String exponent = MojoAuthSDK.getExponent();

		PublicKey publicKey;
		publicKey=getPublicKey(modulus,exponent);
				
	    //get signed data and signature from JWT
        String signedData = jwt.substring(0, jwt.lastIndexOf("."));
        String signatureB64u = jwt.substring(jwt.lastIndexOf(".")+1,jwt.length());
        byte signature[] = Base64.getUrlDecoder().decode(signatureB64u);
        
        //verify Signature
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signedData.getBytes());
        boolean isVerify = sig.verify(signature);
        
        //System.out.println(publicKey);
        verify =isVerify;
        return isVerify;
		
	}catch(Exception e) {
		if (code == 0){
		code = 101;
       error = e.toString();
      
		return false;
		}else {return false;}
	}
		}
	
	private static void setValues(){
		MojoAuthApi mojoAuthApi=new MojoAuthApi();

		mojoAuthApi.getJWKS(new AsyncHandler<JwksResponse>() {
			
			@Override
			public void onSuccess(JwksResponse data) {
				// TODO Auto-generated method stub
			Initialize.setExponent(data.getKeys().get(0).getE().toString());
			Initialize.setModulus(data.getKeys().get(0).getN());
			}
			
			@Override
			public void onFailure(ErrorResponse errorcode) {
				// TODO Auto-generated method stub
				code = errorcode.getCode();
				error = errorcode.getDescription();
				
			
			}
		});
	}
	
	private static PublicKey getPublicKey(String modulus, String exponent)  {
		try {    
		byte exponentB[] = Base64.getUrlDecoder().decode(exponent);
	        byte modulusB[] = Base64.getUrlDecoder().decode(modulus);
	        BigInteger bigExponent = new BigInteger(1,exponentB);
	        BigInteger bigModulus = new BigInteger(1,modulusB);
	   
		
		
		PublicKey publicKey;
		
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(bigModulus, bigExponent));
		
//	    String publicKeyPEM = 
//	    "-----BEGIN PUBLIC KEY-----\n" 
//	    + Base64.getEncoder().encodeToString(publicKey.getEncoded()) +"\n"
//	    + "-----END PUBLIC KEY-----";
//	    System.out.println(  publicKeyPEM);
		return publicKey;
		
		} catch (InvalidKeySpecException | NoSuchAlgorithmException  e) {
			code = 101;
			error = e.toString();
	

		}
		return null;
		
	} 


}
