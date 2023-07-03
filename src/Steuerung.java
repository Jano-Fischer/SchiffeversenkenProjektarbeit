import java.awt.*;
public class Steuerung {
    private final Gui gui;                          //Bekannt machen mit der GUI
    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kann
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    private boolean horizontalDirection =true;      //Speichert die Ausrichtung des Schiffes
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private int player1DestroyedBoats = 0;
    private int player2DestroyedBoats = 0;
    private int arrayPosition=0;
    private final int startwertX =0; //Koordinaten wo das Schiff spawned
    private final int startwertY =0; //Koordinaten wo das Schiff spawned
    private  BoatType[] shipsToPlace;
    private int x;
    private int y;

    public boolean isHorizontalDirection() {
        return horizontalDirection;
    }
    public boolean getLock() {
        return lock;
    }
    public BoatType getShipsToPlace(int arrayPosition) {
        return shipsToPlace[arrayPosition];
    }
    public int getArrayPosition() {
        return arrayPosition;
    }

    private Configuration config;
   /* private Feld[][] playerFieldPlayer1 = new Feld[10][10];  //Feld für Spieler 1 der Größe 10x10
    private Feld[][] playerFieldPlayer2 = new Feld[10][10]; //Feld für Spieler 2 der Größe 10x10
    */
    private Spielfeld playerFieldPlayer1 =new StrgSpielfeld(this);
    private Spielfeld playerFieldPlayer2 =new StrgSpielfeld(this);
    public boolean isPlayer1() {
        return player1;
    }
    public Spielfeld getPlayerFieldPlayer1() {
        return this.playerFieldPlayer1;
    }
    public Spielfeld getPlayerFieldPlayer2() {
        return this.playerFieldPlayer2;
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
        generateShipsToPlace();
    }

    /**
     * wird am Ende des GUI-Konstruktors aufgerufen, wenn die GUI bereit ist
     */
    public void startGame() {
        move(0,0,getShipsToPlace(getArrayPosition()),isHorizontalDirection());
    }


    private class StrgSpielfeld extends Spielfeld {
        public StrgSpielfeld(Steuerung strg) {
            super(strg);
        }

        @Override
        public void positionIsInvalid() {
            gui.disable_shipPlace();
            gui.setPlayerText("Position ist ungültig", "Bitte verschiebe das Boot auf eine gültige Position", Color.red, Color.gray);
        }
        @Override
        public void positionIsValid() {
            gui.setPlayerText("platziere das Boot", "ziehe das Boot auf eine beliebige position \n und bestätige dann mit 'Schiff bestätigen' ", Color.black, Color.gray);
        }

        @Override
        public void enable_shipPlace() {
            gui.enable_shipPlace();
        }
    }
    /**
     * Schaut, ob ein Schiff getroffen wurde und ob dieses Zerstört wurde.
     * @param x Koordinate des Feldes welches geklickt wurde
     * @param y Koordinate des Feldes welches geklickt wurde
     */
    public void click(int x, int y) {
        if (lock) return;
        Spielfeld spielfeld = player1 ? playerFieldPlayer1 : playerFieldPlayer2;
        Feld feld = spielfeld.getFeld(x,y);
        System.out.println("Feld geklickt: " + x + ":" + y + " Spieler:" + ((player1) ? 1 : 2));

        boolean hit = spielfeld.boatExists(x, y);
        if (hit) {
            System.out.println("Treffer bei:"+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            feld.setStatus('h');
            boolean destroyed = feld.getBoat().isDestroyed();
            if (destroyed) {
                winChecker();
            }
        } else {
            System.out.println("Nicht getroffen bei: "+x+":"+y+" Spieler:" + ((player1) ? 1 : 2));
            feld.setStatus('m');
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
        } else{
            //TODO 2 Spieler
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
     * Wird aufgerufen, wenn ein Spieler Schiff Platzieren drückt.
     * Das Spielfeld wird gewechselt und das nächste Schiff aus dem Array wird ausgewählt.
     * Wenn alle Schiffe aus dem Array platziert wurden, werden alle Schiffe des anderen Spielers platziert.
     */
    public void placeBoat(BoatType boatType) {
        Spielfeld spielfeld = player1 ? playerFieldPlayer1 : playerFieldPlayer2;
        horizontalDirection = true; //Standartausrichtung ist horizontal
        if (arrayPosition == shipsToPlace.length-1) {
            player1 = !player1;
            gui.setActivePlayerText("Spieler " + ((isPlayer1()) ? 1 : 2));
            gui.clearPlayerTexts();
            if (!player1) {
                arrayPosition = 0;
                gui.showOtherPlayerFieldPregame(player1);              //showPlayerField zeigt Spielfeld an, das beschossen wird (Spielfeld des anderen Spielers)
                playerFieldPlayer2.move(0, 0,boatType,true);
            } else {
                gui.endPreGame();
                goOne();
            }
        } else {
            arrayPosition++;
            spielfeld.move(0, 0,boatType, true);
        }
        if (spielfeld.isValid(x,y, boatType, horizontalDirection,getLock())) {
            spielfeld.placeBoat(startwertX, startwertY,boatType, horizontalDirection,true);
        }else{
            spielfeld.placeBoat(startwertX, startwertY,boatType, horizontalDirection,false);
        }
    }
    public void move(int x, int y, BoatType boatType, boolean horizontalDirection){
        Spielfeld spielfeld = player1 ? playerFieldPlayer1 : playerFieldPlayer2;
        spielfeld.move(x,y,boatType,horizontalDirection);
    }
    public void switchDirection(int x, int y, BoatType boatType, Boolean lock, Boolean horizontalDirection) {
        Spielfeld spielfeld = player1 ? playerFieldPlayer1 : playerFieldPlayer2;
        spielfeld.switchDirection(x,y,boatType,lock,horizontalDirection);
    }
}
