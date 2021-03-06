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
package LogicSimulator.Objects.Gates.GateLogic;

public class Or extends Gate implements GateInterface {

    public Or(String name) {
        super(2, 1, name);
    }

    @Override
    public boolean update(long deltaTime) {
        if (getInputPin(0).getDataObject().getData() == true || getInputPin(1).getDataObject().getData() == true) {
            showOutputOnConsole(0, false);
            getOutputPin(0).getDataObject().setData(true);
        } else {
            showOutputOnConsole(0, true);
            getOutputPin(0).getDataObject().setData(false);
        }
        return false;
    }

}
