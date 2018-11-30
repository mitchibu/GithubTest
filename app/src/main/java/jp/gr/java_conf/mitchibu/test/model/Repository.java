package jp.gr.java_conf.mitchibu.test.model;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Repository {
	@BindingAdapter("android:src")
	public static void loadImage(ImageView view, String avatarUrl) {
		Picasso.get().load(Uri.parse(avatarUrl)).fit().centerInside().into(view);
	}

	// Connections
//	public String assignableUsers;
//	public String branchProtectionRules;
//	public String collaborators;
//	public String commitComments;
//	public String dependencyGraphManifests;
//	public String deployKeys;
//	public String deployments;
//	public String forks;
//	public String issues;
//	public String labels;
//	public String languages;
//	public String mentionableUsers;
//	public String milestones;
//	public String projects;
//	public String protectedBranches;
//	public String pullRequests;
//	public String refs;
//	public String releases;
//	public String repositoryTopics;
//	public String stargazers;
//	public String vulnerabilityAlerts;
//	public String watchers;

	// Fields
//	public CodeOfConduct codeOfConduct;
	public String createdAt;
	public int databaseId;
//	public Ref defaultBranchRef;
	public String description;
//	public String descriptionHTML;
	public int diskUsage;
	public int forkCount;
	public boolean hasIssuesEnabled;
	public boolean hasWikiEnabled;
//	public String homepageUrl;
	public String id;
	public boolean isArchived;
	public boolean isFork;
	public boolean isLocked;
	public boolean isMirror;
	public boolean isPrivate;
//	public Issue issue;
//	public IssueOrPullRequest issueOrPullRequest;
//	public Label label;
//	public License licenseInfo;
//	public RepositoryLockReason lockReason;
	public boolean mergeCommitAllowed;
//	public Milestone milestone;
	public String mirrorUrl;
	public String name;
	public String nameWithOwner;
//	public GitObject object;
//	public RepositoryOwner owner;
	public Repository parent;
//	public Language primaryLanguage;
//	public Project project;
	public String projectsResourcePath;
	public String projectsUrl;
//	public PullRequest pullRequest;
	public String pushedAt;
	public boolean rebaseMergeAllowed;
//	public Ref ref;
//	public Release release;
	public String resourcePath;
//	public String shortDescriptionHTML;
	public boolean squashMergeAllowed;
//	public GitSSHRemote sshUrl;
	public String tempCloneToken;
	public String updatedAt;
	public String url;
	public boolean viewerCanAdminister;
	public boolean viewerCanCreateProjects;
	public boolean viewerCanSubscribe;
	public boolean viewerCanUpdateTopics;
	public boolean viewerHasStarred;
//	public RepositoryPermission viewerPermission;
//	public SubscriptionState viewerSubscription;
}
