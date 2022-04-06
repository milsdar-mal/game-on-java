import javax.swing.*;

public class MyFrame extends JFrame {
    public static final int WINDOWSIZE = 800;
    MyFrame(MenuFrame menuFrame) {
        super("Adventure");
        setSize(WINDOWSIZE + 6, WINDOWSIZE + 29);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        MyFrame frame = this;
        JPanel panel = new MyPanel(menuFrame, frame);
        panel.setSize(WINDOWSIZE, WINDOWSIZE);
        add(panel);
    }
}