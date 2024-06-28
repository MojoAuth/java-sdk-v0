/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth Inc. All rights reserved.
   
 */
package com.mojoauth.sdk.models.responsemodels;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class JwksResponse {
	
	@SerializedName("keys")
	@Expose
	private List<Keys> keys= null;

	public List<Keys> getKeys() {
	return keys;
	}

	public void setKeys(List<Keys> keys) {
	this.keys = keys;
	}
}

