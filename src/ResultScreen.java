import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by SachinChauhan on 03-Dec-17.
 */
public class ResultScreen {
    private JTextArea ResultTextArea;
    private JPanel ResultPanel;
    private JButton againButton;
    private JTextArea summaryTextaArea;
    private JScrollPane summaryScrollpane;

    public ResultScreen(String summary) {
        JFrame frame = new JFrame();
        frame.setContentPane(ResultPanel);
        frame.getContentPane().setPreferredSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        summaryTextaArea.setText(summary);
        againButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    new FirstScreen();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
