package ecs100;

// Version with lambdas!!!//

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JViewport;

import javax.swing.JLabel;
import javax.swing.JSplitPane;

import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Arc2D;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import java.util.InputMismatchException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** The UI class provides a simple, flexible Graphical User Interface suitable 
 * for small programs.  The UI class will construct a window which can contain 
 * <ul>
 * <li> a menu at the top (with commands for turning the Trace facility on and off, and quitting.
 * <li> a text output pane which can display text output, and read text input
 * <li> a graphics output pane which can display graphical output (anything
 *      output using the draw... methods).
The user can also input positions using the mouse in this region.
 * <LI> an input panel which can contain buttons, textfields,
 *      and sliders.
 * <LI> a message line at the bottom for short messages to the user.
 * </UL>
 * The text pane, graphics pane, and input panel will only be displayed if they are used - if
 * your program only tries to draw on the graphics pane, then only the graphics pane will be visible.
 */


public class UI {
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel inputPanel;
    private JSplitPane splitPane; 
    //    private Box IOarea;
    public Ecs100Canvas canvas;   //    private DEBUG
    private Ecs100TextArea textPane; 
    private JTextArea messageArea;
    private Ecs100MouseListener ml = null;
    private Ecs100KeyListener kl = null;

    private static final String DISP_GRAPHICS = "Graphics";
    private static final String DISP_TEXT = "Text";
    private static final int DEFAULT_SPLITPANE_WIDTH = 600;
    private static final int DEFAULT_SPLITPANE_HEIGHT= 480;
    private static final double DEFAULT_TEXTPANE_FRACTION= 0.3;

    public static UI theUI; 
    //    static boolean initialised = false;
    static boolean packedFrame = false;  // Added Feb 2015
    //mouseevents   added 01/2014
    static BlockingQueue<Ecs100MouseEvent> mouseEventQueue;    // must be accessible from mouse handlers
    static Ecs100MouseEventHandler mouseEventHandler;   // is it needed?
    //endmouseevents

    private static boolean immediateRepaint = true; // added Feb 2014

    static {initialise();}


    /** Ensure that the User Interface window is initialised.
     *  Removes all widgets and the mouse listener. */
    public static void initialise(){
        //mouseevents  added 01/2014
        if (mouseEventHandler!=null){
            mouseEventHandler.finish();
            mouseEventHandler = null;
        } 
        if (mouseEventQueue!=null){
            mouseEventQueue = null;
        }
        if (theUI==null){
            theUI = new UI();
        }
        Trace.initialise(theUI.menuBar);
        if (theUI.inputPanel!=null){
            theUI.inputPanel.removeAll();
        }
        theUI.canvas.clear();    
        theUI.canvas.redisplay();
        theUI.textPane.clear();
        theUI.splitPane.setDividerLocation(DEFAULT_TEXTPANE_FRACTION);
    }

    /** Sets the width and height of the window.
     * Note that adding buttons, sliders, etc may overrule this setting.
     * This should usually be called after all the buttons etc have been added.
     */
    public static void setWindowSize(int width, int height){
        checkInitialised();
        //theUI.frame.setSize(width, height);
	if (! packedFrame) {
	    theUI.splitPane.setPreferredSize(new Dimension(width, height));
	    theUI.messageArea.setColumns(10);
	    sleep(50); //to give it time to happen
	}
	else { // We need to re-create the frame
	    theUI.recreateFrame(width,height);
	}
    }


    /** Sets the width and height of the window.
     * Note that adding buttons, sliders, etc may overrule this setting.
     * This should usually be called after all the buttons etc have been added.
     */
    private void recreateFrame(int width, int height){
        checkInitialised();
	dispose();
	    
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);

	//int cols = (int) (width/11);
	int cols = 10;
        messageArea.setColumns(cols);

        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(messageArea, BorderLayout.SOUTH);
	splitPane.setPreferredSize(new Dimension(width, height));
	frame.add(splitPane, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { quit(); }
            });

        frame.pack();
        frame.setVisible(true);  // Cause frame to be laid out
        //sleep(500);  // Give everything a chance to be displayed (may not help)
    }


    /** Clear the text and graphics panes, but does not remove the widgets. */
    public static void clearPanes(){
        checkInitialised();
        clearText();
        clearGraphics();
        theUI.splitPane.setDividerLocation(DEFAULT_TEXTPANE_FRACTION);
    }

    /** Move the divider between the text and graphics panes to the specified
     * fraction.
     * position should be between 0.0 (at the left side - only graphics visible)
     * and 1.0 (at right side - only text visible)
     * (negative value means the initial default position)
     */
    public static void setDivider(double pos){
	//System.out.println("Setting divider to "+pos);  //DEBUG
        checkInitialised();
	sleep(100);
        if (pos < 0){
            theUI.splitPane.setDividerLocation(DEFAULT_TEXTPANE_FRACTION);
        }
        else {
            theUI.splitPane.setDividerLocation(Math.min(1.0, pos));
        }
    }

    /** Construct a new UI object, with its associated window.  <BR>
     * If two numbers are provided, the window will be set to the specified size.
     */
    private UI() {
        frame = new JFrame();
        //frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // commented out, Feb 2015, size id now about splitPane
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // added, pondy, Jun 2013, to exit when window closed
        menuBar = initMenuBar();
        frame.setJMenuBar(menuBar);

        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setSize(40, 460);
        frame.add(inputPanel, BorderLayout.WEST);

        messageArea = new JTextArea(1, 80);
        messageArea.setEditable(false);
        frame.add(messageArea, BorderLayout.SOUTH);

        canvas = new Ecs100Canvas();
	canvas.setFocusable(true);   // NEW JAN 2015
        textPane = new Ecs100TextArea(0,60);
        textPane.setFont(Font.decode("Monospaced"));


        JScrollPane textSP = new JScrollPane(textPane);
        JScrollPane graphicsSP = new JScrollPane(canvas,
                                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        graphicsSP.getViewport().setBackground(Color.white);
        graphicsSP.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, textSP, graphicsSP);
        splitPane.setOneTouchExpandable(true);
	splitPane.setPreferredSize(new Dimension(DEFAULT_SPLITPANE_WIDTH, DEFAULT_SPLITPANE_HEIGHT));
	frame.add(splitPane, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { quit(); }
            });


        frame.pack();
        frame.setVisible(true);  // Cause frame to be laid out
	packedFrame=true;
	sleep(500);  // Give everything a chance to be displayed (may not help)
        // added Jan 2015:
        // create thread to repaint every 20 milliseconds, if needed
        new Thread(() ->  {
		while (canvas!=null){
		    if (immediateRepaint){
			canvas.redisplay();
		    }
		    try{Thread.sleep(20);}catch(InterruptedException e){}
		}}).start();
    }
 
    /*--- Menu Bar --------------------------------------------------------------*/

    private JMenuBar initMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu menuItems = new JMenu("* MENU *");

        JMenuItem traceOnOff = new JMenuItem("Trace On/Off");
        traceOnOff.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    Trace.setVisible(!Trace.isVisible());}});

        JMenuItem clearPanes = new JMenuItem("Clear Panes");
        clearPanes.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    clearPanes();}});

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { quit(); }});

        menuItems.add(traceOnOff);
        menuItems.add(clearPanes);
        menuItems.add(quit);

        mb.add(menuItems);

        return mb;
    }

    /* ======= UTILITIES ======================================================== */

    static void checkInitialised() {
        if (theUI==null)
            throw new RuntimeException("The UI was not initialised or had been quit");
    }

    static void ensureGraphics() {
        if (theUI==null) checkInitialised();
    }

    static void ensureText() {
        if (theUI==null) checkInitialised();
        //theUI.messageArea.setText("Split at :"+theUI.splitPane.getDividerLocation());
        if (theUI.splitPane.getDividerLocation()<50){  // enough to make some text visible
            theUI.splitPane.setDividerLocation(50);
        }
    }



    /*-----------------------------------------------------------------*/

    private void dispose() {
        if (frame != null) {
            frame.dispose(); 
            frame = null;
        }
    }

    /*-----------------------------------------------------------------*/

    private void reweightLastComponent(Container c) {
        int ncomps = c.getComponentCount();
        if (ncomps > 0) {
            GridBagLayout lm = (GridBagLayout)c.getLayout();
            Component comp = inputPanel.getComponent(ncomps-1);
            GridBagConstraints lc = lm.getConstraints(comp);
            lc.weighty = 0;
            lm.setConstraints(comp,lc);
        }
    }

    /* ======== DRAWING on the GRAPHICS PANE ===================================== */


    /** Set the "immediate repaint" status of the UI drawing commands.<br/>
     * If the status is true (the default), then all the draw, fill, erase,
     * invert methods will have an immediately visible effect (they will repaint
     * the graphics pane directly).
     * If the status is false, then all the draw, fill, erase, invert methods
     * will draw in the background, but will not update the graphics pane so
     * that the effect will not be immediately visible.  To show all the changes
     * on the graphics pane, the program should call the repaintGraphaics
     * method (below).
     */
    public static void setImmediateRepaint(boolean immediate) {
        immediateRepaint = immediate;
    }

    /** Clear the contents of the graphics output region.
     */
    public static void clearGraphics() {
        ensureGraphics();
        theUI.canvas.clear();
    }

    /** Repaint the contents of the graphics output region.
     *  But only repaints the part that is "dirty", as recorded
     *  by the various draw/fill /etc commands.
     *  To repaint the whole graphics region, regardless of whether it is dirty or not,
     *  use repaintAllGraphics.
     */
    public static void repaintGraphics() {
        ensureGraphics();
        theUI.canvas.redisplay();
    }
    /** Repaint all the contents of the graphics output region.
     *  Ignores the "dirty" marker.
     */
    public static void repaintAllGraphics() {
        ensureGraphics();
        theUI.canvas.redisplayAll();
    }

    /** Set the color of the brush in the graphics output region.
     *  This will be the color of any shapes drawn after this. 
     */
    public static void setColor(Color col){
        checkInitialised();
        theUI.canvas.setColor(col);
    }

    /** Set the color of the brush in the graphics output region.
     *  This will be the color of any shapes drawn after this. 
     *  Deprecated - use setColor instead
     */
    public static void setForeGround(Color col){
        checkInitialised();
        theUI.canvas.setColor(col);
    }

    // Changed to double 2016T1 to be able to introduce it earlier.
    /** Set the point size of the Font of String to be drawn in the graphics output region.
     *  This will be the size of the font of any String drawn after this.
     */
    public static void setFontSize(double size) {
        theUI.canvas.setFontSize((int)size);
    }

    /** Set the width of the lines (in pixels) for lines drawn in the graphics output region.
     *  This will be the width of lines drawn with drawLine and the outlines of shapes drawn after this.
     */
    public static void setLineWidth(double width){
        theUI.canvas.setLineWidth(width);
    }


    /** Return the JFrame of the UI window.
        Only needed if you want to do more complicated
        operations on the window than are provided by methods in this
        UI class. */
    public static JFrame getFrame() {
        if (theUI!=null) return theUI.frame;
        return null;
    }

    /*-----------------------------------------------------------------*/


    /** Return the Graphics2D object underlying the
        graphics pane.  Only needed if you want to do more complicated
        operations on the graphics pane than are provided by methods in this
        UI class. */
    public static Graphics2D getGraphics() {
        return (Graphics2D) theUI.canvas.getBackingGraphics();
    }

    /** Returns the height of the canvas in the graphics pane.
     *  Note, this is the size of the actual drawing area,
     *  not necessarily the part of it that is currently visible in the window.
     *  Changing the size of the window will not change the size of the canvas,
     *  it only changes how much of it is visible.
     */
    public static int getCanvasHeight() {
        return theUI.canvas.getHeight();
    }

    /** Returns the width of the canvas in the graphics pane.
     *  Note, this is the size of the actual drawing area,
     *  not necessarily the part of it that is currently visible in the window.
     *  Changing the size of the window will not change the size of the canvas,
     *  it only changes how much of it is visible. 
     */
    public static int getCanvasWidth() {
        return theUI.canvas.getWidth();
    }

    /*---- Draw Line---------------------------------------------------------*/

    /** Draw a line in the graphics output region from (x1, y1) to (x2, y2).<BR>
     */
    public static void drawLine(double x1, double y1, double x2, double y2) {
        theUI.canvas.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    /*---- Erase Line-----------------------------------------------------*/

    /** Erase the line in the graphics output region from (x1, y1) to (x2, y2).
     */
    public static void eraseLine(double x1, double y1, double x2, double y2) {
        ensureGraphics();
        theUI.canvas.erase(new Line2D.Double(x1, y1, x2, y2));
    }

    /*----Invert Line-----------------------------------------------------*/

    /** Invert the line in the graphics output region from (x1, y1) to (x2, y2).
     */
    public static void invertLine(double x1, double y1, double x2, double y2) {
        ensureGraphics();
        theUI.canvas.invert(new Line2D.Double(x1, y1, x2, y2));
    }

    /*---- Draw Rectangle-----------------------------------------------------*/

    /** Draw the outline of a rectangle in the graphics output region with
     * a top-left corner at (x, y) and of the specified width and height.
     */
    public static void drawRect(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.draw(new Rectangle2D.Double(x, y, width,height));
    }

    /*---- Fill Rectangle-----------------------------------------------------*/

    /** Draw a solid rectangle in the graphics output region with
     * a top-left corner at (x, y) and of the specified width and height.
     */
    public static void fillRect(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.fill(new Rectangle2D.Double(x, y, width,height));
}

    /*---- Erase Rect-----------------------------------------------------*/

    /** Erase the rectangular region in the graphics output region with
     * a top-left corner at (x, y) and of the specified width and height.
     */
    public static void eraseRect(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.erase(new Rectangle2D.Double(x, y, width,height));
    }

    /*---- Invert Rect-----------------------------------------------------*/

    /** Invert the rectangular region in the graphics output region with
     * a top-left corner at (x, y) and of the specified width and height.
     */
    public static void invertRect(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.invert(new Rectangle2D.Double(x, y, width,height));
    }

    /*---- Draw String-----------------------------------------------------*/

    /** Draw the given string in the graphics output region at the position (x, y) .
     */
    public static void drawString(String s, double x, double y) {
        ensureGraphics();
        theUI.canvas.drawString(s, (int)x, (int)y);
    }

    /*---- Erase String---------------------------------------------------*/

    /** Erase the region covered given string in the graphics output region at the position (x, y).
     */
    public static void eraseString(String s, double x, double y) {
        ensureGraphics();
        theUI.canvas.eraseString(s, (int)x, (int)y);
    }
    /*---- Invert String---------------------------------------------------*/

    /** Invert the region covered given string in the graphics output region at the position (x, y).
     */
    public static void invertString(String s, double x, double y) {
        ensureGraphics();
        theUI.canvas.invertString(s, (int)x, (int)y);
    }

    /*---- Draw Oval-----------------------------------------------------*/
    /** Draw the outline of an oval in the graphics output region with the left edge of
     * the oval at x, the top of the oval at y, and having the specified width and height.
     */
    public static void drawOval(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.draw(new Ellipse2D.Double(x, y, width, height));
    }

    /*---- Fill Oval-----------------------------------------------------*/
    /** Draw a solid oval in the graphics output region with the left edge of the
     * oval at x, the top of the oval at y, and having the specified width and height.
     */
    public static void fillOval(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.fill(new Ellipse2D.Double(x, y, width,height));
    }

    /*---- Erase Oval-----------------------------------------------------*/
    /** Erase an oval region in the graphics output region with the left edge of the
     * oval at x, the top of the oval at y, and having the specified width and height.
     */
    public static void eraseOval(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.erase(new Ellipse2D.Double(x, y, width,height));
    }

    /*----Invert Oval-----------------------------------------------------*/
    /** Invert an oval region in the graphics output region with the left edge of the
     * oval at x, the top of the oval at y, and having the specified width and height.
     */
    public static void invertOval(double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.invert(new Ellipse2D.Double(x, y, width,height));
    }

    /*---- Draw Arc-----------------------------------------------------*/
    /** Draw the outline of an arc in the graphics output region.
     * An arc is a segment of an oval, and is specified by giving the left edge of the
     * oval (x), the top of the oval (y), the width and height of the oval, and the angle 
     * (anticlockwise from the x-axis) that the arc starts, and the angle of the arc.
     */
    public static void drawArc(double x, double y, double width, double height, 
                               double startAngle, double arcAngle) {
        ensureGraphics();
        theUI.canvas.draw(new Arc2D.Double(x, y, width, height, startAngle, arcAngle, Arc2D.PIE));
    }

    /*---- Fill Arc-----------------------------------------------------*/

    /** Draw a solid arc in the graphics output region.
     * An arc is a segment of an oval, and is specified by giving the left edge of the
     * oval (x), the top of the oval (y), the width and height of the oval, and the angle 
     * (anticlockwise from the x-axis) that the arc starts, and the angle of the arc.
     */
    public static void fillArc(double x, double y, double width, double height, 
                               double startAngle, double arcAngle) {
        ensureGraphics();
        theUI.canvas.fill(new Arc2D.Double(x, y, width, height, startAngle, arcAngle, Arc2D.PIE));
    }

    /*---- Erase Arc-----------------------------------------------------*/
    /** Erase an arc-shaped region of the graphics output region.
     * An arc is a  segment of an oval, and is specified by giving the left edge of the
     * oval (x), the top of the oval (y), the width and height of the oval, and the angle 
     * (anticlockwise from the x-axis) that the arc starts, and the angle of the arc.
     */
    public static void eraseArc(double x, double y, double width, double height, 
                                double startAngle, double arcAngle) {
        ensureGraphics();
        theUI.canvas.erase(new Arc2D.Double(x, y, width, height, startAngle, arcAngle, Arc2D.PIE));
    }

    /*----Invert Arc-----------------------------------------------------*/
    /** Invert an arc-shaped region of the graphics output region.
     * An arc is a segment of an oval, and is specified by giving the left edge of the
     * oval (x), the top of the oval (y), the width and height of the oval, and the angle 
     * (anticlockwise from the x-axis) that the arc starts, and the angle of the arc.
     */
    public static void invertArc(double x, double y, double width, double height, 
                                double startAngle, double arcAngle) {
        ensureGraphics();
        theUI.canvas.invert(new Arc2D.Double(x, y, width, height, startAngle, arcAngle, Arc2D.PIE));
    }



    /*---- Draw Image-----------------------------------------------------*/
    /* Draw an image in the graphics output region.  
     * The image may be specified by giving a file name, or providing the Image object.
     * The 2nd and 3rd arguments are where the left, top corner of the image should be placed.
     * Optional 4th and 5th arguments are the width and height to scale the image; if
     * not provided, the image will have its natural size
     */

    /** Draw a scaled image in the graphics output region.  
     * The image is specified by giving a file name.
     * The 2nd and 3rd arguments are where the left, top corner of the image should be placed.
     * The 4th and 5th arguments are the width and height to scale the image.
     */
    public static void drawImage(String fileName, double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.drawImage(fileName, (int)x, (int)y, (int)width, (int)height);
    }

    /** Draw an unscaled image in the graphics output region.
     * The image is specified by giving a file name.
     * The 2nd and 3rd arguments are where the left, top corner of the image should be placed.
     */
    public static void drawImage(String fileName, double x, double y) {
        ensureGraphics();
        theUI.canvas.drawImage(fileName, (int)x, (int)y);
    }

    /** Draw a scaled image in the graphics output region.  
     * The image is specified by providing the Image object.
     * The 2nd and 3rd arguments are where the left, top corner of the image should be placed.
     * The 4th and 5th arguments are the width and height to scale the image.
     */
    public static void drawImage(Image img, double x, double y, double width, double height) {
        ensureGraphics();
        theUI.canvas.drawImage(img, (int)x, (int)y, (int)width, (int)height);
    }

    /** Draw an unscaled image in the graphics output region.
     * The image is specified by providing the Image object.
     * The 2nd and 3rd arguments are where the left, top corner of the image should be placed.
     */
    public static void drawImage(Image img, double x, double y) {
        ensureGraphics();
        theUI.canvas.drawImage(img, (int)x, (int)y);
    }


    /*---- Erase Image-----------------------------------------------------*/
    /* Erase an unscaled image, specified by a file name or an Image object,
     */
    /** Erase an unscaled image, specified by a file name.
     */
    public static void eraseImage(String fileName, double x, double y) {
        ensureGraphics();
        theUI.canvas.eraseImage(fileName, (int)x, (int)y);
    }

    /** Erase an unscaled image, specified by an Image object.
     */
    public static void eraseImage(Image img, double x, double y) {
        ensureGraphics();
        theUI.canvas.eraseImage(img, (int)x, (int)y);
    }
    

    /*---- Draw Polygon-----------------------------------------------------*/
    /** Draw the outline of an polygon in the graphics output region.
     * The polygon is specified by two arrays containing the x coordinates and the y coordinates
     * of the series of vertices of the polygon, and the number of vertices.
     */
    private static Polygon makePolygon(double[] xPoints, double[] yPoints, int nPoints){
        int[] xs = new int[nPoints];
        int[] ys = new int[nPoints];
        for (int i=0; i<nPoints; i++){
            xs[i] = (int) xPoints[i];
            ys[i] = (int) yPoints[i];
        }
        return new Polygon(xs, ys, nPoints);
    }

    /** Draw the outline of an polygon in the graphics output region.
     * The polygon is specified by two arrays containing the x coordinates and the y coordinates
     * of the series of vertices of the polygon, and the number of vertices.
     */
    public static void drawPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        ensureGraphics();
        theUI.canvas.draw(makePolygon(xPoints, yPoints, nPoints));
    }


    /*---- Fill Polygon-----------------------------------------------------*/
    /** Draw a filled polygon in the graphics output region.
     * The polygon is specified by two arrays containing the x coordinates and the y coordinates
     * of the series of vertices of the polygon, and the number of vertices.
     */
    public static void fillPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        ensureGraphics();
        theUI.canvas.fill(makePolygon(xPoints, yPoints, nPoints));
    }

    /*---- Erase Polygon-----------------------------------------------------*/
    /** Erase an polygon region in the graphics output region.
     * The polygon is specified by two arrays containing the x coordinates and the y coordinates
     * of the series of vertices of the polygon, and the number of vertices.
     */

    public static void erasePolygon(double[] xPoints, double[] yPoints, int nPoints) {
        ensureGraphics();
        theUI.canvas.erase(makePolygon(xPoints, yPoints, nPoints));
    }

    /*----Invert Polygon-----------------------------------------------------*/
    /** Invert an polygon region in the graphics output region.
     * The polygon is specified by two arrays containing the x coordinates and the y coordinates
     * of the series of vertices of the polygon, and the number of vertices.
     */
    public static void invertPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        ensureGraphics();
        theUI.canvas.invert(makePolygon(xPoints, yPoints, nPoints));
    }



