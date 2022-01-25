package DataAnalyzer.Population;

import java.util.List;

public interface PopulationRepository {
    List<Population> findAll();

    List<Population> findByState(String state);

    int sumPopulationByState(String state);

    List<String> getStates();
}
