package ecs100;

/** Interface for programs that listen to the mouse on the UI graphics pane.
*/

public interface UIMouseListener {

/** Respond to mouse actions.
 * The value of action may be "pressed", "released", "clicked", "doubleclicked", "moved", or "dragged".
 * x and y are the coordinates of where the mouse action happened.
*/
  public void mousePerformed(String action, double x, double y);
}
