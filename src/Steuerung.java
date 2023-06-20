import java.awt.*;
import java.awt.event.KeyListener;

//TODO Mindestens ein Schiff
public class Steuerung {
    // Anfang Attribute
    private final Gui gui;                          //Bekannt machen mit der GUI


    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kann
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    private boolean horizontalDirection =true;      //Speichert die Ausrichtung des Schiffes
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private int player1DestroyedBoats = 0;
    private int player2DestroyedBoats = 0;
    private  BoatType[] shipsToPlace;
    private int arrayPosition=0;
    private int posX =0;
    private int posY =0;

    private Configuration config;
    private boolean pregame =true;
   /* private Feld[][] playerFieldPlayer1 = new Feld[10][10];  //Feld für Spieler 1 der Größe 10x10
    private Feld[][] playerFieldPlayer2 = new Feld[10][10]; //Feld für Spieler 2 der Größe 10x10
    */
    private Spielfeld playerFieldPlayer1=new StrgSpielfeld();
    private Spielfeld playerFieldPlayer2=new StrgSpielfeld();
    // Ende Attribute

    // Anfang Methoden
    public boolean isPlayer1() {
        return player1;
    }
    public int getFieldHeight() {
        return fieldHight;
    }                        //gibt die Feldhöhe aus
    public int getFieldWidth() {
        return fieldWidth;
    }                         //gibt die Feldbreite aus
    public Spielfeld getPlayerFieldPlayer1() {
        return this.playerFieldPlayer1;
    }
    public Spielfeld getPlayerFieldPlayer2() {
        return this.playerFieldPlayer2;
    }
    public boolean isLock() {
        return lock;
    }


    private Feld[][] createFelder() {
        Feld[][] felder = new Feld[10][10];
        for (int x=0;x<10;x++){
            for (int y=0;y<10;y++){
                // GuiFeld feld = new GuiFeld(x,y,'w');
                Feld feld = new Feld(x,y,'w');
                felder[x][y] = feld;
            }
        }
        return felder;
    }

    private void generateShipsToPlace() {
        shipsToPlace = new BoatType[config.getTotalNumber()];
        int index =0;
        for (int i = 0; i < config.getFiveBoats(); i++) {
            shipsToPlace[index] = BoatType.FIVEBOAT;
            index++;
        }
        for (int i = 0; i < config.getFourBoats(); i++) {
            shipsToPlace[index] = BoatType.FOURBOAT;
            index++;
        }
        for (int i = 0; i < config.getThreeBoats(); i++) {
            shipsToPlace[index] = BoatType.THREEBOAT;
            index++;
        }
        for (int i = 0; i < config.getTwoBoats(); i++) {
            shipsToPlace[index] = BoatType.TWOBOAT;
            index++;
        }
    }

    public Steuerung(Gui gui, Configuration config) {
        this.gui = gui;
        this.config = config;
       // this.config = config;
        playerFieldPlayer1 = createFelder();
        playerFieldPlayer2 = createFelder();
        generateShipsToPlace();
    }

    private class StrgSpielfeld extends Spielfeld {
        public void positionIsInvalid() {
            gui.disable_shipPlace();
            gui.setPlayerText("Position ist ungültig", "Bitte verschiebe das Boot auf eine gültige Position", Color.red, Color.gray);
        }

        public void positionIsValid() {
            gui.setPlayerText("platziere das Boot", "ziehe das Boot auf eine beliebige position \n und bestätige dann mit 'Schiff bestätigen' ", Color.black, Color.gray);
        }
    }


    public boolean boatAt() {
        for (int i=0; i==shipsToPlace[arrayPosition].getValue(); i++) {
            if (player1){
                if (horizontalDirection) {
                    if(playerFieldPlayer1[i][posY].getStatus()=='p')
                        return true;
                }else{
                    if(playerFieldPlayer1[posX][i].getStatus()=='p')
                        return true;
                }
            }else{
                if (horizontalDirection) {
                    if (playerFieldPlayer2[i][posY].getStatus() == 'p')
                        return true;
                }else{
                    if(playerFieldPlayer2[posX][i].getStatus()=='p')
                        return true;
                }
            }
        }
        return false;
    }



    /**
     * Schaut, ob ein Schiff getroffen wurde und ob dieses Zerstört wurde.
     * @param x Koordinate des Feldes welches geklickt wurde
     * @param y Koordinate des Feldes welches geklickt wurde
     */
    public void click(int x, int y) {
        if (lock) return;
        System.out.println("Feld geklickt: " + x + ":" + y + " Spieler:" + ((player1) ? 1 : 2));
        boolean hit = boatExists(x, y, player1);
        if (hit) {
            System.out.println("Treffer bei:"+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            boolean destroyed = gui.hitAtIsDestroyed(x, y, player1);
            if (destroyed) {
                winChecker();
            }
        } else {
            System.out.println("Nicht getroffen bei: "+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            gui.missAt(x, y, player1);
            lock = true;
            System.out.println("Nächster Spieler");
        }
    }

    /**
     * Schaut, ob die Anzahl zerstörter Schiffe genauso groß ist wie die der gesamten Schiffe
     */
    public void winChecker() {
        player2DestroyedBoats++;
        if (player2DestroyedBoats == shipsToPlace.length) {
            System.out.println("Spieler 1 Gewonnen");
            gui.setActivePlayerText("Spieler 1 hat gewonnen");
            lock = true;
        }

    }

    /**
     * Wechseln des Spielfeldes und entsperren des Schiessens
     */
    public void goOne() {
        player1 = !player1;
        gui.showPlayerField(player1);
        gui.setActivePlayerText("Spieler " + ((player1) ? 1 : 2));
        lock = false;
    }














    /**
     * Wird aufgerufen,  wenn ein Spieler Schiff Platzieren drückt.
     * Das Spielfeld wird gewechselt und das nächste Schiff aus dem Array wird ausgewählt.
     * Wenn alle Schiffe aus dem Array platziert wurden, werden alle Schiffe des anderen Spielers platziert.
     */
    public void placeBoat() {
        posX = 0;     //Koordianten wo das Schiff spawned
        posY = 0;     //Koordianten wo das Schiff spawned
        horizontalDirection = true;

        if (arrayPosition == shipsToPlace.length-1) {
            player1 = !player1;
            gui.setActivePlayerText("Spieler " + ((isPlayer1()) ? 1 : 2));
            gui.clearPlayerTexts();
            if (!player1) {
                arrayPosition = 0;
                gui.showOtherPlayerFieldPregame(player1);              //showplayerField zeigt Spielfeld an, das beschossen wird (Spielfeld des anderen Spielers)
                move(0, 0);
            } else {
                gui.endPreGame();
                goOne();
            }
        } else {
            arrayPosition++;
            move(0, 0);
        }
    }



        if (isValid()) {
            placeBoat(posX,posY,true);
        }else{
            placeBoat(posX,posY,false);
        }




    }
}
