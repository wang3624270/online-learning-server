//auther:wangqianqian for wxshg
//ifame返回页面顶部
window.onload=function(){
	window.parent.scrollTo(0,0);
}
function getUrlValue(name)
{//获取URL中参数
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}
function refresh(){
	//刷新页面
	window.location.reload();    
}
function doReturn(url){
	//跳转到指定页面
	Kelp.url(url);
}
function strLight(row){
	row.style.background="#eaf2ff";
}
function strLightOut(row){
	row.style.background="none";
}