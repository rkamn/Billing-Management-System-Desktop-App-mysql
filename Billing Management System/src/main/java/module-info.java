module in.lightbits.billingmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    //requires pdfbox.app;
    requires org.apache.pdfbox;
    // requires eu.hansolo.tilesfx;

    opens in.lightbits.billingmanagementsystem to javafx.fxml;
    exports in.lightbits.billingmanagementsystem;
}