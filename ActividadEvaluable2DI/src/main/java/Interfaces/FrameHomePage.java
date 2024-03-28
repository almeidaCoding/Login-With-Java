package Interfaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FrameHomePage extends JFrame {
    public FrameHomePage(){
        setTitle("Users");
        setBounds(400,80,320,570);

        PanelHomePage panelHomePage = new PanelHomePage();
        add(panelHomePage, BorderLayout.NORTH);

        PanelHomePageLogo panelHomePageLogo = new PanelHomePageLogo();
        add(panelHomePageLogo, BorderLayout.CENTER);

        PanelHomePageButtons panelHomePageButtons = new PanelHomePageButtons();
        add(panelHomePageButtons, BorderLayout.SOUTH);
    }
}

class PanelHomePage extends JPanel implements ActionListener{
    JLabel textWelcome1, textWelcome2;
    JButton exit;
    public PanelHomePage(){
        setLayout(new GridBagLayout());

        textWelcome1 = new JLabel("¡Hola!");
        textWelcome1.setFont(new Font("Calibri", Font.BOLD, 20));
        textWelcome1.setForeground(new Color(105, 125, 190));
        textWelcome2 = new JLabel("Registrate como usuario");
        textWelcome2.setFont(new Font("Calibri", Font.BOLD, 20));
        textWelcome2.setForeground(new Color(105, 125, 190));

        exit = new JButton("Salir");
        exit.setFont(new Font("Calibri", Font.PLAIN, 15));
        exit.setForeground(new Color(105, 125, 190));
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setBorder(BorderFactory.createEmptyBorder());
        exit.setFocusable(false);
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 220, 10, 10);
        add(exit, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 5, 10);
        add(textWelcome1, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(textWelcome2, gbc);

        setBackground(new Color(255, 255, 255));

        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            System.exit(0);
        }
    }
}

class PanelHomePageLogo extends JPanel{

    private Image imgLogo;

    public PanelHomePageLogo(){
        setLayout(new BorderLayout());

        try {

            imgLogo = ImageIO.read(new File
                    ("C:\\Users\\chpal\\OneDrive\\Escritorio\\Código\\ProgramM-AccessD\\ActividadEvaluable2DI\\src\\main\\java\\images\\imguser.jpg"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (imgLogo != null) {
            setPreferredSize(new Dimension(200, 200));
        }

        setBackground(new Color(255, 255, 255));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imgLogo != null) {
            int x = (getWidth() - getPreferredSize().width) / 2;
            int y = 40;

            g.drawImage(imgLogo, x, y, getPreferredSize().width, getPreferredSize().height, this);
        }
    }
}

class PanelHomePageButtons extends JPanel implements ActionListener {

    JButton login, register;

    public PanelHomePageButtons() {

        setLayout(new GridBagLayout());

        login = new JButton("Iniciar Sesión");
        propiedadesButtons(login);

        register = new JButton("Registrate");
        propiedadesButtons(register);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(login, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 60, 0);
        add(register, gbc);

        setBackground(new Color(255, 255, 255));

    }

    private void propiedadesButtons(JButton boton) {
        boton.setFont(new Font("Calibri", Font.PLAIN, 12));
        boton.setForeground(new Color(255, 255, 255));
        boton.setBackground(new Color(105, 125, 190));
        boton.setBorderPainted(true);
        boton.setPreferredSize(new Dimension(200, 30));
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setFocusable(false);
        boton.addActionListener(this);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register) {
            FrameRegister frameRegister = new FrameRegister();
            frameRegister.setVisible(true);
            frameRegister.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }else if(e.getSource() == login){
            FrameLogin frameLogin = new FrameLogin();
            frameLogin.setVisible(true);
            frameLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}
