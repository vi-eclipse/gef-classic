package org.eclipse.gef;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.printing.Printer;

/**
 * @author danlee
 */
public class PrintGraphicalViewerOperation extends PrintFigureOperation {

private GraphicalViewer viewer;
private List selectedEditParts;

/**
 * Constructor for PrintGraphicalViewerOperation
 * 
 * @param p The Printer to print to * @param g The viewer containing what is to be printed */
public PrintGraphicalViewerOperation(Printer p, GraphicalViewer g) {
	super(p);
	viewer = g;
	LayerManager lm = (LayerManager)viewer.getEditPartRegistry().get(LayerManager.ID);
	IFigure f = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	setPrintSource(f);
}	
	
/**
 * Returns the viewer.
 * 
 * @return GraphicalViewer
 */
public GraphicalViewer getViewer() {
	return viewer;
}

/**
 * @see org.eclipse.draw2d.PrintOperation#preparePrintSource()
 */
protected void preparePrintSource() {
	super.preparePrintSource();
	selectedEditParts = new ArrayList(viewer.getSelectedEditParts());
	viewer.deselectAll();
}

/**
 * @see org.eclipse.draw2d.PrintOperation#restorePrintSource()
 */
protected void restorePrintSource() {
	super.restorePrintSource();
	viewer.setSelection(new StructuredSelection(selectedEditParts));
}

/**
 * Sets the viewer.
 * 
 * @param viewer The viewer to set
 */
public void setViewer(GraphicalViewer viewer) {
	this.viewer = viewer;
}

}