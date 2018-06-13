package cn.edu.sdu.uims.component.complex.avsdk.test.java.com.qiniu.pili;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core.Mat;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import org.bytedeco.javacv.OpenCVFrameConverter;

import org.bytedeco.javacv.FrameGrabber.Exception;

import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;

import java.awt.Component;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import static org.bytedeco.javacpp.opencv_imgcodecs.imread;  
public class CanvasTest {
	private static boolean isStart = true;// 用于控制录制结束

//	public static CanvasFrame example() throws Exception, InterruptedException // 测试
//	{
//		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
//		grabber.start(); // 开始获取摄像头数据
//		CanvasFrame canvas = new CanvasFrame("摄像头");// 新建一个窗口
//		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		canvas.setAlwaysOnTop(true);
//
//		while (true) {
//			if (!canvas.isDisplayable()) {// 窗口是否关闭
//				grabber.stop();// 停止抓取
//				System.exit(2);// 退出
//			}
//			canvas.showImage(grabber.grab());// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
//
//			Thread.sleep(50);// 50毫秒刷新一次图像
//		}
//	}
//
//	public static void recordCamera(String outputFile, double frameRate) // 推流or录像
//			throws Exception, InterruptedException, org.bytedeco.javacv.FrameRecorder.Exception {
//		Loader.load(opencv_objdetect.class);
//
//		FrameGrabber grabber = FrameGrabber.createDefault(0);// 本机摄像头默认0，这里使用javacv的抓取器，至于使用的是ffmpeg还是opencv
//		grabber.start();// 开启抓取器
//
//		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();// 转换器
//		IplImage grabbedImage = converter.convert(grabber.grab());// 抓取一帧视频并将其转换为图像，至于用这个图像用来做什么？自行添加水印，人脸识别等等
//		int width = grabbedImage.width();
//		int height = grabbedImage.height();
//
//		FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height);
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
//		recorder.setFormat("flv");// 封装格式，如果是推送到rtmp就必须是flv封装格式
//		recorder.setFrameRate(frameRate);
//		recorder.start();// 开启录制器
//		long startTime = 0;
//		long videoTS = 0;
//		CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
//		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//		// frame.setAlwaysOnTop(true);
//		Frame rotatedFrame = new Frame();
//		while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
//			rotatedFrame = converter.convert(grabbedImage); // 不知道为什么这里不做转换就不能推到rtmp
//			frame.showImage(rotatedFrame);
//			if (startTime == 0) {
//				startTime = System.currentTimeMillis();
//			}
//			videoTS = 1000 * (System.currentTimeMillis() - startTime);
//			recorder.setTimestamp(videoTS);
//			recorder.record(rotatedFrame);
//			Thread.sleep(40);
//		}
//		frame.dispose();
//		recorder.stop();
//		recorder.release();
//		grabber.stop();
//
//	}

	public static void frameRecord(String inputFile, String outputFile, int audioChannel)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {// * 按帧录制视频
		// *inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径

		// 获取视频源
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
		// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
		// 开始取视频源
		recordByFrame(grabber, recorder, isStart);
	}

