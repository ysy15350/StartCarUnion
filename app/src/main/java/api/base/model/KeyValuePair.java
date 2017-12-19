package api.base.model;

import java.util.HashMap;

/**
 * @ClassName：KeyValuePair
 * @Description：TODO(自定义键值对类型) @author 王青松 @date：2012.12.19
 * @version V1.0
 */
public class KeyValuePair extends HashMap<String, Object> {
	private Object key, value;

	public KeyValuePair() {
	}

	/**
	 * 创建键值对类型
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public KeyValuePair(Object key, Object value) {
		this.value = value;
		this.key = key;
	}

	/**
	 * 获取KEY
	 * 
	 * @return key值
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * 设置KEY
	 * 
	 * @param _key
	 *            KEY值
	 */
	public void setKey(Object _key) {
		this.key = _key;
	}

	/**
	 * 获取值字符串
	 * 
	 * @return
	 */
	public String getValueStr() {
		return value == null ? "" : value.toString();
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param _value
	 */
	public void setValue(Object _value) {
		this.value = _value;
	}

	@Override
	public String toString() {
		return "KeyValuePair [key=" + key + ", value=" + value + "]";
	}
}
