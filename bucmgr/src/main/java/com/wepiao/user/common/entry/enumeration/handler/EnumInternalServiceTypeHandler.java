package com.wepiao.user.common.entry.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.wepiao.user.common.entry.enumeration.InternalServiceType;

/**
 * 自定义枚举InternalServiceType转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumInternalServiceTypeHandler extends BaseTypeHandler<InternalServiceType> {

    public EnumInternalServiceTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InternalServiceType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public InternalServiceType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位InternalServiceType子类
            return locateEnum(i);
        }
    }

    @Override
    public InternalServiceType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位InternalServiceType子类
            return locateEnum(i);
        }
    }

    @Override
    public InternalServiceType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位InternalServiceType子类
            return locateEnum(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param enumVal 数据库中存储的自定义enumVal属性
     * @return enumVal对应的枚举类
     */
    private InternalServiceType locateEnum(int enumVal) {
        for (InternalServiceType serviceType : InternalServiceType.class.getEnumConstants()) {
            if (serviceType.getIntVal() == enumVal) {
                return serviceType;
            }
        }
        throw new IllegalArgumentException("错误的InternalServiceType枚举类型：" + enumVal + ",请核对" + InternalServiceType.class.getSimpleName());
    }
}
