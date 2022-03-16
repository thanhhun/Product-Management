<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>


</head>
<body class="account-body accountbg">
	<!-- Log In page -->
	<div class="container">
		<div class="row vh-100 d-flex justify-content-center">
			<div class="col-12 align-self-center">
				<div class="row">
					<div class="col-lg-5 mx-auto">
						<div class="card">
							<div class="card-body p-0 auth-header-box">
								<div class="text-center p-3">
									<a href="login.htm" class="logo logo-admin"> <img
										src="resources/dashtone/assets/images/logo-sm-dark.png"
										height="50" alt="logo" class="auth-logo">
									</a>
									<h4 class="mt-3 mb-1 fw-semibold text-white font-18">Quản
										lý Vật Tư</h4>
									<p class="text-muted  mb-0">Quản Lý Vật Tư.</p>
								</div>
							</div>
							<div class="card-body p-0">
								<ul class="nav-border nav nav-pills" role="tablist">
									<li class="nav-item"><a
										class="nav-link active fw-semibold" data-bs-toggle="tab"
										href="login.htm" role="tab">Log In</a></li>
								</ul>
								<!-- Tab panes -->
								<div class="tab-content">
									<div class="tab-pane active p-3" id="LogIn_Tab" role="tabpanel">
										<form:form class="form-horizontal auth-form"
											action="login/action.htm" modelAttribute="account">
											<c:choose>
												<c:when test="${check==null}">
													<div class="alert alert-light border-0" role="alert">
														<strong>Đăng nhập!</strong> để vào trang quản Lý.
													</div>
												</c:when>
												<c:otherwise>
													<div class="alert alert-danger border-0" role="alert">
														<strong>Thông báo!</strong> ${message}.
													</div>
												</c:otherwise>
											</c:choose>
											<div class="form-group mb-2">
												<label class="form-label" for="username">Tên đăng
													nhập</label>
												<div class="input-group">
													<form:input path="nhanVien.maNV" type="text"
														class="form-control" id="username"
														placeholder="Nhập tên đăng nhập" />
													<form:errors path="nhanVien.maNV" />
												</div>
											</div>
											<!--end form-group-->

											<div class="form-group mb-2">
												<label class="form-label" for="userpassword">Mật
													khẩu</label>
												<div class="input-group">
													<form:input path="matKhau" type="password"
														class="form-control" id="userpassword"
														placeholder="Nhập mật khẩu" />
													<form:errors path="matKhau" />
												</div>
											</div>
											<!--end form-group-->

											<div class="form-group row my-3">
												<div class="col-sm-6">
													<div class="custom-control custom-switch switch-success">
														<input type="checkbox" class="custom-control-input"
															id="customSwitchSuccess"> <label
															class="form-label text-muted" for="customSwitchSuccess">Ghi
															nhớ</label>
													</div>
												</div>
												<!--end col-->
												<div class="col-sm-6 text-end">
													<a href="login/forgotpw.htm" class="text-muted font-13"><i
														class="dripicons-lock"></i> Quên mật khẩu?</a>
												</div>
												<!--end col-->
											</div>
											<!--end form-group-->

											<div class="form-group mb-0 row">
												<div class="col-12">
													<button
														class="btn btn-primary w-100 waves-effect waves-light"
														type="submit">
														Đăng Nhập <i class="fas fa-sign-in-alt ms-1"></i>
													</button>
												</div>
												<!--end col-->
											</div>
											<!--end form-group-->
										</form:form>
										<!--end form-->
										<div class="m-3 text-center text-muted">
											<p class="mb-0">
												Don't have an account ? <a href="auth-register.html"
													class="text-primary ms-2">Free Resister</a>
											</p>
										</div>
										<div class="account-social">
											<h6 class="mb-3">Or Login With</h6>
										</div>
										<div class="btn-group w-100">
											<button type="button"
												class="btn btn-sm btn-outline-secondary">Facebook</button>
											<button type="button"
												class="btn btn-sm btn-outline-secondary">Twitter</button>
											<button type="button"
												class="btn btn-sm btn-outline-secondary">Google</button>
										</div>
									</div>
								</div>
							</div>
							<!--end card-body-->
							<div class="card-body bg-light-alt text-center">
								<span class="text-muted d-none d-sm-inline-block">Mannatthemes
									© <script>
										document
												.write(new Date().getFullYear())
									</script>
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
	<!-- End Log In page -->

	<%@include file="/WEB-INF/views/include/footer.jsp"%>

</body>

</html>