package Interfaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FrameFinishLogin extends JFrame {
    public FrameFinishLogin(){
        setTitle("Finish Register");
        setBounds(400,80,320,570);

        PanelFinishLogin panelFinishLogin = new PanelFinishLogin();
        add(panelFinishLogin, BorderLayout.NORTH);

        PanelFinishLoginLogo panelFinishLoginLogo = new PanelFinishLoginLogo();
        add(panelFinishLoginLogo, BorderLayout.CENTER);

        PanelFinishLog panelFinishLog = new PanelFinishLog();
        add(panelFinishLog, BorderLayout.SOUTH);
    }
}

class PanelFinishLogin extends JPanel {
    JLabel textFinish1, textFinish2;
    public PanelFinishLogin(){
        setLayout(new GridBagLayout());

        textFinish1 = new JLabel("¡Bienvenido!");
        textFinish1.setFont(new Font("Calibri", Font.BOLD, 15));
        textFinish1.setForeground(new Color(105, 125, 190));
        textFinish2 = new JLabel("Has iniciado sesión correctamente");
        textFinish2.setFont(new Font("Calibri", Font.BOLD, 15));
        textFinish2.setForeground(new Color(105, 125, 190));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(90, 10, 5, 10);
        add(textFinish1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(textFinish2, gbc);

        setBackground(new Color(255, 255, 255));
    }
}

class PanelFinishLoginLogo extends JPanel{

    private Image imgLogo;

    public PanelFinishLoginLogo(){
        setLayout(new BorderLayout());

        try {

            imgLogo = ImageIO.read(new File
                    ("C:\\Users\\chpal\\OneDrive\\Escritorio\\Código\\ProgramM-AccessD\\ActividadEvaluable2DI\\src\\main\\java\\images\\imguser.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (imgLogo != null) {
            setPreferredSize(new Dimension(250, 250));
        }

        setBackground(new Color(255, 255, 255));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imgLogo != null) {
            int x = (getWidth() - getPreferredSize().width) / 2;
            int y = 55;

            g.drawImage(imgLogo, x, y, getPreferredSize().width, getPreferredSize().height, this);
        }
    }
}

class PanelFinishLog extends JPanel{
    public PanelFinishLog(){
        setBackground(new Color(255, 255, 255));
    }
}
