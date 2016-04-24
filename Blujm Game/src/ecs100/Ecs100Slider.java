package ecs100;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;

class Ecs100Slider extends JSlider {
    private UISliderListener controller;

    public Ecs100Slider(int min, int max, UISliderListener ctrl) { // 
        this(min, max, (min+max)/2, ctrl);
    }

    public Ecs100Slider(int min, int max, int initial, UISliderListener ctrl) { // 
        super(min, max, initial);
        controller = ctrl;
	setMajorTickSpacing((max-min)/2);
	setPaintLabels(true);
	setPreferredSize(new Dimension(150,35));
        addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent e) {
                    if (!getValueIsAdjusting()) {
			new Thread(new Runnable(){public void run(){
			    controller.sliderPerformed(getValue()); 
			}}).start();
                    }
                }});
    }
}
