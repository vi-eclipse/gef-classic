/*******************************************************************************
 * Copyright (c) 2004, 2010 IBM Corporation and others.
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

package org.eclipse.gef.examples.text.model;

import org.eclipse.swt.SWT;

import org.eclipse.draw2d.PositionConstants;

/**
 * @since 3.1
 */
public class Style extends Notifier {

	private static final long serialVersionUID = 1;

	public static final String PROPERTY_ALIGNMENT = "alignment"; //$NON-NLS-1$
	public static final String PROPERTY_FONT = "font"; //$NON-NLS-1$
	public static final String PROPERTY_FONT_SIZE = "fontSize"; //$NON-NLS-1$
	public static final String PROPERTY_BOLD = "bold"; //$NON-NLS-1$
	public static final String PROPERTY_ITALIC = "italics"; //$NON-NLS-1$
	public static final String PROPERTY_UNDERLINE = "underline"; //$NON-NLS-1$
	public static final String PROPERTY_ORIENTATION = "orientation"; //$NON-NLS-1$

	private int alignment = PositionConstants.NONE;
	private int orientation = SWT.NONE;
	private boolean bold;
	private String fontFamily;
	private int fontHeight = -1;
	private boolean italic;
	private Style parentStyle;
	private boolean underline;

	public int getAlignment() {
		if (alignment != PositionConstants.NONE) {
			return alignment;
		}
		if (parentStyle != null) {
			return parentStyle.getAlignment();
		}
		return PositionConstants.NONE;
	}

	public String getFontFamily() {
		if (fontFamily != null) {
			return fontFamily;
		}
		if (parentStyle != null) {
			return parentStyle.getFontFamily();
		}
		return ""; //$NON-NLS-1$
	}

	public int getFontHeight() {
		if (fontHeight != -1) {
			return fontHeight;
		}
		if (parentStyle != null) {
			return parentStyle.getFontHeight();
		}
		return -1;
	}

	public int getOrientation() {
		if (orientation != SWT.NONE) {
			return orientation;
		}
		if (parentStyle != null) {
			return parentStyle.getOrientation();
		}
		return SWT.NONE;
	}

	public boolean isAlignedLeft() {
		return alignment == PositionConstants.LEFT || (parentStyle != null && parentStyle.isAlignedLeft());
	}

	public boolean isBold() {
		return bold || (parentStyle != null && parentStyle.isBold());
	}

	public boolean isItalic() {
		return italic || (parentStyle != null && parentStyle.isItalic());
	}

	public boolean isSet(String property) {
		switch (property) {
		case PROPERTY_BOLD:
			return bold;
		case PROPERTY_FONT_SIZE:
			return fontHeight != -1;
		case PROPERTY_ITALIC:
			return italic;
		case PROPERTY_UNDERLINE:
			return underline;
		case PROPERTY_FONT:
			return fontFamily != null;
		case PROPERTY_ALIGNMENT:
			return alignment != PositionConstants.NONE;
		case PROPERTY_ORIENTATION:
			return orientation != SWT.NONE;
		default:
			break;
		}
		return false;
	}

	public boolean isUnderline() {
		return underline || (parentStyle != null && parentStyle.isUnderline());
	}

	public void setAlignment(int value) {
		if (alignment == value) {
			return;
		}
		if (value != PositionConstants.ALWAYS_RIGHT && value != PositionConstants.CENTER
				&& value != PositionConstants.RIGHT && value != PositionConstants.NONE
				&& value != PositionConstants.LEFT && value != PositionConstants.ALWAYS_LEFT) {
			throw new IllegalArgumentException(
					"Alignment must be LEFT, CENTER, RIGHT, ALWAYS_LEFT, ALWAYS_RIGHT or NONE."); //$NON-NLS-1$
		}
		int oldValue = alignment;
		alignment = value;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_ALIGNMENT, oldValue, alignment);
		}
	}

	public void setBold(boolean value) {
		if (bold == value) {
			return;
		}
		bold = value;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_BOLD, !value, value);
		}
	}

	public void setFontFamily(String fontFamily) {
		String oldName = this.fontFamily;
		this.fontFamily = fontFamily;
		firePropertyChange(PROPERTY_FONT, oldName, fontFamily);
	}

	public void setFontHeight(int fontHeight) {
		this.fontHeight = fontHeight;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_FONT_SIZE, fontHeight, fontHeight);
		}
	}

	public void setItalic(boolean value) {
		if (italic == value) {
			return;
		}
		italic = value;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_ITALIC, !value, value);
		}
	}

	public void setOrientation(int value) {
		if (orientation == value) {
			return;
		}
		if (value != SWT.RIGHT_TO_LEFT && value != SWT.LEFT_TO_RIGHT && value != SWT.NONE) {
			throw new IllegalArgumentException("Orientation must LTR, RTL or NONE."); //$NON-NLS-1$
		}
		int oldValue = orientation;
		orientation = value;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_ORIENTATION, oldValue, orientation);
		}
	}

	public void setParentStyle(Style style) {
		parentStyle = style;
	}

	public void setUnderline(boolean value) {
		if (underline == value) {
			return;
		}
		underline = value;
		if (listeners != null) {
			listeners.firePropertyChange(PROPERTY_UNDERLINE, !value, value);
		}
	}

}