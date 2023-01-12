//package com.ifx.videoTest;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufInputStream;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioDatagramChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.serialization.ClassResolver;
//import io.netty.handler.codec.serialization.ClassResolvers;
//import io.netty.handler.codec.serialization.ObjectDecoder;
//import io.netty.handler.codec.serialization.ObjectEncoder;
//import io.netty.handler.logging.LoggingHandler;
//import javafx.application.Platform;
//import javafx.concurrent.Task;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import lombok.extern.slf4j.Slf4j;
//import net.coobird.thumbnailator.Thumbnails;
//
//import javax.sound.sampled.*;
//import javax.xml.ws.Service;
//import java.awt.*;
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//import java.util.concurrent.ExecutorService;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/1/10
// */
//@Slf4j
//public class Client extends Task<ClientUI> implements Serializable {
//    private static final long serialVersionUID = 1L;
//    private static final Rectangle dimension = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//    private static Robot robots;
//    private static InetSocketAddress inet_socket = new InetSocketAddress("localhost",8083);
//    static {
//        try {
//            robots = new Robot();
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Client_Task messages;
//    private volatile boolean is_luzhi = false;
//    private ExecutorService forkJoin;
//    private ByteArrayOutputStream bytess = new ByteArrayOutputStream();
//    private Service service = new Service();
//
//    private Channel tcp_channel;
//    private Channel udp_channel;
//    private EventLoopGroup udp_group = new NioEventLoopGroup();
//    private EventLoopGroup tcp_group = new NioEventLoopGroup();
//    private ClassResolver classLoader = ClassResolvers.cacheDisabled(ClassLoader.getSystemClassLoader());
//
//    public boolean getIs_luzhi() {
//        return this.is_luzhi;
//    }
//    public Service getService() {
//        return service;
//    }
//    public Channel getTcp_channel() {
//        return tcp_channel;
//    }
//    public void setUdp_channel(Channel udp_channel) {
//        this.udp_channel = udp_channel;
//    }
//    public void setIs_luzhi(boolean is_luzhi) {
//        this.is_luzhi = is_luzhi;
//    }
//
//
//    public CTasks(Client_Task messages) {
//        this.messages = messages;
//        this.forkJoin = messages.getForkJoin();
//    }
//
//    @Override
//    protected Client_Task call() throws Exception {
//        stratClientTCP();
//        stratClientUDP();
//        return messages;
//    }
//
//    private void send_video(ImageView video_image) {
//        forkJoin.execute(() -> {
//            try {
//                ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
//                Thumbnails.of(robots.createScreenCapture(dimension))
//                        .scale(0.3f)
//                        .outputQuality(0.3f)
//                        .outputFormat("jpg")
//                        .toOutputStream(byte_out);
//
//                byte[] bytes = byte_out.toByteArray();
//                ByteArrayInputStream inputs = new ByteArrayInputStream(bytes);
//
//                video_image.setImage(new Image(inputs));
//                if (udp_channel != null)udp_channel.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer(bytes),inet_socket));
//
//                //byte_out.flush();
//                byte_out.close();
//                inputs.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    });
//}
//
//    private void stratClientTCP() {
//        forkJoin.execute(() -> {
//            try {
//                Bootstrap b = new Bootstrap();
//                b.group(tcp_group)
//                        .channel(NioSocketChannel.class) // 非阻塞模式
//                        .option(ChannelOption.TCP_NODELAY, true)
//                        .option(ChannelOption.SO_KEEPALIVE, true)
//                        .handler(new ChannelInitializer<SocketChannel>() {
//                            @Override
//                            protected void initChannel(SocketChannel ch) throws Exception {
//                                ch.pipeline()
//                                        .addLast(new LoggingHandler("DEBUG"))
//                                        .addLast(new ObjectDecoder(Integer.MAX_VALUE,classLoader))
//                                        .addLast(new ObjectEncoder())
//                                        .addLast(new SimpleChannelInboundHandler<Object>() {
//                                            @Override
//                                            protected void channelRead0(ChannelHandlerContext ctx, Object msg)
//                                                    throws Exception {
//                                                exetype(ctx,(Datas) msg);
//                                            }
//                                        });
//                            }
//                        });
//                ChannelFuture channel = b.connect("localhost",8082).sync();
//                System.out.println("stratclientTCP");
//                (tcp_channel = channel.channel()).writeAndFlush(new Datas().setType((byte) 100)).channel().closeFuture().sync();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    private void stratClientUDP() {
//        forkJoin.execute(() -> {
//            try {
//                Bootstrap bootstrap = new Bootstrap();
//                bootstrap.group(udp_group)
//                        .channel(NioDatagramChannel.class)
//                        .option(ChannelOption.SO_BROADCAST,true)
//                        .option(ChannelOption.WRITE_BUFFER_WATER_MARK,new WriteBufferWaterMark(65535,65535))
//                        .option(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(65535,65535,65535))
//                        .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR,new DefaultMessageSizeEstimator(65535))
//                        .handler(new ChannelInitializer<NioDatagramChannel>() {
//                            @Override
//                            protected void initChannel(NioDatagramChannel ch) throws Exception {
//                                ch.pipeline()
//                                        .addLast(new LoggingHandler("DEBUG"))
//                                        .addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
//                                            @Override
//                                            protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
//                                                    throws Exception {
//                                                ByteBuf bytebuf= msg.content();
//                                                int type = bytebuf.readInt();
//                                                byte[] bytes = new byte[bytebuf.readableBytes()];
//                                                bytebuf.readBytes(bytes);
//                                                if(type==0){
//                                                    //System.out.println("视频");
//                                                    messages.getVideo_image().setImage(new Image(new ByteBufInputStream(Unpooled.wrappedBuffer(bytes))));
//                                                }else if(type==1){
//                                                    //System.out.println("音频");
//                                                    if(getIs_luzhi()){
//                                                        System.out.println("录制");
//                                                        bytess.write(bytes);
//                                                    }
//                                                    SourceDataLine source = service.getLine2();
//                                                    if(!source.isOpen()){
//                                                        source.open(service.getAudioFormat());
//                                                        source.start();
//                                                    }
//                                                    source.write(bytes,0,bytes.length);
//                                                }
//                                            }
//                                        });
//                            }
//                        });
//                ChannelFuture channel = bootstrap.bind("localhost",8084).sync();
//                System.out.println("stratclientUDP");
//                setUdp_channel(channel.channel());
//                channel.channel().closeFuture().sync();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("stratServerUDP");
//        });
//    }
//
//    private void exetype(ChannelHandlerContext ctx, Datas data) {
//        log.info("客户端收到数据............");
//        Alert alert = messages.getAlertss();
//        Stage audio_stage = messages.getAudio_stage();
//        Stage video_stage = messages.getVideo_stage();
//        try {
//            switch (data.getType()) {
//                case DATA_TYPE.VIDEO:
//                    alert.setAlertType(AlertType.CONFIRMATION);
//                    alert.setContentText("用户 " + data.getUser_name() + " 邀请你视频通话是否接受");
//                    Platform.runLater(() -> {
//                        Optional<ButtonType> buttons = alert.showAndWait();
//                        if (buttons.get() == ButtonType.OK) {
//                            video_stage.show();
//                            messages.send_byte(Datas.builder().user_name(messages.getUser_name()).type(DATA_TYPE.VIDEO_OK).build());
//                        } else
//                            messages.send_byte(Datas.builder().user_name(messages.getUser_name()).type(DATA_TYPE.VIDEO_NO).build());
//                    });
//                    break;
//                case DATA_TYPE.VIDEO_END:
//                    //service.setVideo_stop(true);
//                    Thread thread = service.getVideo_stop();
//                    if(thread!=null && thread.isAlive())thread.stop();
//
//                    Platform.runLater(()->{
//                        if(video_stage.isShowing())
//                            video_stage.close();
//                    });
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setContentText("通话已经结束");
//                    Platform.runLater(()->alert.showAndWait());
//                    break;
//                case DATA_TYPE.VIDEO_NO:
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setContentText("用户 " + data.getUser_name() + " 拒绝了你的请求");
//                    Platform.runLater(()->alert.showAndWait());
//                    break;
//                case DATA_TYPE.VIDEO_OK:
//                    //service.setVideo_stop(false);
//                    Platform.runLater(()->video_stage.show());
//                    service.send_video(udp_channel,messages.getVideo_image(),inet_socket);
//                    break;
//                case DATA_TYPE.AUDIO:
//                    alert.setAlertType(AlertType.CONFIRMATION);
//                    alert.setContentText("用户 " + data.getUser_name() + " 邀请你语音通话是否接受");
//                    Platform.runLater(() -> {
//                        Optional<ButtonType> video_buttons = alert.showAndWait();
//                        if (video_buttons.get() == ButtonType.OK) {
//                            audio_stage.show();
//                            messages.send_byte(Datas.builder().user_name(messages.getUser_name()).type(DATA_TYPE.AUDIO_OK).build());
//                        } else
//                            messages.send_byte(Datas.builder().user_name(messages.getUser_name()).type(DATA_TYPE.AUDIO_OK).build());
//                    });
//                    break;
//                case DATA_TYPE.AUDIO_END:
//                    Thread thread2 = service.getAudio_stop();
//                    if(thread2!=null && thread2.isAlive())thread2.stop();
//                    Platform.runLater(() ->{if(audio_stage.isShowing())audio_stage.close();});
//
//                    String path = null;
//                    if(getIs_luzhi()){
//                        String thistime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
//                        File outfile = new File("D:\\桌面\\audio",thistime+"录制.wav");
//                        path = outfile.getPath();
//                        if(!outfile.getParentFile().exists())outfile.getParentFile().mkdirs();
//                        byte[] datas = bytess.toByteArray();
//                        AudioFormat audioFormat = service.getAudioFormat();
//                        AudioInputStream inputs = new AudioInputStream(new ByteArrayInputStream(datas),audioFormat,datas.length/audioFormat.getFrameSize());
//                        AudioSystem.write(inputs, AudioFileFormat.Type.WAVE,outfile);
//                    }
//
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setContentText(getIs_luzhi()?"通话已经结束\n录音保存位置:"+path:"通话已经结束");
//                    setIs_luzhi(false);
//                    Platform.runLater(() -> alert.showAndWait());
//                    break;
//                case DATA_TYPE.AUDIO_NO:
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setContentText("用户 " + data.getUser_name() + " 拒绝了你的请求");
//                    Platform.runLater(() -> alert.showAndWait());
//                    break;
//                case DATA_TYPE.AUDIO_OK:
//                    Platform.runLater(() -> audio_stage.show());
//                    service.send_audio(udp_channel,inet_socket);
//                    break;
//                case DATA_TYPE.TEXT:
//                    Button button = new Button(new String(data.getDatas()));
//                    button.setTextFill(Color.WHITE);
//                    button.setStyle("-fx-background-color:cadetblue");
//                    String times = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                    messages.getListView().getItems().addAll(times,button);
//                    break;
//                case DATA_TYPE.IMAGE:
//                    ByteArrayOutputStream outs = new ByteArrayOutputStream();
//                    Thumbnails
//                            .of(new ByteArrayInputStream(data.getDatas()))
//                            .size(300,300)
//                            .outputQuality(1.0f)
//                            .outputFormat("jpeg")
//                            .toOutputStream(outs);
//                    ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(outs.toByteArray())));
//
//                    imageView.setOnMouseClicked(x->{
//                        ContextMenu contextMenu = new ContextMenu();
//                        contextMenu.getItems().addAll(new MenuItem("保存"),new MenuItem("复制"));
//                        Platform.runLater(() -> contextMenu.show(imageView,imageView.getX(),imageView.getY()));
//                        System.out.println(x);
//                    });
//                    imageView.setOnDragDropped(x->{
//                        System.out.println("1"+x.getGestureSource());
//                    });
//                    imageView.setOnDragEntered(x->{
//                        System.out.println("2"+x.getGestureSource());
//                    });
//                    imageView.setOnDragExited(x->{
//                        System.out.println("3"+x.getGestureSource());
//                    });
//
//                    String times2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                    messages.getListView().getItems().addAll(times2,imageView);
//                    break;
//                case DATA_TYPE.FILE:
//                    alert.setAlertType(AlertType.CONFIRMATION);
//                    alert.setContentText("用户 " + data.getUser_name() + "\n发来文件  " + data.getFile_name() + "\n文件大小  "
//                            + getSize(data.getData_size() * 1.0) + " 是否接收");
//                    FileChooser fileChooser = messages.getFileChooser();
//                    fileChooser.setTitle("选择保存位置");
//                    fileChooser.getExtensionFilters()
//                            .addAll(new ExtensionFilter("ALL","*.*"),new ExtensionFilter("JPG","*.jpg"),new ExtensionFilter("PNG","*.png"));
//                    fileChooser.setInitialFileName(data.getFile_name());
//                    Platform.runLater(() -> {
//                        try {
//                            Optional<ButtonType> files = alert.showAndWait();
//                            if (files.get() == ButtonType.OK) {
//                                File filess = fileChooser.showSaveDialog(messages.getStage());
//                                if(filess==null)return;
//                                Files.write(data.getDatas(),filess);
//                                alert.setContentText("保存成功");
//                                alert.showAndWait();
//                            }
//                        } catch (IOException e) {
//                            alert.setAlertType(Alert.AlertType.ERROR);
//                            alert.setContentText("保存失败");
//                            alert.showAndWait();
//                            e.printStackTrace();
//                        }
//                    });
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getSize(Double length) {
//        Double KB = 1024d, MB = 1024d * 1024d, GB = 1024d * 1024d * 1024d, TB = 1024d * 1024d * 1024d * 1024d;
//        return (length > KB
//                ? length > MB
//                ? length > GB ? length > TB ? String.format("%.2fTB", length / TB)
//                : String.format("%.2fGB", length / GB) : String.format("%.2fMB", length / MB)
//                : String.format("%.2fKB", length / KB)
//                : length.toString());
//    }
//}