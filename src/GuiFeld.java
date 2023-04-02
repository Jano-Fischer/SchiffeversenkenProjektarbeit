import java.awt.*;
import javax.swing.*;

public class GuiFeld extends JComponent {
    private Feld feld;
    public GuiFeld(Feld feld) {        //Übergabe der Werte für x&y-koordinate und des status(freies feld, boot platziert, fehlschuss oder getroffenes Boot)
      this.feld = feld;
    }

    /**
     * Malen in Gui
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,this.getWidth()-1,this.getWidth()-1);
        switch(feld.getStatus()) {
            case 'h':       //Hit
                g.setColor(Color.red);
                g.drawLine(0, 0, this.getWidth(), this.getWidth());
                g.drawLine(this.getWidth(), 0, 0, this.getWidth());
                break;
            case 'm':       //Miss
                g.drawOval(0, 0, this.getWidth(), this.getWidth());
                break;
            case 'p':       //Placed
                    g.drawLine(0, 0, this.getWidth(), this.getWidth());
                    g.drawLine(this.getWidth(), 0, 0, this.getWidth());
                break;
            default:
                break;
        }
    }
  
    public int getXFeldNumber() {
        return feld.getXFeldNumber();
    }
    public int getYFeldNumber() {
        return feld.getYFeldNumber();
    }
    public char getStatus() {
        return feld.getStatus();
    }

    public void setStatus(char c) {
        feld.setStatus(c);
    }

    public void setBoat(Boat boat) {
        feld.setBoat(boat);
    }

    public void removeBoat(){
        feld.removeBoat();
    }

    public Boat getBoat() {
      return feld.getBoat();
    }

}