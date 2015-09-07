package tombenpotter.icarus.common.util;

import tombenpotter.icarus.api.Wing;
import tombenpotter.icarus.common.IcarusItems;

public class IcarusWing extends Wing {

    public IcarusWing(String name, int durability, int maxHeight, double jumpBoost, double glideFactor, double rainDrag, double waterDrag, double fallReductionFactor) {
        super(name, durability, maxHeight, jumpBoost, glideFactor, rainDrag, waterDrag, fallReductionFactor);

        IcarusItems.wingNames.add(name);
    }
}
