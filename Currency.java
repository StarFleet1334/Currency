import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Currency extends JFrame {
    JTextField amount,sign;
    JComboBox<String> from,to;
    JTextField result;
    private String firstName;
    private ArrayList<String> userNames;
    private String firstChosen = "";
    private String secondChose = "";
    private JButton convert;
    private JTextArea area;


    public Currency(){
        userNames = new ArrayList<>();
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }  catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Container container = getContentPane();

        //Top Panel
        JPanel firstPanel = new JPanel();
        sign = new JTextField("₾",3);
        firstPanel.add(sign);
        firstPanel.setLayout(new FlowLayout(0,3,3));
        firstPanel.setBorder(BorderFactory.createTitledBorder("Converter"));
        firstPanel.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,12));
        firstPanel.add(new JLabel("Amount"));
        amount = new JTextField("1.00",10);
        firstPanel.add(amount);
        amount.setEditable(true);
        amount.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,12));
        firstPanel.add(new JLabel("From"));
        String[] countries = {"Georgia","America","Russia","Euro"};
        //From
        from = new JComboBox<>(countries);
        from.setForeground(Color.blue);
        firstPanel.add(from);
        firstPanel.add(new JLabel("To"));
        //To
        to = new JComboBox<>(countries);
        to.setForeground(Color.blue);
        firstPanel.add(to);

         convert = new JButton("Convert");
        firstPanel.add(convert);

        JButton reset = new JButton("Reset");
        firstPanel.add(reset);

        FirstComboBoxListener listener = new FirstComboBoxListener();
        from.addItemListener(listener);

        SignChange signChange = new SignChange();
        from.addItemListener(signChange);



        //Top Panel
        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JLabel text = new JLabel();
        String txt = "Mid-market exchange rate - last updated " + Calendar.getInstance().getTime();
        text.setText(txt);
        text.setFont(new Font(Font.SERIF,Font.BOLD,10));
        text.setToolTipText("These are derived from the mid-point between the \"buy\" and \"sell\" transactional rates from global currency markets.They are not transactional rates.");
        bottomPanel.add(text);
        //Bottom Panel

        //Menu
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        //First Menu
        FirstMenuItem firstMenuItem = new FirstMenuItem();
        menu = new JMenu("Profile");
        menuItem = new JMenuItem("Sign-in");
        menuItem.addActionListener(firstMenuItem);
        menu.add(menuItem);
        menuBar.add(menu);

        //Second Menu
        SecondMenuItem secondMenuItem = new SecondMenuItem();
        menu = new JMenu("New account");
        menuItem = new JMenuItem("Register");
        menuItem.addActionListener(secondMenuItem);
        menu.add(menuItem);
        menuBar.add(menu);

        firstPanel.setBackground(new Color(123,213,91));

        //Convert Button
        Convert listenButton = new Convert();
        convert.addActionListener(listenButton);


        //Center
         area = new JTextArea();
        area.setFont(new Font(Font.SERIF,Font.ITALIC,13));
        area.setLineWrap(true);
        area.setEditable(false);
        area.setBackground(Color.lightGray);
        area.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //Reset Button to clear Messages in area
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                amount.setText(1.00 + "");
                if(from.getSelectedItem() == "Georgia"){
                    sign.setText("₾");
                } else if(from.getSelectedItem() == "Russia"){
                    sign.setText("RUB");
                } else if(from.getSelectedItem() == "America"){
                    sign.setText("$");
                } else if(from.getSelectedItem() == "Euro"){
                    sign.setText("€");
                }
            }
        });
        container.setLayout(new BorderLayout());
        container.add(firstPanel,BorderLayout.PAGE_START);
        container.add(scrollPane,BorderLayout.CENTER);
        container.add(bottomPanel,BorderLayout.PAGE_END);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("The World's Trusted Currency Authority");
        setSize(650,300);
        setVisible(true);
    }



    private  class FirstMenuItem implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String input;
             input = JOptionPane.showInputDialog(null,"Sign-in (Please Enter your name)","Sign-in",JOptionPane.PLAIN_MESSAGE);
            if (!userNames.contains(input)) {
                while (true) {
                    input = JOptionPane.showInputDialog(null, "This userName doesn't exist,Please Enter real name", "Problem", JOptionPane.PLAIN_MESSAGE);
                    if(userNames.contains(input)){
                        break;
                    }
                }
            }
            JOptionPane.showMessageDialog(null,"Welcome back " + input,"Congratulation",JOptionPane.PLAIN_MESSAGE);
        }
    }
    private class SecondMenuItem implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(null,"Enter userName: (To Register)","Register",JOptionPane.PLAIN_MESSAGE);
            if (!userNames.contains(name)) {
                userNames.add(name);
                JOptionPane.showMessageDialog(null,"Welcome " + name + "(User called: " + name + " Registered, Current Date: " +
                        Calendar.getInstance().getTime() + ")","Greeting",JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"User " + name + " is already registered");
            }
        }
    }

    private class FirstComboBoxListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
        }
    }
    private class Convert implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(from.getSelectedItem() == "Georgia" && to.getSelectedItem() == "America"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer =(firstNumber * 0.30);
                area.append(firstNumber + " Georgian Lari = \n");
                area.append(answer + " US Dollars\n");
                area.append(firstNumber + " GEL = " + answer + " USD");
            } else if(from.getSelectedItem()  == "Georgia" && to.getSelectedItem() == "Russia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer =(firstNumber * 22.33);
                area.append(firstNumber + " Georgian Lari = \n");
                area.append(answer + " RUBL\n");
                area.append(firstNumber + " GEL = " + answer + " RUBL");
            } else if(from.getSelectedItem()  == "Georgia" && to.getSelectedItem() == "Euro"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber * 0.25;
                area.append(firstNumber + " Georgian Lari = \n");
                area.append(answer + " Euro\n");
                area.append(firstNumber + " Lari = " + answer + " Euro");
            } else if(from.getSelectedItem()  == "America" && to.getSelectedItem() == "Georgia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber*3.2900;
                area.append(firstNumber + " US Dollars = \n");
                area.append(answer + " Georgian Lari\n");
                area.append(firstNumber + " US = " + answer + " Lari");
            } else if(from.getSelectedItem()  == "America" && to.getSelectedItem() == "Russia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber * 73.79;
                area.append(firstNumber + " US Dollars = \n");
                area.append(answer + " RUBL\n");
                area.append(firstNumber + " US = " + answer + " RUBL");
            } else if(from.getSelectedItem()  == "America" && to.getSelectedItem() == "Euro"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber * 0.83;
                area.append(firstNumber + " US Dollars = \n");
                area.append(answer + " Euro\n");
                area.append(firstNumber + " US = " + answer + " Euro");
            } else if(from.getSelectedItem()  == "Russia" && to.getSelectedItem() == "Georgia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber*0.045;
                area.append(firstNumber + " RUBL = \n");
                area.append(answer + " Georgian Lari\n");
                area.append(firstNumber + " RUBL = " + answer + " Lari");
            } else if(from.getSelectedItem()  == "Russia" && to.getSelectedItem() == "America"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber*0.014;
                area.append(firstNumber + " RUBL = \n");
                area.append(answer + " US Dollars\n");
                area.append(firstNumber + " RUBL = " + answer + " US");
            } else if(from.getSelectedItem()  == "Russia" && to.getSelectedItem() == "Euro"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber*0.011;
                area.append(firstNumber + " RUBL = \n");
                area.append(answer + " Euro\n");
                area.append(firstNumber + " RUBL = " + answer + " Euro");
            } else if(from.getSelectedItem()  == "Euro" && to.getSelectedItem() == "Georgia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber*3.9540;
                area.append(firstNumber + " Euro = \n");
                area.append(answer + " Georgian Lari\n");
                area.append(firstNumber + " Euro = " + answer + " Lari");
            } else if(from.getSelectedItem()  == "Euro" && to.getSelectedItem() == "America"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber * 1.20;
                area.append(firstNumber + " Euro = \n");
                area.append(answer + " US Dollars\n");
                area.append(firstNumber + " Euro = " + answer + " US");
            } else if(from.getSelectedItem()  == "Euro" && to.getSelectedItem() == "Russia"){
                int firstNumber;
                if(amount.getText().contains(".")){
                    firstNumber = (int)Double.parseDouble(amount.getText());
                } else {
                    firstNumber = Integer.parseInt(amount.getText());
                }
                double answer = firstNumber * 88.87;
                area.append(firstNumber + " Euro = \n");
                area.append(answer + " RUBL\n");
                area.append(firstNumber + " Euro = " + answer + " RUBL");
            }
        }
    }
    private class SignChange implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(from.getSelectedItem() == "Georgia"){
                sign.setText("₾");
            } else if(from.getSelectedItem() == "Russia"){
                sign.setText("RUB");
            } else if(from.getSelectedItem() == "America"){
                sign.setText("$");
            } else if(from.getSelectedItem() == "Euro"){
                sign.setText("€");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Currency();
            }
        });
    }
}
