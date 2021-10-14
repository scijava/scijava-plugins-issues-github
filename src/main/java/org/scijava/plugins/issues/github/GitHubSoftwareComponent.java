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

import org.scijava.issues.SoftwareComponent;

/**
 * Software component that tracks its issues using <a
 * href="https://github.com/">GitHub</a> Issues.
 *
 * @author Curtis Rueden
 */
public class GitHubSoftwareComponent implements SoftwareComponent {

	/** Name of the software component. */
	private String name;

	/** GitHub organization or username where the component resides. */
	private String org;

	/** GitHub repository where the component resides. */
	private String repo;

	public GitHubSoftwareComponent(final String name, final String org,
		final String repo)
	{
		setName(name);
		setOrganization(org);
		setRepository(repo);
	}

	// -- GitHubSoftwareComponent methods --

	/**
	 * Gets the organization (or username in the case of individuals) where the
	 * the component resides.
	 */
	public String getOrganization() {
		return org;
	}

	/**
	 * Sets the organization (or username in the case of individuals) where the
	 * component resides.
	 */
	public void setOrganization(final String org) {
		this.org = org;
	}

	/** Gets the repository where the component resides. */
	public String getRepository() {
		return repo;
	}

	/** Sets the repository where the component resides. */
	public void setRepository(final String repo) {
		this.repo = repo;
	}

	// -- Named methods --

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

}
