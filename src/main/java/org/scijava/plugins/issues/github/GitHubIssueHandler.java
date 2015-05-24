/*
 * #%L
 * SciJava issue reporting plugin for GitHub.
 * %%
 * Copyright (C) 2015 - 2016 Board of Regents of the University of
 * Wisconsin-Madison.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.plugins.issues.github;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.scijava.issues.AbstractIssueHandler;
import org.scijava.issues.IssueHandler;
import org.scijava.issues.IssueReport;
import org.scijava.issues.SoftwareComponent;
import org.scijava.plugin.Plugin;
import org.scijava.util.POM;

/**
 * Issue handler plugin for <a href="https://github.com/">GitHub</a> Issues.
 *
 * @author Curtis Rueden
 */
@Plugin(type = IssueHandler.class)
public class GitHubIssueHandler extends AbstractIssueHandler {

	// -- Constants --

	/** Regular expression matching GitHub Issues issue management URLs. */
	private static final String GITHUB_ISSUES_URL_REGEX =
		"^https?://github.com/([\\w-]+)/([\\w-]+)/issues$";

	// -- IssueHandler methods --

	@Override
	public void reportIssue(final IssueReport report) throws IOException {
		// validate the input
		final SoftwareComponent component = report.getComponent();
		if (!(component instanceof GitHubSoftwareComponent)) {
			throw new IllegalArgumentException("Incompatible component: " +
				component.getName());
		}
		final GitHubSoftwareComponent ghsc = (GitHubSoftwareComponent) component;

		// create the issue
		final String org = ghsc.getOrganization();
		final String repo = ghsc.getRepository();
		final Issue issue = createIssue(report);

		// file the issue
		final GitHubClient client = createClient(report);
		final IssueService issueService = new IssueService(client);
		issueService.createIssue(org, repo, issue);
	}

	@Override
	public void findComponents(final Collection<SoftwareComponent> components) {
		final Pattern p = Pattern.compile(GITHUB_ISSUES_URL_REGEX);

		// iterate over all Maven components on the classpath; each Maven
		// component has an embedded POM declaring its issue tracker.
		for (final POM pom : POM.getAllPOMs()) {
			final String issuesURL = pom.getIssueManagementURL();
			if (issuesURL == null) continue; // no known issue tracker

			final Matcher m = p.matcher(issuesURL);
			if (!m.matches()) continue; // incompatible issue tracker

			// component uses GitHub Issues!
			final String name = projectName(pom);
			final String org = m.group(1);
			final String repo = m.group(2);
			components.add(new GitHubSoftwareComponent(name, org, repo));
		}
	}

	// -- Typed methods --

	@Override
	public boolean supports(final SoftwareComponent component) {
		// We only support software components that use GitHub Issues!
		return component instanceof GitHubSoftwareComponent;
	}

	// -- Helper methods --

	private Issue createIssue(final IssueReport report) {
		final Issue issue = new Issue();
		issue.setTitle(report.getSummary());
		issue.setBody(report.getDescription());
		return issue;
	}

	private GitHubClient createClient(final IssueReport report) {
		final GitHubClient client = new GitHubClient();
		// TODO: OAuth2 authentication.
		final String user = report.getUsername();
		final String pass = report.getPassword();
		client.setCredentials(user, pass);
		return client;
	}

	/** Extracts a friendly project name from the given software component POM. */
	private String projectName(final POM pom) {
		final String projectName = pom.getProjectName();
		if (projectName != null) return projectName;
		return pom.getGroupId() + ":" + pom.getArtifactId();
	}

}
