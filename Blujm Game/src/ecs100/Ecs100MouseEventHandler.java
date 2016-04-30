package ecs100;

import java.util.concurrent.BlockingQueue;

/** Ecs100MouseEventHandler
 The thread that handles the mouse events, sequentially
*/


public class Ecs100MouseEventHandler extends Thread{


    private BlockingQueue<Ecs100MouseEvent> queue;
    private boolean finished = false;

    /**
    */
    public Ecs100MouseEventHandler(BlockingQueue<Ecs100MouseEvent> q){
	queue = q;
	this.start();
    }


    /** keep polling the queue for events and performing them */
    public void run(){
	while (!finished){
	    try {
		Ecs100MouseEvent ev = queue.take();
		ev.controller.mousePerformed(ev.action, ev.x, ev.y);
	    } catch (InterruptedException ex) { }
	}
    }

    public void finish(){
	finished = true;
	queue.clear();
    }

}
