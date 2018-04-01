package com.guoziwei.timerecorder.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class Record extends DataSupport {

    @Column
    private long id;


}
