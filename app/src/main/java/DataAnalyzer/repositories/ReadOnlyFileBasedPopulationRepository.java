package DataAnalyzer.repositories;

import DataAnalyzer.Population.Population;
import DataAnalyzer.Population.PopulationRepository;
import DataAnalyzer.gui.MainViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class ReadOnlyFileBasedPopulationRepository implements PopulationRepository {
    private final List<Population> populations;

    public ReadOnlyFileBasedPopulationRepository() {
        this.populations = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/population.csv")))
                .lines()
                .skip(1)
                .map(this::toPopulation)
                .toList();
    }

    private Population toPopulation(String s) {
        String[] separatedLine = s.split(",");

        return new Population(
                separatedLine[0],
                new Population.Localization(separatedLine[1],
                        separatedLine[2],
                        separatedLine[3],
                        separatedLine[4]),
                Integer.parseInt(separatedLine[5]),
                Integer.parseInt(separatedLine[6])
        );
    }

    @Override
    public List<Population> findAll() {
        return populations.stream().sorted(Comparator.comparingInt(Population::population)).toList();
    }


    @Override
    public List<Population> findByState(String state) {
        return populations.stream()
                .filter(e -> e.localization().state().equals(state))
                .toList();
    }

    @Override
    public List<String> getStates() {
        return populations.stream()
                .filter(MainViewModel.distinctByKey(e -> e.localization().state()))
                .map(population -> population.localization().state())
                .toList();
    }

    @Override
    public int sumPopulationByState(String state) {
        return populations.stream()
                .filter(e -> e.localization().state().equals(state))
                .mapToInt(Population::population)
                .sum();
    }

}
