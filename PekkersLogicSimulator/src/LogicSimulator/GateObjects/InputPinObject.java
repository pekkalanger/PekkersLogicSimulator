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
package LogicSimulator.GateObjects;

import LogicSimulator.GateObjects.GateLogic.InputPin;
import LogicSimulator.Textures;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PEKKA
 */
public class InputPinObject extends PinObject {
    
    InputPin inputPin;
    
    public InputPinObject(Group g, int x , int y, InputPin ip, String n) {
        this.x = x;
        this.y = y;
        name = n; 
        this.inputPin = ip;
        
        rectangle = new Rectangle(width, height);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        rectangle = createInputPinRectangle(Textures.inputPin, g, rectangle, ip, name);
    } 

    
    
}
