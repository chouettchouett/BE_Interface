package controller;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ember
 */

public class ControllerTypeRecherche implements java.awt.event.ActionListener {
    private final DefaultComboBoxModel<String> comboBoxModel;
    private final style.CustomJTextField rechercheContenu;

    public ControllerTypeRecherche(style.CustomJTextField rechercheContenu) {
        this.rechercheContenu = rechercheContenu;
        comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement("Mot ou ensemble de mots");
        comboBoxModel.addElement("Personnage");
        comboBoxModel.addElement("Saison");
        comboBoxModel.addElement("Épisode");
        
        this.rechercheContenu.setPlaceholder(recherchePhrase(comboBoxModel.getElementAt(0)));
    }
    
    public static String recherchePhrase(String type) {
        return "Entrer un " + type.toLowerCase() + " à rechercher";
    }

    public DefaultComboBoxModel<String> getComboBoxModel() {
        return comboBoxModel;
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JComboBox combobox = (javax.swing.JComboBox) evt.getSource();
        rechercheContenu.setPlaceholder(recherchePhrase((String) combobox.getSelectedItem()));
        rechercheContenu.repaint();
    }
}