package com.yunpan.dao;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.User;
import com.yunpan.db.DBAccess;

public class UserDao {
	/**
	 * 新增用户信息
	 */
	public int insertUser(User user) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		int id = 0;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("User.insertUser", user);
			id = user.getId();
			sqlSession.commit();
		} catch (Exception e) {
			return 0;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return id;
	}

	/**
	 * 查找用户（依据用户名）
	 */
	public User queryUser(String username) {
		User user = new User();
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			user = sqlSession.selectOne("User.selectUsername", username);
			sqlSession.commit();
		} catch (IOException e) {
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return user;
	}
}
