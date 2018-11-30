package jp.gr.java_conf.mitchibu.test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import jp.gr.java_conf.mitchibu.test.github.GithubClient;
import jp.gr.java_conf.mitchibu.test.graphql.GraphQl;

public class MainActivity extends AppCompatActivity {
	static final String CLIENT_ID = "6b749b9a562904a8c997";
	static final String CLIENT_SECRET = "1f7eb4dcf98f5a172ef0eebcf32000d885f0dc2d";
//	static final String CLIENT_ID = "1692dc45674361ad70c1";
//	static final String CLIENT_SECRET = "f5f4bab41c83da49248a0018764355d5ffd32b49";

	private final CompositeDisposable disposables = new CompositeDisposable();

	private GithubClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		client = new GithubClient(this, CLIENT_ID, CLIENT_SECRET);

		Intent intent = getIntent();
		String action = intent.getAction();
		Uri uri = getIntent().getData();
		if(Intent.ACTION_MAIN.equals(action)) {
			if(TextUtils.isEmpty(client.token())) {
				startActivity(new Intent(Intent.ACTION_VIEW, client.authorizeUri()));
			} else {
				handleGithubInitialized();
			}
		} else if(Intent.ACTION_VIEW.equals(action) && uri != null) {
			if("/authorized".equals(uri.getPath())) {
				disposables.add(client.accessToken(uri)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(accessToken -> handleGithubInitialized(), Throwable::printStackTrace));
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		disposables.dispose();
	}

	private void handleGithubInitialized() {
/*		GraphQl.Query query = new GraphQl.Query();
		try {
			query.loadQueryFromRawResource(this, R.raw.viewer);
		} catch(IOException e) {
			e.printStackTrace();
		}
		query.put("login", "mitchibu");
		query.put("owner", "mitchibu");
		query.put("name", "Misc");
//		disposables.add(client.query(query)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(data -> {}, Throwable::printStackTrace));
		disposables.add(client.query()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(data -> {}, Throwable::printStackTrace));*/
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new OrganizationsFragment()).commit();
	}
}
