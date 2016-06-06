package com.ghg.tobacco.db;

import org.xutils.DbManager;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/24 10:18
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class DbManagerUtils {

    private static DbManager.DaoConfig daoConfig;
    private DbManagerUtils(){

    }

    public static DbManager.DaoConfig getDaoConfig(){
        if (daoConfig!=null){
            return  daoConfig;
        }
        daoConfig= new DbManager.DaoConfig()
                .setDbName("yincao_db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作
        return daoConfig;
    }

}
