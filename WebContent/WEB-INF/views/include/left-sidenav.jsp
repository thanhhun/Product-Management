<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="left-sidenav">
	<!-- LOGO -->
	<div class="brand">
		<a href="index.html" class="logo"> <span> <img
				src="resources/dashtone/assets/images/logo-sm.png" alt="logo-small"
				class="logo-sm">
		</span> <span> <img src="resources/dashtone/assets/images/logo.png"
				alt="logo-large" class="logo-lg logo-light"> <img
				src="resources/dashtone/assets/images/logo-dark.png"
				alt="logo-large" class="logo-lg logo-dark">
		</span>
		</a>
	</div>
	<!--end logo-->
	<div class="menu-content h-100" data-simplebar>
		<ul class="metismenu left-sidenav-menu">
			<li class="menu-label mt-0">Hệ thống</li>

			<li><a href="javascript: void(0);"><i data-feather="lock"
					class="align-self-center menu-icon"></i><span>Tài khoản</span><span
					class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
				<ul class="nav-second-level" aria-expanded="false">
					<li class="nav-item"><a class="nav-link"
						href="user/index/${id_NV}.htm"><i class="ti-control-record"></i>Trang
							chủ</a></li>
					<li class="nav-item"><a class="nav-link"
						href="login/lock/${id_NV}.htm"><i class="ti-control-record"></i>Khóa
							màn hình</a></li>
				</ul></li>

			<c:if test="${phanQuyen == 'admin'}">
				<li><a href="javascript: void(0);"><i data-feather="grid"
						class="align-self-center menu-icon"></i><span>Nhân viên</span><span
						class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
					<ul class="nav-second-level" aria-expanded="false">

						<li class="nav-item"><a class="nav-link"
							href="user/createaccount/${id_NV}.htm"><i
								class="ti-control-record"></i>Tạo tài khoản nhân viên</a></li>
						<li class="nav-item"><a class="nav-link"
							href="user/list/${id_NV}.htm"><i class="ti-control-record"></i>Danh
								sách nhân viên</a></li>
						<li class="nav-item"><a class="nav-link"
							href="user/chinhanh/${id_NV}.htm"><i
								class="ti-control-record"></i>Các chi nhánh</a></li>

					</ul></li>
			</c:if>



			<hr class="hr-dashed hr-menu">
			<li class="menu-label my-2">Vật tư</li>
			<li><a href="javascript: void(0);"> <i data-feather="home"
					class="align-self-center menu-icon"></i><span>Kho vật tư</span><span
					class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
				<ul class="nav-second-level" aria-expanded="false">
					<li class="nav-item"><a class="nav-link"
						href="product/dathang/${id_NV}.htm"><i
							class="ti-control-record"></i>Đặt hàng</a></li>
					<li class="nav-item"><a class="nav-link"
						href="product/phieunhap/${id_NV}.htm"><i
							class="ti-control-record"></i>Lập phiếu nhập</a></li>
					<li class="nav-item"><a class="nav-link"
						href="product/phieuxuat/${id_NV}.htm"><i
							class="ti-control-record"></i>Lập phiếu xuất</a></li>
					<li class="nav-item"><a class="nav-link"
						href="product/list/${id_NV}.htm"><i class="ti-control-record"></i>Danh
							sách vật tư</a></li>
					<li class="nav-item"><a class="nav-link"
						href="product/kho/${id_NV}.htm"><i class="ti-control-record"></i>Danh
							sách kho</a></li>
				</ul></li>



			<li><a href="#"><i data-feather="layers"
					class="align-self-center menu-icon"></i><span>Widgets</span><span
					class="badge badge-soft-success menu-arrow">New</span></a></li>

			<li><a href="javascript: void(0);"><i
					data-feather="file-plus" class="align-self-center menu-icon"></i><span>Pages</span><span
					class="menu-arrow"><i class="mdi mdi-chevron-right"></i></span></a>
				<ul class="nav-second-level" aria-expanded="false">
					<li class="nav-item"><a class="nav-link"
						href="pages-blogs.html"><i class="ti-control-record"></i>Blogs</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-faqs.html"><i class="ti-control-record"></i>FAQs</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-pricing.html"><i class="ti-control-record"></i>Pricing</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-profile.html"><i class="ti-control-record"></i>Profile</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-starter.html"><i class="ti-control-record"></i>Starter
							Page</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-timeline.html"><i class="ti-control-record"></i>Timeline</a></li>
					<li class="nav-item"><a class="nav-link"
						href="pages-treeview.html"><i class="ti-control-record"></i>Treeview</a></li>
				</ul></li>
		</ul>

		<div class="update-msg text-center">
			<a href="javascript: void(0);" class="float-end close-btn text-muted"
				data-dismiss="update-msg" aria-label="Close" aria-hidden="true">
				<i class="mdi mdi-close"></i>
			</a>
			<h5 class="mt-3">Mannat Themes</h5>
			<p class="mb-3">We Design and Develop Clean and High Quality Web
				Applications</p>
			<a href="javascript: void(0);" class="btn btn-outline-warning btn-sm">Upgrade
				your plan</a>
		</div>
	</div>
</div>
