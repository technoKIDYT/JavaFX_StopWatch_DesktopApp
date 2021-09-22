package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Label lblTimer;

    @FXML
    ImageView btnStart, btnStop, btnPause, btnLap;

    @FXML
    ProgressBar progress;

    @FXML
    ListView<Lap> lapsData;

    @FXML
    AnchorPane header;

    Timeline timeline;

    LocalTime time = LocalTime.parse("00:00:00");
    LocalTime tempTime = LocalTime.parse("00:00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    int lapsCounter;
    ObservableList<Lap> laps = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        progress.setProgress(0);
        enableDisableControls(true, btnPause);
        lapsData.setCellFactory(listView -> new ListCell<Lap>() {
            @Override
            protected void updateItem(Lap lap, boolean empty) {
                super.updateItem(lap, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a HBox to hold our displayed value
                    HBox hBox = new HBox(5);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.setSpacing(15);
                    // Add the values from our piece to the HBox
                    hBox.getChildren().addAll(
                            lap.getImageView(),
                            new Label(lap.getLap())
                    );
                    // Set the HBox as the display
                    setGraphic(hBox);
                }
            }
        });
    }

    @FXML
    private void startTimer() {
        enableDisableControls(false, btnLap);
        enableDisableControls(true, btnStart);
        enableDisableControls(false, btnPause);
        timeline.play();
        progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    @FXML
    private void pauseTimer(MouseEvent event) {
        progress.setProgress(0);
        enableDisableControls(true, btnLap);
        enableDisableControls(false, btnStart);
        if (timeline.getStatus().equals(Animation.Status.PAUSED)) {
            timeline.play();
        } else if (timeline.getStatus().equals(Animation.Status.RUNNING)) {
            timeline.pause();
        }
    }

    @FXML
    private void endTimer(MouseEvent mouseEvent) {
        enableDisableControls(true, btnLap);
        enableDisableControls(false, btnStart);
        enableDisableControls(true, btnPause);
        lapsCounter = 0;
        lapsData.getItems().clear();
        progress.setProgress(0);
        timeline.stop();
        time = LocalTime.parse("00:00:00");
        lblTimer.setText(time.format(dtf));
    }

    private void incrementTime() {
        time = time.plusSeconds(1);
        lblTimer.setText(time.format(dtf));
    }

    @FXML
    private void addLaps(MouseEvent mouseEvent) {
        lapsCounter += 1;
        tempTime = tempTime.plusHours(time.getHour()).plusMinutes(time.getMinute()).plusSeconds(time.getSecond());
        ImageView imageView = new ImageView();
        System.out.println("PATH ->" + System.getProperty("user.dir") + "\\src\\Sample\\grayFlag.png");
        imageView.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\grayFlag.png").toURI().toString()));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);

        laps.add(new Lap(imageView, new String(lapsCounter + "          " + "+ " + time + "         " + tempTime)));
        lapsData.setItems(laps);
    }

    public void enableDisableControls(boolean disable, ImageView imageView) {
        if (disable) {
            if (imageView.equals(btnLap)) {
                btnLap.setDisable(true);
                btnLap.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\grayFlag.png").toURI().toString()));
            } else if (imageView.equals(btnStart)) {
                btnStart.setDisable(true);
                btnStart.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\grayPlay.png").toURI().toString()));
            } else if (imageView.equals(btnPause)) {
                btnPause.setDisable(true);
                btnPause.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\grayPause.png").toURI().toString()));
            }

        } else {
            if (imageView.equals(btnLap)) {
                btnLap.setDisable(false);
                btnLap.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\flag.png").toURI().toString()));
            } else if (imageView.equals(btnStart)) {
                btnStart.setDisable(false);
                btnStart.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\play.png").toURI().toString()));
            } else if (imageView.equals(btnPause)) {
                btnPause.setDisable(false);
                btnPause.setImage(new Image(new File(System.getProperty("user.dir") + "\\src\\Sample\\pause.png").toURI().toString()));
            }
        }
    }

}
