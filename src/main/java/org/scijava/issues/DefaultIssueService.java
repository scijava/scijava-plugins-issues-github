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
import java.util.ArrayList;
import java.util.List;

import org.scijava.plugin.AbstractHandlerService;
import org.scijava.plugin.Plugin;
import org.scijava.service.Service;

/**
 * Default service for managing issue reports.
 *
 * @author Curtis Rueden
 */
@Plugin(type = Service.class)
public class DefaultIssueService extends
	AbstractHandlerService<SoftwareComponent, IssueHandler> implements
	IssueService
{

	@Override
	public void reportIssue(final IssueReport report) throws IOException {
		final IssueHandler handler = getHandler(report.getComponent());
		if (handler == null) {
			throw new IllegalArgumentException(
				"No known issue reporter plugin for component: " +
					report.getComponent().getName());
		}
		handler.reportIssue(report);
	}

	@Override
	public List<SoftwareComponent> findComponents() {
		final ArrayList<SoftwareComponent> components =
			new ArrayList<SoftwareComponent>();
		for (final IssueHandler handler : getInstances()) {
			handler.findComponents(components);
		}
		return components;
	}

	// -- PTService methods --

	@Override
	public Class<IssueHandler> getPluginType() {
		return IssueHandler.class;
	}

	// -- Typed methods --

	@Override
	public Class<SoftwareComponent> getType() {
		return SoftwareComponent.class;
	}

}