	private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		try {// 建议在线程中使用该方法
			grabber.start();
			recorder.start();
			Frame frame = null;
			while (status && (frame = grabber.grabFrame()) != null) {
				recorder.record(frame);
			}
			recorder.stop();
			grabber.stop();
		} finally {
			if (grabber != null) {
				grabber.stop();
			}
		}
	}
	
	public static Mat imRead(String filePath) throws IOException{  
		 Mat  image = imread("/Users/imac/Downloads/test.jpg", IMREAD_COLOR);  
	      if (image==null||image.empty()) {  
	        System.out.println("读取图像失败，图像为空"); 
	        return null;
	      } else {
	    	  	return image;
	      }
	  }  

	public static void audioRecord(int AUDIO_DEVICE_INDEX,String outputFile,  
            int captureWidth, int captureHeight, int FRAME_RATE) throws IOException {
		long startTime = 0;  
	    long videoTS = 0; 
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();// 转换器
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, captureWidth, captureHeight, 2);  
        recorder.setVideoOption("crf", "25");  
        // 2000 kb/s, 720P视频的合理比特率范围  
        recorder.setVideoBitrate(2000000);  
        // h264编/解码器  
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);  
        // 封装格式flv  
        recorder.setFormat("flv");  
        // 视频帧率(保证视频质量的情况下最低25，低于25会出现闪屏)  
        recorder.setFrameRate(FRAME_RATE);  
        // 关键帧间隔，一般与帧率相同或者是视频帧率的两倍  
        recorder.setGopSize(FRAME_RATE * 2);  
        // 不可变(固定)音频比特率  
        recorder.setAudioOption("crf", "0");  
        // 最高质量  
        recorder.setAudioQuality(0);  
        // 音频比特率  
        recorder.setAudioBitrate(192000);  
        // 音频采样率  
        recorder.setSampleRate(44100);  
        // 双通道(立体声)  
        recorder.setAudioChannels(2);  
        // 音频编/解码器  
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);  
        System.out.println("开始录制...");    
        try {  
            recorder.start();  
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e2) {  
            if (recorder != null) {  
                System.out.println("关闭失败，尝试重启");  
                try {  
                    recorder.stop();  
                    recorder.start();  
                } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
                    try {  
                        System.out.println("开启失败，关闭录制");  
                        recorder.stop();  
                        return;  
                    } catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {  
                        return;  
                    }  
                }  
            }  
  
        }  
        // 音频捕获  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                /** 
                 * 设置音频编码器 最好是系统支持的格式，否则getLine() 会发生错误 
                 * 采样率:44.1k;采样率位数:16位;立体声(stereo);是否签名;true: 
                 * big-endian字节顺序,false:little-endian字节顺序(详见:ByteOrder类) 
                 */  
                AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);  
  
                // 通过AudioSystem获取本地音频混合器信息  
                Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();  
                // 通过AudioSystem获取本地音频混合器  
                Mixer mixer = AudioSystem.getMixer(minfoSet[AUDIO_DEVICE_INDEX]);  
                // 通过设置好的音频编解码器获取数据线信息  
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);  
                try {  
                    // 打开并开始捕获音频  
                    // 通过line可以获得更多控制权  
                    // 获取设备：TargetDataLine line  
                    // =(TargetDataLine)mixer.getLine(dataLineInfo);  
                    TargetDataLine line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);  
                    line.open(audioFormat);  
                    line.start();  
                    // 获得当前音频采样率  
                    int sampleRate = (int) audioFormat.getSampleRate();  
                    // 获取当前音频通道数量  
                    int numChannels = audioFormat.getChannels();  
                    // 初始化音频缓冲区(size是音频采样率*通道数)  
                    int audioBufferSize = sampleRate * numChannels;  
                    byte[] audioBytes = new byte[audioBufferSize];  
  
                    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);  
                    exec.scheduleAtFixedRate(new Runnable() {  
                        @Override  
                        public void run() {  
                            try {  
                                // 非阻塞方式读取  
                                int nBytesRead = line.read(audioBytes, 0, line.available());  
                                // 因为我们设置的是16位音频格式,所以需要将byte[]转成short[]  
                                int nSamplesRead = nBytesRead / 2;  
                                short[] samples = new short[nSamplesRead];  
                                /** 
                                 * ByteBuffer.wrap(audioBytes)-将byte[]数组包装到缓冲区 
                                 * ByteBuffer.order(ByteOrder)-按little-endian修改字节顺序，解码器定义的 
                                 * ByteBuffer.asShortBuffer()-创建一个新的short[]缓冲区 
                                 * ShortBuffer.get(samples)-将缓冲区里short数据传输到short[] 
                                 */  
                                ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);  
                                // 将short[]包装到ShortBuffer  
                                ShortBuffer sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);  
                                // 按通道录制shortBuffer  
                                recorder.recordSamples(sampleRate, numChannels, sBuff);  
                            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
                                e.printStackTrace();  
                            }  
                        }  
                    }, 0, (long) 1000 / FRAME_RATE, TimeUnit.MILLISECONDS);  
                } catch (LineUnavailableException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }).start();  
  
        // javaCV提供了优化非常好的硬件加速组件来帮助显示我们抓取的摄像头视频  
        CanvasFrame cFrame = new CanvasFrame("Capture Preview", 1);  
        Frame capturedFrame = converter.convert(imRead("/Users/imac/Downloads/test.jpg"));  
        // 执行抓取（capture）过程  
        while (cFrame.isVisible()) {  
             
                //本机预览要发送的帧  
                cFrame.showImage(capturedFrame);  
        
            //定义我们的开始时间，当开始时需要先初始化时间戳  
            if (startTime == 0)  
                startTime = System.currentTimeMillis();  
  
            // 创建一个 timestamp用来写入帧中  
            videoTS = 1000 * (System.currentTimeMillis() - startTime);  
            //检查偏移量  
            if (videoTS > recorder.getTimestamp()) {  
                System.out.println("Lip-flap correction: " + videoTS + " : " + recorder.getTimestamp() + " -> "  
                        + (videoTS - recorder.getTimestamp()));  
                //告诉录制器写入这个timestamp  
                recorder.setTimestamp(videoTS);  
            }  
            // 发送帧  
            try {  
                recorder.record(capturedFrame);  
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
                System.out.println("录制帧发生异常，什么都不做");  
            }  
        }   
        cFrame.dispose();  
        try {  
            if (recorder != null) {  
                recorder.stop();  
            }  
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
            System.out.println("关闭录制器失败");  
           
        }       
	}