/*==== OUTPUT to TEXT PANE=============================================*/

    /** Clear the contents of the text output region.  */
    public static void clearText() {
        ensureText();
        theUI.textPane.clear();
    }

    /** Print a String to the text pane.
     */
    public static void print(String s) {
        ensureText();
        if (s == null) 
            theUI.textPane.outputString("NULL");
        else 
            theUI.textPane.outputString(s);
    }

    /** Print a boolean to the text pane.
     */
    public static void print(boolean b) {
        ensureText();
        print(String.valueOf(b));
    }

    /** Print a character  to the text pane.
     */
    public static void print(char c) {
        print(String.valueOf(c));
    }

    /** Print a number to the text pane.
     */
    public static void print(double d) {
        print(String.valueOf(d));
    }

    /** Print an integer to the text pane.
     */
    public static void print(int i) {
        print(String.valueOf(i));
    }

    /** Print an object to the text pane.
        Note, it calls the toString() method on the object, and prints the result.
        This may or may not be useful.
     */
    public static void print(Object o) {
        print(String.valueOf(o));
    }

    /** Start a new line on the text pane.
     */
    public static void println() {
        print("\n");
    }

    /** Print a string to the text pane and start a new line.
     */
    public static void println(String s) {
        print(s+"\n");
    }

    /** Print a boolean to the text pane and start a new line.
     */
    public static void println(boolean b) {
        print(String.valueOf(b)+"\n");
    }

    /** Print a character to the text pane and start a new line.
     */
    public static void println(char c) {
        print(c+"\n");
    }

    /** Print a number to the text pane and start a new line.
     */
    public static void println(double d) {
        print(String.valueOf(d)+"\n"); 
    }

    /** Print an integer to the text pane and start a new line.
     */
    public static void println(int i) {
        print(String.valueOf(i)+"\n"); 
    }

    /** Print an object to the text pane and start a new line.
        Note, it calls the toString() method on the object, and prints the result.
        This may or may not be useful.
     */
    public static void println(Object o) {
        print(String.valueOf(o)+"\n");
    }

    /*------printing with a format string ------------------------------*/

    /** The <TT>printf()</TT> method requires a format string (which
     *  will contain "holes" specified with %'s) and additional arguments
     *  which will be placed "in the holes", using the specified formatting.
     */
    public static void printf(String format, Object... args){ 
        print(String.format(format, args));
    }

    /*====== INPUT from TEXT PANE ==============================*/

    // needs to act like a Scanner, but will block until input
    // (perhaps it should put "Enter input..." in the meassage area if blocked)
    // entering text will have no effect until return is typed, then the text will be
    // put into a field of theUI, from which these methods will get the tokens
    // actually, we need two fields - the current scanner, and the StringBuilder waiting to be processed
    // these methods will get a value out of the scanner if it has a token/line.
    // if there is no scanner or the scanner doesn't have a token/line, then it will make a new
    // Scanner on the outstanding StringBuilder. if the stringBuilder is empty, then it will
    // put a message prompting for input, and wait??? (could be a problem if the keylistener
    // is in the same thread!! - may need to put the call to the read in another thread


    private static void prompt(String question){
        ensureText();
        theUI.textPane.clearInputBuffer();
        question = question.trim();
        if (!(question.endsWith("?") || question.endsWith(":")))
            question = question+": ";
        else
            question = question+" ";
        theUI.textPane.outputString(question);
    }

    /** Prints the question and waits for the user to answer.
        Returns the first token in their answer as a String.
        Removes any pending user input before asking the question.
     */
    public static String askToken(String question){
        prompt(question);
        return theUI.textPane.next();
    }

    /** Prints the question and waits for the user to answer.
        Returns their answer as a String.
        Removes any pending user input before asking the question.
     */
    public static String askString(String question){
        prompt(question);
        return theUI.textPane.nextLine();
    }

    /** Prints the question and waits for the user to answer with an integer.
        Returns their answer as an int.
        Removes any pending user input before asking the question.
     */
    public static int askInt(String question){
        prompt(question);
        while (true){
            try{
                int ans = theUI.textPane.nextInt();
                return ans;
            }
            catch (InputMismatchException e){}
            prompt(question+ " (must be an integer)");
        }
    }

    /** Prints the question and waits for the user to answer with a number.
        Returns their answer as a double.
        Removes any pending user input before asking the question.
     */
    public static double askDouble(String question){
        prompt(question);
        while (true){
            try{
                return theUI.textPane.nextDouble();
            }
            catch (InputMismatchException e){}
            prompt(question+ " (must be a number)");
        }
    }

    /** Prints the question and waits for the user to answer with yes/no or true/false.
        Returns their answer as a boolean.
        Removes any pending user input before asking the question.
     */
    public static boolean askBoolean(String question){
        prompt(question);
        while (true){
            try{
                return theUI.textPane.nextBoolean();
            }
            catch (InputMismatchException e){}
            prompt(question+ " (must be a boolean)");
        }
    }

    /** Read the next token of the user's input and return it. 
     * Waits for user input if there isn't any yet.
     */
    public static String next(){
        ensureText();
        return theUI.textPane.next();
    }

    /** Read the next token of the user's input and
     * return it as an int if it is an integer. 
     * Throws an exception if it is not an integer. 
     * Waits for user input if there isn't any yet.
     */
    public static int nextInt(){
        ensureText();
        return theUI.textPane.nextInt();
    }

    /** Read the next token of the user's input and
     * return it as a double if it is a number. 
     * Throws an exception if it is not a number. 
     * Waits for user input if there isn't any yet.
     */
    public static double nextDouble(){
        ensureText();
        return theUI.textPane.nextDouble();
    }

    /** Read the next token of the user's input and
     * return true if it is "yes", "y", or "true",
     * return false if it is "no", "n", or "false" (case insensitive).
     * Throws an exception if it is anything else.
     * Waits for user input if there isn't any yet.
    */
    public static boolean nextBoolean(){
        ensureText();
        return theUI.textPane.nextBoolean();
    }

    /** Read the remaining characters of the user's input up to (but not including) the next end-of-line
     * and return them as a string. Reads and throws away the end-of-line character.
     * If there are no characters on the line, then it returns an empty string ("").
     * Waits for user input if there isn't any yet.
    */
    public static String nextLine(){
        ensureText();
        return theUI.textPane.nextLine();
    }

    /** Returns true if there is any user input, but waits for the
     * user to type something if there isn't any yet.
     * (ie, always returns true.)
    */
    public static boolean hasNext(){
        ensureText();
        return theUI.textPane.hasNext();
    }

    /** Returns true if the next token in the user input is an integer.
        Waits for the user to type something if there isn't any yet.
    */
    public static boolean hasNextInt(){
        ensureText();
        return theUI.textPane.hasNextInt();
    }

    /** Returns true if the next token in the user input is a number.
        Waits for the user to type something if there isn't any yet.
    */
    public static boolean hasNextDouble(){
        ensureText();
        return theUI.textPane.hasNextDouble();
    }

    /** Returns true if the next token in the user input is a yes/no or true/false.
        Waits for the user to type something if there isn't any yet.
    */
    public static boolean hasNextBoolean(){
        ensureText();
        return theUI.textPane.hasNextBoolean();
    }

    /** Returns true if there is any user input, but waits for the
        user to type something if there isn't any yet.
        (ie, always returns true.)*/
    public static boolean hasNextLine(){
        ensureText();
        return theUI.textPane.hasNextLine();
    }


    /*==== MESSAGE OUTPUT =====================================================*/

    /** Print a message to the message line.*/
    public static void printMessage(String s) {
        checkInitialised();
        theUI.messageArea.setText(s);
    }

    /* ==== BUTTONS and INPUT FIELDS =============================================*/

    /* This needs to fix up the input panel, make sure it is visible and
       has been redrawn.  It doesn't work yet!!! */
    private void fixInputPanel(){
        theUI.inputPanel.revalidate();
        //theUI.inputPanel.repaint();
        //theUI.frame.invalidate();
        theUI.frame.validate();
        theUI.frame.pack();
    }
  

    /** Add a button to the input panel on the left side of the gui window.
     * The first argument is the name that should appear on the button.
     * The second argument is the action to perform when the button is
     * clicked. The action must have no parameters.<BR>
     * The action can be a method to call:
     *   eg UI::clearPanes or this::doit
     * Or, the action can be a simple "lambda":
     *   eg  () -> this.size = 5
     * Or a more complicated "lambda":
     *   eg () -> {* this.doit(); this.size = 5; }
     * <P>
     * addButton returns the new button, in case you want to change the color or name of the
     * button later.
     */
    public static JButton addButton(String name, UIButtonListener controller) {
        checkInitialised();

        theUI.reweightLastComponent(theUI.inputPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(5,3,5,3);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        c.ipady = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        Ecs100Button button = new Ecs100Button(name, controller);
        theUI.inputPanel.add(button, c);

        theUI.fixInputPanel();
        return button;
    }

    /** Add a text field to the input panel on the left side of the gui window.<P>
     * A text field allows the user to enter a string value by typing the string
     * into the field. <P>
     * The first argument is the label that  the text field will be given.
     * The second argument is the action to perform when a value is entered
     * in the text field. The action must have one String parameter.
     * The action can be a method (with one String parameter) to call:
     *  eg  this::processName
     * Or, the action can be a simple "lambda":
     *  eg (String v) -> this.name = v
     * Or a more complicated "lambda":
     *  eg (String v) -> {this.name = v; UI.clearText(); this.listDetails(); }
     */
    public static void addTextField(String s, UITextFieldListener obj) {
        checkInitialised();

        theUI.reweightLastComponent(theUI.inputPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(7,3,2,3);
        c.anchor = GridBagConstraints.WEST;
        theUI.inputPanel.add(new JLabel(s), c);

        c.insets = new Insets(2,3,7,3);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1.0;
        Ecs100TextField tf = new Ecs100TextField(10, obj);
        theUI.inputPanel.add(tf, c);
        theUI.fixInputPanel();
    }

    // I think we don't want this.
    /** Add a number field to the input panel on the left side of the gui window.<P>
     * A number field allows the user to enter a numeric value by typing the number
     * into the field. <P>
     * The arguments are the name of the number field and the object
     * that will handle the event when a number is entered into the number field.  <P>
     * Typically, the object will be <TT>this</TT> - the object that is putting the number field
     * on the gui window, but it need not be the same object. <P>
     * The object that handles the number field event must implement the <TT>UINumberFieldListener</TT>
     * interface which means that it must provide the method:<QUOTE><TT>
     *   public void numberFieldPerformed(String name, double num) </TT></QUOTE>
     * which will be passed two values: <UL><LI>
     * the name of the number field into which a value was entered,<LI>
     * the value that was entered into the field (as a double)</UL>
     public static void addNumberField(String s, UINumberFieldListener obj) {
     checkInitialised();

     reweightLastComponent(inputPanel);

     GridBagConstraints c = new GridBagConstraints();
     c.gridx = 0;
     c.insets = new Insets(7,3,2,3);
     c.anchor = GridBagConstraints.WEST;
     inputPanel.add(new Label(s), c);

     c.insets = new Insets(2,3,7,3);
     c.anchor = GridBagConstraints.NORTHWEST;
     c.weighty = 1.0;
     inputPanel.add(new Ecs100NumberField(this, s, 20, obj), c);

     theUI.frame.pack();
     }
    */

    /** Add a slider to the input panel on the left side of the gui window.<P>
     * A slider allows the user to enter a numeric value by moving the slider knob along the slider.
     * The arguments are the name of the slider, the minimum and maximum values of the slider (double), 
     * and the action to perform when a value is set with the slider.
     * The action must have one double parameter.
     * The action can be a method (with one double parameter) to call,
     *  eg  this::resetSize
     * Or, the action can be a simple "lambda":
     *  eg (double v) -> this.size = v,
     * Or a more complicated "lambda":
     *  eg (double v) -> {this.reset(); this.size = v; UI.redraw(); }
     */
    public static void addSlider(String name, double min, double max, UISliderListener obj) {
        addSlider(name, new Ecs100Slider((int)min, (int)max, obj));
    }

    /** Add a slider to the input panel on the left side of the gui window.<P>
     * A slider allows the user to enter a numeric value by moving the slider knob along the slider.
     * The arguments are the name of the slider, the minimum and maximum values of the slider (double), 
     * the initial value of the slider, and the action to perform when a value is set with the slider.
     * The action must have one double parameter.
     * The action can be a method (with one double parameter) to call,
     *  eg  this::resetSize
     * Or, the action can be a simple "lambda":
     *  eg (double v) -> this.size = v,
     * Or a more complicated "lambda":
     *  eg (double v) -> {this.reset(); this.size = v; UI.redraw(); }
     */
    public static void addSlider(String name, double min, double max, double initial, UISliderListener obj) {
        addSlider(name, new Ecs100Slider((int)min, (int)max, (int)initial, obj));
    }

    private static void addSlider(String name, Ecs100Slider sl) {
        checkInitialised();
        theUI.reweightLastComponent(theUI.inputPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(7,3,2,3);
        c.anchor = GridBagConstraints.WEST;
        theUI.inputPanel.add(new JLabel(name), c);

        c.insets = new Insets(2,3,7,3);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1.0;
        theUI.inputPanel.add(sl, c);

        theUI.fixInputPanel();
    }

    /*----Key Input-------------------------------------------------------*/
    /** Enable the use to notice key events when the mouse is over the graphics pane.
     * The program will respond to keys being typed (pressed and then released),
     * not to the presses and releases separately.
     * The argument is the action to perform when a key is typed.
     * The action must have one String parameter.
     * The action can be a method (with one String parameter) to call,
     *  eg  this::recordKey
     * Or, the action can be a simple "lambda":
     *  eg (String v) -> this.setLastKey(v),
     * Or a more complicated "lambda":
     *  eg String v) -> {if (v.equals("Up")) {this.moveUp();} else {this.moveDown(); }
     * The action will be passed a string specifying the key that the user
     * typed, eg "A", "x", "+", "HOME", etc
     */
    public static void setKeyListener(UIKeyListener obj) {
        checkInitialised();
        if (theUI.kl != null){
            theUI.canvas.removeKeyListener(theUI.kl);
        }
        theUI.kl = new Ecs100KeyListener(obj);
	theUI.canvas.addKeyListener(theUI.kl);
    }




    /*----Mouse Input-------------------------------------------------------*/

    /** Enable the user to use the mouse on the graphics pane on the right
     *  side of the gui window.  The program will respond to the user <UL><LI>
     *  pressing the mouse button,<LI>
     *  releasing the mouse button<LI>
     *  clicking the mouse button (pressing and releasing without moving)</UL>
     * but will not notice movements of the mouse.<P>
     * The argument is the action to respond to the mouse.
     * The action must have three parameters:
     *   a String (the kind of mouse event),
     *   and two doubles (the x and y coordinates where the event happened).
     * Typically, the action will be this::doMouse,
     *   where doMouse is a method with the header
     *      public void doMouse(String action, double x, double y)
     * The action will be passed three values:
     *   a string specifying what the user did:
     *     ("pressed", "released", or "clicked")
     *   the coordinates (two doubles) of the mouse when the event happened.
     *  Note also that if the user clicks the mouse there will be three events:
     *  - a "pressed",  a "released", and a "clicked".
     */
    public static void setMouseListener(UIMouseListener obj) {
        checkInitialised();
        if (theUI.ml != null){
            theUI.canvas.removeMouseListener(theUI.ml);
            theUI.canvas.removeMouseMotionListener(theUI.ml);
        }
        //mouseevents    added 01/2014
        if (mouseEventQueue==null){
            mouseEventQueue = new LinkedBlockingQueue<Ecs100MouseEvent>();
            mouseEventHandler = new Ecs100MouseEventHandler(mouseEventQueue);
        }
        //endmouseevents
        if (obj != null) {
            theUI.ml = new Ecs100MouseListener(obj);
            theUI.canvas.addMouseListener(theUI.ml);
        }
    }

    /** Enable the user to use the mouse on the graphics
     * pane on the right side of the gui window.
     * This is identical to the <TT>setMouseListener()</TT>
     * method (see above) except that the program will also respond to the user <UL><LI>
     *  moving the mouse (moving it with the button up), and <LI>
     *  dragging the mouse (moving it with the button down) </UL>
     * The argument is the action to respond to the mouse.
     * The action must have three parameters:
     *   a String (the kind of mouse event),
     *   and two doubles (the x and y coordinates where the event happened).
     * Typically, the action will be this::doMouse,
     *   where doMouse is a method with the header
     *      public void doMouse(String action, double x, double y)
     * The action will be passed three values:
     *   a string specifying what the user did:
     *     ("pressed", "released", or "clicked", "doubleclicked")
     *      "moved", or "dragged")
     *   the coordinates (two doubles) of the mouse when the event happened.
     *  Note also that if the user clicks the mouse there will be three events:
     *  - a "pressed",  a "released", and a "clicked".
     */
    public static void setMouseMotionListener(UIMouseListener obj) {
        checkInitialised();
        if (theUI.ml != null){
            theUI.canvas.removeMouseListener(theUI.ml);
            theUI.canvas.removeMouseMotionListener(theUI.ml);
        }
        //mouseevents    added 01/2014
        if (mouseEventQueue==null){
            mouseEventQueue = new LinkedBlockingQueue<Ecs100MouseEvent>();
            mouseEventHandler = new Ecs100MouseEventHandler(mouseEventQueue);
        }
        //endmouseevents
        theUI.ml = new Ecs100MouseListener(obj);
        theUI.canvas.addMouseListener(theUI.ml);
        theUI.canvas.addMouseMotionListener(theUI.ml);
    }


    /* ==== SLEEP and QUIT =========================================================*/

    /** Causes the program to pause for a specified number of milliseconds.
     * This is useful to control graphical animations - do a sleep between consecutive
     * draw... methods to control the speed of the animation.
     * Doesn't allow more than one minute.
     */
    public static void sleep(double millis) {
        long time = (long)(Math.max(0, Math.min(millis, 60000)));
        try { Thread.sleep(time); }
        catch (InterruptedException e) { }
    }

    /** Quit the Java program. */
    public static void quit() {
        /*
        if (theUI!=null){  // removed by pondy Jun 2013 - not needed with System.exit ??
            theUI.dispose();
            Trace.dispose();
            theUI=null;
        }
        */
        System.exit(0); 
    }

}
