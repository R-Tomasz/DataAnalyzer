package helpers;

import DataAnalyzer.Population.Population;
import DataAnalyzer.Population.PopulationRepository;
import DataAnalyzer.gui.MainViewModel;

import java.util.List;

public class FakePopulationRepository implements PopulationRepository {
    private final List<Population> populations;

    public FakePopulationRepository(List<Population> populations) {
        this.populations = populations;
    }

    @Override
    public List<Population> findAll() {
        return populations;
    }

    @Override
    public List<Population> findByState(String state) {
        return populations.stream()
                .filter(e -> e.localization().state().equals(state))
                .toList();
    }

    @Override
    public int sumPopulationByState(String state) {
        return populations.stream()
                .filter(e -> e.localization().state().equals(state))
                .mapToInt(Population::population)
                .sum();
    }

    @Override
    public List<String> getStates() {
        return populations.stream()
                .filter(MainViewModel.distinctByKey(e -> e.localization().state()))
                .map(population -> population.localization().state())
                .toList();
    }
}
