package Game.Tiles.Units;

public class Health  extends Resource{

    public Health(int healthAmount, int healthPool) {
        super("Health", healthAmount, healthPool);
    }

    public int GetHealthAmount() {
        return super.GetAmount();
    }

    public int GetHealthPool() {
        return super.GetPool();
    }

    public String ToString() {
        return "health amount:" + GetHealthAmount() + ", health pool:" + GetHealthPool();
    }
}
