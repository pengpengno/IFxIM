package com.ifx.opencv;

import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

import java.io.File;

/**
 * @author pengpeng
 * @description
 * @date 2023/1/10
 */
public class OpencvTestCase {
    public static void main(String[] args) throws Exception {
        avutil.av_log_set_level(avutil.AV_LOG_INFO);
        FFmpegLogCallback.set();
        String flowUrl1 = "rtsp://test:test@192.168.18.167:554/h264/ch1/main/av_stream";
        String flowUrl2 = "rtsp://test:test@192.168.18.167:554/h264/ch1/main/av_stream";
        FFmpegFrameGrabber rtspGrabber1 = new FFmpegFrameGrabber(flowUrl1);
        FFmpegFrameGrabber rtspGrabber2 = new FFmpegFrameGrabber(flowUrl2);
        FFmpegFrameGrabber jpgGrabber = new FFmpegFrameGrabber(new File("/Users/wangxianzhi306/Downloads/ffplay/1.jpg"));
        FFmpegFrameGrabber audioGrabber = new FFmpegFrameGrabber(new File("/Users/wangxianzhi306/Downloads/ffplay/test1.aac"));
        initGrabber(rtspGrabber1);
        initGrabber(rtspGrabber2);
        initGrabber(jpgGrabber);
        initGrabber(audioGrabber);
        String videoFilter = "nullsrc=size=1920x2160[base];[0:v]setpts=PTS-STARTPTS,crop=1920:1080:100:100,scale=1920:-1[base0];[1:v]setpts=PTS-STARTPTS,crop=1920:1080:100:100,scale=1920:-1[base1];[base][base0]overlay=shortest=1[baseTmp0];[baseTmp0][base1]overlay=shortest=1:y=1080[vout];[vout][3]overlay=x=mod(100*t\\,main_w):y=abs(sin(t))*main_h*0.7[pvout];[pvout]drawtext=fontsize=100:fontfile=/System/Library/Fonts/Supplemental/Zapfino.ttf:text='helloworld':fontcolor=green:x=mod(100*t\\,main_w):y=abs(sin(t))*main_h*0.7:alpha=0.5:box=1:boxcolor=yellow:enable=1[v]";
        String audioFilter = "[2:a]volume=1[a]";
        FFmpegFrameFilter filter = new FFmpegFrameFilter(videoFilter, audioFilter, 1920, 2160, 1);
        filter.setAudioInputs(3);
        filter.setVideoInputs(2);
        filter.start();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(new File("/Users/wangxianzhi306/Downloads/testwav/stream0.flv"), 1920, 2160,
                1);
        recorder.setFormat("flv");
        recorder.setInterleaved(false);
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setVideoOption("preset", "ultrafast");
        recorder.setVideoOption("crf", "26");
        recorder.setVideoOption("threads", "1");
        recorder.setFrameRate(25);
        recorder.setGopSize(25);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.setOption("keyint_min", "25");
        recorder.setTrellis(1);
        recorder.setMaxDelay(0);
        recorder.start();
        Frame frame, frame1, frame2, frame3, frame4;
        int i = 0;
        while ((frame1 = rtspGrabber1.grab()) != null && (frame2 = rtspGrabber2.grab()) != null) {
            if (i == 1000) {
                break;
            }
            frame3 = audioGrabber.grabFrame();
            frame4 = jpgGrabber.grab();
            filter.push(0, frame1);
            filter.push(1, frame2);
            filter.push(2, frame3);
            filter.push(3, frame4);
            frame = filter.pullImage();
            if (frame != null && frame.image != null) {
                recorder.record(frame, avutil.AV_PIX_FMT_YUV420P);
            }
            i++;
        }
        rtspGrabber1.close();
        rtspGrabber2.close();
        audioGrabber.close();
        jpgGrabber.close();
        filter.close();
        recorder.close();
    }

    private static boolean initGrabber(FFmpegFrameGrabber grabber) {
        grabber.setOption("stimeout", "15000000");
        grabber.setOption("threads", "1");
        grabber.setOption("buffer_size", "1024000");
        grabber.setOption("rw_timeout", "5000000");
        grabber.setOption("probesize", "5000000");
        grabber.setOption("analyzeduration", "5000000");
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setOption("rtsp_flags", "prefer_tcp");
        try {
            grabber.start(true);
            AVFormatContext avFormatContext = grabber.getFormatContext();
            int streamNum = avFormatContext.nb_streams();
            if (streamNum < 1) {
                return false;
            }
            return true;
        } catch (FrameGrabber.Exception e) {
        }
        return false;
    }
}
