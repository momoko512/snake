package snake;

/**
 * Created by $sherry on 2018/6/14.
 */
public class Node {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y){
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Node(int x,int y){
        this.x=x;
        this.y=y;
    }
}
