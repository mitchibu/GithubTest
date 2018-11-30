package jp.gr.java_conf.mitchibu.test;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import jp.gr.java_conf.mitchibu.test.databinding.ItemSampleBinding;
import jp.gr.java_conf.mitchibu.test.model.Organization;
import jp.gr.java_conf.mitchibu.test.view.BindableAdapter;
import jp.gr.java_conf.mitchibu.test.view.BindableViewHolder;
import jp.gr.java_conf.mitchibu.test.view.OnItemClickListener;

public class ItemListAdapter extends BindableAdapter<Organization> {
	private OnItemClickListener<Organization> onItemClickListener = null;

	public ItemListAdapter() {
		super(new DiffUtil.ItemCallback<Organization>() {
			@Override
			public boolean areItemsTheSame(@NonNull Organization s, @NonNull Organization t1) {
//				return s.id == t1.id;
				return s.equals(t1);
			}

			@Override
			public boolean areContentsTheSame(@NonNull Organization s, @NonNull Organization t1) {
				return s.equals(t1);
			}
		});
	}

	public ItemListAdapter setOnItemClickListener(OnItemClickListener<Organization> listener) {
		onItemClickListener = listener;
		return this;
	}

	@Override
	public ViewDataBinding onCreateDataBinding(@NonNull ViewGroup parent, int viewType) {
		return ItemSampleBinding.inflate((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), parent, false);
	}

	@Override
	public void onBindViewHolder(@NonNull BindableViewHolder holder, int position) {
		((ItemSampleBinding)holder.getBinding()).setItem(getItem(position));
		((ItemSampleBinding)holder.getBinding()).setPosition(position);
		((ItemSampleBinding)holder.getBinding()).setOnItemClickListrener(onItemClickListener);
	}
}
