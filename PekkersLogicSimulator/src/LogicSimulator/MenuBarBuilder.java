/*
 * Copyright (C) 2015 PEKKA
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package LogicSimulator;

import LogicSimulator.Objects.Gates.GateObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author PEKKA
 */
public class MenuBarBuilder {

    private final Mesh3D mesh = new Mesh3D();

    private final Main main;

    public MenuBarBuilder(final Main main) {
        this.main = main;
    }

    public MenuBar buildMenuBarWithMenus() {
        final MenuBar menuBar = new MenuBar();

        // Prepare left-most 'File' drop-down menu
        Menu fileMenu = new Menu("File");
        MenuItem fileNew = new MenuItem("New");/*       null all the lists!!!       */

        fileNew.setOnAction((ActionEvent event) -> {
            main.initschematic();
            event.consume();
        });

        MenuItem fileOpen = new MenuItem("Open");
        fileOpen.setDisable(true);
        fileOpen.setOnAction((ActionEvent event) -> {
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
                    //in.readObject();
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    try {
                        fileIn.close();
                        //fileIn = null;
                        System.gc();
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            event.consume();
        });

        MenuItem fileSave = new MenuItem("Save");
        fileSave.setDisable(true);

        MenuItem fileSaveAs = new MenuItem("Save As");
        fileSaveAs.setDisable(true);
        fileSaveAs.setOnAction((ActionEvent event) -> {
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
                    //out.writeObject();
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    try {
                        fileOut.flush();
                        fileOut.close();
                        fileOut = null;
                        System.gc();
                        main.showOnConsole("something Saved successfully");

                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            event.consume();
        });

        MenuItem fileExit = new MenuItem("Exit");         // quit
        fileExit.setOnAction((ActionEvent event) -> {
            System.out.println("Exiting this shi.. witty app ");
            Platform.exit();
            event.consume();
        });
        fileExit.setGraphic(new ImageView(Textures.getHmImage("exiticon")));

        fileMenu.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs, new SeparatorMenuItem(), fileExit);

        // Prepare 'Extras' drop-down menu
        Menu extrasMenu = new Menu("Extras");
        extrasMenu.setDisable(false);

        MenuItem SymbolsMenuItem = new MenuItem("use rectangular shapes");  // distinctive shapes
        SymbolsMenuItem.setDisable(false);
        SymbolsMenuItem.setOnAction((ActionEvent event) -> {
            if (main.rectangularSymbols) {
                main.rectangularSymbols = false;
                SymbolsMenuItem.setText("use rectangular shapes");
            } else if (!main.rectangularSymbols) {
                main.rectangularSymbols = true;
                SymbolsMenuItem.setText("use distinctive shapes");
            }
            for (GateObject next : main.gateObjects) {
                next.updateSymbol(0);
            }
            event.consume();
        });
        extrasMenu.getItems().add(SymbolsMenuItem);
        MenuItem menuItem002 = new MenuItem("002");
        menuItem002.setDisable(true);
        extrasMenu.getItems().add(menuItem002);

        // Prepare 'Help' drop-down menu
        Menu helpMenu = new Menu("Help");

        RadioMenuItem infoPopupMenuItem = new RadioMenuItem("Info Popup");
        infoPopupMenuItem.setSelected(true);
        infoPopupMenuItem.setOnAction((ActionEvent event) -> {
            if (!InfoPopup.getEnabled()) {
                InfoPopup.setEnabled(true);
            } else if (InfoPopup.getEnabled()) {
                InfoPopup.setEnabled(false);
            }
            event.consume();
        });

        MenuItem searchMenuItem = new MenuItem("Search");
        searchMenuItem.setDisable(true);

        MenuItem onlineManualMenuItem = new MenuItem("Online Manual");
        onlineManualMenuItem.setVisible(false);

        MenuItem aboutMenuItem = new MenuItem("Help");
        aboutMenuItem.setOnAction((ActionEvent event) -> {
            main.showOnConsole("About Menu Item was clicked");

            final Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setResizable(false);
            dialogStage.setWidth(400);
            dialogStage.setHeight(550);
            dialogStage.setTitle("Help");
            Text aboutLabel = new Text("Tis tha rumored help window that yo been lookin´ fo \nleft click for moving gates and adding lines\nmiddle click for removal\nright click for toggling switches and editing labels");
            //aboutLabel.setAlignment(Pos.BASELINE_CENTER);
            //aboutLabel.setWrapText(true);
            Button okButt = new Button("Ok");
            okButt.setCancelButton(true);
            okButt.setDefaultButton(true);
            okButt.setOnAction((ActionEvent arg0) -> {
                mesh.timeline.stop();
                dialogStage.close();
            });
            VBox vBox = new VBox();
            dialogStage.setScene(new Scene(vBox));

            Group gg = new Group();
            init(gg, dialogStage);

            vBox.setAlignment(Pos.BASELINE_CENTER);
            vBox.getChildren().addAll(aboutLabel);
            vBox.getChildren().add(gg);
            vBox.getChildren().addAll(okButt);

            dialogStage.show();
            event.consume();
        });
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1, KeyCombination.SHORTCUT_ANY));

        helpMenu.getItems().addAll(infoPopupMenuItem, new SeparatorMenuItem(), searchMenuItem, onlineManualMenuItem, new SeparatorMenuItem(), aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, extrasMenu, helpMenu);
        // bind width of menu bar to width of associated stage
        menuBar.prefWidthProperty().bind(main.primaryStage.widthProperty());
        return menuBar;
    }

    private void init(Group root, Stage primaryStage) {
        root.setDepthTest(DepthTest.ENABLE);
        primaryStage.getScene().setCamera(new PerspectiveCamera());
        root.getChildren().add(mesh.start(root, primaryStage));
    }

}
