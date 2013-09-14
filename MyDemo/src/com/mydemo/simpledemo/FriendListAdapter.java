package com.mydemo.simpledemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

public class FriendListAdapter extends BaseAdapter implements Filterable,
		OnClickListener {
	ArrayList<FriendBean> friendList;
	Context context;
	ViewHolder holder;
	private AQuery aquary;
	private ArrayList<FriendBean> tmpFirendsList;
	private Filter friendsFilter;
	private ImageOptions options;

	public FriendListAdapter(ArrayList<FriendBean> friendList, Context context) {
		// TODO Auto-generated constructor stub
		this.friendList = friendList;
		this.tmpFirendsList = friendList;
		this.context = context;
		options = new ImageOptions();
		options.memCache = true;
		options.fileCache = true;
		options.round = 5;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tmpFirendsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tmpFirendsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return tmpFirendsList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.friend_list_layout, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tvFirendName);
			holder.ivPhoto = (ImageView) convertView
					.findViewById(R.id.ivFriendImage);
			holder.pBar = (ProgressBar) convertView
					.findViewById(R.id.ivProgressBar);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		aquary = new AQuery(convertView);

		holder.tvName.setText(tmpFirendsList.get(position).getFriendName());
		aquary.id(holder.ivPhoto).progress(holder.pBar)
				.image(tmpFirendsList.get(position).getFriendImg(), options);

		return convertView;
	}

	class ViewHolder {
		public TextView tvName;
		public ImageView ivPhoto;
		public ProgressBar pBar;
		public Button btnComment;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer pos = (Integer) v.getTag();

	}

	public void resetData() {
		tmpFirendsList = friendList;
	}

	@Override
	public Filter getFilter() {
		if (friendsFilter == null)
			friendsFilter = new FriendFilter();

		return friendsFilter;
	}

	private class FriendFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = friendList;
				results.count = friendList.size();
			} else {
				// We perform filtering operation
				ArrayList<FriendBean> nFriendList = new ArrayList<FriendBean>();

				for (FriendBean f : tmpFirendsList) {
					if (f.getFriendName().toUpperCase()
							.startsWith(constraint.toString().toUpperCase()))
						nFriendList.add(f);

				}

				results.values = nFriendList;
				results.count = nFriendList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// Now we have to inform the adapter about the new list filtered
			// if (results.count == 0)
			// notifyDataSetInvalidated();
			// else {
			tmpFirendsList = (ArrayList<FriendBean>) results.values;
			notifyDataSetChanged();
			// }

		}
	}

}
