module com.mycompany.csc325_mvcloudfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.csc325_mvcloudfx to javafx.fxml;
    exports com.mycompany.csc325_mvcloudfx;
}
