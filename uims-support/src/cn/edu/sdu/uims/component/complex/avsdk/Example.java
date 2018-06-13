package cn.edu.sdu.uims.component.complex.avsdk;

import java.util.HashMap;
import java.util.Map;

import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Client;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Hub;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.PiliException;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Stream;


public class Example {
	static String accessKey = "2M63A85U1GpU37_hxw6zmCYt7ia0YPIEpOjLeJt5";
	static String secretKey = "n02lUl9NEAyT22aE2JpkYy49rSFTAe-obnZt4G7O";
    public static Map getUrl(String hubName, String streamName,String type,Integer time) {
        //初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);
        if(time ==null) {
        		time =3600;
        }   
        Map urls = new HashMap();
        if(type.equals("RTMP")) {
        	  	// RTMP推流地址
	        String rtmppushurl = cli.RTMPPublishURL("pili-publish.sportshot.cn", hubName, streamName, time);
	        System.out.printf("keyA=%s RTMP推流地址=%s\n", "qdtest", rtmppushurl);
	        
	        //RTMP直播地址
	        String rtmpplayurl = cli.RTMPPlayURL("pili-live-rtmp.sportshot.cn", hubName,streamName);
	        System.out.printf("keyA=%s RTMP直播地址=%s\n", "qdtest", rtmpplayurl);
	        
	        urls.put("rtmppushurl", rtmppushurl);
	        urls.put("rtmpplayurl", rtmpplayurl);
	    
        }
        if(type.equals("HLS")) {
          //HLS直播地址
        	  String hlspushurl = cli.HLSPlayURL("live-hls.test.com", hubName, streamName);
          System.out.printf("keyA=%s HLS直播地址=%s\n", "streamkey", hlspushurl);
  
          //HDL直播地址
          String hlsplayurl = cli.HDLPlayURL("live-hdl.test.com", hubName, streamName);
          System.out.printf("keyA=%s HDL直播地址=%s\n", "streamkey", hlsplayurl);
          
          urls.put("hlspushurl", hlspushurl);
	      urls.put("hlsplayurl", hlsplayurl);
        }
		return urls;

    }
    
    public static String getCoverUrl(String hubName, String streamName) {
    	 	//初始化client
        Client cli = new Client(accessKey,secretKey);
      
	    String url = cli.SnapshotPlayURL("pili-live-snapshot.sportshot.cn", hubName, streamName);
	    	/*
	    	http://live-snapshot.test.com/PiliSDKTest/streamkey.jpg
	    	*/
	    return url;
    }
    
    public static void createStream(String hubName,String stream) {//创建流
    	   	//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);
	      try {
	          hub.create(stream);
	      }catch (PiliException e){
	          e.printStackTrace();
	          return;
	      }
	      System.out.printf("keyA=%s 创建\n", stream);
    }
    
    public static void liveState(Stream stream) {//查询直播状态
      
      try{
          Stream.LiveStatus status = stream.liveStatus();
          System.out.printf("直播状态: status=%s\n", status.toJson());
      }catch (PiliException e){
          if (!e.isNotInLive()) {
              e.printStackTrace();
              return;
          }else{
              System.out.printf("不在直播\n");
          }
      }
    }
    
    public static void pushHistory(Stream stream) { //查询推流历史
   
      Stream.Record[] records;
      try{
          records = stream.historyRecord(0, 0);
      }catch (PiliException e){
          e.printStackTrace();
          return;
      }
      System.out.printf("推流历史: records=%s\n", printArrary(records));

    }
    public static void allStream(String hubName, String streamName,String prefix) {//列出所有流
    		//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);    	 	
        try{
            Hub.ListRet listRet = hub.list(prefix, 10, "");
            System.out.printf("hub=%s 列出流: keys=%s marker=%s\n", hubName,printArrary(listRet.keys) , listRet.omarker);
        }catch (PiliException e){
            e.printStackTrace();
          
        }
    }
    public static void livingStream(String hubName, String streamName){//列出正在直播的流
    		//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);       
        try{
            Hub.ListRet listRet = hub.listLive("", 0, "");
            System.out.printf("hub=%s 列出正在直播的流: keys=%s marker=%s\n", hubName, printArrary(listRet.keys), listRet.omarker);
        }catch (PiliException e){
            e.printStackTrace();
        }  	
    }
    
    public static void snapshotStream(String picName,Stream stream) throws PiliException {//流截图
    		Stream.SnapshotOptions opts = new Stream.SnapshotOptions();
        opts.fname = picName;
        stream.snapshot(opts);
    }

    public static void startStream(String hubName ,String stream) {//启用流
    	  	//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);
    		Stream str = null;
        try{
        		str.enable();
        		str = hub.get(stream);
        }catch (PiliException e ){
            e.printStackTrace();
        }
        System.out.printf("keyA=%s 启用: %s\n", stream, str.toJson());
    }
    
    public static void disabledStream(String hubName ,String stream,Integer time) {//禁用流
    		//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);
        if(time==null) {
        		time=10;
        }
        Stream str = null;
	    try{
	    	  str.disable(time);
	    	  str = hub.get(stream);
	      }catch (PiliException e ){
	          e.printStackTrace();
	          return;
	    }	
	    System.out.printf("keyA=%s 禁用: %s\n", stream, str.toJson());
    }
    
    public static void saveLive(String hubName,String stream) {      //保存直播数据
    	  Stream streamA = getStream(hubName,stream);
      String fname = null;
      try {
          fname = streamA.save(0,0);
      }catch (PiliException e){
          if (!e.isNotInLive()) {
              e.printStackTrace();
             
          }else{
              System.out.printf("keyA=%s 不在直播\n",stream);
          }
      }
      System.out.printf("keyA=%s 保存直播数据: fname=%s\n", stream, fname);
    }
    
    public static Stream getStream(String hubName,String stream) {	 	//获得流
    		//初始化client
        Client cli = new Client(accessKey,secretKey);
        //初始化Hub
        Hub hub = cli.newHub(hubName);
        Stream streamA = null;
        try{
            streamA = hub.get(stream);
        }catch (PiliException e){
            e.printStackTrace();
        }
        System.out.printf("keyA=%s 查询: %s\n", stream, streamA.toJson());
        return streamA;
    }
    
    private static String printArrary(Object[] arr){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object a : arr){
            sb.append(a.toString()+" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
