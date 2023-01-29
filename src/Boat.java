public abstract class Boat {

    protected int lenght;
    public boolean isDestroyed(){
        lenght--;
        if(lenght<=0){
            return true;
        } else {
            return false;
        }
    }
}
