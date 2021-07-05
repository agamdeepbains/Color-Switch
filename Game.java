import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Random;

class Game implements Serializable{
    private Ball ball;
    private ArrayList<Obstacle> obstacle;
    private transient ArrayList<Polygon> star;
    private ArrayList<Integer> starPos;
    private transient ArrayList<Node> colorSwitch;
    private ArrayList<Integer> colorSwitchPos;
    private transient ArrayList<Node> upArrow;
    private ArrayList<Integer> upArrowPos;
    private transient ArrayList<Node> downArrow;
    private ArrayList<Integer> downArrorPos;
    private int granularity;
    private Random selector;
    private transient Group obsGroup;
    private transient Group starGroup;
    private transient Group switchGroup;
    private transient Group upArrowGroup;
    private transient Group downArrowGroup;
    private boolean suwi;
    private boolean d3d;
    private boolean psd;
    private int score;
    private transient Text scoreDisp;
    private transient Scene scene;
    private transient EventHandler<MouseEvent> eventHandler;
    private transient Button death;
    private transient Button pause;
    private boolean fresh;
    private int invincible;
    private final int cutoff=10;
    private static final long serialVersionUID = 12L;
    Game() {
        fresh=true;
    }
    public int getScore() {
        return score;
    }

    public Button getDeathButton() {
        return death;
    }

    public Button getPauseButton() {
        return pause;
    }

    public Scene getScene() {
        return scene;
    }

    public EventHandler<MouseEvent> getEventHandler() {
        return eventHandler;
    }

    public void setStuff(){
        suwi=false;
        d3d = false;
        invincible=1000;
    }

    public void redScore(){
        score -= 5;
        scoreDisp.setText(Integer.toString(score));
    }

    private Polygon makeStar(int X, int Y) {
        int arm=15;
        Polygon star=new Polygon();
        star.getPoints().addAll((double)X+2*arm*Math.cos(Math.toRadians(-90)),(double)Y+2*arm*Math.sin(Math.toRadians(-90)),
                (double)X+arm*Math.cos(Math.toRadians(-54)),(double)Y+arm*Math.sin(Math.toRadians(-54)),
                (double)X+2*arm*Math.cos(Math.toRadians(-18)),(double)Y+2*arm*Math.sin(Math.toRadians(-18)),
                (double)X+arm*Math.cos(Math.toRadians(18)),(double)Y+arm*Math.sin(Math.toRadians(18)),
                (double)X+2*arm*Math.cos(Math.toRadians(54)),(double)Y+2*arm*Math.sin(Math.toRadians(54)),
                (double)X+arm*Math.cos(Math.toRadians(90)),(double)Y+arm*Math.sin(Math.toRadians(90)),
                (double)X+2*arm*Math.cos(Math.toRadians(126)),(double)Y+2*arm*Math.sin(Math.toRadians(126)),
                (double)X+arm*Math.cos(Math.toRadians(162)),(double)Y+arm*Math.sin(Math.toRadians(162)),
                (double)X+2*arm*Math.cos(Math.toRadians(-162)),(double)Y+2*arm*Math.sin(Math.toRadians(-162)),
                (double)X+arm*Math.cos(Math.toRadians(-126)),(double)Y+arm*Math.sin(Math.toRadians(-126)));
        star.setFill(Color.WHITE);
        return star;
    }

    private Group makeUpArrow(int X,int Y) {
        Group group=new Group();
        Polygon upArrow=new Polygon();
        upArrow.getPoints().addAll((double)X,(double)Y,(double)X+20.0,(double)Y+20.0,
                (double)X+20.0,(double)Y+10.0,(double)X,(double)Y-10.0,
                (double)X-20.0,(double)Y+10.0,(double)X-20.0,(double)Y+20.0);
        upArrow.setFill(Color.WHITE);
        group.getChildren().add(upArrow);
        Y-=20;
        upArrow=new Polygon();
        upArrow.getPoints().addAll((double)X,(double)Y,(double)X+20.0,(double)Y+20.0,
                (double)X+20.0,(double)Y+10.0,(double)X,(double)Y-10.0,
                (double)X-20.0,(double)Y+10.0,(double)X-20.0,(double)Y+20.0);
        upArrow.setFill(Color.WHITE);
        group.getChildren().add(upArrow);
        Y+=40;
        upArrow=new Polygon();
        upArrow.getPoints().addAll((double)X,(double)Y,(double)X+20.0,(double)Y+20.0,
                (double)X+20.0,(double)Y+10.0,(double)X,(double)Y-10.0,
                (double)X-20.0,(double)Y+10.0,(double)X-20.0,(double)Y+20.0);
        upArrow.setFill(Color.WHITE);
        group.getChildren().add(upArrow);
        return group;
    }

