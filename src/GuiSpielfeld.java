import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiSpielfeld extends JPanel {

    private Spielfeld spielfeld;
    public GuiFeld[][] playerFieldPlayer;  //= new GuiFeld[10][10]; //Feld für Spieler 2 der Größe 10x10

    public GuiSpielfeld(Spielfeld fields, int displaySize) {
        super();
        setLayout(null);
        spielfeld=fields;
        playerFieldPlayer= createGuiFeld(fields,displaySize);


        this.setBackground(Color.RED);
        JLabel label = new JLabel("Huhu");
        label.setBounds(0,0,200,20);
        this.add(label);


    }


    /**
     * Erstellen eines Spielfeldes mit der Festen Größe von 10x10 und jedem Feld den Zustand water geben.
     *Jedes Feld erhält einen Mouse Listener
     * @return
     */
    private GuiFeld[][] createGuiFeld(Spielfeld strgFelder, int displaySize) {
        int arraySize = strgFelder.getSize();
        //this.setSize(arraySize*displaySize+10,arraySize*displaySize+10);

        GuiFeld[][] felder = new GuiFeld[arraySize][arraySize];

        for (int x=0;x<arraySize;x++){
            for (int y=0;y<arraySize;y++){
                GuiFeld feld = new GuiFeld(strgFelder.getFeld(x,y));
                felder[x][y] = feld;

                feld.addMouseListener(new  MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Component comp =e.getComponent();
                        if(comp instanceof GuiFeld) {
                            GuiFeld fe = (GuiFeld) comp;            //Typecast

                            int x = fe.getXFeldNumber(); int y = fe.getYFeldNumber();
                            spielfeld.click(x,y);
                            felder[x][y].repaint();
                        }

                    }
                });
                feld.setBounds(x*displaySize,y*displaySize,displaySize,displaySize);
                this.add(feld);
            }
        }
        return felder;
    }

    public void setStatus(int x, int y, char h) {
        playerFieldPlayer[x][y].setStatus(h);
    }
}