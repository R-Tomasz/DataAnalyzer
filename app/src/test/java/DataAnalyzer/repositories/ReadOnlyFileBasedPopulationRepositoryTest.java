package DataAnalyzer.repositories;

import DataAnalyzer.Population.Population;
import helpers.FakePopulationRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class ReadOnlyFileBasedPopulationRepositoryTest {

    @Test
    public void should_select_all_objects_from_repository() {
        //given
        FakePopulationRepository su = new FakePopulationRepository(List.of(
                new Population(
                        "1",
                        new Population.Localization(
                                "city", "123", "1231251", "AK"),
                        321, 123412
                ),
                new Population(
                        "2",
                        new Population.Localization(
                                "city2", "123", "1231251", "AK"),
                        321, 123412),
                new Population(
                        "3",
                        new Population.Localization(
                                "city2", "124", "3146143", "AK"),
                        321, 123412)
        ));

        //expected
        Assertions.assertThat(su.findAll().size()).isEqualTo(3);
    }

    @Test
    public void should_sum_population_from_selected_states() {
        //given
        FakePopulationRepository su = new FakePopulationRepository(List.of(
                new Population(
                        "1",
                        new Population.Localization(
                                "city", "123", "1231251", "AK"),
                        321, 123
                ),
                new Population(
                        "2",
                        new Population.Localization(
                                "city2", "123", "1231251", "LR"),
                        321, 1132),
                new Population(
                        "3",
                        new Population.Localization(
                                "city2", "124", "3146143", "AK"),
                        321, 1216)
        ));

        //expected
        Assertions.assertThat(su.sumPopulationByState("AK")).isEqualTo(1339);
    }

    @Test
    public void should_display_towns_based_on_selected_state() {
        //given
        String selectedState = "LR";
        FakePopulationRepository su = new FakePopulationRepository(List.of(
                new Population(
                        "1",
                        new Population.Localization(
                                "city", "123", "1231251", "AK"),
                        321, 123
                ),
                new Population(
                        "2",
                        new Population.Localization(
                                "city2", "123", "1231251", "LR"),
                        321, 1132),
                new Population(
                        "3",
                        new Population.Localization(
                                "city2", "124", "3146143", "AK"),
                        321, 1216)
        ));

        //expected
        Assertions.assertThat(su.findByState(selectedState)).contains(new Population("2", new Population.Localization("city2", "123", "1231251", "LR"), 321, 1132));
    }

    @Test
    public void should_return_list_of_unique_state_codes() {
        //given
        FakePopulationRepository su = new FakePopulationRepository(List.of(
                new Population(
                        "1",
                        new Population.Localization(
                                "city", "123", "1231251", "AK"),
                        321, 123
                ),
                new Population(
                        "2",
                        new Population.Localization(
                                "city2", "123", "1231251", "LR"),
                        321, 1132),
                new Population(
                        "3",
                        new Population.Localization(
                                "city2", "124", "3146143", "AK"),
                        321, 1216)
        ));

        //expected
        Assertions.assertThat(su.getStates()).contains("AK", "LR");
    }
}