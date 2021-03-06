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

public interface GateInterface {

    public boolean update(long deltaTime);

    @Override
    public String toString();

    public void setInputPin(int pos, InputPin ip);

    public InputPin getInputPin(int pos);

    public void setOutputPin(int pos, OutputPin op);

    public OutputPin getOutputPin(int pos);

    public void setDataObject(DataObject dataObject);

    public DataObject getDataObject();

    public void toggle();

    public void destroy();
}
