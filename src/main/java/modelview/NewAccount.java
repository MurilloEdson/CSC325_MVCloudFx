package modelview;

import com.google.firebase.auth.*;
import com.mycompany.csc325_mvcloudfx.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Edson
 */
public class NewAccount implements Initializable {
    @FXML
    private TextField fName,email,userName,userPW;
    @FXML
    private TextField confirmPW;
    @FXML
    private Button createBtn;
    @FXML
    private Label errorMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void createAccount(){
         UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setDisplayName(fName.getText())
                .setEmail(email.getText())
                .setUid(userName.getText())
                .setPassword(userPW.getText());

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());

        } catch (FirebaseAuthException ex) {
           // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        returnLoginIn();
    }
    public void returnLoginIn(){
        try {
            App.setRoot("SignIn.fxml");
        } catch (IOException ex) {
            Logger.getLogger(NewAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
