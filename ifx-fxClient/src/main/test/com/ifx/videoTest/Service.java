//package com.ifx.videoTest;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import net.coobird.thumbnailator.Thumbnails;
//import org.bytedeco.javacv.Java2DFrameConverter;
//import org.bytedeco.javacv.OpenCVFrameGrabber;
//
//import javax.sound.sampled.*;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.net.InetSocketAddress;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/1/10
// */
//public final class Service {
//
//    private Thread video_stop,audio_stop;
//    private static Java2DFrameConverter converter = new Java2DFrameConverter();
//    private static OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
//    private AudioFormat audioFormat;
//    private SourceDataLine line2;
//
//    public Thread getVideo_stop() {
//        return video_stop;
//    }
//
//    public Thread getAudio_stop() {
//        return audio_stop;
//    }
//
//    public SourceDataLine getLine2() {
//        return line2;
//    }
//
//    public AudioFormat getAudioFormat() {
//        return audioFormat;
//    }
//
//
//    public Service(){
//        try {
//            audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);
//            DataLine.Info dataLineInfo2 = new DataLine.Info(SourceDataLine.class, audioFormat);
//            line2 = (SourceDataLine) AudioSystem.getLine(dataLineInfo2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void send_audio(Channel udp_channel,InetSocketAddress inet_socket) {
//        audio_stop = new Thread(()->{
//            TargetDataLine line = null;
//            try {
//                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
//                line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
//                line.open(audioFormat);
//                line.start();
//                byte[] bytes = new byte[1024];
//                AudioInputStream inputStream = new AudioInputStream(line);
//                for (int data = 0; (data = inputStream.read(bytes)) > 0;) {
//                    ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
//                    byte_out.write(bytes, 0, data);
//                    byte[] bytes2 = byte_out.toByteArray();
//                    byte_out.close();
//                    //if(is_play)line2.write(bytes, 0, data);
//                    //if(is_luzhi)bytess.write(bytes, 0, data);
//                    ByteBuf byte_buffer= Unpooled.buffer(1024);
//                    byte_buffer.writeInt(1);
//                    byte_buffer.writeBytes(bytes2);
//                    udp_channel.writeAndFlush(new DatagramPacket(byte_buffer,inet_socket));
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }finally{
//                line.stop();
//                line.close();
//            }
//        });
//        audio_stop.start();
//    }
//
//    public void send_video(Channel udp_channel, ImageView images, InetSocketAddress inet_socket){
//        video_stop = new Thread(()->{
//            try {
//                grabber.start();
//                for(Frame frames;(frames = grabber.grab())!=null;){
//                    ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
//                    Thumbnails.of(converter.convert(frames))
//                            .scale(0.4f).outputQuality(0.4f)
//                            .outputFormat("jpeg").toOutputStream(byte_out);
//                    byte[] bytes = byte_out.toByteArray();byte_out.close();
//                    images.setImage(new Image(new ByteArrayInputStream(bytes)));
//                    ByteBuf byte_buffer= Unpooled.buffer(1024);
//                    byte_buffer.writeInt(0);
//                    byte_buffer.writeBytes(bytes);
//                    udp_channel.writeAndFlush(new DatagramPacket(byte_buffer,inet_socket));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                try {
//                    grabber.stop();
//                    grabber.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        video_stop.start();
//    }
//}