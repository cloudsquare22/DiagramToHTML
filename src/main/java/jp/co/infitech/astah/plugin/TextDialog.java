package jp.co.infitech.astah.plugin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class TextDialog extends JDialog {
    public TextDialog(String text, Window frame) {
        super(frame);
        JLabel label = new JLabel("Exporting...");
        label.setFont(new Font(label.getFont().getFontName(), Font.PLAIN, 20));
        Border border = label.getBorder();
        Border margin = new EmptyBorder(16,16,16,16);
        label.setBorder(new CompoundBorder(border, margin));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(label, BorderLayout.CENTER);
        Rectangle frameBounds = frame.getBounds();
        this.setLocation(frameBounds.width / 2, frameBounds.height / 2);
        this.pack();
        this.setVisible(true);
    }

}
