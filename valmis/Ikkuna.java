import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;

import javax.swing.*;
import java.io.*;
import java.util.Map;

public class Ikkuna extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	// default serial number
	private static final int EXIT_ON_CLOSE = 0;
	private String ikkunan_nimi;
	private JFrame ikkuna = new JFrame();
	private Dimension koko = new Dimension(400, 400);
	private boolean username_edit = false;
	private String username = null;
	private String salasana = null;
	private JFileChooser chooser = new JFileChooser();	//tallennus
	private boolean Gpienet_kirjaimet =true;	//generointiin parametrit
	private boolean Gisot_kirjaimet;			//generointiin parametrit
	private boolean Gnumerot;					//generointiin parametrit
	private boolean Gpohjoismaiset;				//generointiin parametrit
	private boolean Gerikoismerkit;				//generointiin parametrit
	private int Gmaara =7;						//generointiin parametrit

	
	
	
	//-------------------------Let there be FRAME!------------------------------
	public Ikkuna(String ikkunan_nimi) {
		System.setProperty("file.encoding","UTF-8");
		this.ikkunan_nimi = ikkunan_nimi;
		ikkuna.setTitle(this.ikkunan_nimi);
		ikkuna.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ikkuna.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				int result = JOptionPane.showConfirmDialog(null, "Do you want to quit Randomizenator?","Are you sure?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION){
						System.exit(0);
			       }
			 }
			});
		ikkuna.setLayout(new BorderLayout());
		Maker();
		ikkuna.pack();
		ikkuna.setLocationRelativeTo(null);
		ikkuna.setVisible(true);
		ikkuna.setSize(550,550);
	}
	
	
	//-------------------------Let there be GUI!------------------------------
	public void Maker(){
		// tee Paa-paneeli 				EI valltis tartte?
				JPanel paa_panel = new JPanel();
				paa_panel.setPreferredSize(koko);
				JTabbedPane tab1 = new JTabbedPane();
				
				// Eka tabi
				JPanel panel1 = makePanel1();
				tab1.addTab("Generate",panel1);		// laita tabi paikalleen
				
				// toka tabi
				JPanel panel2= makePanel2();
				tab1.addTab("Options",panel2);		// laita tabi paikalleen
				
				// kolmas tabi
				JPanel panel3 = makePanel3();
				tab1.addTab("Help",panel3);			// laita tabi paikalleen

				// Pistä tabi paneeli ikkunaan
				paa_panel.add(tab1);
				paa_panel.setVisible(true);
				ikkuna.add(paa_panel, BorderLayout.CENTER);
				//JLabel varoitus = new JLabel("Jussi Jokio, Niko Kupari");
				//ikkuna.add(varoitus, BorderLayout.SOUTH);
	}
	
	
	//-------------------------Let there be TABS!------------------------------
	public JPanel makePanel1(){
		// ==========eka tabin sisältö===========
		JPanel panel1 = new JPanel(); 
		JPanel panel_TYHJA = new JPanel();
		panel_TYHJA.setPreferredSize(new Dimension(300, 100));
		panel1.add(panel_TYHJA);
		panel1.setPreferredSize(koko);
		JPanel testi = this.MakeTextField("Username is optional", "Username"); 
		panel1.add(testi);
		JPanel button1 = this.MakeButton();
		panel1.add(button1);
		button1.setVisible(true);
		return panel1;
	}
	public JPanel makePanel2(){
		// ==========toka tabin sisältö===========
		JPanel panel2 = new JPanel(); 
		JPanel panel_TYHJA_2 = new JPanel();
		JPanel panel2_sisa = new JPanel();
		panel_TYHJA_2.setPreferredSize(new Dimension(300, 100));
		panel2.add(panel_TYHJA_2);
		panel2.setPreferredSize(koko);
		panel2_sisa.setLayout(new GridLayout(0,1));
		JPanel checkbox = this.CheckBox();
		panel2_sisa.add(checkbox);
		JPanel combobox = this.ComboBox();
		panel2_sisa.add(combobox);
		panel2.add(panel2_sisa);
		return panel2;
	}
	public JPanel makePanel3(){
		// ==========kolmas tabin sisältö==========
		JPanel panel3 = new JPanel(); 
		panel3.setPreferredSize(koko);
		JTextArea textArea = new JTextArea(24, 35);
		textArea.setText("\n\n\n\n	How to use:"
				+ "\n	   Generate password with 'Generate' button."
				+ "\n	   Use 'Options' to give parameters to password."
				+ "\n	   Username is optional."
				+ "\n	   'Save as' button saves file (.txt is generated automatily)"
				+ "\n	   For more information see README.txt"
				+ "\n\n\n"
				+ "	Options:"
				+ "\n	    Small letters		a,b,c..."
				+ "\n	    Capitals letters	A,B,C..."
				+ "\n	    Numbers		1,2,3..."
				+ "\n	    Nordic alphet		ä,Ö,å..."
				+ "\n	    Special digits		!,?,@..."
				+ "\n	    Number of digits	1-20"
				+ "");
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        panel3.add(textArea);
        return panel3;
	}
	
	
	//-------------------------Let there be BUTTONS!------------------------------
	public JPanel MakeTextField(String watermark, String nimi){
		JPanel panel = new JPanel();
		JLabel labelnimi = new JLabel(nimi);
		panel.add(labelnimi);	
		panel.setPreferredSize(new Dimension(300, 50));
		TextField teksti = new TextField(watermark,30);
		teksti.setEditable(true);
		// ===========Username===============
		teksti.addFocusListener(new FocusListener() {
		      public void focusGained(FocusEvent e) {
		    	  if(getEditUser()){
		    		  teksti.setText(getUsername());
		    	  }else{teksti.setText("");}
		        }
		        public void focusLost(FocusEvent e) {
		        	if (teksti.getText().length() == 0){
		        		teksti.setText(watermark);
		        		setEditUser(false);
		        	}else{
		        		setUsername(teksti.getText());
		        		setEditUser(true);
		        	}	
		        }
		});
		this.setEditUser(true); 
      	panel.add(teksti);
		return panel;
	}
	//_____________________namiskat ekaan tabiin____________________
    public JPanel MakeButton(){
    	JTextField jtfInput;
		JButton button_1 = new JButton("Generate");
		JButton button_2 = new JButton("Save as");
		jtfInput = new JTextField(20);
		jtfInput.setEditable(false);
		jtfInput.setBackground(Color.white);
		JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		JPanel panel_3 = new JPanel();
		JPanel panel_4 = new JPanel();
		button_1.setActionCommand("enable");
		button_2.setActionCommand("disable");
		button_2.setEnabled(false);
		button_1.setMnemonic(KeyEvent.VK_E); //Set ShortCut Keys
		//===================napin kuuntelu==============
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//============GENERATE!!!!===========
				GenerateSomeShit(jtfInput);
				if ("enable".equals(e.getActionCommand())) {
					button_1.setEnabled(true);
					button_2.setEnabled(true);
				} 
			}
		});
		button_2.setMnemonic(KeyEvent.VK_S); //Set ShortCut Keys; 
		button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {		
        	    int retrival = chooser.showSaveDialog(ikkuna);
        	    if (retrival == JFileChooser.APPROVE_OPTION) {
        	    	//===========tallenna=================
        	    	WriteSomeShit();
        	    }}
        });	
		
		JLabel varoitus_teksti = new JLabel("Never give your password to anyone!");
		JLabel salasana = new JLabel("Generated Password");
		JPanel isopanel = new JPanel(new GridLayout(0, 1));
		
		panel_1.add(salasana);
		panel_2.add(jtfInput);
		panel_3.add(button_1);
		panel_3.add(button_2);
		panel_4.add(varoitus_teksti);
		
		isopanel.add(panel_1);
		isopanel.add(panel_2);
		isopanel.add(panel_3);
		isopanel.add(panel_4);
		return isopanel;
	}
    //_________________options valikot______________
    public JPanel CheckBox() {
    	//Create the check boxes.
		JCheckBox pienet_kirjaimet = new JCheckBox("Small letter");
		pienet_kirjaimet.setMnemonic(KeyEvent.VK_S);
		pienet_kirjaimet.setSelected(true);
		
		JCheckBox isot_kirjaimet = new JCheckBox("Capital letter");
		isot_kirjaimet.setMnemonic(KeyEvent.VK_C);
		isot_kirjaimet.setSelected(false);
		
		JCheckBox numerot = new JCheckBox("Number");
		numerot.setMnemonic(KeyEvent.VK_N);
		numerot.setSelected(false);
		
		JCheckBox pohjoismaiset_merkit = new JCheckBox("Nordic alphbet");
		pohjoismaiset_merkit.setMnemonic(KeyEvent.VK_A);
		pohjoismaiset_merkit.setSelected(false);
		
		JCheckBox erikoismerkit = new JCheckBox("Special chracter");
		erikoismerkit.setMnemonic(KeyEvent.VK_H);
		erikoismerkit.setSelected(false);
    	//================chekcbox kuuntelu=====================
		pienet_kirjaimet.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {
	         	Gpienet_kirjaimet = pienet_kirjaimet.isSelected(); 
	         }
	      });
		
		isot_kirjaimet.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 Gisot_kirjaimet = isot_kirjaimet.isSelected(); 
	         }
         });
		
		numerot.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 Gnumerot = numerot.isSelected(); 
	         }           
	      });
		
		pohjoismaiset_merkit.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	Gpohjoismaiset = pohjoismaiset_merkit.isSelected();
	         }           
	      });
		
		erikoismerkit.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 Gerikoismerkit = erikoismerkit.isSelected(); 
	         }           
	      });
		//=============juttuja===============
		JLabel option_teksti = new JLabel("Include in password:");
		JLabel option_teksti_1 = new JLabel("");
		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		Font font = option_teksti.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		option_teksti.setFont(font.deriveFont(attributes));
		checkPanel.add(option_teksti);
		checkPanel.add(option_teksti_1);
		checkPanel.add(pienet_kirjaimet);
		checkPanel.add(isot_kirjaimet);
		checkPanel.add(numerot);
		checkPanel.add(pohjoismaiset_merkit);
		checkPanel.add(erikoismerkit);
		return checkPanel;
	}
    //_________________numeron valitsin_______________________
    public JPanel ComboBox(){
		String[] numerot = { "1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20" };
		JComboBox<String> numero_lista = new JComboBox<String>(numerot);
		numero_lista.setSelectedIndex(7);
		numero_lista.setBackground(Color.WHITE);
		numero_lista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Gmaara = numero_lista.getSelectedIndex();
				}  
			});
    	JLabel numerojen_nimi = new JLabel("Number of digits");
    	JPanel paneeli = new JPanel();
    	paneeli.add(numero_lista);
    	paneeli.add(numerojen_nimi);
	    return paneeli; 
    }
    
  //-------------------------Let there be ACTIONS!------------------------------
    public String Tallenna(){
    	// _______________kertoo mitä tallennetaan________________
    	String tallenna = "";
    	if (this.username_edit){
    		tallenna = username + " : " + salasana;
    	}
    	else {
    		tallenna =  " : " + salasana;
    	}
    	return tallenna;
    }
    //__________________setit ja getit____________________
	public void setUsername(String user){username=user;}
	public void setSalasana(String passw){salasana=passw;}
	public void setEditUser(boolean edit){username_edit = edit;}
	
	public String getUsername(){return username;}
	public String getSalasana(){return salasana;}
	public boolean getEditUser(){return username_edit;}
	
	//__________________tiedostoon kirjoitus______________
	private void WriteSomeShit(){
		File file = chooser.getSelectedFile();
		try (FileWriter FW = new FileWriter(file+".txt",true)){
			String path = file.getAbsolutePath();
			FW.append(Tallenna());//funktio!
			FW.append("\r\n");
			FW.close();
			//onnistumisilmoitus
			JOptionPane.showMessageDialog(ikkuna, "File has been saved succesfully! \r\n"+"Saved in: "+path, "File saved", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception ex) {
			//epaonnistumisilmoitus
		    ex.printStackTrace();
		    JOptionPane.showMessageDialog(ikkuna, "File was not saved!\r\n Remember to select a file or make a new file \r\n"+ex, "Save Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	//______________________GENERATEEEEE_____________________
	private void GenerateSomeShit(JTextField field){
		Generator randz = new Generator(Gmaara,Gpienet_kirjaimet,Gisot_kirjaimet,Gnumerot,Gpohjoismaiset,Gerikoismerkit);
		String teksti = randz.generate();
		salasana=teksti;
		if(teksti.equals("no parametrs!")){
			JOptionPane.showMessageDialog(ikkuna, "No parametrs given!", "Parameter Error", JOptionPane.ERROR_MESSAGE);
		}else{
		field.setText(teksti);
		}
	}
}
