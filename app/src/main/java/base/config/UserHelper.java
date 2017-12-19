package base.config;

import org.xutils.db.sqlite.WhereBuilder;

import java.util.Collection;
import java.util.List;

import base.config.entity.UserInfo;
import common.database.DbUtilsXutils3;

/**
 * Created by yangshiyou on 2017/9/13.
 */

public class UserHelper extends DbUtilsXutils3<UserInfo> {

    private static UserHelper userHelper;

    private UserHelper() {

    }


    public static UserHelper getInstance() {
        if (userHelper == null) {
            userHelper = new UserHelper();
            init();
        }
        return userHelper;
    }

    /**
     * 通过uid查询用户信息
     *
     * @param uid
     * @return
     */
    public UserInfo getUserInfoByUid(int uid) {
        try {
            if (uid != 0) {
                List<UserInfo> userInfos = db.selector(UserInfo.class).where("uid", "=", uid).findAll();

                if (userInfos != null) {
                    if (!userInfos.isEmpty()) {
                        return userInfos.get(0);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * t通过uid删除用户
     *
     * @param uid
     * @return
     */
    public int deleteUserInfoByUid(int uid) {
        try {
            if (uid != 0) {
                return db.delete(UserInfo.class, WhereBuilder.b("uid", "=", uid));//.and("salary", "=", "5000")
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int saveOrUpdate(UserInfo userInfo) throws Exception {
        if (userInfo == null)
            return 0;

//        int ret = deleteUserInfoByUid(userInfo.getUid());
        UserInfo userInfo1 = getUserInfoByUid(userInfo.getUid());

        if (userInfo1 != null)
            userInfo.setId(userInfo1.getId());

        db.saveOrUpdate(userInfo);

        return 1;
    }


    @Override
    public int insert(UserInfo userInfo) throws Exception {
        db.save(userInfo);
        return 1;
    }

    @Override
    public int update(UserInfo userInfo) throws Exception {
        db.saveOrUpdate(userInfo);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        db.delete(UserInfo.class);
        return 1;
    }

    @Override
    public int delete(int id) throws Exception {
        db.deleteById(UserInfo.class, id);
        return 1;
    }

    @Override
    public int delete(Collection<UserInfo> list) throws Exception {
        return 0;
    }

    @Override
    public UserInfo getEntity(int id) throws Exception {
        return db.findById(UserInfo.class, id);
    }

    @Override
    public List<UserInfo> getList() throws Exception {
        return db.findAll(UserInfo.class);
    }
}
