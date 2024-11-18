import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, IDField, costField, countField;
    private JButton addButton, clearButton;

    private RandomAccessFile file;
    private int recordCount = 0;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setLayout(new GridLayout(6, 2));

        // Create form fields
        nameField = new JTextField();
        descriptionField = new JTextField();
        IDField = new JTextField();
        costField = new JTextField();
        countField = new JTextField("0");
        countField.setEditable(false);

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("ID:"));
        add(IDField);
        add(new JLabel("Cost:"));
        add(costField);
        add(new JLabel("Record Count:"));
        add(countField);

        addButton = new JButton("Add");
        clearButton = new JButton("Clear");
        add(addButton);
        add(clearButton);

        addButton.addActionListener(e -> addRecord());
        clearButton.addActionListener(e -> clearFields());

        try {
            file = new RandomAccessFile("products.dat", "rw");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
        }

        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addRecord() {
        try {
            // Retrieve field inputs
            String name = nameField.getText();
            String description = descriptionField.getText();
            String ID = IDField.getText();
            String costText = costField.getText();

            // Validate inputs
            if (name == null || name.trim().isEmpty() ||
                    description == null || description.trim().isEmpty() ||
                    ID == null || ID.trim().isEmpty() ||
                    costText == null || costText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

            // Parse cost
            double cost;
            try {
                cost = Double.parseDouble(costText.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid cost! Please enter a numeric value.");
                return;
            }

            // Validate ID length
            if (ID.length() > Product.ID_LENGTH) {
                JOptionPane.showMessageDialog(this, "ID must not exceed " + Product.ID_LENGTH + " characters.");
                return;
            }

            // Create and save the product
            Product product = new Product(name, description, ID, cost);
            System.out.println("Product to be written: " + product); // Debug log
            file.seek(file.length()); // Move to end of file
            product.writeToFile(file);

            // Update record count
            recordCount++;
            countField.setText(String.valueOf(recordCount));

            // Clear input fields
            clearFields();

            JOptionPane.showMessageDialog(this, "Product added successfully!");

        } catch (Exception ex) {
            ex.printStackTrace(); // Print stack trace for debugging
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        IDField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
