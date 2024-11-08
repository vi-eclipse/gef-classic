/*******************************************************************************
 * Copyright (c) 2008, 2024 IBM Corporation and others.
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

package org.eclipse.gef.internal.ui.palette;

import org.eclipse.swt.graphics.Color;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;

/**
 * A class to keep miscellaneous palette color utilities.
 *
 * @author crevells
 * @since 3.4
 */
public class PaletteColorUtil {

	public static final Color WIDGET_LIST_BACKGROUND = ColorConstants.listBackground;

	public static final Color INFO_FOREGROUND = ColorConstants.tooltipForeground;

	public static final Color ARROW_HOVER = new Color(null, 229, 229, 219);

	public static final Color WIDGET_BACKGROUND_LIST_BACKGROUND_40 = FigureUtilities
			.mixColors(ColorConstants.button, PaletteColorUtil.WIDGET_LIST_BACKGROUND, 0.4);

	public static final Color WIDGET_BACKGROUND_LIST_BACKGROUND_60 = FigureUtilities
			.mixColors(ColorConstants.button, PaletteColorUtil.WIDGET_LIST_BACKGROUND, 0.6);

	public static final Color WIDGET_BACKGROUND_LIST_BACKGROUND_85 = FigureUtilities
			.mixColors(ColorConstants.button, PaletteColorUtil.WIDGET_LIST_BACKGROUND, 0.85);

	public static final Color WIDGET_BACKGROUND_LIST_BACKGROUND_90 = FigureUtilities
			.mixColors(ColorConstants.button, PaletteColorUtil.WIDGET_LIST_BACKGROUND, 0.9);
}