    private Group makeDownArrow(int X,int Y) {
        Group group=new Group();
        Polygon downArrow=new Polygon();
        downArrow.getPoints().addAll((double)X,(double)Y,(double)X-20.0,(double)Y-20.0,
                (double)X-20.0,(double)Y-10.0,(double)X,(double)Y+10.0,
                (double)X+20.0,(double)Y-10.0,(double)X+20.0,(double)Y-20.0);
        downArrow.setFill(Color.WHITE);
        Y-=20;
        downArrow=new Polygon();
        downArrow.getPoints().addAll((double)X,(double)Y,(double)X-20.0,(double)Y-20.0,
                (double)X-20.0,(double)Y-10.0,(double)X,(double)Y+10.0,
                (double)X+20.0,(double)Y-10.0,(double)X+20.0,(double)Y-20.0);
        downArrow.setFill(Color.WHITE);
        Y+=40;
        downArrow=new Polygon();
        downArrow.getPoints().addAll((double)X,(double)Y,(double)X-20.0,(double)Y-20.0,
                (double)X-20.0,(double)Y-10.0,(double)X,(double)Y+10.0,
                (double)X+20.0,(double)Y-10.0,(double)X+20.0,(double)Y-20.0);
        downArrow.setFill(Color.WHITE);
        return group;
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

    private Group makeColorSwitch(int X,int Y) {
        int radius = 25, width = 10;

        Arc arcYe = createArc(X, Y, radius, width, 0, 250, 225, 0);
        Polygon triYe = new Polygon();
        triYe.getPoints().setAll((double)X,(double)Y,(double)(X+radius),(double)Y,(double)X,(double)(Y-radius));
        triYe.setFill(Color.rgb(250,225,0));

        Arc arcPu = createArc(X, Y, radius, width, 90, 144, 13, 255);
        Polygon triPu = new Polygon();
        triPu.getPoints().setAll((double)X,(double)Y,(double)(X-radius),(double)Y,(double)X,(double)(Y-radius));
        triPu.setFill(Color.rgb(144,13,255));

        Arc arcPi = createArc(X, Y, radius, width, 180, 251, 1, 129);
        Polygon triPi=new Polygon();
        triPi.getPoints().setAll((double)X,(double)Y,(double)(X-radius),(double)Y,(double)X,(double)(Y+radius));
        triPi.setFill(Color.rgb(255,1,129));

        Arc arcBl = createArc(X, Y, radius, width, 270, 50, 219, 240);
        Polygon triBl=new Polygon();
        triBl.getPoints().setAll((double)X,(double)Y,(double)(X+radius),(double)Y,(double)X,(double)(Y+radius));
        triBl.setFill(Color.rgb(50,219,240));

        Group subGroup=new Group();
        subGroup.getChildren().addAll(arcYe,arcPu,arcPi,arcBl,triYe,triPu,triPi,triBl);

        return subGroup;
    }

    public void pauseCall() {
        psd=true;
        for(int i=0;i<obstacle.size();i++) {
            obstacle.get(i).psd();
        }
    }

    public void playCall() {
        psd=false;
        for(int i=0;i<obstacle.size();i++) {
            obstacle.get(i).pld();
        }
    }

    public void buildCall() {
        for(int i=0;i<obstacle.size();i++) {
            obstacle.get(i).build();
        }
    }

    public void update() {
        pause=new Button();
        death=new Button();
        pause.setPrefSize(80, 80);
        pause.setStyle("-fx-background-color: #1b1b1b");
        ImageView x = new ImageView(new Image("Pause.png"));
        x.setFitHeight(80);
        x.setPreserveRatio(true);
        pause.setGraphic(x);
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 0, 0, 640));
        borderPane.setRight(pause);


