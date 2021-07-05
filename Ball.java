import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

class Ball implements Serializable {
    private int color;
    private int velocity;
    private int position;
    private transient final Circle ball;
    Ball(int color,int X,int Y,int velocity) {
        this.color=color;
        this.velocity=velocity;
        ball=new Circle();
        ball.setCenterX(X);
        ball.setCenterY(Y);
        ball.setRadius(30);
        ball.setFill(Color.WHITE);
        if(color==1)
        {
            ball.setFill(Color.rgb(250,225,0));
        }
        else if(color==2)
        {
            ball.setFill(Color.rgb(144,13,255));
        }
        else if(color==3)
        {
            ball.setFill(Color.rgb(255,1,129));
        }
        else if(color==4)
        {
            ball.setFill(Color.rgb(50,219,240));
        }
        position=Y;
        velocity=0;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
        if(color==1)
        {
            ball.setFill(Color.rgb(250,225,0));
        }
        else if(color==2)
        {
            ball.setFill(Color.rgb(144,13,255));
        }
        else if(color==3)
        {
            ball.setFill(Color.rgb(255,1,129));
        }
        else if(color==4)
        {
            ball.setFill(Color.rgb(50,219,240));
        }
    }
    public int getVelocity() {
        return velocity;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
        ball.setCenterY(position);
    }
    public Circle getBall() {
        return ball;
    }
}