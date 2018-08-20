package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created by $sherry on 2018/6/13.
 */
public class Game {
    JFrame frame;
    JLabel lable;
    MyDrawPanel drawpanel = new MyDrawPanel();
    static int score = 0;
    int x;
    int y = 300;
    int direction = 1;//right:1,left:2,up:3,down:4
    int sleep;
    int[] getfood;
    int[][] body;
    LinkedList<Node> list = new LinkedList<Node>();
    Node n;
    boolean islose = false;
    int ispause = 0;

    public void make() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        lable = new JLabel("score:" + score);
        panel.setLayout(new FlowLayout());
        panel.add(lable);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(605, 605);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(BorderLayout.CENTER, drawpanel);
        frame.add(BorderLayout.SOUTH, panel);
        putitcenter();
        frame.addKeyListener(new Key());
        frame.addMouseListener(new Mouse());
        frame.requestFocus();
        getfood = food();
    }

    class Key implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {//right:1,left:2,up:3,down:4
            if (direction == 1 || direction == 2) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    direction = 4;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    direction = 3;
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    direction = 2;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = 1;
                }
            }
            if (e.getKeyCode() == 32) {
                switch (ispause) {
                    case 0:
                        ispause = 1;
                        break;
                    case 1:
                        ispause = 0;
                        break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    class Mouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (islose) {
                islose = false;
                x = 0;
                y = 300;
                score = 0;
                list.clear();
                direction = 1;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public void putitcenter() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        frame.setLocation(screenWidth / 2 - 300, screenHeight / 2 - 300);
    }

    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.BLUE);
            if (ispause == 1) {
                g.drawString("暂停", 250, 250);
                g.setFont(new Font("TimesRoman", Font.BOLD, 32));
            } else {
                if (islose) {
                    g.drawString("最后得分" + score, 250, 250);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 32));
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        g.fillRect(list.get(i).getX(), list.get(i).getY(), 10, 10);
                        g.fillRect(getfood[0], getfood[1], 10, 10);
                    }
                }
            }
        }
    }

    public int[] food() {
        Random rand = new Random();
        int foodx = 10 * rand.nextInt(575 / 10 - 1);
        int foody = 10 * rand.nextInt(525 / 10 - 1);
        int[] foodxy = new int[]{foodx, foody};
        return foodxy;
    }

    class Go implements Runnable {
        @Override
        public void run() {//right:1,left:2,up:3,down:4
            while (true) {
                if (ispause == 1) {
                    drawpanel.repaint();
                }
                if (ispause == 0) {
                    if (x == getfood[0] && y == getfood[1]) {
                        score++;
                        lable.setText("score:" + score);
                        getfood = food();
                        switch (direction) {
                            case 1:
                                n = new Node(x + 10, y);
                                break;
                            case 2:
                                n = new Node(x - 10, y);
                                break;
                            case 3:
                                n = new Node(x, y - 10);
                                break;
                            case 4:
                                n = new Node(x, y + 10);
                                break;
                        }
                        list.add(n);
                    }
                    switch (direction) {
                        case 1:
                            x += 10;
                            break;
                        case 2:
                            x -= 10;
                            break;
                        case 3:
                            y -= 10;
                            break;
                        case 4:
                            y += 10;
                            break;
                    }
                    Node head = new Node(x, y);
                    list.add(head);
                    if (list.size() > 1) {
                        list.removeFirst();
                    }
                    if (list.getLast().getX() < 0 | list.getLast().getX() > 580 | list.getLast().getY() < 0 | list.getLast().getY() > 530)
                        islose = true;
                    for (int i = 0; i < list.size() - 2; i++) {
                        if (list.getLast().getX() == list.get(i).getX() && list.getLast().getY() == list.get(i).getY())
                            islose = true;
                    }
                    drawpanel.repaint();
                    sleep = 200 /(score + 1);
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
