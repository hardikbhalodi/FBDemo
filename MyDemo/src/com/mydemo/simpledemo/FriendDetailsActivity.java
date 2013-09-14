package com.mydemo.simpledemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FriendDetailsActivity extends Activity implements FbListner,
		OnInflatedLayoutComponentClickListener {
	PinnedHeaderListView listView;
	String imgUrl;
	String id;
	String name;
	FriendsDetailsAdapter adapter;
	ArrayList<BeanComment> commentList;
	FbHandler fbHandler;
	String strTmp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_details_actvity);
		imgUrl = getIntent().getStringExtra("imgUrl");
		id = getIntent().getStringExtra("userId");
		name = getIntent().getStringExtra("userName");
		setTitle(name);
		fbHandler = new FbHandler(FriendDetailsActivity.this,
				savedInstanceState);
		fbHandler.setFbListner(FriendDetailsActivity.this);
		if (!fbHandler.isLoggedIn()) {
			Toast.makeText(this, "Login First", Toast.LENGTH_LONG).show();

		} else {

			listView = (PinnedHeaderListView) findViewById(R.id.listViewUserDetails);
			listView.setPinHeaders(false);
			commentList = new ArrayList<BeanComment>();
			adapter = new FriendsDetailsAdapter(commentList,
					FriendDetailsActivity.this, imgUrl);
			listView.setAdapter(adapter);
			AndyUtils.showSimpleProgressDialog(FriendDetailsActivity.this, "",
					"Loading...", false);
			fbHandler.getComments(id);
		}

	}

	@Override
	public void onFbMeFetchSuccess(GraphUser user, Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbPostSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbInviteFriendSuccess(Bundle values, FacebookException error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbFetchFriendSuccess(List<GraphUser> users, Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbLoginFailed(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbLoginSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbCommentSuccess(final Response response) {
		// TODO Auto-generated method stub

		if (response != null && response.getError() == null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					setData(response);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							adapter.notifyDataSetChanged();
							AndyUtils.removeSimpleProgressDialog();
						}
					});
				}
			}).start();

		} else {
			AndyUtils.removeSimpleProgressDialog();
			// Toast.makeText(this, "no",
			// Toast.LENGTH_LONG).show();
		}

	}

	private void setData(final Response response) {
		BeanComment beanComment;
		commentList.clear();
		JSONObject jObject = response.getGraphObject().getInnerJSONObject();
		try {
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0; i < jArray.length(); i++) {
				beanComment = new BeanComment();
				beanComment.setComment(jArray.getJSONObject(i).getString(
						"message"));
				beanComment.setFromUser(jArray.getJSONObject(i)
						.getJSONObject("from").getString("name"));

				commentList.add(beanComment);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onFbPostCommentSuccess(Response response) {
		// TODO Auto-generated method stub

		if (response != null && response.getError() == null) {
			fbHandler.getComments(id);
		}
	}

	@Override
	public void onInflatedLayoutComponentClick(View v, int position, String tmp) {
		// TODO Auto-generated method stub
		if (tmp != null) {
			if (tmp.length() > 0) {
				strTmp = tmp;
				if (hasPublishPermission()) {
					AndyUtils.showSimpleProgressDialog(this, "", "Loading...",
							true);
				}
				if (fbHandler.getCurrentProfilePictureId() != null
						&& fbHandler.getCurrentProfilePictureId().length() > 0) {
					fbHandler.postComment(
							fbHandler.getCurrentProfilePictureId(), tmp);
				} else {
					AndyUtils.removeSimpleProgressDialog();
					Toast.makeText(this,
							"can not able to make comment for this user",
							Toast.LENGTH_LONG).show();
				}

			} else {
				Toast.makeText(this, "Please Enter Comment!", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Session.getActiveSession().onActivityResult(FriendDetailsActivity.this,
				requestCode, resultCode, data);
		if (strTmp != null) {
			AndyUtils.showSimpleProgressDialog(this, "", "Loading...", false);
			fbHandler.postComment(fbHandler.getCurrentProfilePictureId(),
					strTmp);
		}
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null
				&& session.getPermissions().contains("publish_actions");
	}
}
