package com.ifx.bytecpp;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Demo {
    public static void main(String[] args) throws Exception {
        // Create a frame grabber for your camera (you can also load a video file)
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);

        try {
            // Start the grabber
            grabber.start();

            // Create a canvas frame for displaying the video
            CanvasFrame canvasFrame = new CanvasFrame("Video Processing");
            canvasFrame.setCanvasSize(800, 600);

            // Create a frame converter
            OpenCVFrameConverter.ToMat frameConverter = new OpenCVFrameConverter.ToMat();

            while (true) {
                Frame frame = grabber.grab();
                if (frame == null) {
                    break;
                }
                frame.data

                // Process the frame (e.g., perform some OpenCV operations)
                // Modify the frame here as needed

                // Display the processed frame
//                canvasFrame.showImage(frameConverter.convert(frame));
                canvasFrame.showImage(frame);

                // Exit the application when the user closes the window
                if (canvasFrame.getCanvas().isShowing()) {
                    canvasFrame.setDefaultCloseOperation(CanvasFrame.DO_NOTHING_ON_CLOSE);
                } else {
                    break;
                }
            }

            // Release resources
            grabber.stop();
            canvasFrame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}