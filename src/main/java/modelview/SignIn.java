package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import com.google.firebase.database.snapshot.Node;
import com.mycompany.csc325_mvcloudfx.App;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class SignIn implements Initializable {
    @FXML
    private TextField userInput,userPassword;

    static UserRecord currUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void verifyCredentials() throws IOException, FirebaseAuthException{
        App.setRoot("AccessFBView.fxml");
        /*try {
        String name = userInput.getText();
        String password = userPassword.getText();
        currUser = FirebaseAuth.getInstance().getUser(name);
        System.out.println(currUser.getProviderData());
        } catch (FirebaseAuthException ex) {
        System.err.println("You messed up");
        }*/
    }
    public void toCreateWinodw() throws IOException{
        App.setRoot("NewAccount.fxml");
    }
}
