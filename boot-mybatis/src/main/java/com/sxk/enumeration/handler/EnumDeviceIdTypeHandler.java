package com.sxk.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.sxk.enumeration.DeviceIdType;

/**
 * 自定义枚举DeviceIdType转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumDeviceIdTypeHandler extends BaseTypeHandler<DeviceIdType> {

    public EnumDeviceIdTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DeviceIdType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public DeviceIdType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位DeviceIdType子类
            return locateEnum(i);
        }
    }

    @Override
    public DeviceIdType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位DeviceIdType子类
            return locateEnum(i);
        }
    }

    @Override
    public DeviceIdType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位DeviceIdType子类
            return locateEnum(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param enumVal 数据库中存储的自定义enumVal属性
     * @return enumVal对应的枚举类
     */
    private DeviceIdType locateEnum(int enumVal) {
        for (DeviceIdType deviceIdType : DeviceIdType.class.getEnumConstants()) {
            if (deviceIdType.getIntVal() == enumVal) {
                return deviceIdType;
            }
        }
        throw new IllegalArgumentException("错误的DeviceIdType枚举类型：" + enumVal + ",请核对" + DeviceIdType.class.getSimpleName());
    }
}
