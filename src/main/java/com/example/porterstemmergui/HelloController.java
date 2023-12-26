package com.example.porterstemmergui;

import com.example.IOUnit.IOClass;
import com.example.porterstemmergui.AbstractObjects.StepsListObject;
import com.example.porterstemmergui.AbstractObjects.WordCell;
import com.example.porterstemmergui.utilities.GuiUtilityClass;
import com.example.porterstemmergui.utilities.WordsKeeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController  implements Initializable {
    @FXML
    private Pane step_pane;
    @FXML
    private HBox hbox_steps;
    @FXML
    private VBox vbox_steps;

    @FXML
    private Label step_x_lbl;
    @FXML
    private Label wrd_lbl;
    @FXML
    private Label rule_lbl;
    @FXML
    private Label result_lbl;

    @FXML
    private VBox output_vbox;
    @FXML
    private Pane output_pane;


/***these are the descriptor labels such as Word : , Rule : and Result : ***/
    @FXML
    private Label lbl_1;
    @FXML
    private Label lbl_2;
    @FXML
    private Label lbl_3;

    /****New stuff**/
    @FXML
    private ListView wordsListView;
    private ObservableList<WordCell> listOfWords = FXCollections.observableArrayList();
    private ObservableList<StepsListObject> listOfSteps = FXCollections.observableArrayList();
    private class CustomListCell extends ListCell<WordCell> {
        @FXML
        private AnchorPane mainAnchorPane;

        private FXMLLoader mloader;
        private String odd_selected ="-fx-background-color:  #333333; -fx-border-color: #7b7b7b; -fx-background-radius:  4px; -fx-border-radius:  4px;";
        // private String even_unselected = "-fx-background-color: #262533; -fx-background-radius:  4px; -fx-border-radius:  4px;";
        private String odd_unselected = "-fx-background-color:#333333; -fx-background-radius:  4px; -fx-border-radius:  4px;";

        @FXML
                private Label row_id;
        @FXML
                private Label row_word;
        @FXML
                private Label row_stem;
        @FXML
                private Label row_steps;

        Pane tmpPane;
        CustomListCell() {
            super();
            if (mloader == null) {
                mloader = new FXMLLoader((HelloApplication.class.getResource("row_table.fxml")));
                mloader.setController(this);

                try {
                    mloader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    if (getItem() != null) {
        selectedProperty().addListener((v, oldVal, newVal) -> {
            if (getItem() != null) {
                Pane labelPane = (Pane)mainAnchorPane.getChildren().get(0);
                 if (newVal)
                    labelPane.setStyle(odd_selected);
                else
                    labelPane.setStyle(odd_unselected);

            }

        });


    }


            itemProperty().addListener((list, oldValue, newValue) -> {
                if (newValue != null) {

                    row_id.textProperty().bind(getItem().idProperty());
                    row_stem.textProperty().bind(getItem().stemProperty());
                    row_steps.textProperty().bind(getItem().stepsProperty());
                    row_word.textProperty().bind(getItem().wordProperty());

                    row_id.setFont(GuiUtilityClass.regularPoppins12px);
                    row_stem.setFont(GuiUtilityClass.regularPoppins12px);
                    row_steps.setFont(GuiUtilityClass.regularPoppins12px);
                    row_word.setFont(GuiUtilityClass.regularPoppins12px);
                }
                else {

                    row_id.textProperty().unbind();
                    row_stem.textProperty().unbind();
                    row_steps.textProperty().unbind();
                    row_word.textProperty().unbind();
                }
            });


    mainAnchorPane.setOnMouseClicked((e)->{
        steps_view_pane.setVisible(true);
        listOfSteps.clear();
        listOfSteps.addAll(StepsListObject.ConvertStepsRecorderToStepsList(WordsKeeper.stepsByWords.get(getItem().getWord())));

    });



        }

        @Override
        protected void updateItem(WordCell wordCell, boolean b) {
            super.updateItem(wordCell, b);
            if (b || wordCell == null) {
                setText(null);
                setGraphic(null);
                setTooltip(null);
            }
            else {
                getItem().setId(String.valueOf(listOfWords.indexOf(getItem()) + 1));
                tmpPane = (Pane)mainAnchorPane.getChildren().get(0);

                if (isSelected()) {
                    tmpPane.setStyle(odd_selected);
                 }
                else
                    tmpPane.setStyle(odd_unselected);
             setGraphic(mainAnchorPane);
             if (!isListBeingUsedBySearch)
                 getListView().setItems(listOfWords);
             else
                 getListView().setItems(sortedData);
            }
        }
    }

    /***Steps list view**/
    @FXML
    private ListView<StepsListObject> stepsListView;
    private class StepsListCell extends ListCell<StepsListObject> {
        @FXML
        private AnchorPane mainAnchorPane;

        private FXMLLoader mloader;
        private String odd_selected ="-fx-background-color:  #333333; -fx-border-color: purple; -fx-background-radius:  4px; -fx-border-radius:  4px;";
        // private String even_unselected = "-fx-background-color: #262533; -fx-background-radius:  4px; -fx-border-radius:  4px;";
        private String odd_unselected = "-fx-background-color:#333333; -fx-background-radius:  4px; -fx-border-radius:  4px;";

        @FXML
       private Pane firstStep;
        @FXML
         private Pane secondStep;
        @FXML
                private Label word_lbl;
        @FXML
                private Label rule_lbl;

        @FXML
                private Label result_lbl;
        @FXML
                private Label step_lbl;

        @FXML
                private Label word_lbl_2;
        @FXML
                private Label rule_lbl_2;
        @FXML
                private Label result_lbl_2;
        @FXML
                private Label step_lbl_2;

        @FXML
                private Label lbl_1;
        @FXML
        private Label lbl_2;
        @FXML
        private Label lbl_3;
        @FXML
        private Label lbl_4;
        @FXML
        private Label lbl_5;
        @FXML
        private Label lbl_6;



        StepsListCell() {
            super();
            if (mloader == null) {
                mloader = new FXMLLoader((HelloApplication.class.getResource("steps_table.fxml")));
                mloader.setController(this);

                try {
                    mloader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            itemProperty().addListener((list, oldValue, newValue) -> {
                if (newValue != null) {
                    if (getItem().getFirstStep() != null) {
                        firstStep.setVisible(true);
                        word_lbl.textProperty().bind(getItem().getFirstStep().word_propertyProperty());
                        rule_lbl.textProperty().bind(getItem().getFirstStep().rule_propertyProperty());
                        result_lbl.textProperty().bind(getItem().getFirstStep().transformation_propertyProperty());
                        step_lbl.textProperty().bind(getItem().getFirstStep().step_propertyProperty());

                        word_lbl.setFont(GuiUtilityClass.regularPoppins10px);
                        rule_lbl.setFont(GuiUtilityClass.regularPoppins10px);
                        result_lbl.setFont(GuiUtilityClass.regularPoppins10px);
                        step_lbl.setFont(GuiUtilityClass.regularPoppins10px);

                        lbl_1.setFont(GuiUtilityClass.regularPoppins12px);
                        lbl_2.setFont(GuiUtilityClass.regularPoppins12px);
                        lbl_3.setFont(GuiUtilityClass.regularPoppins12px);
                    }
                    else {
                        firstStep.setVisible(false);
                    }

                    if (getItem().getSecondStep() != null) {
                        secondStep.setVisible(true);
                        word_lbl_2.textProperty().bind(getItem().getSecondStep().word_propertyProperty());
                        rule_lbl_2.textProperty().bind(getItem().getSecondStep().rule_propertyProperty());
                        result_lbl_2.textProperty().bind(getItem().getSecondStep().transformation_propertyProperty());
                        step_lbl_2.textProperty().bind(getItem().getSecondStep().step_propertyProperty());

                        word_lbl_2.setFont(GuiUtilityClass.regularPoppins10px);
                        rule_lbl_2.setFont(GuiUtilityClass.regularPoppins10px);
                        result_lbl_2.setFont(GuiUtilityClass.regularPoppins10px);
                        step_lbl_2.setFont(GuiUtilityClass.regularPoppins10px);


                        lbl_4.setFont(GuiUtilityClass.regularPoppins12px);
                        lbl_5.setFont(GuiUtilityClass.regularPoppins12px);
                        lbl_6.setFont(GuiUtilityClass.regularPoppins12px);
                    }
                    else
                        secondStep.setVisible(false);

                }
                else {
                    //unbind everything
/*                    label01_01.textProperty().unbind();
                    pane02.setDisable(true);*/
                    word_lbl.textProperty().unbind();
                    rule_lbl.textProperty().unbind();
                    result_lbl.textProperty().unbind();
                    step_lbl.textProperty().unbind();
                    word_lbl_2.textProperty().unbind();
                    rule_lbl_2.textProperty().unbind();
                    result_lbl_2.textProperty().unbind();
                    step_lbl_2.textProperty().unbind();

                }
            });



        }

        @Override
        protected void updateItem(StepsListObject stepsListObject, boolean b) {
            super.updateItem(stepsListObject, b);
            if (b || stepsListObject == null) {
                setText(null);
                setGraphic(null);
                setTooltip(null);
            }
            else {

                setGraphic(mainAnchorPane);
                getListView().setItems(listOfSteps);
            }
        }
    }
@FXML
private StackPane cross_icon;
@FXML
private Button importBtn;
@FXML
private TextArea corpus_txtarea;

@FXML
private Label porterizeBtn;

@FXML
private TextField searchbox_txtfield;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCode();


    }

    private  String corpus = "";

    private String preprocessCorpus (String verbatim)  {
         String textWithoutNewLines = verbatim.replaceAll("\\n", " ");

         String textWithoutPunctuation = textWithoutNewLines.replaceAll("[^a-zA-Z0-9 ]", "");

         String[] words = textWithoutPunctuation.split("\\s+");
        Set<String> stopWords = new HashSet<>(Arrays.asList("is", "a", "it", "has", "and", "with"));

        StringBuilder preprocessedTextBuilder = new StringBuilder();
        for (String word : words) {
            if (!stopWords.contains(word.toLowerCase())) {
                preprocessedTextBuilder.append(word).append(" ");
            }
        }

        return preprocessedTextBuilder.toString().trim();
    }
    public void injectCorpus (String verbatim) {

        corpus = preprocessCorpus(verbatim);


        //corpus.replace("\n", " ");
    }
    private void initializeCode() {
        initalizeControlsForFirstPage();
        configureSearchBox();


    }


    private ObservableList<WordCell> currentFilteredWords = FXCollections.observableArrayList();
    boolean isListBeingUsedBySearch = false;
    SortedList<WordCell> sortedData;
    private void configureSearchBox() {
        //-fx-background-image:url('/gui/icons/search.png'); -fx-background-repeat: no-repeat; -fx-background-position: left 3px center;
        searchbox_txtfield.setStyle("-fx-background-color:  #191919; -fx-text-fill: white;");
        searchbox_txtfield.setFont(GuiUtilityClass.regularPoppins12px);
        searchbox_txtfield.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!(newValue.isBlank())) {
                FilteredList<WordCell> filteredData = new FilteredList<>(listOfWords, s -> true);

                filteredData.setPredicate(s -> (s.getWord().contains(newValue.toLowerCase()))
                || s.getSteps().toLowerCase().contains(newValue.toLowerCase()));


                 sortedData = new SortedList<>(filteredData);

                wordsListView.getSelectionModel().clearSelection();
                isListBeingUsedBySearch = true;
                wordsListView.setItems(null);
                wordsListView.setItems(sortedData);
            }
            else {
                isListBeingUsedBySearch = false;
                wordsListView.setItems(null);
                wordsListView.setItems(listOfWords);
            }


        });
    }

    @FXML
     private Pane initialPage;
    @FXML
    private Pane tablePage;
    @FXML
    private Label porter4j_lbl;
    @FXML
    private Label enterCorpus_lbl;
    @FXML
    private Label or_lbl;


 private void initalizeControlsForFirstPage() {
     if (!(configured)) {
         configureControls();
         configureFonts();
         configured = true;
     }
     configureTextArea();

     porterizeBtn.setOnMouseClicked((e)->{
         WordsKeeper.clearData();
        injectCorpus(corpus_txtarea.getText());
        WordsKeeper.CreateStepsByWords(corpus);


  /*      listOfSteps.clear();
        listOfSteps.addAll(StepsListObject.ConvertStepsRecorderToStepsList(WordsKeeper.stepsByWords.get("apples")));
      */  populateWordCells();

        initialPage.setVisible(false);
        tablePage.setVisible(true);
    });

 }

    private void configureTextArea() {
     corpus_txtarea.setFont(GuiUtilityClass.regularPoppins12px);
    }

    private void configureFonts() {
     porter4j_lbl.setFont(GuiUtilityClass.regularRetroFont45px);
     enterCorpus_lbl.setFont(GuiUtilityClass.regularPoppins12px);
     or_lbl.setFont(GuiUtilityClass.regularPoppins12px);
     importBtn.setFont(GuiUtilityClass.regularPoppins12px);
     clearText_btn.setFont(GuiUtilityClass.regularPoppins12px);

     porterizeBtn.setFont(GuiUtilityClass.regularPoppins12px);
    }


    private void configureControls () {
        setupListView();
        stepsListView.refresh();
        configureButtons();
        configureImportButton();
    }
    boolean configured = false;


    private void populateWordCells() {
        listOfWords.clear();
        listOfWords.addAll(WordsKeeper.CreateWordCellsFromWords());
     }
    @FXML
    private Pane steps_view_pane;
    @FXML
    private Label backBtn;
    @FXML
    private Button clearText_btn;
    @FXML
    private StackPane closeIcon;
    private void configureButtons() {
    cross_icon.setOnMouseClicked((e)->{
        steps_view_pane.setVisible(false);
    });
    backBtn.setOnMouseClicked((e)->{
        tablePage.setVisible(false);
        initialPage.setVisible(true);
    });
    clearText_btn.setOnMouseClicked((e)->{
        corpus_txtarea.clear();
    });
    closeIcon.setOnMouseClicked((e)->{
        System.exit(1);
    });
    }

    private void setupListView() {
        wordsListView.setItems(listOfWords);
        wordsListView.setCellFactory(iSiteListView -> new CustomListCell());
        wordsListView.getStylesheets().add(HelloApplication.class.getResource("customListView.css").toExternalForm());


        stepsListView.setItems(listOfSteps);
        stepsListView.setCellFactory(iStepsListView -> new StepsListCell());
        stepsListView.getStylesheets().add(HelloApplication.class.getResource("customListView.css").toExternalForm());
    }

   private void configureImportButton () {

       importBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               FileChooser fc = new FileChooser();
               File selectedFile = fc.showOpenDialog(null);
               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (.csv)", "*.csv");
               fc.getExtensionFilters().add(extFilter);

               if (selectedFile != null) {
                   String corpus = IOClass.importText(selectedFile);

                   if (corpus.trim().equals("")) {
                        corpus_txtarea.setText("Import Failed!");

                   } else {
                       corpus_txtarea.setText(corpus);
                   }
               }
           }
       });
   }
}