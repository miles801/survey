package com.ycrl.utils.dialect;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * @author Michael
 */
public class SQLServerDialect extends SQLServer2008Dialect {
    public SQLServerDialect() {
        super();
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NUMERIC, StandardBasicTypes.FLOAT.getName());
    }
}
