package com.mobzilla.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobzilla.entity.AddressBean;
import com.mobzilla.entity.ForgotBean;
import com.mobzilla.entity.LoginBean;
import com.mobzilla.entity.UserBean;
import com.mobzilla.repository.UserRepository;




@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;

	@Override
	public Boolean registerUser(UserBean user,AddressBean address) {
		// TODO Auto-generated method stub
		CodeGenerator code=new CodeGenerator();
		user.setVerification(code.generateCode());
		return repo.registerUser(user,address);
	}

	@Override
	public Boolean validate(LoginBean login) {
		// TODO Auto-generated method stub
		return repo.validate(login);
	}

	@Override
	public String matchDetails(UserBean user) {
		// TODO Auto-generated method stub
		return repo.matchDetails(user);
	}

	@Override
	public boolean changePassword(ForgotBean bean) {
		
		// TODO Auto-generated method stub
		if(bean.getPassword1().equals(bean.getPassword2())) {
			
			return repo.changePassword(bean);
		}
		else
		return false;
	}

	@Override
	public AddressBean getUserAddress(LoginBean login) {
		// TODO Auto-generated method stub
		return repo.getUserAddress(login);
	}

	@Override
	public boolean alreadyRegistered(UserBean user) {
		// TODO Auto-generated method stub
		return repo.checkRegistered(user);
	}

	@Override
	public UserBean getUserDetails(LoginBean login) {
		// TODO Auto-generated method stub
		return repo.getProfile(login);
	}

	@Override
	public boolean checkVerify(String email,String code) {
		// TODO Auto-generated method stub
		return repo.checkVerify(email,code);
	}

	@Override
	public String getCode(UserBean user) {
		// TODO Auto-generated method stub
		return repo.getCode(user);
	}

}
