package sample;

import javafx.scene.image.ImageView;

public class Lap {
    private ImageView imageView;
    private String lap;

    public Lap() {
        
    }

    public Lap(ImageView imageView, String lap) {
        this.imageView = imageView;
        this.lap = lap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getLap() {
        return lap;
    }

    public void setLap(String lap) {
        this.lap = lap;
    }
}
