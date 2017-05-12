package com.fubang.live.entities;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by jacky on 2017/5/11.
 */
public class BaseLiteOrmEntity implements Serializable {

    // 设置为主键,自增
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
