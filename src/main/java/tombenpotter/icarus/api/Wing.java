package tombenpotter.icarus.api;

public abstract class Wing {

    public String name;
    public int durability;
    public int maxHeight;
    public double jumpBoost;
    public double glideFactor;
    public double rainDrag;
    public double waterDrag;
    public double fallReductionFactor;

    public Wing(String name, int durability, int maxHeight, double jumpBoost, double glideFactor, double rainDrag, double waterDrag, double fallReductionFactor) {
        this.name = name;
        this.durability = durability;
        this.maxHeight = maxHeight;
        this.jumpBoost = jumpBoost;
        this.glideFactor = glideFactor;
        this.rainDrag = rainDrag;
        this.waterDrag = waterDrag;
        this.fallReductionFactor = fallReductionFactor;
    }
}
