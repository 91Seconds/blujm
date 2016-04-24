package ecs100;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Canvas;  
import java.awt.Component;  
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//extending lightweight components doesn't work on Windows
//extending heavyweight components such as Canvas has a problem
//  if the canvas is in a scroll pane with horizontal scrolling -
//  the canvas will end up scrolling over the top of other components.
//  ==> If you use a Canvas, you must not allow scrolling.
//  canvas seems too slow, but it gets the focus for key events
//  Component doesn't get the focus
//  JComponent doesn't get the focus
//  JPanel doesn't get the focus

class Ecs100Canvas extends JComponent { //
    private Image imgBuf;
    private Graphics2D imgGraphic, visibleGraphic;

    /** Maximum width of the canvas */
    public static final int MaxX = 1024;
    /** Maximum height of the canvas */
    public static final int MaxY = 768;

    private boolean dirty; // whether the canvas has been changed since last repaint
    private Rectangle dirtyBounds = new Rectangle(0,0,-1,-1);

    //Constructor
    public Ecs100Canvas(){
	super();
	addMouseListener(new MouseAdapter() {
		public void mouseEntered(MouseEvent event) {
		    if(!hasFocus()) { requestFocus(); }
		}
	    });
    }


    public void addNotify() {
	super.addNotify();
	this.setBackground(Color.white);
	imgBuf = createImage(MaxX, MaxY);  // Can only be done by peer
	imgGraphic = (Graphics2D) imgBuf.getGraphics();
	imgGraphic.setPaintMode();
	imgGraphic.setColor(Color.black);
	visibleGraphic = (Graphics2D) this.getGraphics();
	clear();
	setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
	g.drawImage(imgBuf, 0, 0, null);
    }

    public void update(Graphics g) {  // Stops component being cleared
	paint(g);
    }

    public Dimension getPreferredSize() {
	return new Dimension(Math.min(640, MaxX), Math.min(480, MaxY));
    }

    public Dimension getMaximumSize() {
	return new Dimension(MaxX, MaxY);
    }

