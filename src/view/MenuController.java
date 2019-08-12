package view;

import java.io.File;
import java.io.IOException;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.FileType;
import model.Person;
import util.DateUtil;

public class MenuController {
    @FXML    //person
    private TableView<FileType> fileTable;
    @FXML	//person, string
    private TableColumn<FileType, String> TypeColumn;
    @FXML
    private TableColumn<FileType, String> ExtColumn;

    @FXML
    private Label NameLabel;
    @FXML
    private Label ExtPrefLabel;
    @FXML
    private Label ExtSupportedLabel;
    @FXML
    private Label selectedFileLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML 
    private ComboBox<String> analysisCombo;
    
    // Reference to the main application.
    private MainApp mainApp;
    
    private Stage dialogStage;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MenuController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        TypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        ExtColumn.setCellValueFactory(
                cellData -> cellData.getValue().extPrefProperty());

        //Initialize the Analysys combobox ;  analysisCombo.getSelectionModel().getSelectedItem() return the item selected
        analysisCombo.getItems().removeAll(analysisCombo.getItems());
        analysisCombo.getItems().addAll("Analysis 1", "Analysis 2", "Analysis 3");
        analysisCombo.getSelectionModel().select("Analysys 2");
       
        
        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        fileTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        //getPersonData();
        fileTable.setItems(mainApp.getFileData());
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */ //Person
    private void showPersonDetails(FileType fileType) {
        if (fileType != null) {
            // Fill the labels with info from the person object.
            NameLabel.setText(fileType.getName());
            ExtPrefLabel.setText(fileType.getExtPref());
            ExtSupportedLabel.setText(fileType.getExtSupported());
            selectedFileLabel.setText(fileType.getPath());
//            cityLabel.setText(person.getCity());
//            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            NameLabel.setText("");
            ExtPrefLabel.setText("");
            ExtSupportedLabel.setText("");
            selectedFileLabel.setText("");
          //  cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = fileTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            fileTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
//    @FXML
//    private void handleNewPerson() {
//    	String tmp[] = new String[0]; 
//        FileType tempFile = new FileType(null,null,tmp);
//        boolean okClicked = mainApp.showPersonEditDialog(tempFile);
//        if (okClicked) {
//            mainApp.getFileData().add(tempFile);
//        }
//    }
    
    @FXML
    private void handleNewPerson() {
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Analysis selected");
        alert.setHeaderText("You have selected " + analysisCombo.getSelectionModel().getSelectedItem() + " algorithm");
        alert.setContentText("There will be displayed other information about the algorithm");
        alert.showAndWait();
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
//    @FXML
//    private void handleEditPerson() {
//        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
//        if (selectedPerson != null) {
//            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
//            if (okClicked) {
//                showPersonDetails(selectedPerson);
//            }
//
//        } else {
//            // Nothing selected.
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.initOwner(mainApp.getPrimaryStage());
//            alert.setTitle("No Selection");
//            alert.setHeaderText("No Person Selected");
//            alert.setContentText("Please select a person in the table.");
//
//            alert.showAndWait();
//        }
//    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleSelectFile() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
        		"MIDI or DNA files", "*.mid", "*.dna");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
        	//settare il path del file selezionato
        	mainApp.getFileData().forEach(fileType -> fileType.setPath(file.getAbsolutePath()));
            mainApp.loadDataFromFile(file);
        }
    }
}