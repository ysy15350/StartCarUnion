package common.model;

public class NetworkModel {

	/**
	 * 是否测试，true:测试模式(true:测试环境，false:正式环境)
	 */
	private static Boolean isDebug = true;

	/**
	 * 使用的编码格式，目前只支持utf-8。/gbk
	 */
	public static String input_charset = "utf-8";

	/**
	 * 返回数据格式
	 */
	public static String format = "json";// xml json

	/**
	 * 服务器地址 http://120.76.43.237:8080 172.0.0.33:8080
	 */
	public static String WebHostUrl = isDebug ? "http://web.yfxgame.com" : "http://web.yfxgame.com";

	public static String partner_id = isDebug ? "d89170ee-7ede-415b-8022-3be2793d1674"
			: "17ab7302-6ba9-4c4e-ab48-9ff004951b41";

	/**
	 * MD5私匙
	 * 
	 */
	public static String Md5Key = isDebug ? "ljbapp2015" : "longkin123!@#*";

	/**
	 * 平台类型 AndRoid=2 IOS=3
	 */
	public static String plat_type = "2";

	public static void setIsDebug(Boolean isDebug) {

		partner_id = isDebug ? "d89170ee-7ede-415b-8022-3be2793d1674" : "17ab7302-6ba9-4c4e-ab48-9ff004951b41";

		Md5Key = isDebug ? "ljbapp2015" : "longkin123!@#*";

		WebHostUrl = isDebug ? "http://115.28.78.72:83/" : "https://app.longkin.net/";

		CacheTime = 0;
	}

	/**
	 * 缓存时间
	 */
	public static int CacheTime = 0;// ACache.TIME_MINUTE;

}