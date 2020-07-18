package com.wl.travel.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties(prefix = "primary.datasource.druid")
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DataSourceConfig {
    /**
     * dao层的包路径
     */
    static final String PACKAGE = "com.wl.travel.dao";

    /**
     * mapper文件的相对路径
     */
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${primary.datasource.druid.filters}")
    private String filters;
    @Value("${primary.datasource.druid.driverClassName}")
    private String driverClassName;
    @Value("${primary.datasource.druid.username}")
    private String username;
    @Value("${primary.datasource.druid.password}")
    private String password;
    @Value("${primary.datasource.druid.url}")
    private String url;
    @Value("${primary.datasource.druid.initialSize}")
    private Integer initialSize;
    @Value("${primary.datasource.druid.maxActive}")
    private Integer maxActive;
    @Value("${primary.datasource.druid.minIdle}")
    private Integer minIdle;
    @Value("${primary.datasource.druid.maxWait}")
    private Integer maxWait;
    @Value("${primary.datasource.druid.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${primary.datasource.druid.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Primary
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource druid = new DruidDataSource();

        // 监控统计拦截的filters
        druid.setFilters(filters);

        // 配置基本属性
        druid.setDriverClassName(driverClassName);
        druid.setUsername(username);
        druid.setPassword(password);
        druid.setUrl(url);

        //初始化时建立物理连接的个数
        druid.setInitialSize(initialSize);
        //最大连接池数量
        druid.setMaxActive(maxActive);
        //最小连接池数量
        druid.setMinIdle(minIdle);
        //获取连接时最大等待时间，单位毫秒。
        druid.setMaxWait(maxWait);
        //间隔多久进行一次检测，检测需要关闭的空闲连接
        druid.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //一个连接在池中最小生存的时间
        druid.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        return druid;
    }

    // 创建该数据源的事务管理
    @Primary
    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }

    // 创建Mybatis的连接会话工厂实例
    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSource") DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);  // 设置数据源bean
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConfig.MAPPER_LOCATION));  // 设置mapper文件路径

        return sessionFactory.getObject();
    }
}
