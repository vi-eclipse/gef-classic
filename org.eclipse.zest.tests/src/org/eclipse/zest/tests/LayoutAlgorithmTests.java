/*******************************************************************************
 * Copyright (c) 2011, 2025 Fabian Steeg and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Fabian Steeg - initial tests
 *******************************************************************************/
package org.eclipse.zest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.zest.core.widgets.DAGExpandCollapseManager;
import org.eclipse.zest.core.widgets.DefaultSubgraph;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.interfaces.LayoutContext;

import org.junit.Test;

/**
 * Tests involving the {@link LayoutAlgorithm} interface.
 *
 * @author Fabian Steeg (fsteeg)
 *
 */
public class LayoutAlgorithmTests {

	/**
	 * Access items laid out in a custom layout algorithm (see
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=337144)
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testCustomLayoutSimpleItemAccess() {
		Graph graph = new Graph(new Shell(), SWT.NONE);
		new GraphNode(graph, SWT.NONE);
		graph.setLayoutAlgorithm(new AbstractLayoutAlgorithm() {
			@Override
			public void setLayoutContext(LayoutContext context) {
				Item[] all = context.getEntities()[0].getItems();
				Item[] nodes = context.getNodes()[0].getItems();
				assertEquals(1, all.length);
				assertEquals(1, nodes.length);
				assertTrue("All entity items should be wrapped in a GraphNode[]", all instanceof GraphNode[]); //$NON-NLS-1$
				assertTrue("Node entity items should be wrapped in a GraphNode[]", nodes instanceof GraphNode[]); //$NON-NLS-1$
				assertTrue("All entity items should be GraphNode instances", all[0] instanceof GraphNode); //$NON-NLS-1$
				assertTrue("Node entity items should be GraphNode instances", nodes[0] instanceof GraphNode); //$NON-NLS-1$
			}

			@Override
			public void applyLayout(boolean clean) {
			}
		}, true);
	}

	/**
	 * Access items laid out in a custom layout algorithm (see
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=337144)
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testCustomLayoutSubgraphItemAccess() {
		Graph graph = new Graph(new Shell(), SWT.NONE);
		graph.setSubgraphFactory(new DefaultSubgraph.PrunedSuccessorsSubgraphFactory());
		graph.setExpandCollapseManager(new DAGExpandCollapseManager());
		GraphNode n1 = new GraphNode(graph, SWT.NONE);
		GraphNode n2 = new GraphNode(graph, SWT.NONE);
		GraphNode n3 = new GraphNode(graph, SWT.NONE);
		new GraphConnection(graph, SWT.NONE, n1, n2);
		new GraphConnection(graph, SWT.NONE, n1, n3);
		graph.setLayoutAlgorithm(new AbstractLayoutAlgorithm() {
			@Override
			public void setLayoutContext(LayoutContext context) {
				assertEquals(1, context.getSubgraphs().length);
				Item[] sub = context.getSubgraphs()[0].getItems();
				assertEquals(3, sub.length);
				assertTrue("All subgraph items should be wrapped in a GraphNode[]", sub instanceof GraphNode[]); //$NON-NLS-1$
				assertTrue("All subgraph items should be GraphNode instances", sub[0] instanceof GraphNode); //$NON-NLS-1$
			}

			@Override
			public void applyLayout(boolean clean) {
			}
		}, true);
	}

	/**
	 * Access items laid out in a custom layout algorithm (see
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=337144)
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testCustomLayoutSubgraphFilteredAccess() {
		Graph graph = new Graph(new Shell(), SWT.NONE);
		graph.setSubgraphFactory(new DefaultSubgraph.PrunedSuccessorsSubgraphFactory());
		graph.setExpandCollapseManager(new DAGExpandCollapseManager());
		GraphNode n1 = new GraphNode(graph, SWT.NONE);
		GraphNode n2 = new GraphNode(graph, SWT.NONE);
		GraphNode n3 = new GraphNode(graph, SWT.NONE);
		new GraphConnection(graph, SWT.NONE, n1, n2);
		new GraphConnection(graph, SWT.NONE, n1, n3);
		graph.addLayoutFilter(item -> item == n2);
		graph.setLayoutAlgorithm(new AbstractLayoutAlgorithm() {
			@Override
			public void setLayoutContext(LayoutContext context) {
				assertEquals(1, context.getSubgraphs().length);
				Item[] sub = context.getSubgraphs()[0].getItems();
				assertEquals(2, sub.length); // one filtered
			}

			@Override
			public void applyLayout(boolean clean) {
			}
		}, true);
	}

	/**
	 * Attempt to reproduce an infinite loop with GridLayoutAlgorithm on an empty
	 * graph (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=382791)
	 */
	@Test
	@SuppressWarnings("static-method")
	public void testGridLayoutAlgorithmEmptyGraph() {
		Graph graph = new Graph(new Shell(), SWT.NONE);
		graph.setLayoutAlgorithm(new GridLayoutAlgorithm(), true);
		assertEquals(GridLayoutAlgorithm.class, graph.getLayoutAlgorithm().getClass());
	}
}
