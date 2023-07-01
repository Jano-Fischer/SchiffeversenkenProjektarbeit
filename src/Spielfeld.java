public abstract class Spielfeld {

    private Feld[][] playerFieldPlayer = new Feld[10][10];  //Feld für Spieler 1 der Größe 10x10
    private GuiSpielfeld guiSpielfeld;
    private Gui gui;    //Wird nur einmal genutzt. Geht das auch anderst?
    private Steuerung strg;

    public void setGuiSpielfeld(GuiSpielfeld guiSpielfeld) {
        this.guiSpielfeld = guiSpielfeld;
    }


    /**
     * Platziert ein Boot, je nach ausrichtung.
     *
     * @param x Koordinate des Boots
     * @param y Koordinate des Boots
     */
    private void placeBoat(int x, int y, BoatType boatType, boolean horizontalDirection, boolean valid) {
        Boat boat = new Boat(boatType);
        if (horizontalDirection) {
            for (int i = x; i < x + boat.getBoatType().getValue(); i++) {
                playerFieldPlayer[i][y].setBoat(boat, valid);
            }
        } else {
            for (int i = y; i < y + boat.getBoatType().getValue(); i++) {
                playerFieldPlayer[x][i].setBoat(boat, valid);
            }
        }
        if (guiSpielfeld != null) {
            guiSpielfeld.repaint();
        }
    }

    /**
     * Löscht das Boot, wenn es verschoben wird vom alten Feld
     *
     * @param x Koordinate
     * @param y Koordinate
     */
    public void deleteBoat(int x, int y, BoatType boatType, boolean horizontalDirection) {

        if (horizontalDirection) {
            for (int i = x; i < x + boatType.getValue(); i++) {

                playerFieldPlayer[i][y].removeBoat();
                // TODO: was machte dieser Code? => check!!!!
                    /*
                    if ((shipsToPlace.length-1) == arrayPosition)
                        break;
                     */
            }
        } else {
            for (int i = y; i < y + boatType.getValue(); i++) {
                playerFieldPlayer[x][i].removeBoat();
                    /*
                    if ((shipsToPlace.length-1) == arrayPosition)
                        break;
                     */
            }
        }
        guiSpielfeld.repaint();
    }

    /**
     * Wird aufgerufen, wenn ein Knopf zum Verschieben gedrückt wurde. Ruft zuerst deleteBoat() auf, und verschiebt das Boot je nach eingabe des Spielers.
     *
     * @param x Gibt an um wie viel das Boot in X-Richtung verschoben wird.
     * @param y Gibt an um wie viel das Boot in Y-Richtung verschoben wird.
     */
    public void move(int x, int y, BoatType boatType, boolean horizontalDirection) {
        guiSpielfeld.setFocusable(true);
        deleteBoat(x, y, boatType, horizontalDirection);
        x = x + x;
        y = y + y;
        if (x < 0 || y < 0 || !inField(x, y, boatType, horizontalDirection)) {                 //if(posX<0||posY<0||!inField()||!isValid()){
            x = x - x;
            y = y - y;
        }
        if (isValid(x, y, boatType, horizontalDirection, strg.getLock())) {
            placeBoat(x, y, boatType, horizontalDirection, true);
            positionIsValid();
        } else {
            positionIsInvalid();
            placeBoat(x, y, boatType, horizontalDirection, false);

        }

    }

    protected abstract void positionIsValid();

    protected abstract void positionIsInvalid();


    /**
     * Kontrolliert, ob auf der aktuellen Position schon ein Schiff steht
     *
     * @return False, wenn die aktuelle position schon von einem Schiff belegt ist
     */
    public boolean isValid(int x, int y, BoatType boatType, boolean horizontalDirection, boolean lock) {
        lock = false;
        for (int j = -1; j < 2; j++) {                                               //Zähler y richtung
            for (int i = -1; i < boatType.getValue() + 1; i++) {     //zähler x richtung,  for (int i = -1; i < shipsToPlace[arrayPosition].getValue() + 1; i++)
                if (horizontalDirection) {
                    if (!(x + i < 0 || y + j < 0 || x + i > playerFieldPlayer.length - 1 || y + j > playerFieldPlayer.length - 1)) {
                        if (playerFieldPlayer[x + i][y + j].getStatus() == 'p') {      //if (playerFieldPlayer1[posX + i][posY+j].getStatus() == 'p') {
                            lock = true;                                                      //sperrt den Bestätigen-Knopf
                            return false;
                        }
                    }
                } else {
                    if (!(x + j < 0 || y + i < 0 || x + j > playerFieldPlayer.length - 1 || y + i > playerFieldPlayer.length - 1)) {
                        if (playerFieldPlayer[x + j][y + i].getStatus() == 'p') {      //if (playerFieldPlayer1[posX+j][posY + i].getStatus() == 'p') {
                            lock = true;
                            return false;
                        }
                    }
                }
            }
        }
        if (!lock) {
            gui.enable_shipPlace();
        }
        return true;
    }

    /**
     * Kontrolliert, ob sich das Boot im Spielfeld befindet
     *
     * @return False, wenn das Boot nicht mehr im Spielfeld ist
     */
    private boolean inField(int x, int y, BoatType boatType, boolean horizontalDirection) {
        if (horizontalDirection) {
            return (x + boatType.getValue() <= 10 && y < 10);
        } else {
            return (y + boatType.getValue() <= 10 && x < 10);
        }
    }

    /**
     * Greift das Feld bei X und Y an. Wenn kein Boot getroffen wurde, ist attack false
     *
     * @param x Beschossene Stelle
     * @param y Beschossene Stelle
     * @return
     */
    public boolean boatExists(int x, int y) {
        Boat boat;
        boat = playerFieldPlayer[x][y].getBoat();

        if (boat == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Dreht ein Boot beim Drücken des Drehen-Knopfes. Wechselt dann den Zustand von z.b. Vertical zu Horizontal
     */
    public void switchDirection(int x, int y, BoatType boatType, Boolean lock, Boolean horizontalDirection) {
        deleteBoat(x, y, boatType, lock);
        //horizontalDirection = !horizontalDirection;
        if (!inField(x, y, boatType, horizontalDirection)) {
            horizontalDirection = !horizontalDirection;
        }
    }
}
