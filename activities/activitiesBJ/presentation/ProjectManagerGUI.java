package presentation; 
 
import domain.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * @version ECI 2024
 */
public class ProjectManagerGUI extends JFrame{
    private static final int PREFERRED_WIDTH = 700;
    private static final int PREFERRED_HIGH= 700;
    private static final Dimension PREFERRED_DIMENSION =
                         new Dimension(PREFERRED_WIDTH,PREFERRED_HIGH);
    private Project project;
    private Log log;
    
    /*List*/
    private JButton buttonList;
    private JButton buttonRestartList;
    private JTextArea textDetails;
    
    /*Add*/
    private JTextField name;   
    private JTextField time;
    private JTextField cost;
    private JTextArea  basics;
    private JButton buttonAdd;
    private JButton buttonRestartAdd;
    
    /*Search*/
    private JTextField textSearch;
    private JTextArea textResults;
    private JButton buttonSearch;
    private JButton buttonRestartClear;
    
    private ProjectManagerGUI() throws ProjectException {
        project=new Project();
        prepareElements();
        prepareActions();
    }

    private void prepareElements(){
        setTitle("Gestor de Proyectos");
        name = new JTextField(50);
        time = new JTextField(50);
        cost = new JTextField(50);
        basics = new JTextArea(10, 50);
        basics.setLineWrap(true);
        basics.setWrapStyleWord(true);
        
        JTabbedPane labels = new JTabbedPane();
        labels.add("Listar",   prepareAreaList());
        labels.add("Adicionar",  prepareAreaAdd());
        labels.add("Buscar", prepareSearchArea());
        add(labels);
        setSize(PREFERRED_DIMENSION);
    }


    /**
     * Prepare area list 
     * @return 
     */
    private JPanel prepareAreaList(){

        textDetails = new JTextArea(10, 50);
        textDetails.setEditable(false);
        textDetails.setLineWrap(true);
        textDetails.setWrapStyleWord(true);
        JScrollPane scrollArea =
                new JScrollPane(textDetails,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                
        JPanel  botones = new JPanel();
        buttonList = new JButton("Listar");
        buttonRestartList = new JButton("Limpiar");
        botones.add(buttonList);
        botones.add(buttonRestartList);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollArea, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
     }
     
    /**
     * Prepare area add
     * @return 
     */
    private JPanel prepareAreaAdd(){
            
        Box fields = Box.createVerticalBox();
        fields.add(new JLabel("Nombre", JLabel.LEFT));
        fields.add(name);
        fields.add(new JLabel("Duracion (en horas) o Tipo (Paralela o Secuencial)", JLabel.LEFT));       
        fields.add(time); 
        fields.add(add(new JLabel("Costo", JLabel.LEFT)));
        fields.add(cost);
        fields.add(new JLabel("Actividades", JLabel.LEFT));
        
        
        JPanel textDetailsPanel = new JPanel();
        textDetailsPanel.setLayout(new BorderLayout());
        textDetailsPanel.add(fields, BorderLayout.NORTH);
        textDetailsPanel.add(basics, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        buttonAdd = new JButton("Adicionar");
        buttonRestartAdd = new JButton("Limpiar");

        botones.add(buttonAdd);
        botones.add(buttonRestartAdd);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textDetailsPanel, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }
    
    /**
     * Prepare area search
     * @return 
     */
    private JPanel prepareSearchArea(){

        Box search = Box.createHorizontalBox();
        search.add(new JLabel("Buscar", JLabel.LEFT));
        textSearch = new JTextField(50);
        search.add(textSearch);
        JPanel botones = new JPanel();
        buttonSearch = new JButton("Buscar");
        botones.add(buttonSearch);
        buttonRestartClear = new JButton("Limpiar");
        botones.add(buttonRestartClear);
        textResults = new JTextArea(10,50);
        textResults.setEditable(false);
        textResults.setLineWrap(true);
        textResults.setWrapStyleWord(true);
        JScrollPane scrollArea = new JScrollPane(textResults,
                                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(search, BorderLayout.NORTH);
        panel.add(scrollArea, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }


    private void prepareActions(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                setVisible(false);
                System.exit(0);
            }
        });
        
        /*List*/
        buttonList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                actionList();
            }
        });

        buttonRestartList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                textDetails.setText("");
            }
        });
        
        /*Add*/
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                actionAdd();                    
            }
        });
        
        buttonRestartAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                name.setText("");
                time.setText("");
                cost.setText("");
                basics.setText("");
            }
        });
        
        /*Search*/
        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                actionSearch();
            }
        });
        
        buttonRestartClear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                textSearch.setText("");
            }
        });
        
        /*
        textSearch.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent ev){
                actionSearch();
            }
           
            public void insertUpdate(DocumentEvent ev){
                actionSearch();
            }
            
            public void removeUpdate(DocumentEvent ev){
                actionSearch();
            }
        });
        */
    }    

    /**Execute action list**/
    private void actionList(){
        textDetails.setText(project.toString());
    }
    /**Execute action add**/
    private void  actionAdd(){
        try{
            project.add(name.getText().trim(),cost.getText().trim(),time.getText().trim(), basics.getText().trim());
        }
        catch(ProjectException e){
            if(e.getMessage().equals(ProjectException.NAME_ALREADY_USED)){
                JOptionPane.showMessageDialog(this, "An error occurred during the addition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else if(e.getMessage().equals(ProjectException.COST_AND_TIME_ARE_NOT_NUMBERS)){
                JOptionPane.showMessageDialog(this, "An error occurred during the addition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if((e.getMessage().equals(ProjectException.THE_SUBACTIVITY_NOT_EXISTS))){
                JOptionPane.showMessageDialog(this, "An error occurred during the addition: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "An error occurred, we are sorry: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                log.record(e);
            }
        }
    }
    /**Execute action search**/
    private void actionSearch(){
        try{
            String pattern = textSearch.getText().toString();
            String answer = "";
            if(pattern.length() > 0) {
                answer = project.search(pattern);
            }
            textResults.setText(answer);
        }
        catch(Exception e){
            if(e.getMessage().equals(ProjectException.ACTIVITY_NOT_FOUND)){
                JOptionPane.showMessageDialog(this, "An error occurred during the searching: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "An error occurred, we are sorry: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                log.record(e);
            }
        }
    }
    
    public static void main(String args[]) throws ProjectException {
       ProjectManagerGUI gui=new ProjectManagerGUI();
       gui.setVisible(true);
    }    
}