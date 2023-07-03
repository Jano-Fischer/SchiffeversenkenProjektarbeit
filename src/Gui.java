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
    public GuiSpielfeld playerFieldPlayer1;
    public GuiSpielfeld playerFieldPlayer2;

    //private Spielfeld spielfeld;

    public void disable_shipPlace(){
        shipPlaceButton.setEnabled(false);
    }
    public void enable_shipPlace() {
        shipPlaceButton.setEnabled(true);
    }
    public void setPlayerText(String playerTxt,String subTxt,Color Txt,Color SubTxt) {
        playerText.setText(playerTxt);
        playerText.setForeground(Txt);
        playerSubText.setText(subTxt);
        playerSubText.setForeground(SubTxt);
    }
    public void clearPlayerTexts() {
        playerText.setText("");
        playerSubText.setText("");
    }
    public Gui() {
        Menu menu = new Menu(this,true,config); //Erstellen des Menüs
        strg = new Steuerung(this,config); //Erstellen der Steuerung mit dem Namen strg
        initialize();   //Platzieren der Grafikelemente
    }

    /**
     * Platzieren der Grafikelemente
     */
    private void initialize(){
        setFocusable(true);
        setLayout(null);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }   //Wird für den KeyListener benötigt
            @Override
            public void keyPressed(KeyEvent e) {
            } //Wird für den KeyListener benötigt
            @Override
            /**
             * KeyListener für die Steuerung der Schiffe
             * Abfrage, welche Taste gedrückt wurde und führt die entsprechende Aktion aus.
             */
            public void keyReleased(KeyEvent e) {
                System.out.println("Pressed:"+e.getKeyCode());
                System.out.println(e.getKeyCode());
                System.out.println(e.getKeyChar());
                switch (e.getKeyCode()) {
                    case 38:
                        System.out.println("Hoch"); //Debug
                        strg.move(0, -1,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection());
                        break;
                    case 40:
                        strg.move(0, 1,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection());
                        System.out.println("Runter");
                        break;
                    case 37:
                        strg.move(-1, 0,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection());
                        System.out.println("Links");
                        break;
                    case 39:
                        strg.move(1, 0,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection());
                        System.out.println("Rechts");
                        break;
                    case 17:
                        strg.switchDirection(getX(), getY(),strg.getShipsToPlace(strg.getArrayPosition()),strg.getLock(),strg.isHorizontalDirection());
                        System.out.println("Drehen");
                        break;
                    case 10:
                        strg.placeBoat(strg.getShipsToPlace(strg.getArrayPosition()));
                        System.out.println("Bestätigen");
                        break;
                }
                Gui.this.repaint();
            }
        });
        setBounds(100,100,1920,1080);       //Anzeige Position und Größe des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
//Spieler 1 oder Spieler 2 anzeigen
        activePlayer = new JLabel();
        activePlayer.setBounds(30+10*config.getSize(),config.getSize()-5,5* config.getSize(),2* config.getSize());
        activePlayer.setFont(new Font("title",1,22));
        activePlayer.setText("Spieler 1");
//gibt aktuelle informationen an den Spieler weiter
        playerText = new JLabel();
        playerText.setBounds(1000,0,300,250);
        playerText.setFont(new Font("plTxt1",1,15));
        playerText.setForeground(Color.RED);
        playerText.setText("Willkommen zu Schiffe versenken");
//playerSubText Label(Auch für Informationen)
        playerSubText = new JLabel();
        playerSubText.setBounds(1005,30,300,250);
        playerSubText.setFont(new Font("plTxt2",3,12));
        playerSubText.setForeground(Color.GRAY);
        playerSubText.setText("Bitte platzieren Sie ihre Schiffe");
//Boat Position Set (Knopf fürs Platzieren)
        shipPlaceButton = new JButton();
        shipPlaceButton.setText("Schiff Bestätigen");
        shipPlaceButton.setFocusable(false);
        shipPlaceButton.setBounds(15*config.getSize(),config.getSize(),5* config.getSize(),2* config.getSize());
        shipPlaceButton.addActionListener(e -> keyPressed('p'));
        shipPlaceButton.setVisible(true);
//Button weiter
        weiter = new JButton();
        weiter.setFocusable(false);
        weiter.setText("Weiter");
        weiter.setBounds(550,75,100,50);
        weiter.addActionListener(e -> strg.goOne());
        weiter.setVisible(false);
//Button Hoch
        upS = new JButton();
        upS.setFocusable(false);
        upS.setText("Hoch");
        upS.setBounds(30+13*config.getSize()+10,30+2*config.getSize(),3*config.getSize(),3*config.getSize());
        upS.setVisible(true);
        upS.addActionListener(e -> keyPressed('u'));
