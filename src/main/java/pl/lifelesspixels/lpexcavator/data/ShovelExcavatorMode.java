package pl.lifelesspixels.lpexcavator.data;

public enum ShovelExcavatorMode {

    Disabled,
    Enabled;

    public ShovelExcavatorMode cycle() {
        return switch (this) {
            case Disabled -> Enabled;
            case Enabled -> Disabled;
        };
    }

    public String readableDescription() {
        return switch (this) {
            case Disabled -> "Disabled";
            case Enabled -> "Enabled";
        };
    }

}
