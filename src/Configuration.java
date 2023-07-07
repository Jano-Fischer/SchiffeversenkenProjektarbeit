public class Configuration {
    private int size;
    private int totalNumber = 10;
    private int fiveBoats = 1; //Zahlen beziehen sich auf die Defaulteinstellung des Menüs
    private int fourBoats = 2;
    private int threeBoats = 3;
    private int twoBoats = 4;
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getFiveBoats() {
        return fiveBoats;
    }

    public void setFiveBoats(int fiveBoats) {
        this.fiveBoats = fiveBoats;
    }

    public int getFourBoats() {
        return fourBoats;
    }

    public void setFourBoats(int fourBoats) {
        this.fourBoats = fourBoats;
    }

    public int getThreeBoats() {
        return threeBoats;
    }

    public void setThreeBoats(int threeBoats) {
        this.threeBoats = threeBoats;
    }

    public int getTwoBoats() {
        return twoBoats;
    }

    public void setTwoBoats(int twoBoats) {
        this.twoBoats = twoBoats;
    }


    public void preset(){
        switch (totalNumber){

            case 5:
                fiveBoats=0;fourBoats=0;threeBoats=2;twoBoats=3;
                break;
            case 6:
                fiveBoats=0;fourBoats=1;threeBoats=2;twoBoats=3;
                break;
            case 7:
                fiveBoats=1;fourBoats=1;threeBoats=2;twoBoats=3;
                break;
            case 8:
                fiveBoats=1;fourBoats=1;threeBoats=2;twoBoats=4;
                break;
            case 9:
                fiveBoats=1;fourBoats=1;threeBoats=3;twoBoats=4;
                break;
            case 10:
                fiveBoats=1;fourBoats=2;threeBoats=3;twoBoats=4;
                break;
        }

    }
}