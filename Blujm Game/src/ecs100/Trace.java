package ecs100;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

  /** The Trace class provides a simple debugging facility by which a program can print
    * out information into a trace window without affecting the operation of the program.<P>
    * The trace facility can be turned on and off via a menu command.  When the trace
    * facility is turned off, the trace window is hidden, and no trace information is
    * recorded.  When the trace facility it turned on, the trace window is shown, and
    * trace information will be recorded and displayed in it.  The contents of the trace
    * window can also be printed and/or saved to a file. <P>
    * As well as printing information into the trace window, the program can also
    * invoke the <I>Trace.pause</I> method that will temporarily pause the program until
    * the user is ready to proceed.<P>
    * The methods of the Trace class are all static, so that the <I>Trace.print</I> and
    * <I>Trace.println</I> methods can be called from any point in the program.<P>
    * The program must have created an UI window in order to use the trace facility, but
    * there is no need to explicitly create a trace window or initialise the trace facility
    * from inside the program.  For more sophisticated debugging, it is possible to turn
    * the trace facility on or off from inside the program (and also to determine whether
    * it is on, and to print or save the contents of the trace window), but this is not
    * necessary for simple use.
    */

public class Trace {
    private static JFrame traceWin;
    private static JTextArea trace; 

    static void initialise(JMenuBar menuBar) {
	if (traceWin==null){
	    traceWin = new JFrame("Trace");

	    trace = new JTextArea(25, 70);
	    traceWin.add(new JScrollPane(trace));
	    traceWin.pack();
	}
    }

    static void dispose() {
	if (traceWin != null) {
	    traceWin.dispose();
	    traceWin = null;
	}
    }

    /*-----------------------------------------------------------------*/

    // Print a value (any type) into the trace window, if the trace window is
    // currently showing.  Does nothing if the trace window is hidden.

    /** Print a String into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void print(String s) {
	UI.checkInitialised();
	if (traceWin.isShowing()) {
	    trace.append(s);
	    trace.setVisible(true);
	}
    }

    /** Print a boolean into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void print(boolean b) {
	print(String.valueOf(b));
    }

    /** Print a character into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void print(char c) {
	print(String.valueOf(c));
    }

    /** Print a number into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void print(double d) {
	print(String.valueOf(d));
    }

    /** Print an integer into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void print(int i) {
	print(String.valueOf(i));
    }

    /** Print an object into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     * Note, it calls the toString() method on the object, and prints the result.
     * This may or may not be useful.
     */
    public static void print(Object o) {
	print(String.valueOf(o));
    }

    /** Print into the trace window, if the trace window is
     *  currently showing.  Does nothing if the trace window is hidden.
     *  The <TT>printf()</TT> method requires a format string (which
     *  will contain "holes" specified with %'s) and additional arguments
     *  which will be placed "in the holes", using the specified formatting.
     */
    public static void printf(String format, Object... args){ 
        print(String.format(format, args));
    }

    /** Start a new line in the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     */
    public static void println() {
	print("\n");
    }

    /** Print a String followed by a new line in the trace window,
     * if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void println(String s) {
	print(s+"\n");
    }

    /** Print a boolean followed by a new line in the trace window,
     * if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void println(boolean b) {
	print(b+"\n");
    }

    /** Print a character followed by a new line in the trace window,
     * if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void println(char c) {
	print(c+"\n");
    }

    /** Print a number followed by a new line in the trace window,
     * if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void println(double d) {
	print(d+"\n"); 
    }

    /** Print an integer followed by a new line in the trace window,
     * if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void println(int i) {
	print(i+"\n"); 
    }

    /** Print an object followed by a new line into the trace window, if the trace window is
     * currently showing.  Does nothing if the trace window is hidden.
     * Note, it calls the toString() method on the object, and prints the result.
     * This may or may not be useful.
     */
    public static void println(Object o) {
	print(o+"\n"); 
    }


    /** Print a separator line in the trace window, if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
    public static void printSeparator() {
	println("----------------------------------------------------------------------");
    }
     */

    /*-----------------------------------------------------------------*/

    /** Display a dialog box that allows the user to pause the program until they are ready to
     * proceed, if the trace window is currently showing.
     * Does nothing if the trace window is hidden.
     */
    public static void pause() {
	UI.checkInitialised();
	if (traceWin.isShowing()) {
	    JOptionPane.showMessageDialog(traceWin, "Paused. Click when ready to continue"); // pondy
	}
    }

    /** Display a dialog box that allows the user to pause the program until they are ready to
     * proceed, if the trace window is currently showing.<BR>
     * The argument string is shown in the dialog box. This can be used to indicate the
     * point in the program at which the pause was invoked.<BR>
     * Does nothing if the trace window is hidden.
     */
    public static void pause(String s) {
	UI.checkInitialised();
	if (traceWin.isShowing()) {
	    JOptionPane.showMessageDialog(traceWin, "Paused at "+s+"\nClick when ready to continue"); // pondy
	}
    }

    /*-----------------------------------------------------------------*/


    /** Make the trace window visible.
     */
    public static void setVisible() {
	setVisible(true);
    }

    /** Hide the trace window.
     */
    public static void hide() {
	setVisible(false);
    }

    /** Make the trace window visible or hidden.<BR>
     * If the (boolean) argument is true, the trace window will be visible and future
     * print's and println's will be shown.<BR>
     * If the argument is false, the trace window will be hidden.
     */
    public static void setVisible(boolean v) {
	UI.checkInitialised();
	traceWin.setVisible(v);
    }


    /** Returns true iff the trace window is currently visible.
     */
    public static boolean isVisible() {
	UI.checkInitialised();
	return traceWin.isShowing();
    }

    /*-----------------------------------------------------------------*/

    /** Save the current contents of the trace window to a file.
     */
    public static void saveTrace() {
	UI.checkInitialised();
	JOptionPane.showMessageDialog(traceWin, "Can't save trace contents in this version of the library");
	//    trace.saveContents(traceWin, "Trace");
    }

    /*-----------------------------------------------------------------*/

    /** Print the current contents of the trace window.
     */
    public static void printTrace() {
	UI.checkInitialised();
	JOptionPane.showMessageDialog(traceWin, "Can't print trace contents in this version of the library");
	//    trace.printContents(traceWin, "Trace");
    }

}
