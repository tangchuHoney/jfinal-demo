package com.jklove.demo.config;


import com.jfinal.config.*;
import com.jfinal.core.paragetter.ParaProcessorBuilder;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.RenderManager;
import com.jklove.demo.router.AdminRouter;
import com.sun.xml.internal.ws.api.pipe.Engine;

import java.io.File;

/**
 * API 引导式配置
 */
public class JfinalConfig extends JFinalConfig{

    public static Prop prop = PropKit.use("config/jdbc-config.txt");


    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(prop.getBoolean("jfinal.devMode", true));
//        me.setInjectDependency(true);
//        me.setBaseUploadPath(BaseConstant.UPLOAD_PATH);
//        me.setBaseDownloadPath(BaseConstant.UPLOAD_PATH);
//        me.setJsonFactory(new ErpJsonFactory());
//        //限制上传100M
//        me.setMaxPostSize(104857600);
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        me.add(new AdminRouter());
    }

    public void configEngine(com.jfinal.template.Engine engine) {

    }


    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
//        ParaProcessorBuilder.me.regist(BasePageRequest.class, PageParaGetter.class, null);
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
//        druidPlugin.addFilter(new WallFilter());
        druidPlugin.setInitialSize(0);
        druidPlugin.setMinIdle(0);
        druidPlugin.setMaxActive(2000);
        druidPlugin.setTimeBetweenEvictionRunsMillis(5000);
        druidPlugin.setValidationQuery("select 1");
        druidPlugin.setTimeBetweenEvictionRunsMillis(60000);
        druidPlugin.setMinEvictableIdleTimeMillis(30000);
        me.add(druidPlugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//        arp.setCache(CaffeineCache.ME);
        arp.setDialect(new MysqlDialect());
//        arp.getEngine().addDirective("CrmAuth", CrmDirective.class);
        me.add(arp);
//        //扫描sql模板
        getSqlTemplate(PathKit.getRootClassPath() + "/template", arp);
//        //Redis以及缓存插件
//        createRedisPlugin(me);
//        //cron定时器
//        me.add(new Cron4jPlugin(PropKit.use("config/cron4j.txt")));
//        //model映射
//        _MappingKit.mapping(arp);
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(prop.get("mysql.jdbcUrl"), prop.get("mysql.user"), prop.get("mysql.password").trim()).setInitialSize(1).setMinIdle(1).setMaxActive(2000).setTimeBetweenEvictionRunsMillis(5000).setValidationQuery("select 1").setTimeBetweenEvictionRunsMillis(60000).setMinEvictableIdleTimeMillis(30000).setFilters("stat,wall");
    }

//    private void createRedisPlugin(Plugins me) {
//        for (String configName : prop.get("jfinal.redis", "").split(",")) {
//            RedisPlugin redisPlugin;
//            if (prop.getBoolean(configName + ".open", false)) {
//                if (prop.containsKey(configName + ".password") && StrUtil.isNotEmpty(prop.get(configName + ".password"))) {
//                    redisPlugin = new RedisPlugin(prop.get(configName + ".cacheName").trim(), prop.get(configName + ".host").trim(), prop.getInt(configName + ".port", 6379), prop.getInt(configName + ".timeout", 20000), prop.get(configName + ".password", null));
//                } else {
//                    redisPlugin = new RedisPlugin(prop.get(configName + ".cacheName").trim(), prop.get(configName + ".host").trim(), prop.getInt(configName + ".port", 6379), prop.getInt(configName + ".timeout", 20000));
//                }
//                me.add(redisPlugin);
//            }
//        }
//    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        //添加全局拦截器
//        me.addGlobalActionInterceptor(new ErpInterceptor());
//        me.add(new AuthInterceptor());
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {
//        //配置数据库监控
//        me.add(new DruidStatViewHandler("/druid", new DruidConfig()));
//        //自定义渲染工厂
//        RenderManager.me().setRenderFactory(new ErpRenderFactory());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    private void getSqlTemplate(String path, ActiveRecordPlugin arp) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File childFile : files) {
                    if (childFile.isDirectory()) {
                        getSqlTemplate(childFile.getAbsolutePath(), arp);
                    } else {
                        if (childFile.getName().toLowerCase().endsWith(".sql")) {
                            arp.addSqlTemplate(childFile.getAbsolutePath().replace(PathKit.getRootClassPath(), "").replace("\\", "/"));
                        }
                    }
                }
            }
        }
    }
}
