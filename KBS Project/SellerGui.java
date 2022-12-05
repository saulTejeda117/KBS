package df.agents;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  @author Giovanni Caire - TILAB
 */
class SellerGui extends JFrame {	
	private SellerAgent myAgent;
	
	private JTextField factsField, rulesField;
	
	SellerGui(SellerAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Name file facts:"));
		factsField = new JTextField(15);
		p.add(factsField);
		p.add(new JLabel("Name file rules:"));
		rulesField = new JTextField(15);
		p.add(rulesField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String facts = "src/clips/"+factsField.getText().trim()+".clp";
					String rules = "src/clips/"+rulesField.getText().trim()+".clp";
					myAgent.updateCatalogue(facts, rules);
					factsField.setText("");
					rulesField.setText("");
					///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\///\\\
					dispose();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(SellerGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}
