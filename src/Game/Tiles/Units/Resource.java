package Game.Tiles.Units;

public class Resource {
    private String Name;
    private int Pool;
    private int Amount;

    public Resource(String name, int pool, int amount) {
        this.Name = name;
        this.Pool = pool;
        this.Amount = amount;
    }

    public int GetAmount() {
        return Amount;
    }

    public void SetAmount(int amount) {
        Amount = amount;
    }

    public void SetPool(int pool) {
        Pool = pool;
    }

    public int GetPool() {
        return Pool;
    }

    public void ReduceAmount(int damageDone) {
    }

    public void AddCapacity(int healthGained) {
    }

    public void Restore() {
    }

    public boolean IsDead() {
        return GetAmount() <= 0;
    }
}