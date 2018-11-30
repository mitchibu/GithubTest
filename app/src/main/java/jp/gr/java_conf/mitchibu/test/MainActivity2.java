package jp.gr.java_conf.mitchibu.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jp.gr.java_conf.mitchibu.test.api.AccessToken;
import jp.gr.java_conf.mitchibu.test.api.AuthorizationInterceptor;
import jp.gr.java_conf.mitchibu.test.api.Client;
import jp.gr.java_conf.mitchibu.test.api.Column;
import jp.gr.java_conf.mitchibu.test.api.Project;
import jp.gr.java_conf.mitchibu.test.api.Repository;
import jp.gr.java_conf.mitchibu.test.api.User;
import jp.gr.java_conf.mitchibu.test.graphql.GraphQl;
import jp.gr.java_conf.mitchibu.test.graphql.Test;

public class MainActivity2 extends AppCompatActivity {
	static final String CLIENT_ID = "6b749b9a562904a8c997";
	static final String CLIENT_SECRET = "1f7eb4dcf98f5a172ef0eebcf32000d885f0dc2d";

//	String accessToken;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Client client = new Client(this);
		Intent intent = getIntent();
		String action = intent.getAction();
		if(Intent.ACTION_VIEW.equals(action)) {
			Uri uri = intent.getData();
			if(uri != null) {
				String path = uri.getPath();
				if("/authorized".equals(uri.getPath())) {
					android.util.Log.v("test", "URI: " + uri.toString());
					String code = uri.getQueryParameter("code");
					//https://github.com/login/oauth/access_token?code=<認証で得られたcode>&client_id=<OAuthアプリケーションの登録時のClient ID>&client_secret=<OAuthアプリケーションの登録時のClient Secret>
					client.accessToken(code, CLIENT_ID, CLIENT_SECRET)
							.flatMap(new Function<AccessToken, SingleSource<User>>() {
								@Override
								public SingleSource<User> apply(AccessToken accessToken) throws Exception {
									AuthorizationInterceptor.accessToken = accessToken.accessToken;
									android.util.Log.v("test", "accessToken: " + accessToken.accessToken);
									return client.user();
								}
							})
							.flatMap(new Function<User, SingleSource<List<Repository>>>() {
								@Override
								public SingleSource<List<Repository>> apply(User user) throws Exception {
									return client.repos(/*user.login*/);
								}
							})
							.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<List<Repository>>() {
								@Override
								public void accept(List<Repository> repositories) throws Exception {
									for(Repository repository : repositories) {
										android.util.Log.v("test", "repos: " + repository.name);
									}
									client.projects("mitchibu", "Misc")
											.subscribeOn(Schedulers.io())
											.observeOn(AndroidSchedulers.mainThread())
											.subscribe(new Consumer<List<Project>>() {
												@Override
												public void accept(List<Project> s) throws Exception {
													for(Project project : s) {
														android.util.Log.v("test", project.name);
														client.columns(project.id)
																.subscribeOn(Schedulers.io())
																.observeOn(AndroidSchedulers.mainThread())
																.subscribe(new Consumer<List<Column>>() {
																	@Override
																	public void accept(List<Column> columns) throws Exception {
																		for(Column column : columns) {
																			android.util.Log.v("test", "column: " + column.name);
																		}
																		client.graphQl3(MainActivity2.this, R.raw.test1)
																				.subscribeOn(Schedulers.io())
																				.observeOn(AndroidSchedulers.mainThread())
																				.subscribe(new Consumer<GraphQl.Data<Test>>() {
																					@Override
																					public void accept(GraphQl.Data<Test> data) throws Exception {
																						android.util.Log.v("test", "responseBody: " + data.data.repository.name);
																					}
																				}, new Consumer<Throwable>() {
																					@Override
																					public void accept(Throwable throwable) throws Exception {
																						throwable.printStackTrace();
																					}
																				});
																	}
																}, new Consumer<Throwable>() {
																	@Override
																	public void accept(Throwable throwable) throws Exception {
																		throwable.printStackTrace();
																	}
																});
													}
												}
											}, new Consumer<Throwable>() {
												@Override
												public void accept(Throwable throwable) throws Exception {
													throwable.printStackTrace();
												}
											});
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									throwable.printStackTrace();
								}
							});
//							.subscribe(new Consumer<User>() {
//								@Override
//								public void accept(User s) throws Exception {
//									android.util.Log.v("test", "login: " + s.login);
//									android.util.Log.v("test", "id: " + s.id);
//									android.util.Log.v("test", "url: " + s.url);
//								}
//							}, new Consumer<Throwable>() {
//								@Override
//								public void accept(Throwable throwable) throws Exception {
//									throwable.printStackTrace();
//								}
//							});
//							.subscribe(new Consumer<AccessToken>() {
//								@Override
//								public void accept(AccessToken s) throws Exception {
//									android.util.Log.v("test", "access_token: " + s.accessToken);
//								}
//							}, new Consumer<Throwable>() {
//								@Override
//								public void accept(Throwable throwable) throws Exception {
//									throwable.printStackTrace();
//								}
//							});
					return;
				}
			}
		}
		Uri.Builder builder = Uri.parse("https://github.com").buildUpon();
		builder.path("login/oauth/authorize");
		builder.appendQueryParameter("client_id", CLIENT_ID);
		builder.appendQueryParameter("scope", "repo");
//		builder.appendQueryParameter("redirect_uri", getPackageName() + "://test");
//		new CustomTabsIntent.Builder()
//				.build()
//				.launchUrl(this, builder.build());
		startActivity(new Intent(Intent.ACTION_VIEW, builder.build()));
	}
}
