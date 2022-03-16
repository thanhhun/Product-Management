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
				<!--PageListHolder  -->
				<%-- <jsp:useBean id="pagedListHolder" scope="request"
					type="org.springframework.beans.support.PagedListHolder" />
				<c:url value="user/list/${id_NV}.htm" var="pagedLink">
					<c:param name="p" value="~" />
				</c:url> --%>


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
								<th>ID</th>
								<th>Tên Chi Nhánh</th>
								<th>Họ</th>
								<th>Tên</th>
								<th>Ngày Sinh</th>
								<th>Lương</th>
								<th>SĐT</th>
								<th>Email</th>
								<th>Địa chỉ</th>
								<th>Quyền</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${nhanViens}">
								<tr>
									<td>${pd.maNV}</td>
									<td>${pd.chiNhanh.tenCN}</td>
									<td>${pd.ho}</td>
									<td>${pd.ten}</td>
									<td>${pd.ngaySinh}</td>
									<td>${pd.luong}</td>
									<td>${pd.soDT}</td>
									<td>${pd.email}</td>
									<td>${pd.diaChi}</td>
									<td>${pd.phanQuyen.tenPhanQuyen}</td>
									<td><a class="btnEdit"
										href="user/list/${pd.maNV}/${id_NV}.htm?lnkEdit"><i
											class="ti-settings"></i></a></td>
									<td><a class="btnDelete"
										href="user/list/${pd.maNV}/${id_NV}.htm?lnkDel"><i
											class="ti-trash"></i></a></td>
								</tr>

							</c:forEach>

						</tbody>
					</table>

					<%-- <tg:paging pagedListHolder="${pagedListHolder}"
						pagedLink="${pagedLink}" /> --%>

				</div>
			</div>

			<div class="card" style="width: 40%;">
				<div class="card-header">
					<div class="row align-items-center">
						<div class="col">
							<h4 class="card-title">Nhân Viên</h4>
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

					<form:form action="user/list/action/${id_NV}.htm"
						modelAttribute="user">
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
								NV</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="maNV" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Họ</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ho" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ten" class="form-control" type="text" />
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
								sinh</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="ngaySinh" class="form-control" type="text" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số
								điện thoại</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-phone"></i></span>
									<form:input path="soDT" type="text" class="form-control"
										placeholder="Phone" aria-describedby="basic-addon1" />
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Email</label>
							<div class="col-lg-9 col-xl-8">
								<div class="input-group">
									<span class="input-group-text"><i class="las la-at"></i></span>
									<form:input path="email" type="text" class="form-control"
										placeholder="Email" aria-describedby="basic-addon1" />
								</div>
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
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Lương</label>
							<div class="col-lg-9 col-xl-8">
								<form:input path="luong" class="form-control" type="text" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
								chi nhánh</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="chiNhanh.maCN" class="form-select"
									items="${chiNhanhs}" itemLabel="tenCN" itemValue="maCN" />
							</div>
						</div>

						<div class="form-group row">
							<label
								class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Phân
								Quyền</label>
							<div class="col-lg-9 col-xl-8">
								<form:select path="phanQuyen.idPhanQuyen" class="form-select"
									items="${phanQuyens}" itemLabel="tenPhanQuyen"
									itemValue="idPhanQuyen" />
							</div>
						</div>

						<div class="form-group row">
							<div class="col-lg-9 col-xl-8 offset-lg-3">
								<form:button name="${btnAction}" type="submit"
									class="btn btn-sm btn-outline-primary">Xác nhận</form:button>
								<button formaction="user/index/${id_NV}.htm" type="submit"
									class="btn btn-sm btn-outline-danger">Hủy bỏ</button>
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
