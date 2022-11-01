package modelview;

import com.mycompany.csc325_mvcloudfx.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class AccessFBView {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
