/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth Inc. All rights reserved.
   
 */

package com.mojoauth.sdk.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// <summary>
//	Response containing Definition of Complete Profile data
// </summary>
public class VerifyTokenResponse {

	@SerializedName("isValid")
	@Expose
	private Boolean isValid;
	@SerializedName("access_token")
	@Expose
	private String accessToken;

	public Boolean getIsValid() {
	return isValid;
	}

	public void setIsValid(Boolean isValid) {
	this.isValid = isValid;
	}

	public String getAccessToken() {
	return accessToken;
	}

	public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
	}
}