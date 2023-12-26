package com.example.porterstemmergui.AbstractObjects;

import javafx.beans.property.SimpleStringProperty;

public class WordCell {

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty word = new SimpleStringProperty("");
    private SimpleStringProperty stem = new SimpleStringProperty("");

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getWord() {
        return word.get();
    }

    public SimpleStringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }

    public String getStem() {
        return stem.get();
    }

    public SimpleStringProperty stemProperty() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem.set(stem);
    }

    public String getSteps() {
        return steps.get();
    }

    public SimpleStringProperty stepsProperty() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps.set(steps + " step(s)");
    }

    private SimpleStringProperty steps = new SimpleStringProperty("");

    public WordCell(String id, String word, String stem, String steps) {
        this.id.set(id);
        this.word.set(word);
        this.stem.set(stem);
        this.steps.set(steps + " step(s)");
    }


}