//
//
//	public static void main(String url)
//			throws Exception, InterruptedException, org.bytedeco.javacv.FrameRecorder.Exception {
//		recordCamera(url, 25);
//
//	}
//	// public static void main()
//	// throws FrameRecorder.Exception, FrameGrabber.Exception, InterruptedException
//	// {
//	//
//	// String inputFile = "rtmp://pili-live-rtmp.sportshot.cn/sportshot/EEvvee";
//	// // Decodes-encodes
//	// String outputFile = "recorde.mp4";
//	// frameRecord(inputFile, outputFile,1);
//	// }
//

	 public static void recordWebcamAndMicrophone(int WEBCAM_DEVICE_INDEX, int AUDIO_DEVICE_INDEX, String outputFile,  
	            int captureWidth, int captureHeight, int FRAME_RATE) throws org.bytedeco.javacv.FrameGrabber.Exception {  
	        long startTime = 0;  
	        long videoTS = 0;  
	        /** 
	         * FrameGrabber 类包含：OpenCVFrameGrabber 
	         * (opencv_videoio),C1394FrameGrabber, FlyCaptureFrameGrabber, 
	         * OpenKinectFrameGrabber,PS3EyeFrameGrabber,VideoInputFrameGrabber, 和 
	         * FFmpegFrameGrabber. 
	         */  
	        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(WEBCAM_DEVICE_INDEX);  
	        grabber.setImageWidth(captureWidth);  
	        grabber.setImageHeight(captureHeight);  
	        System.out.println("开始抓取摄像头...");  
	        int isTrue = 0;// 摄像头开启状态  
	        try {  
	            grabber.start();  
	            isTrue += 1;  
	        } catch (org.bytedeco.javacv.FrameGrabber.Exception e2) {  
	            if (grabber != null) {  
	                try {  
	                    grabber.restart();  
	                    isTrue += 1;  
	                } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {  
	                    isTrue -= 1;  
	                    try {  
	                        grabber.stop();  
	                    } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {  
	                        isTrue -= 1;  
	                    }  
	                }  
	            }  
	        }  
	        if (isTrue < 0) {  
	            System.err.println("摄像头首次开启失败，尝试重启也失败！");  
	            return;  
	        } else if (isTrue < 1) {  
	            System.err.println("摄像头开启失败！");  
	            return;  
	        } else if (isTrue == 1) {  
	            System.err.println("摄像头开启成功！");  
	        } else if (isTrue == 1) {  
	            System.err.println("摄像头首次开启失败，重新启动成功！");  
	        }  
	  
	        /** 
	         * FFmpegFrameRecorder(String filename, int imageWidth, int imageHeight, 
	         * int audioChannels) fileName可以是本地文件（会自动创建），也可以是RTMP路径（发布到流媒体服务器） 
	         * imageWidth = width （为捕获器设置宽） imageHeight = height （为捕获器设置高） 
	         * audioChannels = 2（立体声）；1（单声道）；0（无音频） 
	         */  
	        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, captureWidth, captureHeight, 2);  
	        recorder.setInterleaved(true);  
	  
	        /** 
	         * 该参数用于降低延迟 参考FFMPEG官方文档：https://trac.ffmpeg.org/wiki/StreamingGuide 
	         * 官方原文参考：ffmpeg -f dshow -i video="Virtual-Camera" -vcodec libx264 
	         * -tune zerolatency -b 900k -f mpegts udp://10.1.0.102:1234 
	         */  
	  
	        recorder.setVideoOption("tune", "zerolatency");  
	        /** 
	         * 权衡quality(视频质量)和encode speed(编码速度) values(值)： 
	         * ultrafast(终极快),superfast(超级快), veryfast(非常快), faster(很快), fast(快), 
	         * medium(中等), slow(慢), slower(很慢), veryslow(非常慢) 
	         * ultrafast(终极快)提供最少的压缩（低编码器CPU）和最大的视频流大小；而veryslow(非常慢)提供最佳的压缩（高编码器CPU）的同时降低视频流的大小 
	         * 参考：https://trac.ffmpeg.org/wiki/Encode/H.264 官方原文参考：-preset ultrafast 
	         * as the name implies provides for the fastest possible encoding. If 
	         * some tradeoff between quality and encode speed, go for the speed. 
	         * This might be needed if you are going to be transcoding multiple 
	         * streams on one machine. 
	         */  
	        recorder.setVideoOption("preset", "ultrafast");  
	        /** 
	         * 参考转流命令: ffmpeg 
	         * -i'udp://localhost:5000?fifo_size=1000000&overrun_nonfatal=1' -crf 30 
	         * -preset ultrafast -acodec aac -strict experimental -ar 44100 -ac 
	         * 2-b:a 96k -vcodec libx264 -r 25 -b:v 500k -f flv 'rtmp://<wowza 
	         * serverIP>/live/cam0' -crf 30 
	         * -设置内容速率因子,这是一个x264的动态比特率参数，它能够在复杂场景下(使用不同比特率，即可变比特率)保持视频质量； 
	         * 可以设置更低的质量(quality)和比特率(bit rate),参考Encode/H.264 -preset ultrafast 
	         * -参考上面preset参数，与视频压缩率(视频大小)和速度有关,需要根据情况平衡两大点：压缩率(视频大小)，编/解码速度 -acodec 
	         * aac -设置音频编/解码器 (内部AAC编码) -strict experimental 
	         * -允许使用一些实验的编解码器(比如上面的内部AAC属于实验编解码器) -ar 44100 设置音频采样率(audio sample 
	         * rate) -ac 2 指定双通道音频(即立体声) -b:a 96k 设置音频比特率(bit rate) -vcodec libx264 
	         * 设置视频编解码器(codec) -r 25 -设置帧率(frame rate) -b:v 500k -设置视频比特率(bit 
	         * rate),比特率越高视频越清晰,视频体积也会变大,需要根据实际选择合理范围 -f flv 
	         * -提供输出流封装格式(rtmp协议只支持flv封装格式) 'rtmp://<FMS server 
	         * IP>/live/cam0'-流媒体服务器地址 
	         */  
	        recorder.setVideoOption("crf", "25");  
	        // 2000 kb/s, 720P视频的合理比特率范围  
	        recorder.setVideoBitrate(2000000);  
	        // h264编/解码器  
	        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);  
	        // 封装格式flv  
	        recorder.setFormat("flv");  
	        // 视频帧率(保证视频质量的情况下最低25，低于25会出现闪屏)  
	        recorder.setFrameRate(FRAME_RATE);  
	        // 关键帧间隔，一般与帧率相同或者是视频帧率的两倍  
	        recorder.setGopSize(FRAME_RATE * 2);  
	        // 不可变(固定)音频比特率  
	        recorder.setAudioOption("crf", "0");  
	        // 最高质量  
	        recorder.setAudioQuality(0);  
	        // 音频比特率  
	        recorder.setAudioBitrate(192000);  
	        // 音频采样率  
	        recorder.setSampleRate(44100);  
	        // 双通道(立体声)  
	        recorder.setAudioChannels(2);  
	        // 音频编/解码器  
	        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);  
	        System.out.println("开始录制...");  
	  
	        try {  
	            recorder.start();  
	        } catch (org.bytedeco.javacv.FrameRecorder.Exception e2) {  
	            if (recorder != null) {  
	                System.out.println("关闭失败，尝试重启");  
	                try {  
	                    recorder.stop();  
	                    recorder.start();  
	                } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
	                    try {  
	                        System.out.println("开启失败，关闭录制");  
	                        recorder.stop();  
	                        return;  
	                    } catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {  
	                        return;  
	                    }  
	                }  
	            }  
	  
	        }  
	        // 音频捕获  
	        new Thread(new Runnable() {  
	            @Override  
	            public void run() {  
	                /** 
	                 * 设置音频编码器 最好是系统支持的格式，否则getLine() 会发生错误 
	                 * 采样率:44.1k;采样率位数:16位;立体声(stereo);是否签名;true: 
	                 * big-endian字节顺序,false:little-endian字节顺序(详见:ByteOrder类) 
	                 */  
	                AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);  
	  
	                // 通过AudioSystem获取本地音频混合器信息  
	                Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();  
	                // 通过AudioSystem获取本地音频混合器  
	                Mixer mixer = AudioSystem.getMixer(minfoSet[AUDIO_DEVICE_INDEX]);  
	                // 通过设置好的音频编解码器获取数据线信息  
	                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);  
	                try {  
	                    // 打开并开始捕获音频  
	                    // 通过line可以获得更多控制权  
	                    // 获取设备：TargetDataLine line  
	                    // =(TargetDataLine)mixer.getLine(dataLineInfo);  
	                    TargetDataLine line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);  
	                    line.open(audioFormat);  
	                    line.start();  
	                    // 获得当前音频采样率  
	                    int sampleRate = (int) audioFormat.getSampleRate();  
	                    // 获取当前音频通道数量  
	                    int numChannels = audioFormat.getChannels();  
	                    // 初始化音频缓冲区(size是音频采样率*通道数)  
	                    int audioBufferSize = sampleRate * numChannels;  
	                    byte[] audioBytes = new byte[audioBufferSize];  
	  
	                    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);  
	                    exec.scheduleAtFixedRate(new Runnable() {  
	                        @Override  
	                        public void run() {  
	                            try {  
	                                // 非阻塞方式读取  
	                                int nBytesRead = line.read(audioBytes, 0, line.available());  
	                                // 因为我们设置的是16位音频格式,所以需要将byte[]转成short[]  
	                                int nSamplesRead = nBytesRead / 2;  
	                                short[] samples = new short[nSamplesRead];  
	                                /** 
	                                 * ByteBuffer.wrap(audioBytes)-将byte[]数组包装到缓冲区 
	                                 * ByteBuffer.order(ByteOrder)-按little-endian修改字节顺序，解码器定义的 
	                                 * ByteBuffer.asShortBuffer()-创建一个新的short[]缓冲区 
	                                 * ShortBuffer.get(samples)-将缓冲区里short数据传输到short[] 
	                                 */  
	                                ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);  
	                                // 将short[]包装到ShortBuffer  
	                                ShortBuffer sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);  
	                                // 按通道录制shortBuffer  
	                                recorder.recordSamples(sampleRate, numChannels, sBuff);  
	                            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
	                                e.printStackTrace();  
	                            }  
	                        }  
	                    }, 0, (long) 1000 / FRAME_RATE, TimeUnit.MILLISECONDS);  
	                } catch (LineUnavailableException e1) {  
	                    e1.printStackTrace();  
	                }  
	            }  
	        }).start();  
	  
	        // javaCV提供了优化非常好的硬件加速组件来帮助显示我们抓取的摄像头视频  
	        CanvasFrame cFrame = new CanvasFrame("Capture Preview", CanvasFrame.getDefaultGamma() / grabber.getGamma());  
	        Frame capturedFrame = null;  
	        // 执行抓取（capture）过程  
	        while ((capturedFrame = grabber.grab()) != null) {  
	            if (cFrame.isVisible()) {  
	                //本机预览要发送的帧  
	                cFrame.showImage(capturedFrame);  
	            }  
	            //定义我们的开始时间，当开始时需要先初始化时间戳  
	            if (startTime == 0)  
	                startTime = System.currentTimeMillis();  
	  
	            // 创建一个 timestamp用来写入帧中  
	            videoTS = 1000 * (System.currentTimeMillis() - startTime);  
	            //检查偏移量  
	            if (videoTS > recorder.getTimestamp()) {  
	                System.out.println("Lip-flap correction: " + videoTS + " : " + recorder.getTimestamp() + " -> "  
	                        + (videoTS - recorder.getTimestamp()));  
	                //告诉录制器写入这个timestamp  
	                recorder.setTimestamp(videoTS);  
	            }  
	            // 发送帧  
	            try {  
	                recorder.record(capturedFrame);  
	            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
	                System.out.println("录制帧发生异常，什么都不做");  
	            }  
	        }  
	  
	        cFrame.dispose();  
	        try {  
	            if (recorder != null) {  
	                recorder.stop();  
	            }  
	        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {  
	            System.out.println("关闭录制器失败");  
	            try {  
	                if (recorder != null) {  
	                    grabber.stop();  
	                }  
	            } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {  
	                System.out.println("关闭摄像头失败");  
	                return;  
	            }  
	        }  
	        try {  
	            if (recorder != null) {  
	                grabber.stop();  
	            }  
	        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {  
	            System.out.println("关闭摄像头失败");  
	        }  
	    }
}
