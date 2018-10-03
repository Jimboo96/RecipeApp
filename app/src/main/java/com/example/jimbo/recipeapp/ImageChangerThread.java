package com.example.jimbo.recipeapp;

import java.util.Random;

public class ImageChangerThread extends Thread {
    private ImageChanger imgChanger;
    private String imageString;

    void setNotifier(ImageChanger imgChanger) {
        this.imgChanger = imgChanger;
    }

    public void run() {
        try {
            while(true) {
                if (imgChanger != null) {
                    setIcon();
                    imgChanger.changeImage(imageString);
                    Thread.sleep(7500);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setIcon() {
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;
        imageString = "food" + randomNumber;
    }
}


