<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>


</head>
<body class="account-body accountbg">

	<!-- Lock screen page -->
	<div class="container">
		<div class="row vh-100 d-flex justify-content-center">
			<div class="col-12 align-self-center">
				<div class="row">
					<div class="col-lg-5 mx-auto">
						<div class="card">
							<div class="card-body p-0 auth-header-box">
								<div class="text-center p-3">
									<a href="#" class="logo logo-admin"> <img
										src="resources/dashtone/assets/images/logo-sm-dark.png"
										height="50" alt="logo" class="auth-logo">
									</a>
									<h4 class="mt-3 mb-1 fw-semibold text-white font-18">Nhập
										mật khẩu để vào Quản Lý Vật Tư</h4>
									<p class="text-muted  mb-0">Xin chào ${Ten}, hãy nhập mật
										khẩu của bạn để mở khóa màn hình!</p>
								</div>
							</div>
							<div class="card-body">
								${message}
								<form:form class="form-horizontal auth-form"
									action="login/unlock/${id_NV}.htm" modelAttribute="account">

									<div class="form-group mb-2">
										<label class="form-label" for="username">Mật khẩu</label>
										<div class="input-group mb-3">
											<input name="password" type="password" class="form-control"
												id="password" placeholder="Nhập mật khẩu">
										</div>
									</div>
									<!--end form-group-->

									<div class="form-group mb-0 row">
										<div class="col-12">
											<button
												class="btn btn-primary w-100 waves-effect waves-light"
												type="submit">
												Mở khóa <i class="fas fa-sign-in-alt ms-1"></i>
											</button>
										</div>
										<!--end col-->
									</div>
									<!--end form-group-->
								</form:form>
								<!--end form-->
								<p class="text-muted mb-0 mt-3">
									Not you ? return <a href="auth-register.html"
										class="text-primary ms-2">Sign In</a>
								</p>
							</div>
							<div class="card-body bg-light-alt text-center">
								<span class="text-muted d-none d-sm-inline-block">Mannatthemes
									© <script>
										document
												.write(new Date().getFullYear())
									</script>2021
								</span>
							</div>
						</div>
						<!--end card-->
					</div>
					<!--end col-->
				</div>
				<!--end row-->
			</div>
			<!--end col-->
		</div>
		<!--end row-->
	</div>
	<!--end container-->
	<!-- End lock screen page -->




	<%@include file="/WEB-INF/views/include/footer.jsp"%>




	<!-- Mirrored from mannatthemes.com/dastone/default/auth-lock-screen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 04 Aug 2021 07:06:05 GMT -->
</body>
</html>