/**
 * 
 */
package com.wepiao.admin.user.service.test;

import static org.junit.Assert.assertEquals;
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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.MobileRegisterReq;
import com.wepiao.admin.user.rest.msg.MobileRegisterRes;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterReq;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterRes;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.admin.user.service.impl.UserRegisterServiceImpl;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.MobileNoMapping;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.DeviceIdType;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.mq.MsgProducer;
import com.wepiao.user.common.mq.entity.MsgInfo;
import com.wepiao.user.common.redis.RedisUtils4UidGenerate;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;

/**
 * @author Asus
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MsgProducer.class, RedisUtils4UidGenerate.class })
public class UserRegisterServiceTest {

    @Mock
    private UsersHandler            mockUsersHandler;

    @Mock
    private OpenIdInfoMapper        mockOpenIdInfoMapper;

    @Mock
    private OpenIdInfoHandler       mockOpenIdInfoHandler;

    @Mock
    private MobileNum2UIDHandler    mockMobileNum2UIDHandler;

    @Mock
    private IdRelationService       mockIdRelationService;

    @Mock
    private UserDeviceHandler       mockUserDeviceHandler;

    @Mock
    private MsgProducer             mockMsgProducer;

    @InjectMocks
    private UserRegisterServiceImpl UserRegisterService;

    private MobileRegisterReq       mobileRegisterReq;
    private DeviceInfo              deviceInfo;
    private List<DeviceInfo>        deviceinfoList;
    private OpenIdRegisterReq       openIdRegisterReq;
    private OpenIdInfo              openIdInfo;
    private Date                    tempTime;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mobileRegisterReq = new MobileRegisterReq("12345678900", "12345678900", "nickname", "http://a/b/c/d/e");
        deviceinfoList = new ArrayList<DeviceInfo>();
        deviceInfo = new DeviceInfo("mockDeviceId", DeviceIdType.IMEI);
        deviceinfoList.add(0, deviceInfo);
        openIdRegisterReq = new OpenIdRegisterReq("thirdparty_openid", 11, "thirdparty_unionid", "http://a/b/c/d/e", "nickname", 0, 0);
        tempTime = new Date();
        openIdInfo = new OpenIdInfo(123, "thirdparty_openid", OtherID.WEIXIN, "nickname", "http://a/b/c/d/e", tempTime, null, BindingStatus.BOUND,
                tempTime, Status.NORMAL);

        PowerMockito.mockStatic(MsgProducer.class);
        PowerMockito.mockStatic(RedisUtils4UidGenerate.class);
    }

    @After
    public void tearDown() throws Exception {
        mobileRegisterReq = null;
        deviceinfoList.clear();
        deviceInfo = null;
        deviceinfoList = null;
        openIdRegisterReq = null;
        tempTime = null;
        openIdInfo = null;
    }

    @Test(expected = BaseRestException.class)
    public void testRegisterMobileUserExistedError() {
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(0);
        UserRegisterService.registerMobileUser(mobileRegisterReq, deviceinfoList);
    }

    @PrepareForTest({ RedisUtils4UidGenerate.class })
    @Test(expected = BaseRestException.class)
    public void testRegisterMobileUserRedisGetUidFailedEror() {
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        PowerMockito.mockStatic(RedisUtils4UidGenerate.class);
        PowerMockito.when(RedisUtils4UidGenerate.genUid()).thenReturn(null);
        UserRegisterService.registerMobileUser(mobileRegisterReq, deviceinfoList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRegisterMobileUserSuccess() throws Exception {
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        PowerMockito.when(RedisUtils4UidGenerate.class, "genUid").thenReturn(123456);
        when(mockUsersHandler.insert((Users) anyObject())).thenReturn(0);
        when(mockOpenIdInfoMapper.insert((OpenIdInfo) anyObject())).thenReturn(0);
        doNothing().when(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        doNothing().when(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(),
                (Date) anyObject());
        doNothing().when(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        MobileRegisterRes mockMobileRegisterRes = UserRegisterService.registerMobileUser(mobileRegisterReq, deviceinfoList);

        assertEquals(123456, mockMobileRegisterRes.getUid());
        assertEquals(Constants.PREFIX_UID2OPENID_CONVERSION + "123456", mockMobileRegisterRes.getOpenId());
        assertEquals(OtherID.MOBILE.getIntVal(), mockMobileRegisterRes.getOtherId());

        verify(mockUsersHandler).insert((Users) anyObject());
        verify(mockOpenIdInfoMapper).insert((OpenIdInfo) anyObject());
        verify(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        verify(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(), (Date) anyObject());
        verify(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        verifyNoMoreInteractions(mockUsersHandler);
        verifyNoMoreInteractions(mockOpenIdInfoMapper);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testregisterThirdPartyUserNotRegisteredSuccess() throws Exception {
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(null);
        when(mockOpenIdInfoHandler.insert((OpenIdInfo) anyObject())).thenReturn(1);
        doNothing().when(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(),
                (Date) anyObject());
        doNothing().when(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        OpenIdRegisterRes mockOpenIdRegisterRes = UserRegisterService.registerThirdPartyUser(openIdRegisterReq, deviceinfoList);

        assertEquals("thirdparty_openid", mockOpenIdRegisterRes.get_OpenID());
        assertEquals(11, mockOpenIdRegisterRes.get_OtherID());
        assertEquals(Status.NORMAL.getIntVal(), mockOpenIdRegisterRes.getStatus());

        verify(mockOpenIdInfoHandler).insert((OpenIdInfo) anyObject());
        verify(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(), (Date) anyObject());
        verify(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        verifyNoMoreInteractions(mockOpenIdInfoMapper);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testregisterThirdPartyUserRegisteredSuccess() throws Exception {
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockOpenIdInfoHandler.update((OpenIdInfo) anyObject())).thenReturn(openIdInfo);
        doNothing().when(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(),
                (Date) anyObject());
        doNothing().when(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        OpenIdRegisterRes mockOpenIdRegisterRes = UserRegisterService.registerThirdPartyUser(openIdRegisterReq, deviceinfoList);

        assertEquals("thirdparty_openid", mockOpenIdRegisterRes.get_OpenID());
        assertEquals(11, mockOpenIdRegisterRes.get_OtherID());
        assertEquals(Status.NORMAL.getIntVal(), mockOpenIdRegisterRes.getStatus());

        verify(mockOpenIdInfoHandler).update((OpenIdInfo) anyObject());
        verify(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(), (Date) anyObject());
        verify(mockUserDeviceHandler).addUserDevice(anyString(), (OtherID) anyObject(), (List<DeviceInfo>) anyObject());
        verifyNoMoreInteractions(mockOpenIdInfoMapper);
    }

}
