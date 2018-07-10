package com.mobzilla.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mobzilla.entity.AddressBean;
import com.mobzilla.entity.LoginBean;
import com.mobzilla.entity.UserBean;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean registerUser(UserBean user,AddressBean address) {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction txn=null;
		try {
			session=sessionFactory.getCurrentSession();
			txn=session.beginTransaction();
			session.save(user);
			session.save(address);
			txn.commit();
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return false;
		}
		
	}

	@Override
	public Boolean validate(LoginBean login) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		String hql="userName FROM UserBean where userEmail = :username AND userPass= :userpass";
		
		Transaction txn=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setParameter("username",login.getEmail());
		query.setParameter("userpass", login.getPassword());
		String userName=query.toString();
		txn.commit();
		if(userName!=null)
			return true;
		else
			return null;
	}

}
