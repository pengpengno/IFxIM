//package com.ifx.videoTest;
//
//import com.google.common.io.Files;
//import io.netty.channel.ChannelHandlerContext;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ListView;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.TransferMode;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import javax.swing.text.html.ImageView;
//import java.awt.*;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.net.InetSocketAddress;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/1/10
// */
//public class ClientUI extends Application implements Serializable, EventHandler<ActionEvent> {
//    private static final long serialVersionUID = 1L;
//    private Integer count = 2;
//    private String[] buttontxt = {"发送", "发送文件", "视频通话", "音频通话"};
//
//    private ListView<Object> listView;
//    private ImageView imageView = new ImageView();
//    private String user_name = "张三";
//    private Text text = new Text("用户聊天窗口");
//    private Button[] button = new Button[buttontxt.length];
//    private TextArea textarea = new TextArea();
//    private FileChooser fileChooser = new FileChooser();
//
//    private Alert alertss = new Alert(Alert.AlertType.INFORMATION);
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private List<Object> contentLabelList = new ArrayList<>();
//    private ExecutorService forkJoin = Executors.newFixedThreadPool(10);
//
//    private ImageView video_image = new ImageView();
//    private ImageView audio_image = new ImageView();
//    private Stage video_stage = UI.get_video_ui("服务端视频通话", video_image, this);
//    private Stage audio_stage = UI.get_audio_ui("服务端音频通话", audio_image, this);
//    private STasks tasks = new STasks(this);
//    private Stage stage;
//
//
//    public Server_Task() throws IOException {
////		datagram.socket().setReceiveBufferSize(Integer.MAX_VALUE);
////		datagram.socket().setSendBufferSize(Integer.MAX_VALUE);
////		datagram.socket().setBroadcast(true);
////		datagram.socket().setReuseAddress(true);
//
//        fileChooser.setInitialDirectory(new File("D:\\桌面"));
//        fileChooser.setTitle("选择文件");
////		fileChooser.getExtensionFilters().addAll(
////				new ExtensionFilter("JPG","*.jpg"),
////				new ExtensionFilter("PNG","*.png"),
////				new ExtensionFilter("GIF","*.gif"),
////				new ExtensionFilter("PSD","*.psd"),
////				new ExtensionFilter("PM4","*.mp4"),
////				new ExtensionFilter("MP3","*.mp3")
////				);
//
//        for (int a = 0; a < buttontxt.length; a++) {
//            button[a] = new Button(buttontxt[a]);
//            button[a].setOnAction(this);
//        }
//
//        textarea.setTooltip(new Tooltip("此处为发送内容"));
//        textarea.setPromptText("请输入发送内容");
//        textarea.setFocusTraversable(false);
//
//        textarea.setOnDragOver(x -> {
//            if (x.getGestureSource() != textarea)
//                x.acceptTransferModes(TransferMode.ANY);
//        });
//        textarea.setOnDragDropped(x -> {
//            Dragboard dragboard = x.getDragboard();
//            List<File> file = dragboard.getFiles();
//            for (File file2 : file) {
//                File[] files = file2.isDirectory() ? file2.listFiles(b -> b.length() < 1024 * 1024 * 50) : new File[]{file2};
//                for (File file3 : files) send_files(file3);
//            }
//        });
//    }
//
//    @Override
//    public void handle(ActionEvent event) {
//        try {
//            switch (((Button) event.getSource()).getText()) {
//                case "发送":
//                    send_byte(new Datas(DATA_TYPE.TEXT, user_name, textarea[0].getText().getBytes()));
//                    break;
//                case "发送文件":
//                    File files = fileChooser.showOpenDialog(stage);
//                    if (files == null) break;
//                    forkJoin.execute(() -> send_files(files));
//                    break;
//                case "视频通话":
//                    Datas datas = Datas.builder().user_name(user_name).type(DATA_TYPE.VIDEO).build();
//                    send_byte(datas);
//                    break;
//                case "音频通话":
//                    Datas datas2 = Datas.builder().user_name(user_name).type(DATA_TYPE.AUDIO).build();
//                    send_byte(datas2);
//                    break;
//                case "结束视频通话":
//                    tasks.getService().getVideo_stop().stop();
//                    Datas datas3 = Datas.builder().user_name(user_name).type(DATA_TYPE.VIDEO_END).build();
//                    send_byte(datas3);
//                    if (video_stage.isShowing()) video_stage.close();
//                    break;
//                case "结束音频通话":
//                    Thread thread2 = tasks.getService().getAudio_stop();
//                    if (thread2 != null && thread2.isAlive()) thread2.stop();
//                    Datas datas4 = Datas.builder().user_name(user_name).type(DATA_TYPE.AUDIO_END).build();
//                    send_byte(datas4);
//                    if (audio_stage.isShowing()) audio_stage.close();
//                    break;
//                case "通话录音":
//                    tasks.setIs_luzhi(true);
//                    System.out.println(tasks.getIs_luzhi());
//                    Button buttons = ((Button) event.getSource());
//                    buttons.setText("正在录制");
//                    buttons.setStyle("-fx-background-color:orangered;-fx-font-color:while");
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        this.stage = primaryStage;
//        alertss.setTitle("服务端系统提示");
////		contentLabelList.add(sdf.format(new Date(System.currentTimeMillis())) + ":");
////		ImageView imageView = new ImageView(image[0]);
////		imageView.setFitWidth(800);
////		imageView.setFitHeight(400);
////		contentLabelList.add(imageView);
//        listView = new ListView<Object>(FXCollections.observableArrayList(contentLabelList));
//        listView.getStylesheets().add(getClass().getResource("/javafx/listview.css").toExternalForm());
//        HBox hroot = new HBox(10);
//        hroot.getChildren().addAll(button[0], button[1], button[2], button[3]);
//        hroot.setAlignment(Pos.CENTER_RIGHT);
//        //HBox.setMargin(button[1], new Insets(0, 0, 0, 20));
//        VBox vroot = new VBox(20);
//        vroot.getChildren().addAll(text, listView, textarea[0], hroot);
//        vroot.setAlignment(Pos.CENTER);
//        vroot.setPadding(new Insets(10, 10, 10, 10));
//        //VBox.setMargin(textarea[0], new Insets(20, 20, 0, 0));
//        //KeyCodeCombination keys = new KeyCodeCombination(KeyCode.ENTER);
//        Scene scene = new Scene(vroot, 500, 600);
//        //scene.getAccelerators().put(keys,()->button[0].fire());
//        scene.getStylesheets().add(getClass().getResource("/javafx/javafx.css").toExternalForm());
//        primaryStage.getIcons().add(image[0]);
//        primaryStage.setTitle("服务端");
//        //primaryStage.setAlwaysOnTop(true);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        forkJoin.execute(tasks);
//    }
//
//
//    private InetSocketAddress inet = new InetSocketAddress("127.0.0.1", 8083);
//
//    public void send_byte(Datas data) {
//        ChannelHandlerContext channel = tasks.getTcp_channels();
//        if (channel != null) {
//            System.out.println("socketChannel " + channel);
//            channel.writeAndFlush(data);
//        }
//    }
//
//
//    public void send_files(File files) {
//        Boolean is_image = files.getName().matches(".*\\.(jpg|png|gif|jpeg)$");
//        try (ByteArrayOutputStream outputs = new ByteArrayOutputStream();) {
//            Files.copy(files, outputs);
//            if (files.length() < 1024 * 1024 * 10) {
//                Datas datas = Datas.builder()
//                        .type(is_image ? DATA_TYPE.IMAGE : DATA_TYPE.FILE)
//                        .file_name(files.getName())
//                        .data_size(files.length())
//                        .datas(outputs.toByteArray())
//                        .user_name(user_name)
//                        .build();
//                send_byte(datas);
//            } else send_byte_limit2(files, outputs.toByteArray(), 1024 * 1024);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void send_byte_limit(File files, byte[] bytes, int skip) {
//        int start = 0, end = 0;
//        while (start < bytes.length) {
//            end = start + skip;
//            if (end > bytes.length) end = bytes.length;
//            byte[] inputs = Arrays.copyOfRange(bytes, start, end);
//            start = end;
//            Datas datas = Datas.builder()
//                    .type(DATA_TYPE.FILE)
//                    .file_name(files.getName())
//                    .data_size(files.length())
//                    .isfen(1)
//                    .datas(inputs)
//                    .user_name(user_name)
//                    .build();
//            send_byte(datas);
//        }
//        send_byte(Datas.builder().type(DATA_TYPE.FILE).isend(1).build());
//    }
//
//    public static void main(String[] args) throws Exception {
//        launch(args);
//    }
//
//}