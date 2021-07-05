import javafx.scene.Group;

import java.io.Serializable;

interface Obstacle extends Serializable {
    boolean isOut(Ball ball);
    int getPos();
    int getSize();
    void setPos(int Y);
    Group getObstacle();
    void animate(int granularity);
    void cleanup();
    void psd();
    void pld();
    void build();
}