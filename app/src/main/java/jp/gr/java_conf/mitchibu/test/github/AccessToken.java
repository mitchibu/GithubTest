package jp.gr.java_conf.mitchibu.test.github;

import com.squareup.moshi.Json;

public class AccessToken {
	@Json(name="access_token")
	public String accessToken;
	@Json(name="scope")
	public String scope;
	@Json(name="token_type")
	public String tokenType;
}
