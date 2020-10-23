package com.hodo.bean;


/**
 * sessionInfo模型
 * 
 * @author 郭东升
 * 
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String userId;// 用户ID
	private static String loginName;// 用户登录名称
	private static String loginPassword;// 登录密码
	private static String ip;// IP地址
	private static String authIds;// 拥有的权限ID集合
	private static String authNames;
	private static String authUrls;
	private static String roleIds;
	private static String roleNames;
	private static String realName;
	private static String userCode;
	private static String userGroup;

	private static String deptId;// 部门ID
	private static String deptName;// 部门名称

	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		SessionInfo.userId = userId;
	}

	public static String getLoginName() {
		return loginName;
	}

	public static void setLoginName(String loginName) {
		SessionInfo.loginName = loginName;
	}

	public static String getLoginPassword() {
		return loginPassword;
	}

	public static void setLoginPassword(String loginPassword) {
		SessionInfo.loginPassword = loginPassword;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		SessionInfo.ip = ip;
	}

	public static String getAuthIds() {
		return authIds;
	}

	public static void setAuthIds(String authIds) {
		SessionInfo.authIds = authIds;
	}

	public static String getAuthNames() {
		return authNames;
	}

	public static void setAuthNames(String authNames) {
		SessionInfo.authNames = authNames;
	}

	public static String getAuthUrls() {
		return authUrls;
	}

	public static void setAuthUrls(String authUrls) {
		SessionInfo.authUrls = authUrls;
	}

	public static String getRoleIds() {
		return roleIds;
	}

	public static void setRoleIds(String roleIds) {
		SessionInfo.roleIds = roleIds;
	}

	public static String getRoleNames() {
		return roleNames;
	}

	public static void setRoleNames(String roleNames) {
		SessionInfo.roleNames = roleNames;
	}

	public static String getRealName() {
		return realName;
	}

	public static void setRealName(String realName) {
		SessionInfo.realName = realName;
	}

	public static String getUserCode() {
		return userCode;
	}

	public static void setUserCode(String userCode) {
		SessionInfo.userCode = userCode;
	}

	public static String getUserGroup() {
		return userGroup;
	}

	public static void setUserGroup(String userGroup) {
		SessionInfo.userGroup = userGroup;
	}

	public static String getDeptId() {
		return deptId;
	}

	public static void setDeptId(String deptId) {
		SessionInfo.deptId = deptId;
	}

	public static String getDeptName() {
		return deptName;
	}

	public static void setDeptName(String deptName) {
		SessionInfo.deptName = deptName;
	}
}
