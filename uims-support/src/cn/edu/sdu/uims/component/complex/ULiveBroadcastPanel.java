package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import cn.edu.sdu.uims.util.UimsUtils;


public class ULiveBroadcastPanel extends UComplexPanel {
		
	private JButton startButton, stopButton;
	CanvasFrame canvas;
	Integer flag =0;
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		JPanel controlsPane = new JPanel();
		startButton = new JButton("开播");
		controlsPane.add(startButton);
		stopButton = new JButton("停止");
		stopButton.setEnabled(false);
		controlsPane.add(stopButton);
		add(controlsPane, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreviewThread.setFlag();
				UimsUtils.messageBoxInfo("开始推流直播！");
				new RefreshThread(canvas,1).start();
				startButton.setEnabled(false);
				stopButton.setEnabled(true);

			}
		});

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshThread.setFlag();
				UimsUtils.messageBoxInfo("关闭直播！");
				new PreviewThread(canvas,0).start();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);

			}
		});


		canvas = new CanvasFrame("NotVisibleCanvasFrame", CanvasFrame.getDefaultGamma());

		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		canvas.setVisible(false);
		add(canvas.getContentPane(), BorderLayout.CENTER);
		new PreviewThread(canvas,flag).start();
		

	}
	
}
class PreviewThread extends Thread {
	OpenCVFrameGrabber grabber;
	CanvasFrame canvas;
	Frame capturedFrame;
	static Integer flag;
	
	public static void setFlag() {
		flag = 1;
	} 
	public PreviewThread(CanvasFrame canvas,Integer flag) {
		this.canvas = canvas;
		this.flag = flag;
		grabber = new OpenCVFrameGrabber(0);
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
	}
	@Override
	public void run() {
		try {
			sleep(45);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((capturedFrame = grabber.grab()) != null&&flag==0) {
			
				canvas.showImage(capturedFrame);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			grabber.close();
			grabber.release();
			System.out.println("closed!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class RefreshThread extends Thread {
	OpenCVFrameGrabber grabber;
	CanvasFrame canvas;
	FFmpegFrameRecorder recorder;
	Frame capturedFrame;
	long startTime = 0;
	long videoTS = 0;
	static Integer flag;
	
	public static void setFlag() {
		flag = 0;
	} 
	public RefreshThread(CanvasFrame canvas,Integer flag) {
		this.canvas = canvas;
		this.flag = flag;
		grabber = new OpenCVFrameGrabber(0);
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
		
		recorder = new FFmpegFrameRecorder("output.mp4", 600, 400, 2);
		recorder.setInterleaved(true);
		//该参数用于降低延迟
		recorder.setVideoOption("tune", "zerolatency");
		//权衡quality(视频质量)和encode speed(编码速度) values(值)
		recorder.setVideoOption("preset", "ultrafast");

		recorder.setVideoOption("crf", "25");
		// 2000 kb/s, 720P视频的合理比特率范围
		recorder.setVideoBitrate(2000000);
		// h264编/解码器
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		// 封装格式flv
		recorder.setFormat("flv");
		// 视频帧率(保证视频质量的情况下最低25，低于25会出现闪屏)
		recorder.setFrameRate(25);// FRAME_RATE
		// 关键帧间隔，一般与帧率相同或者是视频帧率的两倍
		recorder.setGopSize(25 * 2);
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
	}

	@Override
	public void run() {
		try {
			sleep(45);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		// 音频捕获
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(flag==0) {
					/**
					 * 设置音频编码器 最好是系统支持的格式，否则getLine() 会发生错误
					 * 采样率:44.1k;采样率位数:16位;立体声(stereo);是否签名;true:
					 * big-endian字节顺序,false:little-endian字节顺序(详见:ByteOrder类)
					 */
					AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);
					// 通过AudioSystem获取本地音频混合器信息
					Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();
					// 通过AudioSystem获取本地音频混合器
					Mixer mixer = AudioSystem.getMixer(minfoSet[0]);
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
						}, 0, (long) 1000 / 25, TimeUnit.MILLISECONDS);
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			System.out.print("111");
			}
		}).start();

		try {
			while ((capturedFrame = grabber.grab()) != null&&flag==1) {
			
				canvas.showImage(capturedFrame);
				// 定义我们的开始时间，当开始时需要先初始化时间戳
				if (startTime == 0)
					startTime = System.currentTimeMillis();

				// 创建一个 timestamp用来写入帧中
				videoTS = 1000 * (System.currentTimeMillis() - startTime);
				// 检查偏移量
				if (videoTS > recorder.getTimestamp()) {
					System.out.println("Lip-flap correction: " + videoTS + " : " + recorder.getTimestamp() + " -> "
							+ (videoTS - recorder.getTimestamp()));
					// 告诉录制器写入这个timestamp
					recorder.setTimestamp(videoTS);
				}
				// 发送帧
				try {
					recorder.record(capturedFrame);
				} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
					System.out.println("录制帧发生异常，什么都不做");
				}
			}
			canvas.dispose();
			try {
				if (recorder != null) {
					recorder.stop();
					System.out.println("关闭录制器");
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
					System.out.println("关闭摄像器");
				}
			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				System.out.println("关闭摄像头失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}