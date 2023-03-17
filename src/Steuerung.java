public class Steuerung {
    private final Gui gui;                          //Bekannt machen mit der GUI
    private BoatThings theBoatThing;
    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kann
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    public void setHorizontalDirection(boolean horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }
    private boolean horizontalDirection =true;
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private final int boatNummber = 1;              //? (Anzahl der Boote?)
    private int placedShips = 0;                    //speichert anzahl an Schiffen auf dem Feld (Spieler1, o. 2??)
    private int player1DestroyedBoats = 0;          //Zerstörte Schiffe Player 1
    private int player2DestroyedBoats = 0;          //Zerstörte Schiffe Player 2

    public Steuerung(Gui gui) {
        this.gui = gui;
        theBoatThing = new BoatThings(this);
    }

    public int getFieldHeight() {
        return fieldHight;
    }                        //gibt die Feldhöhe aus
    public int getFieldWidth() {
        return fieldWidth;
    }                         //gibt die Feldbreite aus

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
    }
    /**
     * Platziert ein Boot
     * @param x Koordinate des Boots
     * @param y Koordinate des Boots
     * @param type Boot länge
     */
    public void placeBoat(int x,int y,BoatType type){
        Boat boat = new Boat(type);
        if (horizontalDirection){
            for (int i=x;i<=x+boat.getBoatType().getValue();i++){
                gui.playerFieldPlayer1[i][y].setBoat(boat);
            }
        } else {
            for (int i=y;i<=y+boat.getBoatType().getValue();i++){
                gui.playerFieldPlayer1[x][i].setBoat(boat);
            }
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