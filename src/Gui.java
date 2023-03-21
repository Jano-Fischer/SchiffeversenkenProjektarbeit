import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    private Steuerung strg; //Verknüpfung mit der Steuerung
    private JFrame frame;
    private Container cp;
    private JButton shipPlaceButton;
    private JButton weiter;
    private JButton upS;
    private JButton downS;
    private JButton rightS;
    private JButton leftS;
    private JButton turnVertikalS;
    private JLabel activePlayer;


    public Gui() {
        initialize();
    }

    /**
     * Greift das Feld bei X und Y an. Wenn kein Boot getroffen wurde, ist attack false
     * @param x Beschossene Stelle
     * @param y Beschossene Stelle
     * @param spieler1  Aktueller Spieler
     * @return
     */
    public boolean boatExists(int x, int y, boolean spieler1) {
        Boat boat;
         if (spieler1){
            boat = strg.playerFieldPlayer2[x][y].getBoat();
         }else{
           boat = strg.playerFieldPlayer1[x][y].getBoat();
         }
         if (boat==null){
             return false;
         } else {
             return true;
         }
    }

    /**
     * Wird ausgeführt bei Treffer.
     * Auswahl des aktuellen Spielers und setzt den Status des Feldes auf hit. Danach Repaint des Feldes.
     * Abfrage, ob das Schiff durch den Treffer zerstört wurde. Wenn Ja, dann rückgabewert true.
     * @param x Beschossene Stelle
     * @param y Beschossene Stelle
     * @param spieler1  Auswahl des Spielers
     * @return ob das Schiff zerstört wurde
     */
    public boolean hitAtIsDestroyed(int x, int y, boolean spieler1) {
        Feld feld;
        if (spieler1){
            feld= strg.playerFieldPlayer2[x][y];
        } else{
            feld= strg.playerFieldPlayer1[x][y];
        }
        feld.setStatus('h');
        feld.repaint();
        return feld.getBoat().isDestroyed();
    }

    /**
     * Ausgabe an welcher Stelle man nicht getroffen hat, sowie zustandsänderung des Feldes auf miss
     * @param x Beschossene Stelle
     * @param y Beschossene Stelle
     * @param spieler1 Aktueller Spieler
     */
    //TODO In Steuerung auslagern
    public void missAt(int x, int y, boolean spieler1) {
        Feld feld;
        if (spieler1){
            feld= strg.playerFieldPlayer2[x][y];
        } else{
            feld= strg.playerFieldPlayer1[x][y];
        }
        feld.setStatus('m');
        feld.repaint();
    }
    /**
     * Platzieren der Grafikelemente7
     */
    private void initialize(){
        strg = new Steuerung(this); //Erstellen der Steuerung mit dem Namen strg
        frame = new JFrame();
        frame.setBounds(100,100,1920,1080);       //Anzeige Position und Größe
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        activePlayer = new JLabel();
        activePlayer.setBounds(550,0,150,100);
        activePlayer.setText("Spieler 1");
//Boat Position Set
        shipPlaceButton = new JButton();
        shipPlaceButton.setText("Schiff Bestätigen");
        shipPlaceButton.setBounds(700,75,120,50);
        shipPlaceButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.placeBoat();
            }
        });
        shipPlaceButton.setVisible(true);
//Button weiter
        weiter = new JButton();
        weiter.setText("Weiter");
        weiter.setBounds(550,75,100,50);
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.goOne();
            }
        });
        weiter.setVisible(true);
//Button Hoch
        upS = new JButton();
        upS.setText("Hoch");
        upS.setBounds(550,170,100,100);
        upS.setVisible(true);       //TODO Nur sichtbar wenn Pregame
        upS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(0,-1);
            }
        });
//Button Runter
        downS = new JButton();
        downS.setText("Runter");
        downS.setBounds(550,270,100,100);
        downS.setVisible(true);       //TODO Nur sichtbar wenn Pregame
        downS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(0,1);
            }
        });
//Button Rechts
        rightS = new JButton();
        rightS.setText("Rechts");
        rightS.setBounds(650,170,100,100);
        rightS.setVisible(true);       //TODO Nur sichtbar wenn Pregame
        rightS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(1,0);
            }
        });
//Button Links
        leftS = new JButton();
        leftS.setText("Links");
        leftS.setBounds(650,270,100,100);
        leftS.setVisible(true);       //TODO Nur sichtbar wenn Pregame
        leftS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(-1,0);
            }
        });
//Vertikal Rotieren
        turnVertikalS = new JButton();
        turnVertikalS.setText("Drehen");
        turnVertikalS.setBounds(750,270,100,100);
        turnVertikalS.setVisible(true);
        turnVertikalS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    strg.switchDirection();
            }
        });
//Aktiver Spieler Anzeigen
        cp = frame.getContentPane();
        frame.add(weiter);
        frame.add(activePlayer);
//Erstellen der Spielfelder für 2 Spieler
        strg.playerFieldPlayer1 = generateFields();
        strg.playerFieldPlayer2 = generateFields();
        showPlayerField(false);
        cp.add(upS);
        cp.add(downS);
        cp.add(leftS);
        cp.add(rightS);
        cp.add(shipPlaceButton);
        cp.add(turnVertikalS);
        activePlayer.setVisible(true);
    }

    /**
     * Erstellen eines Spielfeldes mit der Festen Größe von 10·10 und jedem Feld den Zustand water geben.
     *Jedes Feld erhält einen Mouse Listener
     * @return
     */
    public Feld[][] generateFields(){
        Feld[][] felder = new Feld[10][10];
        for (int x=0;x<10;x++){
            for (int y=0;y<10;y++){
                Feld feld = new Feld(x,y,'w');
                feld.setBounds(x*strg.getFieldHeight()+30,y*strg.getFieldWidth()+30,strg.getFieldWidth(),strg.getFieldHeight());
                feld.addMouseListener(new  MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Component comp =e.getComponent();
                        if(comp instanceof Feld) {
                            Feld fe = (Feld) comp;            //Typcast
                            strg.click(fe.getXFeldNumber(),fe.getYFeldNumber());
                        }

                    }
                });


                felder[x][y] = feld;
            }
        }
        return felder;
    }   //Array mit Feldern befüllen und an die richtige position, abhängig des Feldes setzten. Außerdem wird ein MouseListener zu jedem Feld hinzufügen
    /**
     * Anzeigen des Spielfeldes welches beschossen wird
     * @param spieler1
     */
    public void showPlayerField(boolean spieler1){
        cp.removeAll();
        cp.add(weiter);
        cp.add(activePlayer);
        Feld[][] felder;
        if (spieler1){
           felder= strg.playerFieldPlayer2;
        }else{
            setActivePlayerText("Spieler " + ((strg.isPlayer1()) ? 1 : 2));
            felder= strg.playerFieldPlayer1;
        }
        for (int x=0;x<10;x++) {
            for (int y = 0; y < 10; y++) {
                cp.add(felder[x][y]);
                felder[x][y].repaint();
            }
        }
    }

    /**
     * Ausgabe des Spielers der am Zug ist
     * @param text
     */
    public void setActivePlayerText(String text){
        activePlayer.setText(text);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gui window = new Gui();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }   //Main-methode
}
