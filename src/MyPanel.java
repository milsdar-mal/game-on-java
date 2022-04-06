import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static java.awt.event.KeyEvent.*;

public class MyPanel extends JPanel {
    public static final int CELLSIZE = 32;
    public static int noX, noY, appleNoX, appleNoY, level, colApple, k = 0, yEnd = 60;
    Hero mainHero = new Hero();
    boolean action = true, game = true, nGame;
    Pole pole = new Pole();
    Apple apple;
    public static String heroDirection;
    public static String wallDirection;
    public static String floorDirection;
    boolean robotOn = false, dialogueActivate = false, spareParts = false, dialogueExists = false;
    boolean robotKey = false, robotAnim = false, strangeNote = false, osn = false;
    int dialogueNo, rbAnimNo = 0;
    int iEnd = 0;
    MenuFrame menuFrame;
    MyFrame frame;
    List<String> frazes = new ArrayList<String>();
    MyPanel(MenuFrame menuFrame, MyFrame frame) {
        setLayout(null);
        setFocusable(true);
        requestFocus();
        setVisible(true);
        this.menuFrame = menuFrame;
        this.frame = frame;
        newGame();
        repaint();
        KeyListener Listener = new MyKey();
        addKeyListener(Listener);
        Thread robot = new npcThread();
        robot.start();
    }

    public void newGame () {
        noX = (MyFrame.WINDOWSIZE / CELLSIZE) / 2;
        noY = (MyFrame.WINDOWSIZE / CELLSIZE) / 2;
        mainHero.x = noX * CELLSIZE;
        mainHero.y = noY * CELLSIZE;
        Pole.Clearing();
        level = 2;
        levelReader("resources\\2level.txt");
        mainHero.Walk(0,0);
        heroDirection = "resources\\mainHeroDown.png";
        colApple = 0;
        dialogueNo = 0;
        game = false;
        nGame = true;
    }

    void addApple () {
        boolean b = true;
        Random random = new Random();
        int x;
        int y;
        do {
            b = false;
            x = (random.nextInt(25));
            y = (random.nextInt(25));
            if (Pole.lab[y][x][0] != 0) {
                b = true;
            }
        } while (b);
        appleNoX = x;
        appleNoY = y;
        apple = new Apple(x * CELLSIZE, y * CELLSIZE);
        repaint();
    }

    public void newLevel (boolean e) {
        Pole.Clearing();
        String path = "resources\\" + level + "level.txt";
        levelReader(path);
        if (e) {
            mainHero.x = 0;
            noX = 0;
        }
        if (!e) {
            mainHero.x = MyFrame.WINDOWSIZE - CELLSIZE;
            noX = MyFrame.WINDOWSIZE / CELLSIZE - 1;
        }
        repaint();
    }

