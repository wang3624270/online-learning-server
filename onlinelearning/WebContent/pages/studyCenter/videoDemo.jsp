<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp Demo</title>
<link href="<%=request.getContextPath()%>/webFramework/common/css/video-js.css" rel="stylesheet" type="text/css">
<style>
	.m{ width: 740px; height: 400px; margin-left: auto; margin-right: auto; margin-top: 100px; }
</style>
</head>
<body>
   <div class="m">
		<video id="my-video" class="video-js" controls preload="auto" width="1200" height="600"
		  poster="<%=request.getContextPath()%>/webFramework/common/image/wait.jpg" data-setup="{}">
			<source src="<%=request.getContextPath()%>/accDownload?accId=388" type="video/webm">
		</video>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/video.min.js"></script>
		<script type="text/javascript">
			var myPlayer = videojs('my-video');
			videojs("my-video").ready(function(){
				var myPlayer = this;
				myPlayer.play();
			});
		</script>
	</div>
</body>
<ots:initjsp />
</html>
