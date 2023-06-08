import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    Configuration config = new Configuration();
    private Steuerung strg; //Verknüpfung mit der Steuerung
    private Container cp;
    private JButton shipPlaceButton;
    private JButton weiter;
    private JButton upS;
    private JButton downS;
    private JButton rightS;
    private JButton leftS;
    private JButton turnVertikalS;
    private JLabel activePlayer;
    private JLabel playerText;
    private JLabel playerSubText;
    public GuiFeld[][] playerFieldPlayer1 = new GuiFeld[10][10];  //Feld für Spieler 1 der Größe 10x10
    public GuiFeld[][] playerFieldPlayer2 = new GuiFeld[10][10]; //Feld für Spieler 2 der Größe 10x10
    public Gui() {
        setFocusable(true);
        Menu menu = new Menu(this,true,config);
        strg = new Steuerung(this,config); //Erstellen der Steuerung mit dem Namen strg
        initialize();
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
        GuiFeld feld;
        if (spieler1){
            feld = playerFieldPlayer2[x][y];
        } else{
            feld = playerFieldPlayer1[x][y];
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
        GuiFeld feld;
        if (spieler1){
            feld= playerFieldPlayer2[x][y];
        } else{
            feld= playerFieldPlayer1[x][y];
        }
        feld.setStatus('m');
        feld.repaint();
    }

    /**
     * Platzieren der Grafikelemente7
     */
    private void initialize(){
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Presssed:"+e.getKeyCode());
                System.out.println(e.getKeyCode());
                System.out.println(e.getKeyChar());
                switch (e.getKeyCode()) {
                    case 38:
                        System.out.println("Hoch");
                        strg.move(0, -1);

                        break;
                    case 40:
                        strg.move(0, 1);
                        System.out.println("Runter");
                        break;
                    case 37:
                        strg.move(-1, 0);
                        System.out.println("LEFT");
                        break;
                    case 39:
                        strg.move(1, 0);
                        System.out.println("RECHTS");
                        break;
                    case 17:
                        strg.switchDirection();
                        System.out.println("Drehen");
                        break;
                    case 10:
                        strg.placeBoat();
                        System.out.println("Bestätigen");
                        break;
                }
            }
        });
        setBounds(100,100,1920,1080);       //Anzeige Position und Größe
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
//active player label
        activePlayer = new JLabel();
        activePlayer.setBounds(30+10*config.getSize(),config.getSize()-5,5* config.getSize(),2* config.getSize());
        activePlayer.setFont(new Font("title",1,22));
        activePlayer.setText("Spieler 1");
//playerText label                                                        //gibt aktuelle informationen an den Spieler weiter
        playerText = new JLabel();
        playerText.setBounds(1000,0,300,250);
        playerText.setFont(new Font("plTxt1",1,15));
        playerText.setForeground(Color.RED);
        playerText.setText("Willkommen zu Schiffe versenken");
//playerSubText Label
        playerSubText = new JLabel();
        playerSubText.setBounds(1005,30,300,250);
        playerSubText.setFont(new Font("plTxt2",3,12));
        playerSubText.setForeground(Color.GRAY);
        playerSubText.setText("Bitte platzieren Sie ihre Schiffe");
//Boat Position Set
        shipPlaceButton = new JButton();
        shipPlaceButton.setText("Schiff Bestätigen");
        shipPlaceButton.setFocusable(false);
        shipPlaceButton.setBounds(15*config.getSize(),config.getSize(),5* config.getSize(),2* config.getSize());
        shipPlaceButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                    strg.placeBoat();
            }
        });
        shipPlaceButton.setVisible(true);
//Button weiter
        weiter = new JButton();
        weiter.setFocusable(false);
        weiter.setText("Weiter");
        weiter.setBounds(550,75,100,50);
        weiter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.goOne();
            }
        });
        weiter.setVisible(false);
//Button Hoch
        upS = new JButton();
        upS.setFocusable(false);
        upS.setText("Hoch");
        upS.setBounds(30+13*config.getSize()+10,30+2*config.getSize(),3*config.getSize(),3*config.getSize());
        upS.setVisible(true);
        upS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(0,-1);
            }
        });
//Button Runter
        downS = new JButton();
        downS.setFocusable(false);
        downS.setText("Runter");
        downS.setBounds(30+13*config.getSize()+10,30+8*config.getSize(),3*config.getSize(),3*config.getSize());
        downS.setVisible(true);
        downS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(0,1);
            }
        });
//Button Rechts
        rightS = new JButton();
        rightS.setFocusable(false);
        rightS.setText("Rechts");
        rightS.setBounds(30+16*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        rightS.setVisible(true);
        rightS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(1,0);
            }
        });
//Button Links
        leftS = new JButton();
        leftS.setFocusable(false);
        leftS.setText("Links");
        leftS.setBounds(30+10*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        leftS.setVisible(true);
        leftS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.move(-1,0);
            }
        });
