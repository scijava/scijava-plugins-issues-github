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

package org.scijava.issues;

import java.io.IOException;
import java.util.Collection;

import org.scijava.plugin.HandlerPlugin;
import org.scijava.plugin.Plugin;

/**
 * A plugin which knows how to file an issue for a particular issue tracker.
 * <p>
 * Issue handler plugins discoverable at runtime must implement this interface
 * and be annotated with @{@link Plugin} with attribute {@link Plugin#type()} =
 * {@link IssueHandler}.class. While it is possible to create an issue handler
 * plugin merely by implementing this interface, it is encouraged to instead
 * extend {@link AbstractIssueHandler}, for convenience.
 * </p>
 *
 * @author Curtis Rueden
 */
public interface IssueHandler extends HandlerPlugin<SoftwareComponent> {

	/**
	 * Files the specified issue in the appropriate issue tracker.
	 *
	 * @throws IOException if something goes wrong reporting the issue.
	 */
	void reportIssue(IssueReport report) throws IOException;

	/**
	 * Gets a list of known software components compatible with this handler.
	 *
	 * @param components Collection into which the discovered software components
	 *          should be added.
	 */
	void findComponents(final Collection<SoftwareComponent> components);

}
