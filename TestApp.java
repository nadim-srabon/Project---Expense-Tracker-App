import java.util.Scanner;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.*;

public class TestApp extends JFrame {
    private Container container;
    private JTextField tf1, tf2;
    private JLabel label1, label2, label3, label4;
    private Font font, font2, font3;
    private JButton bt1, bt2;
	private  FileReader readDataFromFile;

	
	private HashMap<Date, Integer> weeklyData;

    TestApp() {
        initComponents();
    }

    public void initComponents() {
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 3);
        Border lineBorder2 = BorderFactory.createLineBorder(Color.BLUE, 3);

        font = new Font("Times New Roman", Font.PLAIN, 22);
        font2 = new Font("Arial", Font.PLAIN, 14);
        font3 = new Font("Arial", Font.BOLD, 22);

        label1 = new JLabel("Welcome to Expense Tracker");
        label1.setBounds(40, 10, 450, 50);
        label1.setFont(font3);

        label2 = new JLabel("Username");
        label2.setBounds(25, 100, 225, 50);
        label2.setFont(font);

        label3 = new JLabel("Password");
        label3.setBounds(25, 170, 225, 50);
        label3.setFont(font);

        label4 = new JLabel("Need an Account?");
        label4.setBounds(70, 380, 250, 25);
        label4.setFont(font2);

        tf1 = new JTextField();
        tf1.setBounds(150, 100, 200, 45);
        tf1.setBorder(lineBorder);

        tf2 = new JPasswordField();
        tf2.setBounds(150, 170, 200, 45);
        tf2.setBorder(lineBorder);

        bt1 = new JButton("LOGIN");
        bt1.setBounds(18, 250, 350, 45);
        Color customColor = new Color(0x1E88E5);
        bt1.setBackground(customColor);
        bt1.setBorder(lineBorder2);
        bt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = tf1.getText();
                String password = tf2.getText();
                boolean isLoggedIn = login(username, password);
                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Logged In");
					
					JFrame homeFrame = new JFrame("Home Page");
        homeFrame.setSize(400, 500);
        homeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel homePanel = new JPanel();
        homePanel.setLayout(null);

        JLabel title = new JLabel("Add Information");
        title.setBounds(150, 8, 150, 50);
        homePanel.add(title);
		
		JLabel label1 = new JLabel("Date");
        label1.setBounds(15, 75, 150, 50);
        homePanel.add(label1);
		
		JTextField tf1 = new JTextField("Format:23-12-2023");
		tf1.setBounds(180,75,150,30);
		homePanel.add(tf1);
		
		JLabel label2 = new JLabel("Amount");
        label2.setBounds(15, 125, 150, 50);
        homePanel.add(label2);
		
		JTextField tf2 = new JTextField();
		tf2.setBounds(180,125,150,30);
		homePanel.add(tf2);
		
		JLabel label3 = new JLabel("Category");
        label3.setBounds(15, 175, 150, 50);
        homePanel.add(label3);
		
		JTextField tf3 = new JTextField();
		tf3.setBounds(180,175,150,30);
		homePanel.add(tf3);
		
		JLabel label4 = new JLabel("Description");
        label4.setBounds(15, 225, 150, 50);
        homePanel.add(label4);
		
		JTextField tf4 = new JTextField();
		tf4.setBounds(180,225,150,80);
		homePanel.add(tf4);
		
		JLabel label5 = new JLabel("Budget Per Week");
		label5.setBounds(15,310,200,40);
		homePanel.add(label5);
		
		JTextField tf5 = new JTextField();
		tf5.setBounds(180,310,150,30);
		homePanel.add(tf5);
		
		JButton bt = new JButton("ADD");
		bt.setBounds(150,350,70,50);
		homePanel.add(bt);
		
		bt.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String data = tf1.getText() + "," + tf2.getText() + "," + tf3.getText() + "," +
                tf4.getText() + "," + tf5.getText();

        try {
            FileWriter writer = new FileWriter("info.txt", true);
            writer.write(data + "\n");
            writer.close();
            JOptionPane.showMessageDialog(homePanel, "Information added to info.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
});
		
		JButton bt5 = new JButton("View Previous Week Report");
		bt5.setBounds(80,410,250,40);
		homePanel.add(bt5);
		
		


 bt5.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame reportFrame = new JFrame("Visual Reports");
        reportFrame.setSize(600, 400);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel reportPanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        JTable dataTable = new JTable(tableModel);
        dataTable.getTableHeader().setReorderingAllowed(false); 
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        tableModel.addColumn("Category");
        tableModel.addColumn("Date");
        tableModel.addColumn("Amount");

