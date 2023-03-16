public class Steuerung {
    private final Gui gui;                          //Bekannt machen mit der GUI
    private BoatThings theBoatThing;
    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kann
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private final int boatNummber = 1;              //? (Anzahl der Boote?)
    private int placedShips = 1;                    //speichert anzahl an Schiffen auf dem Feld (Spieler1, o. 2??)
    private int player1DestroyedBoats = 0;          //Zerstörte Schiffe Player 1
    private int player2DestroyedBoats = 0;          //Zerstörte Schiffe Player 2
    public Gui getGui() {
        return gui;
    }              //?
    private int shipX;
    private int shipY;
    public int getShipY() {
        return shipY;
    }          //gibt y-koordinate von Schiff aus (int), (was für ein Schiff?)
    public int getShipX() {
        return shipX;
    }          //gibt x-koordinate von Schiff aus (int), (was für ein Schiff?)

    public void setShipX(int shipX) {
        this.shipX = shipX;
    }                   //setzen des x-Wertes
    public void setShipY(int shipY) {
        this.shipY = shipY;
    }                   //setzen des y-Wertes (Warum solche Kommentare Semjon?)
    public Steuerung(Gui gui) {
        this.gui = gui;
        theBoatThing = new BoatThings(this);
    }

    /**
     * Platzieren aller Schiffe(Temp)
     */
    public void placeShips(){

        placeBattleship();
        //placeCruiser();
        //placeCruiser();


       /* Boat destroyer1 = new Boat(BoatType.THREEBOAT);
        Boat destroyer2 = new Boat(BoatType.THREEBOAT);
        Boat destroyer3 = new Boat(BoatType.THREEBOAT);

        Boat submarines1 = new Boat(BoatType.TWOBOAT);
        Boat submarines2 = new Boat(BoatType.TWOBOAT);
        Boat submarines3 = new Boat(BoatType.TWOBOAT);
        Boat submarines4 = new Boat(BoatType.TWOBOAT);*/
    }   //Platzieren aller Schiffe (Feste Anzahl)
    public int getBoatNummber() {
        return boatNummber;
    }                       //gibt die Anzahl an Booten aus?
    public int getFieldHeight() {
        return fieldHight;
    }                        //gibt die Feldhöhe aus
    public int getFieldWidth() {
        return fieldWidth;
    }                         //gibt die Feldbreite aus
    public void pregame(boolean spieler1) {                                    //?
        boolean schiffeSetzten = false;

    }
    /**
     * Schaut, ob ein Schiff getroffen wurde und ob dieses Zerstört wurde.
     * @param x Koordinate des Feldes welches geklickt wurde
     * @param y Koordinate des Feldes welches geklickt wurde
     */
    public void click(int x, int y) {
        if (lock) return;
        System.out.println("Feld geklickt: " + x + ":" + y + " Spieler:" + ((player1) ? 1 : 2));
        boolean hit = gui.attack(x, y, player1);
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
    public void goOne() {
        player1 = !player1;
        gui.showPlayerField(player1);
        gui.setActivePlayerText("Spieler " + ((player1) ? 1 : 2));
        lock = false;
    }   //Wechseln des Spielfeldes und entsperren des Schiessens

    /**
     * Schaut, ob die Anzahl zerstörter Schiffe genauso groß ist wie die der gesamten Schiffe
     */
    public void winChecker() {
        if (player1) {
            player2DestroyedBoats++;
            if (player2DestroyedBoats == boatNummber) {
                System.out.println("Spieler 1 Gewonnen");
                gui.setActivePlayerText("Spieler 1 hat gewonnen");
                lock = true;
            }
        } else {
            player1DestroyedBoats++;
            if (player1DestroyedBoats == boatNummber) {
                System.out.println("Spieler 2 Gewonnen");
                gui.setActivePlayerText("Spieler 2 hat gewonnen");
                lock = true;

            }
        }
    }   //Methode zur erkennung welcher Spieler gewonnen hat
    boolean placed = false;

    int temp = 1;

    /**
     * Nur temporär? Platzieren des 5er-Schiffes
     */
    public void placeBattleship(){
        Boat  battelship = new Boat(BoatType.FIVEBOOAT);
        while(!placed){

            gui.playerFieldPlayer1[shipX][shipY].setBoat(battelship);
            gui.playerFieldPlayer1[shipX][shipY+1].setBoat(battelship);
            gui.playerFieldPlayer1[shipX][shipY+2].setBoat(battelship);
            gui.playerFieldPlayer1[shipX][shipY+3].setBoat(battelship);
            gui.playerFieldPlayer1[shipX][shipY+4].setBoat(battelship);
        }
    }

    /*public void placeCruiser(){
        Boat cruiser1 = new Boat(BoatType.FOURBOAT);
        Boat cruiser2 = new Boat(BoatType.FOURBOAT);
        while(!placed){
            gui.playerFieldPlayer1[1][2].setBoat(cruiser1);
            gui.playerFieldPlayer1[1][3].setBoat(cruiser1);
            gui.playerFieldPlayer1[1][4].setBoat(cruiser1);
            gui.playerFieldPlayer1[1][5].setBoat(cruiser1);


        }*/
}