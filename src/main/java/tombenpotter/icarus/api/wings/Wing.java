package tombenpotter.icarus.api.wings;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wing wing = (Wing) o;

        if (durability != wing.durability) return false;
        if (Double.compare(wing.fallReductionFactor, fallReductionFactor) != 0) return false;
        if (Double.compare(wing.glideFactor, glideFactor) != 0) return false;
        if (Double.compare(wing.jumpBoost, jumpBoost) != 0) return false;
        if (maxHeight != wing.maxHeight) return false;
        if (Double.compare(wing.rainDrag, rainDrag) != 0) return false;
        if (Double.compare(wing.waterDrag, waterDrag) != 0) return false;
        if (name != null ? !name.equals(wing.name) : wing.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + durability;
        result = 31 * result + maxHeight;
        temp = Double.doubleToLongBits(jumpBoost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(glideFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rainDrag);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(waterDrag);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fallReductionFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Wing{" +
                "name='" + name + '\'' +
                ", durability=" + durability +
                ", maxHeight=" + maxHeight +
                ", jumpBoost=" + jumpBoost +
                ", glideFactor=" + glideFactor +
                ", rainDrag=" + rainDrag +
                ", waterDrag=" + waterDrag +
                ", fallReductionFactor=" + fallReductionFactor +
                '}';
    }
}
