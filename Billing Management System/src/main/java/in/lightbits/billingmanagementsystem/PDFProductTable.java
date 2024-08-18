package in.lightbits.billingmanagementsystem;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDFProductTable {
    public static void main(String[] args) {
        // Sample product details
        String[][] productDetails = {
                {"Product ID", "Product Name", "Price", "Quantity"},
                {"001", "Laptop", "$1000", "2"},
                {"002", "Smartphone", "$500", "5"},
                {"003", "Tablet", "$300", "3"},
                {"004", "Headphones", "$100", "10"}
        };

        // Create a new document
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            // Create a content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set the font
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Starting position for the table
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float tableHeight = rowHeight * productDetails.length;
            float cellMargin = 5f;

            // Draw the table headers
            for (int i = 0; i < productDetails[0].length; i++) {
                contentStream.addRect(margin + i * (tableWidth / productDetails[0].length), yPosition - rowHeight,
                        tableWidth / productDetails[0].length, rowHeight);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + i * (tableWidth / productDetails[0].length) + cellMargin,
                        yPosition - 15);
                contentStream.showText(productDetails[0][i]);
                contentStream.endText();
            }
            yPosition -= rowHeight;

            // Draw the table content
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (int i = 1; i < productDetails.length; i++) {
                for (int j = 0; j < productDetails[i].length; j++) {
                    contentStream.addRect(margin + j * (tableWidth / productDetails[0].length), yPosition - rowHeight,
                            tableWidth / productDetails[0].length, rowHeight);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + j * (tableWidth / productDetails[0].length) + cellMargin,
                            yPosition - 15);
                    contentStream.showText(productDetails[i][j]);
                    contentStream.endText();
                }
                yPosition -= rowHeight;
            }

            // Stroke the lines
            contentStream.stroke();

            // Close the content stream
            contentStream.close();

            // Save the document
            document.save("ProductDetailsTable.pdf");
            System.out.println("PDF created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

