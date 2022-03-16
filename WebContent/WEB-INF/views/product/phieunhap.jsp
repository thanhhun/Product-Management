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
					<h4 class="card-title">Đơn đặt hàng</h4>
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
								<th>Mã DDH</th>
								<th>Ngày</th>
								<th>Nhà cung cấp</th>
								<th>Nhân viên thực hiện</th>
								<th>Mã kho</th>
								<th>Trạng thái</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="pd" items="${datHangs}">
								<tr>
									<td>${pd.masoDDH}</td>
									<td>${pd.ngay}</td>
									<td>${pd.nhaCC}</td>
									<td>${pd.nhanVien.ten}</td>
									<td>${pd.kho.maKho}</td>

									<c:choose>
										<c:when test="${pd.phieuNhap == null}">
											<td><span class="badge badge-soft-warning"
												style="font-weight: bold;">Chưa có phiếu nhập</span></td>
											<td><a
												href="product/phieunhap/${id_NV}/${pd.masoDDH}.htm?lnkAdd"
												style="color: #1761fd;">Lập phiếu nhập</a></td>
										</c:when>
										<c:otherwise>
											<td><span class="badge badge-soft-success"
												style="font-weight: bold;">Đã có phiếu nhập</span></td>
											<td><a
												href="product/phieunhap/${id_NV}/${pd.masoDDH}.htm?lnkVisit"
												style="color: #1761fd;">Xem phiếu nhập</a></td>
										</c:otherwise>
									</c:choose>

								</tr>
							</c:forEach>

							<!--khi bam vao tao phieu nhap se form de tao phieu nhap -->

						</tbody>
					</table>
				</div>
			</div>

			<c:if test="${show == 'showFormAdd'}">
				<div class="card">
					<div class="card-header">
						<div class="row align-items-center">
							<div class="col">
								<h4 class="card-title">Lập phiếu nhập</h4>
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

						<form:form action="product/phieunhap/action/${id_NV}.htm"
							modelAttribute="phieunhap">
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
									NV</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="nhanVien.maNV" class="form-control"
										type="text" value="${id_NV}" readonly="true" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
									DDH</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="datHang.masoDDH" class="form-control"
										type="text" value="${dondathang.masoDDH}" readonly="true" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
									PN</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="maPN" class="form-control" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
									KH</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="hoTenKH" class="form-control" type="text" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
									lập</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="ngay" class="form-control" type="text"
										placeholder="MM/DD/YYYY" />
								</div>
							</div>
							<div class="form-group row">
								<label
									class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
									kho</label>
								<div class="col-lg-9 col-xl-8">
									<form:input path="kho.maKho" class="form-control" type="text"
										value="${dondathang.kho.maKho}" readonly="true" />
								</div>
							</div>
							<%-- <form:input path="datHang" value="${dondathang}"/> --%>
							<div class="form-group row">
								<div class="col-lg-9 col-xl-8 offset-lg-3">
									<button name="${btnAction}" type="submit"
										class="btn btn-sm btn-outline-primary">Xác nhận</button>
									<form:button formaction="user/index/${id_NV}.htm" type="submit"
										class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
								</div>
							</div>
						</form:form>
					</div>
					<!--end card-body-->
				</div>
				<!--end card-->
			</c:if>

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

			<c:if test="${show == 'showFormInfo'}">
				<div class="row">
					<div class="col-lg-9">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">Thông tin Phiếu Nhập</h4>
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- id="datatable" -->
								<table id="datatable"
									class="table table-bordered dt-responsive nowrap table-striped table-hover"
									style="border-collapse: collapse; border-spacing: 0; width: 100%;">
									<thead>
										<tr>
											<th>Mã PN</th>
											<th>Mã DH</th>
											<th>Mã Kho</th>
											<th>Họ tên KH</th>
											<th>Ngày Nhập</th>
											<th>Nhân viên thực hiện</th>
											<th></th>
											<th></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${phieuNhapinf.maPN}</td>
											<td>${phieuNhapinf.datHang.masoDDH}</td>
											<td>${phieuNhapinf.kho.maKho}</td>
											<td>${phieuNhapinf.hoTenKH}</td>
											<td>${phieuNhapinf.ngay}</td>
											<td>${phieuNhapinf.nhanVien.ten}</td>
											<td><a
												href="product/phieunhap/chitiet/${phieuNhapinf.maPN.trim()}/${id_NV}.htm">Chi
													tiết</a></td>
											<td><a class="btnEdit"
												href="product/phieunhap/${phieuNhapinf.datHang.masoDDH}/${id_NV}.htm?lnkEdit"><i
													class="ti-settings"></i></a></td>
											<td><a class="btnDelete"
												href="product/phieunhap/${phieuNhapinf.datHang.masoDDH}/${id_NV}.htm?lnkDel"><i
													class="ti-trash"></i></a></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

					</div>
					<!--end col-->

					<c:choose>
						<c:when test="${actionForm != 'readonly'}">
							<div class="col-lg-3">
								<div class="card">
									<div class="card-header">
										<div class="row align-items-center">
											<div class="col">
												<h4 class="card-title">Phiếu nhập</h4>
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

										<form:form action="product/phieunhap/action/${id_NV}.htm"
											modelAttribute="phieunhap">
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
													NV</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="nhanVien.maNV" class="form-control"
														type="text" value="${id_NV}" readonly="true" />
												</div>
											</div>
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
													DDH</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="datHang.masoDDH" class="form-control"
														type="text" value="${dondathang.masoDDH}" readonly="true" />
												</div>
											</div>
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
													PN</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="maPN" class="form-control" type="text" />
												</div>
											</div>
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Tên
													KH</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="hoTenKH" class="form-control" type="text" />
												</div>
											</div>
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Ngày
													lập</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="ngay" class="form-control" type="text"
														placeholder="MM/DD/YYYY" />
												</div>
											</div>
											<div class="form-group row">
												<label
													class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
													kho</label>
												<div class="col-lg-9 col-xl-8">
													<form:input path="kho.maKho" class="form-control"
														type="text" value="${dondathang.kho.maKho}"
														readonly="true" />
												</div>
											</div>
											<%-- <form:input path="datHang" value="${dondathang}"/> --%>
											<div class="form-group row">
												<div class="col-lg-9 col-xl-8 offset-lg-3">
													<button name="${btnAction}" type="submit"
														class="btn btn-sm btn-outline-primary">Xác nhận</button>
													<form:button formaction="user/index/${id_NV}.htm"
														type="submit" class="btn btn-sm btn-outline-danger">Hủy bỏ</form:button>
												</div>
											</div>
										</form:form>
									</div>
									<!--end card-body-->
								</div>
								<!--end card-->
							</div>
							<!--end col-->
						</c:when>
						<c:otherwise>
							<!-- do nothing -->
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			<!-- Khi bấm vào visit sẽ hiện bảng phiếu nhập và vật tư của có trong
							chi tiết đặt hàng đã tạo -->
			<!-- Hiện bảng phiếu nhập (xóa sửa phiếu nhập)-->
			<!-- khi chọn chi tiết thì  -->

			<c:if test="${show == 'showCTPN'}">
				<div class="card">

					<div class="card-header">
						<h4 class="card-title">Các chi tiết trong đơn đặt hàng</h4>

					</div>
					<!--end card-header-->
					<div class="card-body">

						<!-- id="datatable" -->
						<table id="datatable" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>Mã Đặt hàng</th>
									<th>Tên VT</th>
									<th>Số lượng</th>
									<th>Đơn giá</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="c" items="${listCTDDH}">
									<tr>
										<td>${c.datHang.masoDDH}</td>
										<td>${c.vatTu.tenVT}</td>
										<td>${c.soLuong}</td>
										<td>${c.donGia}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-3">
						<div class="card">
							<div class="card-header">
								<div class="row align-items-center">
									<div class="col">
										<h4 class="card-title">Chi tiết phiếu nhập</h4>
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

								<form:form
									action="product/phieunhap/action/chitiet/${id_NV}.htm"
									modelAttribute="chitietphieunhap">
									<form:input path="id" type="hidden" />
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Mã
											PN</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="phieuNhap.maPN" class="form-control"
												type="text" value="${maPN}" readonly="true" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Vật
											tư</label>
										<div class="col-lg-9 col-xl-8">
											<form:select id="result" path="vatTu.maVT"
												class="form-select" items="${listCTDDH}"
												itemLabel="vatTu.tenVT" itemValue="vatTu.maVT"
												onchange="changeVatTu(this)" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Số
											lượng</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="soLuong" class="form-control"
												value="${soluong}" type="text" />
										</div>
									</div>
									<div class="form-group row">
										<label
											class="col-xl-3 col-lg-3 text-end mb-lg-0 align-self-center">Đơn
											giá</label>
										<div class="col-lg-9 col-xl-8">
											<form:input path="donGia" class="form-control"
												value="${dongia}" type="text" />
										</div>
									</div>

									<div class="form-group row">
										<div class="col-lg-9 col-xl-8 offset-lg-3">
											<button name="${btnAction}" type="submit"
												class="btn btn-sm btn-outline-primary">Xác nhận</button>
											<button formaction="product/phieunhap/${id_NV}.htm"
												type="submit" class="btn btn-sm btn-outline-danger">Trở
												về</button>
										</div>
									</div>
								</form:form>
							</div>
							<!--end card-body-->
						</div>
						<!--end card-->
					</div>
					<!--end col-->
					<div class="col-lg-9">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">
									Chi tiết phiếu nhập <strong>${maPN}</strong>
								</h4>
							</div>
							<!--end card-header-->
							<div class="card-body">
								<!-- id="datatable" -->
								<div class="table-responsive" style="max-height: 270px;">
									<table id="datatable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Mã Phiếu Nhập</th>
												<th>Tên VT</th>
												<th>Số lượng</th>
												<th>Đơn giá</th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="c" items="${CTPNs}">
												<tr>
													<td>${c.phieuNhap.maPN}</td>
													<td>${c.vatTu.tenVT}</td>
													<td>${c.soLuong}</td>
													<td>${c.donGia}</td>
													<td><a class="btnEdit"
														href="product/phieunhap/chitiet/${c.phieuNhap.maPN}/${c.vatTu.maVT}/${id_NV}.htm?lnkEdit"><i
															class="ti-settings"></i></a></td>
													<td><a class="btnDelete"
														href="product/phieunhap/chitiet/${c.phieuNhap.maPN}/${c.vatTu.maVT}/${id_NV}.htm?lnkDel"><i
															class="ti-trash"></i></a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!--end col-->
				</div>
			</c:if>


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
	<script>
		function changeVatTu(obj) {
			let value = obj.value;
			let link = document.createElement('a');
			let maVT = document.getElementById("result").value;
			link.href = "product/phieunhap/chitiet/changevattu/" + $
			{
				id_NV
			}
			+"/" + "${maPN}" + "/" + maVT + ".htm";
			/* link.href = "product/phieunhap/chitiet/changevattu.htm"; */
			link.click();

		}
	</script>
	<%@include file="/WEB-INF/views/include/footer.jsp"%>


</body>


</html>
