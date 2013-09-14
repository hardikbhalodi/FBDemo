package com.mydemo.simpledemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Response;
import com.facebook.model.GraphUser;

public class FriendListAcitivity extends Activity implements
		OnItemClickListener, FbListner {
	ListView listview;
	FbHandler fbHandler;
	ArrayList<FriendBean> friends;
	FriendListAdapter adapter;
	EditText etSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView) findViewById(R.id.listViewFriends);
		listview.setOnItemClickListener(this);
		friends = new ArrayList<FriendBean>();
		fbHandler = new FbHandler(this, savedInstanceState);
		fbHandler.setFbListner(this);
		adapter = new FriendListAdapter(friends, FriendListAcitivity.this);
		listview.setAdapter(adapter);
		listview.setTextFilterEnabled(true);
		etSearch = (EditText) findViewById(R.id.etSearch);

		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					adapter.resetData();
				}

				adapter.getFilter().filter(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		if (fbHandler.isLoggedIn()) {
			AndyUtils.showSimpleProgressDialog(this, "", "Loading...", false);
			fbHandler.getFbFriends();

		} else {
			Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();
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
	public void onFbFetchFriendSuccess(final List<GraphUser> users,
			Response response) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setList(users);
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

	}

	@Override
	public void onFbLoginFailed(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbLoginSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	private void setList(List<GraphUser> users) {

		if (users != null) {

			FriendBean friend;
			for (int i = 0; i < users.size(); i++) {
				friend = new FriendBean();
				friend.setFriendName(users.get(i).getName());
				friend.setFbId(users.get(i).getId());
				System.out.println(users.get(i).getId());
				friend.setFriendImg("http://graph.facebook.com/"
						+ friend.getFbId() + "/picture");
				String username = users.get(i).getUsername();
				friends.add(friend);

			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intentFrendDetails = new Intent(FriendListAcitivity.this,
				FriendDetailsActivity.class);
		FriendBean friend=(FriendBean) arg0.getAdapter().getItem(arg2);
		intentFrendDetails.putExtra("imgUrl", friend.getFriendImg()
				+ "?type=large");
		intentFrendDetails.putExtra("userId", friend.getFbId());
		intentFrendDetails.putExtra("userName", friend.getFriendName());
		startActivity(intentFrendDetails);
	}

	@Override
	public void onFbCommentSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFbPostCommentSuccess(Response response) {
		// TODO Auto-generated method stub

	}
}