        int screenX=960,screenY=900;

        Random random = new Random();
        selector = new Random();

        Group root = new Group();
        obsGroup = new Group();
        starGroup = new Group();
        switchGroup = new Group();
        upArrowGroup = new Group();
        downArrowGroup = new Group();

        star = new ArrayList<Polygon>();
        colorSwitch = new ArrayList<Node>();
        upArrow = new ArrayList<Node>();
        downArrow = new ArrayList<Node>();

        if(fresh==true) {
            invincible=0;
            fresh=false;
            granularity=20;
            suwi=false;
            score=0;
            d3d=false;
            psd=false;

            scoreDisp=new Text();
            scoreDisp.setText("0");
            scoreDisp.setFont(Font.font("Blissful Thinking",70));
            scoreDisp.setFill(Color.WHITE);
            scoreDisp.setX(90);
            scoreDisp.setY(73);

            obstacle = new ArrayList<Obstacle>();
            starPos=new ArrayList<Integer>();
            colorSwitchPos=new ArrayList<Integer>();
            upArrowPos=new ArrayList<Integer>();
            downArrorPos=new ArrayList<Integer>();

            ball = new Ball(random.nextInt(4)+1,screenX/2,800,0);

            ball.getBall().setCacheHint(CacheHint.SPEED);
            ball.getBall().setCache(true);

            obstacle.add(new Ring_Obstacle(screenX/2,screenY/2-100,200,30,1,6000));
            star.add(makeStar(screenX/2,screenY/2-100));
            starPos.add(screenY/2-100);
            if(suwi) {
                colorSwitch.add(makeColorSwitch(screenX/2,screenY/2-100-200-100));
                colorSwitchPos.add(screenY/2-100-200-100);
                suwi=false;
            }
            else {
                suwi=true;
            }

            obsGroup.getChildren().add(obstacle.get(0).getObstacle());
            starGroup.getChildren().add(star.get(0));
            if(colorSwitch.size()>0) {
                switchGroup.getChildren().add(colorSwitch.get(0));
            }
        }
        else {
            scoreDisp=new Text();
            if(score==0) {
                scoreDisp.setText("0");
            }
            else {
                scoreDisp.setText(Integer.toString(score));
            }
            scoreDisp.setFont(Font.font("Blissful Thinking",70));
            scoreDisp.setFill(Color.WHITE);
            scoreDisp.setX(90);
            scoreDisp.setY(73);

            buildCall();
            for(int i=0;i<obstacle.size();i++) {
                obsGroup.getChildren().add(obstacle.get(i).getObstacle());
            }
            for(int i=0;i<starPos.size();i++) {
                star.add(makeStar(screenX/2,starPos.get(i)));
                starGroup.getChildren().add(star.get(i));
            }
            for(int i=0;i<colorSwitchPos.size();i++) {
                colorSwitch.add(makeColorSwitch(screenX/2,colorSwitchPos.get(i)));
                switchGroup.getChildren().add(colorSwitch.get(i));
            }
            for(int i=0;i<upArrowPos.size();i++) {
                upArrow.add(makeUpArrow(screenX/2,upArrowPos.get(i)));
                upArrowGroup.getChildren().add(upArrow.get(i));
            }
            for(int i=0;i<downArrow.size();i++) {
                downArrow.add(makeUpArrow(screenX/2,downArrorPos.get(i)));
                downArrowGroup.getChildren().add(downArrow.get(i));
            }
            ball=new Ball(ball.getColor(),screenX/2,ball.getPosition(),ball.getVelocity());
        }

        root.getChildren().add(obsGroup);
        root.getChildren().add(starGroup);
        root.getChildren().add(switchGroup);
        root.getChildren().add(upArrowGroup);
        root.getChildren().add(downArrowGroup);
        root.getChildren().add(ball.getBall());
        root.getChildren().add(makeStar(50,50));
        root.getChildren().add(scoreDisp);
        root.getChildren().add(borderPane);

