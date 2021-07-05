import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;

import java.io.Serializable;

class Ring_Obstacle implements Obstacle, Serializable {
    private int position;
    private int centerX;
    private int width;
    private transient Group obstacle;
    private final int size;
    private final int time;
    private final int rot;
    private int timeElapsed;
    private static final long serialVersionUID = 12L;

    Ring_Obstacle(int centerX,int centerY,int radius,int width,int rot,int time) {
        obstacle=new Group();
        this.rot=rot;
        this.centerX=centerX;
        this.width=width;
        this.time=time;
        makeCircle(centerX,centerY,radius,width,0);
        position=centerY;
        size=radius;
        timeElapsed=0;
    }

    @Override
    public boolean isOut(Ball ball) {
        int col;
        if(rot==1) {
            col=(3+(timeElapsed*4)/time)%4;
        }
        else {
            col=(2-(timeElapsed*4)/time)%4;
        }
        if((ball.getPosition()+30>getPos()+getSize())&&(ball.getPosition()-30<getPos()+getSize())) {
            if((col+1)!=ball.getColor()) {
                /*System.out.println("Ball Color-"+ball.getColor());
                System.out.println("Obstacle Color-"+(col+1));
                System.out.println("Ball Position-"+ball.getPosition());
                System.out.println("Ring Position-"+getPos()+"+/-"+getSize());
                System.out.println("________________________________");*/
                return true;
            }
            return false;
        }
        else if((ball.getPosition()+30>getPos()-getSize())&&(ball.getPosition()-30<getPos()-getSize())) {
            if(((col+2)%4+1)!=ball.getColor()) {
                return true;
            }
            return false;
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

    private Arc createArc(int X, int Y, int radius, int width, int start, int r, int g, int b){
        Arc temp = new Arc();
        temp.setCenterX(X);
        temp.setCenterY(Y);
        temp.setRadiusX(radius);
        temp.setRadiusY(radius);
        temp.setStartAngle(start);
        temp.setLength(90);
        temp.setType(ArcType.ROUND);
        temp.setStroke(Color.rgb(r,g,b));
        temp.setStrokeWidth(width);
        temp.setStrokeType(StrokeType.INSIDE);
        temp.setType(ArcType.OPEN);
        temp.setFill(Color.rgb(27,27,27));
        return temp;
    }

    public void makeCircle(int centerX,int centerY,int radius,int width,int angle){
        Arc arcYe = createArc(centerX, centerY, radius, width, 0, 250, 225, 0);
        Arc arcPu = createArc(centerX, centerY, radius, width, 90, 144, 13, 255);
        Arc arcPi = createArc(centerX, centerY, radius, width, 180, 251, 1, 129);
        Arc arcBl = createArc(centerX, centerY, radius, width, 270, 50, 219, 240);

        obstacle.getChildren().addAll(arcYe,arcPu,arcPi,arcBl);

        Rotate c=new Rotate();
        c.setAngle(angle);
        c.setPivotX(centerX);
        c.setPivotY(centerY);

        obstacle.getTransforms().add(c);

        obstacle.setCacheHint(CacheHint.ROTATE);
        obstacle.setCache(true);
    }

    @Override
    public void animate(int granularity) {
        if(obstacle.getRotate()>360) {
            obstacle.setRotate(obstacle.getRotate()-360+rot*360.0*((double)granularity)/((double)time));
        }
        else {
            obstacle.setRotate(obstacle.getRotate()+rot*360.0*((double)granularity)/((double)time));
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
        makeCircle(centerX,position,size,width,0);
        obstacle.setRotate(obstacle.getRotate()+rot*360.0*((double)timeElapsed)/((double)time));
        System.out.println("Byld");
    }
}