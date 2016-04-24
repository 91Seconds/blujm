package ecs100;

/** Ecs100MouseEvent   */


public class Ecs100MouseEvent{

    final UIMouseListener controller;
    final String action;
    final double x;
    final double y;
    /**
    */
    public Ecs100MouseEvent(UIMouseListener c, String a, double x, double y){
	controller = c;
	action = a;
	this.x = x;
	this.y = y;
    }


}
