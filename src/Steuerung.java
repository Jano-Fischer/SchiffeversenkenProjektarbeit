public class Steuerung {
    private final Gui gui;                          //Bekannt machen mit der GUI
    private BoatThings theBoatThing;
    private boolean lock = false;                  //Boolean damit nicht mehrmals geschossen werden kan n
    private boolean player1 = true;                //Auswahl des aktuellen Spielers
    private boolean horizontalDirection =true;      //Speichert die Ausrichtung des Schiffes
    private int fieldHight = 50;                   //feldhöhe einstellen
    private int fieldWidth = 50;                    //feldbreite einstellen
    private int player1DestroyedBoats = 0;          //Zerstörte Schiffe Player 1
    private int player2DestroyedBoats = 0;          //Zerstörte Schiffe Player 2
    private final BoatType[] shipsToPlace = {BoatType.ONEBOAT,BoatType.THREEBOAT};
    private final int boatNummber = shipsToPlace.length-1;    // Anzahl der zu platzierenden Boote, muss mit anzahl der Boote in shipsToPlace uebereinstimmen //TODO anzahl bei ändern der schiffanzahl anpassen
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
        Boat boat = new Boat(shipsToPlace[arrayPosition]);
        System.out.println("Boot auswahl anerkannt");       //TODO weg
        if(player1) {
            if (horizontalDirection){
                for (int i=x;i<x+boat.getBoatType().getValue();i++){
                    playerFieldPlayer1[i][y].setBoat(boat);
                    playerFieldPlayer1[i][y].repaint();
                }
            } else {
                for (int i=y;i<y+boat.getBoatType().getValue();i++){
                    playerFieldPlayer1[x][i].setBoat(boat);
                    playerFieldPlayer1[x][i].repaint();
                }
            }
        }else {
            if (horizontalDirection){
                for (int i=x;i<x+boat.getBoatType().getValue();i++){
                    playerFieldPlayer2[i][y].setBoat(boat);
                    playerFieldPlayer2[i][y].repaint();
                }
            } else {
                for (int i=y;i<y+boat.getBoatType().getValue();i++){
                    playerFieldPlayer2[x][i].setBoat(boat);
                    playerFieldPlayer2[x][i].repaint();
                }
            }

        }
    }

    /**
     * Löscht das Boot, wenn es verschoben wird vom alten Feld
     * @param x Koordinate
     * @param y Koordinate
     */
    public void deleteBoat(int x,int y){
        if(player1){
            if (horizontalDirection){
                for (int i = x; i<x+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer1[i][y].removeBoat();
                    playerFieldPlayer1[i][y].repaint();
                }
            } else {
                for (int i = y; i<y+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer1[x][i].removeBoat();
                    playerFieldPlayer1[x][i].repaint();
                }
            }
        }else {
            if (horizontalDirection){
                for (int i = x; i<x+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer2[i][y].removeBoat();
                    playerFieldPlayer2[i][y].repaint();
                }
            } else {
                for (int i = y; i<y+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer2[x][i].removeBoat();
                    playerFieldPlayer2[x][i].repaint();
                }
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
        System.out.println("Wir bewegen uns um("+x +":" +y+")");
        System.out.println(posX +" : " +posY +"Bot typ " + shipsToPlace[arrayPosition].getValue());
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
            return (posX + shipsToPlace[arrayPosition].getValue() <= 10 && posY<10);
        }else {
            return (posY + shipsToPlace[arrayPosition].getValue() <= 10 && posX<10);
        }
    }

    /**
     * Kontrolliert, ob auf der aktuellen Position schon ein Schiff steht
     * @return False, wenn die aktuelle position schon von einem Schiff belegt ist
     */
    public boolean isValid(){
        if(player1) {
            for (int i = 0; i< shipsToPlace[arrayPosition].getValue(); i++){
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
            for (int i = 0; i < shipsToPlace[arrayPosition].getValue(); i++) {
                if (horizontalDirection) {
                    if (playerFieldPlayer2[posX + i][posY].getStatus() == 'p') {
                        return false;
                    }
                } else {
                    if (playerFieldPlayer2[posX][posY + i].getStatus() == 'p') {
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
        posX=5;     //Koordianten wo das Schiff spawned
        posY=1;     //Koordianten wo das Schiff spawned
        horizontalDirection=true;
        move(0,0);
        if(arrayPosition==boatNummber) {
            player1 = !player1;
            System.out.println("Spieler 1 fertig");
            if (player1) {
                gui.showPlayerField(false);
            } else {
                arrayPosition = 0;
                gui.showPlayerField(true);

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