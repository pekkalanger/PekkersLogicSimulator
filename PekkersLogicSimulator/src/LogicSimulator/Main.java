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

import LogicSimulator.GateObjects.GateLogic.LogicLine;
import LogicSimulator.GateObjects.ConnectionLineObject;
import LogicSimulator.GateObjects.GateObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    int mainWidth = 1024;
    int mainHeight = 768;
    int schematicWidth = mainWidth-50;
    int schematicHeigth = 500;
    int consoleWidth = 700;
    int consoleHeight = 200;
    
    /*      schematic objects       */ // will be saved/laoded and nulled on New.
    public List<GateObject> gateObjects;
    public List<Line> lines;
    public List<LogicLine> logicLines;
    public List<ConnectionLineObject> connectionLineObjects;
    public LinkedList<Circle> circleList = null;
    
    
    //create a console for logging mouse events
    final ListView<String> console = new ListView<String>();
    
    //create a rectangle - (XXXpx X XXXpx) in which our circles can move
    Rectangle rectangle;
    
    //variables for storing initial position before drag of circle
    public double initX;
    public double initY;
    public Point2D dragAnchor;
    public MenuBar menuBar;
    public Group schematicGroup;
    public Group circleGroup;  // "the scetch"
    public VBox rootGroup;
    public VBox rootVBox;
    public VBox sideBar;
    public HBox rootHBox;
    public Stage primaryStage;
    Timeline timeline;
    
        //create a observableArrayList of logged events that will be listed in console
    final ObservableList<String> consoleObservableList = FXCollections.observableArrayList();{
        //set up the console
        console.setItems(consoleObservableList);
        //console.setLayoutY(55);
        console.setPrefSize(consoleWidth, consoleHeight);
    }
    
    private void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        gateObjects = new ArrayList();
        lines = new ArrayList();
        connectionLineObjects = new ArrayList<ConnectionLineObject>();
        
        
        primaryStage.setTitle("P.I.L.L.S pekkers incredibly logical logic simulator"); 
        primaryStage.setResizable(false); // this aint working so far
        
        rootGroup = new VBox(2);        // contains menuBar and rootHBox
        rootHBox = new HBox(2);         // contains sideBar and rootVBox
        rootVBox = new VBox(2);         // contains rectangle and console
        rootHBox.setSpacing(1f);
        rootVBox.setSpacing(5f);
        //sideBar = new VBox();         // contains sidebar items
        //menuBar = new MenuBar();      // a most excelent menubar
        schematicGroup = new Group();   // where gateGroup and lineGroup? r comin  
        circleGroup = new Group();      // gateGroup (atm circleGroup)
        
        circleList = new LinkedList<Circle>();
        logicLines = new LinkedList<LogicLine>();
        lines = new LinkedList<Line>();

        
        MenuBarBuilder classyMenuBar = new MenuBarBuilder(this);
        menuBar = classyMenuBar.buildMenuBarWithMenus();
        rootGroup.getChildren().add(menuBar);
        
        SideBarBuilder classySideBarBuilder = new SideBarBuilder(this);
        sideBar = classySideBarBuilder.buildSideBarWithButtons();
        rootHBox.getChildren().add(sideBar);
        
        rectangle = new Rectangle(schematicWidth, schematicHeigth);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(1, Color.rgb(156,216,255)), new Stop(0, Color.rgb(156,216,255, 0.5))
            }));
        
        // we can set mouse event to any node, also on the rectangle
        rectangle.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //log mouse move to console, method listed below
                //showOnConsole("Mouse moved, x: " + me.getX() + ", y: " + me.getY() );
            }
        });

        rectangle.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                double translateX = event.getDeltaX();
                double translateY = event.getDeltaY();

                // reduce the deltas for the circles to stay in the screen
                for (Circle c : circleList) {
                    if (c.getTranslateX() + translateX + c.getRadius() > mainWidth) {
                        translateX = mainWidth - c.getTranslateX() - c.getRadius();
                    }
                    if (c.getTranslateX() + translateX - c.getRadius() < 0) {
                        translateX = - c.getTranslateX() + c.getRadius();
                    }
                    if (c.getTranslateY() + translateY + c.getRadius() > mainHeight) {
                        translateY = mainHeight - c.getTranslateY() - c.getRadius();
                    }
                    if (c.getTranslateY() + translateY - c.getRadius() < 0) {
                        translateY = - c.getTranslateY() + c.getRadius();
                    }
                }

                // move the circles
                for (Circle c : circleList) {
                    c.setTranslateX(c.getTranslateX() + translateX);
                    c.setTranslateY(c.getTranslateY() + translateY);
                }
                // log event
                showOnConsole("Scrolled, deltaX: " + event.getDeltaX() + ", deltaY: " + event.getDeltaY());
            }
        });
        
        schematicGroup.getChildren().add(rectangle);
        rectangle.toBack();
        schematicGroup.getChildren().add(circleGroup);

        rootVBox.getChildren().add(schematicGroup);
        rootVBox.getChildren().add(console);
        
        rootHBox.getChildren().add(rootVBox);
        rootGroup.getChildren().add(rootHBox);
        
        Scene scene = new Scene(rootGroup, mainWidth,mainHeight);
        primaryStage.setScene(scene);
        
        Image defaultCursorImage = Textures.defaultCursor;
        ImageCursor imageCursor = new ImageCursor(defaultCursorImage, -defaultCursorImage.getWidth(), -defaultCursorImage.getHeight());
        scene.setCursor(imageCursor);
        
    }
    
    public void showOnConsole(String text) {
         //if there is 8 items in list, delete first log message, shift other logs and  add a new one to end position
         if (consoleObservableList.size()==8){
            consoleObservableList.remove(0);
         }
         consoleObservableList.add(text);
    }

    protected final void buildAndSetLoop() {        // this vill update everything 100 times per second / once every 1.666 seconds
        final int fps = 100; //  if toggle then 100on + 100off = 200/2 hertz
        //int i;
        final Duration oneFrameAmt = Duration.millis(1000/fps);  // "100 fps" should be enough.. for nao
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
            new EventHandler() {
                @Override
                public void handle(Event event) {
                    System.out.println("==============START===============");
                    Long delta = 0L;
                    //System.out.println(deltaTime *1000 + " ms?");
                    for (Iterator<GateObject> iterator = gateObjects.iterator(); iterator.hasNext(); /*nop*/ ) {
                        GateObject next = iterator.next();
                        next.update(delta);   
                    }
                    /*
                    for (Iterator<LogicLine> iterator = logicLines.iterator(); iterator.hasNext();) {
                        LogicLine next = iterator.next();
                        next.update(delta);   
                    }
                    */
                    for (Iterator<ConnectionLineObject> iterator = connectionLineObjects.iterator(); iterator.hasNext(); /*nop*/ ) {
                        ConnectionLineObject next = iterator.next();
                        if(next != null && next.logicLine != null ){
                            next.update(delta);  
                            if(next.logicLine != null && next.logicLine.getOutputPin(0) != null && next.logicLine.getOutputPin(0).getDataObject() != null){
                                if(next.logicLine.getOutputPin(0).getDataObject().getData() == true){
                                    next.line.setStroke(Color.GREEN);
                                }
                                if(next.logicLine.getOutputPin(0).getDataObject().getData() == false){
                                    next.line.setStroke(Color.RED);
                                }
                            }
                        }
                    }
                    System.out.println("===============END================");
                }
        }); // oneFrame

        // sets the apps loop (Timeline)
        timeline = new Timeline(oneFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
      
    @Override public void start(Stage primaryStage) throws Exception {
        Textures.init();
        Globals.main = this;
        init(primaryStage);
        buildAndSetLoop();
        primaryStage.show();
        
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts,
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}