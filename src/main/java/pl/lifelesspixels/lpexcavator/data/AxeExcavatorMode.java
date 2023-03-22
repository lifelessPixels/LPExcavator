package pl.lifelesspixels.lpexcavator.data;

public enum AxeExcavatorMode {

    Disabled,
    OnlySameDirection,
    AllSame;

    public AxeExcavatorMode cycle() {
        return switch (this) {
            case Disabled -> OnlySameDirection;
            case OnlySameDirection -> AllSame;
            case AllSame -> Disabled;
        };
    }

    public String readableDescription() {
        return switch (this) {
            case Disabled -> "Disabled";
            case OnlySameDirection -> "Affects all blocks with the same direction";
            case AllSame -> "Affects all blocks of the same type";
        };
    }

}
