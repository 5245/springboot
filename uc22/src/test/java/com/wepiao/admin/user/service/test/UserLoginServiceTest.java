/**
 * 
 */
package com.wepiao.admin.user.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.UserLoginReq;
import com.wepiao.admin.user.rest.msg.UserLoginRes;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.admin.user.service.handler.UsersOperationLimitHandler;
import com.wepiao.admin.user.service.impl.UserLoginServiceImpl;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.DeviceIdType;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.MD5Utils;

/**
 * @author Asus
 *
 */
public class UserLoginServiceTest {

    @Mock
    private MobileNum2UIDHandler       mockMobileNum2UIDHandler;

    @Mock
    private UsersHandler               mockUsersHandler;

    @Mock
    private IdRelationService          mockIdRelationService;

    @Mock
    private UserTagHandler             mockUserTagHandler;

    @Mock
    private UserDeviceHandler          mockUserDeviceHandler;

    @Mock
    private UsersOperationLimitHandler mockUsersOperationLimitHandler;

    @InjectMocks
    private UserLoginServiceImpl       mockUserLoginService;

    private UserLoginReq               loginReq;
    private DeviceInfo                 deviceInfo;
    private List<DeviceInfo>           deviceinfoList;
    private String                     passwd;
    private Users                      user;
    private Users                      noPasswdUser;
    private Users                      wrongPasswdUser;
    private String                     wrongPasswd;
    private Date                       tempTime;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        passwd = MD5Utils.getEncryptedPwd("12345678900");
        wrongPasswd = MD5Utils.getEncryptedPwd("wrongPasswd");
        loginReq = new UserLoginReq("12345678900", "12345678900");
        deviceinfoList = new ArrayList<DeviceInfo>();
        deviceInfo = new DeviceInfo("mockDeviceId", DeviceIdType.IMEI);
        deviceinfoList.add(0, deviceInfo);
        tempTime = new Date();

        user = new Users(123456, "12345678900", passwd, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null, tempTime, tempTime,
                null);
        noPasswdUser = new Users(123456, "12345678900", null, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null, tempTime,
                tempTime, null);
        wrongPasswdUser = new Users(123456, "12345678900", wrongPasswd, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null,
                tempTime, tempTime, null);
    }

    @After
    public void tearDown() throws Exception {
        passwd = null;
        wrongPasswd = null;
        loginReq = null;
        deviceinfoList.clear();
        deviceInfo = null;
        deviceinfoList = null;
        tempTime = null;
        user = null;
        noPasswdUser = null;
        wrongPasswdUser = null;
    }

    @Test(expected = BaseRestException.class)
    public void testLoginOverLimitError() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(true);
        mockUserLoginService.login(loginReq, deviceinfoList);
    }

    @Test(expected = BaseRestException.class)
    public void testLoginNotExistedUidError() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(false);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        doNothing().when(mockUsersOperationLimitHandler).addLoginFailRecord(anyString());

        mockUserLoginService.login(loginReq, deviceinfoList);
    }

    @Test(expected = BaseRestException.class)
    public void testLoginNoUserError() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(null);
        doNothing().when(mockUsersOperationLimitHandler).addLoginFailRecord(anyString());

        mockUserLoginService.login(loginReq, deviceinfoList);
    }

    @Test(expected = BaseRestException.class)
    public void testLoginNoPasswdError() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(noPasswdUser);

        mockUserLoginService.login(loginReq, deviceinfoList);
    }

    @Test(expected = BaseRestException.class)
    public void testLoginWrongPasswdError() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(wrongPasswdUser);
        doNothing().when(mockUsersOperationLimitHandler).addLoginFailRecord(anyString());

        mockUserLoginService.login(loginReq, deviceinfoList);
    }

    @Test
    public void testLoginSuccess() {
        when(mockUsersOperationLimitHandler.isLoginLimit(anyString())).thenReturn(false);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(user);
        when(mockIdRelationService.getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUsersHandler.getExtUidByUid(anyInt())).thenReturn(null);

        UserLoginRes mockUserLoginRes = mockUserLoginService.login(loginReq, deviceinfoList);

        assertEquals("12345678900", mockUserLoginRes.getMobileNo());
        assertEquals(123456, mockUserLoginRes.get_UID());
        assertEquals(Status.NORMAL.getIntVal(), mockUserLoginRes.getStatus());

        verify(mockMobileNum2UIDHandler).getUIDByMobileNo(anyString());
        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockIdRelationService).getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject());
        verify(mockUserTagHandler).queryAllUserTag(anyString(), (OtherID) anyObject());
        verify(mockUsersHandler).getExtUidByUid(anyInt());
        verifyNoMoreInteractions(mockMobileNum2UIDHandler);
        //verifyNoMoreInteractions(mockUsersHandler);
        verifyNoMoreInteractions(mockIdRelationService);
        verifyNoMoreInteractions(mockUserTagHandler);
    }

}
