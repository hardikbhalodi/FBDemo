package com.mydemo.simpledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;
import com.mydemo.simpledemo.R;

public class FBLogin extends Activity implements OnClickListener {
	Button btnFbLogin, btnShare, btnFirendList, btnProfile;
	FbHandler fbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fb_login);
		fbHandler = new FbHandler(FBLogin.this, savedInstanceState);
		btnFbLogin = (Button) findViewById(R.id.btnFbLogin);
		btnFirendList = (Button) findViewById(R.id.btnFbFriendList);
		btnProfile = (Button) findViewById(R.id.btnFbProfile);
		btnShare = (Button) findViewById(R.id.btnFbShare);
		btnFbLogin.setOnClickListener(this);
		btnFirendList.setOnClickListener(this);
		btnProfile.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		updateUI();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnFbLogin:
			if (btnFbLogin.getText().toString().equalsIgnoreCase("Login")) {
				fbHandler.loginFacebook();
			} else {
				fbHandler.logoutFacebook();
				updateUI();
			}
			break;
		case R.id.btnFbFriendList:
			if (fbHandler.isLoggedIn()) {
				startActivity(new Intent(FBLogin.this,
						FriendListAcitivity.class));
			} else {
				Toast.makeText(this, "Please Login First!", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btnFbShare:
			break;
		case R.id.btnFbProfile:
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Session.getActiveSession().onActivityResult(FBLogin.this,
					requestCode, resultCode, data);
			updateUI();
		}

	}

	private void updateUI() {
		if (fbHandler.isLoggedIn()) {
			btnFbLogin.setText("Logout");
			btnFirendList.setEnabled(true);
			btnProfile.setEnabled(true);
			btnShare.setEnabled(true);
		} else {
			btnFbLogin.setText("Login");
			btnFirendList.setEnabled(false);
			btnProfile.setEnabled(false);
			btnShare.setEnabled(false);
		}
	}

}
