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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PEKKA
 */
public class SchematicRectangle {

    private Rectangle rectangle;
    public static int gridWidth = 8;
    public static int gridHeight = 8;

    public SchematicRectangle(Main main) {
        rectangle = new Rectangle(main.schematicWidth, main.schematicHeigth);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
            new Stop(1, Color.rgb(205, 235, 255)), new Stop(0, Color.rgb(205, 235, 255, 0.5))
        }));

        // we can set mouse event to any node, also on the rectangle
        //schematicRectangle.setOnMouseMoved((MouseEvent me) -> {
        //me.consume();
        //});
        /*
         rectangle.setOnMouseClicked((MouseEvent me) -> {
         if (me.getButton() == MouseButton.PRIMARY) {
         if (ClipBoard.getGateObject() != null) {
         ClipBoard.getGateObject().getGroup().setTranslateX(me.getSceneX() - sideBar.getWidth() - 2);
         ClipBoard.getGateObject().getGroup().setTranslateY(me.getSceneY() - menuBar.getHeight() - 2);
         gateObjects.add(ClipBoard.getGateObject());
         ClipBoard.clearDragBoard();
         }
         }
         });
         */
    }

    /* draw a grid on screen*/
    public Group drawGrid(Main main) {
        Group g = new Group();
        for (int i = 0; i < main.schematicWidth; i += gridWidth) {
            Line gridLine = new Line(i, 0, i, rectangle.getHeight());
            gridLine.setStroke(Color.LIGHTGRAY);
            gridLine.setStrokeWidth(1);
            g.getChildren().add(gridLine);
        }
        for (int i = 0; i < main.schematicHeigth; i += gridHeight) {
            Line gridLine = new Line(0, i, rectangle.getWidth(), i);
            gridLine.setStroke(Color.LIGHTGRAY);
            gridLine.setStrokeWidth(1);
            g.getChildren().add(gridLine);
        }
        return g;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

}
