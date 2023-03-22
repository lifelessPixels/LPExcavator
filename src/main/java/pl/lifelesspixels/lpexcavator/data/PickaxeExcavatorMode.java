package pl.lifelesspixels.lpexcavator.data;

public enum PickaxeExcavatorMode {

    Disabled,
    OnlyOres,
    OresAndAlternateStones;

    public PickaxeExcavatorMode cycle() {
        return switch (this) {
            case Disabled -> OnlyOres;
            case OnlyOres -> OresAndAlternateStones;
            case OresAndAlternateStones -> Disabled;
        };
    }

    public String readableDescription() {
        return switch (this) {
            case Disabled -> "Disabled";
            case OnlyOres -> "Affects only ores";
            case OresAndAlternateStones -> "Affects ores and alternate stone types (diorite, andesite, etc.)";
        };
    }

}
