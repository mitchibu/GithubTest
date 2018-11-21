package jp.gr.java_conf.mitchibu.test.graphql;

import java.util.HashMap;
import java.util.Map;

public class QueryContainerBuilder {
	private final QueryContainer container = new QueryContainer();

	public QueryContainerBuilder query(String query) {
		container.query = query;
		return this;
	}

	public QueryContainerBuilder put(String name, Object value) {
		container.variables.put(name, value);
		return this;
	}

	public QueryContainer build() {
		return container;
	}

	public static class QueryContainer {
		private String query;
		private final Map<String, Object> variables = new HashMap<>();
	}
}