//Vertikal Rotieren
        turnVertikalS = new JButton();
        turnVertikalS.setFocusable(false);
        turnVertikalS.setText("Drehen");
        turnVertikalS.setBounds(30+13*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        turnVertikalS.setVisible(true);
        turnVertikalS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strg.switchDirection();
            }
        });
//Aktiver Spieler Anzeigen
        cp = getContentPane();
        add(activePlayer);
//Erstellen der Spielfelder für 2 Spieler
        Feld[][] fields1 = strg.getPlayerFieldPlayer1();
        Feld[][] fields2 = strg.getPlayerFieldPlayer2();
        playerFieldPlayer1 = createGuiFeld(fields1);
        playerFieldPlayer2 = createGuiFeld(fields2);
        showPlayerField(false);
        cp.add(upS);
        cp.add(downS);
        cp.add(leftS);
        cp.add(rightS);
        cp.add(shipPlaceButton);
        cp.add(turnVertikalS);
        cp.add(playerText);
        cp.add(playerSubText);


        activePlayer.setVisible(true);
        playerText.setVisible(true);
        playerSubText.setVisible(true);
    
        // setVisible(true);          // wird in der main() gemacht ...

//platzieren des ersten Bootes    
        strg.move(0,0);
    }

    /**
     * Erstellen eines Spielfeldes mit der Festen Größe von 10·10 und jedem Feld den Zustand water geben.
     *Jedes Feld erhält einen Mouse Listener
     * @return
     */
    private GuiFeld[][] createGuiFeld(Feld[][] strgFelder) {
        GuiFeld[][] felder = new GuiFeld[10][10];
        for (int x=0;x<10;x++){
            for (int y=0;y<10;y++){
                GuiFeld feld = new GuiFeld(strgFelder[x][y]);
                feld.setBounds(x*config.getSize()+30,y*config.getSize()+30,config.getSize(),config.getSize());
                feld.addMouseListener(new  MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Component comp =e.getComponent();
                        if(comp instanceof GuiFeld) {
                            GuiFeld fe = (GuiFeld) comp;            //Typcast

                            strg.click(fe.getXFeldNumber(),fe.getYFeldNumber());
                        }

                    }
                });
                felder[x][y] = feld;
            }
        }
        return felder;
    }

    public void disable_shipPlace(){
        shipPlaceButton.setEnabled(false);
    }
    public void enable_shipPlace() {
        shipPlaceButton.setEnabled(true);
    }
    public void setPlayerText(String pMessage) {
        playerText.setText(pMessage);
    }
    public void setPlayerText(String playerTxt,String subTxt) {
        playerText.setText(playerTxt);
        playerSubText.setText(subTxt);
    }
    public void setPlayerText(String playerTxt,String subTxt,Color Txt,Color SubTxt) {
        playerText.setText(playerTxt);
        playerText.setForeground(Txt);

        playerSubText.setText(subTxt);
        playerSubText.setForeground(SubTxt);


    }
    public void setPlayerSubText(String pMessage) {
        playerSubText.setText(pMessage);
    }

    public void clearPlayerTexts() {
        playerText.setText("");
        playerSubText.setText("");
    }

    /**
     * Anzeigen des Spielfeldes welches beschossen wird
     * @param spieler1
     */
    public void showPlayerField(boolean spieler1){
        cp.removeAll();
        cp.add(weiter);
        cp.add(activePlayer);
        GuiFeld[][] felder;
        if (spieler1){
            felder= playerFieldPlayer2;
        }else{
            setActivePlayerText("Spieler " + ((strg.isPlayer1()) ? 1 : 2));
            felder= playerFieldPlayer1;
        }
        for (int x=0;x<10;x++) {
            for (int y = 0; y < 10; y++) {
                cp.add(felder[x][y]);
                felder[x][y].repaint();
            }
        }
    }
    public void showOtherPlayerFieldPregame(boolean spieler1){

        cp.removeAll();
        cp.add(activePlayer);
        cp.add(shipPlaceButton);
        cp.add(upS);
        cp.add(downS);
        cp.add(leftS);
        cp.add(rightS);
        cp.add(turnVertikalS);
        GuiFeld[][] felder;
        if (spieler1){
            felder= playerFieldPlayer1;
        }else{
            setActivePlayerText("Spieler " + ((strg.isPlayer1()) ? 1 : 2));
            felder= playerFieldPlayer2;
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
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void endPreGame() {
        shipPlaceButton.setVisible(false);
        upS.setVisible(false);
        downS.setVisible(false);
        leftS.setVisible(false);
        rightS.setVisible(false);
        turnVertikalS.setVisible(false);
        weiter.setVisible(true);
        for (GuiFeld[] row : playerFieldPlayer1) {
            for (GuiFeld feld : row) {
                feld.setStatus('ü');
            }
        }
        for (GuiFeld[] row : playerFieldPlayer2) {
            for (GuiFeld feld : row) {
                feld.setStatus('ü');
            }
        }
    }

    //TODO KeyListener
}
