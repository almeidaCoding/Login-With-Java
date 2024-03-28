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

public class FrameRegister extends JFrame {
    public FrameRegister(){
        setTitle("Register Users");
        setBounds(400,80,320,570);

        PanelRegister panelRegister = new PanelRegister();
        add(panelRegister, BorderLayout.NORTH);

        PanelRegisterButton panelRegisterButton = new PanelRegisterButton();
        add(panelRegisterButton, BorderLayout.SOUTH);

        PanelRegisterInputs panelRegisterInputs = new PanelRegisterInputs();
        add(panelRegisterInputs, BorderLayout.CENTER);
    }
}

class PanelRegister extends JPanel implements ActionListener {

    private JLabel textRegister;
    private JButton buttonTextCancel;


    public PanelRegister() {
        setLayout(new GridBagLayout());

        MatteBorder textBorder = new MatteBorder(0, 0, 1, 0,
                new Color(105, 125, 190));
        textRegister = new JLabel("Registro de usuarios");
        textRegister.setFont(new Font("Calibri", Font.BOLD, 15));
        textRegister.setForeground(new Color(105, 125, 190));
        textRegister.setBorder(textBorder);

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
        add(textRegister, gbc);

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

class PanelRegisterInputs extends JPanel{
    static JTextField userField;
    static JPasswordField passwordField;
    private JLabel user, password;
    public PanelRegisterInputs(){

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // JLabel y TextField de name
        user = new JLabel("Usuario*");
        propertiesJLabel(user);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(user, gbc);

        userField = new JTextField();
        propertiesJTextfield(userField);
        gbc.gridx = 1;
        add(userField, gbc);

        // JLabel y TextField de Surname
        password = new JLabel("Contraseña*");
        propertiesJLabel(password);
        gbc.gridx = 0;
        gbc.gridy++;
        add(password, gbc);

        passwordField = new JPasswordField();
        propertiesJTextfield(passwordField);
        gbc.gridx = 1;
        add(passwordField, gbc);

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

class PanelRegisterButton extends JPanel implements ActionListener{

    protected JButton register, clean;

    public PanelRegisterButton() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        register = new JButton("Registrarse");
        clean = new JButton("Limpiar Campos");
        styleButtons(clean);
        styleButtons(register);
        add(clean);
        add(register);

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
        if(e.getSource() == register) {
            if(PanelRegisterButton.insertUserDataBase(userField, PanelRegisterInputs.passwordField)){
                FrameFinishRegister frameFinishRegister = new FrameFinishRegister();
                frameFinishRegister.setVisible(true);
                frameFinishRegister.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }else if (e.getSource() == clean){
            userField.setText("");
            passwordField.setText("");
        }
    }

    public static boolean insertUserDataBase(JTextField userField, JPasswordField passwordField) {
        try {

            //Reinicia el AUTOINCREMENT a 1
            PreparedStatement alterTable = GestionDB.connection.prepareStatement(
                    String.format("ALTER TABLE %s AUTO_INCREMENT = 1", SchemeDB.TABLE_BD));
            alterTable.executeUpdate();
            alterTable.close();

            if(userExists(userField.getText())){
                showMessageExists();
                return false;
            }

            PreparedStatement preparedStatement = GestionDB.connection.prepareStatement(
                    String.format("INSERT IGNORE INTO %s (%s,%s) VALUES (?,?)",
                            SchemeDB.TABLE_BD, SchemeDB.COLUM_USER, SchemeDB.COLUM_CONTRASENA));
            preparedStatement.setString(1, userField.getText());
            preparedStatement.setString(2, passwordField.getText());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showMessageExists() {
        JOptionPane.showMessageDialog(null,
                "El usuario ya está registrado",
                "Usuario registrado",
                JOptionPane.WARNING_MESSAGE);
    }

    private static boolean userExists(String username){
        try {
            PreparedStatement preparedStatement = GestionDB.connection.prepareStatement(
                    String.format("SELECT %s FROM %s WHERE %s =?",
                            SchemeDB.COLUM_USER, SchemeDB.TABLE_BD, SchemeDB.COLUM_USER));
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean exists = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