        Timeline moveBall=new Timeline(new KeyFrame(Duration.millis(granularity), actionEvent -> {
            if(psd==false) {
                if((colorSwitch.size()>0)&&(ball.getPosition()-25<=colorSwitch.get(0).getBoundsInParent().getMaxY())) {
                    switchGroup.getChildren().remove(0);
                    colorSwitch.remove(0);
                    colorSwitchPos.remove(0);
                    int temp=random.nextInt(4)+1;
                    if(temp==ball.getColor()) {
                        temp=(temp+1)%4+1;
                    }
                    ball.setColor(temp);
                }
                if((upArrow.size()>0)&&(ball.getPosition()-15<=upArrow.get(0).getBoundsInParent().getMaxY())) {
                    upArrowGroup.getChildren().remove(0);
                    upArrow.remove(0);
                    upArrowPos.remove(0);
                    ball.setVelocity(-100);
                }
                if((downArrow.size()>0)&&(ball.getPosition()-25<=downArrow.get(0).getBoundsInParent().getMaxY())) {
                    downArrowGroup.getChildren().remove(0);
                    downArrow.remove(0);
                    downArrorPos.remove(0);
                    ball.setVelocity(50);
                }
                if((star.size()>0)&&(ball.getPosition()-15<=star.get(0).getBoundsInParent().getMaxY())) {
                    starGroup.getChildren().remove(0);
                    star.remove(0);
                    starPos.remove(0);
                    score+=1;
                    scoreDisp.setText(Integer.toString(score));
                }
                int posn=obstacle.get(obstacle.size()-1).getPos();
                if(obstacle.get(0).getPos()>screenY+obstacle.get(0).getSize()) {
                    obstacle.get(0).cleanup();
                    obstacle.remove(0);
                    obsGroup.getChildren().remove(0);
                }
                if(obstacle.size()<3) {
                    int i=selector.nextInt(6);
                    int multiplier=(int)(1000/Math.pow(1.1,score/10));
                    if(i==0) {
                        obstacle.add(new Ring_Obstacle(screenX/2,posn-800,200,30,1,8*multiplier));
                        star.add(makeStar(screenX/2,posn-800));
                        starPos.add(posn-800);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-800-300));
                            colorSwitchPos.add(posn-800-300);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-800-300));
                                    upArrowPos.add(posn-800-300);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-800-300));
                                    downArrorPos.add(posn-800-300);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    else if(i==1) {
                        obstacle.add(new Square_Obstacle(screenX/2,posn-800,300,30,1,8*multiplier));
                        star.add(makeStar(screenX/2,posn-800));
                        starPos.add(posn-800);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-800-400));
                            colorSwitchPos.add(posn-800-400);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-800-400));
                                    upArrowPos.add(posn-800-400);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-800-400));
                                    downArrorPos.add(posn-800-400);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    else if(i==2) {
                        obstacle.add(new HorLine_Obstacle(screenX/2,posn-800,screenX/4,20,1,8*multiplier));
                        star.add(makeStar(screenX/2,posn-900));
                        starPos.add(posn-900);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-900-100));
                            colorSwitchPos.add(posn-900-100);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-900-100));
                                    upArrowPos.add(posn-900-100);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-900-100));
                                    downArrorPos.add(posn-900-100);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    else if(i==3) {
                        obstacle.add(new VerLine_Obstacle(screenX/2,posn-800,300,30,1,8*multiplier));
                        star.add(makeStar(screenX/2,posn-1050));
                        starPos.add(posn-1050);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-1050-100));
                            colorSwitchPos.add(posn-1050-100);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-1050-100));
                                    upArrowPos.add(posn-1050-100);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-1050-100));
                                    downArrorPos.add(posn-1050-100);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    else if(i==4) {
                        obstacle.add(new Fan_Obstacle(screenX/2-150,posn-800,200,30,-1,8*multiplier));
                        star.add(makeStar(screenX/2,posn-1000));
                        starPos.add(posn-1000);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-1000-100));
                            colorSwitchPos.add(posn-1000-100);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-1000-100));
                                    upArrowPos.add(posn-1000-100);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-1000-100));
                                    downArrorPos.add(posn-1000-100);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    else {
                        obstacle.add(new InfBall_Obstacle(screenX/2,posn-800,300,20,1,12*multiplier));
                        star.add(makeStar(screenX/2,posn-1050));
                        starPos.add(posn-1050);
                        if(suwi) {
                            colorSwitch.add(makeColorSwitch(screenX/2,posn-1050-100));
                            colorSwitchPos.add(posn-1050-100);
                            suwi=false;
                        }
                        else {
                            suwi=true;
                            if(score>cutoff) {
                                int r=random.nextInt(3);
                                if(r==0) {
                                    upArrow.add(makeUpArrow(screenX/2,posn-1050-100));
                                    upArrowPos.add(posn-1050-100);
                                    upArrowGroup.getChildren().add(upArrow.get(upArrow.size()-1));
                                }
                                else if(r==1) {
                                    downArrow.add(makeDownArrow(screenX/2,posn-1050-100));
                                    downArrorPos.add(posn-1050-100);
                                    downArrowGroup.getChildren().add(downArrow.get(downArrow.size()-1));
                                }
                            }
                        }
                    }
                    obsGroup.getChildren().add(obstacle.get(obstacle.size()-1).getObstacle());
                    starGroup.getChildren().add(star.get(star.size()-1));
                    if(!suwi) {
                        switchGroup.getChildren().add(colorSwitch.get(colorSwitch.size()-1));
                    }
                }
                if((ball.getVelocity()!=0)||(ball.getPosition()<800)) {
                    for(int i = 0;(i<obstacle.size())&&(!d3d)&&(invincible==0); i++) {
                        if(obstacle.get(i).isOut(ball)) {
                            death.fire();
                            d3d=true;
                        }
                    }
                    ball.setVelocity(ball.getVelocity()+granularity/5);
                    if(ball.getPosition()+ ball.getVelocity()*granularity/50<screenX/2) {
                        int temp=screenX/2-(ball.getPosition()+ ball.getVelocity()*granularity/50);
                        ball.setPosition(screenX/2);
                        for (Obstacle value : obstacle) {
                            value.setPos(temp);
                        }
                        for(int i=0;i<star.size();i++) {
                            star.get(i).setTranslateY(star.get(i).getTranslateY() + temp);
                            starPos.set(i,starPos.get(i)+temp);
                        }
                        for (int i=0;i<colorSwitch.size();i++) {
                            colorSwitch.get(i).setTranslateY(colorSwitch.get(i).getTranslateY() + temp);
                            colorSwitchPos.set(i,colorSwitchPos.get(i)+temp);
                        }
                        for (int i=0;i<upArrow.size();i++) {
                            upArrow.get(i).setTranslateY(upArrow.get(i).getTranslateY() + temp);
                            upArrowPos.set(i,upArrowPos.get(i)+temp);
                        }
                        for (int i=0;i<downArrow.size();i++) {
                            downArrow.get(i).setTranslateY(downArrow.get(i).getTranslateY() + temp);
                            downArrorPos.set(i,downArrorPos.get(i)+temp);
                        }
                    }
                    else {
                        ball.setPosition(ball.getPosition()+ ball.getVelocity()*granularity/50);
                    }
                    if((ball.getPosition()>=800)&&(ball.getVelocity()>=0)) {
                        ball.setVelocity(0);
                    }
                }
                for (Obstacle value : obstacle) {
                    value.animate(granularity);
                }
            }
            if(invincible>0) {
                if(invincible<granularity) {
                    invincible=0;
                }
                else {
                    invincible-=granularity;
                }
            }
        }));
        moveBall.setCycleCount(Timeline.INDEFINITE);
        moveBall.play();
        eventHandler= mouseEvent -> ball.setVelocity(-50);

        scene = new Scene(root,screenX,screenY,Color.rgb(27,27,27));
    }
}