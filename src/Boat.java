public class Boat {
    public int lenght;
    public BoatType boatType;
    public Boat(BoatType boatType){
        this.boatType = boatType;
        this.lenght = boatType.getValue();
    }
    public BoatType getBoatType(){
        return boatType;
    }

    /**
     * Kontrolliert, ob das Schiff zerstört ist, nachdem die Länge um eins abgezogen wurde.
     * @return Abhängig davon, ob die Länge gleich, oder größer 0
     */
    public boolean isDestroyed(){
        lenght--;
        if(lenght<=0){
            return true;
        } else {
            return false;
        }
    }
}
