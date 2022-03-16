<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style>
.btnDelete, .btnEdit {
	font-size: 15px;
}

.btnDelete {
	color: red;
}

.btnEdit {
	color: #1761fd;
}
</style>
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

			<div class="card">

				<div class="card-header">
					<h4 class="card-title">Default Datatable</h4>
					<p class="text-muted mb-0">
						DataTables has most features enabled by default, so all you need
						to do to use it with your own tables is to call the construction
						function:
						<!-- <code>$().DataTable();</code> -->
						.
					</p>
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>ID Đăng nhập</th>
								<th>Mật khẩu</th>
								<th>Họ tên nhân viên</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${DangNhaps}">
								<tr>
									<td>${pd.nhanVien.maNV}</td>
									<td>${pd.matKhau}</td>
									<td>${pd.nhanVien.ho} ${pd.nhanVien.ten}</td>
									<td><a class="btnEdit"
										href="user/createaccount/${pd.nhanVien.maNV}/${id_NV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="user/createaccount/${pd.nhanVien.maNV}/${id_NV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card" style="width: 40%;">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Tạo tài khoản</h4>
						</div>
						<!--end col-->
					</div>
					<!--end row-->
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- Thông báo -->
					<c:choose>
						<c:when test="${check==null}">
						</c:when>
						<c:when test="${check == 1}">
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

					<form:form action="user/createaccount/action/${id_NV}.htm"
						modelAttribute="dangnhap">
						<form:input path="idDangNhap" type="hidden" />
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã các nhân viên</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="nhanVien.maNV" class="form-select"
									items="${NhanViens}" itemLabel="maNV" itemValue="maNV" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mật
								khẩu</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="matKhau" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<form:button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
							</div>
						</div>
					</form:form>
				</div>
				<!--end card-body-->
			</div>
			<!--end card-->
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

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