        Map<String, Integer> categoryAmounts = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_MONTH, 31);

        String filePath = "info.txt";

         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String category = parts[2].trim(); 
                    Date date = dateFormat.parse(parts[0].trim());
                    int amount = Integer.parseInt(parts[1].trim());
                    
                    
                    if (isDateWithinRange(date, startDate.getTime(), endDate.getTime())) {
                        tableModel.addRow(new Object[]{category, dateFormat.format(date), amount});
                        
                        
                        categoryAmounts.merge(category, amount, Integer::sum);
                    }
                }
            }

            int rowCount = tableModel.getRowCount(); 
int rowIndex = 0; 

for (Map.Entry<String, Integer> entry : categoryAmounts.entrySet()) {
    tableModel.addRow(new Object[]{entry.getKey(), "", entry.getValue()});
    if (rowIndex == rowCount) { 
        tableModel.setValueAt("Total", rowIndex, 0); 
    }
    rowIndex++; 
}

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
		
		
        dataTable.setGridColor(Color.BLACK);
        dataTable.setShowGrid(true);
        dataTable.setRowHeight(30);
        dataTable.setFont(new Font("Arial", Font.PLAIN, 14));

        
        JTableHeader header = dataTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(dataTable);
        reportPanel.add(scrollPane, BorderLayout.CENTER);

        reportFrame.add(reportPanel);
        reportFrame.setVisible(true);
    }
});



		
		
		

        homeFrame.add(homePanel);
        homeFrame.setVisible(true);
} else {
					
					JOptionPane.showMessageDialog(null,"Wrong Password");
                }
            }
        });


        bt2 = new JButton("Sign Up");
        bt2.setBounds(190, 370, 100, 35);
        bt2.setBackground(customColor);
        bt2.setBorder(lineBorder2);
		bt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame signUpFrame = new JFrame("Sign Up");
                signUpFrame.setSize(400, 300);
                signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

                JPanel signUpPanel = new JPanel();
                signUpPanel.setLayout(null);

                JLabel userLabel = new JLabel("Username:");
                userLabel.setBounds(20, 20, 80, 25);
                signUpPanel.add(userLabel);

                JTextField userTextField = new JTextField();
                userTextField.setBounds(120, 20, 200, 25);
                signUpPanel.add(userTextField);

                JLabel passLabel = new JLabel("Password:");
                passLabel.setBounds(20, 50, 80, 25);
                signUpPanel.add(passLabel);

                JPasswordField passField = new JPasswordField();
                passField.setBounds(120, 50, 200, 25);
                signUpPanel.add(passField);

                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setBounds(20, 80, 80, 25);
                signUpPanel.add(emailLabel);

                JTextField emailTextField = new JTextField();
                emailTextField.setBounds(120, 80, 200, 25);
                signUpPanel.add(emailTextField);

                JLabel occupationLabel = new JLabel("Occupation:");
                occupationLabel.setBounds(20, 110, 80, 25);
                signUpPanel.add(occupationLabel);

                JTextField occupationTextField = new JTextField();
                occupationTextField.setBounds(120, 110, 200, 25);
                signUpPanel.add(occupationTextField);

                JButton signUpButton = new JButton("Sign Up");
                signUpButton.setBounds(150, 160, 100, 25);
                signUpPanel.add(signUpButton);

                signUpButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = userTextField.getText();
                        String password = new String(passField.getPassword());
                        String email = emailTextField.getText();
                        String occupation = occupationTextField.getText();
                        signUp(username, password, email, occupation);
                        signUpFrame.dispose(); 
                    }
                });

                signUpFrame.add(signUpPanel);
                signUpFrame.setVisible(true);
            }
        });
        

        container = getContentPane();
        container.setLayout(null);

        container.add(label1);
        container.add(label2);
        container.add(label3);
        container.add(tf1);
        container.add(tf2);
        container.add(label4);
        container.add(bt1);
        container.add(bt2);
    }
	
	

	
	private void signUp(String username, String password, String email, String occupation) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("userDatabase.txt", true))) {
            bw.write(username + "," + password + "," + email + "," + occupation);
            bw.newLine();
            bw.flush();
            JOptionPane.showMessageDialog(null, "Successfully Signed Up");
        } catch (IOException ex) {
            System.err.println("Error writing to user database: " + ex.getMessage());
        }
    }

    private boolean login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && userData[0].equals(username) && userData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading user database: " + ex.getMessage());
        }
        return false;
    }
	
	private boolean isDateWithinRange(Date date, Date startDate, Date endDate) {
    return !date.before(startDate) && !date.after(endDate);
}
	

 public static void main(String[] args) {
		
		
		TestApp frame = new TestApp();
		frame.setVisible(true);
		Color customColor = new Color(0x90CAF9);
		frame.getContentPane().setBackground(customColor);
		
        
   
       
       
        
    }
}