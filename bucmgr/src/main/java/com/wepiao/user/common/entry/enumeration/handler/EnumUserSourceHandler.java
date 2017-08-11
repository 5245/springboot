package com.wepiao.user.common.entry.enumeration.handler;

import com.wepiao.user.common.entry.enumeration.UserSource;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义枚举SubOtherID转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumUserSourceHandler extends BaseTypeHandler<UserSource> {

    public EnumUserSourceHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserSource parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public UserSource getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public UserSource getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OtherID子类
            return locateEnum(i);
        }
    }

    @Override
    public UserSource getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
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
    private UserSource locateEnum(int enumVal) {
        for (UserSource userSource : UserSource.class.getEnumConstants()) {
            if (userSource.getIntVal() == enumVal) {
                return userSource;
            }
        }
        throw new IllegalArgumentException("错误的UserSource枚举类型：" + enumVal + ",请核对" + UserSource.class.getSimpleName());
    }
}
