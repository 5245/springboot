package com.wepiao.user.common.entry.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.wepiao.user.common.entry.enumeration.OtherID;

/**
 * 自定义枚举OtherID转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumOtherIDHandler extends BaseTypeHandler<OtherID> {

    public EnumOtherIDHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OtherID parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public OtherID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public OtherID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public OtherID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param enumVal 数据库中存储的自定义enumVal属性
     * @return enumVal对应的枚举类
     */
    private OtherID locateEnum(int enumVal) {
        for (OtherID otherID : OtherID.class.getEnumConstants()) {
            if (otherID.getIntVal() == enumVal) {
                return otherID;
            }
        }
        throw new IllegalArgumentException("错误的OtherID枚举类型：" + enumVal + ",请核对" + OtherID.class.getSimpleName());
    }
}
