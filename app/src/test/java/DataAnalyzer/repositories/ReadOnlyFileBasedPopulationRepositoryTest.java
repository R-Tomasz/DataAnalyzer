package DataAnalyzer.repositories;

import org.assertj.core.api.Assertions;
import DataAnalyzer.Population.Population;
import helpers.FakePopulationRepository;
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

        //when
        Assertions.assertThat(su.findAll().size()).isEqualTo(3);
    }
}