import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serializable;

class HorLine_Obstacle implements Obstacle, Serializable {
    private int position;
    private transient Rectangle bars[];
    private final int size;
    private final int time;
    private int centerX;
    private final int dirn;
    private final int width;
    private int timeElapsed;
    private static final long serialVersionUID = 12L;

    HorLine_Obstacle(int centerX,int centerY,int len,int width,int dirn,int time) {
        this.dirn=dirn;
        this.time=time;
        makeHorLine(centerX,centerY,len,width);
        this.centerX=centerX;
        position=centerY;
        size=len;
        this.width=width;
        timeElapsed=0;
    }

    @Override
    public boolean isOut(Ball ball) {
        for(int i=0;i<8;i++) {
            if((Shape.intersect(bars[i],ball.getBall()).getLayoutBounds().getHeight()>0)&&(ball.getColor()!=(i%4+1))) {
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
        for(int i=0;i<8;i++) {
            bars[i].setTranslateY(bars[i].getTranslateY()+Y);
        }
    }

    @Override
    public Group getObstacle() {
        Group obstacle=new Group();
        for(int i=0;i<8;i++) {
            obstacle.getChildren().add(bars[i]);
        }
        return obstacle;
    }

    public int getSize() {
        return width;
    }

    public void makeHorLine(int centerX,int centerY,int len,int width) {
        bars=new Rectangle[8];
        for(int i=0;i<8;i++)
        {
            bars[i]=new Rectangle();
            bars[i].setX(centerX-4*len+i*len);
            bars[i].setY(centerY-(width)/2);
            bars[i].setWidth(len);
            bars[i].setHeight(width);
            if(i%4==0)
            {
                bars[i].setFill(Color.rgb(250,225,0));
            }
            else if(i%4==1)
            {
                bars[i].setFill(Color.rgb(144,13,255));
            }
            else if(i%4==2)
            {
                bars[i].setFill(Color.rgb(255,1,129));
            }
            else {
                bars[i].setFill(Color.rgb(50,219,240));
            }
        }

        for(int i=0;i<8;i++)
        {
            bars[i].setCacheHint(CacheHint.SPEED);
            bars[i].setCache(true);
        }
    }

    @Override
    public void animate(int granularity) {
        for(int i=0;i<8;i++) {
            if((bars[i].getBoundsInParent().getMinX()>=5*size)&&(dirn==1)) {
                bars[i].setTranslateX(bars[i].getTranslateX()-8*size);
            }
            else if((bars[i].getBoundsInParent().getMaxX()<=0)&&(dirn==-1)) {
                bars[i].setTranslateX(bars[i].getTranslateX()+8*size);
            }
            else {
                bars[i].setTranslateX(bars[i].getTranslateX()+dirn*4*size*((double)granularity)/((double)time));
            }
        }
        timeElapsed=(timeElapsed+granularity)%time;
    }

    @Override
    public void cleanup() {
        for(int i=0;i<8;i++) {
            bars[i].setCache(false);
        }
    }
    @Override
    public void psd() { ; }
    @Override
    public void pld() { ; }
    @Override
    public void build() {
        makeHorLine(centerX,position,size,width);
        for(int i=0;i<8;i++) {
            bars[i].setTranslateX(bars[i].getTranslateX()+dirn*4*size*((double)timeElapsed)/((double)time));
        }
    }
}