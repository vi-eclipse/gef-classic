/*******************************************************************************
 * Copyright 2005-2007, 2024, CHISEL Group, University of Victoria, Victoria,
 *                            BC, Canada and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: The Chisel Group, University of Victoria
 ******************************************************************************/
package org.eclipse.zest.examples.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.examples.Messages;

import org.eclipse.draw2d.ColorConstants;

/**
 * This snippet shows how you can add a paint listener to a Zest graph to paint
 * on top of the widget. This snippet allows you to type and it selects all the
 * nodes that match what you type.
 *
 * @author Ian Bull
 *
 */
public class GraphSnippet5 {
	private static Graph g;
	public static final int BACKSPACE = 8;
	public static final int ENTER = 13;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Map<String, GraphNode> figureListing = new HashMap<>();
		final StringBuffer stringBuffer = new StringBuffer();
		final Shell shell = new Shell();
		final Display d = shell.getDisplay();
		FontData fontData = d.getSystemFont().getFontData()[0];
		fontData.setHeight(42);

		final Font font = new Font(d, fontData);

		shell.setText(Messages.GraphSnippet5_Title);
		Image image1 = Display.getDefault().getSystemImage(SWT.ICON_INFORMATION);
		Image image2 = Display.getDefault().getSystemImage(SWT.ICON_WARNING);
		Image image3 = Display.getDefault().getSystemImage(SWT.ICON_ERROR);
		shell.setLayout(new FillLayout());
		shell.setSize(400, 400);

		g = new Graph(shell, SWT.NONE);
		g.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		GraphNode n1 = new GraphNode(g, SWT.NONE);
		n1.setText(Messages.GraphSnippet5_Information);
		n1.setImage(image1);
		GraphNode n2 = new GraphNode(g, SWT.NONE);
		n2.setText(Messages.GraphSnippet5_Warning);
		n2.setImage(image2);
		GraphNode n3 = new GraphNode(g, SWT.NONE);
		n3.setText(Messages.GraphSnippet5_Error);
		n3.setImage(image3);
		figureListing.put(n1.getText().toLowerCase(), n1);
		figureListing.put(n2.getText().toLowerCase(), n2);
		figureListing.put(n3.getText().toLowerCase(), n3);

		new GraphConnection(g, SWT.NONE, n1, n2);
		new GraphConnection(g, SWT.NONE, n2, n3);
		n1.setLocation(10, 10);
		n2.setLocation(200, 10);
		n3.setLocation(200, 200);

		g.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				boolean complete = false;
				if (e.keyCode == BACKSPACE) {
					if (stringBuffer.length() > 0) {
						stringBuffer.deleteCharAt(stringBuffer.length() - 1);
					}
				} else if (e.keyCode == ENTER) {
					complete = true;
				} else if ((e.character >= 'a' && e.character <= 'z') || (e.character >= 'A' && e.character <= 'Z')
						|| (e.character == '.') || (e.character >= '0' && e.character <= '9')) {
					stringBuffer.append(e.character);
				}
				List<GraphItem> list = new ArrayList<>();
				if (stringBuffer.length() > 0) {
					for (String string : figureListing.keySet()) {
						if (string.indexOf(stringBuffer.toString().toLowerCase()) >= 0) {
							list.add(figureListing.get(string));
						}
					}
				}
				g.setSelection(list.toArray(new GraphItem[list.size()]));
				if (complete && stringBuffer.length() > 0) {
					stringBuffer.delete(0, stringBuffer.length());
				}

				g.redraw();
			}

		});

		g.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setFont(font);
				e.gc.setClipping((Region) null);
				e.gc.setForeground(ColorConstants.black);
				e.gc.drawText(stringBuffer.toString(), 50, 50, true);
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			while (!d.readAndDispatch()) {
				d.sleep();
			}
		}
		font.dispose();
	}
}
