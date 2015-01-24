/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicSimulator.GateObjects;

import Logic.InputPin;
import Logic.Led;
import Logic.OutputPin;
import Logic.Switch;
import LogicSimulator.Globals;
import LogicSimulator.Textures;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SwitchObject extends GateObject{
    OutputPinObject outputPinObject;
    
     public SwitchObject() {
                
        /*      movable group   */
        group = new Group();
        name = "Switch";
        
        gate = new Switch(false);
        gate.setOutputPin(0, new OutputPin());
        
        final Line lineA = new Line();
        
        // this should be added to a gate list which will be updated all the fucknig time
        // gate also assigned the pins

        outputPinObject = new OutputPinObject(lineA, group, 32, 12, gate.getOutputPin(0), name + " PinA");
        
        rectangle = new Rectangle(32, 32);
        rectangle.setFill(new ImagePattern(Textures.switchOff, 0, 0, 1, 1, true)); /* should create a Gate (square with andGate gate boolean logic linked to pins)*/
        rectangle.setTranslateX(0);
        rectangle.setTranslateY(0);
        
        rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                //change the z-coordinate of the circle
                //circle.toFront();
                Globals.main.showOnConsole("Mouse entered " + name);
                me.consume();
            }
        });
        rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                Globals.main.showOnConsole("Mouse exited " + name);
            }
        });
        
        group.getChildren().addAll(outputPinObject.getRectangle(), rectangle);
        
        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                 //when mouse is pressed, store initial position
                initX = group.getTranslateX();
                initY = group.getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
                //showOnConsole("Mouse pressed above " + name);
            }
        });
        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                 if (me.getButton() == MouseButton.PRIMARY) {
                    double dragX = me.getSceneX() - dragAnchor.getX();
                    double dragY = me.getSceneY() - dragAnchor.getY();
                    double newXPosition = initX + dragX;
                    double newYPosition = initY + dragY;
                    group.setTranslateX(newXPosition);
                    group.setTranslateY(newYPosition);
                    me.consume();
                }
            }
        });
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                group.toFront();
                if (me.getButton() == MouseButton.PRIMARY) {
                    Globals.main.showOnConsole("Clicked on" + name + ", " + me.getClickCount() + "times");
                    rectangle.setFill(new ImagePattern(Textures.switchOff, 0, 0, 1, 1, true)); /* should create a Gate (square with andGate gate boolean logic linked to pins)*/
                    //the event will be passed only to the circle which is on front
                    me.consume();
                } else if (me.getButton() == MouseButton.SECONDARY) {
                    Globals.main.showOnConsole("Clicked on" + name + ", " + me.getClickCount() + "times");
                    rectangle.setFill(new ImagePattern(Textures.switchOn, 0, 0, 1, 1, true)); /* should create a Gate (square with andGate gate boolean logic linked to pins)*/
                    //the event will be passed only to the circle which is on front
                    me.consume();
                } else if (me.getButton() == MouseButton.MIDDLE) {
                    Globals.main.showOnConsole("Removed specified orangeCircle");
                    //Globals.main.circleList.remove(gg); // remove the gate from the list gate all the lines attached to it
                    Globals.main.circleGroup.getChildren().remove(group);
                    
                    //for (int i = 0; i < 3; i++) {
                        if(Globals.main.circleGroup.getChildren().contains(lineA)) {
                            Globals.main.circleGroup.getChildren().remove(lineA);
                        }
                    //}
                    
                    me.consume();
                }
                  
            }
        });
        group.setOpacity(0.8f);
        Globals.main.circleGroup.getChildren().add(group);
    }

    
    @Override
    public void update(boolean clock) {
        //here we will take the data from line and render leds new status (via println())
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
