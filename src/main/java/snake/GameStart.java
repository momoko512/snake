package snake;



/**
 * Created by $sherry on 2018/6/14.
 */
public class GameStart {
    static Thread t;
    static Game game;
    public static void main(String[] args) {
        game=new Game();
        game.make();
        t=new Thread(game.new Go());
        t.start();
            }
}
