public class Steuerung {
    private final Gui gui;                          //Bekannt machen mit der GUI
    private BoatThings theBoatThing;
    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kan n
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    private boolean horizontalDirection =true;      //Speichert die Ausrichtung des Schiffes
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private final int boatNummber = 8;              //? (Anzahl der Boote?) //TODO anzahl bei ändern der schiffanzahl anpassen
    private int placedShips = 0;                    //speichert anzahl an Schiffen auf dem Feld (Spieler1, o. 2??)
    private int player1DestroyedBoats = 0;          //Zerstörte Schiffe Player 1
    private int player2DestroyedBoats = 0;          //Zerstörte Schiffe Player 2
    private final BoatType[] shiptypes = {BoatType.TWOBOAT,BoatType.FOURBOAT,BoatType.THREEBOAT,BoatType.TWOBOAT,BoatType.TWOBOAT,BoatType.ONEBOAT,BoatType.ONEBOAT,BoatType.ONEBOAT};
    private int arrayPosition=0;
    private int posX =0;
    private int posY =0;
    public Feld[][] playerFieldPlayer1 = new Feld[10][10];  //Feld für Spieler 1 der Größe 10x10 TODO Private?
    public Feld[][] playerFieldPlayer2 = new Feld[10][10]; //Feld für Spieler 2 der Größe 10x10  TODO Private?
    public boolean isPlayer1() {
        return player1;
    }
    public int getFieldHeight() {
        return fieldHight;
    }                        //gibt die Feldhöhe aus
    public int getFieldWidth() {
        return fieldWidth;
    }                         //gibt die Feldbreite aus

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
        boolean hit = gui.boatExists(x, y, player1);
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
     * Wechseln des Spielfeldes und entsperren des Schiessens
     */
    public void goOne() {
        player1 = !player1;
        gui.showPlayerField(player1);
        gui.setActivePlayerText("Spieler " + ((player1) ? 1 : 2));
        lock = false;
    }

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
     * Platziert ein Boot, je nach ausrichtung. //TODO Spieler 2
     * @param x Koordinate des Boots
     * @param y Koordinate des Boots
     */
    private void placeBoat(int x,int y){
        Boat boat = new Boat(shiptypes[arrayPosition]);
        System.out.println("Boot auswahl anerkannt");       //TODO weg
        if (horizontalDirection){
            for (int i=x;i<x+boat.getBoatType().getValue();i++){
                gui.playerFieldPlayer1[i][y].setBoat(boat);
                gui.playerFieldPlayer1[i][y].repaint();
            }
        } else {
            for (int i=y;i<y+boat.getBoatType().getValue();i++){
                gui.playerFieldPlayer1[x][i].setBoat(boat);
                gui.playerFieldPlayer1[x][i].repaint();
            }
        }
    }

    /**
     * Löscht das Boot, wenn es verschoben wird vom alten Feld
     * @param x Koordinate
     * @param y Koordinate
     */
    public void deleteBoat(int x,int y){
        if (horizontalDirection){
            for (int i=x;i<x+shiptypes[arrayPosition].getValue();i++){
                gui.playerFieldPlayer1[i][y].removeBoat();
                gui.playerFieldPlayer1[i][y].repaint();
            }
        } else {
            for (int i=y;i<y+shiptypes[arrayPosition].getValue();i++){
                gui.playerFieldPlayer1[x][i].removeBoat();
                gui.playerFieldPlayer1[x][i].repaint();
            }
        }
    }

    /**
     * Wird aufgerufen, wenn ein Knopf zum Verschieben gedrückt wurde. Ruft zuerst deleteBoat() auf, und Verschiebt das Boot je nach eingabe des Spielers.
     * @param x Gibt an um wie viel das Boot in X-Richtung verschoben wird.
     * @param y Gibt an um wie viel das Boot in Y-Richtung verschoben wird.
     */
    public void move(int x, int y) {
        deleteBoat(posX,posY);
        posX = posX +x;
        posY = posY +y;
        System.out.println(x +" " +y);
        System.out.println(posX +" : " +posY +"Bot typ " +shiptypes[arrayPosition].getValue());
        if(posX<0||posY<0||!inField()||!isValid()){
            posX = posX -x;
            posY = posY -y;
        }
        System.out.println("Place" +posX +" : " +posY);
        placeBoat(posX,posY);
    }

    /**
     * Kontrolliert, ob sich das Boot im Spielfeld befindet
     * @return False, wenn das Boot nicht mehr im Spielfeld ist
     */
    private boolean inField(){
        if(horizontalDirection){
            return (posX + shiptypes[arrayPosition].getValue() <= 10 && posY<10);
        }else {
            return (posY + shiptypes[arrayPosition].getValue() <= 10 && posX<10);
        }
    }

    /**
     * Kontrolliert, ob auf der aktuellen Position schon ein Schiff steht
     * @return False, wenn die aktuelle position schon von einem Schiff belegt ist
     */
    public boolean isValid(){
        if(player1) {
            for (int i = 0; i< shiptypes[arrayPosition].getValue(); i++){
                if (horizontalDirection) {
                    if (playerFieldPlayer1[posX+i][posY].getStatus() == 'p'){
                        return false;
                    }
                }else {
                    if (playerFieldPlayer1[posX][posY+i].getStatus() == 'p') {
                        return false;
                    }
                }
            }
        }else {
            for (int i = 0; i < shiptypes[arrayPosition].getValue(); i++) {
                if (horizontalDirection) {
                    if (gui.playerFieldPlayer2[posX + i][posY].getStatus() == 'p') {
                        return false;
                    }
                } else {
                    if (gui.playerFieldPlayer2[posX][posY + i].getStatus() == 'p') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Wird aufgerufen, wenn ein Spieler Schiff Platzieren drückt.
     * Das Spielfeld wird gewechselt und das nächste Schiff aus dem Array wird ausgewählt.
     * Wenn alle Schiffe aus dem Array platziert wurden, werden alle Schiffe des anderen Spielers platziert.
     */
    public void placeBoat() {
        posX=0;
        posY=0;
        move(0,0);
        if(arrayPosition==8) {
            player1 = !player1;
            if(player1){
                gui.showPlayerField(false);
            }else {
                gui.showPlayerField(true);
                arrayPosition=0;
            }
        }else {
            arrayPosition++;
        }
    }

    /**
     * Dreht ein Boot beim Drücken des Drehen-Knopfes. Wechselt dann den Zustand von zb Vertical zu Horizontal
     */
    public void switchDirection(){
        deleteBoat(posX,posY);
        horizontalDirection = !horizontalDirection;
        if(!inField()) horizontalDirection = !horizontalDirection;
        placeBoat(posX,posY);
    }
}