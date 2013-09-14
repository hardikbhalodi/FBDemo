package com.mydemo.simpledemo;

import java.util.List;

import com.facebook.FacebookException;
import com.facebook.Response;
import com.facebook.model.GraphUser;

import android.content.Intent;
import android.os.Bundle;

public interface FbListner {
	
	public void onFbMeFetchSuccess(GraphUser user, Response response);
	public void onFbPostSuccess(Response response);
	public void onFbInviteFriendSuccess(Bundle values,FacebookException error);
	public void onFbFetchFriendSuccess(List<GraphUser> users,Response response);
	public void onFbLoginFailed(Response response);
	public void onFbLoginSuccess(Response response);
	public void onFbCommentSuccess(Response response);
	public void onFbPostCommentSuccess(Response response);
	
}
