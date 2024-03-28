package Interfaces;

import database.GestionDB;
import database.SchemeDB;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Interfaces.PanelRegisterInputs.passwordField;
import static Interfaces.PanelRegisterInputs.userField;

public class FrameLogin extends JFrame {
    public  FrameLogin(){
        setTitle("Login");
        setBounds(400,80,320,570);

        PanelLogin panelLogin = new PanelLogin();
        add(panelLogin, BorderLayout.NORTH);

        PanelLoginInputs panelLoginInputs = new PanelLoginInputs();
        add(panelLoginInputs, BorderLayout.CENTER);

        PanelLoginButtons panelLoginButtons = new PanelLoginButtons();
        add(panelLoginButtons, BorderLayout.SOUTH);
    }
}

class PanelLogin extends JPanel implements ActionListener {
    private JLabel textLogin;
    private JButton buttonTextCancel;
    public PanelLogin() {
        setLayout(new GridBagLayout());

        MatteBorder textBorder = new MatteBorder(0, 0, 1, 0,
                new Color(105, 125, 190));
        textLogin = new JLabel("Login de usuarios");
        textLogin.setFont(new Font("Calibri", Font.BOLD, 15));
        textLogin.setForeground(new Color(105, 125, 190));
        textLogin.setBorder(textBorder);

        buttonTextCancel = new JButton("Cancelar");
        buttonTextCancel.setFont(new Font("Calibri", Font.PLAIN, 13));
        buttonTextCancel.setForeground(new Color(105, 125, 190));
        buttonTextCancel.setBorderPainted(false);
        buttonTextCancel.setContentAreaFilled(false);
        buttonTextCancel.setBorder(BorderFactory.createEmptyBorder());
        buttonTextCancel.setFocusable(false);
        buttonTextCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(40, 0, 30, 0);
        add(buttonTextCancel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 150);
        add(textLogin, gbc);

        buttonTextCancel.addActionListener(this);

        setBackground(new Color(255, 255, 255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonTextCancel) {
            int confirmResult = showCancelConfirmationDialog();

            if (confirmResult == JOptionPane.YES_OPTION) {
                FrameHomePage frameHomePage = new FrameHomePage();
                frameHomePage.setVisible(true);
            }
        }
    }

    private int showCancelConfirmationDialog() {
        Object[] options = {"Sí", "No"};
        int result = JOptionPane.showOptionDialog(
                this,
                "¿Seguro que quieres cancelar la operación?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return result;
    }
}

class PanelLoginInputs extends JPanel{
    static JTextField userFieldLogin;
    static JPasswordField passwordFieldLogin;
    private JLabel userLogin, passwordLogin;
    public PanelLoginInputs(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // JLabel y TextField de name
        userLogin = new JLabel("Usuario*");
        propertiesJLabel(userLogin);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLogin, gbc);

        userFieldLogin = new JTextField();
        propertiesJTextfield(userFieldLogin);
        gbc.gridx = 1;
        add(userFieldLogin, gbc);

        // JLabel y TextField de Surname
        passwordLogin = new JLabel("Contraseña*");
        propertiesJLabel(passwordLogin);
        gbc.gridx = 0;
        gbc.gridy++;
        add(passwordLogin, gbc);

        passwordFieldLogin = new JPasswordField();
        propertiesJTextfield(passwordFieldLogin);
        gbc.gridx = 1;
        add(passwordFieldLogin, gbc);

        setBackground(new Color(255, 255, 255));
    }

    public void propertiesJLabel (JLabel jLabel) {
        jLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        jLabel.setForeground(new Color(105, 125, 190));
    }

    public void propertiesJTextfield(JTextField jTextField) {
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(196, 196, 196));
        jTextField.setPreferredSize(new Dimension(160, 23));
        jTextField.setBackground(new Color(161, 170, 197));
        jTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField.setBorder(border);
    }
}

class PanelLoginButtons extends JPanel implements ActionListener{

    protected JButton login, clean;
    public PanelLoginButtons(){
        setLayout(new FlowLayout(FlowLayout.CENTER));

        login = new JButton("Entrar");
        clean = new JButton("Limpiar Campos");
        styleButtons(clean);
        styleButtons(login);
        add(clean);
        add(login);

        setBackground(new Color(255, 255, 255));
    }

    public void styleButtons(JButton button){
        button.setFont(new Font("Calibri", Font.PLAIN, 12));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(105, 125, 190));
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusable(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == login) {
            if(PanelLoginButtons.verificationUsersDataBase(PanelLoginInputs.userFieldLogin, PanelLoginInputs.passwordFieldLogin)){
                FrameFinishLogin frameFinishLogin = new FrameFinishLogin();
                frameFinishLogin.setVisible(true);
                frameFinishLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }else if (e.getSource() == clean){
            PanelLoginInputs.userFieldLogin.setText("");
            PanelLoginInputs.passwordFieldLogin.setText("");
        }
    }

    private static boolean verificationUsersDataBase(JTextField userFieldLogin, JPasswordField passwordFieldLogin){
        try {
            PreparedStatement preparedStatement = GestionDB.connection.prepareStatement(
                    String.format("SELECT %s, %s FROM %s WHERE %s =? AND %s =?",
                            SchemeDB.COLUM_USER, SchemeDB.COLUM_CONTRASENA, SchemeDB.TABLE_BD, SchemeDB.COLUM_USER, SchemeDB.COLUM_CONTRASENA));
            preparedStatement.setString(1, userFieldLogin.getText());
            preparedStatement.setString(2, passwordFieldLogin.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();
            preparedStatement.close();

            if (!exists) {
                JOptionPane.showMessageDialog(null, "El usuario no está registrado.", "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
            }

            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
