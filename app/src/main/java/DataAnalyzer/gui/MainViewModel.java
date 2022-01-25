package DataAnalyzer.gui;

import DataAnalyzer.repositories.ReadOnlyFileBasedPopulationRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainViewModel {
    private static final ReadOnlyFileBasedPopulationRepository repo =
            new ReadOnlyFileBasedPopulationRepository();

    public MainViewModel() {
    }


    public ObservableList<String> getStatesToObservableList() {
        return FXCollections.observableArrayList(repo.getStates());
    }

    public static int sumPopulationInAllStates() {
        return populationRecordToDtoConverter()
                .stream()
                .mapToInt(o -> o.population().getValue())
                .sum();
    }

    public ObservableList<ObservablePopulationProperties> getPopulationsByState(String state) {
        return populationRecordToDtoConverter()
                .stream()
                .filter(e -> e.state().getValue().equals(state))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FXCollections::observableList
                ));
    }

    public static int sumPopulationInSingleState(String state) {
        return populationRecordToDtoConverter()
                .stream()
                .filter(e -> e.state().getValue().equals(state))
                .mapToInt(o -> o.population().getValue())
                .sum();
    }


    public static ObservableList<ObservablePopulationProperties> populationRecordToDtoConverter() {
        return repo.findAll().stream().map(it -> new ObservablePopulationProperties(
                new SimpleStringProperty(it.id()),
                new SimpleStringProperty(it.localization().city()),
                new SimpleStringProperty(it.localization().placeFips()),
                new SimpleStringProperty(it.localization().geoId()),
                new SimpleStringProperty(it.localization().state()),
                new SimpleObjectProperty<>(it.population())
        ))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FXCollections::observableList
                ));
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

record ObservablePopulationProperties(
        SimpleStringProperty id,
        SimpleStringProperty city,
        SimpleStringProperty placeFips,
        SimpleStringProperty geoId,
        SimpleStringProperty state,
        ObservableValue<Integer> population
) {
}
