package com.company.project.configurer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

import static com.company.project.core.ProjectConstant.*;

/**
 * Mybatis & Mapper & PageHelper 配置
 */
@Configuration
public class MybatisConfigurer {

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(MODEL_PACKAGE);

        //添加插件
        PageHelper pageHelper = pageHelper();
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.plugin(pageHelper);
        factory.setPlugins(new PageInterceptor());

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    /**
     * 配置PageHelper
     * <p>
     * 参考官方提供的配置参数：
     * https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
     *
     * @return PageHelper
     */
    @Bean
    public PageHelper pageHelper() {
        //配置分页插件，详情请查阅官方文档 https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("pageSizeZero", "true");
        //页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("reasonable", "true");
        //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
        //对使用 RowBounds 作为分页参数时有效，将 RowBounds 中的 offset 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页
        properties.setProperty("offsetAsPageNum", "true");
        //使用 RowBounds 分页会进行 count 查询
        properties.setProperty("rowBoundsWithCount", "true");
        //使用mysql数据库
        properties.setProperty("helperDialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    /**
     * 通用mapper配置类
     * <p>
     * MyBatis扫描接口，使用的tk.mybatis.spring.dao.MapperScannerConfigurer
     * <p>
     * 参考官方提供的配置参数：
     * https://github.com/abel533/Mapper/wiki/3.config
     *
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        //扫描该路径下的mapper。mapper类的包名即操作数据库的对应到数据表的Mapper.java类的包名
        mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);

        //配置通用Mapper，详情请查阅官方文档 https://github.com/abel533/Mapper/wiki/3.config
        Properties properties = new Properties();
        // 自己扩展通用接口，需指定mappers键值对
        // 特别注意，不要把自定义core包下的Mapper放到被扫描到的BasePackage包中，也就是不能同其他Mapper一样被扫描到。
        properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        //insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("notEmpty", "false");
        // 获取主键的方式，方言
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }

}

