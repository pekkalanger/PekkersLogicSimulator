package LogicSimulator.GateObjects;

import Logic.And;
import Logic.InputPin;
import Logic.OutputPin;
import LogicSimulator.Textures;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class AndObject extends GateObject{
    
    InputPinObject inputPinObjectA; // maybe use as array gate move all code to GateObject gate extend it
    InputPinObject inputPinObjectB;
    OutputPinObject outputPinObjectQ;
    
    public AndObject() {
        group = new Group();
        name = "And Gate";
        gate = new And();
        gate.setInputPin(0, new InputPin());
        gate.setInputPin(1, new InputPin());
        gate.setOutputPin(0, new OutputPin());
        
        final Line lineA = new Line();  // should be replaced by ConnectionLineObject
        final Line lineB = new Line();  // should be replaced by ConnectionLineObject
        final Line lineQ = new Line();  // should be replaced by ConnectionLineObject
        
        // this should be added to InputPin gate list which will be updated all the fucknig time
        // gate also assigned the pins

        inputPinObjectA = new InputPinObject(lineA, group, 0, 2, gate.getInputPin(0), name + " PinA");
        inputPinObjectB = new InputPinObject(lineB, group, 0, 22, gate.getInputPin(1), name + " PinB");
        outputPinObjectQ = new OutputPinObject(lineQ, group, 40, 12, gate.getOutputPin(0), name + " PinQ");
        
        rectangle = initRectangle(Textures.andGate);
        
        group.getChildren().addAll(inputPinObjectA.getRectangle(), inputPinObjectB.getRectangle(), outputPinObjectQ.getRectangle(), rectangle);
        x = group.getTranslateX();
        y = group.getTranslateY();
        initGroup(lineA, lineB, lineQ);
    }

    
    @Override
    public void update(long deltaTime) {
        //here will the andGate gate drawing of lines be updated 
        x = group.getTranslateX();
        y = group.getTranslateY();
        //System.out.println(x + " " +y);
        if(gate != null) gate.update(deltaTime);
        //lines will get updated coordinates based upon gates coords, render
        
    }
    
}
