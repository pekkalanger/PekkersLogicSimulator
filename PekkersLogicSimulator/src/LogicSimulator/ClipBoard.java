/*
 * Copyright (C) 2015 PEKKA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package LogicSimulator;

import LogicSimulator.Objects.Gates.GateObject;
import LogicSimulator.Objects.Gates.GateLogic.GateInterface;
import LogicSimulator.Objects.Gates.GateLogic.InputPin;
import LogicSimulator.Objects.Gates.GateLogic.OutputPin;
import LogicSimulator.Objects.ConnectionLineObject;
import LogicSimulator.Objects.Pin.InputPinObject;
import LogicSimulator.Objects.Pin.OutputPinObject;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 *
 * @author PEKKA
 */
public class ClipBoard {

    private static GateObject gateObject;
    private static ConnectionLineObject connectionLineObject;
    private static Line line;
    private static String name;
    private static Object object;
    private static Group group;
    private static GateInterface gate;
    private static OutputPin outputPin;
    private static InputPin inputPin;
    private static OutputPinObject outputPinObject;
    private static InputPinObject inputPinObject;
    private static double x = -1;
    private static double y = -1;

    public static Object getObject() {
        return object;
    }

    public static void setObject(Object object) {
        ClipBoard.object = object;
    }

    public static GateInterface getGate() {
        return gate;
    }

    public static void setGate(GateInterface gate) {
        ClipBoard.gate = gate;
    }

    public static void setGateObject(GateObject go) {
        gateObject = go;
    }

    public static GateObject getGateObject() {
        return gateObject;
    }

    public static void setConnectionLineObject(ConnectionLineObject clo) {
        connectionLineObject = clo;
    }

    public static ConnectionLineObject getConnectionLineObject() {
        return connectionLineObject;
    }

    public static Group getGroup() {
        return group;
    }

    public static void setGroup(Group g) {
        group = g;
    }

    public static Line getLine() {
        return line;
    }

    public static void setLine(Line l) {
        line = l;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String n) {
        name = n;
    }

    public static InputPin getInputPin() {
        return inputPin;
    }

    public static void setInputPin(InputPin ip) {
        inputPin = ip;
    }

    public static OutputPin getOutputPin() {
        return outputPin;
    }

    public static void setOutputPin(OutputPin op) {
        outputPin = op;
    }

    public static InputPinObject getInputPinObject() {
        return inputPinObject;
    }

    public static void setInputPinObject(InputPinObject ipo) {
        inputPinObject = ipo;
    }

    public static OutputPinObject getOutputPinObject() {
        return outputPinObject;
    }

    public static void setOutputPinObject(OutputPinObject opo) {
        outputPinObject = opo;
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static void setX(double newX) {
        x = newX;
    }

    public static void setY(double newY) {
        y = newY;
    }

    public static void printClipBoard() {
        System.out.println("=======Start Of ClipBoard=========");
        System.out.println(gateObject);
        System.out.println(connectionLineObject);
        System.out.println(name);
        System.out.println(object);
        System.out.println(group);
        System.out.println(gate);
        System.out.println(inputPin);
        System.out.println(outputPin);
        System.out.println(x);
        System.out.println(y);
        System.out.println("=======End Of Clipboard==========");
    }

    public static void clearClipBoard() {
        printClipBoard();
        gateObject = null;
        connectionLineObject = null;
        //connectionLineObject2 = null;
        line = null;
        name = null;
        object = null;
        group = null;
        gate = null;
        inputPin = null;
        outputPin = null;
        inputPinObject = null;
        outputPinObject = null;
        x = -1;
        y = -1;
    }

}
