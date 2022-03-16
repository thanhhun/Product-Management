<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>



</head>
<body class="">
	<!-- Left Sidenav -->
	<%@ include file="/WEB-INF/views/include/left-sidenav.jsp"%>
	<!-- end left-sidenav-->
	<!-- container -->
	<div class="page-wrapper">
		<!-- Top Bar Start -->
		<%@ include file="/WEB-INF/views/include/top-bar.jsp"%>
		<!-- Top Bar End -->
		<!-- Page Content-->
		<div class="page-content">

			<div class="container-fluid">
				<!-- Page-Title -->
				<div class="row">
					<div class="col-sm-12">
						<div class="page-title-box">
							<div class="row">
								<div class="col">
									<h4 class="page-title">Thông Tin</h4>
									<ol class="breadcrumb">
										<li class="breadcrumb-item active">Thông tin nhân viên</li>
									</ol>
								</div>
								<!--end col-->
								<div class="col-auto align-self-center">
									<a class="btn btn-sm btn-outline-primary" id="Dash_Date"> <span
										class="day-name" id="Day_Name">Today:</span>&nbsp; <span
										class="" id="Select_date">Jan 11</span> <i
										data-feather="calendar" class="align-self-center icon-xs ms-1"></i>
									</a>
								</div>
								<!--end col-->
							</div>
							<!--end row-->
						</div>
						<!--end page-title-box-->
					</div>
					<!--end col-->
				</div>
				<!--end row-->
				<!-- end page title end breadcrumb -->

				<div class="row">
					<div class="col-12">
						<div class="card">
							<div class="card-body p-0">
								<div id="user_map" class="pro-map" style="height: 220px"></div>
								<!-- <div id="Leaf_default" class="" style="height: 400px"></div> -->
							</div>
							<!--end card-body-->
							<div class="card-body">
								<div class="dastone-profile">
									<div class="row">
										<div class="col-lg-4 align-self-center mb-3 mb-lg-0">
											<div class="dastone-profile-main">
												<div class="dastone-profile-main-pic">
													<img
														src="resources/dashtone/assets/images/users/user-5.jpg"
														alt="" height="110" class="rounded-circle"> <span
														class="dastone-profile_main-pic-change"> <i
														class="fas fa-camera"></i>
													</span>
												</div>
												<div class="dastone-profile_user-detail">
													<h5 class="dastone-user-name">${hoTen}</h5>
													<p class="mb-0 dastone-user-name-post">Nhân viên</p>
												</div>
											</div>
										</div>
										<!--end col-->

										<div class="col-lg-4 ms-auto align-self-center">
											<ul class="list-unstyled personal-detail mb-0">
												<li class=""><i
													class="ti ti-mobile me-2 text-secondary font-16 align-middle"></i>
													<b> Phone </b> : ${user.nhanVien.soDT}</li>
												<li class="mt-2"><i
													class="ti ti-email text-secondary font-16 align-middle me-2"></i>
													<b> Email </b> : ${user.nhanVien.email}</li>
												<li class="mt-2"><i
													class="ti ti-world text-secondary font-16 align-middle me-2"></i>
													<b> Website </b> : <a
													href="https://www.facebook.com/shinshintosancho/"
													class="font-14 text-primary">https://quanlivattu.com</a></li>
											</ul>
										</div>
										<!--end col-->
										<div class="col-lg-4 align-self-center">
											<div class="row">
												<div class="col-auto text-end border-end">
													<button type="button"
														class="btn btn-soft-primary btn-icon-circle btn-icon-circle-sm mb-2">
														<i class="fab fa-facebook-f"></i>
													</button>
													<p class="mb-0 fw-semibold">Facebook</p>
													<h4 class="m-0 fw-bold">
														25k <span class="text-muted font-12 fw-normal">Followers</span>
													</h4>
												</div>
												<!--end col-->
												<div class="col-auto">
													<button type="button"
														class="btn btn-soft-info btn-icon-circle btn-icon-circle-sm mb-2">
														<i class="fab fa-twitter"></i>
													</button>
													<p class="mb-0 fw-semibold">Twitter</p>
													<h4 class="m-0 fw-bold">
														58k <span class="text-muted font-12 fw-normal">Followers</span>
													</h4>
												</div>
												<!--end col-->
											</div>
											<!--end row-->
										</div>
										<!--end col-->
									</div>
									<!--end row-->
								</div>
								<!--end f_profile-->
							</div>
							<!--end card-body-->
						</div>
						<!--end card-->
					</div>
					<!--end col-->
				</div>
				<!--end row-->
				<div class="pb-4">
					<ul class="nav-border nav nav-pills mb-0" id="pills-tab"
						role="tablist">
						<li class="nav-item"><a class="nav-link active"
							id="Profile_Post_tab" data-bs-toggle="pill" href="#Profile_Post">Post</a>
						</li>
						<li class="nav-item"><a class="nav-link"
							id="settings_detail_tab" href="user/setting/${id_NV}.htm">Chỉnh sửa thông tin</a></li>
						<li class="nav-item"><a class="nav-link"
							id="portfolio_detail_tab" href="user/changepassword/${id_NV}.htm">Đổi mật khẩu</a></li>
					</ul>
				</div>

				<div class="row">
					<div class="col-12">
						<div class="tab-content" id="pills-tabContent">
							<div class="tab-pane fade show active" id="Profile_Post"
								role="tabpanel" aria-labelledby="Profile_Post_tab">
								<div class="row">
									<div class="col-lg-9">
										<div class="row">
											<div class="col-lg-6">
												<div class="card">
													<div class="card-body  report-card">
														<div class="row d-flex justify-content-center">
															<div class="col">
																<p class="text-dark mb-1 fw-semibold">Views</p>
																<h3 class="my-2 font-24 fw-bold">24k</h3>
																<p class="mb-0 text-truncate text-muted">
																	<i class="ti ti-bell text-warning font-18"></i> <span
																		class="text-dark fw-semibold">1500</span> New
																	subscribers this week
																</p>
															</div>
															<div class="col-auto align-self-center">
																<div
																	class="d-flex justify-content-center align-items-center thumb-xl bg-light-alt rounded-circle mx-auto">
																	<i
																		class="ti ti-eye font-30 align-self-center text-muted"></i>
																</div>
															</div>
														</div>
													</div>
													<!--end card-body-->
												</div>
												<!--end card-->
											</div>
											<!--end col-->
											<div class="col-lg-6">
												<div class="card">
													<div class="card-body  report-card">
														<div class="row d-flex justify-content-center">
															<div class="col">
																<p class="text-dark mb-1 fw-semibold">Comments</p>
																<h3 class="my-2 font-24 fw-bold">24k</h3>
																<p class="mb-0 text-truncate text-muted">
																	<i class="ti ti-thumb-up text-success font-18"></i> <span
																		class="text-dark fw-semibold">854</span> New Like this
																	week
																</p>
															</div>
															<div class="col-auto align-self-center">
																<div
																	class="d-flex justify-content-center align-items-center thumb-xl bg-light-alt rounded-circle mx-auto">
																	<i
																		class="ti ti-brand-hipchat font-30 align-self-center text-muted"></i>
																</div>
															</div>
														</div>
													</div>
													<!--end card-body-->
												</div>
												<!--end card-->
											</div>
											<!--end col-->
										</div>
										<!--end row-->
										<div class="card">
											<div class="card-body">
												<img src="assets/images/widgets/1.jpg" alt=""
													class="img-fluid">
												<div class="post-title mt-4">
													<div class="row">
														<div class="col">
															<span class="badge badge-soft-primary">Natural</span>
														</div>
														<!--end col-->
														<div class="col-auto">
															<span class="text-muted"><i
																class="far fa-calendar me-1"></i>02 July 2020</span>
														</div>
														<!--end col-->
													</div>
													<!--end row-->

													<h5 href="#" class="font-20 fw-bold d-block mt-3 mb-4">There
														is nothing more beautiful than nature.</h5>
													<span class="font-15 bg-light py-2 px-3 rounded">Taking
														pictures is savouring life intensely.</span>
													<p class="font-15 mt-4">It is a long established fact
														that a reader will be distracted by the readable content
														of a page when looking at its layout. The point of using
														Lorem Ipsum is that it has a more-or-less normal
														distribution of letters, as opposed to using 'Content
														here, content here', making it look like readable English.
														Many desktop publishing packages and web page editors now
														use Lorem Ipsum as their default model text, and a search
														for 'lorem ipsum' will uncover many web sites still in
														their infancy.</p>
													<blockquote class="blockquote border-start ps-4">
														<p class="font-18">
															<i>"Lorem ipsum dolor sit amet, consectetur
																adipiscing elit. Integer posuere erat a ante."</i>
														</p>
														<footer class="blockquote-footer">
															Someone famous in <cite title="Source Title">Source
																Title</cite>
														</footer>
													</blockquote>
													<p class="font-15">Taking pictures is savouring life
														intensely, every hundredth of a second – Marc Riboud. Joy
														in looking and comprehending is nature’s most beautiful
														gift.</p>
													<p class="font-15 mb-0">It is a long established fact
														that a reader will be distracted by the readable content
														of a page when looking at its layout. The point of using
														Lorem Ipsum is that it has a more-or-less normal
														distribution of letters, as opposed to using 'Content
														here, content here', making it look like readable English.
														Many desktop publishing packages and web page editors now
														use Lorem Ipsum as their default model text, and a search
														for 'lorem ipsum' will uncover many web sites still in
														their infancy.</p>
												</div>
											</div>
											<div class="card-body pt-0">
												<div class="row mb-3">
													<div class="col">
														<p class="text-dark fw-semibold mb-0">Artical tags</p>
													</div>
												</div>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Music</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Animals</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Natural</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Food</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Fashion</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Helth</span>
												<span class="badge bg-soft-dark px-3 py-2 fw-semibold">Charity</span>
											</div>
										</div>
									</div>
									<!--end col-->
									<div class="col-lg-3">
										<div class="card">
											<div class="card-body">
												<div class="dash-datepick">
													<input type="hidden" id="light_datepick">
												</div>
											</div>
											<!--end card-body-->
										</div>
										<!--end card-->
										<div class="card">
											<div class="card-header">
												<div class="row align-items-center">
													<div class="col">
														<h4 class="card-title">Social Profile</h4>
													</div>
													<!--end col-->
												</div>
												<!--end row-->
											</div>
											<!--end card-header-->
											<div class="card-body">
												<div class="button-list btn-social-icon">
													<button type="button"
														class="btn btn-soft-primary btn-icon-circle">
														<i class="fab fa-facebook-f"></i>
													</button>

													<button type="button"
														class="btn btn-soft-info btn-icon-circle ms-2">
														<i class="fab fa-twitter"></i>
													</button>

													<button type="button"
														class="btn btn-soft-pink btn-icon-circle  ms-2">
														<i class="fab fa-dribbble"></i>
													</button>
													<button type="button"
														class="btn btn-soft-info btn-icon-circle  ms-2">
														<i class="fab fa-linkedin-in"></i>
													</button>
													<button type="button"
														class="btn btn-soft-danger btn-icon-circle  ms-2">
														<i class="fab fa-google-plus-g"></i>
													</button>
												</div>
											</div>
											<!--end card-body-->
										</div>
										<!--end card-->
									</div>
									<!--end col-->
								</div>
								<!--end row-->
							</div>
						</div>
						<!--end tab-content-->
					</div>
					<!--end col-->
				</div>


				<!-- container -->
				<footer class="footer text-center text-sm-start">
					©
					<script>
						document.write(new Date().getFullYear())
					</script>
					2021 Dastone <span
						class="text-muted d-none d-sm-inline-block float-end">Crafted
						with <i class="mdi mdi-heart text-danger"></i> by Mannatthemes
					</span>
				</footer>
				<!--end footer-->
			</div>
			<!-- end page content -->
		</div>
		<!-- end page-wrapper -->
	</div>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
