package Game.Utils;

public class Position {

    public int x = 0;
    public int y = 0;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return x;
    }

    public String ToString() {
        return "x:" + x + ", y:" + y;
    }

    public Position Copy(){
        return new Position(this.x, this.y);
    }

    public int CompareTo(Position position) {
        if (GetX() > position.GetX())
            return 1;
        if (GetX() < position.GetX())
            return -1;
        if (GetY() > position.GetY())
            return 1;
        if (GetY() < position.GetY())
            return -1;
        return 0;
    }

    // return the range between two objects
    public boolean IsInRange(Position c2, int range) {
        return range >= this.Distance(c2);
    }

    public double Distance(Position c2) {
        return Math.sqrt(Math.pow((this.x - c2.x), 2) + Math.pow((this.y - c2.y), 2));
    }
}
