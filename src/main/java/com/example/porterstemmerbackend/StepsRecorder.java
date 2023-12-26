package com.example.porterstemmerbackend;

import javafx.beans.property.SimpleStringProperty;

public class StepsRecorder {
private String step_number;
private String rule = "";

private String word = "";
private String transformation = "";

private SimpleStringProperty word_property;
private SimpleStringProperty rule_property;
private SimpleStringProperty step_property;

    public String getWord_property() {
        return word_property.get();
    }

    public SimpleStringProperty word_propertyProperty() {
        return word_property;
    }

    public void setWord_property(String word_property) {
        this.word_property.set(word_property);
    }

    public String getRule_property() {
        return rule_property.get();
    }

    public SimpleStringProperty rule_propertyProperty() {
        return rule_property;
    }

    public void setRule_property(String rule_property) {
        this.rule_property.set(rule_property);
    }

    public String getStep_property() {
        return step_property.get();
    }

    public SimpleStringProperty step_propertyProperty() {
        return step_property;
    }

    public void setStep_property(String step_property) {
        this.step_property.set(step_property);
    }

    public String getTransformation_property() {
        return transformation_property.get();
    }

    public SimpleStringProperty transformation_propertyProperty() {
        return transformation_property;
    }

    public void setTransformation_property(String transformation_property) {
        this.transformation_property.set(transformation_property);
    }

    private SimpleStringProperty transformation_property;
    public StepsRecorder(String step_number, String rule, String word, String transformation) {
        this.step_number = step_number;
        this.rule = rule;
        this.word = word;
        this.transformation = transformation;

        this.word_property = new SimpleStringProperty(word);
        this.rule_property = new SimpleStringProperty(rule);
        this.step_property = new SimpleStringProperty(step_number);
        this.transformation_property = new SimpleStringProperty(transformation);

    }

    @Override
    public String toString() {
        return step_number + " :\n" + word +"\n" + rule + "\n" + transformation + "\n";
    }

    public String getStep_number() {
        return step_number;
    }

    public void setStep_number(String step_number) {
        this.step_number = step_number;
        setStep_property(step_number);
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
        setRule_property(rule);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        setWord_property(word);
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
        setTransformation_property(transformation);
    }
}
