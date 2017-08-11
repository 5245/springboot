package com.wepiao.user.common.entry.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.wepiao.user.common.entry.enumeration.SubOtherID;

/**
 * 自定义枚举SubOtherID转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumSubOtherIDHandler extends BaseTypeHandler<SubOtherID> {

    public EnumSubOtherIDHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SubOtherID parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public SubOtherID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public SubOtherID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public SubOtherID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
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
    private SubOtherID locateEnum(int enumVal) {
        for (SubOtherID subOtherID : SubOtherID.class.getEnumConstants()) {
            if (subOtherID.getIntVal() == enumVal) {
                return subOtherID;
            }
        }
        throw new IllegalArgumentException("错误的SubOtherID枚举类型：" + enumVal + ",请核对" + SubOtherID.class.getSimpleName());
    }
}
