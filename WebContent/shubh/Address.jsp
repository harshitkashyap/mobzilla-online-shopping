<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/home.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cinzel|Monoton|Muli|PT+Sans|Philosopher|Raleway"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<style type="text/css">
	.background{
	 position: fixed;
  left: 0;
  right: 0;
  z-index: -1;
  display: block;
  background-image: url('css/pattern.svg');
  width: 100%;
  height: 100%;
  -webkit-filter: blur(2px);
  -moz-filter: blur(2px);
  -o-filter: blur(2px);
  -ms-filter: blur(2px);
  filter: blur(2px);
	}
	</style>
</head>

<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<body>
	<%@ include file="NavigationBanner.jsp"%>
	<div class="smartphone" style="background-color: #FFF">
		<div class="content" style="overflow: scroll; height: 480px;">
			<button class="back" onclick="history.go(-1)"></button>
			<h2>Personal Details</h2>
			<hr class="custom">
			<spring:form action="parent.window.location.href='AddAddress.shop'"
				modelAttribute="addressDetails" method="get">
				<div class="form-group">
					<label for="add1">ADDRESS LINE 1<span class="star">*</span></label>
					<input type="text" class="form-control" name="addressLine1">
				</div>
				<div class="form-group">
					<label for="add2">ADDRESS LINE 2<span class="star">*</span></label>
					<input type="text" class="form-control" name="addressLine2">
				</div>
				<div class="form-group">
					<label for="city">CITY<span class="star">*</span></label> <input
						type="text" class="form-control" name="city">
				</div>
				<div class="form-group">
					<label for="state">STATE<span class="star">*</span></label> <input
						type="text" class="form-control" name="state">
				</div>
				<div class="form-group">
					<label for="pincode">PIN CODE<span class="star">*</span></label> <input
						type="text" class="form-control" name="pin">
				</div>
				<!-- <button type="submit" class="btnN">SUBMIT</button> -->
			</spring:form>
			<button onclick="window.location.href='AddAddress.shop'"
				class="btnL">SUBMIT</button>
		</div>
	</div>
</body>
</html>