    /** Set the current font size */
    public void setFontSize(int size) {
	imgGraphic.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, size));
    }

    /** Set the current line width */
    public void setLineWidth(double width) {
	imgGraphic.setStroke(new BasicStroke((float)width));
    }

    /**
     * Specify that some region has been modified
     */
    private void setDirty(Shape shape) {
	dirty = true;
	dirtyBounds.add(shape.getBounds());
    }

    private void setDirty(int x, int y, int wd, int ht) {
	setDirty(new Rectangle(x, y, wd, ht));
    }

    /** Request the canvas to be redrawn, if it is dirty.
     */
    public synchronized void redisplay() {
	if (dirty){
	    Shape clip = visibleGraphic.getClip();
	    dirty = false;
	    Rectangle bounds = dirtyBounds; //new Rectangle(0,0,MaxX,MaxY);
	    dirtyBounds = new Rectangle(0,0,-1,-1);
	    visibleGraphic.setClip(bounds);
	    visibleGraphic.drawImage(imgBuf, 0, 0, null);
	    visibleGraphic.setClip(clip);
	    repaint(); 
	}
    }
    /** Request the whole canvas to be redrawn, regardless of dirty.
     */
    public synchronized void redisplayAll() {
	    Shape clip = visibleGraphic.getClip();
	    dirty = false;
	    dirtyBounds = new Rectangle(0,0,-1,-1);
	    Rectangle bounds = new Rectangle(0,0,MaxX,MaxY);
	    visibleGraphic.setClip(bounds);
	    visibleGraphic.drawImage(imgBuf, 0, 0, null);
	    visibleGraphic.setClip(clip);
	    repaint(); 
    }

    /** Get the Graphics object that is the backing store of the image, so that
	programs can do more complicated operations on the image than are
	provided by this class.   
	<BR>
	Standard usage would be to get the graphics object, call methods on it,
	and then call the display() method on the Canvas to update the 
	visible imagewith the modifications.*/
    public Graphics2D getBackingGraphics() {
	return imgGraphic;
    }

    /** Clear the canvas area. */
    public void clear() {
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.fillRect(0, 0, MaxX, MaxY);
	imgGraphic.setColor(save);
	dirty = true;
	dirtyBounds = new Rectangle(0,0, MaxX, MaxY);
    }

    /** Set the current foreground color - the color for all subsequent shapes or 
     * text 
     */
    public void setColor(Color c) {
	imgGraphic.setColor(c);
	//super.setForeground(c);
    }

    // Draw/Fill/Invert/Erase any Shape

    /** Draw the outline of a shape */
    public synchronized void draw(Shape shape) {
	imgGraphic.draw(shape);
	setDirty(shape);
    }

    /** Fill a shape */
    public synchronized void fill(Shape shape) {
	imgGraphic.fill(shape);
	setDirty(shape);
    }

    public synchronized void invert(Shape shape) {
	imgGraphic.setXORMode(Color.white);
	imgGraphic.draw(shape);
	imgGraphic.setPaintMode();
	setDirty(shape);
    }


    /** Erase a shape, both the outline and the fill */
    public synchronized void erase(Shape shape) {
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.draw(shape);
	imgGraphic.fill(shape);
	imgGraphic.setColor(save);
	setDirty(shape);
    }

    // Strings 

    public void drawString(String s, int x, int y) {
	imgGraphic.drawString(s, x, y);
	FontMetrics fm = imgGraphic.getFontMetrics();
	setDirty(x, y-fm.getMaxAscent(), 
		 fm.stringWidth(s)+fm.getMaxAdvance(),
		 fm.getMaxAscent()+fm.getMaxDescent());
    }

    public void invertString(String s, int x, int y) {
	imgGraphic.setXORMode(Color.white);
	imgGraphic.drawString(s, x, y);
	imgGraphic.setPaintMode();
	FontMetrics fm = imgGraphic.getFontMetrics();
	setDirty(x, y-fm.getMaxAscent(), 
		 fm.stringWidth(s)+fm.getMaxAdvance(),
		 fm.getMaxAscent()+fm.getMaxDescent());
    }

    public void eraseString(String s, int x, int y) {
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.drawString(s, x, y);
	imgGraphic.setColor(save);
	FontMetrics fm = imgGraphic.getFontMetrics();
	setDirty(x, y-fm.getMaxAscent(), 
		 fm.stringWidth(s)+fm.getMaxAdvance(),
		 fm.getMaxAscent()+fm.getMaxDescent());
    }

    // Images


    /* from file, scaled*/
    public void drawImage(String name, int x, int y, int width, int height) {
	File fh = new File(name);
	if (fh.canRead()) {
	    MediaTracker media = new MediaTracker(this);
	    Image img = Toolkit.getDefaultToolkit().getImage(name);
	    media.addImage(img, 0);
	    try {media.waitForID(0);} catch (Exception e) {}
	    // imgGraphic.drawImage(img, x, y, width, height, this.getBackground(), this); // broken - didn't handle transparent properly
	    imgGraphic.drawImage(img, x, y, width, height, this);
	} else {
	    // The file either doesn't exist or we don't have read access
	    imgGraphic.drawRect(x, y, width, height);
	    imgGraphic.drawLine(x, y, x+width, y+height);
	    imgGraphic.drawLine(x+width, y, x, y+height);
	}
	setDirty(x, y, width, height);
    }

    /* from file, unscaled*/
    public void drawImage(String name, int x, int y) {
	File fh = new File(name);
	if (fh.canRead()) {
	    Image img;
	    MediaTracker media = new MediaTracker(this);
	    img = Toolkit.getDefaultToolkit().getImage(name);
	    media.addImage(img, 0);
	    try {media.waitForID(0);} catch (Exception e) {}
	    // imgGraphic.drawImage(img, x, y, this.getBackground(), this);  // broken - didn't handle transparent properly
	    imgGraphic.drawImage(img, x, y, this);
	    setDirty(x, y, img.getWidth(this), img.getHeight(this));
	} else {
	    // The file either doesn't exist or we don't have read access
	    imgGraphic.drawRect(x, y, 10, 10);
	    imgGraphic.drawLine(x, y, x+10, y+10);
	    imgGraphic.drawLine(x+10, y, x, y+10);
	    setDirty(x, y, 10, 10);
	}
    }

    /* from Image, scaled*/
    public void drawImage(Image img, int x, int y, int width, int height) {
	// imgGraphic.drawImage(img, x, y, width, height, this.getBackground(), this); // broken - didn't handle transparent properly
	imgGraphic.drawImage(img, x, y, width, height, this);
	setDirty(x, y, width, height);
    }

    /* from Image, unscaled*/
    public void drawImage(Image img, int x, int y) {
	// imgGraphic.drawImage(img, x, y, this.getBackground(), this);// broken - didn't handle transparent properly
	imgGraphic.drawImage(img, x, y, this);
	setDirty(x, y, img.getWidth(this), img.getHeight(this));
    }

    public void eraseImage(String name, int x, int y) {
	File fh = new File(name);
	if (fh.canRead()) {
	    Image img;
	    MediaTracker media = new MediaTracker(this);
	    img = Toolkit.getDefaultToolkit().getImage(name);
	    media.addImage(img, 0);
	    try {media.waitForID(0);} catch (Exception e) {}
	    int width = img.getWidth(this);
	    int height = img.getHeight(this);
	    Color save = imgGraphic.getColor();
	    imgGraphic.setColor(Color.white);
	    imgGraphic.fillRect(x, y, width+1, height+1);
	    imgGraphic.setColor(save);
	    setDirty(x, y, width, height);
	} 
    }

    public void eraseImage(Image img, int x, int y) {
	int width = img.getWidth(this);
	int height = img.getHeight(this);
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.fillRect(x, y, width+1, height+1);
	imgGraphic.setColor(save);
	setDirty(x, y, width, height);
    }
}
