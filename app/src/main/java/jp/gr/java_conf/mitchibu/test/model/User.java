package jp.gr.java_conf.mitchibu.test.model;

public class User {
	// Connections
//	public CommitCommentConnection commitComment;
//	public FollowerConnection followers;
//	public FollowingConnection following;
//	public GistCommentConnection gistComments;
//	public GistConnection gists;
//	public IssueCommentConnection issueComments;
//	public IssueConnection issues;
	public OrganizationConnection organizations;
//	public PinnedRepositoriesConnection pinnedRepositories;
//	public PublicKeyConnection publicKeys;
//	public PullRequestConnection pullRequests;
	public RepositoryConnection repositories;
//	public RepositoriesConnection repositoriesContributedTo;
//	public StarredRepositoryConnection starredRepositories;
//	public RepositoryConnection watching;

	//Fields
	public String avatarUrl;
	public String bio;
//	public String bioHTML;
	public String company;
//	public String companyHTML;
	public String createdAt;
	public int databaseId;
	public String email;
//	public Gist gist;
//	public Hovercard hovercard;
	public String id;
	public boolean isBountyHunter;
	public boolean isCampusExpert;
	public boolean isDeveloperProgramMember;
	public boolean isEmployee;
	public boolean isHireable;
	public boolean isSiteAdmin;
	public boolean isViewer;
	public String location;
	public String login;
	public String name;
//	public Organization organization;
	public Repository repository;
	public String resourcePath;
	public String updatedAt;
	public String url;
	public boolean viewerCanFollow;
	public boolean viewerIsFollowing;
	public String websiteUrl;
}
