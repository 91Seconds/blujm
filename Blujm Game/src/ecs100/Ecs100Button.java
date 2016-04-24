package ecs100;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

class Ecs100Button extends JButton implements ActionListener {
  private UIButtonListener controller;

    /*  public void setButtonListener(UIButtonListener controller) {
    this.controller = controller;
    }*/
    // can be called with a lambda:  () -> ....

  public Ecs100Button(String name, UIButtonListener controller) {
    super(name);
    this.controller = controller;
    addActionListener(this);
  }

  public void actionPerformed(final ActionEvent e) {
      if (controller == null) { return; }
      if (Ecs100TextArea.enablingButtonFlag ||
	  "quit".equalsIgnoreCase(e.getActionCommand()) ||
	  JOptionPane.showOptionDialog(
				       UI.getFrame(),
				       "The program is waiting for text input.\nAre you sure you want to click another button?\nChoosing \"Continue\" may lead to strange behaviour.\n",
				       "Warning",
				       JOptionPane.DEFAULT_OPTION,
				       JOptionPane.WARNING_MESSAGE,
				       null,
				       new Object[]{"Continue", "Cancel"},
				       "Cancel") == 0) {
	  new Thread( () -> controller.buttonPerformed() ).start();
      }
  }
	      
}
