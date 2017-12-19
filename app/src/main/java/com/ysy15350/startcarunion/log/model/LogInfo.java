package com.ysy15350.startcarunion.log.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/8/31.
 */

@Table(name = "loginfo")
public class LogInfo {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "type")
    private int type;


    @Column(name = "logTime")
    private String logTime;
}
