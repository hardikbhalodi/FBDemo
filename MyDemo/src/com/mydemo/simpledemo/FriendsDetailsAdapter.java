package com.mydemo.simpledemo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

public class FriendsDetailsAdapter extends SectionedBaseAdapter implements
		OnClickListener, OnFocusChangeListener {
	private ArrayList<BeanComment> commentList;
	private Context context;
	ViewHolder holder;
	ViewHolderSeciton sectionholder;
	AQuery aq;
	String strUrl;
	private ImageOptions options;
	private OnInflatedLayoutComponentClickListener listener;
	private String strTmp = "";

	public FriendsDetailsAdapter(ArrayList<BeanComment> commentList,
			Context context, final String url) {
		// TODO Auto-generated constructor stub
		this.commentList = commentList;
		this.context = context;
		this.strUrl = url;
		options = new ImageOptions();
		options.memCache = true;
		options.fileCache = true;
		options.round = 5;
		listener = (OnInflatedLayoutComponentClickListener) context;
	}

	@Override
	public Object getItem(int section, int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int section, int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSectionCount() {
		// TODO Auto-generated method stub
		if (commentList.size() > 0) {
			return 3;
		} else {
			return 2;
		}

	}

	@Override
	public int getCountForSection(int section) {
		// TODO Auto-generated method stub

		int count = 0;
		if (commentList.size() > 0) {
			switch (section) {
			case 0:
				count = 1;
				break;
			case 1:
				count = commentList.size();
				break;
			case 2:
				count = 1;
				break;

			}
		} else {
			switch (section) {
			case 0:
				count = 1;
				break;

			case 1:
				count = 1;
				break;

			}

		}
		return count;

	}

	@Override
	public View getItemView(int section, int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			LayoutInflater inflator = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator
					.inflate(R.layout.friend_details_layout, null);
			holder = new ViewHolder();
			holder.tvComment = (TextView) convertView
					.findViewById(R.id.tvComment);
			holder.tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);
			holder.ivUserImage = (ImageView) convertView
					.findViewById(R.id.ivUserImage);
			holder.etEnterComment = (EditText) convertView
					.findViewById(R.id.etEnterComment);
			holder.etEnterComment.setOnFocusChangeListener(this);
			holder.rellayoutUserImage = (RelativeLayout) convertView
					.findViewById(R.id.rellayoutUserImage);
			holder.rellayoutPostComment = (RelativeLayout) convertView
					.findViewById(R.id.rellayoutPostComment);
			holder.rellayoutUserComment = (RelativeLayout) convertView
					.findViewById(R.id.rellayoutUserComment);
			holder.pBar = (ProgressBar) convertView
					.findViewById(R.id.progressBarDetails);
			holder.btnPostComment = (Button) convertView
					.findViewById(R.id.btnPostComment);
			holder.btnPostComment.setOnClickListener(this);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/* VISIBLE ALL VIEWS */
		aq = new AQuery(convertView);
		holder.rellayoutUserComment.setVisibility(View.VISIBLE);
		holder.rellayoutPostComment.setVisibility(View.VISIBLE);
		holder.rellayoutUserImage.setVisibility(View.VISIBLE);
		holder.etEnterComment.setTag(position);
		if (commentList.size() > 0) {
			switch (section) {
			case 0:
				holder.rellayoutUserComment.setVisibility(View.GONE);
				holder.rellayoutPostComment.setVisibility(View.GONE);
				holder.rellayoutUserImage.setVisibility(View.VISIBLE);
				aq.id(holder.ivUserImage).progress(holder.pBar)
						.image(strUrl, options);
				break;
			case 1:
				holder.rellayoutUserImage.setVisibility(View.GONE);
				holder.rellayoutPostComment.setVisibility(View.GONE);
				holder.rellayoutUserComment.setVisibility(View.VISIBLE);
				holder.tvComment
						.setText(commentList.get(position).getComment());
				holder.tvFrom.setText(commentList.get(position).getFromUser()
						+ ": ");

				break;
			case 2:
				holder.rellayoutUserImage.setVisibility(View.GONE);
				holder.rellayoutUserComment.setVisibility(View.GONE);
				holder.rellayoutPostComment.setVisibility(View.VISIBLE);
				holder.btnPostComment.setTag(position);
				holder.etEnterComment.setTag(position);
				break;
			}
		} else {
			switch (section) {
			case 0:
				holder.rellayoutUserComment.setVisibility(View.GONE);
				holder.rellayoutPostComment.setVisibility(View.GONE);
				holder.rellayoutUserImage.setVisibility(View.VISIBLE);
				aq.id(holder.ivUserImage).progress(holder.pBar)
						.image(strUrl, options);

				break;
			case 1:
				holder.rellayoutUserImage.setVisibility(View.GONE);
				holder.rellayoutUserComment.setVisibility(View.GONE);
				holder.rellayoutPostComment.setVisibility(View.VISIBLE);

				holder.btnPostComment.setTag(position);
				break;

			}
		}

		return convertView;
	}

	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			LayoutInflater inflator = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.list_header, null);
			sectionholder = new ViewHolderSeciton();
			sectionholder.tvHeader = (TextView) convertView
					.findViewById(R.id.tvListHeader);
			convertView.setTag(sectionholder);
		} else {
			sectionholder = (ViewHolderSeciton) convertView.getTag();
		}
		if (commentList.size() > 0) {
			switch (section) {
			case 0:
				sectionholder.tvHeader.setText("User Profile Picture");
				break;
			case 1:
				sectionholder.tvHeader.setText("Users Comment");
				break;
			case 2:
				sectionholder.tvHeader.setText("Enter Comment");
				break;

			}
		} else {
			switch (section) {
			case 0:
				sectionholder.tvHeader.setText("User Profile Picture");
				break;

			case 1:
				sectionholder.tvHeader.setText("Enter Comment");
				break;

			}
		}

		return convertView;
	}

	class ViewHolder {
		public ImageView ivUserImage;
		public TextView tvComment;
		public TextView tvFrom;
		public EditText etEnterComment;
		public ProgressBar pBar;
		public RelativeLayout rellayoutUserImage, rellayoutUserComment,
				rellayoutPostComment;
		public Button btnPostComment;

	}

	class ViewHolderSeciton {

		public TextView tvHeader;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer pos = (Integer) v.getTag();

		listener.onInflatedLayoutComponentClick(v, pos, strTmp);

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (!hasFocus) {
			final int position = (Integer) v.getTag();
			final EditText caption = (EditText) v;
			strTmp = caption.getText().toString();

		}

	}

}
