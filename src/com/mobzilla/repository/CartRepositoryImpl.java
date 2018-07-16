package com.mobzilla.repository;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mobzilla.entity.AddProductBean;
import com.mobzilla.entity.CartBean;
import com.mobzilla.entity.LoginBean;
import com.mobzilla.entity.OrdersBean;
import com.mobzilla.entity.ProductBean;

@Repository
public class CartRepositoryImpl implements CartRepository {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public AddProductBean productExist(CartBean cart) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Transaction txn=session.beginTransaction();
		Query query=session.createQuery("FROM CartBean WHERE userId= :user AND productId= :product");
		query.setParameter("user", cart.getUserId());
		query.setParameter("product", cart.getProductId());
		
		List<CartBean> products = query.list();
		txn.commit();
		AddProductBean add=new AddProductBean();
		if(products.isEmpty()) {
			add.setQuantity(1);
			add.setExist(false);
			return add;
		}
		else {
			add.setQuantity(products.get(0).getQuantity());
			add.setExist(true);
			return add;
		}
	}

	@Override
	public boolean addProduct(CartBean cart) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Transaction txn=session.beginTransaction();
		
		session.save(cart);
		txn.commit();
		return true;
	}

	@Override
	public boolean updateProduct(CartBean cart) {
		
		Session session=sessionFactory.getCurrentSession();
		Transaction txn=session.beginTransaction();
		
		Query query=session.createQuery("UPDATE CartBean c SET c.quantity= :new WHERE c.userId= :user AND c.productId= :product");
		query.setParameter("new",cart.getQuantity());
		query.setParameter("user", cart.getUserId());
		query.setParameter("product", cart.getProductId());
		query.executeUpdate();
		txn.commit();
		return true;
	}

	@Override
	public List<CartBean> getCartProducts(LoginBean login) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Transaction txn=session.beginTransaction();
		
		Query query=session.createQuery("FROM CartBean WHERE userId= :user");
		
		query.setParameter("user", login.getEmail());
		
		List<CartBean> list=query.list();
		txn.commit();

		
		
		return list;
	}

	@Override
	public boolean orderProducts(LoginBean login,List<CartBean> cartList) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Transaction txn=session.beginTransaction();
		OrdersBean order=new OrdersBean();
		CartBean cart=null;
		for(int i=0;i<cartList.size();i++) {
			cart=cartList.get(i);
			order.setProductId(cart.getProductId());
			order.setUserId(cart.getUserId());
			order.setProductPrice(cart.getTotalPrice()*cart.getQuantity());
			
			session.save(order);
		}
		
		
		/*Query query=session.createQuery("DELETE CartBean WHERE userId= :user");
		
		query.setParameter("user", login.getEmail());
		
		query.executeUpdate();*/
		txn.commit();
		
		
		
		
		return true;
	}
	
	

}