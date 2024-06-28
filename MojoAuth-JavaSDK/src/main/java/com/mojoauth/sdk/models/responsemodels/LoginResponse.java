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
public class LoginResponse {

	@SerializedName("state_id")
	@Expose
	private String stateId;
	
	public String getStateId() {
	return stateId;
	}

	public void setStateId(String stateId) {
	this.stateId = stateId;
	}



}