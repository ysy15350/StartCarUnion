package base.config.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/9/13.
 */

@Table(name = "systemInfo")
public class SystemInfo {

    /**
     * 主键
     */
    @Column(name = "id", isId = true)
    private int id;


}
