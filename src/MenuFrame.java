import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {
    public static final int MENUSIZE = 400;
    MenuFrame() {
        super("Меню");
        setSize(MENUSIZE, MENUSIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        JButton b = new JButton("Начать Новую Игру");
        b.setBounds(100, 200, 200, 100);
        MenuFrame menuFrame = this;
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JFrame frame = new MyFrame(menuFrame);
            }
        });
        add(b);
    }
}
