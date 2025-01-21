/*******************************************************************************
 * Copyright (c) 2011 itemis AG and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.LineAttributes;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Shape;

import org.junit.jupiter.api.Test;

/**
 * @author nyssen
 *
 */
public class ShapeTest {

	/**
	 * Test case to demonstate bug #297223
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLineStyleBackwardsCompatibility() {
		LineAttributes attributes = new LineAttributes(1);
		attributes.style = SWT.LINE_DASHDOT;
		Shape shape = new Shape() {

			@Override
			protected void fillShape(Graphics graphics) {
				// NOTHING TO DO
			}

			@Override
			protected void outlineShape(Graphics graphics) {
				// NOTHING TO DO
			}

		};
		shape.setLineAttributes(attributes);
		assertEquals(SWT.LINE_DASHDOT, shape.getLineStyle());
	}

	@SuppressWarnings("static-method")
	@Test
	public void testLineWidthBackwardsCompatibility() {
		LineAttributes attributes = new LineAttributes(4);
		Shape shape = new Shape() {

			@Override
			protected void fillShape(Graphics graphics) {
				// NOTHING TO DO
			}

			@Override
			protected void outlineShape(Graphics graphics) {
				// NOTHING TO DO
			}

		};
		shape.setLineAttributes(attributes);
		assertEquals(4, shape.getLineWidth());
	}
}
