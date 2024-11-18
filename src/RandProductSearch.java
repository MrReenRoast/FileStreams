import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;

    public RandProductSearch() {
        setTitle("Product Search");
        setLayout(new BorderLayout());

        searchField = new JTextField();
        resultArea = new JTextArea(15, 30);
        searchButton = new JButton("Search");

        add(searchField, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(searchButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchProducts());

        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchProducts() {
        resultArea.setText("");
        String search = searchField.getText().toLowerCase();

        try (RandomAccessFile file = new RandomAccessFile("products.dat", "r")) {
            while (file.getFilePointer() < file.length()) {
                Product product = Product.readFromFile(file);
                if (product.getName().toLowerCase().contains(search)) {
                    resultArea.append(product.getName() + " - " + product.getDescription() + " - $" + product.getCost() + "\n");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
