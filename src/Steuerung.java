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
    private final BoatType[] shipsToPlace = {BoatType.FIVEBOAT,BoatType.FOURBOAT,BoatType.THREEBOAT};
    private final int boatNumber = shipsToPlace.length;          //shipsToPlace.length-1;    // Anzahl der zu platzierenden Boote, muss mit anzahl der Boote in shipsToPlace uebereinstimmen //TODO anzahl bei ändern der schiffanzahl anpassen
    private int arrayPosition=0;
    private int posX =0;
    private int posY =0;
    private boolean pregame =true;

    public boolean isPregame() {
        return pregame;
    }
    private int counter = 0;
    private Feld[][] playerFieldPlayer1 = new Feld[10][10];  //Feld für Spieler 1 der Größe 10x10 TODO Private?
    private Feld[][] playerFieldPlayer2 = new Feld[10][10]; //Feld für Spieler 2 der Größe 10x10  TODO Private?
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
    public Feld[][] getPlayerFieldPlayer1() {
        return this.playerFieldPlayer1;
    }
    public Feld[][] getPlayerFieldPlayer2() {
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

    public Steuerung(Gui gui) {
        this.gui = gui;
    
        playerFieldPlayer1 = createFelder();
        playerFieldPlayer2 = createFelder();
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
            boat = playerFieldPlayer2[x][y].getBoat();
        }else{
            boat = playerFieldPlayer1[x][y].getBoat();
        }
        if (boat==null){
            return false;
        } else {
            return true;
        }
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
            if (player2DestroyedBoats == boatNumber) {
                System.out.println("Spieler 1 Gewonnen");
                gui.setActivePlayerText("Spieler 1 hat gewonnen");
                lock = true;
            }
        } else {
            player1DestroyedBoats++;
            if (player1DestroyedBoats == boatNumber) {
                System.out.println("Spieler 2 Gewonnen");
                gui.setActivePlayerText("Spieler 2 hat gewonnen");
                lock = true;

            }
        }
    }
    /**
     * Platziert ein Boot, je nach ausrichtung.
     * @param x Koordinate des Boots
     * @param y Koordinate des Boots
     */
    private void placeBoat(int x,int y){
        Boat boat = new Boat(shipsToPlace[arrayPosition]);
        if(player1) {
            if (horizontalDirection){
                for (int i=x;i<x+boat.getBoatType().getValue();i++){
                    playerFieldPlayer1[i][y].setBoat(boat);
                    // gui.repaint(i,y);
                }
            } else {
                for (int i=y;i<y+boat.getBoatType().getValue();i++){
                    playerFieldPlayer1[x][i].setBoat(boat);
                }
            }
        }else {
            if (horizontalDirection){
                for (int i=x;i<x+boat.getBoatType().getValue();i++){
                    playerFieldPlayer2[i][y].setBoat(boat);
                }
            } else {
                for (int i=y;i<y+boat.getBoatType().getValue();i++){
                    playerFieldPlayer2[x][i].setBoat(boat);
                }
            }

        }
        gui.repaint();
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
                }
            } else {
                for (int i = y; i<y+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer1[x][i].removeBoat();
                }
            }
        }else {
            if (horizontalDirection){
                for (int i = x; i<x+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer2[i][y].removeBoat();
                }
            } else {
                for (int i = y; i<y+ shipsToPlace[arrayPosition].getValue(); i++){
                    playerFieldPlayer2[x][i].removeBoat();
                }
            }
        }
        gui.repaint();
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
        if(posX<0||posY<0||!inField()||!isValid()){
            posX = posX -x;
            posY = posY -y;
        }
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
        int pY = posY;
        if(player1) {                                                                       //player 1
           // for(int j = -1; j< pY+2; j++) {                                               //zaehler y richtung
                for (int i = 0; i < shipsToPlace[arrayPosition].getValue(); i++) {     //zaehler x richtung,  for (int i = -1; i < shipsToPlace[arrayPosition].getValue() + 1; i++)
                    if (horizontalDirection) {
                        if (playerFieldPlayer1[posX + i][posY].getStatus() == 'p') {      //if (playerFieldPlayer1[posX + i][posY+j].getStatus() == 'p') {
                            return false;
                        }
                    } else {
                        if (playerFieldPlayer1[posX][posY + i].getStatus() == 'p') {      //if (playerFieldPlayer1[posX+j][posY + i].getStatus() == 'p') {
                            return false;
                        }
                    }
                }
           // }
        }else {
            // for(int j = -1; j< pY+2; j++) {
                for (int i = 0; i < shipsToPlace[arrayPosition].getValue(); i++) {         //same for player 2
                    if (horizontalDirection) {
                        if (playerFieldPlayer2[posX + i][posY].getStatus() == 'p') {      //if (playerFieldPlayer2[posX + i][posY+j].getStatus() == 'p') {
                            return false;
                        }
                    } else {
                        if (playerFieldPlayer2[posX][posY + i].getStatus() == 'p') {      //if (playerFieldPlayer2[posX+j][posY + i].getStatus() == 'p') {
                            return false;
                        }
                    }
                }
            // }
        }
        return true;
    }



    /**
     * Wird aufgerufen, wenn ein Spieler Schiff Platzieren drückt.
     * Das Spielfeld wird gewechselt und das nächste Schiff aus dem Array wird ausgewählt.
     * Wenn alle Schiffe aus dem Array platziert wurden, werden alle Schiffe des anderen Spielers platziert.
     */
    public void placeBoat() {
        posX = 0;     //Koordianten wo das Schiff spawned
        posY = 0;     //Koordianten wo das Schiff spawned
        horizontalDirection = true;

        if (arrayPosition == boatNumber-1) {
            player1 = !player1;
            gui.setActivePlayerText("Spieler " + ((isPlayer1()) ? 1 : 2));
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


    /**
     * Dreht ein Boot beim Drücken des Drehen-Knopfes. Wechselt dann den Zustand von zb Vertical zu Horizontal
     */
    public void switchDirection(){
        deleteBoat(posX,posY);
        horizontalDirection = !horizontalDirection;
        if(!inField() || !isValid()) horizontalDirection = !horizontalDirection;
        placeBoat(posX,posY);
    }
}
