/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.ui.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;

/**
 * This class serves as a quick starting point for clients who are new to GEF.
 * It will create an Editor containing a Split composite, with one side
 * contianing a PaletteViewer, and the other a GraphicalViewer.
 *
 * @author hudsonr
 */
public abstract class GraphicalEditorWithPalette extends GraphicalEditor {

	private static final int PALETTE_SIZE = 125;

	private PaletteViewer paletteViewer;

	/**
	 * Called to configure the viewer before it receives its contents.
	 */
	protected void configurePaletteViewer() {
	}

	/**
	 * Creates the palette on the given composite.
	 *
	 * @param parent the composite
	 */
	protected void createPaletteViewer(Composite parent) {
		PaletteViewer viewer = new PaletteViewer();
		setPaletteViewer(viewer);
		viewer.createControl(parent);
		configurePaletteViewer();
		hookPaletteViewer();
		initializePaletteViewer();
	}

	private void updateZoom(int zoom) {
		ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(ZoomManager.class.toString());
		manager.setInvisibleUiMultiplier(zoom / 100d);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.addListener(SWT.ZoomChanged, event -> {
			updateZoom(event.detail);
		});
		Splitter splitter = new Splitter(parent, SWT.HORIZONTAL);
		createPaletteViewer(splitter);
		createGraphicalViewer(splitter);
		splitter.maintainSize(getPaletteViewer().getControl());
		splitter.setFixedSize(getInitialPaletteSize());
		splitter.addFixedSizeChangeListener(evt -> handlePaletteResized(((Splitter) evt.getSource()).getFixedSize()));
		updateZoom(parent.nativeZoom);
	}

	/**
	 * Returns the PaletteRoot for the palette viewer.
	 *
	 * @return the palette root
	 */
	protected abstract PaletteRoot getPaletteRoot();

	/**
	 * Returns the initial palette size in pixels. Subclasses may override this
	 * method to return a persisted value.
	 *
	 * @see #handlePaletteResized(int)
	 * @return the initial size of the palette in pixels.
	 */
	@SuppressWarnings("static-method")
	protected int getInitialPaletteSize() {
		return PALETTE_SIZE;
	}

	/**
	 * Returns the PaletteViewer.
	 *
	 * @return the palette viewer
	 */
	protected PaletteViewer getPaletteViewer() {
		return paletteViewer;
	}

	/**
	 * Called whenever the user resizes the palette.
	 *
	 * @param newSize the new size in pixels
	 */
	protected void handlePaletteResized(int newSize) {
	}

	/**
	 * Called when the palette viewer is set. By default, the EditDomain is given
	 * the palette viewer.
	 */
	protected void hookPaletteViewer() {
		getEditDomain().setPaletteViewer(paletteViewer);
	}

	/**
	 * Called to populate the palette viewer.
	 */
	protected void initializePaletteViewer() {
	}

	/**
	 * Sets the palette viewer
	 *
	 * @param paletteViewer the palette viewer
	 */
	protected void setPaletteViewer(PaletteViewer paletteViewer) {
		this.paletteViewer = paletteViewer;
	}

	/**
	 * Sets the {@link #getPaletteRoot() palette root} of the edit domain
	 *
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#setEditDomain(org.eclipse.gef.DefaultEditDomain)
	 */
	@Override
	protected void setEditDomain(DefaultEditDomain ed) {
		super.setEditDomain(ed);
		getEditDomain().setPaletteRoot(getPaletteRoot());
	}

}
