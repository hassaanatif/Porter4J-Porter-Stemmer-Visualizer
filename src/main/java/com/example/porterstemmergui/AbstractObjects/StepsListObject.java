package com.example.porterstemmergui.AbstractObjects;

import com.example.porterstemmerbackend.StepsRecorder;

import java.util.ArrayList;

public class StepsListObject {
    public StepsRecorder getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(StepsRecorder firstStep) {
        this.firstStep = firstStep;
    }

    public StepsRecorder getSecondStep() {
        return secondStep;
    }

    public void setSecondStep(StepsRecorder secondStep) {
        this.secondStep = secondStep;
    }

    StepsRecorder firstStep;
    StepsRecorder secondStep;

    public StepsListObject(StepsRecorder firstStep, StepsRecorder secondStep) {
        this.firstStep = firstStep;
        this.secondStep = secondStep;
    }

    public static ArrayList<StepsListObject> ConvertStepsRecorderToStepsList(ArrayList<StepsRecorder> stepsRecorderArrayList) {
        ArrayList<StepsListObject> stepListObjArrayList = new ArrayList<>();
        StepsListObject tmpStepListObj = new StepsListObject(null, null);
        stepListObjArrayList.add(tmpStepListObj);
        for (StepsRecorder stepsRecorder : stepsRecorderArrayList) {
            if (tmpStepListObj.getFirstStep() == null)
                tmpStepListObj.setFirstStep(stepsRecorder);
            else if (tmpStepListObj.getSecondStep() == null)
                tmpStepListObj.setSecondStep(stepsRecorder);
            else {
                tmpStepListObj = new StepsListObject(stepsRecorder, null);
                stepListObjArrayList.add(tmpStepListObj);
            }
        }
      return stepListObjArrayList;
    }
}
