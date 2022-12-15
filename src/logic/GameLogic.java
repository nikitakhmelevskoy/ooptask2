package logic;

import field.FieldHelp;
import field.GameField;
import models.Point;
import models.figure.AbstractFigure;
import models.figure.Goose;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLogic {
    private boolean player = true;
    private boolean endGame = false;
    private List<AbstractFigure> geese;
    private Map<Point, AbstractFigure> field = GameField.getField();
    private AbstractFigure fox;

    public GameLogic(AbstractFigure fox, List<AbstractFigure> geese) {
        this.fox = fox;
        this.geese = geese;
    }

    public void playGame() {

        while (!endGame) {
            if (player) {
                moveFox();
            } else {
                moveGoose();
            }
            player = !player;
        }

    }

    private void moveGoose() {

        if (geese.size() <= 8) {
            endGame = true;
            System.out.println("лиса win");
        } else {

            List<Point> steps;

            AbstractFigure walkingGoose;

            for (int i = 0; i < geese.size(); i++) {
                walkingGoose = new Goose(geese.get(i).getCurrentPosition());

                steps = setPossibleStepsForGoose(geese.get(i));

                if (steps.size() != 0) {

                    int rndIndexFromSteps = rnd(0, steps.size() - 1);

                    field.put(walkingGoose.getCurrentPosition(), null);
                    walkingGoose = new Goose(steps.get(rndIndexFromSteps));
                    field.put(walkingGoose.getCurrentPosition(), walkingGoose);

                    geese.remove(i);
                    geese.add(walkingGoose);

                    GameField.printField();
                    break;

                } else {
                    if (i == geese.size() - 1) {
                        endGame = true;
                        System.out.println("лиса win");// нет ходов для гусей
                    }
                }
            }
        }
    }

    private List<Point> setPossibleStepsForGoose(AbstractFigure goose) {

        final List<Point> potentialPoints = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            if (x == 0) {

                Point checkingPoint = new Point(goose.getCurrentPosition().x(),
                        goose.getCurrentPosition().y() - 1);

                if (field.get(checkingPoint) == null && FieldHelp.canPointBeInField(checkingPoint)) {

                    potentialPoints.add(checkingPoint);

                }
            } else {
                for (int y = -1; y <= 0; y++) {

                    Point checkingPoint = new Point(goose.getCurrentPosition().x() + x,
                            goose.getCurrentPosition().y() + y);

                    if (null == field.get(checkingPoint) && FieldHelp.canPointBeInField(checkingPoint)) {

                        potentialPoints.add(checkingPoint);

                    }
                }
            }
        }

        potentialPoints.removeIf(point -> !FieldHelp.canPointBeInField(point));

        return potentialPoints;
    }

    public void moveFox() {

        List<Point> notEmptyPointsNearFox = new ArrayList<>();

        List<Point> emptyPointsNearFox = new ArrayList<>();

        for (var posSteps : fox.getPossibleSteps()) {
            AbstractFigure figure = GameField.getField().get(posSteps.end());

            if (figure != null) {
                notEmptyPointsNearFox.add(posSteps.end());
            } else {
                emptyPointsNearFox.add(posSteps.end());
            }
        }

        if (notEmptyPointsNearFox.size() != 0) {
            if (emptyPointsNearFox.size() == 0) {
                endGame = true;
                System.out.println("гуси win");
            } else {
                int chose = rnd(0, 2);

                if (chose == 0 || chose == 1) {

                    moveFoxOnRandomEmptyPosition(emptyPointsNearFox);

                } else {
                    if (!foxEatGoose(notEmptyPointsNearFox)) {

                        moveFoxOnRandomEmptyPosition(emptyPointsNearFox);

                    }
                }
            }
        }


        if (notEmptyPointsNearFox.size() == 0) {
            moveFoxOnRandomEmptyPosition(null);

        }
    }

    private boolean foxEatGoose(List<Point> notEmptyPointsNearFox) {
        for (Point emptyPointsNearFox : notEmptyPointsNearFox) {

            int deltaX = emptyPointsNearFox.x() -
                    fox.getCurrentPosition().x();
            int deltaY = emptyPointsNearFox.y() -
                    fox.getCurrentPosition().y();

//            System.out.println("---------ЛИСА ХОЧЕТ БИТЬ ГУСЯ ---------");
//            System.out.println("x= " +
//                    emptyPointsNearFox.x() +
//                    " - " + fox.getCurrentPosition().x() + " = " + deltaX);
//            System.out.println("y= " +
//                    emptyPointsNearFox.y() +
//                    " - " + fox.getCurrentPosition().y() + " = " + deltaY);

            int newX = emptyPointsNearFox.x() + deltaX;
            int newY = emptyPointsNearFox.y() + deltaY;

            if (field.get(new Point(newX, newY)) == null && FieldHelp.canPointBeInField(new Point(newX, newY))) {

                field.put(new Point(fox.getCurrentPosition().x(), fox.getCurrentPosition().y()), null);
                field.put(new Point(emptyPointsNearFox.x(), emptyPointsNearFox.y()), null);

                AbstractFigure tmp = fox.moveFigure(new Point(emptyPointsNearFox.x() + deltaX,
                        emptyPointsNearFox.y() + deltaY));

                field.put(new Point(tmp.getCurrentPosition().x(),
                        tmp.getCurrentPosition().y()), tmp);
                fox = tmp;

                geese.remove(new Goose(new Point(emptyPointsNearFox.x(),
                        emptyPointsNearFox.y())));
                GameField.setField(field);
                GameField.printField();

                return true;
            }
        }
        return false;
    }

    private void moveFoxOnRandomEmptyPosition(List<Point> emptyPointsNearFox) {

        AbstractFigure tmp;

        if (emptyPointsNearFox != null) {
            int rndIndForEmptyPoints = rnd(0, emptyPointsNearFox.size() - 1);
            tmp = fox.moveFigure(emptyPointsNearFox.get(rndIndForEmptyPoints));
        } else {
            int delta = rnd(0, fox.getPossibleSteps().size() - 1);
            tmp = fox.moveFigure(fox.getPossibleSteps().get(delta).end());
        }

        field.put(new Point(fox.getCurrentPosition().x(), fox.getCurrentPosition().y()), null);

        field.put(new Point(tmp.getCurrentPosition().x(), tmp.getCurrentPosition().y()), tmp);
        GameField.setField(field);
        fox = tmp;
        GameField.printField();
    }

    private int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}