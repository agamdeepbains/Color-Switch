import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

class VerLine_Obstacle implements Obstacle, Serializable {
    private int position;
    private transient Group[] obstacle;
    private final int size;
    private final int time;
    private final int centerX;
    private final int dirn;
    private final int width;
    private int timeElapsed;
    private static final long serialVersionUID = 12L;

    VerLine_Obstacle(int centerX,int centerY,int len,int width,int dirn,int time) {
        obstacle=new Group[4];
        this.dirn=dirn;
        this.time=time;
        makeVerLine(centerX,centerY,len,width);
        this.centerX=centerX;
        position=centerY;
        size=len;
        this.width=width;
        timeElapsed=0;
    }

    @Override
    public boolean isOut(Ball ball) {
        for(int i=0;i<4;i++) {
            if((ball.getBall().intersects(obstacle[i].localToScene(obstacle[i].getBoundsInLocal())))&&((i+1)!=ball.getColor())) {
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
        for(int i=0;i<4;i++) {
            obstacle[i].setTranslateY(obstacle[i].getTranslateY()+Y);
        }
    }

    @Override
    public Group getObstacle() {
        Group a=new Group();
        for(int i=0;i<4;i++) {
            a.getChildren().add(obstacle[i]);
        }
        return a;
    }

    public int getSize() {
        return size;
    }

    public void makeVerLine(int centerX,int centerY,int len,int width) {
        Rectangle[] bar=new Rectangle[4];
        for(int i=0;i<4;i++){
            bar[i]=new Rectangle();
            bar[i].setX(i*centerX/2);
            bar[i].setY(centerY-(len)/2);
            bar[i].setWidth(width);
            bar[i].setHeight(len);
            bar[i].setArcHeight(width);
            bar[i].setArcWidth(width);
            if(i%4==0)
            {
                bar[i].setFill(Color.rgb(250,225,0));
            }
            else if(i%4==1)
            {
                bar[i].setFill(Color.rgb(144,13,255));
            }
            else if(i%4==2)
            {
                bar[i].setFill(Color.rgb(255,1,129));
            }
            else {
                bar[i].setFill(Color.rgb(50,219,240));
            }

            obstacle[i]=new Group();
            obstacle[i].getChildren().add(bar[i]);

            obstacle[i].setCacheHint(CacheHint.SPEED);
            obstacle[i].setCache(true);
        }
    }

    @Override
    public void animate(int granularity) {
        for(int i=0;i<4;i++) {
            if((obstacle[i].localToScene(obstacle[i].getBoundsInLocal()).getMinX()>=2*centerX)&&(dirn==1)) {
                obstacle[i].setTranslateX(obstacle[i].getTranslateX()-(2*centerX+width));
            }
            else if((obstacle[i].localToScene(obstacle[i].getBoundsInLocal()).getMinX()<=-width)&&(dirn==-1)) {
                obstacle[i].setTranslateX(obstacle[i].getTranslateX()+(2*centerX+width));
            }
            else {
                obstacle[i].setTranslateX(obstacle[i].getTranslateX()+dirn*centerX*2*((double)granularity)/((double)time));
            }
        }
        timeElapsed=(timeElapsed+granularity)%time;
    }

    @Override
    public void cleanup() {
        for (Group group : obstacle) {
            group.setCache(false);
        }
    }
    @Override
    public void psd() { ; }
    @Override
    public void pld() { ; }
    @Override
    public void build() {
        obstacle=new Group[4];
        makeVerLine(centerX,position,size,width);
        for(int i=0;i<4;i++) {
            obstacle[i].setTranslateX(obstacle[i].getTranslateX()+dirn*centerX*2*((double)timeElapsed)/((double)time));
        }
    }
}