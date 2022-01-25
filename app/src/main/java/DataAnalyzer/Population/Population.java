package DataAnalyzer.Population;

public record Population(
        String id,
        Localization localization,
        int stateFips,
        int population
) {
    @Override
    public String toString() {
        return localization +
                ", " + population;
    }

    public record Localization(
            String city,
            String placeFips,
            String geoId,
            String state
    ) {
        @Override
        public String toString() {
            return city +
                    ", " + placeFips +
                    ", " + state;
        }
    }
}
