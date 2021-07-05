import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    MainScreen mainScreen = new MainScreen();
    MainMenu mainMenu = new MainMenu();
    PauseMenu pauseMenu = new PauseMenu();
    DeathMenu deathMenu = new DeathMenu();
    LoadMenu loadMenu = new LoadMenu();
    Game game = new Game();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Colour Switch");
        mainScreen.update();
        mainMenu.update();
        stage.setScene(mainScreen.getScene());
        mainScreen.getPlayButton().setOnAction(e -> mainMenuScreen(stage));
        stage.show();
    }

    private void mainMenuScreen(Stage stage){
        mainMenu.update();
        mainMenu.getNewGameButton().setOnAction(e -> playGame(stage, false, false, 0));
        mainMenu.getLoadGameButton().setOnAction(e -> loadGame(stage));
        mainMenu.getExitButton().setOnAction(e -> stage.close());
        stage.setScene(mainMenu.getScene());
        stage.show();
    }

    private void loadGame(Stage stage){
        if(loadMenu.update()){
            loadMenu.getButton().setOnAction(e -> {
                int temp = -1;
                boolean f = false;
                try{
                    temp = Integer.parseInt(loadMenu.getTextField().getText());
                    f = true;
                }
                catch (Exception ignored){
                }
                int y = -1;
                FileInputStream x;
                try {
                    x = new FileInputStream("load.txt");
                    y = x.read();
                    x.close();
                } catch (Exception exception) {
//            e.printStackTrace();
                }
                if (y == -1)
                    y = 0;
                else
                    y -= 48;
                if(f){
                    if (temp >= 1 && temp <= y)
                        playGame(stage, true, false, temp);
                }
            });
        }
        else{
            loadMenu.getButton().setOnAction(e -> playGame(stage, false, false, 0));
        }
        game.update();
        stage.setScene(loadMenu.getScene());
    }

    private void playGame(Stage stage, boolean cont, boolean death, int num){
        if (!cont){
            game = new Game();
            game.update();
        }
        else{
            try {
                game=new Game();
                deserialize(num);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            game.update();
            game.setStuff();
            game.playCall();
        }
        if (death) {
            game.redScore();
            game.setStuff();
            game.playCall();
        }
        game.getDeathButton().setOnAction(e -> deathMenuScreen(stage));
        game.getPauseButton().setOnAction(e -> pauseMenuScreen(stage));
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, game.getEventHandler());
        stage.setScene(game.getScene());
        stage.show();
    }

    private void pauseMenuScreen (Stage stage) {
        try {
            serialize();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        game.getDeathButton().setOnAction(e -> System.out.println("Death in Pause Menu"));
        game.pauseCall();
        pauseMenu.update();
        pauseMenu.getSaveButton().setOnAction(e -> {
            try {
                saveGame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        pauseMenu.getResumeButton().setOnAction(e -> playGame(stage, true, false, 0));
        stage.setScene(pauseMenu.getScene());
        stage.show();
    }

    private void deathMenuScreen (Stage stage){
        try {
            serialize();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        game.pauseCall();
        deathMenu.update();
        if (game.getScore() >= 5)
            deathMenu.getResumeButton().setOnAction(e ->playGame(stage, true, true, 0));
        else
            deathMenu.getResumeButton().setOnAction(e -> System.out.println("Insufficient score"));
        deathMenu.getRestartButton().setOnAction(e -> playGame(stage, false, false, 0));
        deathMenu.getExitButton().setOnAction(e -> stage.close());
        stage.setScene(deathMenu.getScene());
        stage.show();
    }

    private void saveGame() throws Exception {
        try {
            File make=new File("load.txt");
            make.createNewFile();
        }
        catch(IOException e) {
            ;
        }
        FileInputStream x = new FileInputStream("load.txt");
        int y = x.read();
        y++;
        x.close();
        if (y == 0)
            y++;
        else
            y -= 48;
        FileOutputStream z = new FileOutputStream("load.txt");
        z.write(y+48);
        z.close();

        FileInputStream fileInputStream = new FileInputStream("out.txt");
        String s = "save" + y + ".txt";
        FileOutputStream fileOutputStream = new FileOutputStream(s);

        int temp;
        while ((temp = fileInputStream.read()) != -1)
            fileOutputStream.write(temp);

        fileInputStream.close();
        fileOutputStream.close();
    }

    private void serialize() throws Exception {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("out.txt"))) {
            outputStream.writeObject(game);
        }
    }

    private void deserialize(int num) throws Exception{
        if (num == 0) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("out.txt"))) {
                game = (Game) inputStream.readObject();
            }
        }
        else {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("save" + num + ".txt"))) {
                game = (Game) inputStream.readObject();
            }
        }
    }

    public static void main (String[] args){
        launch(args);
    }
}
