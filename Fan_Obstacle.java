import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.io.Serializable;

class Fan_Obstacle implements Obstacle, Serializable {
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

    Fan_Obstacle(int centerX,int centerY,int len,int width,int dirn,int time) {
        obstacle=new Group();
        arms=new Rectangle[4];
        this.centerX=centerX;
        this.width=width;
        this.dirn=dirn;
        this.time=time;
        makeFan(centerX,centerY,len,width,0);
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

    public void makeFan(int centerX,int centerY,int arm,int width,int angle)  {
        Rectangle armYe=new Rectangle();
        armYe.setX(centerX);
        armYe.setY(centerY-(width)/2);
        armYe.setHeight(width);
        armYe.setWidth(arm+width);
        armYe.setArcHeight(width);
        armYe.setArcWidth(width);
        armYe.setFill(Color.rgb(250,225,0));

        arms[0]=armYe;

        Polygon triYe=new Polygon();
        triYe.getPoints().setAll((double)centerX,(double)centerY,(double)(centerX+width/2),(double)(centerY-width/2),(double)(centerX+width/2),(double)(centerY+width/2));
        triYe.setFill(Color.rgb(250,225,0));

        Rectangle armPu=new Rectangle();
        armPu.setX(centerX-(width)/2);
        armPu.setY(centerY);
        armPu.setHeight(arm+width);
        armPu.setWidth(width);
        armPu.setArcHeight(width);
        armPu.setArcWidth(width);
        armPu.setFill(Color.rgb(144,13,255));

        arms[1]=armPu;

        Polygon triPu=new Polygon();
        triPu.getPoints().setAll((double)centerX,(double)centerY,(double)(centerX+width/2),(double)(centerY+width/2),(double)(centerX-width/2),(double)(centerY+width/2));
        triPu.setFill(Color.rgb(144,13,255));

        Rectangle armPi=new Rectangle();
        armPi.setX(centerX-(arm+width));
        armPi.setY(centerY-(width)/2);
        armPi.setHeight(width);
        armPi.setWidth(arm+width);
        armPi.setArcHeight(width);
        armPi.setArcWidth(width);
        armPi.setFill(Color.rgb(255,1,129));

        arms[2]=armPi;

        Polygon triPi=new Polygon();
        triPi.getPoints().setAll((double)centerX,(double)centerY,(double)(centerX-width/2),(double)(centerY-width/2),(double)(centerX-width/2),(double)(centerY+width/2));
        triPi.setFill(Color.rgb(255,1,129));

        Rectangle armBl=new Rectangle();
        armBl.setX(centerX-(width)/2);
        armBl.setY(centerY-(arm+width));
        armBl.setHeight(arm+width);
        armBl.setWidth(width);
        armBl.setArcHeight(width);
        armBl.setArcWidth(width);
        armBl.setFill(Color.rgb(50,219,240));

        arms[3]=armBl;

        Polygon triBl=new Polygon();
        triBl.getPoints().setAll((double)centerX,(double)centerY,(double)(centerX-width/2),(double)(centerY-width/2),(double)(centerX+width/2),(double)(centerY-width/2));
        triBl.setFill(Color.rgb(50,219,240));

        obstacle.getChildren().addAll(armYe,armPu,armPi,armBl,triYe,triPu,triPi,triBl);

        obstacle.setCacheHint(CacheHint.ROTATE);
        obstacle.setCache(true);

        Rotate c=new Rotate();
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
        makeFan(centerX,position,size,width,0);
        obstacle.setRotate(obstacle.getRotate()+dirn*360.0*((double)timeElapsed)/((double)time));
    }
}