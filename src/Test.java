package src;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        // Créer un panneau pour contenir la combobox
        JPanel panel = new JPanel();

        // Création de la combobox avec des options
        JLabel comboBoxLabel = new JLabel("Sélectionnez une option :");
        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        // Ajouter les composants au panneau
        panel.add(comboBoxLabel);
        panel.add(comboBox);

        // Afficher la boîte de dialogue
        int result = JOptionPane.showConfirmDialog(null, panel, "Veuillez sélectionner une option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedOption = (String) comboBox.getSelectedItem();
            System.out.println("Vous avez sélectionné : " + selectedOption);
        } else {
            System.out.println("La sélection a été annulée.");
        }
    }
}
