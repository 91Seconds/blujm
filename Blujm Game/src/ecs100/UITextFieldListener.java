package ecs100;

/** Interface for programs that listen to TextFields on the UI window.
*/

public interface UITextFieldListener {
    /** Respond to text field events.
     * The argument is the string that the user entered.
     */
  public void textFieldPerformed(String newText);
}
