package models.figure;

import models.Point;
import models.Step;

import java.util.List;

public interface Character {

    List<Step> getPossibleSteps();

    Point getCurrentPosition();
}