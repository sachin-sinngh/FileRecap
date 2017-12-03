

import javafx.scene.effect.DropShadow;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Created by SachinChauhan on 14-Sep-17.
 */
public class FirstScreen {
    private JButton selectFilesButton;
    private JPanel MainPanel;
    private JTextArea FileRecapTextArea;
    private JTextArea getSummaryOfATextArea;
    private JTextArea HowManySentences;
    private JFormattedTextField formattedTextField1;
    File infile;

    public FirstScreen() throws IOException {
        JFrame frame=new JFrame();
        frame.setContentPane(MainPanel);
        frame.getContentPane().setPreferredSize(new Dimension(700,500));
        HowManySentences.setVisible(false);
        formattedTextField1.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("Select Files");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Text File","txt");
        fileChooser.setFileFilter(filter);
        selectFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result=fileChooser.showOpenDialog(frame);
                if(result==JFileChooser.CANCEL_OPTION)
                    System.exit(2);

                selectFilesButton.setVisible(false);
                getSummaryOfATextArea.setVisible(false);
                infile=fileChooser.getSelectedFile();
                HowManySentences.setVisible(true);
                NumberFormat format = NumberFormat.getInstance();
                NumberFormatter formatter = new NumberFormatter(format);
                formatter.setValueClass(Integer.class);
                formatter.setMinimum(0);
                formatter.setMaximum(Integer.MAX_VALUE);
                formatter.setAllowsInvalid(false);
                formattedTextField1.setFormatterFactory(new DefaultFormatterFactory(formatter));
                formattedTextField1.setVisible(true);
            }
        });
        formattedTextField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int top_sentences=Integer.parseInt(formattedTextField1.getValue().toString());
                String summary="";
                try {
                    summary = new Trie().getSummary(infile,top_sentences);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.setVisible(false);
                new ResultScreen(summary);
            }
        });
    }
   public static void main(String[] args) throws IOException {
        new FirstScreen();
    }
}
