package com.fubang.live.util;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * Created by jacky on 16/3/2.
 */
public class LiteOrmDBUtil {
    public static String DB_NAME;
    public static LiteOrm liteOrm;

    public static void createDb(Context _activity, Object userId) {
        DB_NAME = "fubang_" + userId.toString() + ".db";
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(_activity, DB_NAME);
        }
        liteOrm.setDebugged(true);
    }


    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        liteOrm.single().save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.single().query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", (Object[]) value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", (Object[]) value).limit(start, length));
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     *
     * @param cla
     * @param field
     * @param value
     */
    public static <T> void deleteWhere(Class<T> cla, String field, String[] value) {
        liteOrm.delete(cla, WhereBuilder.create(cla).where(field + "=?", (Object[]) value));
    }

    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        liteOrm.cascade().deleteAll(cla);
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }


//    public static void Text() {
//
//        //我们把这个对象当做以填充数据的后的对象
//        CostItem mConversation = new CostItem();
//        mConversation.setEat("100");
//        mConversation.setHotel("200");
//        mConversation.setOil("300");
//        mConversation.setOther("400");
//        mConversation.setReapir("500");
//        mConversation.setRoad_toll("600");
//        mConversation.setStore("700");
//        mConversation.setTip("800");
//        CostItem mConversation2 = new CostItem();
//        mConversation2.setEat("100");
//        mConversation2.setHotel("200");
//        mConversation2.setOil("300");
//        mConversation2.setOther("400");
//        mConversation2.setReapir("500");
//        mConversation2.setRoad_toll("600");
//        mConversation2.setStore("700");
//        mConversation2.setTip("800");
//        List<CostItem> list = new ArrayList<CostItem>();
//        for (int i = 0; i < 10; i++) {
//            list.add(mConversation);
//            //1、插入单条数据
//        }
//
//        //1、插入单条数据
//        LiteOrmDBUtil.insert(mConversation);
//        //1、插入单条数据
//        LiteOrmDBUtil.insert(mConversation2);
//
//        //2、插入多条数据
//        LiteOrmDBUtil.insertAll(list);
//
//        //3、查询CostItem表中所有记录
//        List<CostItem> list1 = LiteOrmDBUtil.getQueryAll(CostItem.class);
//
//        //4、查询CostItem表中 eat 字段 等于 true 的记录
//        List<CostItem> list2 = LiteOrmDBUtil.getQueryByWhere(CostItem.class, "eat", new String[]{"100"});
//
//        //5、查询CostItem表中 eat 字段 等于 true 的记录,并且只取20条
//        List<CostItem> list3 = LiteOrmDBUtil.getQueryByWhereLength(CostItem.class, "eat", new String[]{"100"}, 0, 20);
//
//        //6、删除CostItem表中 isVisibility 字段 等于 true 的记录
////        LiteOrmDBUtil.deleteWhere(CostItem.class,CostItem.ISVISIBILITY , new String[]{"true"});
//
//        //7、删除所有
////        LiteOrmDBUtil.deleteAll(CostItem.class);
//
//
//    }
}
