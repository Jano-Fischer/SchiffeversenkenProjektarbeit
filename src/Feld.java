import java.awt.*;
import javax.swing.*;

public class Feld extends JComponent {
    private int x;
    private Boat boat;
    private int y;
    private char status;

    public Feld(int x, int y, char status) {
        this.x=x;
        this.y=y;
        this.status =status;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,this.getWidth()-1,this.getWidth()-1);
        switch(status) {
            case 'h':
                g.setColor(Color.red);
                g.drawLine(0, 0, this.getWidth(), this.getWidth());
                g.drawLine(this.getWidth(), 0, 0, this.getWidth());
                break;
            case 'm':
                g.drawOval(0, 0, this.getWidth(), this.getWidth());
                break;
            case 'p':
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