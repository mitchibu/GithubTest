package jp.gr.java_conf.mitchibu.test.model;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Organization {
	@BindingAdapter("android:src")
	public static void loadImage(ImageView view, String avatarUrl) {
		Picasso.get().load(Uri.parse(avatarUrl)).fit().centerInside().into(view);
	}

	// Connections
//	public UserConnection members;
	public RepositoryConnection pinnedRepositories;
//	public ProjectConnection projects;
	public RepositoryConnection repositories;
//	public TeamConnection teams;

	// Fields
	public String avatarUrl;
	public int databaseId;
	public String email;
	public String id;
	public boolean isVarified;
	public String location;
	public String login;
	public String name;
	public String newTeamResourcePath;
	public String newTeamUrl;
	public String organizationBillingEmail;
//	public Project project;
	public String projectsResourcePath;
	public String projectsUrl;
	public Repository repository;
	public boolean requiresTwoFactorAuthentication;
	public String resourcePath;
//	public OrganizationIdentityProvider samlIdentityProvider;
//	public Team team;
	public String teamsResourcePath;
	public String teamsUrl;
	public String url;
	public boolean viewerCanAdminister;
	public boolean viewerCanCreateProjects;
	public boolean viewerCanCreateRepositories;
	public boolean viewerCanCreateTeams;
	public boolean viewerIsAMember;
	public String websiteUrl;
}
