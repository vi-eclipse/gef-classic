/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.palette;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * A PaletteDrawer is a collapsible container that can have other non-container palette
 * entries.
 * 
 * @author Pratik Shah */
public class PaletteDrawer 
	extends PaletteContainer 
{

/**
 * The type for this PaletteEntry.
 * 
 * @see PaletteEntry#getType()
 */
public static final Object PALETTE_TYPE_DRAWER = "$Palette Drawer"; //$NON-NLS-1$
/**
 * Property name used when notification about a change in the drawer's initial state is
 * fired.
 */
public static final String PROPERTY_INITIAL_STATUS = "Initial status"; //$NON-NLS-1$

/**
 * Constants indicating the possible initial states for a drawer:
 * <UL>
 * 		<LI>INITIAL_STATE_OPEN - The drawer is expanded when the palette is
 * 		created.</LI>
 * 		<LI>INITIAL_STATE_CLOSED - The drawer is collapsed when the palette is
 * 		created.</LI>
 * 		<LI>INITIAL_STATE_PINNED_OPEN - The drawer is pinned open (which would prevent
 * 		it from being collapsed automatically) when the palette is created.</LI>
 * </UL>
 */
public static final int INITIAL_STATE_OPEN = 0,
                        INITIAL_STATE_CLOSED = 1,
                        INITIAL_STATUS_PINNED_OPEN = 2;

private int initialState;
private Object drawerType = ToolEntry.PALETTE_TYPE_TOOL;

/**
 * Constructor
 * 
 * @param label	The name/label for this entry
 */
public PaletteDrawer(String label) {
	this(label, (ImageDescriptor)null);
}

/**
 * Constructor
 * 
 * @param label	The name/label for this entry
 * @param icon	An icon for this drawer
 */
public PaletteDrawer(String label, ImageDescriptor icon) {
	super(label, null, icon, PALETTE_TYPE_DRAWER);
	setUserModificationPermission(PERMISSION_LIMITED_MODIFICATION);
}

/**
 * @return	<code>ToolEntry.PALETTE_TYPE_TOOL</code> or 
 * 			<code>PaletteTemplateEntry.PALETTE_TYPE_TEMPLATE</code>
 * 			
 * 	@see	#setDrawerType(Object)
 */
public Object getDrawerType() {
	if (drawerType != null) {
		return drawerType;
	}
	for (int i = 0; i < children.size(); i++) {
		PaletteEntry child = (PaletteEntry)children.get(i);
		Object type = child.getType();
		if (type != PaletteSeparator.PALETTE_TYPE_SEPARATOR)
			return type;
	}
	return PaletteEntry.PALETTE_TYPE_UNKNOWN;
}

/**
 * @return INITIAL_STATE_OPEN or INITIAL_STATE_CLOSED or INITIAL_STATE_PINNED_OPEN
 */
public int getInitialState() {
	return initialState;
}

/**
 * @return <code>true</code> if open initially
 */
public boolean isInitiallyOpen() {
	return (getInitialState() == INITIAL_STATE_OPEN 
			|| getInitialState() == INITIAL_STATUS_PINNED_OPEN);
}

/**
 * @return <code>true</code> if the drawer is to be pinned open initially.
 */
public boolean isInitiallyPinned() {
	return (getInitialState() == INITIAL_STATUS_PINNED_OPEN);
}

/**
 * DrawerType indicates whether a drawer will contain ToolEntries of
 * PaletteTemplateEntries.  A drawer should not contain entries of both these types. 
 * However, there are no checks/restrictions that will prevent you from doing so.  By
 * default, a drawer is of type <code>ToolEntry.PALETTE_TYPE_TOOL</code> 
 * 
 * @param	obj		<code>ToolEntry.PALETTE_TYPE_TOOL</code> or 
 * 					<code>PaletteTemplateEntry.PALETTE_TYPE_TEMPLATE</code>
 */
public void setDrawerType(Object obj) {
	drawerType = obj;
}

/**
 * Sets the initial state of this drawer (i.e. the state that this drawer should be when
 * the palette is created).
 * 
 * @param state	INITIAL_STATE_OPEN or INITIAL_STATE_CLOSED or INITIAL_STATE_PINNED_OPEN
 */
public void setInitialState(int state) {
	if (initialState == state)
		return;
	int oldState = initialState;
	initialState = state;
	listeners.firePropertyChange(PROPERTY_INITIAL_STATUS, oldState, state);
}
	
}