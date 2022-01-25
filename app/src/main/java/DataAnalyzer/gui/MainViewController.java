package DataAnalyzer.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    MainViewModel model;

    @FXML
    Button submitButton;
    @FXML
    TextField stateField;
    @FXML
    Label hintLabel;
    @FXML
    Label sumPopulationLabel;
    @FXML
    ListView<String> statesView;
    @FXML
    TableView<ObservablePopulationProperties> resultView;
    @FXML
    TableColumn<ObservablePopulationProperties, String> cityName;
    @FXML
    TableColumn<ObservablePopulationProperties, String> cityFips;
    @FXML
    TableColumn<ObservablePopulationProperties, String> stateShort;
    @FXML
    TableColumn<ObservablePopulationProperties, Integer> populationCount;

    ObservableList<ObservablePopulationProperties> populationsDTO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainViewModel();

        populationsDTO = MainViewModel.populationRecordToDtoConverter();

        cityName.setCellValueFactory(param -> param.getValue().city());
        cityFips.setCellValueFactory(param -> param.getValue().placeFips());
        stateShort.setCellValueFactory(param -> param.getValue().state());
        populationCount.setCellValueFactory(param -> param.getValue().population());

        hintLabel.setText("Suma znalezionych wpisów: "
                + populationsDTO.size());
        sumPopulationLabel
                .setText("Suma populacji w wyświetlonych rekordach: "
                        + MainViewModel.sumPopulationInAllStates());

        statesView.setItems(model.getStatesToObservableList());

        populationsDTO.forEach(e -> resultView.getItems().add(e));
    }


    @FXML
    public void handleStateClick(ActionEvent e) {
        String selectedState = stateField.getText();

        try {
            if (!selectedState.isEmpty()) {
                ObservableList<ObservablePopulationProperties> tempObservableList =
                        model.getPopulationsByState(selectedState);
                resultView.setItems(tempObservableList);

                hintLabel.setText("Suma znalezionych wpisów: "
                        + tempObservableList.size());
                sumPopulationLabel
                        .setText("Suma populacji w wyświetlonych rekordach: "
                                + MainViewModel.sumPopulationInSingleState(selectedState));
            } else
                throw new Exception();
        } catch (Exception ex) {
            resultView.setItems(populationsDTO);

            hintLabel.setText("Suma znalezionych wpisów: "
                    + populationsDTO.size());
            sumPopulationLabel
                    .setText("Suma populacji w wyświetlonych rekordach: "
                            + MainViewModel.sumPopulationInAllStates());
        }
    }

    public void handleStatesListClicked(MouseEvent mouseEvent) {
        stateField.setText(statesView.getSelectionModel().getSelectedItem());
    }
}
