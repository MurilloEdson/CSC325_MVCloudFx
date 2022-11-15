package modelview;

import com.mycompany.csc325_mvcloudfx.App;
import java.io.IOException;
import java.net.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import javafx.beans.value.*;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.web.*;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import netscape.javascript.JSObject;
import org.w3c.dom.Document;

public class WebContainerController implements Initializable {
    Document doc;
        private DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            private static String HTML_STRING2 = //
                "<html>"//
                    + "<head> " //
                   + "  <script language='javascript'> " //
                   + "     function changeBgColor()  { "//
                   + "       var color= document.getElementById('ueberschr').value; "//
                   + "       document.body.style.backgroundColor= color; " //
                   + "     } " //
                   + "  </script> "//
                    + "  </script> "//
                    + "</head> "//
                    + "<body> "//
                    + "   <h2>This is Html content</h2> <input id='ueberschr' value='yellow' />"//
                    + "   <button onclick='app12.showTime();changeBgColor();'>Call To JavaFX</button> "//
                    + "</body> "//
                    + "</html> "//
    ;
 private static String HTML_STRING = //
                "<html>"//
                   + "<head> " //
                   + "  <script language='javascript'> " //
                   + "     function changeBgColor()  { "//
                   + "       var color= document.getElementById('color').value; "//
                   + "       document.body.style.backgroundColor= color; " //
                   + "     } " //
                   + "  </script> "//
                   + "</head> "//
                   + "<body> "//
                   + "   <h2>This is Html content</h2> "//
                   + "   <b>Enter Color:</b> "//
                   + "   <input id='color' value='yellow' /> "//
                   + "   <button onclick='changeBgColor();'>Change Bg Color</button> "//
                   + "</body> "//
                   + "</html> "//
   ;
 
    @FXML
    Label label;
    
    @FXML
    WebView webView;
    @FXML
    private MenuItem username;
    
    private WebEngine webEngine;

    @FXML
    private void goAction(ActionEvent evt) {
        webEngine.load("http://google.com");
    }
    @FXML
    private void setLabel(ActionEvent e){
        System.out.println("H1");
        doc.getElementById("ueberschr").setAttribute("value", "Red");
    }
    
    @FXML
    private void swithcBackStage(ActionEvent e){
        try {
            App.setRoot("AccessFBView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(WebContainerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username.setText(SignIn.currUser.getDisplayName());
        try {
        webEngine = webView.getEngine();//  webView.setContextMenuEnabled(false);
        webEngine.loadContent(HTML_STRING2);
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
        
        @Override
        public void changed(ObservableValue<? extends State> ov, State t, State newState) {
        if (newState == Worker.State.SUCCEEDED) {
        doc = webEngine.getDocument();
        // Get window object of page.
        JSObject jsobj = (JSObject) webEngine.executeScript("window");
        System.out.println("H2");
        // Set member for 'window' object.
        // In Javascript access: window.myJavaMember....
        jsobj.setMember("app12", new Bridge());
        }
        }});
        
        webView.setContextMenuEnabled(false);
        //txtURL.setText("http://www.google.com");
        // webEngine.load("http://www.google.com");
        webEngine.setJavaScriptEnabled(true);
        //webEngine.load(this.getClass().getResource("newhtml.html").toExternalForm()
        // "file://Users/MoaathAlrajab/Documents/demo265/MVVMExample/src/main/resources/com/mycompany/mvvmexample/newhtml.html");
        } catch (Exception ex) {
        //Logger.getLogger(WebContainerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public class Bridge {
        public void showTime() {
            System.out.println("Show Time");
            label.setText("Now is: " + df.format(new Date()));
        }
    }
}