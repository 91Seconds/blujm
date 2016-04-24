package ecs100;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

class Ecs100MouseListener extends MouseAdapter implements MouseMotionListener{
    private UIMouseListener controller;

    public Ecs100MouseListener(UIMouseListener controller) {
	this.controller = controller;
    }

    public void mousePressed(final MouseEvent e) {
	if (controller != null) {
	    UI.mouseEventQueue.offer(new Ecs100MouseEvent(controller, "pressed",
							  (double)e.getX(), (double)e.getY()));
	}
    }

    public void mouseReleased(final MouseEvent e) {
	if (controller != null) {
	    UI.mouseEventQueue.offer(new Ecs100MouseEvent(controller, "released",
							  (double)e.getX(), (double)e.getY()));
	}
    }

    //The doubleclicked is a problem, because it misses clicks!!
    //Making two events (as in the modification below) is probably a bad idea.
    //I think it should be removed.  
    public void mouseClicked(final MouseEvent e) {
	if (controller != null) {
	    UI.mouseEventQueue.offer(new Ecs100MouseEvent(controller, "clicked",
							  (double)e.getX(), (double)e.getY()));
	}
    }

    public void mouseMoved(final MouseEvent e) {
	if (controller != null) {
	    UI.mouseEventQueue.offer(new Ecs100MouseEvent(controller, "moved",
							  (double)e.getX(), (double)e.getY()));
	}
    }

    public void mouseDragged(final MouseEvent e) {
	if (controller != null) {
	    UI.mouseEventQueue.offer(new Ecs100MouseEvent(controller, "dragged",
							  (double)e.getX(), (double)e.getY()));
	}
    }

}
