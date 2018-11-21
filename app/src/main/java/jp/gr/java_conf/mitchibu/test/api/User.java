package jp.gr.java_conf.mitchibu.test.api;

import com.squareup.moshi.Json;

public class User {
	@Json(name="login")
	public String login;
	@Json(name="id")
	public long id;
	@Json(name="url")
	public String url;
}