    public void dialogue (String path) {
        try {
            FileReader fr = new FileReader(new File(path));
            BufferedReader reader = new BufferedReader(fr);
            String fraze;
            frazes.clear();
            while ((fraze = reader.readLine()) != null) {
                frazes.add(fraze);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game) {
            BufferedImage mainHeroIm = null;
            BufferedImage wallIm = null;
            BufferedImage wallToFloorIm = null;
            BufferedImage floorIm = null;
            BufferedImage appleIm = null;
            BufferedImage robotBrokenIm = null;
            BufferedImage dialogueFrameIm = null;
            BufferedImage sparePartsIm = null;
            BufferedImage robotKeyIm = null;
            BufferedImage rbAnim1Im = null;
            BufferedImage rbAnim2Im = null;
            BufferedImage robotAliveIm = null;
            BufferedImage strangeNoteIm = null;
            BufferedImage strangeNoteBigIm = null;
            try {
                mainHeroIm = ImageIO.read(new File(heroDirection));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                appleIm = ImageIO.read(new File("resources\\apple.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sparePartsIm = ImageIO.read(new File("resources\\spareParts.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                robotKeyIm = ImageIO.read(new File("resources\\robotKey.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                strangeNoteIm = ImageIO.read(new File("resources\\strangeNoteTexture.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                strangeNoteBigIm = ImageIO.read(new File("resources\\strangeNote.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < ((MyFrame.WINDOWSIZE / CELLSIZE)); i++) {
                for (int j = 0; j < ((MyFrame.WINDOWSIZE / CELLSIZE)); j++) {
                    switch (Pole.lab[i][j][0]) {
                        case 1: {
                            switch (Pole.lab[i][j][1]) {
                                case 0:
                                    wallDirection = "resources\\wall1.png";
                                    break;
                                case 1:
                                    wallDirection = "resources\\wall2.png";
                                    break;
                                case 2:
                                    wallDirection = "resources\\wall3.png";
                                    break;
                                case 3:
                                    wallDirection = "resources\\wall4.png";
                                    break;
                                case 4:
                                    wallDirection = "resources\\wall5.png";
                                    break;
                            }
                            try {
                                wallIm = ImageIO.read(new File(wallDirection));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(wallIm, (j * CELLSIZE), (i * CELLSIZE), this);
                            break;
                        }
                        case 2: {
                            try {
                                wallToFloorIm = ImageIO.read(new File("resources\\wallToFloor.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(wallToFloorIm, (j * CELLSIZE), (i * CELLSIZE), this);
                            break;
                        }
                        case 5: {
                            try {
                                robotBrokenIm = ImageIO.read(new File("resources\\robotBreak.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                rbAnim1Im = ImageIO.read(new File("resources\\robotAnim1.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                rbAnim2Im = ImageIO.read(new File("resources\\robotAnim2.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                robotAliveIm = ImageIO.read(new File("resources\\robotAlive.png"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            switch (rbAnimNo) {
                                case 0:
                                    g.drawImage(robotBrokenIm, (j * CELLSIZE), (i * CELLSIZE), this);
                                    break;
                                case 1:
                                    g.drawImage(rbAnim1Im, (j * CELLSIZE), (i * CELLSIZE), this);
                                    break;
                                case 2:
                                    g.drawImage(rbAnim2Im, (j * CELLSIZE), (i * CELLSIZE), this);
                                    break;
                                case 3:
                                    g.drawImage(robotAliveIm, (j * CELLSIZE), (i * CELLSIZE), this);
                                    break;
                            }
                            break;
                        }
                        case 4:
                        case 3:
                        case 0: {
                            switch (Pole.lab[i][j][1]) {
                                case 0:
                                    floorDirection = "resources\\floor1.png";
                                    break;
                                case 1:
                                    floorDirection = "resources\\floor2.png";
                                    break;
                                case 2:
                                    floorDirection = "resources\\floor3.png";
                                    break;
                            }
                            try {
                                floorIm = ImageIO.read(new File(floorDirection));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            g.drawImage(floorIm, (j * CELLSIZE), (i * CELLSIZE), this);
                            break;
                        }
                    }
                }
            }
            if (level == 2) {
                if ((noX != appleNoX) || (noY != appleNoY)) {
                    g.drawImage(appleIm, apple.getX(), apple.getY(), this);
                } else {
                    addApple();
                    colApple += 1;
                }
            }
            if ((level == 1) && (!spareParts)) {
                if ((noX != 5) || (noY != 5)) {
                    g.drawImage(sparePartsIm, 5 * CELLSIZE, 5 * CELLSIZE, this);
                } else {
                    spareParts = true;
                    dialogueNo = 2;
                }
            }
            if ((level == 1) && (!robotKey)) {
                if ((noX != 15) || (noY != 11)) {
                    g.drawImage(robotKeyIm, 15 * CELLSIZE, 11 * CELLSIZE, this);
                } else {
                    robotKey = true;
                    dialogueNo = 3;
                }
            }
            g.drawImage(mainHeroIm, mainHero.x, mainHero.y, this);
            g.setColor(Color.WHITE);
            g.fillRect(MyFrame.WINDOWSIZE - CELLSIZE, 0, CELLSIZE, CELLSIZE);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", 0, 20));
            g.drawString(colApple + "", MyFrame.WINDOWSIZE - CELLSIZE + 5, CELLSIZE - 10);
            if ((level == 1) && (!strangeNote)) {
                if ((noX != 14) || (noY != 19)) {
                    g.drawImage(strangeNoteIm, 14 * CELLSIZE, 19 * CELLSIZE, this);
                } else {
                    strangeNote = true;
                    g.drawImage(strangeNoteBigIm, 0, 0, this);
                    dialogueExists = true;
                }
            }
            if ((dialogueActivate) && (k <= frazes.size())) {
                switch (dialogueNo) {
                    case 1:
                        dialogue("resources\\dialogueBrokenRobot.txt");
                        break;
                    case 2:
                        dialogue("resources\\dialogueAliveRobot.txt");
                        break;
                    case 3:
                        dialogue("resources\\dialogueAliveRobot2.txt");
                        break;
                    case 4:
                        dialogue("resources\\dialogueRobot.txt");
                        break;
                    case 5:
                        dialogue("resources\\dialogueNoApple.txt");
                        break;
                    case 6:
                        dialogue("resources\\dialogueTheresApple.txt");
                        break;
                }
                try {
                    dialogueFrameIm = ImageIO.read(new File("resources\\dialogueFrame.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(dialogueFrameIm, 0, MyFrame.WINDOWSIZE - 200, this);
                g.setColor(Color.WHITE);
                int y = MyFrame.WINDOWSIZE - (CELLSIZE * 5);
                for (int i = 0; i < 4; i++) {
                    if ((i + k) >= (frazes.size())) {
                        if (dialogueNo == 3) {
                            robotAnim = true;
                        }
                        if (dialogueNo == 4) {
                            osn = true;
                        }
                        if (dialogueNo == 6) {
                            game = false;
                        }
                        break;
                    }
                    g.drawString(frazes.get(k + i), 32, y);
                    y += 32;
                }
                k += 4;
                dialogueExists = true;
            } else {
                dialogueExists = false;
                k = 0;
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,800,800);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", 0, 20));
            if (nGame) {
                dialogue("resources\\newGame.txt");
            } else {
                dialogue("resources\\End.txt");
                if (iEnd == 12) {
                    iEnd = 0;
                    yEnd = 60;
                }
            }
            g.drawString(frazes.get(iEnd), 32, yEnd);
            yEnd += 32;
            iEnd++;
            if (!nGame) {
                if (iEnd == frazes.size()) {
                    menuFrame.setVisible(true);
                    frame.dispose();
                }
            } else {
                if (iEnd == frazes.size()) {
                    game = true;
                    nGame = false;
                    addApple();
                }
            }
        }
    }

    boolean wallCheck (int a) {
        switch (a) {
            case 2: {
                if (((Pole.lab[noY + 1][noX][0] == 0) || (Pole.lab[noY + 1][noX][0] == 3) || (Pole.lab[noY + 1][noX][0] == 4)) && ((noY + 1) < (MyFrame.WINDOWSIZE / CELLSIZE))) {
                    if (Pole.lab[noY + 1][noX][0] == 3) {
                        level++;
                        newLevel(true);
                    }
                    if (Pole.lab[noY + 1][noX][0] == 4) {
                        level--;
                        newLevel(false);
                    }
                    return true;
                }
                break;
            }
            case 1: {
                if (((Pole.lab[noY][noX + 1][0] == 0) || (Pole.lab[noY][noX + 1][0] == 3) || (Pole.lab[noY][noX + 1][0] == 4)) && ((noX + 1) < (MyFrame.WINDOWSIZE / CELLSIZE))) {
                    if (Pole.lab[noY][noX + 1][0] == 3) {
                        level++;
                        newLevel(true);
                    }
                    if (Pole.lab[noY][noX + 1][0] == 4) {
                        level--;
                        newLevel(false);
                    }
                    return true;
                }
                break;
            }
            case 4: {
                if (((Pole.lab[noY - 1][noX][0] == 0) || (Pole.lab[noY - 1][noX][0] == 3) || (Pole.lab[noY - 1][noX][0] == 4)) && (noY > 0)) {
                    if (Pole.lab[noY - 1][noX][0] == 3) {
                        level++;
                        newLevel(true);
                    }
                    if (Pole.lab[noY - 1][noX][0] == 4) {
                        level--;
                        newLevel(false);
                    }
                    return true;
                }
                break;
            }
            case 3: {
                if (((Pole.lab[noY][noX - 1][0] == 0) || (Pole.lab[noY][noX - 1][0] == 3) || (Pole.lab[noY][noX - 1][0] == 4)) && (noX > 0)) {
                    if (Pole.lab[noY][noX - 1][0] == 3) {
                        level++;
                        newLevel(true);
                    }
                    if (Pole.lab[noY][noX - 1][0] == 4) {
                        level--;
                        newLevel(false);
                    }
                    return true;
                }
                break;
            }
            case 5: {
                if (((Pole.lab[noY][noX - 1][0] == 5) || (Pole.lab[noY][noX + 1][0] == 5) || (Pole.lab[noY - 1][noX][0] == 5) || (Pole.lab[noY + 1][noX][0] == 5)) && (!dialogueActivate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void levelReader (String path) {
        int i = 0;
        int j = 0;
        try {
            FileReader reader = new FileReader(new File(path));
            int c = 0;
            while(c != -1){
                c = reader.read();
                Random random = new Random();
                if(c == 13){
                    reader.read();
                    i++;
                    j = 0;
                }
                else if(c == -1){
                    break;
                }
                else{
                    int res = Integer.parseInt((char)c + "");
                    int b = 1;
                    Pole.lab[i][j][0] = res;
                    switch (res) {
                        case 0:
                            b = 3;
                            break;
                        case 1:
                            b = 5;
                            break;
                    }
                    Pole.lab[i][j][1] = random.nextInt(b);
                    j++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MyKey implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (action) {
                int newDir = e.getKeyCode();
                switch (newDir) {
                    case VK_RIGHT:
                        if ((wallCheck(1)) && (!dialogueExists) && (game)) {
                            heroDirection = "resources\\mainHeroRight.png";
                            mainHero.Walk(CELLSIZE, 0);
                            noX += 1;
                        }
                        break;
                    case VK_DOWN:
                        if ((wallCheck(2)) && (!dialogueExists) && (game)) {
                            heroDirection = "resources\\mainHeroDown.png";
                            mainHero.Walk(0, CELLSIZE);
                            noY += 1;
                        }
                        break;
                    case VK_LEFT:
                        if ((wallCheck(3)) && (!dialogueExists) && (game)) {
                            heroDirection = "resources\\mainHeroLeft.png";
                            mainHero.Walk(-CELLSIZE, 0);
                            noX -= 1;
                        }
                        break;
                    case VK_UP:
                        if ((wallCheck(4)) && (!dialogueExists) && (game)) {
                            heroDirection = "resources\\mainHeroUp.png";
                            mainHero.Walk(0, -CELLSIZE);
                            noY -= 1;
                        }
                        break;
                    case VK_Z:
                        if (wallCheck(5)) {
                            dialogueActivate = true;
                            if (dialogueNo == 0) {
                                dialogueNo = 1;
                            }
                            if (osn) {
                                if (colApple < 10) {
                                    dialogueNo = 5;
                                } else {
                                    dialogueNo = 6;
                                }
                            }
                        }
                        repaint();
                        break;
                }
                if (!dialogueExists) {
                    repaint();
                    action = false;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            action = true;
            if (e.getKeyCode() == VK_Z) {
                dialogueActivate = false;
            }
        }
    }

    private class npcThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                if ((level == 3) && (!robotOn) && (robotAnim)) {
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                    rbAnimNo++;
                    repaint();
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                    rbAnimNo++;
                    repaint();
                    try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                    rbAnimNo++;
                    repaint();
                    robotAnim = false;
                    robotOn = true;
                    dialogueNo = 4;
                }
            }
        }
    }
}