package ecs100;

/** Interface for programs that listen to keys on the UI graphics pane.
*/

public interface UIKeyListener {

/** Respond to key actions.
 * The value of key will be a string containing the key that was pressed.
 * Normally, the string will be a single character (eg "A" or "s"), but for special keys,
 * it will be the name of the key, eg "Space", "Enter", "Left", "Right", "Page Up", "Page Down".
*/
  public void keyPerformed(String key);
}
