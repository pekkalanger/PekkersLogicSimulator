package Logic;
 /*
    draw line from PinA.pos to PinB.pos
*/
public class LogicLine implements Gate{
    private String name = "Line";
    private Pin pinA ;
    private Pin pinB ;
    
    public LogicLine(){

    }
    
    public LogicLine(Pin pinA, Pin pinB){
        this.pinA = pinA;
        this.pinB = pinB;
    }

    public void setPinA(Pin p){
        this.pinA = p;
    }
    public Pin getPinA(){
        return pinA;
    }
    public void setPinB(Pin p){
        this.pinB = p;
    }
    public Pin getPinB(){
        return pinB;
    }
    
    @Override
    public boolean update() {
        if(pinA != null && pinB != null && pinA.getDataObject() != pinB.getDataObject()){
            if(pinA instanceof InputPin){
                pinB.setDataObject(pinA.getDataObject());
            } else if(pinB instanceof InputPin){
                pinA.setDataObject(pinB.getDataObject());
            }
            System.out.println("Line bit: " + pinA.getDataObject().getData());
            return false;
        } else return true;
        
    }

    public String toString(){
            return name;
    }

    @Override
    public void setInputPin(int pos, InputPin ip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputPin getInputPin(int pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOutputPin(int pos, OutputPin op) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutputPin getOutputPin(int pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
