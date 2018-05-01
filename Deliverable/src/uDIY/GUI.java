package uDIY;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The GUI handler of the program
 * 
 * @author Matthew Hwang, Parker Olive
 * @version 12/4/2017
 */
public class GUI {
	
	private String projectName = "";
	private boolean saved = true;
	
	// Data class
	private Data data;
	
	// FileIO
	private final FileIO fileHandler;
	
	// The outer frame that holds everything
    private final JFrame outerFrame;
    
    // Menu bar and menus
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu helpMenu;
    
    // JFileChooser
    private final JFileChooser chooser;
    private File file;

    // File Menu Buttons
    private final JMenuItem newButtonMenu;
    private final JMenuItem openButtonMenu;
    private final JMenuItem saveButtonMenu;
    private final JMenuItem exportButtonMenu;
    private final JMenuItem exitButtonMenu;
    
    // Help Menu Buttons
    private final JMenuItem aboutButton;
    
    // Pages and buttons
    // Main Page
    private final JPanel mainPage;
    private final JButton newButtonPage;
    private final JButton openButtonPage;
    // Edit Page
    private final JPanel editPage;
    private final JLabel editTitle;
    private final JScrollPane scrollPane;
	private final JPanel energyPanel;
	private final JPanel itemPanel;
	ArrayList<Energy> energyList;
	ArrayList<Item> itemList;
	// Results Page
	private final JPanel resultsPage;
	private final JLabel resultsTitle;
	private final JLabel resultsEnergy;
	private final JLabel resultsCost;
	private final JLabel resultsTime;
	private final JPanel resultsPanel;

	private double totalCost;
	private double breakEven;
	
	private Graph graph;
	
