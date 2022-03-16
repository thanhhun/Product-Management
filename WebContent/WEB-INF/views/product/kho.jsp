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
					<h4 class="card-title">Danh sách kho</h4>
				</div>
				<!--end card-header-->
				<div class="card-body">
					<!-- id="datatable" -->
					<table id="datatable"
						class="table table-bordered dt-responsive nowrap table-striped table-hover"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<thead>
							<tr>
								<th>Mã kho</th>
								<th>Tên kho</th>
								<th>Địa chỉ</th>
								<th>Chi nhánh</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${khos}">
								<tr>
									<td>${pd.maKho}</td>
									<td>${pd.tenKho}</td>
									<td>${pd.diaChi}</td>
									<td>${pd.chiNhanh.tenCN}</td>
									<td><a class="btnEdit"
										href="product/kho/${pd.maKho.trim()}/${id_NV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="product/kho/${pd.maKho.trim()}/${id_NV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card">
				<div class="card-header">
					<h4 class="card-title">Kho</h4>
				</div>
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
					<form:form action="product/kho/action/${id_NV}.htm"
						modelAttribute="kho">
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
								kho</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maKho" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
								kho</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="tenKho" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Địa
								chỉ</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="diaChi" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Chi
								nhánh</label>
							<div class="col-lg-9 col-xl-8">
								<c:if test="${quyen == 'admin'}">
									<form:select path="chiNhanh.maCN" class="form-select"
										items="${chiNhanhs}" itemLabel="tenCN" itemValue="maCN" />
								</c:if>
								<c:if test="${quyen == 'nhanvien'}">
									<form:input path="chiNhanh.maCN" class="form-control"
										type="text" value="${chiNhanh.trim()}" readonly="true" />
								</c:if>
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
