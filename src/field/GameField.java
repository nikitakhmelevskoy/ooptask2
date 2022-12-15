package field;

import logic.GameLogic;
import models.Point;
import models.figure.AbstractFigure;
import models.figure.Fox;
import models.figure.Goose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameField {
    private static Map<Point, AbstractFigure> field;
    private boolean gameEnd;
    private AbstractFigure fox;
    private List<AbstractFigure> geese = new ArrayList<>();


    public GameField() {

        int yFox = rnd(1, 4);
        int xFox = 0;
        int plusGeese = rnd(0, 1);

        if (yFox >= 1 && yFox <= 2) {
            xFox = rnd(3, 5);
        } else {
            if (yFox >= 3 && plusGeese == 0 && yFox <= 4) xFox = rnd(1, 7);
            else {
                if (yFox == 3 && plusGeese == 1) xFox = rnd(1, 7);
                else if (yFox == 4 && plusGeese == 1) xFox = rnd(2, 6);
            }
        }

        fox = new Fox(new Point(xFox, yFox));

        field = createField(xFox, yFox, plusGeese == 1);

        printField();

        GameLogic logic = new GameLogic(fox, geese);
        logic.playGame();
    }

    public static void printField() {
        for (int y = 1; y < 3; y++) {
            for (int x = 1; x < 3; x++) {
                System.out.print("  ");
            }
            for (int x = 3; x <= 5; x++) {
                if (field.get(new Point(x, y)) == null) {
                    System.out.print("· ");
                } else {
                    if (field.get(new Point(x, y)).equals(new Fox(new Point(x, y)))) {
                        System.out.print("@ ");
                    } else {
                        if (field.get(new Point(x, y)).equals(new Goose(new Point(x, y)))) {
                            System.out.print("* ");
                        }
                    }
                }
            }
            System.out.println();
        }
        for (int y = 3; y <= 5; y++) {
            for (int x = 1; x <= 7; x++) {
                if (field.get(new Point(x, y)) == null) {
                    System.out.print("· ");
                } else {
                    if (field.get(new Point(x, y)).equals(new Fox(new Point(x, y)))) {
                        System.out.print("@ ");
                    } else {
                        if (field.get(new Point(x, y)).equals(new Goose(new Point(x, y)))) {
                            System.out.print("* ");
                        }
                    }
                }
            }
            System.out.println();
        }
        for (int y = 6; y <= 7; y++) {
            for (int x = 1; x < 3; x++) {
                System.out.print("  ");
            }
            for (int x = 3; x <= 5; x++) {
                if (field.get(new Point(x, y)) == null) {
                    System.out.print("· ");
                } else {
                    if (field.get(new Point(x, y)).equals(new Fox(new Point(x, y)))) {
                        System.out.print("@ ");
                    } else {
                        if (field.get(new Point(x, y)).equals(new Goose(new Point(x, y)))) {
                            System.out.print("* ");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        ;
    }

    private Map<Point, AbstractFigure> createField(int xFox, int yFox, boolean moreGeese) {
        Map<Point, AbstractFigure> field =
                new HashMap<>(Map.of(new Point(xFox, yFox), new Fox(new Point(xFox, yFox))));
        for (int y = 1; y < 3; y++) {
            for (int x = 3; x <= 5; x++) {
                if (x != xFox && y != yFox) {
                    field.put(new Point(x, y), null);
                }
            }
        }

        for (int x = 1; x <= 7; x++) {
            if (x != xFox && 3 != yFox) {
                field.put(new Point(x, 3), null);
            }
        }
        if (moreGeese) {
            field.put(new Point(1, 4), new Goose(new Point(1, 4)));
            field.put(new Point(7, 4), new Goose(new Point(7, 4)));
            geese.add(new Goose(new Point(1, 4)));
            geese.add(new Goose(new Point(7, 4)));

            for (int x = 2; x <= 6; x++) {
                if (x != xFox && 4 != yFox) {
                    field.put(new Point(x, 4), null);
                }
            }
        }
        for (int x = 1; x <= 7; x++) {
            field.put(new Point(x, 5), new Goose(new Point(x, 5)));
            geese.add(new Goose(new Point(x, 5)));
        }


        for (int y = 5; y <= 7; y++) {
            for (int x = 3; x <= 5; x++) {
                field.put(new Point(x, y), new Goose(new Point(x, y)));
                geese.add(new Goose(new Point(x, y)));
            }
        }

        return field;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public static Map<Point, AbstractFigure> getField() {
        return field;
    }

    public static void setField(Map<Point, AbstractFigure> field) {
        GameField.field = field;
    }

    private int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }


}