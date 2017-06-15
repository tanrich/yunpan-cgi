package com.yunpan.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.User;
import com.yunpan.bean.UserShare;
import com.yunpan.db.DBAccess;

public class UserShareDao {
	/**
	 * 
	 * @param userShare新增用户分享记录
	 */
	public void insertShare(UserShare userShare) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("UserShare.insertShare", userShare);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 删除用户分享记录
	 */
	public void deleteShare(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.delete("UserShare.deleteShare", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 查看用户所有的分享
	 */
	public List<UserShare> selectAllShare(User user) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		List<UserShare> list = new ArrayList<UserShare>();
		try {
			sqlSession = db.getSqlSession();
			list = sqlSession.selectList("UserShare.selectAllShare", user);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;
	}

	/**
	 * 根据url查找分享出来的文件
	 */
	public UserShare selectShare(String url) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		UserShare userShare = new UserShare();
		try {
			sqlSession = db.getSqlSession();
			userShare = sqlSession.selectOne("UserShare.selectShare", url);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userShare;
	}
	/**
	 * 根据id查找分享出来的文件
	 */
	public UserShare selectShareById(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		UserShare userShare = new UserShare();
		try {
			sqlSession = db.getSqlSession();
			userShare = sqlSession.selectOne("UserShare.selectShareById", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userShare;
	}
	/**
	 * 下载次数加1
	 */
	public void updateDLtimes(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("UserShare.updateDLtimes", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 
	 * 保存次数加1
	 */
	public void updateStimes(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("UserShare.updateStimes", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
