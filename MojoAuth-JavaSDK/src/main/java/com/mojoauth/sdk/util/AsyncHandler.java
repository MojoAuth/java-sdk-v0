/* 
 * 
 * Created by MojoAuth Development Team
   Copyright 2021 MojoAuth.io All rights reserved.
*/
package com.mojoauth.sdk.util;

public interface AsyncHandler<T> {

	public void onSuccess(T data);

	public void onFailure(ErrorResponse errorcode);

}
