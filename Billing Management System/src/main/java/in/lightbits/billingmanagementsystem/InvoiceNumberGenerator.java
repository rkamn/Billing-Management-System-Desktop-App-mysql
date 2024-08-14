package in.lightbits.billingmanagementsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class InvoiceNumberGenerator {

    // File to store the last generated invoice number
    private static final String LAST_NUMBER_FILE = "last_invoice_number.pdf";

    // Method to generate the next invoice number
    public static String generateInvoiceNumber() throws IOException {
        // Get the current year as a string
        String year = String.valueOf(LocalDate.now().getYear());

        // Read the last number from the file
        int lastNumber = readLastNumber();

        // Increment the last number by 1
        int newNumber = lastNumber + 1;

        // Save the new number back to the file for future use
        saveLastNumber(newNumber);

        // Format the new number to be a 4-digit string
        String formattedNumber = String.format("%06d", newNumber);

        // Combine the year with the formatted number to create the invoice number
        return year + formattedNumber;
    }

    // Helper method to read the last number from the file
    private static int readLastNumber() throws IOException {
        File file = new File(LAST_NUMBER_FILE);
        if (!file.exists()) {
            // If the file doesn't exist, start with 0
            return 0;
        }

        String content = new String(Files.readAllBytes(Paths.get(LAST_NUMBER_FILE)));
        return Integer.parseInt(content.trim());
    }

    // Helper method to save the new number to the file
    private static void saveLastNumber(int number) throws IOException {
        try (FileWriter writer = new FileWriter(LAST_NUMBER_FILE)) {
            writer.write(String.valueOf(number));
        }
    }

    // Example main method to demonstrate usage
//    public static void main(String[] args) {
//        try {
//            String invoiceNumber = generateInvoiceNumber();
//            System.out.println("Generated Invoice Number: " + invoiceNumber);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