//Button Runter
        downS = new JButton();
        downS.setFocusable(false);
        downS.setText("Runter");
        downS.setBounds(30+13*config.getSize()+10,30+8*config.getSize(),3*config.getSize(),3*config.getSize());
        downS.setVisible(true);
        downS.addActionListener(e -> keyPressed('d'));
//Button Rechts
        rightS = new JButton();
        rightS.setFocusable(false);
        rightS.setText("Rechts");
        rightS.setBounds(30+16*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        rightS.setVisible(true);
        rightS.addActionListener(e -> keyPressed('r'));
//Button Links
        leftS = new JButton();
        leftS.setFocusable(false);
        leftS.setText("Links");
        leftS.setBounds(30+10*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        leftS.setVisible(true);
        leftS.addActionListener(e -> keyPressed('l'));
//Vertikal Rotieren
        turnVertikalS = new JButton();
        turnVertikalS.setFocusable(false);
        turnVertikalS.setText("Drehen");
        turnVertikalS.setBounds(30+13*config.getSize()+10,30+5*config.getSize(),3*config.getSize(),3*config.getSize());
        turnVertikalS.setVisible(true);
        turnVertikalS.addActionListener(e -> keyPressed('o'));
//Aktiver Spieler Anzeigen
        cp = getContentPane();
        cp.add(activePlayer);

        Spielfeld fields1 = strg.getPlayerFieldPlayer1();
        playerFieldPlayer1 = new GuiSpielfeld(fields1,config.getSize());
        playerFieldPlayer1.setLocation(30,30);
        cp.add(playerFieldPlayer1);

        Spielfeld fields2 = strg.getPlayerFieldPlayer2();
        playerFieldPlayer2 = new GuiSpielfeld(fields2,config.getSize());
        playerFieldPlayer2.setLocation(30,30);
        cp.add(playerFieldPlayer2);

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
        strg.startGame();
        cp.setVisible(true);
    }

    public void keyPressed(char c) {

        switch (c) {
            case 'u': strg.move(0,-1,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection()); break;
            case 'd': strg.move(0,1,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection()); break;
            case 'r':strg.move(1,0,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection()); break;
            case 'l': strg.move(-1,0,strg.getShipsToPlace(strg.getArrayPosition()),strg.isHorizontalDirection()); break;
            case 'o': strg.switchDirection(getX(), getY(),strg.getShipsToPlace(strg.getArrayPosition()),strg.getLock(),strg.isHorizontalDirection()); break;
            case 'p': strg.placeBoat(strg.getShipsToPlace(strg.getArrayPosition())); break;
        }

        this.repaint();
        GuiSpielfeld guiSpielfeld = playerFieldPlayer1.isVisible() ? playerFieldPlayer1 : playerFieldPlayer2;
        guiSpielfeld.setFocusable(true);    // for keyListener ...
    }

    /**
     * Anzeigen des Spielfeldes welches beschossen wird
     * @param spieler1
     */
    public void showPlayerField(boolean spieler1){
        cp.removeAll();
        cp.add(weiter);
        cp.add(activePlayer);

        if (spieler1){
            playerFieldPlayer1.setVisible(true);
            playerFieldPlayer2.setVisible(false);
        }else{
            playerFieldPlayer2.setVisible(true);
            playerFieldPlayer1.setVisible(false);
            setActivePlayerText("Spieler " + ((strg.isPlayer1()) ? 1 : 2));
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
        cp.add(playerText);
        cp.add(playerSubText);
        if (spieler1) {
            playerFieldPlayer1.setVisible(true);
            playerFieldPlayer2.setVisible(false);
        } else {
            playerFieldPlayer1.setVisible(false);
            playerFieldPlayer2.setVisible(true);
        }
        setActivePlayerText("Spieler " + ((strg.isPlayer1()) ? 1 : 2));
    }
    /**
     * Ausgabe des Spielers der am Zug ist
     * @param text
     */
    public void setActivePlayerText(String text){
        activePlayer.setText(text);
    }

    /**
     * Beendet das Pregame und zeigt das Spielfeld des Spielers an
     */
    public void endPreGame() {
        shipPlaceButton.setVisible(false);
        upS.setVisible(false);
        downS.setVisible(false);
        leftS.setVisible(false);
        rightS.setVisible(false);
        turnVertikalS.setVisible(false);
        weiter.setVisible(true);
        /*
        // TODO: herausfinden, wozu das gut war! Warum 'Ü'???
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
         */
    }

    /**
     * Main Methode
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Gui window = new Gui();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
