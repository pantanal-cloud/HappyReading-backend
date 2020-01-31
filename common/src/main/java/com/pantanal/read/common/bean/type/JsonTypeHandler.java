package com.pantanal.read.common.bean.type;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.pantanal.read.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class JsonTypeHandler extends BaseTypeHandler<Object> {
    private Class<Object> type;

    public JsonTypeHandler(Class<Object> type) {
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }

        if (null == type) {
            throw new MybatisPlusException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }

    private Object parse(String json) {
        return JsonUtil.stringToObject(json, this.type);
    }

    private String toJsonString(Object obj) {
        return JsonUtil.objectToString(obj);
    }

    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.parse(rs.getString(columnName));
    }

    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.parse(rs.getString(columnIndex));
    }

    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.parse(cs.getString(columnIndex));
    }

    public void setNonNullParameter(PreparedStatement ps, int columnIndex, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(columnIndex, this.toJsonString(parameter));
    }
}
