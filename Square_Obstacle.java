import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.io.Serializable;

class Square_Obstacle implements Obstacle, Serializable {
    private int position;
    private int centerX;
    private int width;
    private transient Group obstacle;
    private transient Rectangle[] arms;
    private final int size;
    private final int time;
    private final int dirn;
    private int timeElapsed;
    private static final long serialVersionUID = 12L;

    Square_Obstacle(int centerX,int centerY,int len,int width,int dirn,int time) {
        obstacle=new Group();
        arms=new Rectangle[4];
        this.dirn=dirn;
        this.centerX=centerX;
        this.width=width;
        this.time=time;
        makeSquare(centerX,centerY,len,width,0);
        position=centerY;
        size=len;
        timeElapsed=0;
    }

    @Override
    public boolean isOut(Ball ball) {
        for(int i=0;i<4;i++) {
            if((Shape.intersect(arms[i],ball.getBall()).getLayoutBounds().getHeight()>0)&&((i+1)!=ball.getColor())) {
                System.out.println("Obstacle-"+(i+1)+", Ball-"+ball.getColor());
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

    public void makeSquare(int centerX,int centerY,int side,int width,int angle){
        Rectangle armYe=new Rectangle();
        armYe.setX(centerX-(side)/2-width);
        armYe.setY(centerY-(side)/2-width);
        armYe.setHeight(side+width*2);
        armYe.setWidth(width);
        armYe.setArcHeight(width/2);
        armYe.setArcWidth(width/2);
        armYe.setFill(Color.rgb(250,225,0));

        arms[0]=armYe;

        Rectangle cover=new Rectangle();
        cover.setX(centerX-(side)/2-width);
        cover.setY(centerY-(side)/2-width);
        cover.setHeight((side+width*2)/2);
        cover.setWidth(width);
        cover.setArcHeight(width/2);
        cover.setArcWidth(width/2);
        cover.setFill(Color.rgb(250,225,0));

        Rectangle armPu=new Rectangle();
        armPu.setX(centerX-(side)/2-width);
        armPu.setY(centerY+(side)/2);
        armPu.setHeight(width);
        armPu.setWidth(side+2*width);
        armPu.setArcHeight(width/2);
        armPu.setArcWidth(width/2);
        armPu.setFill(Color.rgb(144,13,255));

        arms[1]=armPu;

        Rectangle armPi=new Rectangle();
        armPi.setX(centerX+(side)/2);
        armPi.setY(centerY-(side)/2-width);
        armPi.setHeight(side+2*width);
        armPi.setWidth(width);
        armPi.setArcHeight(width/2);
        armPi.setArcWidth(width/2);
        armPi.setFill(Color.rgb(255,1,129));

        arms[2]=armPi;

        Rectangle armBl=new Rectangle();
        armBl.setX(centerX-(side)/2-width);
        armBl.setY(centerY-(side)/2-width);
        armBl.setHeight(width);
        armBl.setWidth(side+2*width);
        armBl.setArcHeight(width/2);
        armBl.setArcWidth(width/2);
        armBl.setFill(Color.rgb(50,219,240));

        arms[3]=armBl;

        obstacle.getChildren().addAll(armYe,armPu,armPi,armBl,cover);

        obstacle.setCacheHint(CacheHint.ROTATE);
        obstacle.setCache(true);

        Rotate c;
        c=new Rotate();
        c.setAngle(angle);
        c.setPivotX(centerX);
        c.setPivotY(centerY);

        obstacle.getTransforms().add(c);
    }

    @Override
    public void animate(int granularity) {
        if(obstacle.getRotate()>360) {
            obstacle.setRotate(obstacle.getRotate()-360+dirn*360.0*((double)granularity)/((double)time));
        }
        else {
            obstacle.setRotate(obstacle.getRotate()+dirn*360.0*((double)granularity)/((double)time));
        }
        timeElapsed=(timeElapsed+granularity)%time;
    }
    @Override
    public void cleanup() {
        obstacle.setCache(false);
    }
    @Override
    public void psd() { ; }
    @Override
    public void pld() { ; }
    @Override
    public void build() {
        obstacle=new Group();
        arms=new Rectangle[4];
        makeSquare(centerX,position,size,width,0);
        obstacle.setRotate(obstacle.getRotate()+dirn*360.0*((double)timeElapsed)/((double)time));
    }
}