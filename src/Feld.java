import java.awt.*;
import javax.swing.*;

public class Feld extends JComponent {
    private int x;
    private Boat boat;
    private int y;
    private char status;

    /**
     * Status des Feldes setzten
     * @param x Koordinate
     * @param y Koordinate
     * @param status Getroffen, Wasser, Nicht Getroffen...
     */
    public Feld(int x, int y, char status) {        //Das ist ein Feld.
        this.x=x;
        this.y=y;
        this.status =status;
    }

    /**
     * Malen in Gui
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,this.getWidth()-1,this.getWidth()-1);
        switch(status) {
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
            case 'w': //TODO Entfernen(Nur als Hilfe!)
                g.setColor(Color.blue);
                g.drawLine(0, 0, this.getWidth(), this.getWidth());
                g.drawLine(this.getWidth(), 0, 0, this.getWidth());
                break;
        }
    }

    public int getXFeldNumber() {
        return x;
    }
    public int getYFeldNumber() {
        return y;
    }
    public char getStatus() {
        return status;
    }

    public void setStatus(char c) {
        this.status = c;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
            return boat;
    }

}