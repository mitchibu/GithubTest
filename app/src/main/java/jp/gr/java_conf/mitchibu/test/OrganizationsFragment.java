package jp.gr.java_conf.mitchibu.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.gr.java_conf.mitchibu.test.databinding.FragmentOrganizationsBinding;
import jp.gr.java_conf.mitchibu.test.github.GithubClient;

public class OrganizationsFragment extends Fragment {
	private final ItemListAdapter adapter = new ItemListAdapter();

	private FragmentOrganizationsBinding binding;
	private GithubClient client;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = new GithubClient(getContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_organizations, container, false);
		binding.setLifecycleOwner(this);
		binding.list.setAdapter(adapter);
		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		client.organizations()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(data -> {
					adapter.submitList(data.data.viewer.organizations.nodes);
					}, Throwable::printStackTrace);
	}
}
