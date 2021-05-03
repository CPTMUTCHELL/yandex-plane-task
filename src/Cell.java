class Cell  {
    private String letter;
    private boolean taken;
    private boolean reserved;
    private boolean aisle;
    private final int[] coords;

    public Cell(int x, int y) {
        this.coords = new int[]{x, y};
    }

    public String getStatus() {
        if (taken) return "#";
        if (aisle) return "_";
        if (reserved) return "X";
        return ".";

    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int[] getCoords() {
        return coords;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getLetter() {
        switch (coords[1]){
            case 0:
                letter= "A";
                break;
            case 1:
                letter= "B";
                break;
            case 2:
                letter= "C";
                break;
            case 4:
                letter= "D";
                break;
            case 5:
                letter= "E";
                break;
            case 6:
                letter= "F";
                break;
        }
        return letter;
    }

    public void setAisle(boolean aisle) {
        this.aisle = aisle;
    }

}
