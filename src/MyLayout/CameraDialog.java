package MyLayout;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
interface ImageSetter{
    public void setimage();
    public void ErrorWebcame();
}
public class CameraDialog implements Initializable {
    @FXML
    ImageView imageView;
    protected ImageSetter imageSetter;

    protected boolean Capture;
    protected String Filename ;
    @FXML
    private void Capture(){
                Stage stage = (Stage) imageView.getScene().getWindow();
                stage.close();
                imageSetter.setimage();
    }


    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public void shutdown() {
        Capture = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Capture = true;
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    void data() throws FrameGrabber.Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);
        try {
            grabber.start();
            opencv_core.IplImage image = grabber.grab();
            while (image != null && Capture ) {
                image = grabber.grab();
                cvSaveImage(Filename, image);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Filename);
                        Image im = new Image(file.toURI().toString());
                        imageView.setImage(im);
                    }
                });

                Thread.sleep(60);
            }

            grabber.stop();

        } catch (FrameGrabber.Exception e) {
            grabber.stop();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Stage stage = (Stage) imageView.getScene().getWindow();
                    stage.close();
                    imageSetter.ErrorWebcame();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    Task task = new Task<Void>() {
        @Override
        public Void call() throws Exception {
            data();

            return null;
        }
    };

}
