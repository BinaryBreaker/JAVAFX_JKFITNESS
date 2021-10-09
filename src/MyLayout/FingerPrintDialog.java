package MyLayout;

import com.jfoenix.controls.JFXButton;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

interface TemplateSetter {
    public void SetTemplate(String string);

}


public class FingerPrintDialog implements Initializable {
    private static final long serialVersionUID = 1L;


    //the width of fingerprint image
    int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;
    //for verify test
    private byte[] lastRegTemp = new byte[2048];
    //the length of lastRegTemp
    private int cbRegTemp = 0;
    //pre-register template
    private byte[][] regtemparray = new byte[3][2048];
    //Register
    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;

    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];


    private boolean mbStop = true;
    private long mhDevice = 0;
    private long mhDB = 0;
    private WorkThread workThread = null;

    @FXML
    ImageView imageView;
    @FXML
    Text textArea;

    private TemplateSetter templateSetter = null;

    public void setTemplateSetter(TemplateSetter templateSetter) {
        this.templateSetter = templateSetter;
    }


    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值。
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (0 != mhDevice) {
            //already inited
            textArea.setText("Please close device first!\n");
            return;
        }
        int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
        //Initialize
        cbRegTemp = 0;
        bRegister = false;
        bIdentify = false;
        iFid = 1;
        enroll_idx = 0;

        ret = FingerprintSensorEx.GetDeviceCount();
        if (ret < 0) {
            textArea.setText("No devices connected!\n");
            FreeSensor();
            return;
        }
        if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0))) {
            textArea.setText("Open device fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }
        if (0 == (mhDB = FingerprintSensorEx.DBInit())) {
            textArea.setText("Init DB fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }

        //For ISO/Ansi
        int nFmt = 0;    //Ansi

        FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);
        //For ISO/Ansi End

        //set fakefun off
        //FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        //GetFakeOn
        //size[0] = 4;
        //FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
        //nFakeFunOn = byteArrayToInt(paramValue);

        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
        fpWidth = byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
        fpHeight = byteArrayToInt(paramValue);

        imgbuf = new byte[fpWidth * fpHeight];
        //btnImg.resize(fpWidth, fpHeight);
        mbStop = false;
        workThread = new WorkThread();
        workThread.setDaemon(true);
        workThread.start();// 线程启动
        textArea.setText("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight + "\n");

        if (0 == mhDevice) {
            textArea.setText("Please Open device first!\n");
            return;
        }
        if (!bRegister) {
            enroll_idx = 0;
            bRegister = true;
            textArea.setText("Please your finger 3 times!\n");
        }
    }

    private void FreeSensor() {
        mbStop = true;
        try {        //wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != mhDB) {
            FingerprintSensorEx.DBFree(mhDB);
            mhDB = 0;
        }
        if (0 != mhDevice) {
            FingerprintSensorEx.CloseDevice(mhDevice);
            mhDevice = 0;
        }
        FingerprintSensorEx.Terminate();
        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();

                    int ret = 0;
                    while (!mbStop) {
                        templateLen[0] = 2048;
                        if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
                            if (nFakeFunOn == 1) {
                                byte[] paramValue = new byte[4];
                                int[] size = new int[1];
                                size[0] = 4;
                                int nFakeStatus = 0;
                                //GetFakeStatus
                                ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
                                nFakeStatus = byteArrayToInt(paramValue);
                                System.out.println("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
                                if (0 == ret && (byte) (nFakeStatus & 31) != 31) {
                                    textArea.setText("Is a fake finger?\n");
                                    return;
                                }
                            }
                            OnCatpureOK(imgbuf);
                            OnExtractOK(template, templateLen[0]);
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


        }
    }




    public void shutdown() {
        FreeSensor();
    }

    private void OnCatpureOK(byte[] imgBuf) {
        try {
            writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
            File file = new File("fingerprint.bmp");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < templateLen[0]; i++) {
            result.append(String.format("%02x", bytes[i]));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth + 3) / 4) * 4);
        int bfType = 0x424d; // 位图文件类型（0—1字节）
        int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大小（2—5字节）
        int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
        int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
        int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

        dos.writeShort(bfType); // 输入位图文件类型'BM'
        dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
        dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

        int biSize = 40;// 信息头所需的字节数（14-17字节）
        int biWidth = nWidth;// 位图的宽（18-21字节）
        int biHeight = nHeight;// 位图的高（22-25字节）
        int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
        int biBitcount = 8;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
        int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
        int biSizeImage = w * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
        int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
        int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
        int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
        int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

        dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
        dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
        dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
        dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
        dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
        dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
        dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
        dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
        dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth) {
            filter = new byte[w - nWidth];
        }

        for (int i = 0; i < nHeight; i++) {
            dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w - nWidth);
        }
        dos.flush();
        dos.close();
        fos.close();
    }

    private void OnExtractOK(byte[] template, int len) {
        if (bRegister) {
            int[] fid = new int[1];
            int[] score = new int[1];
            int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
            if (ret == 0) {
                textArea.setText("the finger already enroll by " + fid[0] + ",cancel enroll\n");
                bRegister = false;
                enroll_idx = 0;
                return;
            }
            if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {
                textArea.setText("please press the same finger 3 times for the enrollment\n");
                return;
            }
            System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
            enroll_idx++;
            if (enroll_idx == 3) {
                int[] _retLen = new int[1];
                _retLen[0] = 2048;
                byte[] regTemp = new byte[_retLen[0]];

                if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2], regTemp, _retLen)) &&
                        0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
                    iFid++;
                    cbRegTemp = _retLen[0];
                    System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                    //Base64 Template
                    String base64 = FingerprintSensorEx.BlobToBase64(regTemp, _retLen[0]);
                    templateSetter.SetTemplate(hex(regTemp));
                    textArea.setText("enroll succ:\n");
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            FreeSensor();
                        }
                    });


                } else {
                    textArea.setText("enroll fail, error code=" + ret + "\n");
                }
                bRegister = false;
            } else {
                textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint\n");
            }
        } else {
            if (bIdentify) {
                int[] fid = new int[1];
                int[] score = new int[1];
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                if (ret == 0) {
                    textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] + "\n");
                } else {
                    textArea.setText("Identify fail, errcode=" + ret + "\n");
                }

            } else {
                if (cbRegTemp <= 0) {
                    textArea.setText("Please register first!\n");
                } else {
                    int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
                    if (ret > 0) {
                        textArea.setText("Verify succ, score=" + ret + "\n");
                    } else {
                        textArea.setText("Verify fail, ret=" + ret + "\n");
                    }
                }
            }
        }
    }

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    public static byte[] intToByteArray(final int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

}
