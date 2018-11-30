package jp.gr.java_conf.mitchibu.test.github;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import jp.gr.java_conf.mitchibu.test.R;
//import jp.gr.java_conf.mitchibu.test.graphql.GraphQl;
import jp.gr.java_conf.mitchibu.test.model.GraphQl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class GithubClient {
	static final String TOKEN = GithubClient.class.getName() + ".preferences.TOKEN";
	static final String BASE_GITHUB = "https://github.com";
	static final String BASE_API = "https://api.github.com";
//	static final String BASE_GITHUB = "https://ghe.apctdl.com";
//	static final String BASE_API = "https://ghe.apctdl.com";

	private final SharedPreferences sp;
	private final Context context;
	private final String clientId;
	private final String clientSecret;
	private final Github github;

	public GithubClient(Context context) {
		this(context, null, null);
	}

	public GithubClient(Context context, String clientId, String clientSecret) {
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		this.context = context;
		this.clientId = clientId;
		this.clientSecret = clientSecret;

		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.addInterceptor(new AuthorizationInterceptor(sp))
				.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
				.build();
		github = new Retrofit.Builder()
				.baseUrl(BASE_API)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
				.client(client)
				.build()
				.create(Github.class);
	}

	public String token() {
		String s = sp.getString(TOKEN, null);
		if(s != null) android.util.Log.v("test", s);
		return s;
	}

	public Uri authorizeUri() {
		Uri.Builder builder = Uri.parse(BASE_GITHUB).buildUpon();
		builder.path("login/oauth/authorize");
		builder.appendQueryParameter("client_id", clientId);
		builder.appendQueryParameter("scope", "public_repo read:org admin:org");
		return builder.build();
	}

	public Single<AccessToken> accessToken(Uri callbackUri) {
		return github.accessToken(callbackUri.getQueryParameter("code"), clientId, clientSecret)
				.map(accessToken -> {
					token(accessToken.accessToken);
					return accessToken;
				});
	}

	public Single<GraphQl> organizations() {
		try {
			GraphQl query = GraphQl.queryFromRawResource(context, R.raw.organizations);
			return github.query(query);
		} catch(IOException e) {
			e.printStackTrace();
			return Single.error(e);
		}
	}

	private void token(String token) {
		sp.edit().putString(TOKEN, token).apply();
	}

	public interface Github {
		@GET(BASE_GITHUB + "/login/oauth/access_token")
		@Headers({
				"Accept: application/json"
		})
		Single<AccessToken> accessToken(@Query("code") String code, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

//		@POST("/api/graphql")
		@POST("/graphql")
		@Headers({
				"Accept: application/json"
		})
		Single<GraphQl> query(@Body GraphQl query);
	}
}
