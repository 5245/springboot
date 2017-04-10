/**
 * 
 */
package com.wepiao.admin.user.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wepiao.admin.user.rest.msg.BindMobileNoReq;
import com.wepiao.admin.user.rest.msg.BindMobileNoRes;
import com.wepiao.admin.user.rest.msg.ChangePasswordReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UpdateMobileNoReq;
import com.wepiao.admin.user.rest.msg.UserInfoUpdateRes;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.admin.user.service.handler.UsersOperationLimitHandler;
import com.wepiao.admin.user.service.impl.UserInfoUpdateServiceImpl;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.MobileNoMapping;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.handler.IdRelationHandler;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.mq.MsgProducer;
import com.wepiao.user.common.mq.entity.MsgInfo;
import com.wepiao.user.common.redis.RedisUtils4UidGenerate;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.MD5Utils;

/**
 * @author Asus
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MsgProducer.class, RedisUtils4UidGenerate.class })
//@PrepareForTest(RedisUtils4UidGenerate.class)
public class UserInfoUpdateServiceTest {

    @Mock
    private UsersHandler               mockUsersHandler;

    @Mock
    private UserTagHandler             mockUserTagHandler;

    @Mock
    private OpenIdInfoHandler          mockOpenIdInfoHandler;

    @Mock
    private OpenIdInfoMapper           mockOpenIdInfoMapper;

    @Mock
    private MobileNum2UIDHandler       mockMobileNum2UIDHandler;

    @Mock
    private IdRelationHandler          mockIdRelationHandler;

    @Mock
    private IdRelationService          mockIdRelationService;

    @Mock
    private UsersOperationLimitHandler mockUsersOperationLimitHandler;

    @Mock
    private MsgProducer                mockMsgProducer;

    @InjectMocks
    private UserInfoUpdateServiceImpl  mockUserInfoUpdateService;

    private String                     updateUserReqId;
    private Map<String, Object>        updateUserReqMap;
    private Map<String, Object>        errorUpdateUserReqMap;
    private BindMobileNoReq            bindMobileNoReq;
    private UpdateMobileNoReq          updateMobileNoReq;
    private ChangePasswordReq          changePasswordReq;

    private Date                       tempTime;
    private OpenIdInfo                 openIdInfo;
    private IdRelationNode             uidIdRelationNode;
    private IdRelationNode             nonUidIdRelationNode;
    private List<IdRelationNode>       nonUidList;

    private String                     passwd;
    private String                     wrongPasswd;
    private Users                      noPasswdUser;
    private Users                      existedUser;
    private Users                      correctPasswdUser;
    private Users                      wrongPasswdUser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        updateUserReqId = "mockUpdateUserReqId";
        updateUserReqMap = new HashMap<String, Object>();
        updateUserReqMap.put("memberid", "123456");
        updateUserReqMap.put("mobileNo", "12345678900");
        updateUserReqMap.put("extUid", "654321");
        updateUserReqMap.put("city", "beijing");
        updateUserReqMap.put("nickname", "nickname");
        updateUserReqMap.put("sex", 2);
        updateUserReqMap.put("birthday", "1996-01-02");
        updateUserReqMap.put("email", "");
        updateUserReqMap.put("photourl", "");
        updateUserReqMap.put("name", "");
        updateUserReqMap.put("userkey", "");
        updateUserReqMap.put("signature", "二锅头");
        updateUserReqMap.put("maritalStat", 4);
        updateUserReqMap.put("carrer", 11);
        updateUserReqMap.put("enrollmentYear", 2016);
        updateUserReqMap.put("highestEdu", 3);
        updateUserReqMap.put("school", "北京大学");
        updateUserReqMap.put("watchCPNum", 2);

        errorUpdateUserReqMap = new HashMap<String, Object>();
        errorUpdateUserReqMap.put("memberid", "error_memberid");

        bindMobileNoReq = new BindMobileNoReq("123456", 12, "12345678900");
        updateMobileNoReq = new UpdateMobileNoReq("123456", "00987654321", "12345678900");
        changePasswordReq = new ChangePasswordReq("123456", "00987654321", "12345678900", 0, "12345678900");

        tempTime = new Date();
        openIdInfo = new OpenIdInfo(123, "thirdparty_openid", OtherID.QQ, "nickname", "http://a/b/c/d/e", tempTime, null, BindingStatus.BOUND,
                tempTime, Status.NORMAL);
        uidIdRelationNode = new IdRelationNode("123456", OtherID.UID);
        nonUidIdRelationNode = new IdRelationNode("123456", OtherID.QQ);
        nonUidList = new ArrayList<IdRelationNode>();
        nonUidList.add(nonUidIdRelationNode);

        passwd = MD5Utils.getEncryptedPwd("12345678900");
        wrongPasswd = MD5Utils.getEncryptedPwd("wrongPasswd");
        existedUser = new Users();
        noPasswdUser = new Users(123456, "12345678900", null, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null, tempTime,
                tempTime, null);
        correctPasswdUser = new Users(123456, "12345678900", passwd, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null,
                tempTime, tempTime, null);
        wrongPasswdUser = new Users(123456, "12345678900", wrongPasswd, null, null, Status.NORMAL, null, Gender.UNKNOWN, null, null, null, null,
                tempTime, tempTime, null);

        PowerMockito.mockStatic(MsgProducer.class);
        PowerMockito.mockStatic(RedisUtils4UidGenerate.class);
    }

    @After
    public void tearDown() throws Exception {
        updateUserReqId = null;
        updateUserReqMap.clear();
        updateUserReqMap = null;
        errorUpdateUserReqMap.clear();
        errorUpdateUserReqMap = null;

        bindMobileNoReq = null;
        updateMobileNoReq = null;
        changePasswordReq = null;

        tempTime = null;
        openIdInfo = null;
        uidIdRelationNode = null;
        nonUidIdRelationNode = null;
        nonUidList.clear();
        nonUidList = null;

        passwd = null;
        wrongPasswd = null;
        noPasswdUser = null;
        existedUser = null;
        correctPasswdUser = null;
        wrongPasswdUser = null;
    }

    // updateUserInfo
    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoNoUidError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, updateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoNoUserError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, updateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoSexFormatError() {
        errorUpdateUserReqMap.put("sex", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoSexIllegalArgumentError() {
        errorUpdateUserReqMap.put("sex", "123");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoBirthdayFormatError() {
        errorUpdateUserReqMap.put("birthday", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoBirthdayTimeError() {
        errorUpdateUserReqMap.put("birthday", "0-0-0");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoMaritalstatFormatError() {
        errorUpdateUserReqMap.put("maritalstat", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoCarrerFormatError() {
        errorUpdateUserReqMap.put("carrer", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoEnrollmentyearFormatError() {
        errorUpdateUserReqMap.put("enrollmentyear", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoHighesteduFormatError() {
        errorUpdateUserReqMap.put("highestedu", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateUserInfoWatchcpnumFormatError() {
        errorUpdateUserReqMap.put("watchcpnum", "wrongformat");
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);

        mockUserInfoUpdateService.updateUserInfo(updateUserReqId, errorUpdateUserReqMap);
    }

    @Test
    public void testUpdateUserInfoSuccess() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUserTagHandler.queryAllUserTag(anyString(), (OtherID) anyObject())).thenReturn(null);
        when(mockUserTagHandler.addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(true);
        when(mockUsersHandler.update((Users) anyObject())).thenReturn(0);
        when(mockUsersHandler.getExtUidByUid(anyInt())).thenReturn("654321");

        UserInfoUpdateRes mockUserInfoUpdateRes = mockUserInfoUpdateService.updateUserInfo(updateUserReqId, updateUserReqMap);

        assertEquals(123456, mockUserInfoUpdateRes.get_UID());
        assertEquals("12345678900", mockUserInfoUpdateRes.getMobileNo());
        assertEquals("654321", mockUserInfoUpdateRes.getExtUid());
        assertEquals(123456, mockUserInfoUpdateRes.getMemberId());

        verify(mockUsersHandler).getUid(anyString());
        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockUserTagHandler).queryAllUserTag(anyString(), (OtherID) anyObject());
        verify(mockUserTagHandler, times(7)).addUserTag(anyString(), (OtherID) anyObject(), anyString(), anyString(), anyBoolean(), anyLong());
        verify(mockUsersHandler).update((Users) anyObject());
        verify(mockUsersHandler).getExtUidByUid(anyInt());

        verifyNoMoreInteractions(mockUsersHandler);
        verifyNoMoreInteractions(mockUserTagHandler);
    }

    // bindMobileNo
    @Test(expected = BaseRestException.class)
    public void testBindMobileNoNullOpenidInfoError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(null);
        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    // Case 1
    // isMobileNoMappingExisted && isOpenIdMappingExisted
    @Test(expected = BaseRestException.class)
    public void testBindMobileNoMobileOpenIdExistedBoundByAnotherError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(0);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(uidIdRelationNode);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    @Test(expected = BaseRestException.class)
    public void testBindMobileNoMobileOpenIdExistedBoundBySelfError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(123456);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(uidIdRelationNode);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    // Case 2
    // isMobileNoMappingExisted && !isOpenIdMappingExisted
    @Test(expected = BaseRestException.class)
    public void testBindMobileNoMobileExistedIdRelationMultiPlatformError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).checkMemberIdBindCount(anyInt(), anyString());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(123456);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(nonUidIdRelationNode);
        when(mockIdRelationService.getIdListFromRoot((IdRelationNode) anyObject())).thenReturn(nonUidList);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    @Test
    public void testBindMobileNoMobileExistedSuccess() throws Exception {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).checkMemberIdBindCount(anyInt(), anyString());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(123456);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(nonUidIdRelationNode);
        when(mockIdRelationService.getIdListFromRoot((IdRelationNode) anyObject())).thenReturn(null);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(existedUser);
        when(mockUsersHandler.update((Users) anyObject())).thenReturn(0);
        doNothing().when(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(),
                (Date) anyObject());
        when(mockIdRelationService.getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject())).thenReturn(nonUidList);
        when(mockUsersHandler.getExtUidByUid(anyInt())).thenReturn("testextuid");
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        BindMobileNoRes bindMobileNoRes = mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);

        assertEquals(123456, bindMobileNoRes.get_UID());
        assertEquals("testextuid", bindMobileNoRes.getExtUid());

        verify(mockOpenIdInfoHandler, times(2)).getOpenIdInfoByOpenId(anyString());
        verify(mockMobileNum2UIDHandler).getUIDByMobileNo(anyString());
        verify(mockIdRelationHandler).getRootNode((IdRelationNode) anyObject());
        verify(mockIdRelationService).getIdListFromRoot((IdRelationNode) anyObject());
        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockUsersHandler).update((Users) anyObject());
        verify(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(), (Date) anyObject());
        verify(mockIdRelationService).getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject());
        verify(mockUsersHandler).getExtUidByUid(anyInt());

        verifyNoMoreInteractions(mockMobileNum2UIDHandler);
        verifyNoMoreInteractions(mockIdRelationHandler);
        verifyNoMoreInteractions(mockIdRelationService);
        verifyNoMoreInteractions(mockUsersHandler);
    }

    // Case 3
    // !isMobileNoMappingExisted && isOpenIdMappingExisted
    @Test(expected = BaseRestException.class)
    public void testBindMobileNoOpenIdExistedNoUserError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).checkMemberIdBindCount(anyInt(), anyString());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(uidIdRelationNode);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(null);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    @Test(expected = BaseRestException.class)
    public void testBindMobileNoOpenIdExistedMobileEqualError() {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).checkMemberIdBindCount(anyInt(), anyString());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(uidIdRelationNode);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(noPasswdUser);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    @Test
    public void testBindMobileNoOpenIdExistedSuccess() throws Exception {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).checkMemberIdBindCount(anyInt(), anyString());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(uidIdRelationNode);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(existedUser);
        when(mockUsersHandler.update((Users) anyObject())).thenReturn(0);
        doNothing().when(mockMobileNum2UIDHandler).deleteMobileNo2UIDMapping(anyString());
        doNothing().when(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        when(mockUsersHandler.getExtUidByUid(anyInt())).thenReturn("testextuid");
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        BindMobileNoRes bindMobileNoRes = mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);

        assertEquals(123456, bindMobileNoRes.get_UID());
        assertEquals("testextuid", bindMobileNoRes.getExtUid());

        verify(mockOpenIdInfoHandler).getOpenIdInfoByOpenId(anyString());
        verify(mockMobileNum2UIDHandler).getUIDByMobileNo(anyString());
        verify(mockIdRelationHandler).getRootNode((IdRelationNode) anyObject());
        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockUsersHandler).update((Users) anyObject());
        verify(mockMobileNum2UIDHandler).deleteMobileNo2UIDMapping(anyString());
        verify(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        verify(mockUsersHandler).getExtUidByUid(anyInt());

        verifyNoMoreInteractions(mockOpenIdInfoHandler);
        verifyNoMoreInteractions(mockMobileNum2UIDHandler);
        verifyNoMoreInteractions(mockIdRelationHandler);
        verifyNoMoreInteractions(mockUsersHandler);
    }

    // Case 4
    // !isMobileNoMappingExisted && !isOpenIdMappingExisted
    @PrepareForTest({ RedisUtils4UidGenerate.class })
    @Test(expected = BaseRestException.class)
    public void testBindMobileNoRedisGenUidError() throws Exception {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(nonUidIdRelationNode);
        PowerMockito.when(RedisUtils4UidGenerate.class, "genUid").thenReturn(null);

        mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);
    }

    @Test
    public void testBindMobileNoSuccess() throws Exception {
        doNothing().when(mockUsersOperationLimitHandler).checkOpenIdBindCount((BindMobileNoReq) anyObject());
        when(mockOpenIdInfoHandler.getOpenIdInfoByOpenId(anyString())).thenReturn(openIdInfo);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockIdRelationHandler.getRootNode((IdRelationNode) anyObject())).thenReturn(nonUidIdRelationNode);
        PowerMockito.when(RedisUtils4UidGenerate.class, "genUid").thenReturn(123456);
        when(mockUsersHandler.insert((Users) anyObject())).thenReturn(0);
        doNothing().when(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        doNothing().when(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(),
                (Date) anyObject());
        when(mockIdRelationService.getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject())).thenReturn(nonUidList);
        doNothing().when(mockMsgProducer).sendMsg((MsgInfo<?>) anyObject());

        BindMobileNoRes bindMobileNoRes = mockUserInfoUpdateService.bindMobileNo(bindMobileNoReq);

        assertEquals(123456, bindMobileNoRes.get_UID());

        verify(mockOpenIdInfoHandler, times(2)).getOpenIdInfoByOpenId(anyString());
        verify(mockMobileNum2UIDHandler).getUIDByMobileNo(anyString());
        verify(mockIdRelationHandler).getRootNode((IdRelationNode) anyObject());
        verify(mockUsersHandler).insert((Users) anyObject());
        verify(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        verify(mockIdRelationService).insertIdRelation(anyString(), (OtherID) anyObject(), anyString(), (OtherID) anyObject(), (Date) anyObject());
        verify(mockIdRelationService).getIdListFromRootByType((IdRelationNode) anyObject(), (OtherID) anyObject());

        verifyNoMoreInteractions(mockMobileNum2UIDHandler);
        verifyNoMoreInteractions(mockIdRelationHandler);
        verifyNoMoreInteractions(mockUsersHandler);
        verifyNoMoreInteractions(mockIdRelationService);
    }

    // updateMobileNo
    @Test(expected = BaseRestException.class)
    public void testUpdateMobileNoGetUidError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(null);

        mockUserInfoUpdateService.updateMobileNo(updateMobileNoReq);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateMobileNoGetUserError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123);
        doNothing().when(mockUsersOperationLimitHandler).checkUpdateMobileCount(anyString(), anyString());
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(null);

        mockUserInfoUpdateService.updateMobileNo(updateMobileNoReq);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateMobileNoOldMobileNoError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123);
        doNothing().when(mockUsersOperationLimitHandler).checkUpdateMobileCount(anyString(), anyString());
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(existedUser);

        mockUserInfoUpdateService.updateMobileNo(updateMobileNoReq);
    }

    @Test(expected = BaseRestException.class)
    public void testUpdateMobileNoOldMobileNoExistedError() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123);
        doNothing().when(mockUsersOperationLimitHandler).checkUpdateMobileCount(anyString(), anyString());
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(noPasswdUser);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(0);

        mockUserInfoUpdateService.updateMobileNo(updateMobileNoReq);
    }

    @Test
    public void testUpdateMobileNoSuccess() {
        when(mockUsersHandler.getUid(anyString())).thenReturn(123);
        doNothing().when(mockUsersOperationLimitHandler).checkUpdateMobileCount(anyString(), anyString());
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(noPasswdUser);
        when(mockMobileNum2UIDHandler.getUIDByMobileNo(anyString())).thenReturn(Constants.NOT_EXISTED_UID);
        when(mockUsersHandler.update((Users) anyObject())).thenReturn(0);
        doNothing().when(mockMobileNum2UIDHandler).deleteMobileNo2UIDMapping(anyString());
        doNothing().when(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());
        doNothing().when(mockUsersOperationLimitHandler).recordUserLimitOperate(anyString(), anyInt());

        SingleResultRes mockSingleResultRes = mockUserInfoUpdateService.updateMobileNo(updateMobileNoReq);

        assertEquals(0, mockSingleResultRes.getResult());

        verify(mockUsersHandler).getUid(anyString());
        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockMobileNum2UIDHandler).getUIDByMobileNo(anyString());
        verify(mockUsersHandler).update((Users) anyObject());
        verify(mockMobileNum2UIDHandler).deleteMobileNo2UIDMapping(anyString());
        verify(mockMobileNum2UIDHandler).insertMobileNo2UIDMapping((MobileNoMapping) anyObject());

        verifyNoMoreInteractions(mockUsersHandler);
        verifyNoMoreInteractions(mockMobileNum2UIDHandler);
    }

    // changePassword
    @Test(expected = BaseRestException.class)
    public void testChangePasswordOverLimitError() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(true);

        mockUserInfoUpdateService.changePassword(changePasswordReq);
    }

    @Test(expected = BaseRestException.class)
    public void testChangePasswordNoUidError() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.getUid(anyString())).thenReturn(null);

        mockUserInfoUpdateService.changePassword(changePasswordReq);
    }

    @Test(expected = BaseRestException.class)
    public void testChangePasswordNoUserError() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(null);
        doNothing().when(mockUsersOperationLimitHandler).addUpdatePWCount(anyString());

        mockUserInfoUpdateService.changePassword(changePasswordReq);
    }

    @Test(expected = BaseRestException.class)
    public void testChangePasswordNoOldPasswdError() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(noPasswdUser);
        doNothing().when(mockUsersOperationLimitHandler).addUpdatePWCount(anyString());

        mockUserInfoUpdateService.changePassword(changePasswordReq);
    }

    @Test(expected = BaseRestException.class)
    public void testChangePasswordInvalidOldPasswdError() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(wrongPasswdUser);
        doNothing().when(mockUsersOperationLimitHandler).addUpdatePWCount(anyString());

        mockUserInfoUpdateService.changePassword(changePasswordReq);
    }

    @Test
    public void testChangePasswordSuccess() {
        when(mockUsersOperationLimitHandler.isUpdatePWLimit(anyString())).thenReturn(false);
        when(mockUsersHandler.getUid(anyString())).thenReturn(123456);
        when(mockUsersHandler.queryOneByUid(anyInt())).thenReturn(correctPasswdUser);
        when(mockUsersHandler.updatePwd(anyInt(), anyString())).thenReturn(1);
        doNothing().when(mockUsersOperationLimitHandler).addUpdatePWCount(anyString());

        SingleResultRes mockSingleResultRes = mockUserInfoUpdateService.changePassword(changePasswordReq);
        ;

        assertEquals(0, mockSingleResultRes.getResult());

        verify(mockUsersHandler).queryOneByUid(anyInt());
        verify(mockUsersHandler).updatePwd(anyInt(), anyString());
    }

}
