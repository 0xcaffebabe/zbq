package wang.ismy.zbq.handler;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wang.ismy.zbq.dto.Page;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type= StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
@Component
public class SQLInterceptor implements Interceptor {
    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        //通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        // 配置文件中SQL语句的ID
        String id = mappedStatement.getId();
        if(id.matches(".+Paging$")) { //需要拦截的ID(正则匹配)
            BoundSql boundSql = statementHandler.getBoundSql();
            // 原始的SQL语句
            String sql = boundSql.getSql();

            Map<?,?> parameter = (Map<?,?>)boundSql.getParameterObject();
            Page page = (Page)parameter.get("page");

            // 改造后带分页查询的SQL语句
            String pageSql = sql + " LIMIT " + (page.getPageNumber()-1)*page.getLength() + "," + page.getLength();

            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
