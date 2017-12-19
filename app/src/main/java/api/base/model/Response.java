package api.base.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import common.JsonConvertor;

public class Response {

    // 08-08 02:05:14.442: I/json(7336):
    // {"scenicCount":"5","orderCount":"7","code":200,"result":[{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u7f19\u4e91\u5c71","ids":"5"},{"ddCount":"3","noPays":null,"noEnter":null,"scenic":"\u6b4c\u4e50\u5c71","ids":"4"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u91d1\u4f5b\u5c71","ids":"1"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u4ed9\u5973\u5c71","ids":"2"},{"ddCount":"1","noPays":null,"noEnter":null,"scenic":"\u56db\u9762\u5c71","ids":"3"}],"message":"\u4fe1\u606f\u83b7\u53d6\u6210\u529f"}

    private int code;

    private String token;

    private int uid;

    private String message;

    private Object result;

    // ------------图片上传-------------------
    private String pic;

    private int id;

    // ------------图片上传end -------------------
    // ------------我的积分-------------------
    private int point;
    private int number;//签到成功积分
    // ------------我的积分end-------------------

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 获取指定数据类型
     *
     * @param type 数据类型
     * @return
     */
    public <T> T getData(Type type) {
        try {
            String dataJson = JsonConvertor.toJson(this.result);

            dataJson = dataJson.replace("\n", "");

            T t = JsonConvertor.fromJson(dataJson, type);

            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getResultJson() {

        try {
            return JsonConvertor.toJson(this.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // public void setData(T data) {
    // this.data = data;
    // }

    private Type getType() {
        Type genType = this.getClass().getGenericSuperclass();

        Type type = null;

        try {
            Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

            if (types.length > 0)
                type = types[0];

            return type;
        } catch (Exception exception) {

        }

        return null;
    }

}
