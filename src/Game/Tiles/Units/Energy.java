package Game.Tiles.Units;

public class Energy extends Resource{
    private int cost;

    public Energy(String name, int pool, int amount) {
        super(name, pool, amount);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void ToString(){}
}
