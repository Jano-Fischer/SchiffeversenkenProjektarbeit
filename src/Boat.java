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
    public boolean isDestroyed(){
        lenght--;
        if(lenght<=0){
            return true;
        } else {
            return false;
        }
    }
}
