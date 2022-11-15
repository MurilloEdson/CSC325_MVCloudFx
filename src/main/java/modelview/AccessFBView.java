package modelview;

import viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.*;
import com.mycompany.csc325_mvcloudfx.App;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Person;

public class AccessFBView implements Initializable {

    @FXML
    private TextField nameField, majorField, ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    @FXML
    private ListView<Person> outputList;
    @FXML
    private Button select;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private MenuItem username;


    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;
    AccessDataViewModel accessDataViewModel = new AccessDataViewModel();

    public void setOutputList() {
        outputList.setItems(listOfUsers);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
        select.setDisable(true);
        update.setDisable(true);
        delete.setDisable(true);
        username.setText(SignIn.currUser.getDisplayName());
    }

    @FXML
    private void addRecord(ActionEvent event) {
        addData();
    }

    @FXML
    private void readRecord(ActionEvent event) {
        readFirebase();
        select.setDisable(false);
    }

    @FXML
    private void deleteRecord(ActionEvent event) {
        deleteData();
        readFirebase();
    }

    @FXML
    private void updateRecord(ActionEvent event) {
        updateData();
        readFirebase();
        nameField.clear();
        majorField.clear();
        ageField.clear();
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("WebContainer.fxml");
    }

    public void addData() {
        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        nameField.clear();
        majorField.clear();
        ageField.clear();
    }

    public boolean readFirebase() {
        key = false;
        listOfUsers.clear();
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (!documents.isEmpty()) {
                for (QueryDocumentSnapshot document : documents) {
                    setOutputList();
                    person = new Person(String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString()));
                    listOfUsers.add(person);
                }
            } else {
                System.out.println("No data");
            }
            key = true;
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }
    
    private void deleteData() {
        try {
            String docID = "";
            ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Person curr = new Person(String.valueOf(document.getData().get("Name")),
                        document.getData().get("Major").toString(),
                        Integer.parseInt(document.getData().get("Age").toString()));
                if (person.equals(curr)) {
                    docID = document.getId();
                    ApiFuture<WriteResult> writeResult = App.fstore.collection("References").document(docID).delete();
                    System.out.println("Update time : " + writeResult.get().getUpdateTime());
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(AccessFBView.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail");
        }
    }

    public void updateData() {
        String docID = "";
        try {
            ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Person curr = new Person(String.valueOf(document.getData().get("Name")),
                        document.getData().get("Major").toString(),
                        Integer.parseInt(document.getData().get("Age").toString()));
                if (person.equals(curr)) {
                    docID = document.getId();
                }
            }
            // Update an existing document
            DocumentReference docRef = App.fstore.collection("References").document(docID);
            // (async) Update one field
            ApiFuture<WriteResult> futureUpdate = docRef.update("Name", nameField.getText(),
                    "Major",majorField.getText(),"Age",ageField.getText());
            // ...
            WriteResult result = futureUpdate.get();
            //System.out.println("Write result: " + result);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(AccessFBView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void selectData(){
        nameField.setText(person.getName());
        majorField.setText(person.getMajor());
        ageField.setText(""+person.getAge());
        update.setDisable(false);
        delete.setDisable(false);
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty());
    }
    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        nameField.clear();
        majorField.clear();
        ageField.clear();
        update.setDisable(true);
        delete.setDisable(true);
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
        person = outputList.getSelectionModel().getSelectedItem();
    }
    public void logOut() throws IOException {
        App.setRoot("SignIn.fxml");
    }
}
