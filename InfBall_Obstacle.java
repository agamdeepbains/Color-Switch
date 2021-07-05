import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;

class InfBall_Obstacle implements Obstacle, Serializable {
    private int position;
    private int centerX;
    private transient Group obstacle;
    private final int size;
    private final int time;
    private final int dirn;
    private final int width;
    private int timeElapsed;
    private transient PathTransition[] pathTransition;
    private static final long serialVersionUID = 12L;

    InfBall_Obstacle(int centerX,int centerY,int len,int width,int dirn,int time) {
        obstacle=new Group();
        this.dirn=dirn;
        this.centerX=centerX;
        this.time=time;
        makeInfBall(centerX,centerY,len,width,time,0);
        position=centerY;
        size=len;
        this.width=width;
        timeElapsed=0;
    }

    @Override
    public boolean isOut(Ball ball) {
        int col;
        col=(4+(3-timeElapsed*8/time)%4)%4;
        if((ball.getPosition()+30>=getPos()-width/2)&&(ball.getPosition()-30<getPos()+width/2)) {
            if((col+1)!=ball.getColor()) {
                System.out.println("Obstacle-"+(col+1)+", Ball-"+ball.getColor());
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPos() {
        return position;
    }

    @Override
    public void setPos(int Y) {
        position+=Y;
        obstacle.setTranslateY(obstacle.getTranslateY()+Y);
    }

    @Override
    public Group getObstacle() {
        return obstacle;
    }

    public int getSize() {
        return size;
    }

    private Path infPath(int X, int Y, int sz){
        Path path=new Path();
        path.getElements().add(new MoveTo(X,Y));
        path.getElements().add(new CubicCurveTo(X,Y,X-sz,Y-sz,X-sz,Y));
        path.getElements().add(new CubicCurveTo(X-sz,Y+sz,X,Y,X,Y));
        path.getElements().add(new CubicCurveTo(X,Y,X+sz,Y-sz,X+sz,Y));
        path.getElements().add(new CubicCurveTo(X+sz,Y+sz,X,Y,X,Y));
        path.setOpacity(1.0);
        return path;
    }

    public void makeInfBall(int centerX,int centerY,int size,int brad,int time,int delay) {
        Circle[] circle=new Circle[32];
        for(int i=0;i<2;i++){
            for(int j=0;j<4;j++){
                for(int k=0;k<4;k++){
                    circle[16*i+4*j+k]=new Circle();
                    circle[16*i+4*j+k].setCenterX(centerX);
                    circle[16*i+4*j+k].setCenterY(centerY);
                    circle[16*i+4*j+k].setRadius(brad);
                    if(j==0)
                    {
                        circle[16*i+4*j+k].setFill(Color.rgb(250,225,0));
                    }
                    else if(j==1)
                    {
                        circle[16*i+4*j+k].setFill(Color.rgb(144,13,255));
                    }
                    else if(j==2)
                    {
                        circle[16*i+4*j+k].setFill(Color.rgb(255,1,129));
                    }
                    else {
                        circle[16*i+4*j+k].setFill(Color.rgb(50,219,240));
                    }
                }
            }
        }

        Path path=infPath(centerX,centerY,size);

        pathTransition=new PathTransition[32];
        for(int i=0;i<32;i++)
        {
            pathTransition[i]=new PathTransition();
            pathTransition[i].setDuration(Duration.millis(time));
            pathTransition[i].setPath(path);
            pathTransition[i].setNode(circle[i]);
            pathTransition[i].setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition[i].setCycleCount(Timeline.INDEFINITE);
            pathTransition[i].setAutoReverse(false);
            pathTransition[i].setInterpolator(Interpolator.LINEAR);
            pathTransition[i].jumpTo(Duration.millis(delay+((double)time*(double)i)/32.0));
        }

        for(int i=0;i<32;i++) {
            pathTransition[i].play();
        }

        for(int i=0;i<32;i++)
        {
            obstacle.getChildren().add(circle[i]);
        }

        obstacle.setCacheHint(CacheHint.SPEED);
        obstacle.setCache(true);
    }

    @Override
    public void animate(int granularity) {
        timeElapsed=(timeElapsed+granularity)%time;
    }

    @Override
    public void cleanup() {
        for(int i=0;i<32;i++) {
            pathTransition[i].stop();
        }
        obstacle.setCache(false);
    }
    @Override
    public void psd() {
        for(int i=0;i<32;i++) {
            pathTransition[i].pause();
        }
    }
    @Override
    public void pld() {
        for(int i=0;i<32;i++) {
            pathTransition[i].play();
        }
    }
    @Override
    public void build() {
        obstacle=new Group();
        makeInfBall(centerX,position,size,width,time,timeElapsed);
    }
}