package base.config;

import java.util.Collection;
import java.util.List;

import base.config.entity.ConfigInfo;
import common.database.DbUtilsXutils3;

/**
 * Created by yangshiyou on 2017/9/13.
 */

public class ConfigHelper extends DbUtilsXutils3<ConfigInfo> {

    private static ConfigHelper configHelper;

    private ConfigHelper() {

    }


    private static ConfigHelper getInstance() {
        if (configHelper == null) {
            configHelper = new ConfigHelper();
            init();
        }
        return configHelper;
    }

    /**
     * 初始化配置，启动时调用
     */
    public static void initConfigInfo() {

        getInstance();


        //------------------正式------------------------------

        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setIsDebug(0);
        configInfo.setServerUrl("qidian.59156.cn");
        configInfo.setServerPort(80);
        configInfo.setProjectName("App");

        //------------------测试------------------------------

        ConfigInfo configInfo1 = new ConfigInfo();
        configInfo1.setIsDebug(1);
        configInfo1.setServerUrl("192.168.0.118");
        configInfo1.setServerPort(803);
        configInfo1.setProjectName("App");


        try {

            if (configHelper == null)
                configHelper = getInstance();

            configHelper.delete();

            configHelper.saveOrUpdate(configInfo);
            configHelper.saveOrUpdate(configInfo1);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取配置信息
     *
     * @param isDebug 0：正式；1：调试
     * @return
     */
    public static ConfigInfo getConfigInfo(int isDebug) {
        try {

            if (configHelper == null)
                configHelper = getInstance();

            return configHelper.getEntity(isDebug);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取接口访问地址
     *
     * @param isDebug         是否是调试
     * @param withProjectName 是否包含项目名称
     * @return
     */
    public static String getServerUrl(boolean isDebug, boolean withProjectName) {
        try {
            ConfigInfo configInfo = null;
            if (isDebug) {
                configInfo = ConfigHelper.getConfigInfo(2);
            } else {
                configInfo = ConfigHelper.getConfigInfo(1);
            }

            if (configInfo != null) {
                //int isDebug = configInfo.getIsDebug();
                String server_url = configInfo.getServerUrl();
                int server_port = configInfo.getServerPort();
                String projectName = configInfo.getProjectName();

                String serverUrl = String.format("http://%s:%d/", server_url, server_port);

                if (withProjectName)
                    serverUrl = String.format("http://%s:%d/%s/", server_url, server_port, projectName);

                return serverUrl;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public int saveOrUpdate(ConfigInfo configInfo) throws Exception {
        db.saveOrUpdate(configInfo);
        return 1;
    }

    @Override
    public int insert(ConfigInfo configInfo) throws Exception {
        db.save(configInfo);
        return 1;
    }

    @Override
    public int update(ConfigInfo configInfo) throws Exception {
        db.saveOrUpdate(configInfo);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        return 0;
    }

    @Override
    public int delete(int id) throws Exception {
        return 0;
    }

    @Override
    public int delete(Collection<ConfigInfo> list) throws Exception {
        return 0;
    }

    @Override
    public ConfigInfo getEntity(int id) throws Exception {
        return db.findById(ConfigInfo.class, id);
    }

    @Override
    public List<ConfigInfo> getList() throws Exception {
        return db.findAll(ConfigInfo.class);
    }
}
