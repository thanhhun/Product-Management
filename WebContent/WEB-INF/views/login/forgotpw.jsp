<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>


</head>
<body class="account-body accountbg">
	<!-- Recover-pw page -->
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
									<h4 class="mt-3 mb-1 fw-semibold text-white font-18">Reset
										Password For Dastone</h4>
									<p class="text-muted  mb-0">Enter your Email and
										instructions will be sent to you!</p>
								</div>
							</div>
							<div class="card-body">

								<c:if test="${show=='sendCode'}">
									<c:choose>
										<c:when test="${message==null}">
											<div class="alert alert-light border-0" role="alert">
												<strong>Quên mật khẩu!</strong>
											</div>
										</c:when>
										<c:otherwise>
											<div class="alert alert-danger border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:otherwise>
									</c:choose>
									<form class="form-horizontal auth-form"
										action="login/sendcode.htm" method="post">
										<div class="form-group mb-2">
											<label class="form-label" for="username">Tài khoản</label>
											<div class="input-group">
												<input name="id" type="text" class="form-control"
													placeholder="Nhập tên tài khoản">
											</div>
										</div>
										<div class="form-group mb-2">
											<label class="form-label" for="username">Email</label>
											<div class="input-group">
												<input name="email" type="email" class="form-control"
													id="email" placeholder="Email">
											</div>
										</div>
										<!--end form-group-->

										<div class="form-group mb-0 row">
											<div class="col-12 mt-2">
												<button
													class="btn btn-primary w-100 waves-effect waves-light"
													type="submit">
													Send Code <i class="fas fa-sign-in-alt ms-1"></i>
												</button>
											</div>
											<!--end col-->
										</div>
										<!--end form-group-->
									</form>
									<!--end form-->
									<p class="text-muted mb-0 mt-3">
										Remember It ? <a href="login/index.htm"
											class="text-primary ms-2">Sign in here</a>
									</p>
								</c:if>
								<c:if test="${show=='confirmCode'}">
									<c:choose>
										<c:when test="${check=='success'}">
											<div class="alert alert-success border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:when>
										<c:otherwise>
											<div class="alert alert-danger border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:otherwise>
									</c:choose>
									<form class="form-horizontal auth-form"
										action="login/confirmcode/${id_NV}.htm" method="post">
										<div class="form-group mb-2">
											<label class="form-label" for="username">Mã xác nhận</label>
											<div class="input-group">
												<input name="confirmCode" type="text" class="form-control"
													placeholder="Nhập mã xác nhận">
											</div>
										</div>
										<!--end form-group-->
										<div class="form-group mb-0 row">
											<div class="col-12 mt-2">
												<button
													class="btn btn-primary w-100 waves-effect waves-light"
													type="submit">
													Xác nhận <i class="fas fa-sign-in-alt ms-1"></i>
												</button>
											</div>
											<!--end col-->
										</div>
										<!--end form-group-->
									</form>
									<!--end form-->
									<p class="text-muted mb-0 mt-3">
										Remember It ? <a href="login/index.htm"
											class="text-primary ms-2">Sign in here</a>
									</p>
								</c:if>
								<c:if test="${show=='newPassword'}">
									<c:choose>
										<c:when test="${check==null}">
										</c:when>
										<c:when test="${check=='success'}">
											<div class="alert alert-success border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:when>
										<c:otherwise>
											<div class="alert alert-danger border-0" role="alert">
												<strong>Thông báo!</strong> ${message}.
											</div>
										</c:otherwise>
									</c:choose>
									<form class="form-horizontal auth-form"
										action="login/newpw/${id_NV}.htm" method="post">
										<div class="form-group mb-2">
											<label class="form-label" for="username">Mật khẩu mới</label>
											<div class="input-group">
												<input name="newpw" type="password" class="form-control"
													placeholder="Nhập mật khẩu mới">
											</div>
										</div>
										<div class="form-group mb-2">
											<label class="form-label" for="username">Xác nhận mật
												khẩu</label>
											<div class="input-group">
												<input name="confirmpw" type="password" class="form-control"
													id="email" placeholder="Nhập lại mật khẩu mới">
											</div>
										</div>
										<!--end form-group-->
										<div class="form-group mb-0 row">
											<div class="col-12 mt-2">
												<c:choose>
													<c:when test="${check=='success'}">
														<a href="login/index.htm"
															class="btn btn-primary w-100 waves-effect waves-light">
															Quay lại trang chủ <i class="fas fa-sign-in-alt ms-1"></i>
														</a>
													</c:when>
													<c:otherwise>
														<button
															class="btn btn-primary w-100 waves-effect waves-light"
															type="submit">
															Thay đổi <i class="fas fa-sign-in-alt ms-1"></i>
														</button>
													</c:otherwise>
												</c:choose>

											</div>
											<!--end col-->
										</div>
										<!--end form-group-->
									</form>
									<!--end form-->
								</c:if>
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

	<%@include file="/WEB-INF/views/include/footer.jsp"%>

</body>

</html>