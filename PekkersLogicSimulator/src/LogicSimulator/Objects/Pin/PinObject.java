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
package LogicSimulator.Objects.Pin;

import LogicSimulator.Objects.Gates.GateLogic.InputPin;
import LogicSimulator.Objects.Gates.GateLogic.LogicLine;
import LogicSimulator.Objects.Gates.GateLogic.OutputPin;
import LogicSimulator.ClipBoard;
import static LogicSimulator.ClipBoard.connectionLineObject;
import LogicSimulator.Objects.Gates.GateLogic.DataObject;
import LogicSimulator.Globals;
import LogicSimulator.InfoPopup;
import LogicSimulator.Objects.ConnectionLineObject;
import LogicSimulator.Textures;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PinObject {

    String name;
    public int x; // schematicRectangle translate
    public int y;
    static final int width = 8;
    static final int height = 8;
    protected Rectangle rectangle;
    Image infoImage;
    Image gateImage;
    public List<ConnectionLineObject> connectionLineObjects;
    public ConnectionLineObject connectionLineObject2;

    public PinObject(String n, int x, int y) {
        infoImage = Textures.texture;
        gateImage = Textures.texture;
        name = n;
        this.x = x;
        this.y = y;
        connectionLineObjects = new ArrayList<>();
        connectionLineObject2 = new ConnectionLineObject();
        rectangle = new Rectangle(width, height);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
    }

    public Rectangle createPinRectangle(InputPinObject ipo, final InputPin inputPin, final Group g) {
        ConnectionLineObject connectionLineObject = new ConnectionLineObject();
        connectionLineObjects.add(connectionLineObject);

        Image cursorImage = Textures.defaultCursorActive;
        ImageCursor imageCursor = new ImageCursor(cursorImage, -cursorImage.getWidth(), -cursorImage.getHeight());
        rectangle.setCursor(imageCursor);//rectangle.setCursor(Cursor.HAND);
        rectangle.setFill(new ImagePattern(gateImage, 0, 0, 1, 1, true));
        rectangle.setOnMouseClicked((MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                if (ClipBoard.getInputPin() == null && ClipBoard.getOutputPin() == null) {
                    setDragBoard(inputPin, null, ipo, null, g);
                } else if (ClipBoard.getOutputPin() != null) {
                    if (ClipBoard.getConnectionLineObject2() != null) {
                        LogicLine ll = ClipBoard.getConnectionLineObject2().logicLine;
                        nullLogicLine(ll);
                        ClipBoard.setConnectionLineObject2(null);
                    }
                    LogicLine logicLine = new LogicLine();
                    logicLine.setInputPin(0, inputPin);
                    logicLine.setOutputPin(0, ClipBoard.getOutputPin());
                    ConnectionLineObject connectionLineObject3 = new ConnectionLineObject();
                    connectionLineObject3.logicLine = logicLine;
                    connectionLineObject3.inputPinObjectSource = ipo;
                    connectionLineObjects.add(connectionLineObject3);
                    connectionLineObject2 = ClipBoard.getConnectionLineObject();
                    Line line = connectionLineObject3.createLine(connectionLineObject2, g, rectangle, rectangle.getWidth(), rectangle.getHeight());
                    addLine(line, logicLine);
                    if (ClipBoard.getOutputPinObject() != null && !ClipBoard.getOutputPinObject().connectionLineObjects.contains(connectionLineObject3)) {
                        ClipBoard.getOutputPinObject().connectionLineObjects.add(connectionLineObject3);
                    }
                    if (!Globals.main.connectionLineObjects.contains(connectionLineObject3)) {
                        Globals.main.connectionLineObjects.add(connectionLineObject3);
                    }
                    ClipBoard.clearDragBoard();
                } else if (ClipBoard.getInputPin() == inputPin) {
                    ClipBoard.clearDragBoard();
                } else if (ClipBoard.getInputPin() != inputPin && ClipBoard.getInputPin() != null) {
                    setDragBoard(inputPin, null, ipo, null, g);
                }
            } else if (me.getButton() == MouseButton.MIDDLE) {
                me.consume();
            }
        });
        rectangle.setOnMouseDragged((MouseEvent me) -> {
            me.consume();
        });

        setOnMousePressedReleased(gateImage);
        InfoPopup.setinfoPopup(rectangle, infoImage);
        return rectangle;
    }

    public Rectangle createPinRectangle(OutputPinObject opo, final OutputPin outputPin, final Group g) {
        ConnectionLineObject connectionLineObject = new ConnectionLineObject();
        connectionLineObjects.add(connectionLineObject);
        Image cursorImage = Textures.defaultCursorActive;
        ImageCursor imageCursor = new ImageCursor(cursorImage, -cursorImage.getWidth(), -cursorImage.getHeight());
        rectangle.setCursor(imageCursor);//rectangle.setCursor(Cursor.HAND);
        rectangle.setFill(new ImagePattern(gateImage, 0, 0, 1, 1, true));
        rectangle.setOnMouseClicked((MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                if (ClipBoard.getInputPin() == null && ClipBoard.getOutputPin() == null) {
                    setDragBoard(null, outputPin, null, opo, g);
                } else if (ClipBoard.getInputPin() != null) {
                    if (ClipBoard.getConnectionLineObject2() != null) {
                        LogicLine ll = ClipBoard.getConnectionLineObject2().logicLine;
                        nullLogicLine(ll);
                        ClipBoard.setConnectionLineObject2(null);
                    }
                    LogicLine logicLine = new LogicLine();
                    logicLine.setInputPin(0, ClipBoard.getInputPin());
                    logicLine.setOutputPin(0, outputPin);
                    ConnectionLineObject connectionLineObject3 = new ConnectionLineObject();
                    connectionLineObject3.logicLine = logicLine;
                    connectionLineObject3.outputPinObjectSource = opo;
                    connectionLineObjects.add(connectionLineObject3);
                    connectionLineObject2 = ClipBoard.getConnectionLineObject();
                    Line line = connectionLineObject3.createLine(connectionLineObject2, g, rectangle, rectangle.getWidth(), rectangle.getHeight());
                    addLine(line, logicLine);
                    if (ClipBoard.getInputPinObject() != null && !ClipBoard.getInputPinObject().connectionLineObjects.contains(connectionLineObject3)) {
                        ClipBoard.getInputPinObject().connectionLineObjects.add(connectionLineObject3);
                    }
                    if (!Globals.main.connectionLineObjects.contains(connectionLineObject3)) {
                        Globals.main.connectionLineObjects.add(connectionLineObject3);
                    }
                    ClipBoard.clearDragBoard();
                } else if (ClipBoard.getOutputPin() == outputPin) {
                    ClipBoard.clearDragBoard();
                } else if (ClipBoard.getOutputPin() != outputPin && ClipBoard.getOutputPin() != null) {
                    setDragBoard(null, outputPin, null, opo, g);
                    System.out.println("sorry bro, you cant link an" + ClipBoard.getOutputPin().getClass());
                }
            } else if (me.getButton() == MouseButton.MIDDLE) {
                me.consume();
            }
        });
        rectangle.setOnMouseDragged((MouseEvent me) -> {
            me.consume();
        });

        setOnMousePressedReleased(gateImage);
        InfoPopup.setinfoPopup(rectangle, infoImage);

        return rectangle;
    }

    public void setRectangle(Rectangle r) {
        rectangle = r;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setOnMousePressedReleased(Image i) {
        rectangle.setOnMousePressed((MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                rectangle.setFill(new ImagePattern(Textures.pinPressed, 0, 0, 1, 1, true));
                rectangle.toFront();
                me.consume();
            }
        });
        rectangle.setOnMouseReleased((MouseEvent me) -> {
            rectangle.setFill(new ImagePattern(i, 0, 0, 1, 1, true));
            me.consume();
        });
    }

    public void nullLogicLine(LogicLine ll) {
        if (ll != null) {
            ll.getInputPin(0).setDataObject(new DataObject(false));
            ll.getOutputPin(0).setDataObject(new DataObject(false));
            ll.setInputPin(0, new InputPin());
            ll.setOutputPin(0, new OutputPin());
            //ll = null;
        }
    }

    public void addLine(Line line, LogicLine logicLine) {
        if (line != null && !Globals.main.gateGroup.getChildren().contains(line)) {
            Globals.main.gateGroup.getChildren().add(line);
            Globals.main.logicLines.add(logicLine);
        }
    }

    public void removeLine(Line line, LogicLine logicLine) {
        if (!Globals.main.gateGroup.getChildren().contains(line)) {
            Globals.main.gateGroup.getChildren().add(line);
            Globals.main.logicLines.add(logicLine);
        }
    }

    public void setDragBoard(InputPin inputPin, OutputPin outputPin, InputPinObject ipo, OutputPinObject opo, Group g) {
        ClipBoard.clearDragBoard();
        ClipBoard.setInputPin(inputPin);
        ClipBoard.setOutputPin(outputPin);
        if (connectionLineObject != null) {
            ClipBoard.setLine(connectionLineObject.line);
        }
        ClipBoard.setGroup(g);
        ClipBoard.setName(name);
        ClipBoard.setX(rectangle.getTranslateX());     // + Dragboard.pinOver.setGroup.getTranslateX()
        ClipBoard.setY(rectangle.getTranslateY());      // + Dragboard.pinOver.setGroup.getTranslateY()
        ClipBoard.setOutputPinObject(opo);
        ClipBoard.setInputPinObject(ipo);
        ClipBoard.setConnectionLineObject(connectionLineObject);
        ClipBoard.setConnectionLineObject2(connectionLineObject2);
    }

    public void removeLines() {

    }

}
