package com.peteralbus.service.image_detection;

import com.peteralbus.domain.Img;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class image_detection{
    private static final String SAVED_MODEL_PATH = "/home/PeterAlbus/check_fish/model_/saved_model";
    private static List<String> FILE_PATHS;
    private static final String IMAGE_TENSOR_NAME = "image_tensor:0";
    private static final String DETECTION_BOXES_NAME = "detection_boxes:0";
    private static final String DETECTION_SCORES_NAME = "detection_scores:0";
    private static final String DETECTION_CLASSES_NAME = "detection_classes:0";
    private static final String DETECTION_MULTICLASS_SCORES = "detection_multiclass_scores:0";
    private static final String NUM_DETECTIONS = "num_detections:0";
    private static final String RAW_DETECTION_SCORES = "raw_detection_scores:0";
    private static final String RAW_DETECTION_BOXES = "raw_detection_boxes:0";

    public static Img performInference(String FILE_PATH) {
        SavedModelBundle model = null;
        Tensor<UInt8> imageTensor = null;
        List<Tensor<?>> outputs = null;

//		for (String FILE_PATH : FILE_PATHS) {

        try {
            model = SavedModelBundle.load(SAVED_MODEL_PATH, "serve");
            imageTensor = makeImageTensor(FILE_PATH);

            outputs = model
                    .session()
                    .runner()
                    .feed(IMAGE_TENSOR_NAME, imageTensor)
                    .fetch(DETECTION_CLASSES_NAME)
                    .fetch(DETECTION_SCORES_NAME)
                    .fetch(DETECTION_BOXES_NAME)
//                    .fetch(NUM_DETECTIONS)
                    .run();

            try (Session session = new Session(model.graph())) {
                //输出class
                Tensor t = outputs.get(0);
                float[][] detection_classes = new float[1][300];
                t.copyTo(detection_classes);
                float tar_class = detection_classes[0][0];

                //输出sore
                t = outputs.get(1);
                float[][] detection_scores = new float[1][300];
                t.copyTo(detection_scores);
                float tar_score = detection_scores[0][0];

                //画框
                t = outputs.get(2);
                float[][][] detection_boxes = new float[1][300][4];
                t.copyTo(detection_boxes);
                float[] tar_box = detection_boxes[0][0];
                String FILE_PATH_TAR = image_drawbox.Draw(tar_box[0], tar_box[1], tar_box[2], tar_box[3], FILE_PATH);

                Img detection = initDetection("", FILE_PATH, FILE_PATH_TAR);
                detection.setTarClass(tar_class);
                detection.setTarScore(tar_score);
                if((tar_box[2]-tar_box[0]) > (tar_box[3]-tar_box[1])){
                    detection.setTarLength((tar_box[2]-tar_box[0]));
                }
                else {
                    detection.setTarLength((tar_box[3]-tar_box[1]));
                }
                return detection;

                //NUM
//                t = outputs.get(3);
//                float[][][] detection_multiclass_scores = new float[1][300][3];
//                t.copyTo(detection_multiclass_scores);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            // this closes Session and Graph that belongs to model as well
            if (model != null) {
                model.close();
            }

            if (imageTensor != null) {
                imageTensor.close();
            }

            if (outputs != null) {
                for (Tensor output : outputs) {
                    if (output != null) {
                        output.close();
                    }
                }
            }
        }
//		}


    }

    private static Img initDetection(String i_name, String i_path,String t_path){
        Img detection=new Img();
        detection.setId(0);
        detection.setImgName(i_name);
        detection.setImgPath(i_path);
        detection.setTarPath(t_path);
        detection.setTarClass(0);
        detection.setTarLength(0);
        detection.setTarScore(0);
        return detection;
    }

    public static Tensor<UInt8> makeImageTensor(String filename) throws IOException {
        BufferedImage img = ImageIO.read(new File(filename));
        if (img.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            throw new IOException(
                    String.format("Expected 3-byte BGR encoding in BufferedImage, found %d (file: %s). This code could be made more robust",
                            img.getType(), filename)
            );
        }

        byte[] data = ((DataBufferByte) img.getData().getDataBuffer()).getData();
        // ImageIO.read seems to produce BGR-encoded images, but the model expects RGB.
        bgr2rgb(data);
        final long BATCH_SIZE = 1;
        final long CHANNELS = 3;
        long[] shape = new long[]{BATCH_SIZE, img.getHeight(), img.getWidth(), CHANNELS};
        Tensor<UInt8> imageTensor = Tensor.create(UInt8.class, shape, ByteBuffer.wrap(data));
        img.flush();
        return imageTensor;
    }

    public static void bgr2rgb(byte[] data) {
        for (int i = 0; i < data.length; i += 3) {
            byte tmp = data[i];
            data[i] = data[i + 2];
            data[i + 2] = tmp;
        }
    }

}