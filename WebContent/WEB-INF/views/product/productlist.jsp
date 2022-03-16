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
			<div class="container-fluid">
				<!-- Page-Title -->
				<div class="row">
					<div class="col-sm-12">
						<div class="page-title-box">
							<div class="row">
								<div class="col">
									<h4 class="page-title">Danh sách Vật Tư</h4>
									<ol class="breadcrumb">
										<li class="breadcrumb-item active">List</li>
									</ol>
								</div>
								<!--end col-->
								<div class="col-auto align-self-center">
									<a href="#" class="btn btn-sm btn-outline-primary"
										id="Dash_Date"> <span class="day-name" id="Day_Name">Today:</span>&nbsp;
										<span class="" id="Select_date">Jan 11</span> <i
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
						<div class="table-responsive">
							<table id="datatable" class="table table-bordered">
								<thead>
									<tr>
										<th>Tên vật tư</th>
										<th>Mã vật tư</th>
										<th>Đơn vị tính</th>
										<th>Số lượng</th>
										<th>Avai.Color</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="v" items="${VatTus}">
										<tr>
											<td><img src="resources/img-product/${v.phoTo}" alt=""
												height="40">
												<p class="d-inline-block align-middle mb-0">
													<a href=""
														class="d-inline-block align-middle mb-0 product-name">${v.tenVT}</a>
													<br> <span class="text-muted font-13">size-08
														(Model 2020)</span>
												</p></td>
											<td>${v.maVT}</td>
											<td>${v.donVi}</td>
											<td>${v.soLuongTon}</td>
											<td>
												<ul class="list-inline mb-0">
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-success"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-pink"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-info"></i></li>
													<li class="list-inline-item align-middle"><i
														class="fas fa-circle text-warning"></i></li>
												</ul>
											</td>
											<td><a class="btnEdit"
												href="product/list/${v.maVT.trim()}/${id_NV}.htm?lnkEdit"><i
													class="las la-pen text-secondary font-16"></i></a> <a
												class="btnDelete"
												href="product/list/${v.maVT.trim()}/${id_NV}.htm?lnkDel"><i
													class="las la-trash-alt text-secondary font-16"></i></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- end col -->
				</div>
				<!-- end row -->
				<div class="card" style="width: 40%;">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Vật Tư</h4>
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

						<form:form action="product/list/action/${id_NV}.htm"
							modelAttribute="vattu" enctype="multipart/form-data">
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
									VT</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="maVT" class="form-control" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
									vật tư </label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="tenVT" class="form-control" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Đơn
									vị tính</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="donVi" class="form-control" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số
									lượng tồn</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="soLuongTon" class="form-control" type="text" />
								</div>
							</div>

							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Hình
									ảnh minh họa(nếu có)</label>
								<div class="col-lg-9 col-xl-8">
									<input name="phoToVT" type="file" id="input-file-now"
										class="dropify">
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

	<%@include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
