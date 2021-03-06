package com.mobzilla.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.mobzilla.entity.AddressBean;
import com.mobzilla.entity.ErrorBean;
import com.mobzilla.entity.ForgotBean;
import com.mobzilla.entity.LoginBean;
import com.mobzilla.entity.UserBean;
import com.mobzilla.entity.VerificationBean;
import com.mobzilla.services.CartService;
import com.mobzilla.services.CodeGenerator;
import com.mobzilla.services.HomeService;
import com.mobzilla.services.SmsSender;
import com.mobzilla.services.UserService;

@Controller
@SessionAttributes({ "personalDetails", "userEmail", "userLogin" })

public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private HomeService homeService;

	@Autowired
	private CartService cartService;
	
	

	@RequestMapping(value = "LoginUser.shop")
	public String loginUser(LoginBean login, Model model) {
		
		try{

		if (service.validate(login)) {
			System.out.println("user found------------");
			model.addAttribute("BrandList", homeService.getAllBrands());
			model.addAttribute("ProductList", homeService.getAllProducts());
			model.addAttribute("userLogin", login);
			return "Home";
		} else {
			System.out.println("user not found------------");
			model.addAttribute("userNotFound", "true");
			model.addAttribute("BrandList", homeService.getAllBrands());
			return "Login";
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			
			model.addAttribute("return", "LoginUser.shop");
			return "errpage";
		}
	}

	@RequestMapping(value = "LoginPage.shop")
	public String loginPage(Model model) {
		try{
		model.addAttribute("BrandList", homeService.getAllBrands());
		}
		catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "LoginPage.shop");
			return "errpage";
		}
		return "Login";
	}

	@RequestMapping(value = "RegisterUser.shop")
	public String registerUser(UserBean user, Model model) {

		try{
		System.out.println(user.getUserLastName());

		model.addAttribute("personalDetails", user);

		if (service.alreadyRegistered(user)) {

			model.addAttribute("alreadyRegistered", "true");
			return "Login";
		} else
			return "Address";
		}
		catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "RegisterUser.shop");
			return "errpage";
		}
	}

	@RequestMapping(value = "AddAddress.shop")
	public String addAddress(@ModelAttribute("personalDetails") UserBean user, AddressBean address, Model model) {

		try{
		System.out.println(user.getUserEmail());
		System.out.println(address.getAddressLine1());
		address.setUser(user.getUserEmail());
		Boolean isRegistered = service.registerUser(user, address);
		if (isRegistered) {
			return "Welcome";
		} else {

			model.addAttribute("BrandList", homeService.getAllBrands());
			model.addAttribute("ProductList", homeService.getAllProducts());
			return "Home";
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "RegisterUser.shop");
			return "errpage";
		}
	}

	@RequestMapping(value = "forgotPassword.shop")
	public String forgetPassPage() {
		return "ForgotPassword";
	}

	@RequestMapping(value = "newPass.shop")
	public String forgotPassword(UserBean user, Model model) {
		
		try{
		// fBean=new ForgotBean();
		SmsSender sms = new SmsSender();
		String email = service.matchDetails(user);
		if (email != null) {
			sms.sendOTP(user.getUserContact(), service.getCode(user));
			ForgotBean bean = new ForgotBean();
			bean.setEmail(email);
			model.addAttribute("userEmail", bean);
			return "EnterCode";
		} else {
			model.addAttribute("isPhoneCorrect", "false");
			return "ForgotPassword";
		}
		}catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "forgotPassword.shop");
			return "errpage";
		}
	}

	@RequestMapping(value = "otp.shop")
	public String verifyOTP(@ModelAttribute("vbean") VerificationBean vBean,
			@ModelAttribute("userEmail") ForgotBean bean, Model model) {
		
		try{
		System.out.println(vBean.getCode());

		if (service.checkVerify(bean.getEmail(), vBean.getCode())) {
			return "ReEnterPass";
		} else {
			model.addAttribute("noOtp", "true");
			return "ForgotPassword";
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "forgotPassword");
			return "errpage";
		}
	}

	@RequestMapping(value = "ChangePass.shop", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("userEmail") ForgotBean bean, Model model) {

		try{
		if (service.changePassword(bean)) {
			model.addAttribute("passChaged", "true");
			return "Login";
		} else {

			model.addAttribute("userEmail", bean);
			model.addAttribute("doPassMatch", "false");
			return "ReEnterPass";
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "forgotPassword.shop");
			return "errpage";
		}

	}

	@RequestMapping("LogoutUser.shop")
	public String logoutUser(Model model, WebRequest request, SessionStatus status) {

		status.setComplete();
		model.addAttribute("userLogin", null);
		model.addAttribute("BrandList", homeService.getAllBrands());
		return "Index";
	}

	@RequestMapping("profile.shop")
	public String getUserProfile(Model model, HttpSession session) {

		try {
			LoginBean login = (LoginBean) session.getAttribute("userLogin");
			model.addAttribute("BrandList", homeService.getAllBrands());
			model.addAttribute("address", service.getUserAddress(login));
			model.addAttribute("user", service.getUserDetails(login));
			model.addAttribute("orders", cartService.getUserOrders(login));

			return "Profile";

		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("return", "profile.shop");
			return "errpage";
		}

	}
}
