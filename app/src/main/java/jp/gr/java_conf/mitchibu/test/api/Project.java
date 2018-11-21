package jp.gr.java_conf.mitchibu.test.api;

import com.squareup.moshi.Json;

public class Project {
	@Json(name="id")
	public long id;
	@Json(name="node_id")
	public String nodeId;
	@Json(name="name")
	public String name;
}
