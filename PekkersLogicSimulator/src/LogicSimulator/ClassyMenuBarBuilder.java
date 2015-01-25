/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicSimulator;

import LogicSimulator.GateObjects.ConnectionLineObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PEKKA
 */
public class ClassyMenuBarBuilder {
    final Main main;
    public ClassyMenuBarBuilder(final Main main) {
        this.main = main;
    }
            
    public MenuBar buildMenuBarWithMenus(){
        final MenuBar menuBar = new MenuBar();

        // Prepare left-most 'File' drop-down menu
        final javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        final MenuItem fileNew = new MenuItem("New");
        fileNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            /*       null all the lists!!!       */
            main.schematicGroup.getChildren().remove(main.circleGroup);
            main.circleGroup = new Group();
            main.schematicGroup.getChildren().add(main.circleGroup);
          
            main.gateObjects = new ArrayList();
            main.lines = new ArrayList();
            main.connectionLineObjects = new ArrayList<ConnectionLineObject>();
        
            event.consume();
            }
        });
                
        final MenuItem fileOpen = new MenuItem("Open");
         fileOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Schematic");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("schematic", "*.schematic"));
                File file = fileChooser.showOpenDialog(main.primaryStage);
                if (file != null) {
                    main.showOnConsole(file.toString());
                    FileInputStream fileIn = null;
                    try {
                        fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        //circleList = (LinkedList<Circle>) in.readObject();
                    }  catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        try {
                            fileIn.close();
                            fileIn = null;
                            System.gc();
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                event.consume();
            }
        });
         
        final MenuItem fileSave = new MenuItem("Save");
        
        final MenuItem fileSaveAs = new MenuItem("Save As");
        fileSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Schematic");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("schematic", "*.schematic"));
                File file = fileChooser.showSaveDialog(main.primaryStage);
                if (file != null) {
                    FileOutputStream fileOut = null;
                    try {
                        fileOut = new FileOutputStream(file);
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        //out.writeObject(circleList);
                    }  catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        try {
                            fileOut.flush();
                            fileOut.close();
                            fileOut = null;
                            main.showOnConsole("Saved successfully");
                            System.gc();
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                event.consume();
            }
        });
        
        final MenuItem fileExit = new MenuItem("Exit");         // quit
        fileExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.out.println("Exiting this shitty app ");
                Platform.exit(); 
                event.consume();
            }
        });
        fileExit.setGraphic(new ImageView(Textures.exitIcon));
        
        fileMenu.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs, new SeparatorMenuItem(), fileExit);

        // Prepare 'Examples' drop-down menu
        final javafx.scene.control.Menu examplesMenu = new javafx.scene.control.Menu("Extras");
        
        examplesMenu.getItems().add(new MenuItem("001"));
        examplesMenu.getItems().add(new MenuItem("002"));
        examplesMenu.getItems().add(new MenuItem("003"));
        
        // Prepare 'Help' drop-down menu
        final javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("Help");
        
        final MenuItem searchMenuItem = new MenuItem("Search");
        searchMenuItem.setDisable(true);
        
        final MenuItem onlineManualMenuItem = new MenuItem("Online Manual");
        onlineManualMenuItem.setVisible(false);
        
        final MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                main.showOnConsole("About Menu Item was clicked");
                
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initStyle(StageStyle.UTILITY);//UNDECORATED);//
                dialogStage.setResizable(false);
                dialogStage.setTitle("About");
                Label aboutLabel = new Label("Tis tha rumored about window that yo been lookin´ fo \nleft for move and add lines\nmiddle for removal\nright for toggling switches");
                aboutLabel.setAlignment(Pos.BASELINE_CENTER);
                Button okButt = new Button("Ok");
                okButt.setCancelButton(true);
                okButt.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.close();
                    }
                });
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_CENTER);
                hBox.setSpacing(40.0);
                hBox.getChildren().addAll(okButt);
                VBox vBox = new VBox();
                vBox.setSpacing(40.0);
                vBox.getChildren().addAll(aboutLabel, hBox);

                dialogStage.setScene(new Scene(vBox));
                dialogStage.show();
                event.consume();
            }
        });
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1, KeyCombination.SHORTCUT_ANY));
                
        helpMenu.getItems().addAll(searchMenuItem, onlineManualMenuItem, new SeparatorMenuItem(), aboutMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, examplesMenu, helpMenu);
        // bind width of menu bar to width of associated stage
        menuBar.prefWidthProperty().bind(main.primaryStage.widthProperty());
        return menuBar;
    }
}