    /**
     * Constructor
     * @author Matthew Hwang
     * @version 12/5/2017
     */
    public GUI() {
    	
    	// Data Reference
    	data = new Data();
    	
    	// FileIO
    	fileHandler = new FileIO();
    	
    	outerFrame = new JFrame("uDIY");
    	outerFrame.setLayout(new BorderLayout());
    	outerFrame.getContentPane().setBackground(Color.white);
    	// Menu bar and menus
    	menuBar = new JMenuBar();
    	fileMenu = new JMenu("File");
    	helpMenu = new JMenu("Help");
    	
    	// Menu buttons
    	// File
    	newButtonMenu = new JMenuItem("New Project");
    	openButtonMenu = new JMenuItem("Open Project");
    	saveButtonMenu = new JMenuItem("Save Project");
    	exportButtonMenu = new JMenuItem("Export Project");
    	exitButtonMenu = new JMenuItem("Exit Program");
    	// Help
    	aboutButton = new JMenuItem("About");
    	
    	// JFileChooser
    	chooser = new JFileChooser();
    	file = new File("");
    	
    	// Pages and Buttons
    	// Main Page
    	mainPage = new JPanel();
    	mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));
    	newButtonPage = new JButton("New Project");
    	openButtonPage = new JButton("Open Existing Project");
    	// Edit Page
    	editPage = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	editPage.setPreferredSize(new Dimension(680, 535));
    	scrollPane = new JScrollPane(editPage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    	editTitle = new JLabel();
    	energyPanel = new JPanel(new GridLayout(0, 2, 40, 0));
    	itemPanel = new JPanel(new GridLayout(0, 3, 40, 0));
    	energyList = new ArrayList<Energy>();
    	itemList = new ArrayList<Item>();
		// Results Page
		resultsPage = new JPanel(new FlowLayout());
		resultsTitle = new JLabel();
		resultsEnergy = new JLabel();
		resultsCost = new JLabel();
		resultsTime = new JLabel();
		resultsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		// Graph
		graph = new Graph();
    	
        setup();
    }

    /**
     * Method that prepares the GUI for use
     * @author Matthew Hwang
     * @version 12/4/2017
     */
    private void setup() {
    	createMenuBar();
    	createMainPage();
    	createEditPage();
    	createResultsPage();
    	
    	// Making the program closable
        outerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        outerFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                exit();
            }
        });
        
        // Preventing frame resize
        outerFrame.setResizable(false);  
    	// Disabling buttons and menus at startup
    	saveButtonMenu.setEnabled(false);
    	exportButtonMenu.setEnabled(false); 
    	// Add pages to the frame
    	outerFrame.add(mainPage);
    	// Adds the JMenuBar to the frame
        outerFrame.setJMenuBar(menuBar);    	
        // Sets minimum size of the frame
        outerFrame.setMinimumSize(new Dimension(700, 600)); 
        
        // Makes the frame fit the contents
        outerFrame.pack();
    	// Centers the frame
        outerFrame.setLocationRelativeTo(null);
        // Makes the frame visible
        outerFrame.setVisible(true);
    }
    
    /**
     * Helper method that creates the menubar
     * @author Matthew Hwang
     * @version 11/30/2017
     */
    private void createMenuBar() {
    	// Adds the buttons to the menus
    	// File Menu
    	fileMenu.add(newButtonMenu);
    	fileMenu.add(openButtonMenu);
    	fileMenu.add(saveButtonMenu);
    	fileMenu.add(exportButtonMenu);
    	fileMenu.add(exitButtonMenu);
    	// Help Menu
    	helpMenu.add(aboutButton);
    	// Adds the menus to the menu bar
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	
    	// Adds Action Listeners to all the buttons
    	createActionListeners();
    }
    
    /**
     * Helper method that creates the main page
     * @author Matthew Hwang
     * @version 11/30/2017
     */
    private void createMainPage() {
    	// Set background color of panel
    	mainPage.setBackground(Color.white);

    	mainPage.add(Box.createRigidArea(new Dimension(0, 50)));
    	
    	// Adding the title image
//    	ClassLoader cl = this.getClass().getClassLoader();
//    	cl.getResourceAsStream("Title.png");
    	ImageIcon titleImage = new ImageIcon(GUI.class.getResource("Title.png"));
    	JLabel titleLabel = new JLabel("", titleImage, JLabel.CENTER);  	
    	titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   	
    	mainPage.add(titleLabel);   
    	
    	// Adding buttons to main page	
    	newButtonPage.setAlignmentX(Component.CENTER_ALIGNMENT);  
    	mainPage.add(newButtonPage);
    	
    	mainPage.add(Box.createRigidArea(new Dimension(0, 5)));
    	
    	openButtonPage.setAlignmentX(Component.CENTER_ALIGNMENT);  
    	mainPage.add(openButtonPage); 
    	
    }
    
    /**
     * Helper method that creates the results page
     * @author Matthew Hwang , Parker Olive (minor contribution)
     * @version 12/5/2017
     */
    private void createResultsPage() {
    	resultsPage.setBackground(Color.white);

    	resultsPage.add(Box.createVerticalStrut(50));
    	JButton backButton = new JButton("Back to Edit");
    	resultsPage.add(backButton);
    	resultsPage.add(Box.createHorizontalStrut(460));
    	
    	backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	outerFrame.remove(resultsPage);
            	outerFrame.add(scrollPane);
            	outerFrame.revalidate();
            	exportButtonMenu.setEnabled(false);
            	outerFrame.repaint();
            	outerFrame.requestFocusInWindow();
            }
    	});
    	
    	JButton exportButton = new JButton("Export");
    	resultsPage.add(exportButton);
    	
    	exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	exportProject();
            }
    	});

    	JLabel resultsLabel = new JLabel("Results");
    	resultsLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
    	resultsLabel.setBorder(new EmptyBorder(10, 275, 0, 275));
    	resultsPage.add(resultsLabel);
    	
    	// Project Title
    	resultsTitle.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	resultsTitle.setBorder(new EmptyBorder(10, 275, 0, 275));
    	resultsPage.add(resultsTitle);

    	// Results
    	resultsPanel.setPreferredSize(new Dimension(350,350));
    	resultsPanel.setBackground(Color.white);

    	resultsEnergy.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	resultsEnergy.setHorizontalAlignment(SwingConstants.LEFT);
    	resultsPanel.add(resultsEnergy);
    	
    	resultsCost.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	resultsCost.setHorizontalAlignment(SwingConstants.LEFT);
    	resultsPanel.add(resultsCost);
    	resultsPanel.add(Box.createHorizontalStrut(100));
    	
    	resultsTime.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	resultsTime.setHorizontalAlignment(SwingConstants.LEFT);
    	resultsPanel.add(resultsTime);
    	
    	resultsPage.add(resultsPanel);
    	

    }
    
    /**
     * Helper method that creates the data input page
     * @author Matthew Hwang, Tyler Shupack
     * @version 12/4/2017
     */
    private void createEditPage() {
    	editPage.setBackground(Color.white);

    	// Project Title
    	editTitle.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	editTitle.setHorizontalAlignment(SwingConstants.LEFT);
    	editTitle.setBorder(new EmptyBorder(10, 20, 0, 500));
    	editPage.add(editTitle);
    	
    	// Energy Consumption Label
    	JLabel energyTitle = new JLabel("Energy Consumption");
    	energyTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
    	energyTitle.setHorizontalAlignment(SwingConstants.LEFT);
    	energyTitle.setBorder(new EmptyBorder(10, 20, 0, 450));
    	editPage.add(energyTitle);
    	
    	// Creating Energy Grid Container
    	energyPanel.setBorder(new EmptyBorder(0, 20, 0, 250));
    	energyPanel.setBackground(Color.white);
    	JLabel oldLabel = new JLabel("Old");
    	oldLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	oldLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	energyPanel.add(oldLabel);

    	JLabel newLabel = new JLabel("New");
    	newLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	energyPanel.add(newLabel);
    	
    	addEnergyLine();
    	editPage.add(energyPanel);
    	
    	editPage.add(Box.createHorizontalStrut(20));
    	JButton addEnergyButton = new JButton("Add");
    	editPage.add(addEnergyButton);
    	
    	editPage.add(Box.createHorizontalStrut(500));
    	
    	// Items Label
    	JLabel itemTitle = new JLabel("Materials");
    	itemTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
    	itemTitle.setHorizontalAlignment(SwingConstants.LEFT);
    	itemTitle.setBorder(new EmptyBorder(30, 20, 0, 450));
    	editPage.add(itemTitle);
    	
    	// Creating Items Grid Container
    	itemPanel.setBorder(new EmptyBorder(0, 20, 0, 250));
    	itemPanel.setBackground(Color.white);
    	JLabel itemNameLabel = new JLabel("Item Name");
    	itemNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	itemNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	itemPanel.add(itemNameLabel);

    	JLabel itemPriceLabel = new JLabel("Price/Unit");
    	itemPriceLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	itemPanel.add(itemPriceLabel);

    	JLabel itemQuantityLabel = new JLabel("Quantity");
    	itemQuantityLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
    	itemPanel.add(itemQuantityLabel);
    	
    	addItemLine();    	
    	editPage.add(itemPanel);
    	
    	editPage.add(Box.createHorizontalStrut(20));
    	JButton addItemButton = new JButton("Add");
    	editPage.add(addItemButton);
    	editPage.add(Box.createHorizontalStrut(550));
    	editPage.add(Box.createHorizontalStrut(510));
    	
    	JButton doneButton = new JButton("Done");
    	editPage.add(doneButton);
    	
        outerFrame.pack();	
    	
    	// Add Energy Action Listener
    	addEnergyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	addEnergyLine();
            	if (doneButton.getY() + doneButton.getHeight() > 510) {
            		editPage.setPreferredSize(new Dimension(680, editPage.getHeight() + 40));
                	outerFrame.revalidate();
                	outerFrame.repaint();
            		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
            		scrollBar.setValue(scrollBar.getMaximum());
            	}
            	outerFrame.revalidate();
            	outerFrame.repaint();
            }
    	});
    	
    	// Add Item Action Listener
    	addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	addItemLine();
            	if (doneButton.getY() + doneButton.getHeight() > 510) {
            		editPage.setPreferredSize(new Dimension(680, editPage.getHeight() + 40));
                	outerFrame.revalidate();
                	outerFrame.repaint();
            		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
            		scrollBar.setValue(scrollBar.getMaximum());
            	}
            	outerFrame.revalidate();
            	outerFrame.repaint();
            }
    	});
    	
    	// Add Done Button Listener
    	doneButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(final ActionEvent arg0) {
    			prepareResults();
    			
    			exportButtonMenu.setEnabled(true);
            	outerFrame.remove(scrollPane);
            	outerFrame.add(resultsPage);
            	resultsPanel.add(graph);
            	resultsTitle.setText("Project: " + projectName);
            	outerFrame.revalidate();
            	outerFrame.repaint();
    		}
    	});
    	
    }
    
    /**
     * Helper method that stores and prepares the results
     */
    private void prepareResults() {
    	for(Energy e : energyList) {
			e.gatherData();
		}
		for(Item i : itemList){
			i.gatherData();
		}
		fileHandler.setProjectName(projectName);
		data = new Data(itemList, energyList);
		
		totalCost = data.totalCost();
		breakEven = data.breakEven();

    	resultsPanel.remove(graph);
		
		graph = new Graph(projectName, totalCost, breakEven);
		
		resultsEnergy.setText("New Energy Consumption: " + data.totalNewEnergy() + " Kilowatt-Hour");
		resultsCost.setText("Cost of Materials: $" + data.totalCost());
		resultsTime.setText("Time to Recoup Costs: " + data.breakEven() + " Days");
    }
    
    /**
     * Helper method that adds a new line to the item panel
     */
    private void addItemLine() {
    	addItemLine("", 0.0, 0);
    }
    
    /**
     * Helper method that adds a filled line to the item panel
     * @author Matthew Hwang
     * @version 12/5/2017
     * @param itemName Name of material
     * @param itemPrice Price per material
     * @param itemQuantity Number of material
     */
    private void addItemLine(String itemName, double itemPrice, int itemQuantity) {
    	// Item Name
    	JPanel leftSide = new JPanel();
    	leftSide.setBackground(Color.white);
    	Item item = new Item();
    	if (itemPrice > 0 && itemQuantity > 0) {
    		item = new Item(itemName, itemPrice, itemQuantity);
    	}
    	itemList.add(item);
    	leftSide.add(item.nameField);

    	itemPanel.add(leftSide);

    	// Item Price

    	JPanel middleSide = new JPanel();
    	middleSide.setBackground(Color.white);

    	JLabel dollarLabel = new JLabel("$");
    	dollarLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	middleSide.add(dollarLabel);
    	
    	middleSide.add(item.unitPriceField);
    	
    	itemPanel.add(middleSide);

    	// Item Quantity
    	JPanel rightSide = new JPanel();
    	rightSide.setBackground(Color.white);
    	rightSide.add(item.quantityField);
    	
    	itemPanel.add(rightSide);
    }
    
    /**
     * Helper method that adds a new line to the energy panel
     */
    private void addEnergyLine() {
    	addEnergyLine(0.0, 0.0);
    }
    
    /**
     * Helper method that adds a filled line to the energy panel
     * @author Matthew Hwang
     * @version 12/5/2017
     * @param oldE Old energy value
     * @param newE New energy value
     */
    private void addEnergyLine(double oldE, double newE) {
    	// Old Energy
    	JPanel leftSide = new JPanel();
    	leftSide.setBackground(Color.white);
    	Energy energy = new Energy();
    	if (oldE > 0) {
    		energy = new Energy(oldE, newE);
    	}
    	energyList.add(energy);
    	leftSide.add(energy.oldField);

    	JLabel leftLabel = new JLabel("Kilowatt-Hour");
    	leftLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	leftSide.add(leftLabel);

    	energyPanel.add(leftSide);

    	// New Energy
    	JPanel rightSide = new JPanel();
    	rightSide.setBackground(Color.white);
    	rightSide.add(energy.newField);

    	JLabel rightLabel = new JLabel("Kilowatt-Hour");
    	rightLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
    	rightSide.add(rightLabel);
    	
    	energyPanel.add(rightSide);
    }
    
    /**
     * Helper method for creating a new project
     * @author Matthew Hwang, minor contribution Parker Olive
     * @version 11/30/2017
     */
    private void createNew() {
		boolean allow = true;
    	if (!saved) {
    		int result = JOptionPane.showConfirmDialog((Component) outerFrame, "You have not saved your project\nContinue anyway?",
    		        "alert", JOptionPane.OK_CANCEL_OPTION);
    		if (result != JOptionPane.OK_OPTION) {        
    			allow = false;
    		}
    	}
		if (allow) {
            String input = (String)JOptionPane.showInputDialog("Input name of your project");
			if (input != null) {
				projectName = input;
				saveButtonMenu.setEnabled(true);
				exportButtonMenu.setEnabled(false);
				saved = false;
            	outerFrame.remove(mainPage);
            	outerFrame.remove(resultsPage);
            	resultsPanel.remove(graph);
            	outerFrame.remove(scrollPane);
            	outerFrame.add(scrollPane);
            	editTitle.setText("Project: " + projectName);
            	itemList = new ArrayList<Item>();
            	energyList = new ArrayList<Energy>();
            	
            	energyPanel.removeAll();
            	itemPanel.removeAll();
            	
            	JLabel oldLabel = new JLabel("Old");
            	oldLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
            	oldLabel.setHorizontalAlignment(SwingConstants.LEFT);
            	energyPanel.add(oldLabel);

            	JLabel newLabel = new JLabel("New");
            	newLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
            	energyPanel.add(newLabel);
            	addEnergyLine();
            	addItemLine();
            	
            	outerFrame.revalidate();
            	outerFrame.repaint();
            	outerFrame.requestFocusInWindow();
        	}    
		}    	
    }
    
    /**
     * Helper method for opening a project
     * @author Matthew Hwang, Anh Nguyen
     * @version 12/5/2017
     */
    private void openProject() {
		boolean allow = true;
    	if (!saved) {
    		int result = JOptionPane.showConfirmDialog((Component) outerFrame, "You have not saved your project\nContinue anyway?",
    		        "alert", JOptionPane.OK_CANCEL_OPTION);
    		if (result != JOptionPane.OK_OPTION) {        
    			allow = false;
    		}
    	}
		if (allow) {
        	chooser.setCurrentDirectory(new File("projects"));
        	chooser.setSelectedFile(new File(""));
        	chooser.setDialogTitle("Choose the project file");

        	outerFrame.revalidate();
        	outerFrame.repaint();
	    	
	    	//open dialog to choose a file if the user clicks open
	    	if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
	    		saved = false;
            	file = chooser.getSelectedFile();	    	
            	//start reading new file.
            	fileHandler.open(file);
            	projectName = fileHandler.getProjectName();

            	if (outerFrame.isAncestorOf(mainPage)) {
            		outerFrame.remove(mainPage);
            	}
            	if (outerFrame.isAncestorOf(resultsPage)) {
            		outerFrame.remove(resultsPage);
            	}
            	if (outerFrame.isAncestorOf(scrollPane)) {
            		outerFrame.remove(scrollPane);
            	}
            	
            	itemList = new ArrayList<Item>();
            	energyList = new ArrayList<Energy>();
            	ArrayList<Item> tempItemList = fileHandler.getItemList();
            	ArrayList<Energy> tempEnergyList = fileHandler.getEnergyList();
            	
            	energyPanel.removeAll();
            	itemPanel.removeAll();
            	
            	JLabel oldLabel = new JLabel("Old");
            	oldLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
            	oldLabel.setHorizontalAlignment(SwingConstants.LEFT);
            	energyPanel.add(oldLabel);

            	JLabel newLabel = new JLabel("New");
            	newLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
            	energyPanel.add(newLabel);
            	
            	for (Energy currentEnergy : tempEnergyList) {
            		addEnergyLine(currentEnergy.getOld(), currentEnergy.getNew());
            	}
            	
            	
            	for (Item currentItem : tempItemList) {
            		addItemLine(currentItem.getName(), currentItem.getUnitPrice(), currentItem.getQuantity());
            	}
            	
            	prepareResults();
            	
            	saveButtonMenu.setEnabled(true);
            	exportButtonMenu.setEnabled(false);
            	
            	outerFrame.add(scrollPane);
            	editTitle.setText("Project: " + projectName);
            	outerFrame.revalidate();
            	outerFrame.repaint();
            	outerFrame.requestFocusInWindow();
            	
	    	}
		}
    	
    }
    
    /**
     * Helper method for exporting a project
     * @author Matthew Hwang
     * @version 12/5/2017
     */
    private void exportProject() {
    	chooser.setCurrentDirectory(new File("exports"));
    	chooser.setSelectedFile(new File(""));
    	chooser.setDialogTitle("Choose the location for the readable txt file");
    	outerFrame.revalidate();
    	outerFrame.repaint();
        int chosenOption = chooser.showSaveDialog(null);
    	outerFrame.revalidate();
    	outerFrame.repaint();
        if (chosenOption == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = chooser.getSelectedFile();
        	if (selectedFile.exists()) {
        		int choice = JOptionPane.showConfirmDialog(outerFrame, "This file already exists. Overwrite?", "Overwrite Confirmation", JOptionPane.YES_NO_OPTION);
        		if (choice == JOptionPane.YES_OPTION) {
                	file = new File(selectedFile + "");
                	fileHandler.export(data, file);  
                	outerFrame.revalidate();
                	outerFrame.repaint();              			
        		} else {
        			exportProject();
        		}
        	} else {
            	file = new File(selectedFile + "");
            	if (!(selectedFile + "").endsWith(".txt")) {
            		file = new File(selectedFile + ".txt");
            	}
            	fileHandler.export(data, file);
            	outerFrame.revalidate();
            	outerFrame.repaint();                    		
        	}
        }
    	
    	
    }
    
    /**
     * Helper method that creates the action listeners for the buttons
     * @author Parker Olive, Matthew Hwang
     * @version 12/5/17
     */
    private void createActionListeners(){
    	// Perform action when user clicks the new project button
        newButtonPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	createNew();
            }
    	});
        
        /*perform action when user clicks the open project button*/
        openButtonPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	openProject();
            }
    	});
        
        /*perform action when user clicks the new project menu button*/
        newButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	createNew();
            }
    	});
        
        saveButtonMenu.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(final ActionEvent arg0) {
        		prepareResults();
            	chooser.setCurrentDirectory(new File("projects"));
            	chooser.setSelectedFile(new File(""));
            	chooser.setDialogTitle("Choose the location for project and its name");
                int chosenOption = chooser.showSaveDialog(null);
            	outerFrame.revalidate();
            	outerFrame.repaint();
                if (chosenOption == JFileChooser.APPROVE_OPTION) {
                	File selectedFile = chooser.getSelectedFile();
                	if (selectedFile.exists()) {
                		int choice = JOptionPane.showConfirmDialog(outerFrame, "This file already exists. Overwrite?", "Overwrite Confirmation", JOptionPane.YES_NO_OPTION);
                		if (choice == JOptionPane.YES_OPTION) {
                    		saved = true;
                        	file = new File(selectedFile + "");
                        	fileHandler.save(projectName, data.getEnergyList(), data.getItemList(), file);                			
                		} else {
                			actionPerformed(arg0);
                		}
                	} else {
                		saved = true;
                    	file = new File(selectedFile + "");
                    	if (!(selectedFile + "").endsWith(".csv")) {
                    		file = new File(selectedFile + ".csv");
                    	}
                    	fileHandler.save(projectName, data.getEnergyList(), data.getItemList(), file);                    		
                	}
                }
        	}
        });
        
        /*adding an exit button action listener*/
        exitButtonMenu.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(final ActionEvent arg0){
        		exit();
        	}
        });
        
        /*perform action when user clicks the open project menu button*/
        openButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	openProject();
            }
    	});
        
        /*perform action when user clicks the export menu button*/
        exportButtonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	exportProject();
            }
    	});
        
        /*perform action when user clicks the about menu button*/
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
            	JOptionPane.showMessageDialog(outerFrame, "uDIY Created by:\nMatthew Hwang\nParker Olive\nAnh Nguyen\nChris Kim\nTyler Shupack");
            }
    	});
    }

    /**
     * Helper method to exit the program.
     * @author Anh Nguyen, Matthew Hwang
     * @version 12/5/17
     */
	private void exit() {
		if (saved) {
			System.exit(0);
		} else {
			if (JOptionPane.showConfirmDialog(outerFrame, "You have not saved your project. Still exit out?", "Closing Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
		    }
		}		
	}
}